package com.mk.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.mapper.HmsUMemberMapper;
import com.mk.hms.model.HmsUMemberModel;

/**
 * 会员service
 * @author hdy
 *
 */
@Service
@Transactional
public class HmsUMemberService {

	@Autowired
	private HmsUMemberMapper hmsUMemberMapper;
	
	/**
	 * 通过id，获取会员信息
	 * @param mid 会员id
	 * @return 会员用户id
	 */
	public HmsUMemberModel findUMemberById(long mid) {
		return hmsUMemberMapper.findUMemberById(mid);
	}
	
	/**
	 * 获取会员信息
	 * @return 会员列表
	 */
	public List<HmsUMemberModel> findUMerbers() {
		return hmsUMemberMapper.findUMembers();
	}
}
