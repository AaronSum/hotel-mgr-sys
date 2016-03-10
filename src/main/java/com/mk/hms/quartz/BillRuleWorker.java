package com.mk.hms.quartz;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mk.hms.model.HMSHotelRuleModel;
import com.mk.hms.service.HmsHotelRuleService;

public class BillRuleWorker {

	@Autowired
	private HmsHotelRuleService hmsHotelRuleService;

	private static final Logger logger = Logger.getLogger(BillRuleWorker.class);
	protected ThreadPoolExecutor threadPool;
	/*
	 * 线程池属性 线程池保存线程大小 corePoolSize 保存任务的队列大小 queueCapacity 线程池最大规模
	 * maximumPoolSize 超过规模线程最大存活时间 keepAliveTime
	 * 
	 * allowCoreThreadTimeOut 设置为true是，线程规模超过max时，corepoolsize内的线程超过存活时间也将关闭
	 */
	private int corePoolSize = 10;
	private int queueCapacity = 1000;
	protected int maximumPoolSize = 50;
	protected long keepAliveTime = 60L;
	// 时间
	protected long timeout = 30L;

	/**
	 * <p>
	 * Title: 定时器
	 * </p>
	 * <p>
	 * Description: 在构造时初始化线程池
	 * </p>
	 */
	public BillRuleWorker() {
		ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(queueCapacity);
		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,keepAliveTime, TimeUnit.SECONDS, workQueue);
	}

	/**
	 * @throws Exception
	 * @Title: doTask
	 * @Description: 定时器入口
	 */

	public void doTask() throws Exception {
		// 获取有效数据
		List<HMSHotelRuleModel> rules = hmsHotelRuleService.getStateTask();
		// 是否需要执行task
		if (CollectionUtils.isEmpty(rules)) {
			logger.info("没有数据，不需要处理");
			return;
		}
		logger.info("the task Method start...");
		// 线程计数
		final CountDownLatch doneSingal = new CountDownLatch(rules.size());
		// 同步数据
		for (final HMSHotelRuleModel rule : rules) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// 账单任务处理
						hmsHotelRuleService.executeBillTask(rule);
					} finally {
						doneSingal.countDown();
					}
				}
			});
		}
		// 等待直到所有待拆分订单处理完成
		doneSingal.await(timeout, TimeUnit.MINUTES);
		logger.info("the task Method down...");
	}

}
