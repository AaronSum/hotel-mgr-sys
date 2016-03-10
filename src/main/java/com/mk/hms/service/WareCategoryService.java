package com.mk.hms.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mk.hms.mapper.WareCategoryMapper;
import com.mk.hms.model.WareCategory;
import com.mk.hms.model.WareCategoryCriteria;

/**
 * ware category service
 * @author wangchen
 *
 */
@Service
@Transactional
public class WareCategoryService {
 
	@Autowired
	private WareCategoryMapper wareCategoryMapper = null;
	
	/**
	 * 获取正排序分类列表
	 * @return
	 */
	public List<WareCategory> findCategoryList() {
		WareCategoryCriteria wareCategoryCriteria = new WareCategoryCriteria();
		wareCategoryCriteria.createCriteria().andValidEqualTo(1);
		wareCategoryCriteria.setOrderByClause("orderby ASC");
		return this.getWareCategoryMapper().selectByExample(wareCategoryCriteria);
	}

	private WareCategoryMapper getWareCategoryMapper() {
		return wareCategoryMapper;
	}
	
}
