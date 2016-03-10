package com.mk.hms.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mk.hms.mapper.*;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.db.HmsJdbcTemplate;
import com.mk.hms.enums.HmsEHotelPmsStatusEnum;
import com.mk.hms.enums.HmsEHotelStatusEnum;
import com.mk.hms.enums.HmsTBusinessZoneTypeEnum;
import com.mk.hms.enums.HmsTFacilityBindingEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HmsBAreaRule;
import com.mk.hms.model.HmsEHotelFacilityModel;
import com.mk.hms.model.HmsEHotelModel;
import com.mk.hms.model.HmsEHotelbussinesszoneModel;
import com.mk.hms.model.HmsSaleUserModel;
import com.mk.hms.model.HmsTBasePriceModel;
import com.mk.hms.model.HmsTBusinesszoneModel;
import com.mk.hms.model.HmsTFacilityModel;
import com.mk.hms.model.HmsTHotelModel;
import com.mk.hms.model.HmsTRoomtypeFacilityModel;
import com.mk.hms.model.HmsTRoomtypeInfoModel;
import com.mk.hms.model.HmsTRoomtypeModel;
import com.mk.hms.model.MUser;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.THotelWithBLOBs;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.HttpClientUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;

/**
 * 酒店讯息 sevice
 * @author hdy
 *
 */
@Service
@Transactional
public class HmsHotelMessageService {
	
	@Autowired
	private HmsHotelMessageMapper hmsHotelMessageMapper;
	
	@Autowired
	private HmsEBasePriceMapper hmsEBasePriceMapper;
	
	@Autowired
	private HmsERoomtypeFacilityMapper hmsERoomtypeFacilityMapper;
	
	@Autowired
	private HmsERoomtypeInfoMapper hmsERoomtypeInfoMapper;
	
	@Autowired
	private HmsSaleUserMapper saleUserMapper;
	
	@Autowired
	private HmsBAreaRuleMapper hmsBAreaRuleMapper;
	
	@Autowired
	private EHotelMapper eHotelMapper;
	
	@Autowired
	private THotelMapper tHotelMapper;
	
	@Autowired
	private THotelMapperExt tHotelMapperExt;

	@Autowired
	private RoomSaleConfigMapper roomSaleConfigMapper;
	
	//begin
	@Autowired
	private HotelAuditSynToTHotelMapper mapper;
	public int addTRoomtypeInfos(List<HmsTRoomtypeInfoModel> hmsrttModels){
		return mapper.addTRoomtypeInfos(hmsrttModels);
	}
	public int deleteTRoomtypeInfo(List<HmsTRoomtypeModel> hmsTRoomtypeModels){
		return mapper.deleteTs(hmsTRoomtypeModels);
	}
	public int addTRoomtypeFacilitys(List<HmsTRoomtypeFacilityModel> hmsTRtFacModels){
	    return mapper.addTRoomtypeFacilitys(hmsTRtFacModels);
	}
	public int insertTHotelFacilities(Map<Object,Object> map){
		return mapper.insertTHotelFacilities(map);
	}
	public int[] updateTPrices(final List<HmsTBasePriceModel> hmspriceModels){
		JdbcTemplate jdbc = HmsJdbcTemplate.getJdbcTemplate();
		String sql="update t_baseprice set price=?,subprice=?,subper=?,updateTime=? where roomTypeId=?";
		BatchPreparedStatementSetter setter=new BatchPreparedStatementSetter (){
	          public void setValues(PreparedStatement ps,int i) throws SQLException{
	        	  HmsTBasePriceModel model=(HmsTBasePriceModel)hmspriceModels.get(i);
	        	  ps.setBigDecimal(1, model.getPrice());
		          ps.setBigDecimal(2, model.getSubprice());
		          ps.setBigDecimal(3, model.getSubper());
		          ps.setDate(4, HmsDateUtils.convert(model.getUpdateTime()));
		          ps.setLong(5, model.getRoomTypeId());
	          }
	          public int getBatchSize(){
	             return hmspriceModels.size();
	          }
	    };
	    return jdbc.batchUpdate(sql,setter);
	}
	public int addTBasePrices(List<HmsTBasePriceModel> hmspriceModels){
		return mapper.addTBasePrices(hmspriceModels);
	}
	public List<HmsTBasePriceModel> findAllTBasePriceByIds(List<HmsTRoomtypeModel> hmsTRoomtypeModels){
		return mapper.findAllTBasePriceByIds(hmsTRoomtypeModels);
	}

	/**
	 * 获取酒店设置列表
	 * @return 酒店讯息列表
	 */
	public List<HmsTFacilityModel> findTFacilityList() {
		return hmsHotelMessageMapper.finsTFacilities(HmsTFacilityBindingEnum.Hotel.getValue(), HmsVisibleEnum.T.getValue());
	}
	
	/**
	 * 获取酒店已配置设施
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public List<HmsEHotelFacilityModel> findEHotelFacilitityList() throws SessionTimeOutException {
		return hmsHotelMessageMapper.findEHotelFacilities(SessionUtils.getThisHotel().getId());
	}
	
	/**
	 * 获取当前酒店对象
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public HmsEHotelModel thisHotel() throws SessionTimeOutException {
		EHotel thisHotel = SessionUtils.getThisHotel();
		return hmsHotelMessageMapper.findEHotelById(thisHotel.getId());
	}
	
	/**
	 * 删除酒店设施
	 * @param hotelId 酒店id
	 * @return 受影响条数
	 */
	public int deleteTHotelFacilities(long hotelId) {
		return hmsHotelMessageMapper.deleteTHotelFacilities(hotelId);
	}
	
	/**
	 * 添加酒店设施数据
	 * @param hotelId 酒店id
	 * @param facId 设施id
	 * @return 受影响条数
	 */
	public int insertTHotelFacilities(long hotelId, long facId) {
		return hmsHotelMessageMapper.insertTHotelFacilities(hotelId, facId);
	}
	
	/**
	 * 保存配置信息
	 * @throws SessionTimeOutException 
	 */
	public void saveTFacilityList(String[] tFacilityArray) throws SessionTimeOutException {
		long thisHotelId = SessionUtils.getThisHotel().getId();
		List<HmsEHotelFacilityModel> tFacilityList = hmsHotelMessageMapper.findEHotelFacilities(thisHotelId);
		// 相同值
		List<String> sameTFacilityList = new ArrayList<String>();
		//获取相同数组项
		for (String tFacility : tFacilityArray) {
			for (HmsEHotelFacilityModel eHotelFacility : tFacilityList) {
				if (tFacility.equals(eHotelFacility.getFacId() + "")) {
					sameTFacilityList.add(tFacility);
				}
			}
		}
		// 删除数据库中多余项
		List<Long>  delFacIds = new ArrayList<Long>(); //被删除fac IDs
		for (HmsEHotelFacilityModel eHotelFacility : tFacilityList) {
			boolean isDel = true;
			for (String s : sameTFacilityList) {
				if (s.equals(eHotelFacility.getFacId() + "")) {
					isDel = false;
				}
			}
			if (isDel) {
				delFacIds.add(eHotelFacility.getFacId());
			}
		}
		String delFacIdsStr = StringUtils.join(delFacIds.toArray(), ContentUtils.CHAR_COMMA);
		if (StringUtils.isNotBlank(delFacIdsStr)) {
			hmsHotelMessageMapper.deleteEHotelFacilities(thisHotelId, delFacIdsStr);
		}
		// 添加数据库没有项
		for (String tFacility : tFacilityArray) {
			boolean isAdd = true;
			for (String s : sameTFacilityList) {
				if (s.equals(tFacility)) {
					isAdd = false;
				}
			}
			if (isAdd && StringUtils.isNotBlank(tFacility)) {
				hmsHotelMessageMapper.addEHotelFacilities(thisHotelId, Long.parseLong(tFacility));
			}
		}
	}
	
	/**
	 * 获取商圈信息列表
	 * @return 商圈信息列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsTBusinesszoneModel> getCircleList() throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		return hmsHotelMessageMapper.findTBusinesszones(hotel.getDisId(), HmsTBusinessZoneTypeEnum.Circle.getValue());
	}
	
	/**
	 * 获取商圈信息列表
	 * @return 商圈信息列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsTBusinesszoneModel> getSubwayList() throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		return hmsHotelMessageMapper.findTBusinesszones(hotel.getDisId(), HmsTBusinessZoneTypeEnum.Subway.getValue());
	}
	
	/**
	 * 获取周边大学信息列表
	 * @return 周边大学信息列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsTBusinesszoneModel> getUniversityList() throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		return hmsHotelMessageMapper.findTBusinesszones(hotel.getDisId(), HmsTBusinessZoneTypeEnum.University.getValue());
	}
	
	/**
	 * 获取酒店周边商圈信息列表
	 * @return 商圈列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsTBusinesszoneModel> getMyCircleList() throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		List<HmsEHotelbussinesszoneModel> list = hmsHotelMessageMapper.findEHotelbussinesszones(hotel.getId());
		List<Long> bussinesszoneIds = new ArrayList<Long>();
		for (HmsEHotelbussinesszoneModel bussinesszone : list) {
			bussinesszoneIds.add(bussinesszone.getBusinessZoneId());
		}
		if (bussinesszoneIds.size() > 0) {
			return hmsHotelMessageMapper.findMyTBusinesszones(StringUtils.join(bussinesszoneIds.toArray(), ContentUtils.CHAR_COMMA),
					HmsTBusinessZoneTypeEnum.Circle.getValue());
		}
		return null;
	}
	
	/**
	 * 获取酒店地铁信息列表表
	 * @return 地铁数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsTBusinesszoneModel> getMySubwayList() throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		List<HmsEHotelbussinesszoneModel> list = hmsHotelMessageMapper.findEHotelbussinesszones(hotel.getId());
		List<Long> bussinesszoneIds = new ArrayList<Long>();
		for (HmsEHotelbussinesszoneModel bussinesszone : list) {
			bussinesszoneIds.add(bussinesszone.getBusinessZoneId());
		}
		if (bussinesszoneIds.size() > 0) {
			return hmsHotelMessageMapper.findMySubwayStations(StringUtils.join(bussinesszoneIds.toArray(), ContentUtils.CHAR_COMMA),
					HmsTBusinessZoneTypeEnum.Subway.getValue());
		}
		return null;
	}
	
	/**
	 * 获取酒店周边大学信息列表
	 * @return 大学列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsTBusinesszoneModel> getMyUniversityList() throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		List<HmsEHotelbussinesszoneModel> list = hmsHotelMessageMapper.findEHotelbussinesszones(hotel.getId());
		List<Long> bussinesszoneIds = new ArrayList<Long>();
		for (HmsEHotelbussinesszoneModel bussinesszone : list) {
			bussinesszoneIds.add(bussinesszone.getBusinessZoneId());
		}
		if (bussinesszoneIds.size() > 0) {
			return hmsHotelMessageMapper.findMyTBusinesszones(StringUtils.join(bussinesszoneIds.toArray(), ContentUtils.CHAR_COMMA),
					HmsTBusinessZoneTypeEnum.University.getValue());
		}
		return null;
	}
	
	/**
	 * 修改酒店交通信息
	 * @param trafficText 信息文本
	 * @return 修改条数
	 * @throws SessionTimeOutException 
	 */
	public int updateEHotelTraffic(String trafficText) throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		return hmsHotelMessageMapper.updateEHotelTraffic(trafficText, hotel.getId());
	}
	
	/**
	 * 修改酒店周边信息
	 * @param peripheralText 信息文本
	 * @return 修改条数
	 * @throws SessionTimeOutException 
	 */
	public int updateEHotelPeripheral(String peripheraltext) throws SessionTimeOutException {
		EHotel hotel = SessionUtils.getThisHotel();
		return hmsHotelMessageMapper.updateEHotelPeripheral(peripheraltext, hotel.getId());
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @return 数据列表
	 */
	public List<HmsEHotelModel> findHotelList(Object queryContent, Page page) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelsByQueryContentHasHotel(stateIndex, dataSize, queryString);
			}
			return hmsHotelMessageMapper.findHotelsByQueryContentNoHotel(stateIndex, dataSize, queryString);
		}
		return hmsHotelMessageMapper.findHotels(stateIndex, dataSize);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param whereStr 查询条件
	 * @return 数据列表
	 */
	public List<HmsTHotelModel> findTHotelList(String whereStr) {
		return hmsHotelMessageMapper.findTHotels(whereStr);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsEHotelModel> findMyHotelList(Object queryContent, Page page) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelsByQueryContentHasHotel(stateIndex, dataSize, mUser.getLoginname(), queryString);
			}
			return hmsHotelMessageMapper.findMyHotelsByQueryContentNoHotel(stateIndex, dataSize, mUser.getLoginname(), queryString);
		}
		return hmsHotelMessageMapper.findMyHotels(stateIndex, dataSize, mUser.getLoginname());
	}
	
	
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 */
	public List<HmsEHotelModel> findHotelListByPmsStatus(Object queryContent, Page page, int pmsStatus) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelListByPmsStatusByQueryContentHasHotel(stateIndex, dataSize, pmsStatus, queryString);
			}
			return hmsHotelMessageMapper.findHotelListByPmsStatusByQueryContentNoHotel(stateIndex, dataSize, pmsStatus, queryString);
		}
		return hmsHotelMessageMapper.findHotelListByPmsStatus(stateIndex, dataSize, pmsStatus);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsEHotelModel> findMyHotelListByPmsStatus(Object queryContent, Page page, int pmsStatus) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelListByPmsStatusByQueryContentHasHotelId(mUser.getLoginname(), stateIndex, dataSize, pmsStatus, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelListByPmsStatusByQueryContentNoHotelId(mUser.getLoginname(), stateIndex, dataSize, pmsStatus, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelListByPmsStatus(mUser.getLoginname(), stateIndex, dataSize, pmsStatus);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @param states 状态
	 * @return 数据列表
	 */
	public List<HmsEHotelModel> findHotelListByCheck(Object queryContent, Page page, String states) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelListByCheckAndQueryContentHasHotelId(stateIndex, dataSize, states, queryString);
			}
			return hmsHotelMessageMapper.findHotelListByCheckAndQueryContentNoHotelId(stateIndex, dataSize, states, queryString);
		}
		return hmsHotelMessageMapper.findHotelListByCheck(stateIndex, dataSize, states);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @param states 状态
	 * @return 数据列表
	 */
	public List<HmsEHotelModel> findHotelListByState(Object queryContent, Page page, int state) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelListByStateAndQueryContentHasHotelId(stateIndex, dataSize, state, queryString);
			}
			return hmsHotelMessageMapper.findHotelListByStateAndQueryContentNoHotelId(stateIndex, dataSize, state, queryString);
		}
		return hmsHotelMessageMapper.findHotelListByState(stateIndex, dataSize, state);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @param states 状态
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsEHotelModel> findMyHotelListByCheck(Object queryContent, Page page, String states) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelListByCheckByQueryContentHasHotel(mUser.getLoginname(), stateIndex, dataSize, states, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelListByCheckByQueryContentNoHotel(mUser.getLoginname(), stateIndex, dataSize, states, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelListByCheck(mUser.getLoginname(), stateIndex, dataSize, states);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @param state 状态
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsEHotelModel> findMyHotelListByState(Object queryContent, Page page, int state) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelListByStateAndQueryContentHasHotel(mUser.getLoginname(), stateIndex, dataSize, state, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelListByStateAndQueryContentNoHotel(mUser.getLoginname(), stateIndex, dataSize, state, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelListByState(mUser.getLoginname(), stateIndex, dataSize, state);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @return 数据列表
	 */
	public List<HmsEHotelModel> findHotelListByReason(Page page) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		return hmsHotelMessageMapper.findHotelListByReason(stateIndex, dataSize);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsEHotelModel> findMyHotelListByReason(Object queryContent, Page page) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelListByReasonAndQueryContentHasHotel(mUser.getLoginname(), stateIndex, dataSize, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelListByReasonAndQueryContentNoHotel(mUser.getLoginname(), stateIndex, dataSize, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelListByReason(mUser.getLoginname(), stateIndex, dataSize);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @param states 上线状态
	 * @return 数据列表
	 */
	public List<HmsEHotelModel> findHotelListOnLine(Object queryContent, Page page, String visible) {
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelListOnLineByQueryContentHasHotelId(stateIndex, dataSize, visible, queryString);
			}
			return hmsHotelMessageMapper.findHotelListOnLineByQueryContentNoHotelId(stateIndex, dataSize, visible, queryString);
		}
		return hmsHotelMessageMapper.findHotelListOnLine(stateIndex, dataSize, visible);
	}
	
	/**
	 * 获取酒店信息列表
	 * @param pageNum 页码
	 * @param pageSize 当叶数据纪录条数
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<HmsEHotelModel> findMyHotelListOnLine(Object queryContent, Page page, String visible) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		int stateIndex = page.getStartIndex();
		int dataSize = page.getPageSize();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelListOnLineByQueryContentHasHotelId(mUser.getLoginname(), stateIndex, dataSize, visible, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelListOnLineByQueryContentNoHotelId(mUser.getLoginname(), stateIndex, dataSize, visible, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelListOnLine(mUser.getLoginname(), stateIndex, dataSize, visible);
	}
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 */
	public int findHotelListCount(Object queryContent) {
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelCountByQueryContentHasHotel(queryString);
			}
			return hmsHotelMessageMapper.findHotelCountByQueryContentNoHotel(queryString);
		}
		return hmsHotelMessageMapper.findHotelCount();
	}
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 * @throws SessionTimeOutException 
	 */
	public int findMyHotelListCount(Object queryContent) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null != queryContent) {
			// 判断是否为数字，是数字则添加酒店id；不是则不添加酒店id过滤
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelCountByQueryContentHasHotelId(mUser.getLoginname(), queryString);
			}
			return hmsHotelMessageMapper.findMyHotelCountByQueryContentNoHotelId(mUser.getLoginname(), queryString);
		}
		return hmsHotelMessageMapper.findMyHotelCount(mUser.getLoginname());
	}
	

	/**
	 * 获取酒店列表数据条数
	 * @param pmsStatus  pms状态
	 * @return 数据条数
	 */
	public int findHotelByPmsStatusCount(Object queryContent, int pmsStatus) {
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelByPmsStatusCountByQueryContentHasHotelId(pmsStatus, queryString);
			}
			return hmsHotelMessageMapper.findHotelByPmsStatusCountByQueryContentNoHotelId(pmsStatus, queryString);
		}
		return hmsHotelMessageMapper.findHotelByPmsStatusCount(pmsStatus);
	}
	
	/**
	 * 获取我的酒店列表数据条数
	 * @param pmsStatus  pms状态
	 * @return 数据条数
	 * @throws SessionTimeOutException 
	 */
	public int findMyHotelByPmsStatusCount(Object queryContent, int pmsStatus) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelByPmsStatusCountByQueryContentHasHotel(mUser.getLoginname(), pmsStatus, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelByPmsStatusCountByQueryContentNoHotel(mUser.getLoginname(), pmsStatus, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelByPmsStatusCount(mUser.getLoginname(), pmsStatus);
	}
	
	/**
	 * 获取酒店列表数据条数
	 * @param states  状态
	 * @return 数据条数
	 */
	public int findHotelByCheckCount(Object queryContent, String states) {
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelByCheckCountByQueryContentHasHotelId(states, queryString);
			}
			return hmsHotelMessageMapper.findHotelByCheckCountByQueryContentNoHotelId(states, queryString);
		}
		return hmsHotelMessageMapper.findHotelByCheckCount(states);
	}
	
	/**
	 * 获取我的酒店列表数据条数
	 * @param states  状态
	 * @return 数据条数
	 * @throws SessionTimeOutException 
	 */
	public int findMyHotelByCheckCount(Object queryContent, String states) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelByCheckCountByQueryContentHasHotel(mUser.getLoginname(), states, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelByCheckCountByQueryContentNoHotel(mUser.getLoginname(), states, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelByCheckCount(mUser.getLoginname(), states);
	}
	
	/**
	 * 获取酒店列表数据条数
	 * @param states  状态
	 * @return 数据条数
	 */
	public int findHotelByStateCount(Object queryContent, int states) {
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelByStateCountAndQueryContentHasHotelId(states, queryString);
			}
			return hmsHotelMessageMapper.findHotelByStateCountAndQueryContentNoHotelId(states, queryString);
		}
		return hmsHotelMessageMapper.findHotelByStateCount(states);
	}
	
	/**
	 * 获取我的酒店列表数据条数
	 * @param states  状态
	 * @return 数据条数
	 * @throws SessionTimeOutException 
	 */
	public int findMyHotelByStateCount(Object queryContent, int states) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelByStateCountAndQueryContentHasHotel(mUser.getLoginname(), states, queryString);
			}
			return hmsHotelMessageMapper.findMyHotelByStateCountAndQueryContentNoHotel(mUser.getLoginname(), states, queryString);
		}
		return hmsHotelMessageMapper.findMyHotelByStateCount(mUser.getLoginname(), states);
	}
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 */
	public int findHotelByReasonCount() {
		return hmsHotelMessageMapper.findHotelByReasonCount();
	}
	
	/**
	 * 获取我的酒店列表数据条数
	 * @return 数据条数
	 * @throws SessionTimeOutException 
	 */
	public int findMyHotelByReasonCount(Object queryContent) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelByReasonCountAndQueryContentHasHotel(mUser.getLoginname(), queryString);
			}
			return hmsHotelMessageMapper.findMyHotelByReasonCountAndQueryContentNoHotel(mUser.getLoginname(), queryString);
		}
		return hmsHotelMessageMapper.findMyHotelByReasonCount(mUser.getLoginname());
	}
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 */
	public int findHotelOnLineCount(Object queryContent, String visible) {
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findHotelOnLineCountByQueryContentHasHotelId(visible, queryString).size();
			}
			return hmsHotelMessageMapper.findHotelOnLineCountByQueryContentNoHotelId(visible, queryString).size();
		}
		return hmsHotelMessageMapper.findHotelOnLineCount(visible).size();
	}
	
	/**
	 * 获取我的酒店列表数据条数
	 * @return 数据条数
	 * @throws SessionTimeOutException 
	 */
	public int findMyHotelOnLineCount(Object queryContent, String visible) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		if (null != queryContent) {
			String queryString = queryContent.toString();
			if (NumberUtils.isNumber(queryString)) {
				return hmsHotelMessageMapper.findMyHotelOnLineCountAndQueryContentHasHotelId(visible,
						mUser.getLoginname(), queryString).size();
			}
			return hmsHotelMessageMapper.findMyHotelOnLineCountAndQueryContentNoHotelId(visible,
						mUser.getLoginname(), queryString).size();
		}
		return hmsHotelMessageMapper.findMyHotelOnLineCount(mUser.getLoginname(), visible).size();
	}
	
	/**
	 * 获取指定酒店对象信息
	 * @param hotelId 酒店id
	 * @return 酒店对象
	 */
	public HmsEHotelModel findEHotelById(long hotelId) {
		return  hmsHotelMessageMapper.findEHotelById(hotelId);
	}
	
	/**
	 * 获取指定酒店对象信息
	 * @param hotelId 酒店id
	 * @return 酒店对象
	 */
	public HmsEHotelModel findMyEHotelById(long hotelId) {
		return  hmsHotelMessageMapper.findMyEHotelById(hotelId);
	}
	
	/**
	 * 获取指定酒店对象信息
	 * @param pms pms编码
	 * @return 酒店对象列表
	 */
	public List<HmsEHotelModel> findMyEHotelByPms(String pms) {
		return  hmsHotelMessageMapper.findMyEHotelByPms(pms);
	}
	
	/**
	 * 获取指定酒店对象信息
	 * @param hotelId 酒店id
	 * @return 酒店对象
	 */
	public HmsTHotelModel findTHotelById(long hotelId) {
		return  hmsHotelMessageMapper.findTHotelById(hotelId);
	}
	
	/**
	 * 获取指定酒店对象信息
	 * @param hotelId 酒店id
	 * @return 酒店对象
	 */
	public Map<String,Object> findTHotelRealStatus(long hotelId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取酒店
		HmsTHotelModel tHotel = findTHotelById(hotelId);
		
		//获取在线状态
		String online = tHotel.getOnLine();
		String onlineStr = "未上线";
		if (online.equals("T")) {
			onlineStr = "在线";
		}else if (online.equals("F")) {
			onlineStr = "离线";
		}
		map.put("online", online);
		map.put("onlineStr", onlineStr);
		
		return  map;
	}
	
	/**
	 * 安装PMS
	 * @param pms pms编码
	 * @param updateTime 修改时间
	 * @param isNewPms 是否为新PMS
	 * @param hotelId 酒店主键
	 * @return 受影响条数
	 */
	public int installPms(String pms, Date updateTime, String isNewPms, long hotelId) {
		return hmsHotelMessageMapper.updateEHotelPms(pms, updateTime, isNewPms, hotelId);
	}
	
	/**
	 * 安装PMS
	 * @param pms pms编码
	 * @param updateTime 修改时间
	 * @param isNewPms 是否为新PMS
	 * @param hotelId 酒店主键
	 * @return 受影响条数
	 */
	public int installPms2(String pms,String pmshotelname, Date updateTime, String isNewPms, long hotelId, int pmsStatus) {
		return hmsHotelMessageMapper.updateEHotelPms2(pms, pmshotelname,updateTime, isNewPms, hotelId, pmsStatus);
	}
	
	/**
	 * 修改酒店状态
	 * @param state 状态值
	 * @param id 主键
	 * @return 受影响条数
	 */
	public int updateEHotelState(int state, long id) {
		return hmsHotelMessageMapper.updateEHotelState(state, id);
	}
	
	/**
	 * 修改酒店坐标
	 * @param state 状态值
	 * @param id 主键
	 * @return out
	 */
	public OutModel updateHotelLocation(long id, double lat, double lng) {
		OutModel out = new OutModel(false);
		// 获取酒店信息
		HmsEHotelModel eHotel = findEHotelById(id);
		if (null == eHotel) {
			out.setErrorMsg("酒店不存在");
			return out;
		}
		
		String adderss = null;
		try {
			if (eHotel.getState() == HmsEHotelStatusEnum.Submit.getValue()) {
				adderss = HmsFileUtils.getSysContentItem(ContentUtils.CHECK_HOTEL_INSERT_ADDRESS);
			} else {
				adderss = HmsFileUtils.getSysContentItem(ContentUtils.CHECK_HOTEL_UPDATE_ADDRESS);
			}
		} catch(IOException e) {
			out.setErrorMsg("获取接口地址失败");
			return out;
		}
		
		hmsHotelMessageMapper.updateEHotelLocation(id, lat, lng);
		
		if (eHotel.getState() < HmsEHotelStatusEnum.ManagerEditing.getValue()) {
			out.setSuccess(true);
			return out;
		}
		
		// 判断酒店是否安装PMS
		if (StringUtils.isBlank(eHotel.getPms()) || eHotel.getPmsStatus() <= HmsEHotelPmsStatusEnum.Init.getValue()) {
			out.setSuccess(true);
			return out;
		}
		
		//酒店已下线
		if(HmsVisibleEnum.F.toString().equals( eHotel.getVisible()) ){
			out.setSuccess(true);
			return out;			
		}
		
		//未设置酒店规则
		if(eHotel.getRulecode() <= 0){
			out.setSuccess(true);
			return out;
		}
		
		String sql = "SELECT id,name from t_roomtype  WHERE thotelid =" + id+ " and (cost is null or cost <=0) limit 0,1";
		List<Map<String, Object>> resultList = HmsJdbcTemplate.getJdbcTemplate().queryForList(sql);
		// 判断该房型的门市价是否已经同步过来
		if (!resultList.isEmpty()) {
			out.setSuccess(true);
			return out;
		}
		
		if (adderss == null) {
			out.setSuccess(true);
			return out;
		}
		
		if (hmsHotelMessageMapper.updateTHotelLocation(id, lat, lng) != 1) {
			out.setSuccess(true);
			return out;
		}
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("hotelid", Long.toString(id));
		JSONObject obj = null;
		try {
			obj = HttpClientUtils.post(adderss, paramsMap);
			if (null == obj || !obj.getBoolean("success")) {
				out.setErrorMsg("调用失败");
				return out;
			}
		} catch(IOException e) {
			out.setErrorMsg("调用失败");
			return out;
		}
		
		out.setSuccess(true);
		return out;
	}
	
	/**
	 * 保存酒店商圈信息
	 * @param eHotel 酒店实体
	 * @param circleList 商圈信息
	 * @param subwayList 地铁信息
	 * @param universityList 周边大学
	 * @throws SessionTimeOutException 
	 */
	public void saveBussinesszones(HmsEHotelModel eHotel, List<Long> deleteIds, List<HmsTBusinesszoneModel> addList) throws SessionTimeOutException {
		// 获取当前酒店对象
		EHotel thisHotel = SessionUtils.getThisHotel();
		// 保存酒店商圈基本信息
		hmsHotelMessageMapper.updateEHotelBussinesszones(eHotel.getOpenTime(), eHotel.getRepairTime(),
				eHotel.getRetentionTime(), eHotel.getDefaultLeaveTime(), eHotel.getIntroduction(),
				eHotel.getHotelphone(), eHotel.getHotelName(), eHotel.getDetailAddr(),eHotel.getDisId(),
				eHotel.getLatitude(), eHotel.getLongitude(), eHotel.getId());
		if (deleteIds.size() > 0) {
			hmsHotelMessageMapper.deleteEHotelBussinesszones(HmsStringUtils.join(deleteIds.toArray(), ContentUtils.CHAR_COMMA), 
					thisHotel.getId());
		}
		for (HmsTBusinesszoneModel businesszone : addList) {
			hmsHotelMessageMapper.addEHotelBussinesszones(businesszone.getId(), thisHotel.getId());
		}
	}
	
	/**
	 * 注册酒店证件图片
	 * @param hotel 酒店信息
	 * @return 受影响条数
	 * @throws SessionTimeOutException 
	 */
	public int saveHotelCredentialsPic(EHotelWithBLOBs hotel) throws SessionTimeOutException {
		return this.geteHotelMapper().updateByPrimaryKeyWithBLOBs(hotel);
	}
	
	/**
	 * 酒店信息图片
	 * @param picText 酒店信息图片
	 * @param id 酒店id
	 * @return 受影响条数
	 */
	public int saveHotelInfoPic(String picText, long id) {
		return hmsHotelMessageMapper.saveHotelInfoPic(picText, id);
	}
	

	/**
	 * 插入一条t表数据
	 * @param tBasePrice t表对象
	 * @return 受影响条数
	 */
	public HmsTBasePriceModel findTBasePrice(long roomTypeId) {
		return hmsEBasePriceMapper.findT(roomTypeId);
	}
	
	/**
	 * 插入一条t表数据
	 * @param tBasePrice t表对象
	 * @return 受影响条数
	 */
	public int addTBasePrice(HmsTBasePriceModel tBasePrice) {
		return hmsEBasePriceMapper.addT(tBasePrice);
	}
	
	/**
	 * 删除一条t表数据
	 * @param id 主键
	 * @return 受影响条数
	 */
	public int deleteTBasePrice(long id) {
		return hmsEBasePriceMapper.deleteTById(id);
	}
	
	/**
	 * 修改t表数据
	 * @param tBasePrice 对象
	 * @return 受影响条数
	 */
	public int updateTBasePrice(HmsTBasePriceModel tBasePrice) {
		return hmsEBasePriceMapper.updateT(tBasePrice);
	}
	
	/**
	 * 添加房型设备
	 * @param roomTypeId 房型id
	 * @param facId
	 * @return 受影响条数
	 */
	public int addTRoomtypeFacility(HmsTRoomtypeFacilityModel tRoomtypeFacilityModel) {
		return hmsERoomtypeFacilityMapper.addT(tRoomtypeFacilityModel);
	}
	
	/**
	 * 删除条房型设备数据
	 * @param roomTypeId
	 * @param facId
	 * @return
	 */
	public int deleteTRoomtypeFacilityByRoomtypeId(long roomTypeId) {
		return hmsERoomtypeFacilityMapper.deleteTByRoomtypeId(roomTypeId);
	}
	
	/**
	 * 通过主键删除房型设备
	 * @param id
	 * @return
	 */
	public int deleteTRoomtypeFacilityById(long id) {
		return hmsERoomtypeFacilityMapper.deleteTById(id);
	}

	/**
	 * 获取一条房型信息
	 * @param tRoomtypeInfo
	 * @return
	 */
	public HmsTRoomtypeInfoModel findTRoomtypeInfo(long roomtypeId) {
		return hmsERoomtypeInfoMapper.findTByRoomtypeId(roomtypeId);
	}
	
	/**
	 * 获取一条房型信息
	 * @param tRoomtypeInfo
	 * @return
	 */
	public int findTListRoomtypeInfo(long roomtypeId) {
		return hmsERoomtypeInfoMapper.findTListByRoomtypeId(roomtypeId);
	}
	
	/**
	 * 添加一条房型信息
	 * @param tRoomtypeInfo
	 * @return
	 */
	public int addTRoomtypeInfo(HmsTRoomtypeInfoModel tRoomtypeInfo) {
		return hmsERoomtypeInfoMapper.addT(tRoomtypeInfo);
	}
	
	/**
	 * 更新一条房型数据
	 * @param tRoomtypeInfo
	 * @return
	 */
	public int updateTRoomtypeInfo(HmsTRoomtypeInfoModel tRoomtypeInfo) {
		return hmsERoomtypeInfoMapper.updateT(tRoomtypeInfo);
	}
	
	/**
	 * 删除一条房型数据
	 * @param tRoomtypeInfo
	 * @return
	 */
	public int deleteTRoomtypeInfo(long roomTypeId) {
		return hmsERoomtypeInfoMapper.deleteT(roomTypeId);
	}
	
	/**
	 * 重置t表可用状态
	 * @param visible 状态
	 * @param id 主键
	 * @return 受影响条数
	 */
	public int resetTHotelVisible(String visible, long id) {
		return hmsHotelMessageMapper.resetTHotelVisible(visible, id);
	}
	
	/**
	 * 重置e表可用状态
	 * @param visible 状态
	 * @param id 主键
	 * @return 受影响条数
	 */
	public int resetEHotelVisible(String visible, long id) {
		return hmsHotelMessageMapper.resetEHotelVisible(visible, id);
	}
	
	/**
	 * 修改t表上下线
	 * @param visible 状态
	 * @param online 在线状态
	 * @param id 主键
	 * @return 受影响条数
	 */
	public int resetTHotelLine(String visible, String online, long id) {
		return hmsHotelMessageMapper.resetTHotelline(visible, online, id);
	}
	
	/**
	 * 修改e表上下线
	 * @param visible 状态
	 * @param online 在线状态
	 * @param id 主键
	 * @return 受影响条数
	 */
	public int resetEHotelLine(String visible,long id) {
		return hmsHotelMessageMapper.resetEHotelline(visible, id);
	}
	
	/**
	 * 获取一组酒店的销售人员信息
	 * @return
	 */
	public List<HmsSaleUserModel> findSaleUserList(String hotelIds){
		return saleUserMapper.finds(hotelIds);
	}
	
	/**
	 * 获取地域规则字典列表
	 * @return
	 */
	public List<HmsBAreaRule> findAreaRule(){
		return hmsBAreaRuleMapper.findAreaRule();
	}
	
	/**
	 * 设置酒店规则
	 * @param hotelId
	 * @param ruleCode
	 * @return
	 */
	public int saveHotelRule(long hotelId,int ruleCode){
		return hmsHotelMessageMapper.saveHotelRule(hotelId,ruleCode);
	}
	/**
	 * 获取酒店规则
	 * @param hotelId
	 * @return ruleCode
	 */
	public int getHotelRule(long hotelId){
		return hmsHotelMessageMapper.getHotelRule(hotelId);
	}
	/**
	 * 设置是否阈值结算
	 * @param hotelId
	 * @param isThreshold
	 * @return
	 */
	public int setHotelThreshold(String isThreshold, Date effectDate,long hotelId){
		return hmsHotelMessageMapper.setHotelThreshold(isThreshold,effectDate,hotelId);
	}
	
	/**
	 * 获取阈值结算方式
	 * @param hotelId
	 * @param isThreshold
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public String getHotelThreshold() throws SessionTimeOutException{
		long hotelId = SessionUtils.getThisHotelId();
		return hmsHotelMessageMapper.getHotelThreshold(hotelId);
	}
	/**
	 * 在公共配置表sy_config中查找是否配置有skey字段值与酒店对应的城市ID相等的记录
	 * @param disId
	 * @return 
	 * skey, ,stype,id
	 */
	public String getCityCode(long disid){
		return hmsHotelMessageMapper.getCityCode(disid);
	}
	/**
	 * 
	 */
	public List<HmsTHotelModel> getValidHotel(){
		return hmsHotelMessageMapper.getValidHotel();
	}
	public int insertHotelRule(long hotelid,int type,String value,String effectdate,Date createdate){
		return hmsHotelMessageMapper.insertHotelRule(hotelid, type, value, effectdate, createdate);
	}
	
	public int updateHotelRuleToA(long hotelid){
		return hmsHotelMessageMapper.updateHotelRuleToA(hotelid);
	}
	
	/**
	 * 获取酒店信息
	 * @param hotelId 酒店主键
	 * @return 酒店信息
	 */
	public EHotelWithBLOBs getEHotelById(long hotelId) {
		return this.geteHotelMapper().selectByPrimaryKey(hotelId);
	}
	
	/**
	 * 获取酒店信息
	 * @param hotelId 酒店主键
	 * @return 酒店信息
	 */
	public THotelWithBLOBs getTHotelById(long hotelId) {
		return this.gettHotelMapper().selectByPrimaryKey(hotelId);
	}
	
	/**
	 * 添加t酒店
	 * @param tHotel 酒店对象
	 * @return 受影响条数
	 */
	public int insertTHotle(THotelWithBLOBs tHotel) {
		return this.gettHotelMapperExt().insert(tHotel);
	}
	
	/**
	 * 修改t酒店
	 * @param tHotel 酒店对象
	 * @return 受影响条数
	 */
	public int updateTHotle(THotelWithBLOBs tHotel) {
		return this.gettHotelMapperExt().updateByPrimaryKeyWithBLOBs(tHotel);
	}
	
	private THotelMapper gettHotelMapper() {
		return tHotelMapper;
	}
	
	private THotelMapperExt gettHotelMapperExt() {
		return tHotelMapperExt;
	}
	
	private EHotelMapper geteHotelMapper() {
		return eHotelMapper;
	}

	public RoomSaleConfigMapper getRoomSaleConfigMapper() {
		return roomSaleConfigMapper;
	}
}
