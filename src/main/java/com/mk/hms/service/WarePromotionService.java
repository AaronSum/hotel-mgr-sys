package com.mk.hms.service;
 

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.mapper.WarePromotionMapper;
import com.mk.hms.mapper.WarePromotionMappingMapper;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.WarePromotion;
import com.mk.hms.model.WarePromotionCriteria;
import com.mk.hms.model.WarePromotionMapping;
import com.mk.hms.model.WarePromotionMappingCriteria;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.view.WarePromotionView;
 
/**
 *	商品促销Service
 */
@Service
@Transactional
public class WarePromotionService {
 
	@Autowired
	private WarePromotionMapper warePromotionMapper = null;
	
	@Autowired
	private WarePromotionMappingMapper warePromotionMappingMapper = null;
	
	/**
	 * 获取商品促销信息
	 * @param wareId 商品ID
	 * @return OutModel
	 */
	public OutModel findWarePromotion(Long wareId) {
		OutModel out = new OutModel();
		try {
			List<WarePromotion> list = this.findWarePromotionBywareId(wareId);
			if(list == null){
				out.setSuccess(false);
				out.setErrorMsg("没有促销信息");
			}else{
				out.setAttribute(list);
				out.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.setSuccess(false);
			out.setErrorMsg("获取促销信息失败");
		}
		return out;
	}
	
	/**
	 * 获取商品促销信息
	 * @param wareId 商品ID
	 * @return OutModel
	 */
	private List<WarePromotion> findWarePromotionBywareId(Long wareId) {
		WarePromotionCriteria wpc = new WarePromotionCriteria();
		List<WarePromotion> list = null;
		List<Long> promotionIdList = new ArrayList<Long>();
		try {
			WarePromotionMappingCriteria wpmc = new WarePromotionMappingCriteria();
			Date currentDate = HmsDateUtils.getDateFromeDateString(HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE),HmsDateUtils.FORMAT_DATE);
			wpmc.createCriteria().andWareidEqualTo(wareId)
								.andValidEqualTo(Integer.valueOf(1))
								.andStartdateLessThanOrEqualTo(currentDate)
								.andEnddateGreaterThanOrEqualTo(currentDate);
			//查询所有促销ID
			List<WarePromotionMapping> listMapping = this.getWarePromotionMappingMapper().selectByExample(wpmc);
			
			if(listMapping == null || listMapping.size() == 0){
				return list;
			}
			for (WarePromotionMapping wpm : listMapping) {
				promotionIdList.add(wpm.getPromotionid());
				//默认只有一条促销信息
			}
			//查询所有促销信息
			wpc.createCriteria().andIdIn(promotionIdList)
								.andValidEqualTo(Integer.valueOf(1))
								.andStartdateLessThanOrEqualTo(currentDate)
								.andEnddateGreaterThanOrEqualTo(currentDate);
			list = this.getWarePromotionMapper().selectByExample(wpc);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据商品ID获取促销价格
	 * @param List<WarePromotionView>
	 * @return 促销价格
	 */
	public OutModel getWarePromotionData(List<WarePromotionView> wpvlist){
		OutModel out = new OutModel();
		double promotionPrice = 0.0;
		for (WarePromotionView wpv : wpvlist) {
			List<WarePromotion> list = this.findWarePromotionBywareId(wpv.getWareId());
			if(list == null || list.size() == 0){
				//如果没有促销,直接返回元数据金额
				wpv.setPromotionData(new BigDecimal("0"));
				wpv.setActualData(wpv.getSourceData());
				continue;
			}
			WarePromotion warePromotion = list.get(0);//默认一个商品只有一个促销
			wpv.setPromotionType(warePromotion.getPromotiontype()); //促销类型
			wpv.setPromotionDesc(warePromotion.getPromotiondesc()); //促销标语

			if(wpv.getPromotionType().equals(1)){ //金额满减促销
				promotionPrice = getFullReductionPrice(promotionPrice, wpv, warePromotion);
			} else if(wpv.getPromotionType().equals(2)){ //满数量折扣促销
				promotionPrice = getFullDiscountPrice(wpv, warePromotion);
			}
			out.setSuccess(true);
			out.setAttribute(wpvlist);
		}
		return out;
	}

	/**
	 * 满减促销
	 * @param promotionPrice
	 * @param wpv
	 * @param warePromotion
	 * @return
	 */
	private double getFullReductionPrice(double promotionPrice, WarePromotionView wpv, WarePromotion warePromotion) {

		double tempData = wpv.getSourceData().doubleValue();
		//如果tempData >= 促销的基础数据
		while(tempData >= warePromotion.getBasedata().doubleValue()){
            promotionPrice =  new BigDecimal(promotionPrice + "").add( warePromotion.getPromotionadata()).doubleValue();
            if(warePromotion.getIscyclepromotion() == 1){
                //循环促销
                tempData = new BigDecimal(tempData + "").subtract(warePromotion.getBasedata()).doubleValue();
            }else{
                break;
            }
        }
		wpv.setPromotionData(new BigDecimal(promotionPrice + ""));
		wpv.setActualData(wpv.getSourceData().subtract(new BigDecimal(promotionPrice + "")));
		return promotionPrice;
	}

	/**
	 * 满数量折扣促销
	 * @param promotionPrice
	 * @param wpv
	 * @param warePromotion
	 * @return
	 */
	private double getFullDiscountPrice(WarePromotionView wpv, WarePromotion warePromotion) {
		BigDecimal promotionPrice = new BigDecimal(0);
		BigDecimal sourceData = wpv.getSourceData();
		if(sourceData.compareTo(warePromotion.getBasedata()) > -1){ //元数据大于等于设置满折金额
			promotionPrice =  sourceData.multiply(warePromotion.getPromotionadata());
			wpv.setPromotionData(wpv.getSourceData().subtract(promotionPrice));
			wpv.setActualData(promotionPrice);
		} else if(sourceData.compareTo(warePromotion.getBasedata()) < 0){ //元数据小于设置满折金额
			promotionPrice = new BigDecimal(0);
			wpv.setPromotionData(promotionPrice);
			wpv.setActualData(sourceData);
		}
		return promotionPrice.doubleValue();
	}


	public WarePromotionMapper getWarePromotionMapper() {
		return warePromotionMapper;
	}

	public WarePromotionMappingMapper getWarePromotionMappingMapper() {
		return warePromotionMappingMapper;
	}
}
