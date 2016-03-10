package com.mk.hms.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.db.HmsJdbcTemplate;
import com.mk.hms.mapper.HmsTRoomtypeMapper;
import com.mk.hms.mapper.HotelAuditSynToTHotelMapper;
import com.mk.hms.model.HmsERoomtypeInfoModel;
import com.mk.hms.model.HmsTRoomtypeModel;
import com.mk.hms.utils.ContentUtils;

/**
 * 房型服务类
 * Created by qhdhiman on 2015/5/2.
 */
@Service
@Transactional
public class HmsTRoomTypeService {
	
	@Autowired
    private HmsTRoomtypeMapper hmsTRoomtypeMapper;
    //begin
	@Autowired
	private HotelAuditSynToTHotelMapper mapper;
	public int updateBedNum(Map<Object,Object> map){
		return mapper.updateBedNum(map);
	}
	public int[] updateBedNum(final List<HmsERoomtypeInfoModel> roomtypeInfoModels){
		JdbcTemplate jdbc = HmsJdbcTemplate.getJdbcTemplate();
//        for(HmsERoomtypeInfoModel model:roomtypeInfoModels){
//        	String bedSize = model.getBedSize();
//        	long roomTypeId = model.getRoomTypeId();
//        	int bedNum = 0;
//        	if(StringUtils.isNotEmpty(bedSize)){
//        		bedNum = bedSize.split(ContentUtils.CHAR_COMMA).length;
//        	}
//        	String sql = "update t_roomtype set bedNum="+bedNum+" where id="+roomTypeId+"";
//        	jdbc.update(sql);
//        }
//		jdbc.update("update t_roomtype set bedNum=? where id=?",  new BatchPreparedStatementSetter() {
//			
//			@Override
//			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				int bedNum = 0;
//				String bedSize = roomtypeInfoModels.get(i).getBedSize();
//	        	if(StringUtils.isNotEmpty(bedSize)){
//	        		bedNum = bedSize.split(ContentUtils.CHAR_COMMA).length;
//	        	}
//				 ps.setInt(1, bedNum);
//	             ps.setLong(2, roomtypeInfoModels.get(i).getRoomTypeId());				
//			}
//			
//			@Override
//			public int getBatchSize() {
//				return roomtypeInfoModels.size();
//			}
//		});
		String sql="update t_roomtype set bedNum=? where id=?";
	    BatchPreparedStatementSetter setter=new BatchPreparedStatementSetter (){
	          public void setValues(PreparedStatement ps,int i) throws SQLException{
	        	  int bedNum = 0;
					String bedSize = roomtypeInfoModels.get(i).getBedSize();
		        	if(StringUtils.isNotEmpty(bedSize)){
		        		bedNum = bedSize.split(ContentUtils.CHAR_COMMA).length;
		        	}
		        	ps.setInt(1, bedNum);
		            ps.setLong(2, roomtypeInfoModels.get(i).getRoomTypeId());	
	          }
	          public int getBatchSize(){
	             return roomtypeInfoModels.size();
	          }
	    };
	    return jdbc.batchUpdate(sql,setter);
	}
	
	//end
    /**
     * 获取所有房型
     * @return 房型列表
     */
    public List<HmsTRoomtypeModel> finds(){
        return hmsTRoomtypeMapper.finds();
    }

    /**
     * 根据酒店Id获取房型
     * @param hotelId 酒店Id
     * @return 房型列表
     */
    public List<HmsTRoomtypeModel> findsByHotelId(long hotelId){
        return hmsTRoomtypeMapper.findsByHotelId(hotelId);
    }

    /**
     * 根据房型Id获取房型
     * @param hotelId 酒店Id
     * @return 房型列表
     */
    public HmsTRoomtypeModel findById(long id){
        return hmsTRoomtypeMapper.findById(id);
    }
    /**
     * 更新床的数量
     * @param id
     * @param bedNum
     * @return
     */
    public int updateBedNum(long id,int bedNum){
    	return hmsTRoomtypeMapper.updateBedNum(id, bedNum);
    }
}
