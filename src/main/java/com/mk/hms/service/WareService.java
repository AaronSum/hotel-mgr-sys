package com.mk.hms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.mapper.WareAttrMapper;
import com.mk.hms.mapper.WareShoppingMapper;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.WareAttr;
import com.mk.hms.model.WareAttrCriteria;
import com.mk.hms.model.WareCategory;
import com.mk.hms.model.WareShopping;
import com.mk.hms.model.WareShoppingCriteria;
import com.mk.hms.model.WareShoppingCriteria.Criteria;
import com.mk.hms.view.Page;

/**
 * ware service
 * @author wangchen
 *
 */
@Service
@Transactional
public class WareService {

	@Autowired
	private WareShoppingMapper wareShoppingMapper = null;
	
	@Autowired
	private WareAttrMapper wareAttrMapper = null;
	
	@Autowired
	private WareCategoryService wareCategoryService = null;
	

	/**
	 * 获取正排序商品列表
	 * @return
	 */
	public Map<String, Object> query(long categoryId, Page page) {
		List<WareCategory> validCategoryList = wareCategoryService.findCategoryList();
		
		Map<String, Object> res = new HashMap<String, Object>();
		WareShoppingCriteria wareShoppingCriteria = new WareShoppingCriteria();
		int total = page.getTotal();
		Criteria criteria = wareShoppingCriteria.createCriteria();
		
		//where
		if (categoryId > 0) {
			criteria.andCategoryidEqualTo(categoryId);
		} else {
			//filter invalid categories
			List<Long> categoryidInList = new ArrayList<Long>();
			for (WareCategory cat : validCategoryList) {
				long validCategoryId = cat.getId();
				categoryidInList.add(validCategoryId);
			}
			if (categoryidInList.size() == 0) {
				res.put("total", 0);
				res.put("rows", null);
				return res;
			}
			criteria.andCategoryidIn(categoryidInList);
		}
		criteria.andIssgEqualTo(1); //是否上柜
		
		//total
		if (total <= 0 ) {
			total = this.getWareShoppingMapper().countByExample(wareShoppingCriteria);
		}
		//page
		wareShoppingCriteria.setLimitStart(page.getStartIndex());
		wareShoppingCriteria.setLimitEnd(page.getPageSize());
		//order
		wareShoppingCriteria.setOrderByClause("orderby,id ASC");
		res.put("total", total);
		res.put("rows", this.getWareShoppingMapper().selectByExample(wareShoppingCriteria));
		return res;
	}
	
	/**
	 * 获取商品属性 
	 * @param wareId 商品ID
	 * @return OutModel
	 */
	public OutModel findWareAttr(Long wareId) {
		OutModel out = new OutModel();
		try {
			WareAttrCriteria wc = new WareAttrCriteria();
			wc.createCriteria().andWareidEqualTo(wareId).andValidEqualTo(Integer.valueOf(1));
			wc.setOrderByClause("orderby ASC");
			List<WareAttr> list = this.getWareAttrMapper().selectByExample(wc);
			out.setSuccess(true);
			out.setAttribute(list);
		} catch (Exception e) {
			e.printStackTrace();
			out.setSuccess(false);
			out.setErrorMsg("获取商品属性失败");
		}
		return out;
	}
	
	/**
	 * 获取正排序商品列表
	 * @return
	 */
	public WareShopping find(long wareId) {
		if (wareId > 0) {
			return this.getWareShoppingMapper().selectByPrimaryKey(wareId);
		}
		return null;
	}


	
	private WareShoppingMapper getWareShoppingMapper() {
		return wareShoppingMapper;
	}

	public WareAttrMapper getWareAttrMapper() {
		return wareAttrMapper;
	}

}
