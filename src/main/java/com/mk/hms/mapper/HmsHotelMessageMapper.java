package com.mk.hms.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mk.hms.model.HmsEHotelFacilityModel;
import com.mk.hms.model.HmsEHotelModel;
import com.mk.hms.model.HmsEHotelbussinesszoneModel;
import com.mk.hms.model.HmsTBusinesszoneModel;
import com.mk.hms.model.HmsTFacilityModel;
import com.mk.hms.model.HmsTHotelModel;

/**
 * 酒店讯息mapper接口
 * @author hdy
 *
 */
public interface HmsHotelMessageMapper {

	/**
	 * 获取酒店设置分类
	 * @param binding 匹配类型
	 * @param visible 是否可用标示，T－可用，F－不可用
	 * @return 酒店设置列表
	 */
	@Select("select * from t_facility where binding = #{binding} and visible = #{visible} order by facType, facSort")
	List<HmsTFacilityModel> finsTFacilities(@Param("binding") String binding, @Param("visible") String visible);
	
	/**
	 * 获取酒店已配置设备
	 * @param hotelId 酒店id
	 * @return 酒店配置设备列表
	 */
	@Select("select * from e_hotel_facility where hotelId = #{hotelId}")
	List<HmsEHotelFacilityModel> findEHotelFacilities(@Param("hotelId") long hotelId);
	
	/**
	 * 删除酒店设置
	 * @param hotelId 酒店id
	 * @return 受影响条数
	 */
	@Delete("delete from t_hotel_facility where hotelId = #{hotelId}")
	int deleteTHotelFacilities(@Param("hotelId") long hotelId);
	
	/**
	 * 添加一条酒店设施
	 * @param hotelId 酒店id
	 * @param facId 设施id
	 * @return 受影响条数
	 */
	@Insert("insert into t_hotel_facility (hotelId, facId) values (#{hotelId}, #{facId})")
	int insertTHotelFacilities(@Param("hotelId") long hotelId, @Param("facId") long facId);
	
	/**
	 * 插入一条数据
	 * @param hotelId 酒店id
	 * @param facId 商圈id
	 * @return 执行成功结果条数
	 */
	@Insert("insert into e_hotel_facility(hotelId, facId) values(#{hotelId}, #{facId})")
	int addEHotelFacilities(@Param("hotelId") long hotelId, @Param("facId") long facId);
	
	/**
	 * 删除一条数据
	 * @param hotelId 酒店id
	 * @param facIds  商圈ids
	 * @return 执行成功结果条数
	 */
	@Delete("delete from e_hotel_facility where hotelId = #{hotelId} and facId in (${facIds})")
	int deleteEHotelFacilities(@Param("hotelId") long sql, @Param("facIds") String facIds);
	
	/**
	 * 获取酒店商圈信心
	 * @param dis 区县id
	 * @param businessZoneType 商圈信息类型
	 * @return 商圈信息列表
	 */
	@Select("select * from t_businesszone where dis= #{dis} and businessZoneType = #{businessZoneType}")
	List<HmsTBusinesszoneModel> findTBusinesszones(@Param("dis") int dis, @Param("businessZoneType") int businessZoneType);
	
	/**
	 * 获取地铁信息列表
	 * @param businessZoneType 商圈信息类型
	 * @return 地铁信息列表
	 */
	@Select("select * from t_businesszone where businessZoneType = #{businessZoneType}")
	List<HmsTBusinesszoneModel> findTBusinesszonesBySubway(@Param("businessZoneType") int businessZoneType);
	
	/**
	 * 获取我的商圈信息列表
	 * @param ids 主键
	 * @param businessZoneType 商圈信息类型
	 * @return 商圈信息列表
	 */
	@Select("select * from t_businesszone where id in(${ids}) and businessZoneType = #{businessZoneType}")
	List<HmsTBusinesszoneModel> findMyTBusinesszones(@Param("ids") String ids, @Param("businessZoneType") int businessZoneType);
	
	/**
	 * 获取我的地铁站点信息列表
	 * @param ids 主键
	 * @param businessZoneType 商圈信息类型
	 * @return 商圈信息列表
	 */
	@Select("select * from t_businesszone where id in(${ids}) and businessZoneType = #{businessZoneType} and fatherid is not null")
	List<HmsTBusinesszoneModel> findMySubwayStations(@Param("ids") String ids, @Param("businessZoneType") int businessZoneType);
	
	/**
	 * 获取酒店商圈信息列表
	 * @param hotelId 酒店id
	 * @return 酒店商圈信息列表
	 */
	@Select("select * from e_hotelbussinesszone where hotelId = #{hotelId}")
	List<HmsEHotelbussinesszoneModel> findEHotelbussinesszones(@Param("hotelId") long hotelId);
	
	/**
	 * 修改酒店交通信息
	 * @param traffic 交通信息
	 * @param id 酒店id
	 * @return 修改结果
	 */
	@Update("update e_hotel set traffic = #{traffic} where id = #{id}")
	int updateEHotelTraffic(@Param("traffic") String traffic, @Param("id") long id);
	
	/**
	 * 修改酒店周边信息
	 * @param peripheral 交周边信息
	 * @param id 酒店id
	 * @return 修改结果
	 */
	@Update("update e_hotel set peripheral = #{peripheral} where id = #{id}")
	int updateEHotelPeripheral(@Param("peripheral") String peripheral, @Param("id") long id);
	
	/**
	 * 安装pms
	 * @param pms 编码
	 * @param updateTime 修改时间
	 * @param isNewPms 是否为新PMS
	 * @param id 主键
	 * @return 受影响条数
	 */
	@Update("update e_hotel set pms = #{pms}, updateTime = #{updateTime}, isNewPms = #{isNewPms} where id = #{id}")
	int updateEHotelPms(@Param("pms") String pms, @Param("updateTime") Date updateTime, @Param("isNewPms") String isNewPms, 
			@Param("id") long id);	
	/**
	 * 安装pms
	 * @param pms 编码
	 * @param updateTime 修改时间
	 * @param isNewPms 是否为新PMS
	 * @param id 主键
	 * @return 受影响条数
	 */
	@Update("update e_hotel set pms = #{pms}, pmshotelname = #{pmshotelname}, updateTime = #{updateTime}, isNewPms = #{isNewPms}, pmsStatus = #{pmsStatus} where id = #{id}")
	int updateEHotelPms2(@Param("pms") String pms, @Param("pmshotelname") String pmshotelname,  @Param("updateTime") Date updateTime, @Param("isNewPms") String isNewPms,
			@Param("id") long id, @Param("pmsStatus") int pmsStatus);
	
	/**
	 * 修改eHotel状态
	 * @param state 状态
	 * @param id 主键
	 * @return 受影响条数
	 */
	@Update("update e_hotel set state = #{state} where id = #{id}")
	int updateEHotelState(@Param("state") int state, @Param("id") long id);
	
	/**
	 * 修改eHotel位置信息
	 * @param id 主键
	 * @param lat 纬度
	 * @param lng 经度
	 * @return 受影响条数
	 */
	@Update("update e_hotel set latitude = #{lat}, longitude = #{lng} where id = #{id}")
	int updateEHotelLocation(@Param("id") long id, @Param("lat") double lat, @Param("lng") double lng);
	
	/**
	 * 修改tHotel位置信息
	 * @param id 主键
	 * @param lat 纬度
	 * @param lng 经度
	 * @return 受影响条数
	 */
	@Update("update t_hotel set latitude = #{lat}, longitude = #{lng} where id = #{id}")
	int updateTHotelLocation(@Param("id") long id, @Param("lat") double lat, @Param("lng") double lng);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @return 数据列表
	 */
	@Select("select e.*, t.online, d.CityID from (select * from e_hotel order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id left join t_district d on e.disId = d.id")
	List<HmsEHotelModel> findHotels(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @return 数据列表
	 */
	@Select("select e.*, t.online, d.CityID from (select * from e_hotel where id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%' order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id left join t_district d on e.disId = d.id")
	List<HmsEHotelModel> findHotelsByQueryContentHasHotel(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("queryContent") String queryContent);
	
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @return 数据列表
	 */
	@Select("select e.*, t.online, d.CityID from (select * from e_hotel where hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%' order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id left join t_district d on e.disId = d.id")
	List<HmsEHotelModel> findHotelsByQueryContentNoHotel(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @return 数据列表
	 */
	@Select("select * from t_hotel where id in (${ids}) order by regTime desc")
	List<HmsTHotelModel> findTHotels(@Param("ids") String ids);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @return 数据列表
	 */
	@Select("select id from t_hotel where hotelName like '%${hotelName}%'")
	List<Long> findTHotelByHotelName(@Param("hotelName") String hotelName);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotels(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("pmsUser") String pmsUser);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%' ) order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelsByQueryContentHasHotel(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("pmsUser") String pmsUser, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and (hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelsByQueryContentNoHotel(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("pmsUser") String pmsUser, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsStatus <= #{pmsStatus} order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByPmsStatus(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("pmsStatus") int pmsStatus);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsStatus <= #{pmsStatus} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByPmsStatusByQueryContentHasHotel(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("pmsStatus") int pmsStatus, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsStatus <= #{pmsStatus} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByPmsStatusByQueryContentNoHotel(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("pmsStatus") int pmsStatus, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and pmsStatus <= #{pmsStatus} order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByPmsStatus(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("pmsStatus") int pmsStatus);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and pmsStatus <= #{pmsStatus} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByPmsStatusByQueryContentHasHotelId(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("pmsStatus") int pmsStatus, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param pmsStatus pms状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and pmsStatus <= #{pmsStatus} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByPmsStatusByQueryContentNoHotelId(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("pmsStatus") int pmsStatus, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param status 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where visible!='F' and state in (${states}) order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByCheck(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("states") String states);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param status 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where state in (${states}) and visible!='F' and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByCheckAndQueryContentHasHotelId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("states") String states, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param status 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where state in (${states}) and visible!='F' and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByCheckAndQueryContentNoHotelId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("states") String states, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param statu 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where state = #{state} order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByState(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("state") int state);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param statu 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where state = #{state} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByStateAndQueryContentHasHotelId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("state") int state, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param statu 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where state = #{state} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByStateAndQueryContentNoHotelId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("state") int state, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param status 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and visible!='F' and state in (${states}) order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByCheck(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("states") String states);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param status 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and state in (${states}) and visible!='F' and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByCheckByQueryContentHasHotel(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("states") String states, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param status 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and state in (${states}) and visible!='F' and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByCheckByQueryContentNoHotel(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("states") String states, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param statu 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and state = #{state} order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByState(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("state") int state);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param statu 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and state = #{state} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByStateAndQueryContentHasHotel(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("state") int state, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param statu 状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and state = #{state} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByStateAndQueryContentNoHotel(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("state") int state, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where length(reason) > 0 order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findHotelListByReason(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and length(reason) > 0 order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByReason(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and length(reason) > 0 and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByReasonAndQueryContentHasHotel(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from (select * from e_hotel where pmsUser = #{pmsUser} and length(reason) > 0 and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%') order by regTime desc limit #{pageNum}, #{pageSize}) e left join t_hotel t on e.id = t.id")
	List<HmsEHotelModel> findMyHotelListByReasonAndQueryContentNoHotel(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param states 上线状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible} order by e.regTime desc limit #{pageNum}, #{pageSize}")
	List<HmsEHotelModel> findHotelListOnLine(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("visible") String visible);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param states 上线状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible} and (e.id = ${queryContent}"
			+ " or e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%') order by e.regTime desc limit #{pageNum}, #{pageSize}")
	List<HmsEHotelModel> findHotelListOnLineByQueryContentHasHotelId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("visible") String visible, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param states 上线状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible} and ("
			+ " e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%') order by e.regTime desc limit #{pageNum}, #{pageSize}")
	List<HmsEHotelModel> findHotelListOnLineByQueryContentNoHotelId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
			@Param("visible") String visible, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param states 上线状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from e_hotel e inner join t_hotel t on e.id = t.id where e.pmsUser = #{pmsUser}"
			+ " and t.visible = #{visible} order by e.regTime desc limit #{pageNum}, #{pageSize}")
	List<HmsEHotelModel> findMyHotelListOnLine(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("visible") String visible);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param states 上线状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from e_hotel e inner join t_hotel t on e.id = t.id where e.pmsUser = #{pmsUser}"
			+ " and t.visible = #{visible} and (e.id = ${queryContent}"
			+ " or e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%') order by e.regTime desc limit #{pageNum}, #{pageSize}")
	List<HmsEHotelModel> findMyHotelListOnLineByQueryContentHasHotelId(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("visible") String visible, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店数据列表
	 * @param pmsUser pms用户
	 * @param pageNum 页码
	 * @param pageSize 当页数据记录条数
	 * @param pmsUser  pms用户编码
	 * @param states 上线状态
	 * @return 数据列表
	 */
	@Select("select e.*, t.online from e_hotel e inner join t_hotel t on e.id = t.id where e.pmsUser = #{pmsUser}"
			+ " and t.visible = #{visible} and ("
			+ " e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%') order by e.regTime desc limit #{pageNum}, #{pageSize}")
	List<HmsEHotelModel> findMyHotelListOnLineByQueryContentNoHotelId(@Param("pmsUser") String pmsUser, @Param("pageNum") int pageNum,
			@Param("pageSize") int pageSize, @Param("visible") String visible, @Param("queryContent") String queryContent);
	
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel")
	int findHotelCount();
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%'")
	int findHotelCountByQueryContentHasHotel(@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where hotelName like '%${queryContent}%'"
			+ " or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%'")
	int findHotelCountByQueryContentNoHotel(@Param("queryContent") String queryContent);
	
	/**
	 * 获取我的酒店列表数据条数
	 * @param pmsUser  pms用户编码
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser}")
	int findMyHotelCount(@Param("pmsUser") String pmsUser);
	
	/**
	 * 获取我的酒店列表数据条数, 含有模糊查询（查询内容如果为数字则加上酒店id查询）
	 * @param pmsUser  pms用户编码
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelCountByQueryContentHasHotelId(@Param("pmsUser") String pmsUser, @Param("queryContent") String queryContent);
	
	/**
	 * 获取我的酒店列表数据条数, 含有模糊查询（查询内容如果为数字则加上酒店id查询）
	 * @param pmsUser  pms用户编码
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and ("
			+ "hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelCountByQueryContentNoHotelId(@Param("pmsUser") String pmsUser, @Param("queryContent") String queryContent);
	
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsStatus pms状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsStatus <= #{pmsStatus}")
	int findHotelByPmsStatusCount(@Param("pmsStatus") int pmsStatus);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsStatus pms状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsStatus <= #{pmsStatus} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findHotelByPmsStatusCountByQueryContentHasHotelId(@Param("pmsStatus") int pmsStatus,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsStatus pms状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsStatus <= #{pmsStatus} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findHotelByPmsStatusCountByQueryContentNoHotelId(@Param("pmsStatus") int pmsStatus,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param statues 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where  visible!='F'and state in (${states})")
	int findHotelByCheckCount(@Param("states") String states);
	
	/**
	 * 获取酒店列表数据条数
	 * @param statues 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where state in (${states}) and visible!='F' and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findHotelByCheckCountByQueryContentHasHotelId(@Param("states") String states, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param statues 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where state in (${states}) and visible!='F' and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findHotelByCheckCountByQueryContentNoHotelId(@Param("states") String states, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param statue 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where state = #{state}")
	int findHotelByStateCount(@Param("state") int state);
	
	/**
	 * 获取酒店列表数据条数
	 * @param statue 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where state = #{state} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findHotelByStateCountAndQueryContentHasHotelId(@Param("state") int state, @Param("queryContent") String queryContent);
	
	
	/**
	 * 获取酒店列表数据条数
	 * @param statue 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where state = #{state} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findHotelByStateCountAndQueryContentNoHotelId(@Param("state") int state, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where length(reason) > 0")
	int findHotelByReasonCount();
	
	/**
	 * 获取酒店列表数据条数
	 * @param states 上线标志
	 * @return 数据条数
	 */
	@Select("select e.id from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible}")
	List<Long> findHotelOnLineCount(@Param("visible") String visible);
	
	/**
	 * 获取酒店列表数据条数
	 * @param states 上线标志
	 * @return 数据条数
	 */
	@Select("select e.id from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible}"
			+ " and (e.id = ${queryContent}"
			+ " or e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%')")
	List<Long> findHotelOnLineCountByQueryContentHasHotelId(@Param("visible") String visible, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param states 上线标志
	 * @return 数据条数
	 */
	@Select("select e.id from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible} and ("
			+ " e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%')")
	List<Long> findHotelOnLineCountByQueryContentNoHotelId(@Param("visible") String visible, @Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param pmsStatus pms状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and pmsStatus <= #{pmsStatus}")
	int findMyHotelByPmsStatusCount(@Param("pmsUser") String pmsUser, @Param("pmsStatus") int pmsStatus);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param pmsStatus pms状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and pmsStatus <= #{pmsStatus} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByPmsStatusCountByQueryContentHasHotel(@Param("pmsUser") String pmsUser, @Param("pmsStatus") int pmsStatus,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param pmsStatus pms状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and pmsStatus <= #{pmsStatus} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByPmsStatusCountByQueryContentNoHotel(@Param("pmsUser") String pmsUser, @Param("pmsStatus") int pmsStatus,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param status 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and visible!='F' and state in (${states}) ")
	int findMyHotelByCheckCount(@Param("pmsUser") String pmsUser, @Param("states") String states);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param status 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and state in (${states}) and visible!='F' and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByCheckCountByQueryContentHasHotel(@Param("pmsUser") String pmsUser, @Param("states") String states,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param status 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and state in (${states}) and visible!='F' and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByCheckCountByQueryContentNoHotel(@Param("pmsUser") String pmsUser, @Param("states") String states,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param statu 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and state = #{states}")
	int findMyHotelByStateCount(@Param("pmsUser") String pmsUser, @Param("states") int states);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param statu 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and state = #{states} and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByStateCountAndQueryContentHasHotel(@Param("pmsUser") String pmsUser, @Param("states") int states,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param statu 状态
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and state = #{states} and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByStateCountAndQueryContentNoHotel(@Param("pmsUser") String pmsUser, @Param("states") int states,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and length(reason) > 0")
	int findMyHotelByReasonCount(@Param("pmsUser") String pmsUser);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and length(reason) > 0 and (id = ${queryContent}"
			+ " or hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByReasonCountAndQueryContentHasHotel(@Param("pmsUser") String pmsUser,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @return 数据条数
	 */
	@Select("select count(id) from e_hotel where pmsUser = #{pmsUser} and length(reason) > 0 and ("
			+ " hotelName like '%${queryContent}%' or hotelContactName like '%${queryContent}%'"
			+ " or detailAddr like '%${queryContent}%')")
	int findMyHotelByReasonCountAndQueryContentNoHotel(@Param("pmsUser") String pmsUser,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param states 上线状态
	 * @return 数据条数
	 */
	@Select("select e.id from e_hotel e inner join t_hotel t on e.id = t.id where e.pmsUser = #{pmsUser}"
			+ " and t.visible = #{visible})")
	List<Long> findMyHotelOnLineCount(@Param("pmsUser") String pmsUser, @Param("visible") String visible);
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param visible 上线状态
	 * @return 数据条数
	 */
	@Select("select e.id from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible}"
			+ " and e.pmsUser = #{pmsUser} and (e.id = ${queryContent}"
			+ " or e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%')")
	List<Long> findMyHotelOnLineCountAndQueryContentHasHotelId(@Param("visible") String visible, @Param("pmsUser") String pmsUser,
			@Param("queryContent") String queryContent);
	
	
	/**
	 * 获取酒店列表数据条数
	 * @param pmsUser pms用户
	 * @param visible 上线状态
	 * @return 数据条数
	 */
	@Select("select e.id from e_hotel e inner join t_hotel t on e.id = t.id where t.visible = #{visible}"
			+ " and e.pmsUser = #{pmsUser}"
			+ " and (e.hotelName like '%${queryContent}%' or e.hotelContactName like '%${queryContent}%'"
			+ " or e.detailAddr like '%${queryContent}%')")
	List<Long> findMyHotelOnLineCountAndQueryContentNoHotelId(@Param("visible") String visible, @Param("pmsUser") String pmsUser,
			@Param("queryContent") String queryContent);
	
	/**
	 * 获取酒店对象信息
	 * @param id id
	 * @return 酒店对象
	 */
	@Select("select * from e_hotel where id = #{id}")
	HmsEHotelModel findEHotelById(@Param("id") long id);
	
	/**
	 * 获取酒店对象信息
	 * @param id id
	 * @return 酒店对象
	 */
	@Select("select * from e_hotel where id = #{id}")
	HmsEHotelModel findMyEHotelById(@Param("id") long id);
	
	/**
	 * 获取酒店对象信息
	 * @param pms pms编码
	 * @return 酒店对象
	 */
	@Select("select * from e_hotel where pms = #{pms}")
	List<HmsEHotelModel> findMyEHotelByPms(@Param("pms") String pms);
	
	/**
	 * 获取酒店对象信息
	 * @param id id
	 * @return 酒店对象
	 */
	@Select("select * from t_hotel where id = #{id}")
	HmsTHotelModel findTHotelById(@Param("id") long id);

	/**
	 * 更新酒店商圈信息
	 * @param openTime 开业时间
	 * @param repairTime 最近装修时间
	 * @param retentionTime 预计抵达时间
	 * @param defaultLeaveTime 默认离店时间
	 * @param introduction 简介
	 * @param id 酒店id
	 * @return 受影响条数
	 */
	@Update("update e_hotel set openTime = #{openTime}, repairTime = #{repairTime}, retentionTime = #{retentionTime}, "
			+ "defaultLeaveTime = #{defaultLeaveTime}, introduction = #{introduction}, hotelphone = #{hotelphone},"
			+ "hotelName = #{hotelName}, detailAddr = #{detailAddr}, disId = #{disId}, latitude = #{latitude}, longitude = #{longitude} where id = #{id}")
	int updateEHotelBussinesszones(@Param("openTime") Date openTime, @Param("repairTime") Date repairTime, 
			@Param("retentionTime") String retentionTime, @Param("defaultLeaveTime") String defaultLeaveTime, 
			@Param("introduction") String introduction, @Param("hotelphone") String hotelphone,
			@Param("hotelName") String hotelName, @Param("detailAddr") String detailAddr, @Param("disId") int disId,
			@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("id") long id);
	
	/**
	 * 删除商圈信息
	 * @param businessZoneIds 商圈id集合
	 * @param hotelId 酒店id
	 * @return 受影响条数
	 */
	@Delete("delete from e_hotelbussinesszone where businessZoneId in (${businessZoneIds}) and hotelId = #{hotelId}")
	int deleteEHotelBussinesszones(@Param("businessZoneIds") String businessZoneIds, @Param("hotelId") long hotelId);
	
	/**
	 * 添加商圈信息
	 * @param businessZoneIds 商圈id集合
	 * @param hotelId 酒店id
	 * @return 受影响条数
	 */
	@Insert("insert into e_hotelbussinesszone (businessZoneId, hotelId) values (#{businessZoneId}, #{hotelId})")
	int addEHotelBussinesszones(@Param("businessZoneId") long businessZoneId, @Param("hotelId") long hotelId);
	
	/**
	 * 保存证件图片url
	 * @param businessLicenseFront 证件正面url
	 * @param idCardFront 身份证正面url
	 * @param idCardBack 身份证背面url
	 * @param id 酒店id
	 * @return 受影响条数
	 */
	@Update("update e_hotel set businessLicenseFront = #{businessLicenseFront}, idCardFront = #{idCardFront},"
			+ " idCardBack = #{idCardBack} where id = #{id}")
	int saveHotelCredentialsPic(@Param("businessLicenseFront") String businessLicenseFront, @Param("idCardFront") String idCardFront,
			@Param("idCardBack") String idCardBack, @Param("id") long id);
	
	/**
	 * 修改e表可用状态
	 * @param visible 状态只
	 * @param id 主键
	 * @return 受影响条数
	 */
	@Update("update e_hotel set visible = #{visible} where id = #{id}")
	int resetEHotelVisible(@Param("visible") String visible, @Param("id") long id);
	
	/**
	 * 修改t表可用状态
	 * @param visible 状态只
	 * @param id 主键
	 * @return 受影响条数
	 */
	@Update("update t_hotel set visible = #{visible} where id = #{id}")
	int resetTHotelVisible(@Param("visible") String visible, @Param("id") long id);
	
	/**
	 * 修改e表上下线
	 * @param visible 状态只
	 * @param online 上线状态
	 * @param id 主键
	 * @return 受影响条数
	 */
	@Update("update e_hotel set visible = #{visible} where id = #{id}")
	int resetEHotelline(@Param("visible") String visible, @Param("id") long id);
	
	/**
	 * 修改t表上下线
	 * @param visible 状态只
	 * @param online 上线状态
	 * @param id 主键
	 * @return 受影响条数
	 */
	@Update("update t_hotel set visible = #{visible}, online = #{online} where id = #{id}")
	int resetTHotelline(@Param("visible") String visible, @Param("online") String online, @Param("id") long id);
	
	/**
	 * 修改酒店图片信息
	 * @param hotelpic 酒店图片信息
	 * @param id 酒店id
	 * @return 受影响条数
	 */
	@Update("update e_hotel set hotelpic = #{hotelpic} where id = #{id}")
	int saveHotelInfoPic(@Param("hotelpic") String hotelpic, @Param("id") long id);
	
	/**
	 * 设置酒店规则
	 * @param hotelId
	 * @param rulecode
	 * @return
	 */
	@Update("update e_hotel set rulecode = #{rulecode} where id = #{id}")
	int saveHotelRule(@Param("id") long hotelId, @Param("rulecode") int rulecode);
	
	/**
	 * 获取酒店规则
	 * @param hotelId
	 * @return  rulecode
	 */
	@Select("select rulecode from e_hotel  where id = #{id}")
	int getHotelRule(@Param("id") long hotelId);
	
	/**
	 * h_hotel_rule
	 * @param hotelId
	 * @param type
	 * @return
	 */
	@Insert("insert into h_hotel_rule(hotelid,type,value,effectdate,createdate) values (#{hotelid},#{type},#{value},#{effectdate},#{createdate}) ")
	int insertHotelRule(@Param("hotelid")long hotelid,@Param("type")int type,@Param("value")String value,@Param("effectdate")String effectdate,@Param("createdate")Date createdate);
	/**
	 * 当B规则未生效前，可以将B规则修改回A规则
     * 将当前酒店的h_hotel_rule两条记录的生效时间置空
	 */
	@Update("UPDATE h_hotel_rule SET effectdate = NULL WHERE hotelid = #{hotelid} AND effectdate IS NOT NULL")
	int updateHotelRuleToA(@Param("hotelid") long hotelid);
	
	/**
	 * 设置是否阈值结算
	 * @param hotelId
	 * @param isThreshold
	 * @return
	 */
	@Update("update t_hotel set isThreshold = #{isThreshold},effectDate = #{effectDate} where id = #{id}")
	int setHotelThreshold(@Param("isThreshold") String isThreshold,@Param("effectDate") Date effectDate,@Param("id") long hotelId);
	
	
	/**
	 * 
	 */
	@Select("select * from t_hotel where visible ='T' and effectDate is not null")
	List<HmsTHotelModel> getValidHotel();
	/**
	 * 获取阈值结算方式
	 * @param hotelId
	 * @param isThreshold
	 * @return isThreshold下月设置  isCurrTheshold当月的
	 */
	@Select("select isThreshold from t_hotel where id = #{id}")
	String getHotelThreshold(@Param("id") long hotelId);
	/**
	 * 获取表t_district中的cityid
	 * @param disId
	 * @return Integer
	 * 
	 */
	@Select("select CityID from t_district WHERE id = ${disid}")
	Integer getCityId(@Param("disid") long disid);
	/**
	 * 获取表t_city中的Code
	 * @param cityid
	 * @return String
	 * 
	 */
	@Select("select Code from t_city where cityid = ${cityid}")
	String getCityCode(@Param("cityid") long cityid);
	
	/**
	 * 获得b_bill_confirm_check账单表isFreeze是否冻结字段
	 * @return
	 *
	 */
	@Select("select isFreeze from b_bill_confirm_check WHERE skey = ${disid}")
	String getIsFreeze(@Param("disid") long disid);

	/**
	 * 获得b_threshold_config表CONFIG_VALUE
	 * @return 
	 *
	 */

	@Select("SELECT CONFIG_VALUE from b_threshold_config where CITY_CODE = ${cityCode}")
	BigDecimal getConfigValue(@Param("cityCode") String cityCode);
	/**
	 * 获取b_bill_period的最后结账时间
	 * */
	@Select("SELECT max(endTime) FROM b_bill_period WHERE hotelId = ${hotelId} AND theMonth = ${theMonth}")
	Date getEndTime(@Param("hotelId") long hotelId, @Param("theMonth") int theMonth);
}
