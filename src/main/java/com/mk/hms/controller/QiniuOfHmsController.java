package com.mk.hms.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.EHotel;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HmsEHotelModel;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.HmsHotelMessageService;
import com.mk.hms.utils.HmsQiniuUtils;
import com.mk.hms.utils.LogUtils;
import com.mk.hms.utils.SessionUtils;
import com.qiniu.util.StringUtils;

/**
 * 七牛 上传
 * @author wangchen
 */
@Controller
@RequestMapping(value="/qiniu", produces = MediaType.APPLICATION_JSON_VALUE)
public class QiniuOfHmsController {
	
	private static final Logger logger = LoggerFactory.getLogger(QiniuOfHmsController.class);
	
	@Autowired
	private HmsHotelMessageService hmsHotelMessageService;
	
	/**
	 * 获取简单上传授权
	 * @return  jeson data
	 */
	@RequestMapping("/getSimpleUploadToken")
	@ResponseBody
	public OutModel getSimpleUploadToken(){
		// LoginUser loginUser = SessionUtils.getSessionLoginUser();
		OutModel out = new OutModel(false);
		
//		if (loginUser == null) {
//			LogUtils.error("用户未登录");
//			out.setErrorMsg("用户未登录");
//			return out;
//		}
		out.setSuccess(true);
		//out.setAttribute(new HashMap<String, String>().put("ticket", HmsQiniuUtils.generateSimpleUploadTicket()));
		out.setAttribute(HmsQiniuUtils.generateSimpleUploadTicket());
		return out;
	}
	
	/**
	 * 获取下载授权
	 * @return  jeson data
	 * @throws Exception 
	 */
	@RequestMapping("/getDownloadUrl")
	@ResponseBody
	public OutModel getDownloadUrl(String key, String mode, String w, String h) throws Exception{
		String treatMethod = "";
		if (!StringUtils.isNullOrEmpty(mode)) {
			if (!StringUtils.isNullOrEmpty(w)) {
				treatMethod = treatMethod + "/w/" + w;
			}
			if ( !StringUtils.isNullOrEmpty(h)) {
				treatMethod = treatMethod + "/h/" + h;
			}
			if (!StringUtils.isNullOrEmpty(treatMethod)) {
				treatMethod = "?imageView2/" + mode + treatMethod;
			}
		}
		OutModel out = new OutModel(false);
		if (StringUtils.isNullOrEmpty(key)) {
			QiniuOfHmsController.logger.error("七牛获取下载url，key为空");
			out.setErrorMsg("key为空");
			return out;
		}
		out.setSuccess(true);
		out.setAttribute(HmsQiniuUtils.generateDownloadTicket(key, treatMethod));
		return out;
	}

	/**
	 * 添加酒店营业执照、身份证照片(注册阶段)
	 * @return url状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/saveHotelCredentialsPic")
	@ResponseBody
	public OutModel saveHotelCredentialsPic(String businessLicenseFront, String idCardFront, String idCardBack) throws SessionTimeOutException {
		Map<String, String> logs = new HashMap<String, String>();
		
		EHotelWithBLOBs eHotel = SessionUtils.getRegHotel();
		eHotel.setBusinessLicenseFront(businessLicenseFront);
		eHotel.setIdCardFront(idCardFront);
		eHotel.setIdCardBack(idCardBack);
		int count = hmsHotelMessageService.saveHotelCredentialsPic(eHotel);
		
		logs.put("成功数量", Long.toString(count));
		logs.put("营业执照", businessLicenseFront);
		logs.put("身份证正面", idCardFront);
		logs.put("身份证背面", idCardBack);
		LogUtils.logStepPlusMap(logger, "保存完照片", logs);
		
		OutModel out = new OutModel(false);
		out.setErrorMsg("获取图片信息异常");
		if (count == 1) {
			out.setSuccess(true);
		} 
		LogUtils.logStep(logger, "结束请求");
		return out;
	}
	
	/**
	 * 修改酒店营业执照、身份证照片(酒店资料维护阶段)
	 * @return url状态
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/updateHotelCredentialsPic")
	@ResponseBody
	public OutModel updateHotelCredentialsPic(String businessLicenseFront, String idCardFront, String idCardBack) throws SessionTimeOutException {
		Map<String, String> logs = new HashMap<String, String>();
		
		EHotelWithBLOBs eHotel = SessionUtils.getThisHotel();
		eHotel.setBusinessLicenseFront(businessLicenseFront);
		eHotel.setIdCardFront(idCardFront);
		eHotel.setIdCardBack(idCardBack);
		int count = hmsHotelMessageService.saveHotelCredentialsPic(eHotel);
		
		logs.put("成功数量", Long.toString(count));
		logs.put("营业执照", businessLicenseFront);
		logs.put("身份证正面", idCardFront);
		logs.put("身份证背面", idCardBack);
		LogUtils.logStepPlusMap(logger, "保存完照片", logs);
		
		OutModel out = new OutModel(false);
		out.setErrorMsg("获取图片信息异常");
		if (count == 1) {
			out.setSuccess(true);
		} 
		LogUtils.logStep(logger, "结束请求");
		return out;
	}
}
