package com.mk.hms.service;

import java.util.ArrayList;
import java.util.List;

import com.mk.hms.mapper.RoomTypeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.HmsERoomtypeInfoMapper;
import com.mk.hms.mapper.HmsTRoomtypeMapper;
import com.mk.hms.mapper.HotelAuditSynToTHotelMapper;
import com.mk.hms.model.HmsERoomtypeInfoModel;
import com.mk.hms.model.HmsHOperateLogModel;
import com.mk.hms.model.HmsTRoomtypeModel;

/**
 * 房型服务类
 * Created by qhdhiman on 2015/5/3.
 */
@Service
@Transactional
public class HmsERoomTypeInfoService {
	
	@Autowired
	private HmsHOperateLogService hmsHOperateLogService;
	
	@Autowired
    private HmsERoomtypeInfoMapper hmsERoomtypeInfoMapper;
	
	@Autowired
	private RoomTypeMapper roomTypeMapper;
	
	@Autowired
	private HotelAuditSynToTHotelMapper mapper;
	@Autowired
	private HmsTRoomtypeMapper hmsTRoomtypeMapper;

	public List<HmsERoomtypeInfoModel> findByRoomtypeIds(List<HmsTRoomtypeModel> hmsTRoomtypeModels){
		return mapper.findRoomtypeByIds(hmsTRoomtypeModels);
	}

    /**
     * 获取所有房型
     * @return 房型列表
     */
    public List<HmsERoomtypeInfoModel> finds(){
        return hmsERoomtypeInfoMapper.finds();
    }

    /**
     * 根据房型Id获取房型信息
     * @param roomtypeId 房型Id
     * @return 房型信息列表
     */
    public HmsERoomtypeInfoModel findByRoomtypeId(long roomtypeId){
        return hmsERoomtypeInfoMapper.findByRoomtypeId(roomtypeId);
    }
    
    /**
     * 增加新的房型信息
     * @param eRoomtypeInfo
     * @return
     * @throws SessionTimeOutException 
     */
    public int add(HmsERoomtypeInfoModel eRoomtypeInfo) throws SessionTimeOutException{
    	int result = hmsERoomtypeInfoMapper.add(eRoomtypeInfo);
    	if(result>0){
    		// 添加操作日志信息
    		HmsHOperateLogModel log = new HmsHOperateLogModel("e_roomtype_info", "save","首次设置房型信息("+eRoomtypeInfo.getRoomTypeId()+")" );
    		hmsHOperateLogService.addLog(log);    		
    	}		
		return result;
    }
    
    /**
     * 更新房型信息
     * @param eRoomtypeInfo
     * @return
     * @throws SessionTimeOutException 
     */
    public int update(HmsERoomtypeInfoModel eRoomtypeInfo) throws SessionTimeOutException{
    	int result = hmsERoomtypeInfoMapper.update(eRoomtypeInfo);
    	if(result>0){
    		// 添加操作日志信息
    		HmsHOperateLogModel log = new HmsHOperateLogModel("e_roomtype_info", "update","修改房型信息("+eRoomtypeInfo.getRoomTypeId()+")" );
    		hmsHOperateLogService.addLog(log);    		
    	}		
		return result;
    }
    /**
     * 根据酒店Id获取 房型数据
     * @param hotelId
     * @return
     */
    public List<HmsERoomtypeInfoModel> findsByHotelId(long hotelId){
    	List<HmsERoomtypeInfoModel> eRoomtypeInfoModels = new ArrayList<HmsERoomtypeInfoModel>();
    	List<HmsTRoomtypeModel> tRoomtypeModels = hmsTRoomtypeMapper.findsByHotelId(hotelId);
    	for(HmsTRoomtypeModel tRoomtypeModel : tRoomtypeModels){
    		HmsERoomtypeInfoModel  eRoomtypeInfoModel = hmsERoomtypeInfoMapper.findByRoomtypeId(tRoomtypeModel.getId());
    		if(eRoomtypeInfoModel!=null){
    			eRoomtypeInfoModels.add(eRoomtypeInfoModel);
    		}
    	}
    	return eRoomtypeInfoModels;
    }

	public Boolean checkPromoRoomType(Long roomTypeId){
		Long num = roomTypeMapper.getSaleConfigByRoomTypeId(roomTypeId);
		if(num == null){
			return false;
		}
		if(num > 0){
			return true;
		}else {
			return false;
		}
	}
}
