package com.mk.hms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.HmsEBasePriceMapper;
import com.mk.hms.mapper.HmsTRoomtypeMapper;
import com.mk.hms.mapper.HotelAuditSynToTHotelMapper;
import com.mk.hms.model.HmsEBasePriceModel;
import com.mk.hms.model.HmsHOperateLogModel;
import com.mk.hms.model.HmsTRoomtypeModel;

/**
 * 基本房价服务类
 * Created by qhdhiman on 2015/5/3.
 */
@Service
@Transactional
public class HmsEBasePriceService {
	@Autowired
	private HmsHOperateLogService hmsHOperateLogService;
	
    @Autowired
    private HmsEBasePriceMapper hmsEBasePriceMapper;
	
	@Autowired
	private HmsTRoomtypeMapper hmsTRoomtypeMapper;
	
	
	@Autowired
	private HotelAuditSynToTHotelMapper mapper;
	 public List<HmsEBasePriceModel> findTRoomTypeModels(List<HmsTRoomtypeModel> hmsTRoomtypeModels){
     	return mapper.findTRoomTypeModels(hmsTRoomtypeModels);
   }
	/**
	 * 插入一条新数据
	 * @param eBasePriceModel
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public int add(HmsEBasePriceModel eBasePriceModel) throws SessionTimeOutException{
		eBasePriceModel.setUpdateTime(new Date());
		int result =  hmsEBasePriceMapper.add(eBasePriceModel);
		if(result>0){
			HmsHOperateLogModel log = new HmsHOperateLogModel("e_baseprice", "add","首次设置眯客基础房价("+eBasePriceModel.getRoomTypeId()+")" );
			hmsHOperateLogService.addLog(log);			
		}
		return result;
	}
	
	/**
	 * 根据id删除设备设置
	 * @param id
	 * @return
	 */
	public int deleteById(long id){
		return hmsEBasePriceMapper.deleteById(id);
	}
	
	/**
	 * 根据房型编号删除数据
	 * @param roomTypeId
	 * @param facId
	 * @return
	 */
	public int delete(long roomTypeId){
		return hmsEBasePriceMapper.delete(roomTypeId);
	}
	
	/**
	 * 根据房型ID获取房价
	 * @param roomTypeId
	 * @return
	 */
	public HmsEBasePriceModel find(long roomTypeId){
		return hmsEBasePriceMapper.find(roomTypeId);
	}
	
	/**
	 * 更新房价信息
	 * @param roomTypeId
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public int update(HmsEBasePriceModel eBasePriceModel) throws SessionTimeOutException{
		eBasePriceModel.setUpdateTime(new Date());
		int result =  hmsEBasePriceMapper.update(eBasePriceModel);
		if(result>0){
			HmsHOperateLogModel log = new HmsHOperateLogModel("e_baseprice", "add","更改眯客基础房价("+eBasePriceModel.getRoomTypeId()+")" );
			hmsHOperateLogService.addLog(log);			
		}
		return result;
	}
	
	/**
	 * 保存房价信息
	 * @param roomTypeId
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public int save(HmsEBasePriceModel eBasePriceModel) throws SessionTimeOutException{
		HmsEBasePriceModel exit = find(eBasePriceModel.getRoomTypeId());
		if(exit!=null){
			return update(eBasePriceModel);
		}else{
			return add(eBasePriceModel);			
		}
	}
	

    /**
     * 根据酒店Id获取 房价数据
     * @param hotelId
     * @return
     */
    public List<HmsEBasePriceModel> findsByHotelId(long hotelId){
    	List<HmsEBasePriceModel> eBasePriceModels = new ArrayList<HmsEBasePriceModel>();
    	List<HmsTRoomtypeModel> tRoomtypeModels = hmsTRoomtypeMapper.findsByHotelId(hotelId);
    	for(HmsTRoomtypeModel tRoomtypeModel : tRoomtypeModels){
    		HmsEBasePriceModel  eBasePriceModel = find(tRoomtypeModel.getId());
    		if(eBasePriceModel!=null){
    			eBasePriceModels.add(eBasePriceModel);
    		}
    	}
    	return eBasePriceModels;
    }
}
