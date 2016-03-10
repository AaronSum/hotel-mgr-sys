package com.mk.hms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.Qrcode;
import com.mk.hms.service.I2DimCodesService;
import com.mk.hms.utils.I2DimCodesUtils;
import com.mk.hms.utils.SessionUtils;

/***
 * 
 * @author hdy
 *
 */
@Controller
@RequestMapping("/i2dimcodes")
public class I2DimCodesController {
	
	@Autowired
	private I2DimCodesService i2DimCodesService = null;
	
	/***
	 * 获取用户信息列表
	 * @param hotelId 酒店ID
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	/*@RequestMapping(params = "userList")
	@ResponseBody
	public List<Map<String, String>> getUserInfoList() {
		RequestUtils.getParameters4Angular();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 5; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("tag", "A");
			map.put("qrcontent", "http://www.imike.com?hotelId=123&userId=abc");
			list.add(map);
		}
		return list;
	}*/
	
	/**
	 * 获取二维码流数据
	 * @param content 内容
	 * @param tag 二维码类型
	 */
	@RequestMapping("/userI2DimCode")
	@ResponseBody
	public void getUserInfoI2DimCode(String content, String tag , HttpServletRequest request, HttpServletResponse response) {
		I2DimCodesUtils.drawI2DimCode2OutputStream(content, tag, request, response);
	}
	 
	/**
	 * 获取当前酒店二维码数据
	 * @return 二维码列表
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/printI2DimCodes")
	@ResponseBody
	public List<Qrcode> printI2DimCodes() throws SessionTimeOutException {
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		return this.getI2DimCodesService().findI2DimCodes(thisHotel.getId());
	}

	private I2DimCodesService getI2DimCodesService() {
		return i2DimCodesService;
	}
	
}
