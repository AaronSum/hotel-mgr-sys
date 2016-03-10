package com.mk.hms.controller.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.mk.hms.mapper.RoomSaleConfigMapper;
import com.mk.hms.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Component;

import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.service.HmsEBasePriceService;
import com.mk.hms.service.HmsERoomTypeFacilityService;
import com.mk.hms.service.HmsERoomTypeInfoService;
import com.mk.hms.service.HmsHOperateLogService;
import com.mk.hms.service.HmsHotelMessageService;
import com.mk.hms.service.HmsTRoomTypeService;
import com.mk.hms.service.RoomSettingService;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsStringUtils;

/**
 * 酒店讯息控制器helper
 * @author hdy
 *
 */
@Component
public class HotelMessageControllerHelper {
	
	/**操作日志service*/
	private static HmsHOperateLogService hmsHOperateLogService;

	/** 酒店门头key*/
	private static final String def = "def";
	
	private static final Logger logger = LoggerFactory.getLogger(HotelMessageControllerHelper.class);
	
	@Autowired
	public void setHOperateLogService(HmsHOperateLogService hmsHOperateLogService) {
		HotelMessageControllerHelper.hmsHOperateLogService = hmsHOperateLogService;
	}

    /**
	 * 过滤商圈信息列表中需要删除和需要保存数据列表
	 * @param deleteIds 需要删除商圈信息id集合
	 * @param addList 需要添加商圈信息列表
	 * @param list 需要操作商圈信息集合
	 * @param myList 数据库中已存在商圈信息
	 */
	public static void filterList(List<Long> deleteIds, List<HmsTBusinesszoneModel> addList, 
			List<JSONObject> list, List<HmsTBusinesszoneModel> myList) {
		if (myList == null) {
			myList = new ArrayList<HmsTBusinesszoneModel>();
		}
		// 获取需要删除商圈信息id列表
		for (HmsTBusinesszoneModel businesszone : myList) {
			boolean isExit = false;
			for (JSONObject obj : list) {
				if (businesszone.getId() == obj.getLong("id")) {
					isExit = true;
				}
			}
			if (!isExit) {
				deleteIds.add(businesszone.getId());
			}
		}
		// 获取需要添加商圈信息列表
		for (JSONObject obj : list) {
			boolean isExit = false;
			for (HmsTBusinesszoneModel businesszone : myList) {
				if (businesszone.getId() == obj.getLong("id")) {
					isExit = true;
					break;
				}
			}
			if (!isExit) {
				HmsTBusinesszoneModel circle = new HmsTBusinesszoneModel();
				circle.setId(obj.getLong("id"));
				circle.setBusinessZoneType(obj.getLong("businessZoneType"));
				circle.setCityid(obj.getLong("cityid"));
				circle.setDis(obj.getLong("dis"));
				circle.setFatherid(obj.getLong("fatherid"));
				circle.setName(obj.getString("name"));
				addList.add(circle);
			}
		}
	}
	/**
     * 将eHotel数据保存到tHotel中
     * @param eHotel 
     * @param hmsHotelMessageService 服务
	 * @throws SessionTimeOutException 
     */
    public static void copyEHotel2THotelold(EHotelWithBLOBs eHotel,
            HmsHotelMessageService hmsHotelMessageService,
            HmsTRoomTypeService hmsTRoomTypeService,
            HmsERoomTypeInfoService hmsRoomTypeInfoService,
            HmsEBasePriceService hmsEBasePriceService,
            HmsERoomTypeFacilityService hmsERoomTypeFacilityService, OutModel out) throws SessionTimeOutException {
        // 同步酒店
        THotelWithBLOBs syncTHotel = new THotelWithBLOBs();
        BeanCopier beanCopierT = BeanCopier.create(EHotelWithBLOBs.class, THotelWithBLOBs.class, false);
        beanCopierT.copy(eHotel, syncTHotel, null);
        syncTHotel.setNeedwait(HmsVisibleEnum.F.getValue());
        syncTHotel.setOnLine(HmsVisibleEnum.T.getValue());
        THotelWithBLOBs tHotel = hmsHotelMessageService.getTHotelById(eHotel.getId());
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [基本] 数据---开始：copy e_hotel to t_hotel begin");
        if (null == tHotel) {
            hmsHotelMessageService.insertTHotle(syncTHotel);
        } else {
            hmsHotelMessageService.updateTHotle(syncTHotel);
        }
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [基本] 数据---结束：copy e_hotel to t_hotel is okay");
        // 添加审核操作日志
        hmsHOperateLogService.addTHotelOperateLog(syncTHotel, eHotel.getState());
        // 同步房型（e_roomtype_info）
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [房型] 数据---开始：copy e_hotel's e_roomtype_info to t_hotel's t_roomtype_info begin");
        List<HmsTRoomtypeModel> hmsTRoomtypeModels = hmsTRoomTypeService.findsByHotelId(eHotel.getId());
        for(HmsTRoomtypeModel hmsTRoomtypeModel : hmsTRoomtypeModels){
            try {
                long roomtypeId = hmsTRoomtypeModel.getId();
                int bedNum = 0; //床的数量
                HmsERoomtypeInfoModel roomtypeInfoModel =  hmsRoomTypeInfoService.findByRoomtypeId(roomtypeId);
                if(roomtypeInfoModel!=null){
                    String bedSize = roomtypeInfoModel.getBedSize();
                    if(StringUtils.isNotEmpty(bedSize)){
                        bedNum = bedSize.split(ContentUtils.CHAR_COMMA).length;
                    }
                    //复制到t_roomtypeInfo表
                    hmsHotelMessageService.deleteTRoomtypeInfo(roomtypeId); //删除原始的roomType数据
                    HmsTRoomtypeInfoModel tRoomtypeInfoModel = new HmsTRoomtypeInfoModel();
                    BeanCopier beanCopierRoomeType = BeanCopier.create(HmsERoomtypeInfoModel.class, HmsTRoomtypeInfoModel.class, false);
                    beanCopierRoomeType.copy(roomtypeInfoModel, tRoomtypeInfoModel, null);
                    hmsHotelMessageService.addTRoomtypeInfo(tRoomtypeInfoModel);                
                }
                
                HmsEBasePriceModel basePriceModel =  hmsEBasePriceService.find(roomtypeId);
                if(basePriceModel!=null){
                    //复制到t_baseprice表
                    HmsTBasePriceModel tBasePriceModel = new HmsTBasePriceModel();
                    BeanCopier beanCopier = BeanCopier.create(HmsEBasePriceModel.class, HmsTBasePriceModel.class, false);
                    beanCopier.copy(basePriceModel, tBasePriceModel, null);
                    if(hmsHotelMessageService.findTBasePrice(roomtypeId)!=null){
                        hmsHotelMessageService.updateTBasePrice(tBasePriceModel);               
                    }else{
                        hmsHotelMessageService.addTBasePrice(tBasePriceModel);
                    }                   
                }           
                List<HmsERoomtypeFacilityModel> roomtypeFacilityModels = hmsERoomTypeFacilityService.findsByRoomtypeId(roomtypeId);
                hmsHotelMessageService.deleteTRoomtypeFacilityByRoomtypeId(roomtypeId); //删除房型下的所有设备信息
                for(HmsERoomtypeFacilityModel roomtypeFacilityModel : roomtypeFacilityModels){
                    //复制到t_roomtype_facility表
                    HmsTRoomtypeFacilityModel tRoomtypeFacilityModel = new HmsTRoomtypeFacilityModel();
                    BeanUtils.copyProperties(tRoomtypeFacilityModel,roomtypeFacilityModel);
                    hmsHotelMessageService.addTRoomtypeFacility(tRoomtypeFacilityModel);
                }
                //TODO update t_roomtype表
                hmsTRoomTypeService.updateBedNum(roomtypeId, bedNum);

            } catch (IllegalAccessException e) {
                HotelMessageControllerHelper.logger.error("酒店审核复制酒店 [房型] 数据异常(IllegalAccessException)：" + e.getMessage(), e);
            } catch (InvocationTargetException e) {
                HotelMessageControllerHelper.logger.error("酒店审核复制酒店 [房型] 数据异常(InvocationTargetException)：" + e.getMessage(), e);
            }
        }
        // 添加酒店设置
        //删除酒店设施信息
        hmsHotelMessageService.deleteTHotelFacilities(eHotel.getId());
        // 获取我的设施列表
        List<HmsEHotelFacilityModel> eHotelFacilityList =hmsHotelMessageService.findEHotelFacilitityList();
        // 添加我的设施列表
        for (HmsEHotelFacilityModel facility : eHotelFacilityList) {
            hmsHotelMessageService.insertTHotelFacilities(eHotel.getId(), facility.getFacId());
        }
        out.setSuccess(true);
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [房型] 数据---结束：copy e_hotel's e_roomtype_info to t_hotel's t_roomtype_info is okay");
    }
    /**
     * 将eHotel数据保存到tHotel中
     * @param eHotel 
     * @param hmsHotelMessageService 服务
     * @throws SessionTimeOutException 
     */
    public static void copyEHotel2THotel(EHotelWithBLOBs eHotel,
            HmsHotelMessageService hmsHotelMessageService,
            HmsTRoomTypeService hmsTRoomTypeService,
            HmsERoomTypeInfoService hmsRoomTypeInfoService,
            HmsEBasePriceService hmsEBasePriceService,
            HmsERoomTypeFacilityService hmsERoomTypeFacilityService,
            RoomSettingService roomSettingService, OutModel out) throws SessionTimeOutException {
        // 同步酒店
        THotelWithBLOBs syncTHotel = new THotelWithBLOBs();
        BeanCopier beanCopierT = BeanCopier.create(EHotelWithBLOBs.class, THotelWithBLOBs.class, false);
        beanCopierT.copy(eHotel, syncTHotel, null);
        syncTHotel.setNeedwait(HmsVisibleEnum.F.getValue());
        syncTHotel.setOnLine(HmsVisibleEnum.T.getValue());
        THotelWithBLOBs tHotel = hmsHotelMessageService.getTHotelById(eHotel.getId());
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [基本] 数据---开始：copy e_hotel to t_hotel begin");
        if (null == tHotel) {
            hmsHotelMessageService.insertTHotle(syncTHotel);
        } else {
            hmsHotelMessageService.updateTHotle(syncTHotel);
        }
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [基本] 数据---结束：copy e_hotel to t_hotel is okay");
        // 添加审核操作日志
        hmsHOperateLogService.addTHotelOperateLog(syncTHotel, eHotel.getState());
        // 同步房型（e_roomtype_info）
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [房型] 数据---开始：copy e_hotel's e_roomtype_info to t_hotel's t_roomtype_info begin");
        List<HmsTRoomtypeModel> hmsTRoomtypeModels = hmsTRoomTypeService.findsByHotelId(eHotel.getId());

        Iterator<HmsTRoomtypeModel> hmsTRoomtypeModelIterator = hmsTRoomtypeModels.iterator();
        Map<Integer,Integer> tjRoomTypeIdMap = new HashMap<Integer, Integer>();
        while (hmsTRoomtypeModelIterator.hasNext()){
            HmsTRoomtypeModel hmsTRoomtypeModel  = hmsTRoomtypeModelIterator.next();
            //将特价房信息排除掉
            TRoomSaleConfig queryRoomSaleConfig = new TRoomSaleConfig();
            queryRoomSaleConfig.setValid("T");
            queryRoomSaleConfig.setSaleRoomTypeId((int) hmsTRoomtypeModel.getId());
            TRoomSaleConfig tRoomSaleConfig = hmsHotelMessageService.getRoomSaleConfigMapper().queryRoomSaleConfig(queryRoomSaleConfig);
            if(tRoomSaleConfig == null || tRoomSaleConfig.getId() == null || tRoomSaleConfig.getSaleRoomTypeId() == null){
                continue;
            }else {
                //排除主题酒店
                TRoomSaleConfig queryRoomSaleConfigInfo = new TRoomSaleConfig();
                queryRoomSaleConfigInfo.setId(tRoomSaleConfig.getSaleConfigInfoId());
                TRoomSaleConfig roomSaleConfigInfo = hmsHotelMessageService.getRoomSaleConfigMapper().queryRoomSaleConfigInfo(queryRoomSaleConfigInfo);
                if(roomSaleConfigInfo!= null && 3 == roomSaleConfigInfo.getSaleTypeId()){
                    continue;
                }else{
                    tjRoomTypeIdMap.put(tRoomSaleConfig.getSaleRoomTypeId(), tRoomSaleConfig.getSaleRoomTypeId());
                }
            }
        }
        removeTjRoomTypeId(hmsTRoomtypeModels, tjRoomTypeIdMap);
        List<HmsERoomtypeInfoModel> roomtypeInfoModels = hmsRoomTypeInfoService.findByRoomtypeIds(hmsTRoomtypeModels);//hmsRoomTypeInfoService.findByRoomtypeId(roomtypeId);
        Map<Long,HmsERoomtypeInfoModel> rtinfoMdMap = new HashMap<Long,HmsERoomtypeInfoModel>();
        for(HmsERoomtypeInfoModel model:roomtypeInfoModels){
        	rtinfoMdMap.put(model.getRoomTypeId(), model);
        }
        if(hmsTRoomtypeModels.size()>0){
        	hmsHotelMessageService.deleteTRoomtypeInfo(hmsTRoomtypeModels); //删除原始的roomType数据
        }
        
        List<HmsTRoomtypeInfoModel> tRoomtypeInfoModels = new ArrayList<HmsTRoomtypeInfoModel>();
        
        List<HmsEBasePriceModel> basePriceModels =  hmsEBasePriceService.findTRoomTypeModels(hmsTRoomtypeModels);//find
        Map<Long,HmsEBasePriceModel> bpModelsMap = new HashMap<Long,HmsEBasePriceModel>();
        for(HmsEBasePriceModel model:basePriceModels){
        	bpModelsMap.put(model.getRoomTypeId(), model);
        }
//        update t_roomtype表
        hmsTRoomTypeService.updateBedNum(roomtypeInfoModels);
        List<HmsTBasePriceModel> updateList = new ArrayList<HmsTBasePriceModel>();
        List<HmsTBasePriceModel> addList = new ArrayList<HmsTBasePriceModel>();
        List<HmsTBasePriceModel> tbpModels = hmsHotelMessageService.findAllTBasePriceByIds(hmsTRoomtypeModels);
        Map<Long,HmsTBasePriceModel> tbpModelsMap = new HashMap<Long,HmsTBasePriceModel>();
        for(HmsTBasePriceModel model:tbpModels){
        	tbpModelsMap.put(model.getRoomTypeId(), model);
        }
        for(HmsTRoomtypeModel hmsTRoomtypeModel : hmsTRoomtypeModels){
//            try {
                long roomtypeId = hmsTRoomtypeModel.getId();
//                int bedNum = 0; //床的数量
                HmsERoomtypeInfoModel roomtypeInfoModel =  rtinfoMdMap.get(roomtypeId);//hmsRoomTypeInfoService.findByRoomtypeId(roomtypeId);
                if(roomtypeInfoModel!=null){
//                    String bedSize = roomtypeInfoModel.getBedSize();
//                    if(StringUtils.isNotEmpty(bedSize)){
//                        bedNum = bedSize.split(ContentUtils.CHAR_COMMA).length;
//                    }
                    //下面的删除放到for循环外
//                    hmsHotelMessageService.deleteTRoomtypeInfo(roomtypeId); //删除原始的roomType数据
                    HmsTRoomtypeInfoModel tRoomtypeInfoModel = new HmsTRoomtypeInfoModel();
                    BeanCopier beanCopierRoomeType = BeanCopier.create(HmsERoomtypeInfoModel.class, HmsTRoomtypeInfoModel.class, false);
                    beanCopierRoomeType.copy(roomtypeInfoModel, tRoomtypeInfoModel, null);
                    //下面的增加放到for循环外，批量增加
//                    hmsHotelMessageService.addTRoomtypeInfo(tRoomtypeInfoModel); 
                    tRoomtypeInfoModels.add(tRoomtypeInfoModel);
                }
                //放for循环外，一次性取出，构成map类型，按roomtypeid获取
                HmsEBasePriceModel basePriceModel =  bpModelsMap.get(roomtypeId);//hmsEBasePriceService.find(roomtypeId);
                if(basePriceModel!=null){
                    //复制到t_baseprice表
                    HmsTBasePriceModel tBasePriceModel = new HmsTBasePriceModel();
                    BeanCopier beanCopier = BeanCopier.create(HmsEBasePriceModel.class, HmsTBasePriceModel.class, false);
                    beanCopier.copy(basePriceModel, tBasePriceModel, null);
                    HmsTBasePriceModel tbasePriceModel = tbpModelsMap.get(roomtypeId);
                    if(tbasePriceModel == null){
                    	addList.add(tBasePriceModel);//增加到t_baseprice表
                    }else{
                    	updateList.add(tBasePriceModel);//存在，则更新t_baseprice表
                    }
//                    if(hmsHotelMessageService.findTBasePrice(roomtypeId)!=null){
//                        hmsHotelMessageService.updateTBasePrice(tBasePriceModel);               
//                    }else{
//                        hmsHotelMessageService.addTBasePrice(tBasePriceModel);
//                    }                   
                }  
        }
       // List<HmsERoomtypeFacilityModel> roomtypeFacilityModels = hmsERoomTypeFacilityService.findsByRoomtypeId(roomtypeId);
        //全部查询出来，之前根据roomtypeId，for循环中
        List<HmsERoomtypeFacilityModel> roomtypeFacilityModels = hmsERoomTypeFacilityService.findRoomtypeFacilityById(hmsTRoomtypeModels);
        //之前for循环中逐条删除，下面改为批量删除
        //        hmsHotelMessageService.deleteTRoomtypeFacilityByRoomtypeId(roomtypeId); //删除房型下的所有设备信息
        if(hmsTRoomtypeModels.size()>0){
        	hmsHotelMessageService.deleteTRoomtypeInfo(hmsTRoomtypeModels);
        }
        List<HmsTRoomtypeFacilityModel> hmsTRoomTypeFacilityModels = new ArrayList<HmsTRoomtypeFacilityModel>();
        for(HmsERoomtypeFacilityModel roomtypeFacilityModel : roomtypeFacilityModels){
            //复制到t_roomtype_facility表
            HmsTRoomtypeFacilityModel tRoomtypeFacilityModel = new HmsTRoomtypeFacilityModel();
            try {
				BeanUtils.copyProperties(tRoomtypeFacilityModel,roomtypeFacilityModel);
				hmsTRoomTypeFacilityModels.add(tRoomtypeFacilityModel);
            } catch (IllegalAccessException e) {
                HotelMessageControllerHelper.logger.error("酒店审核复制酒店 [房型] 数据异常(IllegalAccessException)：" + e.getMessage(), e);
            } catch (InvocationTargetException e) {
                HotelMessageControllerHelper.logger.error("酒店审核复制酒店 [房型] 数据异常(InvocationTargetException)：" + e.getMessage(), e);
            }
        }
        //批量增加
        if(hmsTRoomTypeFacilityModels.size()>0){
        	hmsHotelMessageService.addTRoomtypeFacilitys(hmsTRoomTypeFacilityModels);
        }
        if(updateList.size()>0){
        	hmsHotelMessageService.updateTPrices(updateList);//edit
        }
        if(addList.size()>0){
        	hmsHotelMessageService.addTBasePrices(addList);
        }
        //批量添加
        if(tRoomtypeInfoModels.size()>0){
        	hmsHotelMessageService.addTRoomtypeInfos(tRoomtypeInfoModels);
        }
        // 添加酒店设置
        //删除酒店设施信息
        hmsHotelMessageService.deleteTHotelFacilities(eHotel.getId());
        // 获取我的设施列表
        List<HmsEHotelFacilityModel> eHotelFacilityList =hmsHotelMessageService.findEHotelFacilitityList();
        // 添加我的设施列表
//        for (HmsEHotelFacilityModel facility : eHotelFacilityList) {
//            hmsHotelMessageService.insertTHotelFacilities(eHotel.getId(), facility.getFacId());
//        }
        Map<Object,Object> map = new HashMap<Object,Object>();
        List<Long> list = new ArrayList<Long>();
        for(HmsEHotelFacilityModel facility : eHotelFacilityList){
        	list.add(facility.getFacId());
        }
        if(list.size()>0){
	        map.put("facIds", list);
	    	map.put("eHotelId", eHotel.getId());
	    	hmsHotelMessageService.insertTHotelFacilities(map);
        }
        out.setSuccess(true);
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [房型] 数据---结束：copy e_hotel's e_roomtype_info to t_hotel's t_roomtype_info is okay");

        //将e_room_setting同步至t_room_setting
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [房间] 数据---开始：copy e_room_setting to t_room_setting begin");
        roomSettingService.copyERoomSetting2TRoomSetting();
        HotelMessageControllerHelper.logger.info("酒店审核复制酒店 [房间] 数据---结束：copy e_room_setting to t_room_setting is okay");
    }

    private static void removeTjRoomTypeId(List<HmsTRoomtypeModel> hmsTRoomtypeModels, Map<Integer,Integer> tjRoomTypeIdMap) {
        Iterator<HmsTRoomtypeModel> hmsTRoomtypeModelIterator = hmsTRoomtypeModels.iterator();
        while (hmsTRoomtypeModelIterator.hasNext()) {
            HmsTRoomtypeModel hmsTRoomtypeModel = hmsTRoomtypeModelIterator.next();
            if(tjRoomTypeIdMap.get((int)hmsTRoomtypeModel.getId()) != null){
                hmsTRoomtypeModelIterator.remove();
            }
        }
    }


    /**
	 * 检测酒店必填项
	 * @param eHotel 酒店对象
	 * @param out 状态
	 */
	public static void checkEhotelRequiredItems(EHotelWithBLOBs eHotel, HmsERoomTypeInfoService hmsRoomTypeInfoService, OutModel out) {
		// 酒店名称
		if (HmsStringUtils.isBlank(eHotel.getHotelName())) {
			out.setErrorMsg("请输入酒店名称，并点击页面[保存]按钮");
			return;
		}
		// 联系电话
		if (HmsStringUtils.isBlank(eHotel.getHotelphone())) {
			out.setErrorMsg("请输入酒店联系电话，并点击页面[保存]按钮");
			return;
		}
		// 联系电话
		if (HmsStringUtils.isBlank(eHotel.getDetailAddr())) {
			out.setErrorMsg("请输入酒店详细信息，并点击页面[保存]按钮");
			return;
		}
		// 开业时间
		if (null == eHotel.getOpenTime()) {
			out.setErrorMsg("请输入酒店开业时间，并点击页面[保存]按钮");
			return;
		}
		// 最近装修时间
		if (null == eHotel.getRepairTime()) {
			out.setErrorMsg("请输入酒店最近装修时间，并点击页面[保存]按钮");
			return;
		}
		// 最晚保留时间
		if (null == eHotel.getRetentionTime()) {
			out.setErrorMsg("请输入酒店最晚保留时间，并点击页面[保存]按钮");
			return;
		}
		// 默认离店时间
		if (null == eHotel.getDefaultLeaveTime()) {
			out.setErrorMsg("请输入酒店默认离店时间，并点击页面[保存]按钮");
			return;
		}
		// 门头照片
		String hotelPic = eHotel.getHotelpic();
		if (HmsStringUtils.isBlank(hotelPic)) {
			out.setErrorMsg("请上传酒店门头图片，并点击页面[保存]按钮");
			return;
		}
		List<JSONObject> hotelPicList = JSONArray.toList(JSONArray.fromObject(hotelPic), JSONObject.class);
		for (JSONObject obj : hotelPicList) {
			if (obj.getString("name").equals(def)) {
				JSONArray defPics = obj.getJSONArray("pic");
				if (defPics.size() <= 0) {
					out.setErrorMsg("请上传酒店门头图片，并点击页面[保存]按钮");
					return;
				}
			}
		}
		// 房型管理
		List<HmsERoomtypeInfoModel> roomtypeList = hmsRoomTypeInfoService.findsByHotelId(eHotel.getId());
		if (null == roomtypeList || roomtypeList.size() <= 0) {
            out.setErrorMsg("此酒店没有房型信息");
            return;
        }
        for (HmsERoomtypeInfoModel roomTye : roomtypeList) {
            //判断特价房类型
            if(hmsRoomTypeInfoService.checkPromoRoomType(roomTye.getRoomTypeId())){
                continue;
            }
			if (roomTye.getBedType() < 0) {
				out.setErrorMsg("请完善酒店房型管理中床型设置，并点击页面[保存]按钮");
				return;
			}
			String pics = roomTye.getPics();
			if (HmsStringUtils.isBlank(pics)) {
				out.setErrorMsg("请上传酒店房型管理中主展图片，并点击页面[保存]按钮");
				return;
			}
			List<JSONObject> picList = JSONArray.toList(JSONArray.fromObject(pics), JSONObject.class);
			for (JSONObject obj : picList) {
				if (obj.getString("name").equals(def)) {
					JSONArray defPics = obj.getJSONArray("pic");
					if (defPics.size() <= 0) {
						out.setErrorMsg("请上传酒店房型管理中主展图片，并点击页面[保存]按钮");
						return;
					}
				}
			}
		}
		out.setSuccess(true);
	}
}
