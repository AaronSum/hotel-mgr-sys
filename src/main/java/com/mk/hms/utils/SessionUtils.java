package com.mk.hms.utils;

import javax.servlet.http.HttpSession;

import com.mk.hms.enums.ErrorCodeEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.Group;
import com.mk.hms.model.MUser;
import com.mk.hms.model.User;
import com.mk.hms.model.VerifyPhoneModel;
import com.mk.hms.view.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会话用工具类
 * @author hdy
 *
 */
public class SessionUtils{
	private static final Logger logger = LoggerFactory.getLogger(SessionUtils.class);
	/**
	 * 获取登录用户信息
	 * @throws SessionTimeOutException 
	 */
	public static LoginUser getSessionLoginUser() throws SessionTimeOutException {
		Object obj = getSession().getAttribute(ContentUtils.LOGINED_USER);
		if (null == obj) {
			logger.info("getSessionLoginUser getAttribute loginedUser is null");
			throw new SessionTimeOutException(ErrorCodeEnum.ERROR_SESSION_2001.getValue(), new Throwable());
		}
		logger.info("getSessionLoginUser getAttribute loginedUser value is not empty");
		return (LoginUser) obj;
	}
	
	/**
	 * 设置登录用户信息
	 * @param user 登录用户信息
	 */
	public static void setSessionLoginUser(LoginUser user) {
		getSession().setAttribute(ContentUtils.LOGINED_USER, user);
	}
	
	/**
	 * 获取pms用户登录信息
	 * @return 登录用户信息对象
	 * @throws SessionTimeOutException 
	 */
	public static MUser getSessionPmsUser() throws SessionTimeOutException {
		Object obj = getSession().getAttribute(ContentUtils.PMS_USER_IN_SESSION);
		if (null == obj) {
			logger.info("getSessionPmsUser getAttribute loginedUser is null");
			throw new SessionTimeOutException(ErrorCodeEnum.ERROR_SESSION_2001.getValue(), new Throwable());
		}
		logger.info("getSessionPmsUser getAttribute loginedUser value is not empty");
		return (MUser) obj;
	}
	
	/**
	 * 设施pms用户登录信息
	 * @param mUser pms登录用户
	 */
	public static void setSessionPmsUser(MUser mUser) {
		if(mUser == null){
			logger.info("setSessionPmsUser mUser is null");
		}
		getSession().setAttribute(ContentUtils.PMS_USER_IN_SESSION, mUser);
	}
	
	/**
	 *  设置pms通过token登录hms用户token值
	 * @param token token字符串
	 */
	public static void setToken4PmsUser(String token) {
		getSession().setAttribute(ContentUtils.TOKEN_4_PMS_USER_IN_SESSION, token);
	}
	
	/**
	 * 获取token字符串
	 * @return token字符串
	 */
	public static String getToken4PmsUser() {
		return (String) getSession().getAttribute(ContentUtils.TOKEN_4_PMS_USER_IN_SESSION);
	}
	
	/**
	 * 获取pms酒店信息
	 * @return pms酒店信息对象
	 * @throws SessionTimeOutException 
	 */
	public static EHotelWithBLOBs getSessionPmsHotel() throws SessionTimeOutException {
		Object obj = getSession().getAttribute(ContentUtils.PMS_HOTEL_IN_SESSION);
		if (null == obj) {
			throw new SessionTimeOutException(ErrorCodeEnum.ERROR_SESSION_2001.getValue(), new Throwable());
		}
		return (EHotelWithBLOBs) obj; 
	}
	
	/**
	 * pms酒店信息
	 * @param pms酒店信息
	 */
	public static void setSessionPmsHotel(EHotelWithBLOBs hotel) {
		getSession().setAttribute(ContentUtils.PMS_HOTEL_IN_SESSION, hotel);
	}
	
	/**
	 * 添加注册用户
	 * @param user 注册用户实体
	 */
	public static void setRegUser(User user) {
		getSession().setAttribute(ContentUtils.REG_USER_IN_SESSION, user);
	}
	
	/**
	 * 获取注册用户
	 * @return 注册用户实体
	 * @throws SessionTimeOutException 
	 */
	public static User getRegUser() throws SessionTimeOutException {
		Object obj = getSession().getAttribute(ContentUtils.REG_USER_IN_SESSION);
		if (null == obj) {
			throw new SessionTimeOutException(ErrorCodeEnum.ERROR_SESSION_2001.getValue(), new Throwable());
		}
		return (User) obj;
	}
	
	/**
	 * 获取注册酒店
	 * @return 注册酒店实体
	 * @throws SessionTimeOutException 
	 */
	public static EHotelWithBLOBs getRegHotel() throws SessionTimeOutException {
		Object obj = getSession().getAttribute(ContentUtils.REG_HOTEL_IN_SESSION);
		if (null == obj) {
			throw new SessionTimeOutException(ErrorCodeEnum.ERROR_SESSION_2001.getValue(), new Throwable());
		}
		return (EHotelWithBLOBs) obj;
	}
	
	/**
	 * 添加注册酒店
	 * @param eHotel 注册酒店实体
	 */
	public static void setRegHotel(EHotelWithBLOBs eHotel) {
		getSession().setAttribute(ContentUtils.REG_HOTEL_IN_SESSION, eHotel);
	}
	
	/**
	 * 添加找回密码用户
	 * @param retrievePasswordUser 找回密码用户实体
	 */
	public static void setRetrievePasswordUser(VerifyPhoneModel retrievePasswordUser) {
		getSession().setAttribute(ContentUtils.RETRIEVE_PASSWORD_USER, retrievePasswordUser);
	}
	
	/**
	 * 获取找回密码用户
	 * @return 注册用户实体
	 * @throws SessionTimeOutException 
	 */
	public static VerifyPhoneModel getRetrievePasswordUser() throws SessionTimeOutException {
		Object obj = getSession().getAttribute(ContentUtils.RETRIEVE_PASSWORD_USER);
		return (VerifyPhoneModel) obj;
	}
	
	/**
	 * 是否是店长权限
	 * @return 是否是店长权限
	 * @throws SessionTimeOutException 
	 */
	public static boolean isHotelManager() throws SessionTimeOutException {
		LoginUser loginUser = getSessionLoginUser();
		Group group = loginUser.getGroup();
		User user = loginUser.getUser();
		if (null != group) {
			// 店长
			if(user.getLoginname().equals(group.getRegphone())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  获取session对象
	 * @return session对象
	 */
	public static HttpSession getSession() {
		/*HttpSession session = RequestUtils.getRequest().getSession();
		if (null == session || null == session.getAttribute(ContentUtils.PMS_USER_IN_SESSION)
				|| null == session.getAttribute(ContentUtils.LOGINED_USER)) {
			RequestDispatcher dispatcher = RequestUtils.getRequest().getRequestDispatcher("/webpage/login/msg.jsp?status=3");
			try {
				dispatcher.forward(RequestUtils.getRequest(), RequestUtils.getResponse());
			} catch (Exception e) {
				SessionUtils.logger.error("获取session异常：" + e.getMessage(), e);
			}
		}*/
		return RequestUtils.getRequest().getSession();
	}
	
	/**
	 * 获取当前酒店，包括酒店老板登录、pms用户登录
	 * @return 酒店信息
	 * @throws SessionTimeOutException 
	 */
	public static EHotelWithBLOBs getThisHotel() throws SessionTimeOutException {
		LoginUser loginUser;
		EHotelWithBLOBs thisHotel = new EHotelWithBLOBs();
		try {
			loginUser = getSessionLoginUser();
			thisHotel = loginUser.getThisHotel();
		} catch (SessionTimeOutException e) {
			thisHotel = getSessionPmsHotel();
		}
		return thisHotel;
	}
	
	/**
	 *  设置token中的hotel
	 * @param eh
	 */
	public static void setTokenHotel(EHotelWithBLOBs eh) {
		getSession().setAttribute(ContentUtils.TOKEN_HOTEL_IN_SESSION, eh);
	}
	
	/**
	 * 获取token中hotel对象
	 * @return hms酒店对象
	 */
	public static EHotelWithBLOBs getTokenHotel() {
		Object obj = getSession().getAttribute(ContentUtils.TOKEN_HOTEL_IN_SESSION);
		if (null != obj) {
			return (EHotelWithBLOBs) obj;
		}
		return null;
	}
	
	/**
	 * 获取当前酒店Id
	 * @return 酒店Id
	 * @throws SessionTimeOutException 
	 */
    public static long getThisHotelId() throws SessionTimeOutException{
    	long hotelId = -1;
    	EHotelWithBLOBs thisHotel = getThisHotel();
    	if(null!=thisHotel){
    		hotelId = thisHotel.getId();
    	}
    	return hotelId;
    }
}
