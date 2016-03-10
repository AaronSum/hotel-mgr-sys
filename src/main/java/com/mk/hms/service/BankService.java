package com.mk.hms.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.GroupHotelMapper;
import com.mk.hms.mapper.GroupMapper;
import com.mk.hms.mapper.HotelBankLogMapper;
import com.mk.hms.mapper.HotelBankMapper;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.Group;
import com.mk.hms.model.GroupHotel;
import com.mk.hms.model.GroupHotelCriteria;
import com.mk.hms.model.HotelBank;
import com.mk.hms.model.HotelBankCriteria;
import com.mk.hms.model.HotelBankLog;
import com.mk.hms.model.OutModel;
import com.mk.hms.model.User;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HmsRedisCacheUtils;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.SaveBank;

/**
 * 酒店银行账号信息
 * @author hdy
 *
 */
@Service
@Transactional
public class BankService {

	@Autowired
	private HotelBankMapper hotelBankMapper = null;
	
	@Autowired
	private GroupMapper groupMapper = null;
	
	@Autowired
	private GroupHotelMapper groupHotelMapper = null;
	
	@Autowired
	private HotelBankLogMapper hotelBankLogMapper = null;
	
	@Autowired
	private SmsService smsService = null;

	 /**
     * 根据酒店Id获取酒店银行账号信息
     * @return 房型列表
	 * @throws SessionTimeOutException 
     */
	public HotelBank find(long hotelId) throws SessionTimeOutException{
		long id = 0;
		if (hotelId > 0) {
			id = hotelId;
		} else {
			EHotel thisHotel = SessionUtils.getThisHotel();
	    	if(null == thisHotel){
	    		return null;
	    	}
	    	id = thisHotel.getId();
		}
    	HotelBankCriteria example = new HotelBankCriteria();
    	example.createCriteria().andHotelIdEqualTo(id);
    	List<HotelBank> list = this.getHotelBankMapper().selectByExample(example);
    	//判断账号信息
    	if(list!=null && list.size() > 0){
    		HotelBank bank = list.get(0);
    		//校验数据是否完整
    		if(bank.getAccount()==null          || bank.getAccount().equals("")
    		   ||bank.getBankBranch() == null   || bank.getBankBranch().equals("")
    		   ||bank.getName() == null         || bank.getName().equals("")
    		   ||bank.getTransferType() == null || bank.getTransferType().equals("")
    		   ||bank.getBank() == null         || bank.getBank().equals(""))
    		{
    			return null;
    		}
    	}else{
    		//没查到
    		return null;
    	}
    	//返回第一条
        return list.get(0);
    }
    
	/**
     * 保存酒店银行账号信息
     * @return
	 * @throws SessionTimeOutException 
	 * @throws IOException 
     */
    public OutModel save(SaveBank sb) throws SessionTimeOutException, IOException {	
		OutModel out = new OutModel(false);
		LoginUser loginUserModel = SessionUtils.getSessionLoginUser();
		EHotel thisHotel;
		User userModel;
		if (null == loginUserModel) {
			out.setErrorMsg("用户登录已过期");
			return out;
		} else {
			thisHotel = loginUserModel.getThisHotel();
			userModel = loginUserModel.getUser();
		}
		if (HmsStringUtils.isBlank(sb.getName()) || HmsStringUtils.isBlank(sb.getAccount())
				|| HmsStringUtils.isBlank(sb.getBank()) || HmsStringUtils.isBlank(sb.getBankBranch())
				|| HmsStringUtils.isBlank(sb.getTransferType()) || HmsStringUtils.isBlank(sb.getVerifyCode())) {
			out.setErrorMsg("传入了非法空数据");
    		return out;
		}
    	//验证码验证
    	String phoneNum = this.getPhoneNo(thisHotel.getId());
    	VerifyPhoneModel verifyPhoneModel = HmsRedisCacheUtils.getVerifyPhoneObjInCache(phoneNum);
		if (null == verifyPhoneModel) {
			if(!HmsStringUtils.isRootVerifyCode(Integer.parseInt(sb.getVerifyCode())) ){
				out.setErrorMsg("请先获取验证码");
				return out;				
			}
		} else if (!sb.getVerifyCode().equals(String.valueOf(verifyPhoneModel.getVerifyCode()))) {
			out.setErrorMsg("验证码错误");
			return out;
		}
		if (this.saveBank(thisHotel.getId(),sb, userModel) > 0) {
			out.setSuccess(true);
		}
        return out;
    }
    
    /**
	 * 发送手机验证码
	 * @return 发送手机验证码
     * @throws IOException 
     * @throws SessionTimeOutException 
	 */
	public OutModel sendVerifyCode() throws IOException, SessionTimeOutException {    	
		OutModel out = new OutModel(false);
    	EHotel thisHotel = SessionUtils.getThisHotel();
    	if(null == thisHotel){
    		out.setErrorMsg("登陆已经过期");
    		return out;
    	}    	
    	String phoneNum = this.getPhoneNo(thisHotel.getId());
		// 手机号是否合法
		if (!HmsStringUtils.isAllowPhoneNo(phoneNum)) {
			out.setErrorMsg("手机号码不合法");
			return out;
		}
		// 获取缓存中是否存在该手机号验证码
		VerifyPhoneModel verifyPhone = HmsRedisCacheUtils.getVerifyPhoneObjInCache(phoneNum);
		if (null != verifyPhone) {
			out.setErrorMsg("验证码还未过期可继续使用");
			return out;
		}
		VerifyPhoneModel newVerifyPhone = new VerifyPhoneModel();
		newVerifyPhone.setPhnoeNo(phoneNum);
		newVerifyPhone.setVerifyCode(HmsStringUtils.getRandomNum(6));
		// 调用发送验证码接口
		String message = "尊敬的业主您此次修改收款账户的验证码是：" + newVerifyPhone.getVerifyCode()
				+ HmsFileUtils.getSysContentItem(ContentUtils.SMS_MSG_SUFFIX);
		// 发送验证码码
		out = this.getSmsService().sendSmsMsg(newVerifyPhone, message);
		if (!out.isSuccess()) {
			return out;
		}
		out.setSuccess(true);
		return out;
	}
	
    /**
     * 保存一条新的银行账号信息
     * @param hotelId 酒店Id
     * @param name 用户名
     * @param account 账号
     * @param bank 开户行
     * @param bankBranch 支行
     * @param transferType 转帐类型
     * @param userModel 用户
     * @return
     * @throws SessionTimeOutException 
     */
    private int saveBank(long hotelId, SaveBank sb ,User user) throws SessionTimeOutException{
    	HotelBank hotelBankModel = new HotelBank();
    	hotelBankModel.setHotelId(hotelId);
    	hotelBankModel.setName(sb.getName());
    	hotelBankModel.setAccount(sb.getAccount());
    	hotelBankModel.setBank(sb.getBank());
    	hotelBankModel.setBankBranch(sb.getBankBranch());
    	hotelBankModel.setTransferType(sb.getTransferType());
    	HotelBank exits = this.find(hotelId);
    	int result = 0;
    	if(null != exits){
    		hotelBankModel.setId(exits.getId());
    		result =  this.getHotelBankMapper().updateByPrimaryKey(hotelBankModel);
		}else{
			result =  this.getHotelBankMapper().insert(hotelBankModel);
			
		}
		// 添加操作日志
		if (result > 0) {
			this.saveHotelBankLog(hotelId, user, hotelBankModel, exits);
		}
    	return result;
    }

    /**
     * 保存log日志
     * @param hotelId 酒店id
     * @param user 用户对象
     * @param hotelBankModel log对象
     * @param exits 银行账号信息对象
     */
	private void saveHotelBankLog(long hotelId, User user,
			HotelBank hotelBankModel, HotelBank exits) {
		HotelBankLog hotelBankLogModel = new HotelBankLog();
		hotelBankLogModel.setHotelId(hotelId);
		if (null != exits) {
			JSONObject oldObj = JSONObject.fromObject(exits);
			oldObj.remove("id");
			oldObj.remove("hotelId");
			hotelBankLogModel.setOldVal(oldObj.toString());
		}
		JSONObject newObj = JSONObject.fromObject(hotelBankModel);
		newObj.remove("id");
		newObj.remove("hotelId");
		hotelBankLogModel.setNewVal(newObj.toString());
		hotelBankLogModel.setCreateTime(new Date());
		hotelBankLogModel.setUserCode(user.getLoginname());
		hotelBankLogModel.setUserName(user.getName());
		this.getHotelBankLogMapper().insert(hotelBankLogModel);
	};
    
    /**
     * 保存银行账号信息
     * @param hb 银行账号对象
     * @param hotelId 酒店id
     * @return 受影响条数
     */
/*    private int saveBaknInfo(HotelBank hb, long hotelId) {
    	if (null == hb) {
    		HotelBankCriteria example = new HotelBankCriteria();
    		example.createCriteria().andHotelIdEqualTo(hotelId);
    		List<HotelBank> list = this.getHotelBankMapper().selectByExample(example);
    		HotelBank info = list.size() > 0 ? list.get(0) : null;
    		if (null != info) {
    			hb.setId(info.getId());
    			return this.getHotelBankMapper().updateByPrimaryKey(hb);
    		}
    		return this.getHotelBankMapper().updateByExample(hb, example);
    	} else {
    		return this.getHotelBankMapper().insert(hb);
    	}
    }*/
    
    /**
     * 获取酒店老板注册电话
     * @param hotelId 酒店id
     * @return 注册电话
     */
    private String getPhoneNo (long hotelId) {
    	GroupHotel gh = this.getGroupByHotelId(hotelId);
    	if (null != gh) {
    		Group g = getGroupById (gh.getGroupid());
    		if (null != g) {
    			return g.getRegphone();
    		}
    	}
    	return null;
    }
    
    /**
     * 获取酒店分组 对象
     * @param hotelId 酒店id
     * @return 酒店分组对象
     */
    private GroupHotel getGroupByHotelId(long hotelId) {
    	GroupHotelCriteria example = new GroupHotelCriteria();
    	example.createCriteria().andHotelidEqualTo(hotelId);
    	List<GroupHotel> list = this.getGroupHotelMapper().selectByExample(example);
    	return list.size() > 0 ? list.get(0) : null; 
    }
    
    /**
     * 获取分组信息
     * @param groupId 分组主键
     * @return 分组对象
     */
    private Group getGroupById (long groupId) {
    	return this.getGroupMapper().selectByPrimaryKey(groupId);
    }
    
	private HotelBankMapper getHotelBankMapper() {
		return hotelBankMapper;
	}

	private GroupMapper getGroupMapper() {
		return groupMapper;
	}

	private GroupHotelMapper getGroupHotelMapper() {
		return groupHotelMapper;
	}

	private HotelBankLogMapper getHotelBankLogMapper() {
		return hotelBankLogMapper;
	}

	private SmsService getSmsService() {
		return smsService;
	}
	
}
