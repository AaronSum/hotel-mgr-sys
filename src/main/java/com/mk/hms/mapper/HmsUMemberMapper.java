package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsUMemberModel;

/**
 * 会员mapper接口
 * @author hdy
 *
 */
public interface HmsUMemberMapper {

	/**
	 * 获取会员id
	 * @param mid 会员id
	 * @return 会员对象
	 */
	@Select("select * from u_member where mid = #{mid}")
	HmsUMemberModel findUMemberById(@Param("mid") long mid);
	
	/**
	 * 获取会员
	 * @return 会员对象
	 */
	@Select("select * from u_member")
	List<HmsUMemberModel> findUMembers();
}
