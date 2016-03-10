package com.mk.hms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.mapper.WareImageMapper;
import com.mk.hms.model.WareImage;
import com.mk.hms.model.WareImageCriteria;

/**
 * WareImage service
 */
@Service
@Transactional
public class WareImageService {

	@Autowired
	private WareImageMapper wareImageMapper = null;
	
	/**
	 * 获取商品列表的图片
	 * @param WareId 商品ID
	 * @param imageType 获取图片类型(1-大图片  2-中图片  3-小图片)
	 * @return List<WareImage>
	 */
	public List<WareImage> findWareImageListByWareId(long wareId) {
		WareImageCriteria wareImageCriteria = new WareImageCriteria();
		wareImageCriteria.setOrderByClause("orderby");
		wareImageCriteria.createCriteria().andValidEqualTo(new Integer(1)).andWareidEqualTo(wareId);
		
		return this.getWareImageMapper().selectByExample(wareImageCriteria);
	}

	private WareImageMapper getWareImageMapper() {
		return wareImageMapper;
	}
	
}
