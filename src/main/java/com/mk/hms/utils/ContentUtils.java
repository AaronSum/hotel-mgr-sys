package com.mk.hms.utils;

/**
 * 常量工具类
 * @author hdy
 *
 */
public class ContentUtils {

	/**逗号*/
	public static final String CHAR_COMMA = ",";
	/**双引号*/
	public static final String CHAR_QUOTES = "\"";
	/**冒号*/
	public static final String CHAR_COLON = ":";
	/**get请求*/
	public static final String GET_METHOD = "get";
	/**post请求*/
	public static final String POST_METHOD = "post";
	/**合法二维码标示*/
	public static final String ALLOW_QRCODE_TAGS = "A, B, C, D, E, F, G";
	/**用户手机验证码缓存*/
	public static final String VERIFY_PHONE_CODE_CACHE = "VerifyPhoneCode";
	/**cache key 前缀*/
	public static final String CACHE_KEY_PREFIX = "cache_key_";
	/**session 中存已放注册用户*/
	public static final String REG_USER_IN_SESSION = "REG_USER";
	/**session 中存放已注册酒店*/
	public static final String REG_HOTEL_IN_SESSION = "REG_HOTEL";
	/**二维码内容前缀key*/
	public static final String QRCODE_CONTTENT_PREFIX = "qrCodeConttentPrefix";
	/**session 中存已放PMS用户*/
	public static final String PMS_USER_IN_SESSION = "PMS_USER";
	/**session 中存已放通过token登录hms系统的PMS用户*/
	public static final String TOKEN_4_PMS_USER_IN_SESSION = "TOKEN_4_PMS_USER";
	/**session 中存已放通过token登录hms系统的酒店*/
	public static final String TOKEN_HOTEL_IN_SESSION = "TOKEN_4_HMS_HOTEL";
	/**session 中存放PMS酒店信息*/
	public static final String PMS_HOTEL_IN_SESSION = "PMS_HOTEL";
	/**登录用户key值*/
	public static final String LOGINED_USER = "loginedUser";
	/**短信验证码发送地址*/
	public static final String SMS_MSG_ADDRESS = "smsMsgAddress";
	/**手机验证码信息内容前缀*/
	public static final String SMS_MSG_PREFIX = "smsMsgPrefix";
	/**短信验证码后缀*/
	public static final String SMS_MSG_SUFFIX = "smsMsgSuffix";
	/**超级验证码key*/
	public static final String ROOT_VERIFY_CODE = "rootVerifyCode";
	/**前台议价接口地址*/
	public static final String WAITER_BARGAIN_ADDRESS = "waiterBargainAddress";
	/**ots反查酒店房态接口地址key*/
	public static final String REFRESH_ROOM_ONLINESTATE_ADDRESS = "refreshRoomOnlineStateAddress";
	/**更新酒店mike价格key*/
	public static final String UPDATE_HOTEL_IMIKEPRICES_CACHE_ADDRESS = "updateHotelMikepricesCacheAddress";
	/**酒店审核添加thotel接口地址key*/
	public static final String CHECK_HOTEL_INSERT_ADDRESS = "checkHotelInsertAddress";
	/**酒店审核修改thotel接口地址key*/
	public static final String CHECK_HOTEL_UPDATE_ADDRESS = "checkHotelUpdateAddress";
	/**酒店审核删除thotel接口地址key*/
	public static final String CHECK_HOTEL_DELETE_ADDRESS = "checkHotelDeleteAddress";
	/**酒店上线接口地址key*/
	public static final String ONLINE_HOTEL_ON_ADDRESS = "onlineHotelOnAddress";
	/**酒店下线接口地址key*/
	public static final String ONLINE_HOTEL_OFF_ADDRESS = "onlineHotelOffAddress";
	/**安装旧pms地址key*/
	public static final String OLD_PMS_INSTALL_ADDRESS = "oldPmsInstallAdreess";
	/**安装新pms地址key*/
	public static final String NEW_PMS_INSTALL_ADDRESS = "newPmsInstallAdreess";
	/**七牛文件下载路径key*/
	public static final String QINIU_DOWNLOAD_ADDRESS = "qiniuDownloadAddress";
	/**七牛安全公钥key*/
	public static final String QINIU_ACCESS_KEY = "qiniuAccessKey";
	/**七牛安全私钥key*/
	public static final String QINIU_SECRET_KEY = "qiniuSecretKey";
	/**七牛下载凭证时间key*/
	public static final String QINIU_INVALIDATION_TIME = "qiniuInvalidationTime";
	/**七牛空间名称key*/
	public static final String QINIU_BUCKET = "qiniuBucket";
	/**主机地址*/
	public static final String HOST_ADDRESS = "hostAddress";
	/**hms操作用户类型*/
	public static final String HMS = "hms";
	/**PMS操作用户类型*/
	public static final String PMS = "pms";
	/**DES 明文密钥码key*/
	public static final String DES_KEY = "desKey";
	/**获取pms用户信息接口地址key*/
	public static final String PMS_USER_ADDRESS = "pmsUserAddress";
	/**酒店统计数据缓存*/
	public static final String HOTEL_STATISTICS_CACHE = "HotelStatistics";
	/**酒店统计数据账单缓存(单日)前缀*/
	public static final String CACHE_CHECKER_BILL_DAY_PREFIX = "cache_checker_bill_day_key_";
	/**酒店统计数据账单缓存(两周)前缀*/
	public static final String CACHE_CHECKER_BILL_2WEEKS_PREFIX = "cache_checker_bill_2weeks_key_";
	/**酒店统计数据最大房价缓存前缀*/
	public static final String CACHE_HOTEL_MAX_PRICE_PREFIX = "cache_hotel_max_price_key_";
	/**酒店统计数据60天日订单列表*/
	public static final String CACHE_HOTEL_ORDER_DAILY_LIST_60 = "cache_hotel_order_daily_list_60_key_";
	/**忘记密码用户session key*/
	public static final String RETRIEVE_PASSWORD_USER = "retrievePasswordUser";
	/**会员缓存key值*/
	public static final String U_MEMBER_CACHE = "cacheUMember";
	/**缓存中会员列表key*/
	public static final String CACHE_U_MEMBER_KEY = "cacheUMemberKey";
	/**pms审核用户角色code key值*/
	public static final String PMS_CHECK_USER_ROLE_CODE = "pmsCheckUserRoleCode";
	/**外来人员用户角色code key值*/
	public static final String FOREIGN_PERSON_ROLE_CODE = "foreignPersonRoleCode";
	/**切客优惠券id*/
	public static final int CHECK_PROMOTION_KEY = 4;
	/**眯客价t表fields*/
	public static final String T_BASEPRICE_FIELDS = "id, roomTypeId, price, subprice, subper, updateTime";
	/**眯客价e表fields*/
	public static final String E_BASEPRICE_FIELDS = "id, roomTypeId, price, subprice, subper, updateTime";
	/**省份表fields*/
	public static final String T_PROVINCE_FIELDS = "ProID, Code, ProName, ProSort, ProRemark, latitude, longitude";
	/**城市表fields*/
	public static final String T_CITY_FIELDS = "cityid, Code, CityName, ProID, CitySort, latitude, longitude";
	/**区县表fields*/
	public static final String T_DISTRICT_FIELDS = "id, Code, DisName, CityID, DisSort, latitude, longitude";
	/**超级验证码key*/
	public static final String RULE_B_URL = "ruleBURL";
	/**购物车key*/
	public static final String HMS_SHOPPING_CART_DATA = "hms_shopping_cart_data_";
	/**profile对应的label*/
	public static final String SYS_LABEL = "sysLabel";
	/**      **/
	public static final String GEN_BILLCONFIRMCHECKS = "genBillConfirmChecks";
	/** 账单生成起始时间 */
	public static final String BILL_START_TIME = "startTime";
	/** 账单生成结束时间 */
	public static final String BILL_END_TIME = "endTime";

	public static final String OTS_RESTFUL_ADDRESS = "ots.restful.address";
	/**Object to long**/
    public static long getLong(Object toLong,long def){
    	long rtnLong = def;
    	if(null!=toLong){
    		try{
    			rtnLong =  Long.parseLong(toLong.toString());    			
    		}catch(Exception e){
    			rtnLong = def;
    		}
    	}
    	return rtnLong;
    }
	/**Object to int**/
    public static int getInt(Object toInt,int def){
    	int rtnInt = def;
    	if(null!=toInt){
    		try{
        		rtnInt = Integer.parseInt(toInt.toString());    			
    		}catch(Exception e){
    			rtnInt = def;
    		}
    	}
    	return rtnInt;
    }
	/**Object to string**/
    public static String getStr(Object toStr,String def){
    	String rtnStr = def;
    	if(null!=toStr){
    		rtnStr = toStr.toString();
    	}
    	return rtnStr;
    }
}
