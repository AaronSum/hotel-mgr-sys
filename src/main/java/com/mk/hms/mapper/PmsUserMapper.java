package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsMRoleModel;
import com.mk.hms.model.HmsMRoleUser;
import com.mk.hms.model.HmsMUserModel;
import com.mk.hms.model.PmsUserModel;

/**
 * pms用户接口实现
 * @author hdy
 *
 */
public interface PmsUserMapper {

	/**
	 * 根据登录名查看pms用户
	 * @param loginname 登录名
	 * @return pms用户实体
	 */
	@Select("select * from m_user where loginname = #{loginname}")
	HmsMUserModel findPmsUserByLoginName(@Param("loginname") String loginname);
	
	/**
	 * 获取pms用户角色对象
	 * @param userid 用户id
	 * @return 用户角色对象
	 */
	@Select("select * from m_role_user where userid = #{userid}")
	HmsMRoleUser findPmsUserRoleByUserId(@Param("userid") long userid);
	
	/**
	 * 获取pms角色对象
	 * @param id 角色id
	 * @return 角色对象
	 */
	@Select("select * from m_role where id = #{id}")
	HmsMRoleModel findPmsRoleById(@Param("id") long id);
	
	/**
	 * 获取session
	 * @return pms用户列表
	 */
	@Select("select loginname, name from m_user")
	List<PmsUserModel> findPmsUsers();
	
	/**
	 * 获取pms用户信息
	 * @return pms用户
	 */
	@Select("select * from m_user where phone = #{phone}")
	HmsMUserModel findPmsUserByPhone(@Param("phone") String phone);
}
