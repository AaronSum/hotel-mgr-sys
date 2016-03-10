package com.mk.hms.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.enums.HmsEHotelStatusEnum;
import com.mk.hms.enums.HmsTHotelOperateLogCheckTypeEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.HmsHOperateLogMapper;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.HmsHOperateLogModel;
import com.mk.hms.model.HmsTHotelModel;
import com.mk.hms.model.HmsTHotelOperateLogModel;
import com.mk.hms.model.MUser;
import com.mk.hms.model.THotelWithBLOBs;
import com.mk.hms.model.User;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.RequestUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;

/**
 * 日志 service
 * @author hdy
 *
 */
@Service
@Transactional
public class HmsHOperateLogService {

	@Autowired
	private HmsHOperateLogMapper hmsHOperateLogMapper;
	
	/**
	 * 添加操作日志
	 * @param log 日志对象
	 * @return 添加之后有主键日志对象
	 * @throws SessionTimeOutException 
	 */
	public HmsHOperateLogModel addLog(HmsHOperateLogModel log) throws SessionTimeOutException {
		// 获取当前登录用户
		LoginUser loginUser;
		try {
			loginUser = SessionUtils.getSessionLoginUser();
			User user = loginUser.getUser();
			if (null == user) {
				return null;
			}
			log.setUsercode(user.getLoginname());
			log.setUsername(user.getName());
			log.setUsertype(ContentUtils.HMS);
		} catch (SessionTimeOutException e) {
			MUser pmsUser = SessionUtils.getSessionPmsUser();
			// pms用户也不存在（没有登录）
			if (null == pmsUser) {
				return null;
			}
			log.setUsercode(pmsUser.getLoginname());
			log.setUsername(pmsUser.getName());
			log.setUsertype(ContentUtils.PMS);
		}
		log.setOperatetime(new Date());
		log.setIp(RequestUtils.getIp());
		hmsHOperateLogMapper.addLog(log);
		return log;
	}
	
	/**
	 * 审核酒店操作日志
	 * @param tHotel 上线酒店
	 * @param state 状态
	 * @return 受影响条数
	 * @throws SessionTimeOutException 
	 */
	public int addTHotelOperateLog(THotelWithBLOBs tHotel, int state) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		HmsTHotelOperateLogModel log = new HmsTHotelOperateLogModel();
		// 初次审核
		if (state == HmsEHotelStatusEnum.Submit.getValue()) {
			log.setChecktype(HmsTHotelOperateLogCheckTypeEnum.Init.getValue());
			log.setChecktypename(HmsTHotelOperateLogCheckTypeEnum.Init.getText());
		// 更新审核
		} else if (state == HmsEHotelStatusEnum.Editing.getValue()) {
			log.setChecktype(HmsTHotelOperateLogCheckTypeEnum.Update.getValue());
			log.setChecktypename(HmsTHotelOperateLogCheckTypeEnum.Update.getText());
		}
		log.setHotelid(tHotel.getId());
		log.setChecktime(new Date());
		log.setHotelname(tHotel.getHotelName());
		log.setUsercode(mUser.getId());
		log.setUsername(mUser.getName());
		return hmsHOperateLogMapper.addTHotelOperateLog(log);
	}
	
	/**
	 * 添加上线、下线操作日志
	 * @param tHotel t表数据
	 * @param state 状态编码
	 * @param stateText 状态名字
	 * @return 受影响条数
	 * @throws SessionTimeOutException 
	 */
	public int addTHotelOperateLogForOnline(HmsTHotelModel tHotel, int state, String stateText) throws SessionTimeOutException {
		MUser mUser = SessionUtils.getSessionPmsUser();
		HmsTHotelOperateLogModel log = new HmsTHotelOperateLogModel();
		log.setChecktype(state);
		log.setChecktypename(stateText);
		log.setHotelid(tHotel.getId());
		log.setChecktime(new Date());
		log.setHotelname(tHotel.getHotelName());
		log.setUsercode(mUser.getId());
		log.setUsername(mUser.getName());
		return hmsHOperateLogMapper.addTHotelOperateLog(log);
	}
	
	/**
	 * 添加修改房价操作日志
	 * @param tHotel t表数据
	 * @param state 状态编码
	 * @param stateText 状态名字
	 * @return 受影响条数
	 * @throws SessionTimeOutException 
	 */
	public int addTHotelOperateLogForRates(EHotel eHotel, int state, String stateText) throws SessionTimeOutException {
		User user = SessionUtils.getSessionLoginUser().getUser();
		HmsTHotelOperateLogModel log = new HmsTHotelOperateLogModel();
		log.setChecktype(state);
		log.setChecktypename(stateText);
		log.setHotelid(eHotel.getId());
		log.setChecktime(new Date());
		log.setHotelname(eHotel.getHotelName());
		log.setUsercode(user.getId());
		log.setUsername(user.getName());
		return hmsHOperateLogMapper.addTHotelOperateLog(log);
	}
}
