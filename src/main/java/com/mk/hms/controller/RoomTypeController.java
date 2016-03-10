package com.mk.hms.controller;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.EBasePrice;
import com.mk.hms.model.Facility;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.RoomType;
import com.mk.hms.service.RoomTypeService;
import com.mk.hms.view.ERoomtypeInfoWithFacility;
import com.mk.hms.view.SaveRoomType;

/**
 * Created by qhdhiman on 2015/5/2.
 */
@Controller
@RequestMapping("/roomtype")
public class RoomTypeController {
	
	@Autowired
    private RoomTypeService roomTypeService = null;

	
    /**
     * 获取所有房型
     * @return 房型列表
     */
    @RequestMapping("/finds")
    @ResponseBody
    public List<RoomType> finds(){
        return this.getRoomtypeService().finds();
    }

    /**
     * 根据酒店Id获取房型
     * @return 房型列表
     * @throws SessionTimeOutException 
     */
    @RequestMapping("/save")
    @ResponseBody
    public OutModel save(SaveRoomType srt) throws SessionTimeOutException{
        return this.getRoomtypeService().save(srt);
    }

    /**
     * 根据酒店Id获取房型
     * @return 房型列表
     */
    @RequestMapping("/findsByHotelId")
    @ResponseBody
    public List<RoomType> findsByHotelId(long hotelId){
        return this.getRoomtypeService().findsByHotelId(hotelId);
    }

    /**
     * 根据房型Id获取房型信息
     * @param roomtypeId 房型Id
     * @return 房型信息
     */
    @RequestMapping("/findByRoomtypeId")
    @ResponseBody
    public ERoomtypeInfoWithFacility findByRoomtypeId(long roomTypeId){
        return this.getRoomtypeService().findERoomtypeInfoByRoomtypeId(roomTypeId);
    }
    /**
     * 获取所有房型设备设施
     * @return 设备设施列表
     */
    @RequestMapping("/findFacilities")
    @ResponseBody
    public Map<String,List<Facility>> findFacilities(){
    	return this.getRoomtypeService().findFacilities();
    }

    /**
	 * 根据房型ID获取房价
	 * @param roomTypeId
     */
    @RequestMapping("/findBasePrice")
    @ResponseBody
    public EBasePrice findBasePrice(long roomTypeId){
    	return this.getRoomtypeService().findEBasePriceByRoomtypeId(roomTypeId);
    }

    /**
	 * 根据房型ID获取房价
     * @throws SessionTimeOutException 
     */
    @RequestMapping("/saveBasePrice")
    @ResponseBody
    public OutModel saveBasePrice(long roomTypeId, BigDecimal value, String type) throws SessionTimeOutException{
        return this.getRoomtypeService().saveBasePrice(roomTypeId, value, type);
    }

	private RoomTypeService getRoomtypeService() {
		return roomTypeService;
	}
    
}
