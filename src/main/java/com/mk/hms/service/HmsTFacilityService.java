package com.mk.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.enums.HmsTFacilityBindingEnum;
import com.mk.hms.enums.HmsTfacilityTypeEnum;
import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.mapper.HmsTFacilityMapper;
import com.mk.hms.model.HmsTFacilityModel;

/**
 * 设施设备服务类
 * Created by qhdhiman on 2015/5/3.
 */
@Service
@Transactional
public class HmsTFacilityService {
	
	private String hotelBing = HmsTFacilityBindingEnum.Hotel.getValue();
	private String roomBing = HmsTFacilityBindingEnum.Room.getValue();
	private String visible = HmsVisibleEnum.T.getValue();
	private int normal = HmsTfacilityTypeEnum.normal.getValue();
	private int other = HmsTfacilityTypeEnum.other.getValue();
	
	@Autowired
    private HmsTFacilityMapper hmsTFacilityMapper;
    
    /**
     * 获取酒店设备设置列表
     * @return
     */
    public List<HmsTFacilityModel> findsHotelFacilities(){
        return hmsTFacilityMapper.finds(hotelBing,visible);
    }
    /**
     * 获取酒店常见设备设置列表
     * @return
     */
    public List<HmsTFacilityModel> findsHotelNormalFacilities(){
        return hmsTFacilityMapper.findsWithFacType(hotelBing,visible,normal);
    }
    /**
     * 获取酒店其它设备设置列表
     * @return
     */
    public List<HmsTFacilityModel> findsHotelOtherFacilities(){
        return hmsTFacilityMapper.findsWithFacType(hotelBing,visible,other);
    }
    /**
     * 获取房型设备设置列表
     * @return
     */
    public List<HmsTFacilityModel> findsRoomTypeFacilities(){
        return hmsTFacilityMapper.finds(roomBing,visible);
    }
    /**
     * 获取房型常见设备设置列表
     * @return
     */
    public List<HmsTFacilityModel> findsRoomTypeNormalFacilities(){
        return hmsTFacilityMapper.findsWithFacType(roomBing,visible,normal);
    }
    /**
     * 获取房型其它设备设置列表
     * @return
     */
    public List<HmsTFacilityModel> findsRoomTypeOtherFacilities(){
        return hmsTFacilityMapper.findsWithFacType(roomBing,visible,other);
    }
}
