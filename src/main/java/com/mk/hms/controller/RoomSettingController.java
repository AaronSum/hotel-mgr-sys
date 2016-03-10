package com.mk.hms.controller;

import com.alibaba.fastjson.JSON;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.RoomSetting;
import com.mk.hms.service.RoomSettingService;
import com.mk.hms.view.Page;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by qhdhiman on 2015/5/2.
 */
@Controller
@RequestMapping("/roomsetting")
public class RoomSettingController {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RoomSettingController.class);
	@Autowired
    private RoomSettingService roomSettingService;

    /**
     * 获取所有房型
     * @return 房型列表
     */
    @RequestMapping("/finds")
    @ResponseBody
    public Map<String, Object> findListPage(Long roomtypeid,String bedtypename, Page page){
        return roomSettingService.findListPage(roomtypeid,bedtypename,page);
    }

    /**
     * 房间设置列表或查看详情
     * @param roomTypeList
     * @return
     */
    @RequestMapping("/sets")
    @ResponseBody
    public OutModel findRoomSet(String roomTypeList){
        List<RoomSetting> list = null;
        if(!StringUtils.isBlank(roomTypeList)){
            list = JSON.parseArray(roomTypeList, RoomSetting.class);
        }
        return roomSettingService.findRoomSet(list);
    }

    /**
     * 保存房间设置信息
     * @param setting
     * @return
     * @throws SessionTimeOutException 
     */
    @RequestMapping("/saveRoomSetting")
    @ResponseBody
    public OutModel saveRoomSetting(String setting) throws SessionTimeOutException{
    	RoomSetting roomSetting = JSON.parseObject(setting, RoomSetting.class);
        return roomSettingService.saveRoomSetting(roomSetting);
    }

}
