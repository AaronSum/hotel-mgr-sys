package com.mk.hms.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.HmsEBasePriceModel;
import com.mk.hms.model.HmsEPriceModel;
import com.mk.hms.model.HmsEPriceTimeModel;
import com.mk.hms.model.HmsTRoomtypeModel;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.HmsTRoomTypeService;
import com.mk.hms.service.PriceService;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.view.AddPrice;
import com.mk.hms.view.DatePrice;

/**
 * 价格控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/price")
public class PriceController {

	@Autowired
	private PriceService priceService = null;
	@Autowired
	private HmsTRoomTypeService hmdTRoomTypeService = null;
	
	/**
	 * 获取眯客基础价
	 * @return
	 */
    @RequestMapping("/basePrice")
    @ResponseBody
	public BigDecimal getBasePrice(long roomTypeId){
		return this.getPriceService().getBasePrice(roomTypeId);
	}
    
    /**
     * 加载房价时间列表
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/loadPrice")
    @ResponseBody
	public Map<Long, List<DatePrice>> loadPrice(long hotelId, String beginTime, String endTime) throws ParseException {
//		return this.getPriceService().loadPrice(hotelId, beginTime, endTime);
    	Map<Long,List<DatePrice>> map=new HashMap<Long, List<DatePrice>>();
		try{
        	List<HmsTRoomtypeModel> tRoomTypeList =  this.getHmdTRoomTypeService().findsByHotelId(hotelId);
        	List<HmsEBasePriceModel> basePrices = priceService.findTRoomTypeModels(tRoomTypeList);
        	Map<Long,HmsEBasePriceModel> basePricesMap = new HashMap<Long,HmsEBasePriceModel>();
        	for(HmsEBasePriceModel m:basePrices){
        		basePricesMap.put(m.getRoomTypeId(), m);
        	}
        	List<HmsEPriceModel> pricelist=priceService.findPriceByRoomTypeId(tRoomTypeList);
        	Map<Object,List<HmsEPriceModel>> pricesMap = new HashMap<Object,List<HmsEPriceModel>>();
        	List<HmsEPriceModel> priceModels = null;
        	for(HmsEPriceModel obj:pricelist){
        		Long roomTypeId =obj.getRoomTypeId();
        		if(!pricesMap.containsKey(roomTypeId)){
        			priceModels = new ArrayList<HmsEPriceModel>();
        			priceModels.add(obj);
        		}else{
        			priceModels = pricesMap.get(roomTypeId);
        			priceModels.add(obj);
        		}
        		pricesMap.put(roomTypeId, priceModels);
        	}
        	List<HmsEPriceTimeModel> ePriceTimeModels = null;
        	if(pricelist!=null && pricelist.size()>0){
        		ePriceTimeModels = priceService.findTpriceByIds(pricelist);
        	}else{
        		ePriceTimeModels = new ArrayList<HmsEPriceTimeModel>();
        	}
        	Map<Object,Object> ePriceTimeModelsMap = new HashMap<Object,Object>();
        	for(HmsEPriceTimeModel heptm:ePriceTimeModels){
        		ePriceTimeModelsMap.put(heptm.getId(),heptm);
        	}
        	for (HmsTRoomtypeModel tRoomType : tRoomTypeList) {
        		BigDecimal price = tRoomType.getCost();
        		HmsEBasePriceModel basePrice = basePricesMap.get(tRoomType.getId());
        		if(basePrice!=null){
					if(basePrice.getPrice()!=null && basePrice.getPrice().doubleValue()>0){
						price=basePrice.getPrice();
					}else if(basePrice.getSubprice()!=null  && basePrice.getSubprice().doubleValue()>0){
						price=price.subtract(basePrice.getSubprice());
					}else if(basePrice.getSubper()!=null  && basePrice.getSubper().doubleValue()>0){
						price=BigDecimal.ONE.subtract(basePrice.getSubper()).multiply(price);
					}
				}
        		List<DatePrice> datapriceList=new ArrayList<DatePrice>();
				Date temp= HmsDateUtils.getDateFromeDateString(beginTime.toString(), "yyyy-MM-dd");
				Date endDate = HmsDateUtils.getDateFromeDateString(endTime.toString(), "yyyy-MM-dd");
				
				while(!temp.after(endDate)){					 	
					BigDecimal $price=price;
					boolean find=false;
					List<HmsEPriceModel> epriceList = pricesMap.get(tRoomType.getId());
					if(epriceList!=null && epriceList.size()>0){
    					for (HmsEPriceModel ePrice : epriceList) {//pricelist
    						HmsEPriceTimeModel ePriceTimeModel = (HmsEPriceTimeModel)ePriceTimeModelsMap.get(ePrice.getTimeId());

//	    					for(HmsEPriceTimeModel ePriceTimeModel:ePriceTimeModels){
	    						if(HmsDateUtils.checkDate(temp, ePriceTimeModel.getBeginTime(),ePriceTimeModel.getEndTime() )==false){
	    							continue;
	    						}
	    						CronExpression cron=new CronExpression(ePriceTimeModel.getCron());
	    						if(cron.isSatisfiedBy(temp)){
	    							if(ePrice.getPrice()!=null && ePrice.getPrice().doubleValue()>0){
	    								$price=ePrice.getPrice();
	    							}else if(ePrice.getSubprice()!=null &&  ePrice.getSubprice().doubleValue()>0){
	    								$price=$price.subtract(ePrice.getSubprice());
	    							}else if(ePrice.getSubper()!=null &&  ePrice.getSubper().doubleValue()>0){
	    								$price=BigDecimal.ONE.subtract(ePrice.getSubper()).multiply($price);
	    							}
	    							DatePrice databean=new DatePrice(temp,$price.setScale(0, BigDecimal.ROUND_CEILING));
	    							datapriceList.add(databean);
	    							find=true;
	    							break;
	    						}
//	    					}
    					}
					}
					if(find==false){
						DatePrice databean=new DatePrice(temp,$price.setScale(0, BigDecimal.ROUND_CEILING));
						datapriceList.add(databean);
					}
					Calendar ca=Calendar.getInstance();
					ca.setTime(temp);
					ca.add(Calendar.DATE, 1);
					temp=ca.getTime();
					map.put(tRoomType.getId(), datapriceList);
				}
        	}
    	} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}
    
    /**
     * 增加房价时间规则
     * @return
     * @throws IOException 
     * @throws SessionTimeOutException 
     */
    @RequestMapping("/addPrice")
    @ResponseBody
    public OutModel addPrice(AddPrice price) throws IOException, SessionTimeOutException{
    	return this.getPriceService().addPrice(price);
    }
    
	private PriceService getPriceService() {
		return priceService;
	}

	private HmsTRoomTypeService getHmdTRoomTypeService() {
		return hmdTRoomTypeService;
	}

}
