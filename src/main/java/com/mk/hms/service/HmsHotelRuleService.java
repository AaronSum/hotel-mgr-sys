package com.mk.hms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.mapper.HotelRuleMapper;
import com.mk.hms.model.HMSHotelRuleModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HttpClientUtils;

/**
 * 更新规则
 * */
@Service
@Transactional
public class HmsHotelRuleService {

	private static final Logger logger = Logger.getLogger(HmsHotelRuleService.class);
	
	@Autowired
	private HotelRuleMapper hotelRuleMapper;

	public static final Integer INITSTATE = 0;//初始化
	public static final Integer STATE = 1;//规则同步成功
	public static final Integer PREBILLSTATE = -1;//开始进行提前结算
	public static final Integer BILLSTATE = 2;//提前结算成功
	/**
	 * 查询当天有效的初始化数据
	 * */
	public List<HMSHotelRuleModel> getInitTask() throws Exception{
		//获取当天时间
		String curDate = HmsDateUtils.getDate();
		//获取下一天时间
		String nextDate = HmsDateUtils.getNextDateString();
		//查询当天有效数据
		return hotelRuleMapper.getHMSHotelRuleList(INITSTATE,curDate,nextDate);
	}
	/**
	 * 查询当天有效的初始化数据
	 * */
	public List<HMSHotelRuleModel> getStateTask() throws Exception{
		//获取当天时间
		String curDate = HmsDateUtils.getDate();
		//获取下一天时间
		String nextDate = HmsDateUtils.getNextDateString();
		//查询当天有效数据
		return hotelRuleMapper.getHMSHotelRuleList(STATE,curDate,nextDate);
	}
	/**
	 * 更新数据
	 * */
	public void syncHotelRule(HMSHotelRuleModel rule){
		Long hotelid = rule.getHotelid();
		Integer rulecode = rule.getRulecode();
		String isthreshold = rule.getIsthreshold();
		Integer preState = rule.getState();
		//T表
		hotelRuleMapper.updateThotel(hotelid, rulecode, isthreshold);
		//E表
		hotelRuleMapper.updateEhotel(hotelid, rulecode, isthreshold);
		//更新状态
		hotelRuleMapper.updateState(hotelid, STATE, preState);
	}
	/**
	 * 插入数据
	 * */
	public int insertHotelRule(Long hotelId, Integer rulecode, String isthreshold, String effectdate){
		return hotelRuleMapper.insertHotelRule(hotelId, rulecode, isthreshold, effectdate);
	}
	/**
	 * 处理A订单数据，提前生成订单
	 * */
	public void executeBillTask(HMSHotelRuleModel rule){
		Long hotelid = rule.getHotelid();
		Integer preState = rule.getState();
		//锁定任务
		int count = hotelRuleMapper.updateState(hotelid, PREBILLSTATE, preState);
		if(count>0){
			//设置参数，酒店id，时间，isThreshold
			Date date = new Date();
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("hotelid", hotelid + "");
			paramsMap.put("theMonthDay",HmsDateUtils.formatDate(date));
			paramsMap.put("isThreshold", "N");
			//调用接口
			boolean result = invokeBillServcie(paramsMap);
			if(result){
				//执行成功 
				hotelRuleMapper.updateState(hotelid, BILLSTATE, PREBILLSTATE);
			}else{
				//执行失败
				hotelRuleMapper.updateState(hotelid, STATE, PREBILLSTATE);
			}
		}
	}
	/**
	 * 调用提前结算接口
	 * @param Map<String, String>
	 * @return boolean
	 * */
	public boolean invokeBillServcie(Map<String, String> paramsMap){
		try{
			String address = HmsFileUtils.getSysContentItem(ContentUtils.GEN_BILLCONFIRMCHECKS);
			JSONObject obj = HttpClientUtils.post(address, paramsMap);
			if (null == obj) {
				logger.error("调用提前结算接口异常");
				return false;
			}else if(!obj.getString("sucess").equals("true")) {
				logger.error("调用提前结算接口错误："
							+ obj.getString("errmsg") + "，错误代码："
							+ obj.getString("errcode"));
				return false;
			}
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
}
