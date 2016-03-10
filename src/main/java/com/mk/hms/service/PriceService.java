package com.mk.hms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.enums.HmsTHotelOperateLogCheckTypeEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.enums.PricePolicyEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.EBasePriceMapper;
import com.mk.hms.mapper.HotelAuditSynToTHotelMapper;
import com.mk.hms.mapper.HotelOperateLogMapper;
import com.mk.hms.mapper.RoomTypeMapper;
import com.mk.hms.mapper.TPriceMapper;
import com.mk.hms.mapper.TPriceTimeMapper;
import com.mk.hms.model.EBasePrice;
import com.mk.hms.model.EBasePriceCriteria;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.HmsEBasePriceModel;
import com.mk.hms.model.HmsEPriceModel;
import com.mk.hms.model.HmsEPriceTimeModel;
import com.mk.hms.model.HmsTRoomtypeModel;
import com.mk.hms.model.HotelOperateLog;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.RoomType;
import com.mk.hms.model.RoomTypeCriteria;
import com.mk.hms.model.TPrice;
import com.mk.hms.model.TPriceCriteria;
import com.mk.hms.model.TPriceTimeCriteria;
import com.mk.hms.model.TPriceTimeWithBLOBs;
import com.mk.hms.model.User;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HttpClientUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.AddPrice;
import com.mk.hms.view.DatePrice;
import com.mk.hms.view.PricePolicy;

/**
 * 价格 service
 * 
 * @author hdy
 *
 */
@Service
@Transactional
public class PriceService {

	private static final Logger logger = LoggerFactory
			.getLogger(PriceService.class);

	@Autowired
	private RoomTypeMapper roomTypeMapper = null;

	@Autowired
	private EBasePriceMapper eBasePriceMapper = null;

	@Autowired
	private TPriceMapper tPriceMapper = null;

	@Autowired
	private TPriceTimeMapper tPriceTimeMapper = null;

	@Autowired
	private HotelOperateLogMapper hotelOperateLogMapper = null;

	@Autowired
	private HotelAuditSynToTHotelMapper mapper;

	public List<HmsEBasePriceModel> findTRoomTypeModels(
			List<HmsTRoomtypeModel> hmsTRoomtypeModels) {
		return mapper.findTRoomTypeModels(hmsTRoomtypeModels);
	}

	public List<HmsEPriceModel> findPriceByRoomTypeId(
			List<HmsTRoomtypeModel> tRoomTypeList) {
		return mapper.findPriceByRoomTypeId(tRoomTypeList);
	}
	public List<HmsEPriceTimeModel> findTpriceByIds(List<HmsEPriceModel> pricelist){
		return mapper.findTpriceByIds(pricelist);
	}
	/**
	 * 加载房价时间列表
	 * 
	 * @param hotelId
	 *            酒店id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 房价时间列表
	 * @throws ParseException
	 */
	public Map<Long, List<DatePrice>> loadPrice(long hotelId, String beginTime,
			String endTime) throws ParseException {
		Map<Long, List<DatePrice>> map = new HashMap<Long, List<DatePrice>>();

		List<RoomType> tRoomTypeList = this.getRoomTypeByHotelId(hotelId);

		List<EBasePrice> basePrices = this
				.getEBasePriceByRoomeTypeIds(tRoomTypeList);

		List<TPrice> pricelist = this.getTPriceByRoomTypeIds(tRoomTypeList);

		List<TPriceTimeWithBLOBs> ePriceTimeModels = this
				.getTPriceTimeByPriceIds(pricelist);

		for (RoomType tRoomType : tRoomTypeList) {
			BigDecimal price = tRoomType.getCost();
			for (EBasePrice basePrice : basePrices) {
				if (basePrice != null) {
					if (basePrice.getPrice() != null
							&& basePrice.getPrice().doubleValue() > 0) {
						price = basePrice.getPrice();
					} else if (basePrice.getSubprice() != null
							&& basePrice.getSubprice().doubleValue() > 0) {
						price = price.subtract(basePrice.getSubprice());
					} else if (basePrice.getSubper() != null
							&& basePrice.getSubper().doubleValue() > 0) {
						price = BigDecimal.ONE.subtract(basePrice.getSubper())
								.multiply(price);
					}
				}
			}
			List<DatePrice> datapriceList = new ArrayList<DatePrice>();
			Date temp = HmsDateUtils.getDateFromeDateString(beginTime,
					HmsDateUtils.FORMAT_DATE);
			Date endDate = HmsDateUtils.getDateFromeDateString(endTime,
					HmsDateUtils.FORMAT_DATE);

			while (!temp.after(endDate)) {
				BigDecimal $price = price;
				boolean find = false;
				for (TPrice ePrice : pricelist) {
					for (TPriceTimeWithBLOBs ePriceTimeModel : ePriceTimeModels) {
						if (HmsDateUtils.checkDate(temp,
								ePriceTimeModel.getBeginTime(),
								ePriceTimeModel.getEndTime()) == false) {
							continue;
						}
						CronExpression cron = new CronExpression(
								ePriceTimeModel.getCron());
						if (cron.isSatisfiedBy(temp)) {
							if (ePrice.getPrice() != null
									&& ePrice.getPrice().doubleValue() > 0) {
								$price = ePrice.getPrice();
							} else if (ePrice.getSubprice() != null
									&& ePrice.getSubprice().doubleValue() > 0) {
								$price = $price.subtract(ePrice.getSubprice());
							} else if (ePrice.getSubper() != null
									&& ePrice.getSubper().doubleValue() > 0) {
								$price = BigDecimal.ONE.subtract(
										ePrice.getSubper()).multiply($price);
							}
							DatePrice databean = new DatePrice(
									temp,
									$price.setScale(0, BigDecimal.ROUND_CEILING));
							datapriceList.add(databean);
							find = true;
							break;
						}
					}
				}
				if (find == false) {
					DatePrice databean = new DatePrice(temp, $price.setScale(0,
							BigDecimal.ROUND_CEILING));
					datapriceList.add(databean);
				}
				Calendar ca = Calendar.getInstance();
				ca.setTime(temp);
				ca.add(Calendar.DATE, 1);
				temp = ca.getTime();
				map.put(tRoomType.getId(), datapriceList);
			}
		}
		return map;

	}

	/**
	 * 获取基准价格
	 * 
	 * @param roomTypeId
	 *            房间类型id
	 * @return 基准价格
	 */
	public BigDecimal getBasePrice(long roomTypeId) {
		BigDecimal rtnVal = new BigDecimal(0);
		RoomType roomtypeModel = this.getRoomTypebyId(roomTypeId);
		BigDecimal $price = roomtypeModel.getCost();
		EBasePrice basePriceModel = this.getEBasePricebyTypeId(roomTypeId);
		if (basePriceModel != null) {
			BigDecimal price = basePriceModel.getPrice();
			BigDecimal subprice = basePriceModel.getSubprice();
			BigDecimal subper = basePriceModel.getSubper();
			if (price != null && price.doubleValue() > 0) {
				rtnVal = price;
			} else if (subprice != null && subprice.doubleValue() > 0) {
				rtnVal = $price.subtract(subprice);
			} else if (subper != null && subper.doubleValue() > 0) {
				rtnVal = BigDecimal.ONE.subtract(subper).multiply($price);
			}
		}
		return rtnVal;
	}

	/**
	 * 增加房价时间规则
	 * 
	 * @param price
	 * @return 返回状态
	 * @throws IOException
	 * @throws SessionTimeOutException 
	 */
	public OutModel addPrice(AddPrice price) throws IOException, SessionTimeOutException {
		OutModel out = new OutModel(false);
		EHotel thisHotel = SessionUtils.getThisHotel();
		if (thisHotel != null) {
			long hotelId = thisHotel.getId(); // 酒店Id
			Date beginTime = HmsDateUtils.getDateFromString(price
					.getBeginTime());
			Date endTime = HmsDateUtils.getDateFromString(price.getEndTime());
			Map<String, Boolean> weekState = this.getWeekState(price);

			PricePolicyEnum changeType = null;
			String valueStr = price.getValue();
			if (!NumberUtils.isNumber(valueStr)) {
				out.setErrorMsg("value不是数字");
				return out;
			}
			String type = price.getType();
			changeType = type.equals("price") ? PricePolicyEnum.price : type
					.equals("subprice") ? PricePolicyEnum.subprice : type
					.equals("subper") ? PricePolicyEnum.subper : null;
//			if(!checkPrice(price)){
//				out.setSuccess(false);
//				out.setErrorMsg("已经设置过");
//			}
			
			this.updatePriceByRoomType(hotelId, price.getRoomTypeId(),
					beginTime, endTime, weekState, changeType,
					NumberUtils.createBigDecimal(valueStr));
			// 调用房价设置接口
			PriceService.logger.info("刷新酒店眯客价格缓存开始");
			String address = HmsFileUtils
					.getSysContentItem(ContentUtils.UPDATE_HOTEL_IMIKEPRICES_CACHE_ADDRESS);
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("hotelid", hotelId + "");
			paramsMap.put("isforce", HmsVisibleEnum.T.getValue());
			JSONObject obj = HttpClientUtils.post(address, paramsMap);
			if (null == obj) {
				PriceService.logger.error("调用刷新酒店眯客价格缓存接口异常");
				out.setErrorMsg("调用刷新酒店眯客价格缓存接口异常");
				return out;
			}
			if (!obj.getBoolean("success")) {
				PriceService.logger.error("调用刷新酒店眯客价格缓存接口错误："
						+ obj.getString("errmsg") + "，错误代码："
						+ obj.getString("errcode"));
				out.setErrorMsg("调用刷新酒店眯客价格缓存接口错误：" + obj.getString("errmsg")
						+ "，错误代码：" + obj.getString("errcode"));
				return out;
			}
			// add log
			// 添加修改房价日志
			this.addTHotelOperateLogForRates();
			out.setSuccess(true);
		} else {
			// session超时
			out.setErrorMsg("session超时");
		}
		return out;
	}
//	/**
//	 * 计算策略价格调整后的金额是否大于0
//	 * @param checkPrice
//	 * @return boolean
//	 * */
//	public boolean checkPrice(AddPrice price){
//		/*如果金额下调需要比较是否小于等于0*/
//		//下调金额校验 subprice
//		if(price.getType().equals("subprice")){
//			//计算过的眯客价
//			BigDecimal imikePrice = getBasePrice(price.getRoomTypeId());
//			//策略价
//			BigDecimal subPrice = new BigDecimal(price.getValue());
//			if(imikePrice.compareTo(BigDecimal.ZERO)>0){
//				BigDecimal result = imikePrice.subtract(subPrice);
//				logger.info("策略价与眯客价计算结果为："+result);
//				if(result.compareTo(BigDecimal.ZERO)>0){
//					return true;
//				}else{
//					return false;
//				}
//			}
//		}
//		//默认返回成功
//		return true;
//	}
	/**
	 * 添加日志
	 * 
	 * @param thisHotel
	 *            当前酒店
	 * @throws SessionTimeOutException 
	 */
	private void addTHotelOperateLogForRates() throws SessionTimeOutException {
		HotelOperateLog log = new HotelOperateLog();
		EHotel thisHotel = SessionUtils.getThisHotel();
		User user = SessionUtils.getSessionLoginUser().getUser();
		log.setChecktype(HmsTHotelOperateLogCheckTypeEnum.Rates.getValue());
		log.setChecktypename(HmsTHotelOperateLogCheckTypeEnum.Rates.getText());
		log.setHotelid(thisHotel.getId());
		log.setChecktime(new Date());
		log.setHotelname(thisHotel.getHotelName());
		log.setUsercode(user.getId());
		log.setUsername(user.getName());
		this.getHotelOperateLogMapper().insert(log);
	}

	/**
	 * 根据房型修改价格
	 * 
	 * @param hotelId
	 *            酒店id
	 * @param roomTypeId
	 *            房型id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param weekState
	 *            周状态
	 * @param changeType
	 *            改变类型
	 * @param value
	 *            值
	 */
	private void updatePriceByRoomType(Long hotelId, Long roomTypeId,
			Date beginTime, Date endTime, Map<String, Boolean> weekState,
			PricePolicyEnum changeType, BigDecimal value) {
		Map<String, Map<PricePolicy, List<Integer>>> map = this
				.getAllMonthDayPricePolicy(roomTypeId, beginTime, endTime,
						weekState, changeType, value);
		for (Map.Entry<String, Map<PricePolicy, List<Integer>>> entry : map
				.entrySet()) {
			String ym = entry.getKey();
			Integer year = Integer.parseInt(ym.substring(0, 4));
			Integer month = Integer.parseInt(ym.substring(4));
			Calendar beginCa = Calendar.getInstance();
			Calendar endCa = Calendar.getInstance();
			beginCa.set(year, month - 1, 1);
			endCa.set(year, month - 1,
					beginCa.getMaximum(Calendar.DAY_OF_MONTH));

			for (Map.Entry<PricePolicy, List<Integer>> ppEn : entry.getValue()
					.entrySet()) {
				if (ppEn != null && ppEn.getValue() != null
						&& ppEn.getValue().size() > 0) {
					TPriceTimeWithBLOBs priceTime = new TPriceTimeWithBLOBs();
					StringBuilder sb = new StringBuilder();
					sb.append("* * * ");
					sb.append(this.getListStr(ppEn.getValue()));
					sb.append(" " + month);
					sb.append(" ?");
					sb.append(" " + year);
					priceTime.setCron(sb.toString());
					priceTime.setBeginTime(beginCa.getTime());
					priceTime.setEndTime(endCa.getTime());
					priceTime.setHotelId(hotelId);
					priceTime.setUpdateTime(new Date());
					priceTime.setName("price policy");
					this.gettPriceTimeMapper().insert(priceTime);

					TPrice price = new TPrice();
					price.setOrderIndex(1);
					price.setTimeId(priceTime.getId());
					price.setRoomTypeId(roomTypeId);
					price.setUpdateTime(new Date());
					PricePolicy pp = ppEn.getKey();
					if (pp.getTypeEnum().equals(PricePolicyEnum.price)) {
						price.setPrice(pp.getValue());
					} else if (pp.getTypeEnum()
							.equals(PricePolicyEnum.subprice)) {
						price.setSubprice(pp.getValue());
					} else if (pp.getTypeEnum().equals(PricePolicyEnum.subper)) {
						price.setSubper(pp.getValue());
					}
					this.gettPriceMapper().insert(price);
				}
			}
		}
	}

	private Map<String, Map<PricePolicy, List<Integer>>> getAllMonthDayPricePolicy(
			Long roomTypeId, Date beginTime, Date endTime,
			Map<String, Boolean> weekState, PricePolicyEnum changeType,
			BigDecimal value) {
		SimpleDateFormat ymSf = new SimpleDateFormat(
				HmsDateUtils.FORMAT_YEARMONTH_NOPARTING);
		PricePolicy newPP = new PricePolicy();
		if (PricePolicyEnum.price.equals(changeType)) {
			newPP.setTypeEnum(PricePolicyEnum.price);
			newPP.setValue(value);
		} else if (PricePolicyEnum.subprice.equals(changeType)) {
			newPP.setTypeEnum(PricePolicyEnum.subprice);
			newPP.setValue(value);
		} else if (PricePolicyEnum.subper.equals(changeType)) {
			newPP.setTypeEnum(PricePolicyEnum.subper);
			newPP.setValue(value);
		}
		Map<String, Map<PricePolicy, List<Integer>>> map = new HashMap<String, Map<PricePolicy, List<Integer>>>();
		// 加入页面新增策略
		Map<String, List<Integer>> monthDays = HmsDateUtils.getMonthDays(
				beginTime, endTime, weekState);
		for (Map.Entry<String, List<Integer>> md : monthDays.entrySet()) {
			Map<PricePolicy, List<Integer>> tempmap = new HashMap<PricePolicy, List<Integer>>();
			tempmap.put(newPP, md.getValue());
			map.put(md.getKey(), tempmap);
		}
		// 找出数据中策略并且和页面策略合并
		List<TPrice> priceList = this.getPriceByRoomTypeId(roomTypeId);
		List<TPriceTimeWithBLOBs> priceTimes = this
				.getTPriceTimeByPriceIds(priceList);
		for (TPrice tPrice : priceList) {
			for (TPriceTimeWithBLOBs tpt : priceTimes) {
				if (tPrice.getTimeId().equals(tpt.getId())) {
					String key = ymSf.format(tpt.getBeginTime());
					if (!map.containsKey(key)) {
						break;
					}
					PricePolicy pp = new PricePolicy();
					if (tPrice.getPrice() != null) {
						pp.setTypeEnum(PricePolicyEnum.price);
						pp.setValue(tPrice.getPrice());
					} else if (tPrice.getSubprice() != null) {
						pp.setTypeEnum(PricePolicyEnum.subprice);
						pp.setValue(tPrice.getSubprice());
					} else if (tPrice.getSubper() != null) {
						pp.setTypeEnum(PricePolicyEnum.subper);
						pp.setValue(tPrice.getSubper());
					}

					Map<PricePolicy, List<Integer>> ppDays = map.get(key);// 每个月
																			// 策略的days
					if (ppDays == null) {
						ppDays = new HashMap<PricePolicy, List<Integer>>();
					}

					List<Integer> dbDaysList = ppDays.get(pp);
					if (dbDaysList == null) {
						dbDaysList = new ArrayList<Integer>();
					}

					String cron = tpt.getCron();
					String[] dbDays = cron.substring(6, cron.indexOf(" ", 6))
							.split(ContentUtils.CHAR_COMMA);
					for (int i = 0; i < dbDays.length; i++) {
						dbDaysList.add(Integer.parseInt(dbDays[i]));
					}
					if (monthDays.get(key) != null) {// 同一月份
						if (pp.equals(newPP)) {// 同一策略
							dbDaysList.addAll(monthDays.get(key));
						} else if (pp.getTypeEnum().equals(newPP.getTypeEnum())) {
							dbDaysList.removeAll(monthDays.get(key));
						} else {
							dbDaysList.removeAll(monthDays.get(key));
						}

						if (!monthDays.get(key).equals(dbDaysList)) {
							ppDays.put(pp, dbDaysList);
						}
					}
					map.put(key, ppDays);
					this.deleteTPriceByTimeId(tpt.getId());
					this.gettPriceTimeMapper().deleteByPrimaryKey(tpt.getId());
				}
			}
		}
		return map;
	}

	/**
	 * 根据时间id 删除价格表
	 * 
	 * @param timeId
	 *            时间id
	 */
	private void deleteTPriceByTimeId(long timeId) {
		TPriceCriteria priceCriteria = new TPriceCriteria();
		priceCriteria.createCriteria().andTimeIdEqualTo(timeId);
		this.gettPriceMapper().deleteByExample(priceCriteria);
	}

	/**
	 * 根据房型id 获取合格列表
	 * 
	 * @param roomTypeId
	 *            房型id
	 * @return 价格列表
	 */
	private List<TPrice> getPriceByRoomTypeId(long roomTypeId) {
		TPriceCriteria priceCriteria = new TPriceCriteria();
		priceCriteria.createCriteria().andRoomTypeIdEqualTo(roomTypeId);
		return this.gettPriceMapper().selectByExample(priceCriteria);
	}

	private String getListStr(List<Integer> list) {
		StringBuilder sb = new StringBuilder();
		for (Integer value : list) {
			if (sb.length() == 0) {
				sb.append(value);
			} else {
				sb.append("," + value);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取周几
	 * 
	 * @param price
	 *            价格
	 * @return 周状态
	 */
	private Map<String, Boolean> getWeekState(AddPrice price) {
		// 1-7
		Map<String, Boolean> weekState = new TreeMap<String, Boolean>();
		for (int i = 1; i < 8; i++) {
			weekState.put(i + "", false);
		}
		if ("true".equals(price.getMon()))
			weekState.put(2 + "", true);
		if ("true".equals(price.getTues()))
			weekState.put(3 + "", true);
		if ("true".equals(price.getWed()))
			weekState.put(4 + "", true);
		if ("true".equals(price.getThur()))
			weekState.put(5 + "", true);
		if ("true".equals(price.getFri()))
			weekState.put(6 + "", true);
		if ("true".equals(price.getSat()))
			weekState.put(7 + "", true);
		if ("true".equals(price.getSun()))
			weekState.put(1 + "", true);
		return weekState;
	}

	/**
	 * 根据房间类型，获取价格列表
	 * 
	 * @param list
	 *            房型列表
	 * @return 房价列表
	 */
	private List<TPrice> getTPriceByRoomTypeIds(List<RoomType> list) {
		if (list.size() <= 0) {
			return null;
		}
		List<Long> roomTypeIds = new ArrayList<Long>();
		for (RoomType rt : list) {
			roomTypeIds.add(rt.getId());
		}
		TPriceCriteria priceCriteria = new TPriceCriteria();
		priceCriteria.createCriteria().andRoomTypeIdIn(roomTypeIds);
		return this.gettPriceMapper().selectByExample(priceCriteria);
	}

	/**
	 * 获取价格时间列表
	 * 
	 * @param list
	 *            价格列表
	 * @return 价格时间列表
	 */
	private List<TPriceTimeWithBLOBs> getTPriceTimeByPriceIds(List<TPrice> list) {
		if (list.size() <= 0) {
			return null;
		}
		List<Long> priceIds = new ArrayList<Long>();
		for (TPrice p : list) {
			priceIds.add(p.getTimeId());
		}
		TPriceTimeCriteria priceTimeCriteria = new TPriceTimeCriteria();
		priceTimeCriteria.createCriteria().andIdIn(priceIds);
		return this.gettPriceTimeMapper().selectByExampleWithBLOBs(
				priceTimeCriteria);
	}

	/**
	 * 获取基准价列表
	 * 
	 * @param list
	 *            房型列表
	 * @return 基准介个列表
	 */
	private List<EBasePrice> getEBasePriceByRoomeTypeIds(List<RoomType> list) {
		if (list.size() <= 0) {
			return null;
		}
		List<Long> roomTypeIds = new ArrayList<Long>();
		for (RoomType rt : list) {
			roomTypeIds.add(rt.getId());
		}
		EBasePriceCriteria basePriceCriteria = new EBasePriceCriteria();
		basePriceCriteria.createCriteria().andRoomTypeIdIn(roomTypeIds);
		return this.geteBasePriceMapper().selectByExample(basePriceCriteria);
	}

	/**
	 * 获取酒店房型列表
	 * 
	 * @param hotelId
	 *            酒店id
	 * @return 房型列表
	 */
	private List<RoomType> getRoomTypeByHotelId(long hotelId) {
		RoomTypeCriteria roomTypeCriteria = new RoomTypeCriteria();
		roomTypeCriteria.createCriteria().andEhotelIdEqualTo(hotelId);
		return this.getRoomTypeMapper().selectByExample(roomTypeCriteria);
	}

	/**
	 * 通过主键获取房间类型
	 * 
	 * @param id
	 *            主键
	 * @return 房间类型实体
	 */
	private RoomType getRoomTypebyId(long id) {
		return this.getRoomTypeMapper().selectByPrimaryKey(id);
	}

	/**
	 * 根据主键获取基准价
	 * 
	 * @param TypeId
	 *            
	 * @return 基准价实体
	 */
	private EBasePrice getEBasePricebyTypeId(long roomTypeId) {
		EBasePriceCriteria ebaseCriteria = new EBasePriceCriteria();
		ebaseCriteria.createCriteria().andRoomTypeIdEqualTo(roomTypeId);
		List<EBasePrice> list =  this.geteBasePriceMapper().selectByExample(ebaseCriteria);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	private RoomTypeMapper getRoomTypeMapper() {
		return roomTypeMapper;
	}

	private EBasePriceMapper geteBasePriceMapper() {
		return eBasePriceMapper;
	}

	private TPriceMapper gettPriceMapper() {
		return tPriceMapper;
	}

	private TPriceTimeMapper gettPriceTimeMapper() {
		return tPriceTimeMapper;
	}

	private HotelOperateLogMapper getHotelOperateLogMapper() {
		return hotelOperateLogMapper;
	}

}
