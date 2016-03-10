package com.mk.hms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.mk.hms.enums.HmsTFacilityBindingEnum;
import com.mk.hms.enums.HmsTfacilityTypeEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.EBasePriceMapper;
import com.mk.hms.mapper.ERoomtypeFacilityMapper;
import com.mk.hms.mapper.ERoomtypeInfoMapper;
import com.mk.hms.mapper.FacilityMapper;
import com.mk.hms.mapper.OperateLogMapper;
import com.mk.hms.mapper.RoomTypeMapper;
import com.mk.hms.model.EBasePrice;
import com.mk.hms.model.EBasePriceCriteria;
import com.mk.hms.model.ERoomtypeFacility;
import com.mk.hms.model.ERoomtypeFacilityCriteria;
import com.mk.hms.model.ERoomtypeInfo;
import com.mk.hms.model.ERoomtypeInfoCriteria;
import com.mk.hms.model.Facility;
import com.mk.hms.model.FacilityCriteria;
import com.mk.hms.model.MUser;
import com.mk.hms.model.OperateLog;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.RoomType;
import com.mk.hms.model.RoomTypeCriteria;
import com.mk.hms.model.User;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.LogUtils;
import com.mk.hms.utils.RequestUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.ERoomtypeInfoWithFacility;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.SaveRoomType;

/**
 * 房型 service
 * @author hdy
 *
 */
@Service
@Transactional
public class RoomTypeService {

	private static final Logger logger = LoggerFactory.getLogger(RoomTypeService.class);
	
	private final String roomBing = HmsTFacilityBindingEnum.Room.getValue();
	private final String visible = HmsVisibleEnum.T.getValue();
	private final int normal = HmsTfacilityTypeEnum.normal.getValue();
	private final int other = HmsTfacilityTypeEnum.other.getValue();
	private final int bathroom = HmsTfacilityTypeEnum.Bathroom.getValue();
			
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;
	
	@Autowired
	private ERoomtypeInfoMapper eRoomtypeInfoMapper = null;
	
	@Autowired
	private  ERoomtypeFacilityMapper eRoomtypeFacilityMapper = null;
	
	@Autowired
	private FacilityMapper facilityMapper = null;
	
	@Autowired
	private EBasePriceMapper eBasePriceMapper = null;
	
	@Autowired
	private OperateLogMapper operateLogMapper = null;
	
	/**
	 * 获取房型列表
	 * @return
	 */
	public List<RoomType> finds() {
		return this.getRoomTypeMapper().selectByExample(null);
	}

	/**
     * 根据酒店Id获取房型
     * @return 房型列表
	 * @throws SessionTimeOutException 
     */
	public OutModel save(SaveRoomType srt) throws SessionTimeOutException {
		ERoomtypeInfo eRoomtypeInfo = new ERoomtypeInfo();
        eRoomtypeInfo.setMaxArea(srt.getMaxArea());
        eRoomtypeInfo.setBedType(srt.getBedType());
        eRoomtypeInfo.setBedSize(srt.getBedSize());
        String pic = srt.getPics();
        if(!HmsStringUtils.isEmpty(pic)){
        	pic = HmsStringUtils.removeStart(pic, ContentUtils.CHAR_QUOTES);
        	pic = HmsStringUtils.removeEnd(pic, ContentUtils.CHAR_QUOTES);
            eRoomtypeInfo.setPics(pic);
        }
        pic.trim();
        long roomTypeId = srt.getRoomTypeId();
        eRoomtypeInfo.setRoomTypeId(roomTypeId);
		if (null != this.findByRoomtypeId(roomTypeId)) {
			// update
			this.updateERoomtypeInfo(eRoomtypeInfo);
		} else {
			// save
			this.addERoomtypeInfo(eRoomtypeInfo);
		}
        List<Long> addList = new ArrayList<Long>();
        String addFacs = srt.getAddFacs();
		if (!HmsStringUtils.isEmpty(addFacs)) {
			// 防止添加数据重复
			ERoomtypeFacilityCriteria example = new ERoomtypeFacilityCriteria();
			example.createCriteria().andRoomTypeIdEqualTo(roomTypeId);
			List<ERoomtypeFacility> roomtypeFacilityList = this.geteRoomtypeFacilityMapper().selectByExample(example);
			String[] addFacsArray = addFacs.split(ContentUtils.CHAR_COMMA);
			for (String s : addFacsArray) {
				boolean isExist = false;
				for (ERoomtypeFacility f : roomtypeFacilityList) {
					if ((f.getFacId().longValue() + "").equals(s)) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					addList.add(NumberUtils.toLong(s));
				}
			}
			this.addTRoomTypeFacility(addList, roomTypeId);
		}    	
        //删除设备
        List<Long> delList = new ArrayList<Long>();
        String minusFacs = srt.getMinusFacs();
        if(!HmsStringUtils.isEmpty(minusFacs)){
	        for(String facIdStr : minusFacs.split(ContentUtils.CHAR_COMMA)){
	        	long facId = Long.parseLong(facIdStr);
	        	delList.add(facId);
	        }
	        this.delTRoomTypeFacility(delList, roomTypeId);
        }
        LogUtils.logStep(logger, "结束请求");
        return new OutModel();
	}
	
	 /**
     * 根据酒店Id获取房型
     * @return 房型列表
     */
	public List<RoomType> findsByHotelId(long hotelId){
		RoomTypeCriteria example = new RoomTypeCriteria();
		example.createCriteria().andEhotelIdEqualTo(hotelId);
        return this.getRoomTypeMapper().selectByExample(example);
    }
	
	/**
     * 根据房型Id获取房型信息
     * @param roomtypeId 房型Id
     * @return 房型信息
     */
    public ERoomtypeInfoWithFacility findERoomtypeInfoByRoomtypeId(long roomTypeId){
    	ERoomtypeInfo roomTypeInfo =  this.findByRoomtypeId(roomTypeId);
    	ERoomtypeInfoWithFacility riwf = new ERoomtypeInfoWithFacility();
        BeanCopier beanCopier = BeanCopier.create(ERoomtypeInfo.class, ERoomtypeInfoWithFacility.class, false);
        beanCopier.copy(roomTypeInfo, riwf, null);
		if (riwf != null) {
			riwf.setFacilities(this.findRoomTypeFacilityByRoomTypeId(roomTypeId));
		}
        return riwf;
    }
    
    /**
     * 获取所有房型设备设施
     * @return 设备设施列表
     */
    public Map<String,List<Facility>> findFacilities(){
    	Map<String,List<Facility>> rtnMap = new HashMap<String,List<Facility>>();
    	rtnMap.put("normal", this.findsRoomTypeNormalFacilities());
    	rtnMap.put("other", this.findsRoomTypeOtherFacilities());
    	rtnMap.put("bathroom", this.findsRoomTypeBathroomFacilities());
    	return rtnMap;
    }
    
    /**
	 * 根据房型ID获取房价
	 * @param roomTypeId
     */
    public EBasePrice findEBasePriceByRoomtypeId (long roomTypeId) {
    	EBasePriceCriteria example = new EBasePriceCriteria();
    	example.createCriteria().andRoomTypeIdEqualTo(roomTypeId);
    	List<EBasePrice> list = this.geteBasePriceMapper().selectByExample(example);
    	return list.size() > 0 ? list.get(0) : null;
    }
    
    
    /**
	 * 根据房型ID获取房价
     * @throws SessionTimeOutException 
     */
    public OutModel saveBasePrice(long roomTypeId, BigDecimal value, String type) throws SessionTimeOutException{
        EBasePrice priceModel = new EBasePrice();
		if (type.equals("price")) {
			priceModel.setPrice(value);
		} else if (type.equals("subper")) {
			priceModel.setSubper(value);
		} else if (type.equals("subprice")) {
			priceModel.setSubprice(value);
		}
        int result = 0;
        OutModel outModle = new OutModel();
        priceModel.setRoomTypeId(roomTypeId);
    	result = this.saveEBasePrice(priceModel);
		if (result > 0) {
			outModle.setSuccess(true);
		} else {
			outModle.setSuccess(false);
		}
        return outModle;
    }
    
    /**
	 * 根据房型ID获取房价
     * @throws SessionTimeOutException 
     */
    private int saveEBasePrice (EBasePrice priceModel) throws SessionTimeOutException {
    	EBasePriceCriteria example = new EBasePriceCriteria();
    	long roomTypeId = priceModel.getRoomTypeId();
    	example.createCriteria().andRoomTypeIdEqualTo(roomTypeId);
    	List<EBasePrice> list = this.geteBasePriceMapper().selectByExample(example);
    	priceModel.setUpdateTime(new Date());
    	int result = 0;
    	OperateLog log = new OperateLog();
    	log.setTatablename("e_baseprice");
    	// 修改
    	if (list.size() > 0) {
    		EBasePriceCriteria update = new EBasePriceCriteria();
    		update.createCriteria().andRoomTypeIdEqualTo(roomTypeId);
    		List<EBasePrice> infoList = this.geteBasePriceMapper().selectByExample(update);
    		EBasePrice info = infoList.size() > 0 ? infoList.get(0) : null;
    		if (null != info) {
    			priceModel.setId(info.getId());
    			result = this.geteBasePriceMapper().updateByPrimaryKey(priceModel);
    		} else {
    			result = this.geteBasePriceMapper().updateByExample(priceModel, update);
    		}
    		if(result > 0){
    			// 添加操作日志信息
        		log.setFunctioncode("update");
        		log.setFunctionname("更改眯客基础房价(" + roomTypeId + ")");
    		}
    	// 新增	
    	}  else {
    		result =  this.geteBasePriceMapper().insert(priceModel);
			if (result > 0) {
				// 添加操作日志信息
				log.setFunctioncode("add");
				log.setFunctionname("首次设置眯客基础房价(" + roomTypeId + ")");
			}
    	}
    	this.addLog(log);
    	return result;
    }
    
    /**
	 * 获取设备设备
	 * @param binding 匹配类型
	 * @param visible 是否可用标示，T－可用，F－不可用
	 * @param facType 1常见设置、2其他设置
	 * @return 酒店设置列表
	 */
    private List<Facility> findsRoomTypeNormalFacilities() {
    	FacilityCriteria example = new FacilityCriteria();
    	example.setOrderByClause("facType asc, facSort asc");
    	example.createCriteria().andBindingEqualTo(roomBing).andVisibleEqualTo(visible).andFacTypeEqualTo(normal);
    	return this.getFacilityMapper().selectByExample(example);
    }
    
    /**
	 * 获取设备设备
	 * @param binding 匹配类型
	 * @param visible 是否可用标示，T－可用，F－不可用
	 * @param facType 1常见设置、2其他设置
	 * @return 酒店设置列表
	 */
    private List<Facility> findsRoomTypeOtherFacilities() {
    	FacilityCriteria example = new FacilityCriteria();
    	example.setOrderByClause("facType asc, facSort asc");
    	example.createCriteria().andBindingEqualTo(roomBing).andVisibleEqualTo(visible).andFacTypeEqualTo(other);
    	return this.getFacilityMapper().selectByExample(example);
    }
    
    /**
	 * 获取卫浴设备设备
	 * @param binding 匹配类型
	 * @param visible 是否可用标示，T－可用，F－不可用
	 * @param facType 1常见设置、2其他设置
	 * @return 酒店设置列表
	 */
    private List<Facility> findsRoomTypeBathroomFacilities() {
    	FacilityCriteria example = new FacilityCriteria();
    	example.setOrderByClause("facType asc, facSort asc");
    	example.createCriteria().andBindingEqualTo(roomBing).andVisibleEqualTo(visible).andFacTypeEqualTo(bathroom);
    	return this.getFacilityMapper().selectByExample(example);
    }
    
    /**
     * 根据房型id 获取设备信息
     * @param roomTypeId 房型id
     * @return 设备信息
     */
    private List<ERoomtypeFacility> findRoomTypeFacilityByRoomTypeId (long roomTypeId) {
    	ERoomtypeFacilityCriteria example = new ERoomtypeFacilityCriteria();
    	example.createCriteria().andRoomTypeIdEqualTo(roomTypeId);
    	return this.geteRoomtypeFacilityMapper().selectByExample(example);
    }
    
	/**
	 * 根据主键获取房型信息
	 * @param roomTypeInfoId
	 * @return
	 */
	private ERoomtypeInfo findByRoomtypeId (long roomTypeInfoId){
		ERoomtypeInfoCriteria eRoomtypeInfoCriteria = new ERoomtypeInfoCriteria();
		eRoomtypeInfoCriteria.createCriteria().andRoomTypeIdEqualTo(roomTypeInfoId);
		List<ERoomtypeInfo> list = this.geteRoomtypeInfoMapper().selectByExampleWithBLOBs(eRoomtypeInfoCriteria);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
     * 更新房型信息
     * @param eRoomtypeInfo
     * @return
	 * @throws SessionTimeOutException 
     */
    private int updateERoomtypeInfo(ERoomtypeInfo eRoomtypeInfo) throws SessionTimeOutException{
    	ERoomtypeInfoCriteria eRoomtypeInfoCriteria = new ERoomtypeInfoCriteria();
    	eRoomtypeInfoCriteria.createCriteria().andRoomTypeIdEqualTo(eRoomtypeInfo.getRoomTypeId());
    	List<ERoomtypeInfo> list = this.geteRoomtypeInfoMapper().selectByExample(eRoomtypeInfoCriteria);
    	ERoomtypeInfo info = list.size() > 0 ? list.get(0) : null;
    	int result = 0;
    	if (null != info) {
    		eRoomtypeInfo.setId(info.getId());
    		result = this.geteRoomtypeInfoMapper().updateByPrimaryKeyWithBLOBs(eRoomtypeInfo);
    	} else {
    		result = this.geteRoomtypeInfoMapper().updateByExampleWithBLOBs(eRoomtypeInfo, eRoomtypeInfoCriteria);
    	}
    	if(result > 0){
    		// 添加操作日志信息
    		OperateLog log = new OperateLog();
    		log.setTatablename("e_roomtype_info");
    		log.setFunctioncode("update");
    		log.setFunctionname("修改房型信息("+eRoomtypeInfo.getRoomTypeId()+")");
    		this.addLog(log);    		
    	}		
		return result;
    }
    
    /**
     * 增加新的房型信息
     * @param eRoomtypeInfo
     * @return
     * @throws SessionTimeOutException 
     */
    private int addERoomtypeInfo(ERoomtypeInfo eRoomtypeInfo) throws SessionTimeOutException{
    	int result = this.geteRoomtypeInfoMapper().insert(eRoomtypeInfo);
		if (result > 0) {
			// 添加操作日志信息
    		OperateLog log = new OperateLog();
    		log.setTatablename("e_roomtype_info");
    		log.setFunctioncode("save");
    		log.setFunctionname("首次设置房型信息(" + eRoomtypeInfo.getRoomTypeId() + ")");
    		this.addLog(log);
		}		
		return result;
    }
    
    /**
     * 添加房型设施
     * @param ttf 房型设施
     * @return 受影响条数
     */
    private void addTRoomTypeFacility(List<Long> facIds, long roomTypeId) {
    	for (long l : facIds) {
    		ERoomtypeFacility rtf = new ERoomtypeFacility();
    		rtf.setFacId(l);
    		rtf.setRoomTypeId(roomTypeId);
    		this.geteRoomtypeFacilityMapper().insert(rtf);
    	}
    }
    
    /**
     * 删除设备信息
     * @param facIds 设备信息id
     */
    private void delTRoomTypeFacility(List<Long> facIds, long roomTypeId) {
    	if (facIds.size() > 0) {
    		ERoomtypeFacilityCriteria example = new ERoomtypeFacilityCriteria();
        	example.createCriteria().andRoomTypeIdEqualTo(roomTypeId).andFacIdIn(facIds);
        	this.geteRoomtypeFacilityMapper().deleteByExample(example);
    	}
    }
    /**
	 * 添加操作日志
	 * @param log 日志对象
	 * @return 添加之后有主键日志对象
     * @throws SessionTimeOutException 
	 */
	private OperateLog addLog(OperateLog log) throws SessionTimeOutException {
		// 获取当前登录用户
		LoginUser loginUser;
		try {
			loginUser = SessionUtils.getSessionLoginUser();
			User user = loginUser.getUser();
			if (null == user) {
				return null;
			}
			log.setUsercode(user.getLoginname());
			log.setUsername(user.getName());
			log.setUsertype(ContentUtils.HMS);
		} catch (SessionTimeOutException e) {
			MUser pmsUser = SessionUtils.getSessionPmsUser();
			// pms用户也不存在（没有登录）
			if (null == pmsUser) {
				return null;
			}
			log.setUsercode(pmsUser.getLoginname());
			log.setUsername(pmsUser.getName());
			log.setUsertype(ContentUtils.PMS);
		}
		log.setOperatetime(new Date());
		log.setIp(RequestUtils.getIp());
		this.getOperateLogMapper().insert(log);
		return log;
	}
	
	private RoomTypeMapper getRoomTypeMapper() {
		return roomTypeMapper;
	}

	private ERoomtypeInfoMapper geteRoomtypeInfoMapper() {
		return eRoomtypeInfoMapper;
	}

	private ERoomtypeFacilityMapper geteRoomtypeFacilityMapper() {
		return eRoomtypeFacilityMapper;
	}

	private FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}

	private EBasePriceMapper geteBasePriceMapper() {
		return eBasePriceMapper;
	}

	private OperateLogMapper getOperateLogMapper() {
		return operateLogMapper;
	}
}
