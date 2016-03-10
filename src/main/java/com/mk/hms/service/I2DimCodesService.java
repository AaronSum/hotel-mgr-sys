package com.mk.hms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.enums.HmsRuleCodeEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.QrcodeMapper;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.Qrcode;
import com.mk.hms.model.QrcodeCriteria;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.SessionUtils;

/**
 * 二维码service
 * @author hdy
 *
 */
@Service
@Transactional
public class I2DimCodesService {

	@Autowired
	private QrcodeMapper qrcodeMapper = null;
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(I2DimCodesService.class);
	
	private static int RULE_A_CODE =  1001;
	private static int RULE_B_CODE =  1002;
	/**
	 * 获取二维码打印数据列表
	 * @return 数据列表
	 * @throws SessionTimeOutException 
	 */
	public List<Qrcode> findI2DimCodeList() throws SessionTimeOutException {
		/*========2015年7月23日增加B规则逻辑  hao.jiang========*/
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		//初始化二维码信息
		List<Qrcode> qrcodes= new ArrayList<Qrcode>();
		//B规则判断
		if(thisHotel.getRulecode() == RULE_B_CODE){
			//默认返回一个B规则 二维码信息
			logger.info("B规则二维码生成处理");
			Qrcode qrcode = new Qrcode();
			qrcode.setHotelid(thisHotel.getId());
			//读取配置
			String content = "";
			try {
				content = HmsFileUtils.getSysContentItem(ContentUtils.RULE_B_URL);
			} catch (IOException e) {
				logger.error("获取B规则二维码信息报错",e);
			}
			//设置二维码地址
			qrcode.setContent(content);
			qrcodes.add(qrcode);
		}else if (thisHotel.getRulecode() == RULE_A_CODE){
			//A规则获取二维码逻辑
			logger.info("A规则二维码生成处理");
			qrcodes =  this.findI2DimCodes(thisHotel.getId());
		}

		return qrcodes;
	}

	/**
	 * 获取二维码
	 * @param hotelId 酒店id
	 * @return 二维码列表
	 */
	public List<Qrcode> findI2DimCodes(long hotelId) {
		//查询T表rulecode
		int ruleCode = userService.findRuleCode(hotelId);
		if(ruleCode == HmsRuleCodeEnum.B_RULE.getRuleCode()){
			List<Qrcode> qrcodes =new ArrayList<Qrcode>();
			//默认返回一个B规则 二维码信息
			logger.info("B规则二维码生成处理");
			Qrcode qrcode = new Qrcode();
			qrcode.setHotelid(hotelId);
			//读取配置
			String content = "";
			try {
				content = HmsFileUtils.getSysContentItem(ContentUtils.RULE_B_URL);
			} catch (IOException e) {
				logger.error("获取B规则二维码信息报错",e);
			}
			//设置二维码地址
			qrcode.setContent(content);
			qrcodes.add(qrcode);
			return qrcodes;
		}else{
			QrcodeCriteria example = new QrcodeCriteria();
			example.createCriteria().andHotelidEqualTo(hotelId);
			return this.getQrcodeMapper().selectByExample(example);
		}
	}
	
	private QrcodeMapper getQrcodeMapper() {
		return qrcodeMapper;
	}
}
