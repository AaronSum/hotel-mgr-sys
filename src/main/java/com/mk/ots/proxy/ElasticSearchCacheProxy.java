package com.mk.ots.proxy;

import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import com.mk.ots.utils.SpringContextUtil;

@Component
public class ElasticSearchCacheProxy {

	private static final String ES_KEYWORD_CACHE = "es_keyword_cache";

	private static final String ES_KEYWORD_CACHE_ID = "es_keyword_cache_id";

	private Logger logger = LoggerFactory.getLogger(ElasticSearchCacheProxy.class);

	/** 异步执行，防止reids拖慢主业务流程,单线程同步保证一致性 ,保证redis down掉好后不影响主流程. */
	private ArrayBlockingQueue<Long> keywordIdQueue = null;

	public ElasticSearchCacheProxy() {
		this.keywordIdQueue = new ArrayBlockingQueue<Long>(10);
		new Thread(new UpdateCacheKeywordIdTask()).start();
	}

	public Long getKeywordId() {
		return this.getKeywordCache().get(ElasticSearchCacheProxy.ES_KEYWORD_CACHE_ID, Long.class);
	}

	public void setKeywordMaxId(Long id) {
		this.getKeywordIdQueue().add(id);
	}

	private void setKeywordCacheMaxId(Long id) {
		this.getKeywordCache().put(ElasticSearchCacheProxy.ES_KEYWORD_CACHE_ID, id);
	}

	private Cache getKeywordCache() {
		return this.getRedisCacheManager().getCache(ElasticSearchCacheProxy.ES_KEYWORD_CACHE);
	}

	private RedisCacheManager getRedisCacheManager() {
		return SpringContextUtil.getApplicationContext().getBean(RedisCacheManager.class);
	}

	private void updateCacheKeywordId() {
		while (true) {
			try {
				Long newId = this.getKeywordIdQueue().take();
				Long cacheId = this.getKeywordId();
				if ((cacheId != null) && (newId < cacheId)) {
					continue;
				}
				this.setKeywordCacheMaxId(newId);
			} catch (InterruptedException e) {
				this.logger.error("ElasticSearchCacheProxy updateCacheKeywordId thread interrupted!", e);
			}
		}
	}

	private ArrayBlockingQueue<Long> getKeywordIdQueue() {
		return this.keywordIdQueue;
	}

	private class UpdateCacheKeywordIdTask implements Runnable {
		@Override
		public void run() {
			ElasticSearchCacheProxy.this.updateCacheKeywordId();
		}
	}

}
