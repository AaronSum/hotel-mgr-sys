package com.mk.hms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.enums.HmsVisibleEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.RoomSettingMapper;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.RoomSetting;
import com.mk.hms.model.RoomSettingCriteria;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;

/**
 * 房型 service
 *
 * @author hdy
 */
@Service
@Transactional
public class RoomSettingService {

	private static final Logger logger = LoggerFactory.getLogger(RoomSettingService.class);

    @Autowired
    private RoomSettingMapper RoomSettingMapper;

    public Map<String, Object> findListPage(Long roomtypeid, String bedtypename, Page page) {
        Map<String, Object> outMap = new HashMap<String, Object>();
        try {
            Long currHotelId = SessionUtils.getThisHotelId();
            //设置查询对象
            RoomSetting rts = new RoomSetting();
            rts.setBedtypename(StringUtils.trim(bedtypename));
            rts.setRoomtypeid(roomtypeid);
            rts.setHotelid(currHotelId);
            //获取总数
            Integer countSize = RoomSettingMapper.findListCount(rts);
            //取出t_room_type,t_room表内房间数据
            List<RoomSetting> rtList = RoomSettingMapper.findListPage(rts, page);
            outMap.put("total", countSize);
            for (RoomSetting roomSetting : rtList) {
                if (roomSetting.getEhotelId() == null) {
                    roomSetting.setIsset("否");
                } else {
                    roomSetting.setIsset("是");
                }
            }
            outMap.put("data", rtList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outMap;
    }

    /**
     * 房间设置或查看详情
     * @param roomTypeList 房间勾选列表
     * @return
     */
    public OutModel findRoomSet(List<RoomSetting> roomTypeList){
        OutModel out = new OutModel();
        StringBuilder context = new StringBuilder();//格式：房型ID_房型名称_房间号|
        String rts_separator = "_"; //房型、房间号，组内分隔符
        try {
            if(roomTypeList.isEmpty()){
                out.setSuccess(false);
                out.setErrorMsg("没有查询到房型房间信息");
            }else{
                for (RoomSetting setting : roomTypeList) {
                    context.append(setting.getRoomtypeid()).append(rts_separator)
                            .append(setting.getRoomtypename()).append(rts_separator)
                            .append(setting.getRoomno()).append("|");
                }
                if(context.length() != 0){ //去掉最后一个"|"
                    context.deleteCharAt(context.length()-1);
                }
                //设置保存数据时所需信息,获取设置房间信息
                RoomSetting sett = new RoomSetting();
                if(roomTypeList.size() == 1){
                	sett = roomTypeList.get(0);
                }
                sett.setRtsArray(context.toString());
                out.setAttribute(sett);
                out.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.setSuccess(false);
            out.setErrorMsg("房型房间查询或设置信息失败");
        }
        return out;
    }

    /**
     * 设置房间保存方法
     * @param setting 设置房间信息对象
     * @return
     * @throws SessionTimeOutException 
     */
    public OutModel saveRoomSetting(RoomSetting setting) throws SessionTimeOutException{
        OutModel out = new OutModel();
        if(StringUtils.isBlank(setting.getIswindow())){
            out.setSuccess(false);
            out.setErrorMsg("保存失败，是否有窗为必填项");
            return out;
        }
        
        if(setting.getRoomfloor() != null && setting.getRoomfloor().length() > 999){
            out.setSuccess(false);
            out.setErrorMsg("楼层不能超过20字！");
            return out;
        }
        
        if(setting.getRoomdirection() != null && setting.getRoomdirection().length() > 20){
            out.setSuccess(false);
            out.setErrorMsg("房间朝向不能超过20字！");
            return out;
        }
        
        if(setting.getBedtypename() != null && setting.getBedtypename().length() > 20){
            out.setSuccess(false);
            out.setErrorMsg("床型设置不能超过20字！");
            return out;
        }
        
        String context = setting.getRtsArray();//接收设置房间数组数据
        Long currHotelId = SessionUtils.getThisHotelId();

        String rts_separator = "_"; //房型、房间号，组内分隔符
        try {
            //删除现有酒店房间设置数据
            //取出房间表内，现存数据
            List<RoomSetting> roomTypeList = new ArrayList<RoomSetting>();
            List<String> roomNos = new ArrayList<String>();
            String []rooms = context.split("\\|");
            for (String s : rooms) {
                RoomSetting sett = new RoomSetting();
                setting.setCretedate(HmsDateUtils.getTimestamp());
                setting.setUpdatedate(HmsDateUtils.getTimestamp());
                //将接收对象转换至新对象
                BeanUtils.copyProperties(sett,setting);
                //设置新对象房型ID_房型名称_房间号
                String[] rts = s.split(rts_separator);
                String roomtypeid = rts[0];
                String roomtypename = rts[1];
                String roomno = rts[2];
                roomNos.add(roomno); //删除对象用
                sett.setRoomtypeid(Long.parseLong(roomtypeid));
                sett.setRoomtypename(roomtypename);
                sett.setRoomno(roomno);
                sett.setHotelid(currHotelId);
                sett.setBedtypename(StringUtils.trim(setting.getBedtypename()));
                sett.setRoomdirection(StringUtils.trim(setting.getRoomdirection()));
                //有效无效，默认是T
                sett.setValid(HmsVisibleEnum.T.getValue());
                roomTypeList.add(sett);
            }
            RoomSettingCriteria criteria = new RoomSettingCriteria();
            criteria.createCriteria().andHotelidEqualTo(currHotelId)
                    .andRoomnoIn(roomNos);
            Integer count = RoomSettingMapper.deleteByExample(criteria);
            //循环insert
            for (RoomSetting sett:roomTypeList){
                RoomSettingMapper.insert(sett);
            }
            out.setSuccess(true);
            out.setContext("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            out.setSuccess(false);
            out.setErrorMsg("设置房间信息失败");
            return out;
        }
        return out;
    }

    /**
     * 删除t_room_setting表数据
     * @return
     */
    private OutModel deleteRoomSetting(Long currHotelId) throws Exception {
        OutModel out = new OutModel();
        try {
            RoomSettingCriteria criteria = new RoomSettingCriteria();
            criteria.createCriteria().andHotelidEqualTo(currHotelId);
            Integer count = RoomSettingMapper.deleteRoomSetting(criteria);
            out.setSuccess(true);
            out.setContext("删除成功");
            logger.error("当前酒店:" + currHotelId + ",删除t表房间信息数据成功,条数:" + count);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("当前酒店:" + currHotelId + ",删除t表房间信息数据失败");
            out.setErrorMsg("删除t表房间信息失败");
            throw e;
        }
        return out;
    }

    /**
     * 插入t_room_setting表数据
     * @return
     */
    private OutModel insertToTRoomSetting(Long currHotelId) throws Exception {
        OutModel out = new OutModel();
        try {
            Integer count = RoomSettingMapper.insertToTRoomSetting(currHotelId);
            out.setSuccess(true);
            out.setContext("插入数据数据成功");
            logger.info("当前酒店:" + currHotelId + ",插入t表数据数据成功,条数:" + count);
        } catch (Exception e) {
            e.printStackTrace();
            out.setSuccess(false);
            logger.error("当前酒店:" + currHotelId + ",插入t表数据房间信息失败");
            throw e;
        }
        return out;
    }

    /**
     * 将e_room_setting表数据同步至t_room_setting表
     * @return
     * @throws SessionTimeOutException 
     */
    public OutModel copyERoomSetting2TRoomSetting() throws SessionTimeOutException{
        OutModel out = new OutModel();

        EHotelWithBLOBs thisHotel = SessionUtils.getSessionPmsHotel();

        Long currHotelId = thisHotel.getId();
        
        try {
            logger.info("当前酒店:"+currHotelId+",同步t_room_setting表数据开始");
            this.deleteRoomSetting(currHotelId);
            this.insertToTRoomSetting(currHotelId);
            out.setSuccess(true);
            logger.info("当前酒店:"+currHotelId+",同步t_room_setting表数据结束");
        } catch (Exception e) {
            e.printStackTrace();
            out.setSuccess(false);
            logger.info("当前酒店:" + currHotelId + ",同步t_room_setting表数据失败");
            return out;
        }
        return out;
    }
}
