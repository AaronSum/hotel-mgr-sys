package com.mk.hms.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mk.hms.db.HmsJdbcTemplate;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.EHotelMapper;
import com.mk.hms.mapper.HomeMapper;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HomeUtilMode;
import com.mk.hms.model.OutModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;

/**
 * 首页 service
 * @author hdy
 *
 */
@Service
@Transactional
public class HomeService {

	@Autowired
	private EHotelMapper eHotelMapper = null;

	@Autowired
	private HomeMapper homeMapper;
	/**
	 * 根据id 获取酒店对象
	 * @param hotelId 酒店id
	 * @return 酒店对象
	 */
	public EHotelWithBLOBs getEHotelById(long hotelId) {
		return this.geteHotelMapper().selectByPrimaryKey(hotelId);
	}
	
	/**
	 * get echart data
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public OutModel loadHomeDatas() throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		if (null == loginUser) {
			out.setErrorMsg("用户未登录");
			return out;
		}
		long userId = loginUser.getUser().getId();
		EHotel thisHotel = loginUser.getThisHotel();
		long thisHotelId = thisHotel.getId();

		// 【已缓存】酒店房间数
		int hotelRoomNum = thisHotel.getRoomNum() == null ? 0 : thisHotel.getRoomNum();
		
		// 【已缓存】酒店前台切客收益，根据酒店id、当前登录人id查询数据
//		List checkerBill = hmsHomeService.getCheckerBill2Week(thisHotelId, userId);
		this.getCurrCheckInfo(thisHotelId, userId, map);
		
		// 【已缓存】查今日预定、入住等信息
		Map<String, Object> maxPrice = this.getHotelMaxPrice(thisHotelId);
		
		// 【实时查】pmsOrderNum pmsCheckinNum otaCheckinNum otaRevenues
		String date = HmsDateUtils.getNowFormatDateString(HmsDateUtils.FORMAT_DATE);
		Date begintime = HmsDateUtils.addDays(HmsDateUtils.getDateFromString(date + " 00:00:00"), 0);
		Date endtime = HmsDateUtils.addDays(HmsDateUtils.getDateFromString(HmsDateUtils.getDateAdded(1, date) + " 00:00:00"), 0);
		
		int pmsOrderNum = this.getCurrPmsOrderNum(thisHotelId, begintime, endtime);
		int pmsCheckinNum = this.getCurrPmsCheckinNum(thisHotelId, begintime, endtime);
		Map<String, Object> oatMap = this.getCurrOtaCheckinNumAndRevenues(thisHotelId, begintime, endtime);
		int otsRentOrderNum = this.getOtsRentOrderNum(thisHotelId, begintime, endtime);
		int otsOrderNum = this.getOtsOrderNum(thisHotelId, begintime, endtime);
		oatMap.put("pmsOrderNum", pmsOrderNum);
		oatMap.put("pmsCheckinNum", pmsCheckinNum);
		oatMap.put("otsRentOrderNum", otsRentOrderNum);
		oatMap.put("pmsRentOrderNum", pmsCheckinNum - otsRentOrderNum);
		oatMap.put("otsOrderNum", otsOrderNum);
		map.put("curData", oatMap);
		
		//【已缓存】查询酒店的订单、入住数据，根据时间查询，date为当天则查当前的实时数据，date为历史则配合top进行查询
		String yesterday = HmsDateUtils.getCertainDate(date, -1);
		List<Map<String, Object>> hisDatas = this.getHotelOrdersDailys(thisHotelId, yesterday, 60);
		
		map.put("roomNum", hotelRoomNum);
		map.put("dateInfo", date + " " + HmsDateUtils.getChineseDayOfWeek());
//		map.put("checkerBill", checkerBill);
		map.put("maxPrice", maxPrice);
		map.put("hisDatas", hisDatas);
		out.setSuccess(true);
		out.setAttribute(map);
		return out;
	}
	
	/**
	 * get echart data
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public OutModel getSomeDateCheckInfo(String beginDateStr, String endDateStr) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		Map<String, Object> map = new HashMap<String, Object>();
		//
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		long userId = loginUser.getUser().getId();
		EHotel thisHotel = loginUser.getThisHotel();
		long thisHotelId = thisHotel.getId();
		Date beginDate = HmsDateUtils.getDateFromString(beginDateStr);
		Date endDate = HmsDateUtils.getDateFromString(endDateStr);
		this.getOrderCounts(thisHotelId, userId, map, beginDate, endDate);
		out.setSuccess(true);
		out.setAttribute(map);
		return out;
	}
	
	public OutModel getAllCheckInfo(String beginDateStr, String endDateStr) throws SessionTimeOutException {
		OutModel out = new OutModel(false);
		Map<String, Object> map = new HashMap<String, Object>();
		//
		LoginUser loginUser = SessionUtils.getSessionLoginUser();
		long userId = loginUser.getUser().getId();
		EHotel thisHotel = loginUser.getThisHotel();
		long thisHotelId = thisHotel.getId();
		Date beginDate = HmsDateUtils.getDateFromString(beginDateStr);
		Date endDate = HmsDateUtils.getDateFromString(endDateStr);
		this.getAllValidOrderCounts(thisHotelId, userId, map, beginDate, endDate);
		out.setSuccess(true);
		out.setAttribute(map);
		return out;
	}
	
	private void getCurrCheckInfo(long thisHotelId, long userId, Map<String, Object> map){
		// 标示本星期的周一
		Date endDate = HmsDateUtils.getDateFromString(HmsDateUtils.getDatetime());
		Date beginDate = HmsDateUtils.getDateFromString(HmsDateUtils.getFirstDateOfWeek());
		this.getOrderCounts(thisHotelId, userId, map, beginDate, endDate);
	}
	
	/**
	 * 查询某个时间段某个酒店的工作人员的有效切客订单数
	 * 
	 */
	private void getOrderCounts(long thisHotelId, long userId, Map<String, Object> map, Date beginDate, Date endDate){
		JdbcTemplate jdbc = HmsJdbcTemplate.getJdbcTemplate();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(t.Hotelid) size FROM b_otaroomorder t, b_pmsroomorder ro, b_otaorder o, b_promotion_price bpp ")
		  .append("WHERE t.Hotelid = ro.Hotelid AND t.PMSRoomOrderNo = ro.PmsRoomOrderNo AND t.OtaOrderId = o.id ")
		  .append("AND bpp.otaorderid = o.id AND bpp.promotion = 4 AND o.hotelid = ? ")
		  .append("AND ro.Checkintime >= ? AND ro.Checkintime < ? AND ro.status IN ('IN' , 'OK', 'PM') AND o.OrderStatus IN (180, 190, 200, 520) ")
		  .append("AND o.Invalidreason is null ");
		//只看自己
		sb.append(" AND o.spreadUser = ? ");
		Map<String, Object> res  = jdbc.queryForMap(sb.toString(), new Object[] { thisHotelId, beginDate, endDate, userId });
//		Map<String, Object> res = null;
//		//判断ession 登录用户信息
//		if (SessionUtils.isHotelManager()) {
//			//老板查全部
//			sb.append(" AND o.spreadUser is not null ");
//			res = jdbc.queryForMap(sb.toString(), new Object[] { thisHotelId, beginDate, endDate });
//		}else{
//			//前台看自己
//			sb.append(" AND o.spreadUser = ? ");
//			res = jdbc.queryForMap(sb.toString(), new Object[] { thisHotelId, beginDate, endDate, userId });
//		}
		map.put("orderNum", res == null ? 0 : NumberUtils.toInt(res.get("size").toString()));
	}
	/**
	 * 查询时间段内全部订单数
	 * 
	 */	
	private void getOrderCounts(long thisHotelId, Map<String, Object> map, Date beginDate, Date endDate){
		JdbcTemplate jdbc = HmsJdbcTemplate.getJdbcTemplate();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(t.Hotelid) size FROM b_otaroomorder t, b_pmsroomorder ro, b_otaorder o ")
		  .append("WHERE t.Hotelid = ro.Hotelid AND t.PMSRoomOrderNo = ro.PmsRoomOrderNo AND t.OtaOrderId = o.id ")
		  .append(" AND o.hotelid = ? ")
		  .append("AND ro.Checkintime >= ? AND ro.Checkintime < ? AND ro.status IN ('IN' , 'OK', 'PM') AND o.OrderStatus IN (180, 190, 200) ");
		Map<String, Object> res = jdbc.queryForMap(sb.toString(), new Object[] { thisHotelId, beginDate, endDate });
		map.put("orderNum", NumberUtils.toInt(res.get("size").toString()));
	}
	
	/**
	 * 预付补贴订单数统计、到付补贴订单数统计、预付补贴金额统计、到付补贴金额统计、可结账余额统计
	 * 
	 */
	private void getAllValidOrderCounts(long thisHotelId, long userId, Map<String, Object> map, Date beginDate, Date endDate){
		JdbcTemplate jdbc = HmsJdbcTemplate.getJdbcTemplate();
		// 预付订单号查询，查询一周
		StringBuffer sbyf = new StringBuffer();
		sbyf.append("SELECT o.id FROM b_otaroomorder t, b_pmsroomorder ro, b_otaorder o ")
		  	.append("WHERE t.Hotelid = ro.Hotelid AND t.PMSRoomOrderNo = ro.PmsRoomOrderNo AND t.OtaOrderId = o.id ")
		  	.append(" AND o.Ordertype = 1 AND o.hotelid = ")
		  	.append(thisHotelId).append(" AND o.spreadUser is not null ")
		  	.append("AND ro.Checkintime >= ? AND ro.Checkintime < ? AND ro.status IN ('IN' , 'OK', 'PM') AND o.OrderStatus IN (180, 190, 200) ")
		  	.append("AND (o.Invalidreason is null or o.Invalidreason in(2,3,4) ) ");
		List<Map<String,Object>> yfList = jdbc.queryForList(sbyf.toString(), new Object[] {beginDate, endDate });
		// 预付订单数
		int yfqks = 0;
		// 预付补贴
		BigDecimal yfbtje = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(yfList)){
			//计算预付收益
			HomeUtilMode mode = calSubsidy(yfList);
			yfqks = mode.getNum();
			yfbtje = mode.getSubsidy();
		}
		StringBuffer sbdf = new StringBuffer();//AND o.spreadUser = ?
		sbdf.append("SELECT o.id FROM b_otaroomorder t, b_pmsroomorder ro, b_otaorder o ")
		  .append("WHERE t.Hotelid = ro.Hotelid AND t.PMSRoomOrderNo = ro.PmsRoomOrderNo AND t.OtaOrderId = o.id ")
		  .append(" AND o.Ordertype = 2 AND o.hotelid = ?  AND o.spreadUser is not null ")
		  .append("AND ro.Checkintime >= ? AND ro.Checkintime < ? AND ro.status IN ('IN' , 'OK', 'PM') AND o.OrderStatus IN (180, 190, 200) ")
		  .append("AND (o.Invalidreason is null or o.Invalidreason in(2,3,4) ) ");
		List<Map<String,Object>> resdf = jdbc.queryForList(sbdf.toString(), new Object[] { thisHotelId, beginDate, endDate });
		// 到付订单数
		int dfqks = 0;
		// 到付补贴
		BigDecimal dfbtje = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(resdf)){
			//计算到付补贴
			HomeUtilMode mode = calSubsidy(resdf);
			dfqks = mode.getNum();
			dfbtje = mode.getSubsidy();
		}
		// TODO
//		StringBuffer dd = new StringBuffer();
//		dd.append("SELECT o.id orderId FROM b_otaroomorder t, b_pmsroomorder ro, b_otaorder o ")
//		  .append("WHERE t.Hotelid = ro.Hotelid AND t.PMSRoomOrderNo = ro.PmsRoomOrderNo AND t.OtaOrderId = o.id ")
//		  .append(" AND o.Ordertype = 1 ")
//		  .append("AND o.hotelid = ? AND ro.Checkintime >= ? AND ro.Checkintime < ?  ")
//		  .append(" AND ro.status IN ('IN' , 'OK', 'PM') AND o.OrderStatus IN (180, 190, 200) ");
//		List<Map<String,Object>> list = jdbc.queryForList(dd.toString(),thisHotelId, beginDate,endDate);
//		float sum = 0.0f;
//		for(Map<String,Object> o:list){
//			String sql = "SELECT p.cost FROM p_pay s,p_payinfo p WHERE s.id=p.payid AND type in (1,3,8) AND";
//			String key = o.keySet().iterator().next();
//			int orderId = Integer.parseInt(o.get(key).toString());
//			sql +=" s.orderid = "+orderId+"";
//			List<Map<String,Object>> value = jdbc.queryForList(sql);
//			for(Map<String,Object> payinfo : value){
//				String k = payinfo.keySet().iterator().next();
//				float cost = Float.parseFloat(payinfo.get(k).toString());
//				sum += cost;
//			}
//		}
//		StringBuffer yjfee = new StringBuffer();
//		yjfee.append("SELECT o.TotalPrice totalPrice FROM b_otaroomorder t, b_pmsroomorder ro, b_otaorder o ")
//		  .append("WHERE t.Hotelid = ro.Hotelid AND t.PMSRoomOrderNo = ro.PmsRoomOrderNo AND t.OtaOrderId = o.id ")
//		  .append("AND o.hotelid = ? AND o.spreadUser is null AND ro.Checkintime >= ? AND ro.Checkintime < ?  ")
//		  .append(" AND ro.status IN ('IN' , 'OK', 'PM') AND o.OrderStatus IN (180, 190, 200)");
//		List<Map<String,Object>> yjlist = jdbc.queryForList(yjfee.toString(),thisHotelId, beginDate,endDate);
//		float yjFeeSum = 0.0f;
//		for(Map<String,Object> o:yjlist){
//			String k = o.keySet().iterator().next();
//			float fee = Float.parseFloat(o.get(k).toString())*0.1f;
//			yjFeeSum += fee;
//		}
		DecimalFormat decimalFormat = new DecimalFormat("#");
		map.put("yfbtdds", yfqks);
		map.put("dfbtdds", dfqks);
		map.put("yfbtje", decimalFormat.format(yfbtje));
		map.put("dfbtje", decimalFormat.format(dfbtje));
//		float total = yfbtje+dfbtje+sum-yjFeeSum;
		map.put("kjzye", 0);
		this.getOrderCounts(thisHotelId, map, beginDate, endDate);
	}
	
	/**
	 * 获取酒店的maxPrice缓存
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getHotelMaxPrice(Long hotelId){
		Map<String, Object> maxPrice = (Map<String, Object>) HmsRedisCacheUtils.getRedisCache(ContentUtils.HOTEL_STATISTICS_CACHE).get(
				ContentUtils.CACHE_HOTEL_MAX_PRICE_PREFIX + hotelId, HashMap.class);
		if (maxPrice == null) {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = HmsJdbcTemplate.getJdbcTemplate().queryForList(
					"select hotelId, otaCheckinNum, pmsRevenues, otaRevenues from t_hotel_maxprice t where t.hotelId = ?",
						new Object[] { hotelId });
			maxPrice = list.size() > 0 ? list.get(0) : null;
			if (maxPrice == null) {
				HmsJdbcTemplate.getJdbcTemplate()
						.update("insert into t_hotel_maxPrice (hotelId, otaCheckinNum, pmsRevenues, otaRevenues) values(?,?,?,?)",
							new Object[] { hotelId, 0, 0, 0 });
				maxPrice = new HashMap<String, Object>();
				maxPrice.put("hotelId", hotelId);
				maxPrice.put("otaCheckinNum", 0);
				maxPrice.put("pmsRevenues", 0);
				maxPrice.put("otaRevenues", 0);
			}
			// 缓存一天的时间
			HmsRedisCacheUtils.getRedisCache(ContentUtils.HOTEL_STATISTICS_CACHE).put(
					ContentUtils.CACHE_HOTEL_MAX_PRICE_PREFIX + hotelId, maxPrice);
		}
		return maxPrice;
	}
	
	/** 
	 * 查询酒店的PMS订单间夜数
	 */
	private int getCurrPmsOrderNum(long hotelId, Date begintime, Date endtime) {
		String sql = "SELECT COUNT(id) FROM b_pmsroomorder WHERE Status in ('RE', 'IN', 'OK') AND Begintime >= ? AND Begintime < ? AND Hotelid = ?";
		return HmsJdbcTemplate.getJdbcTemplate().queryForObject(sql, new Object[] { begintime, endtime, hotelId }, Integer.class);
	}
	
	/** 
	 * 查询酒店的PMS入住间夜数
	 */
	private int getCurrPmsCheckinNum(long hotelId, Date begintime, Date endtime) {
		String sql = "SELECT COUNT(id) FROM b_pmsroomorder WHERE Status in ('IN', 'OK') AND Checkintime >= ? AND Checkintime < ? AND Hotelid = ?";
		return HmsJdbcTemplate.getJdbcTemplate().queryForObject(sql, new Object[] { begintime, endtime, hotelId }, Integer.class);
	}
	
	/* 
	 * 查询酒店的PMS入住间夜数
	 */
	private Map<String, Object> getCurrOtaCheckinNumAndRevenues(long hotelId, Date begintime, Date endtime) {
		String sql = "SELECT COUNT(t0.id) otaCheckinNum, SUM(pms_cost.Roomcost) otaRevenues FROM b_pmsroomorder t0,"
				+ " b_otaroomorder t, b_pmscost pms_cost WHERE t0.PmsRoomOrderNo = t.PmsRoomOrderNo AND t0.Hotelid = " + hotelId
				+ " AND t.Hotelid = " + hotelId + " AND pms_cost.Hotelid = " + hotelId + " AND t0.Checkintime >= ? AND t0.Checkintime < ?"
				+ " AND t.PmsRoomOrderNo = pms_cost.customerno AND t0.Status in ('IN','OK') AND pms_cost.Roomcost > 0";					 
		return HmsJdbcTemplate.getJdbcTemplate().queryForMap(sql, new Object[] { begintime, endtime }, HashMap.class);
	}
	
	/**
	 * 获取Ots入住数量
	 */
	private int getOtsRentOrderNum(long hotelId, Date begintime, Date endtime) {
		String sql = "select count(a.id) pmsRentOrderNum from b_otaroomorder a inner join b_pmsroomorder b"
				+ " on a.Hotelid = b.Hotelid and a.PmsRoomOrderNo = b.PmsRoomOrderNo where b.Status in ('IN', 'OK')"
				+ " and b.Begintime >= ? and b.Begintime < ? and b.Hotelid = ?";
		return HmsJdbcTemplate.getJdbcTemplate().queryForObject(sql, new Object[] { begintime, endtime, hotelId }, Integer.class);
	}
	/**
	 * 获取Ots入住数量
	 */
	private int getOtsOrderNum(long hotelId, Date begintime, Date endtime) {
		String sql = "select count(a.id) otsOrderNum from b_otaroomorder a inner join b_pmsroomorder b"
				+ " on a.Hotelid = b.Hotelid and a.PmsRoomOrderNo = b.PmsRoomOrderNo where b.Status in ('RE','IN', 'OK')"
				+ " and b.Begintime >= ? and b.Begintime < ? and b.Hotelid = ?";
		return HmsJdbcTemplate.getJdbcTemplate().queryForObject(sql, new Object[] { begintime, endtime, hotelId }, Integer.class);
	}
	/* 
	 * 查询酒店的订单、入住数据，根据时间查询，date为当天则查当前的实时数据，date为历史则配合top进行查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getHotelOrdersDailys(Long hotelId, String date, int top) {
		List<Map<String, Object>> datas = null;
		if (HmsDateUtils.getDatetime().startsWith(date)) {
			// 查当天的数据，实时查询
			datas = getHomeDatas(hotelId, HmsDateUtils.getDate(), 0);
		} else if (top > 0) {
			// 先查缓存里的数据
			List<Map<String, Object>> topDailys = (List) HmsRedisCacheUtils.getRedisCache(ContentUtils.HOTEL_STATISTICS_CACHE)
					.get(ContentUtils.CACHE_HOTEL_ORDER_DAILY_LIST_60 + hotelId, ArrayList.class);
			if (topDailys == null || topDailys.size() == 0) {
				// 查60天的数据，缓存一天
				topDailys = HmsJdbcTemplate.getJdbcTemplate().queryForList(
					"select * from h_hotel_orders_daily t where t.hotelId = ? order by date desc limit "
							+ top, new Object[] { hotelId });
				HmsRedisCacheUtils.getRedisCache(ContentUtils.HOTEL_STATISTICS_CACHE)
					.put(ContentUtils.CACHE_HOTEL_ORDER_DAILY_LIST_60 + hotelId, topDailys);
			}
			return topDailys;
		}
		return datas;
	}
	
	/**
	 * 供每天的调度使用，查询请使用下面的getHotelOrdersDailys方法
	 */
	public List<Map<String, Object>> getHomeDatas(Long hotelId, String date, int addDays) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date begintime = HmsDateUtils.addDays(HmsDateUtils.getDateFromString(date + " 00:00:00"), addDays);
		Date endtime = HmsDateUtils.addDays(HmsDateUtils.getDateFromString(HmsDateUtils.getDateAdded(1, date) + " 00:00:00"), addDays);
		paramMap.put("begintime", begintime);
		paramMap.put("endtime", endtime);
		String sql = "SELECT \n" +
				"    a.Hotelid,\n" +
				"    a.hotelRoomNum,\n" +
				"    a.pmsOrderNum,		--  PMS订单间夜数\n" +
				"    a.pmsCheckinNum,	--  PMS入住间夜数\n" +
				"    a.pmsRevenues,		--  PMS营业额\n" +
				"    b.otaOrderNum,		--  OTA订单间夜数\n" +
				"    b.otaCheckinNum,	--  OTA入住间夜数\n" +
				"    b.otaRevenues		--  OTA营业额\n" +
				"FROM\n" +
				"    (SELECT \n" +
				"        t.Hotelid,\n" +
				"            MAX(h.roomNum) 'hotelRoomNum',\n" +
				"            SUM(CASE\n" +
				"                WHEN\n" +
				"                    Status IN ('RE' , 'IN', 'OK')\n" +
				"                        AND t.Begintime >= '2015-05-25'\n" +
				"                        AND t.Begintime < '2015-05-26'\n" +
				"                THEN\n" +
				"                    1\n" +
				"                ELSE 0\n" +
				"            END) 'pmsOrderNum',\n" +
				"            SUM(CASE\n" +
				"                WHEN\n" +
				"                    Status IN ('IN' , 'OK')\n" +
				"                        AND t.Checkintime >= '2015-05-25'\n" +
				"                        AND t.Checkintime < '2015-05-26'\n" +
				"                THEN\n" +
				"                    1\n" +
				"                ELSE 0\n" +
				"            END) 'pmsCheckinNum',\n" +
				"            SUM(CASE\n" +
				"                WHEN\n" +
				"                    Status IN ('IN' , 'OK')\n" +
				"                        AND c.Roomcost > 0\n" +
				"                        AND t.Checkintime >= '2015-05-25'\n" +
				"                        AND t.Checkintime < '2015-05-26'\n" +
				"                THEN\n" +
				"                    c.Roomcost\n" +
				"                ELSE 0\n" +
				"            END) 'pmsRevenues'\n" +
				"    FROM\n" +
				"        b_pmsroomorder t\n" +
				"    LEFT JOIN t_hotel h ON t.hotelid = h.id\n" +
				"    LEFT JOIN b_pmscost c ON t.PmsRoomOrderNo = c.customerno\n" +
				"        AND t.Hotelid = c.Hotelid\n" +
				"    WHERE\n" +
				"        " + (hotelId != null ? " t.Hotelid = " + hotelId +" and " : "") + "\n" +
				"        ((t.Begintime >= '2015-05-25'\n" +
				"            AND t.Begintime < '2015-05-26')\n" +
				"            OR (t.Checkintime >= '2015-05-25'\n" +
				"            AND t.Checkintime < '2015-05-26'))\n" +
				"    GROUP BY t.hotelid) a,\n" +
				"    (SELECT \n" +
				"        t.hotelid,\n" +
				"            COUNT(t.hotelid) 'otaOrderNum',\n" +
				"            COUNT(c.hotelid) 'otaCheckinNum',\n" +
				"            SUM(c.Roomcost) 'otaRevenues'\n" +
				"    FROM\n" +
				"        b_otaroomorder t\n" +
				"    LEFT OUTER JOIN b_pmsroomorder pro ON pro.Hotelid = t.hotelid\n" +
				"        AND pro.PmsRoomOrderNo = t.PMSRoomOrderNo\n" +
				"        AND pro.Status IN ('IN' , 'OK')\n" +
				"    LEFT JOIN b_PmsCost c ON pro.PmsRoomOrderNo = c.customerno\n" +
				"        AND pro.Hotelid = c.hotelid\n" +
				"        AND c.Roomcost > 0\n" +
				"    WHERE\n" +
				"        " + (hotelId != null ? " t.Hotelid = " + hotelId +" and " : "") + "\n" +
				"        ((t.Begintime >= '2015-05-25'\n" +
				"            AND t.Begintime < '2015-05-26')\n" +
				"            OR (pro.Checkintime >= '2015-05-25'\n" +
				"            AND pro.Checkintime < '2015-05-26'))\n" +
				"    GROUP BY t.Hotelid) b\n" +
				"WHERE\n" +
				"    a.hotelid = b.hotelid";
		List<Map<String, Object>> datas = HmsJdbcTemplate.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		return datas;
	}
	
	private EHotelMapper geteHotelMapper() {
		return eHotelMapper;
	}
	/**
	 * 获取订单编号id集合
	 * @param List<Map<String,Object>>
	 * @return String
	 */
//	public String getOtsOrderIds(List<Map<String,Object>> yfList) {
//		StringBuffer ids = new StringBuffer();
//		// 获取订单id集合
//		for (int i=0; i<yfList.size(); i++) {
//			// 获取一条数据的map
//			Entry<String,Object> entry = yfList.get(i).entrySet().iterator().next();
//			// 获取订单号
//			String id = String.valueOf(entry.getValue());
//			// 第二条数据开始，订单号前加","
//			if(i > 0) {
//				ids.append(ContentUtils.CHAR_COMMA);
//			}
//			//追加订单号
//			ids.append(id);
//		}
//		//返回结果
//		return ids.toString();
//	}
	/**
	 * 获取补贴金额
	 * @param List<Map<String,Object>>
	 * @return HomeUtilMode
	 */
	public HomeUtilMode calSubsidy(List<Map<String,Object>> yfList) {
		// 订单号集合
		List<Long> list = new ArrayList<Long>();
		for (int i=0; i<yfList.size(); i++) {
			// 获取一条数据的map
			Entry<String,Object> entry = yfList.get(i).entrySet().iterator().next();
			list.add((Long) entry.getValue());
		}
		// 返回结果
		HomeUtilMode result = new HomeUtilMode();
		result.setNum(0);
		result.setSubsidy(BigDecimal.ZERO);
		// 单次查询200条数据
		int interval = 100;
		// 每200条查询一次
		for(int i = 0 ;i<list.size(); ){
			// 间隔为200
			int toIndex = i + interval;
			// 边界判断
			toIndex = toIndex > list.size() ? list.size() : toIndex;
			// 查询结果
			HomeUtilMode temp = homeMapper.querySubsidy(list.subList(i, toIndex));
			result.setNum(result.getNum()+temp.getNum());
			BigDecimal subsidy = temp.getSubsidy() == null ? BigDecimal.ZERO : temp.getSubsidy();
			result.setSubsidy(result.getSubsidy().add(subsidy));
			// 累加
			i = toIndex;
		}
		return  result;
	}
	
}
