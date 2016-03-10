package com.mk.hms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.mapper.HmsERoomtypeFacilityMapper;
import com.mk.hms.mapper.HotelAuditSynToTHotelMapper;
import com.mk.hms.model.HmsERoomtypeFacilityModel;
import com.mk.hms.model.HmsTRoomtypeModel;

/**
 * 房型设施设备服务类
 * Created by qhdhiman on 2015/5/3.
 */
@Service
@Transactional
public class HmsERoomTypeFacilityService {
	
	@Autowired
    private HmsERoomtypeFacilityMapper hmsERoomtypeFacilityMapper;
    
	//begin
	@Autowired
	private HotelAuditSynToTHotelMapper mapper;
	
	public int add(Map<Object,Object> map){
		return mapper.add(map);
	}
	public int delete(Map<Object,Object> map){
		return mapper.delete(map);
	}
	public List<HmsERoomtypeFacilityModel> findRoomtypeFacilityById(List<HmsTRoomtypeModel> hmsTRoomtypeModels){
		return mapper.findRoomtypeFacilityById(hmsTRoomtypeModels);
	}
	public int deleteTByRoomtypeId(List<HmsTRoomtypeModel> hmsTRoomtypeModels){
		return mapper.deleteTByRoomtypeId(hmsTRoomtypeModels);
	}
	//end
    /**
     * 根据房型编码获取设备设置列表
     * @param roomTypeId
     * @return
     */
    public List<HmsERoomtypeFacilityModel> findsByRoomtypeId(long roomTypeId){
        return hmsERoomtypeFacilityMapper.findsByRoomtypeId(roomTypeId);
    }
    /**
     * 增加新的设备设施
     * @param roomTypeId
     * @param facId
     * @return
     */
    public int add(long roomTypeId,long facId){
    	return hmsERoomtypeFacilityMapper.add(roomTypeId, facId);
    }
    /**
     * 根据主键删除设备设施
     * @param id
     * @return
     */
    public int deleteById(long id){
    	return hmsERoomtypeFacilityMapper.deleteById(id);
    }
    /**
     * 根据房型编码 和 设备设施编码删除
     * @param roomTypeId
     * @param facId
     * @return
     */
    public int delete(long roomTypeId,long facId){
    	return hmsERoomtypeFacilityMapper.delete(roomTypeId, facId);
    }
    /**
     * 保存房型设备设施
     * @param facilityModel
     * @return
     */
    public int save(long roomTypeId,long facId){
    	if(!isExit(roomTypeId, facId)){
    		return hmsERoomtypeFacilityMapper.add(roomTypeId, facId);
    	}
    	return 0;
    }
    /**
     * 保存房型设备设施
     * @param facilityModel
     * @return
     */
    public boolean isExit(long roomTypeId,long facId){
    	HmsERoomtypeFacilityModel exitModel = hmsERoomtypeFacilityMapper.findOne(roomTypeId, facId);
    	return exitModel!=null;
    }
}
