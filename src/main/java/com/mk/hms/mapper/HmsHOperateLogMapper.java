package com.mk.hms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import com.mk.hms.model.HmsHOperateLogModel;
import com.mk.hms.model.HmsTHotelOperateLogModel;

/**
 * 日志操作接口
 * @author hdy
 *
 */
public interface HmsHOperateLogMapper {

	/**
	 * 添加操作日志
	 * @param log 日志对象
	 * @return 受影响条数
	 */
	@Insert("insert into h_operate_log (hotelid,tatablename, usercode, username, ip, functioncode, functionname, operatetime, "
			+ "usertype) values (#{hotelid},#{tatablename}, #{usercode}, #{username}, #{ip}, #{functioncode}, #{functionname}, "
			+ "#{operatetime}, #{usertype})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache = true)
	int addLog(HmsHOperateLogModel log);
	
	/**
	 * 添加酒店审核log
	 * @param log 日志对象
	 * @return 受影响条数
	 */
	@Insert("insert into t_hotel_operate_log (hotelid, hotelname, usercode, username, checktime, checktype, checktypename)"
			+ " values (#{hotelid}, #{hotelname}, #{usercode}, #{username}, #{checktime}, #{checktype}, #{checktypename})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache = true)
	int addTHotelOperateLog(HmsTHotelOperateLogModel log);
	
}
