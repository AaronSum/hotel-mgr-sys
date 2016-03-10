package com.mk.hms.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mk.hms.model.HmsGroupHotelModel;
import com.mk.hms.model.HmsGroupModel;
import com.mk.hms.model.HmsHQrcodeModel;
import com.mk.hms.model.HmsRoleModel;
import com.mk.hms.model.HmsUserModel;
import com.mk.hms.model.HmsUserRoleModel;

/**
 * hms用户mapper接口
 * @author hdy
 *
 */
public interface HmsUserMapper{
	
	/**
	 * 获取某一个用户信息
	 * @param loginname  登录名
	 * @return 用户对象
	 */
	@Select("select * from h_user where loginname = #{loginname} and visible = 'T'")
	HmsUserModel findHmsUserByLoginName(@Param("loginname") String loginname);
	
	/**
	 * 获取指定loginname获取用户信息
	 * @param loginnames  登录名分割字符串
	 * @return 用户对象列表
	 */
	@Select("select * from h_user where loginname in (${loginnames}) and visible = 'T'")
	List<HmsUserModel> findHmsUserByLoginNames(@Param("loginnames") String loginnames);
	
	/**
	 * 获取指定id获取用户信息
	 * @param ids  id分割字符串
	 * @return 用户对象列表
	 */
	@Select("select * from h_user where id in (${ids}) and visible = 'T'")
	List<HmsUserModel> findHmsUserByIds(@Param("ids") String ids);
	
	/**
	 * 获取某一个用户信息
	 * @param id  主键
	 * @return 用户对象
	 */
	@Select("select * from h_user where id = #{id} and visible = 'T'")
	HmsUserModel findHmsUserById(@Param("id") long id);
	
	/**
	 * 删除用户信息
	 * @param id 用户主键
	 * @return 受影响条数
	 */
	@Delete("update h_user set visible = 'F', endtime = #{endtime} where id = #{id} and visible = 'T'")
	int updateHmsUserById(@Param("id") long id, @Param("endtime") Date endtime);
	
	/**
	 * 获取分组
	 * @param id 分组id
	 * @return
	 */
	@Select("select * from h_group where id = #{id}")
	HmsGroupModel findGroup(@Param("id") long id);
	
	/**
	 * 获取中间表数据列表
	 * @param groupid 分组id
	 * @return 数据列表
	 */
	@Select("select * from h_group_hotel where groupid = #{groupid}")
	List<HmsGroupHotelModel> findGroupHotelList(@Param("groupid") long groupid);
	
	/**
	 * 获取中间表数据列表
	 * @param groupid 分组id
	 * @return 数据列表
	 */
	@Select("select * from h_group_hotel where hotelid = #{hotelid}")
	HmsGroupHotelModel findGroupByHotelId(@Param("hotelid") long hotelid);
	
	
	/**
	 * 获取用户角色列表
	 * @param userid 用户主键
	 * @return 用户角色列表
	 */
	@Select("select * from h_user_role where userid = #{userid}")
	List<HmsUserRoleModel> findUserRoleList(@Param("userid") long userid);
	
	/**
	 * 获取用户角色列表
	 * @param roleids 角色主键
	 * @return 用户角色列表
	 */
	@Select("select * from h_user_role where roleid in (${roleids})")
	List<HmsUserRoleModel> findUserRoleListByRoleIds(@Param("roleids") String roleids);
	
	/**
	 * 根据用户id，角色id查询用户角色表信息
	 * @param roleid 角色id
	 * @param userid 用户id
	 * @return 用户角色信息
	 */
	@Select("select * from h_user_role where roleid = #{roleid} and userid = #{userid}")
	HmsUserRoleModel findUserRoleByRoleIdAndUserId(@Param("roleid") long roleid, @Param("userid") long userid);
	
	/**
	 * 删除用户角色信息
	 * @param roleid 角色id
	 * @param userid 用户id
	 * @return 受影响条数
	 */
	@Delete("delete from h_user_role where roleid = #{roleid} and userid = #{userid}")
	int deleteUserRoleByRoleIdAndUserId(@Param("roleid") long roleid, @Param("userid") long userid);
	
	/**
	 * 删除用户角色信息
	 * @param userid 用户id
	 * @return 受影响条数
	 */
	@Delete("delete from h_user_role where userid = #{userid}")
	int deleteUserRoleByUserId(@Param("userid") long userid);
	
	/**
	 * 获取角色列表
	 * @param roleIds 角色主键
	 * @return 角色列表
	 */
	@Select("select * from h_role where id in (${roleIds})")
	List<HmsRoleModel> findRoles(@Param("roleIds") String roleIds);
	
	/**
	 * 获取该酒店用户列表
	 * @param hotelid 酒店id
	 * @return 酒店用户列表数据
	 */
	@Select("select * from h_role where hotelid = #{hotelid}")
	List<HmsRoleModel> findHotelRoleIds(@Param("hotelid") long hotelid);
	
	/**
	 * 根据酒店id，角色类型获取酒店角色信息
	 * @param hotelid 酒店id
	 * @param type 类型
	 * @return 酒店角色信息
	 */
	@Select("select * from h_role where hotelid = #{hotelid} and type = #{type}")
	HmsRoleModel findHotelRoleByHotelIdAndType(@Param("hotelid") long hotelid, @Param("type") int type);
	
	/**
	 * 获取角色列表
	 * @param id 用户主键
	 * @return 角色列表
	 */
	@Select("select * from h_role where id = #{id}")
	List<HmsRoleModel> findMyRoles(@Param("id") long id);
	
	/**
	 * 修改用户密码
	 * @param psw 密码
	 * @param id 用户id
	 * @param loginname 登录名
	 * @return 受影响条数
	 */
	@Update("update h_user set psw = #{psw} where id = #{id} and loginname = #{loginname}")
	int modifyUserPwd(@Param("psw") String psw, @Param("id") long id, @Param("loginname") String loginname);
	
	/**
	 * 修改PMS用户密码
	 * @param psw 密码
	 * @param id 用户id
	 * @param loginname 登录名
	 * @return 受影响条数
	 */
	@Update("update m_user set psw = #{psw} where id = #{id}")
	int modifyPmsUserPwd(@Param("psw") String psw, @Param("id") long id);
	
	/**
	 * 添加酒店用户信息
	 * @param loginname 登录名
	 * @param psw 密码
	 * @param groupid 组名
	 * @param name 昵称
	 * @return 受影响条数
	 */
	@Insert("insert into h_user(loginname, psw, groupid, name, visible, begintime) values (#{loginname}, #{psw}, #{groupid}, #{name}, #{visible}, #{begintime})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int addHotelUser(HmsUserModel user);
	
	/**
	 * 插入分组数据
	 * @param group 分组对象
	 * @return 受影响条数
	 */
	@Insert("insert into h_group(regphone, name) values (#{regphone}, #{name})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache = true)
	int addGroup(HmsGroupModel group);
	
	/**
	 * 添加角色
	 * @param role 角色实体
	 * @return 受影响条数
	 */
	@Insert("insert into h_role(hotelid, name, type) values(#{hotelid}, #{name}, #{type})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache = true)
	int addRole(HmsRoleModel role);
	
	/**
	 * 添加用户角色信息
	 * @param userid 用户id
	 * @param roleid 角色id
	 * @return 添加完毕之后实体
	 */
	@Insert("insert into h_user_role(userid, roleid) values(#{userid}, #{roleid})")
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache=true)
	int addHotelUserRole(HmsUserRoleModel userRole);
	
	/**
	 * 获取二维码数据
	 * @param tag 二维码标示
	 * @param hotelid 酒店id
	 * @return 二维码对象
	 */
	@Select("select * from h_qrcode where tag = #{tag} and hotelid = #{hotelid}")
	HmsHQrcodeModel findQrcodeByTag(@Param("tag") String tag, @Param("hotelid") long hotelid);
	
	/**
	 * 获取二维码数据
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @return 二维码对象
	 */
	@Select("select * from h_qrcode where userid = #{userid} and hotelid = #{hotelid}")
	HmsHQrcodeModel findQrcodeByUserId(@Param(value = "userid") long userid, @Param(value = "hotelid") long hotelid);
	
	/**
	 * 绑定二维码标示
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param tag 二维码标示
	 * @return 受影响条数
	 */
	@Update("update h_qrcode set userid = #{userid} where hotelid = #{hotelid} and tag = #{tag}")
	int updateQrcode(@Param("userid") long userid, @Param("hotelid") long hotelid, @Param("tag") String tag);
	
	/**
	 * 解除绑定二维码标示
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param userid 用户id
	 * @return 受影响条数
	 */
	@Update("update h_qrcode set userid = null where hotelid = #{hotelid} and tag = #{tag} and userid = #{userid}")
	int unbindQrcode(@Param("hotelid") long hotelid, @Param("tag") String tag, @Param("userid") long userid);
	
	/**
	 * 修改用户二维码绑定标示
	 * @param tag 二维码标示
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param oldTag 旧tag标示
	 * @return 受影响条数
	 */
	@Update("update h_qrcode set tag = #{tag} where userid = #{userid} and hotelid = #{hotelid} and tag = #{oldTag}")
	int updateQrcodeUser(@Param("tag") String tag, @Param("userid") long userid, @Param("hotelid") long hotelid,
			@Param("oldTag") String oldTag);

	/**
	 * 绑定二维码标示
	 * @param userid 用户id
	 * @param hotelid 酒店id
	 * @param tag 二维码标示
	 * @return 受影响条数
	 */
	@Update("update h_qrcode set tag = #{tag} where hotelid = #{hotelid} and tag = #{oldTag} and userid is null")
	int updateNewQrcode(@Param("hotelid") long hotelid, @Param("tag") String tag,@Param("oldTag") String oldTag);
	
	/**
	 * 获取可用二维码列表
	 * @param hotelid 酒店id
	 * @param userid 用户id
	 * @return 二维码列表
	 */
	@Select("select * from h_qrcode where hotelid = #{hotelid} and (userid = #{userid} or userid is null) order by tag")
	List<HmsHQrcodeModel> findAvailableQrocodes(@Param("hotelid") long hotelid, @Param("userid") long userid);
	
	/**
	 * 获取可用二维码列表
	 * @param hotelid 酒店id
	 * @return 二维码列表
	 */
	@Select("select * from h_qrcode where hotelid = #{hotelid} and userid is null order by tag")
	List<HmsHQrcodeModel> findAvailableQrocodesByHotel(@Param("hotelid") long hotelid);
	
	/**
	 * 修改用户昵称
	 * @param name 昵称
	 * @param loginname 登录名
	 * @return 受影响条数
	 */
	@Update("update h_user set name = #{name} where loginname = #{loginname}")
	int modifyUser(@Param("name") String name, @Param("loginname") String loginname);
}
