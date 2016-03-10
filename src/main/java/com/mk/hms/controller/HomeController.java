package com.mk.hms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.HomeService;
import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;

/**
 * 首页控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private HomeService homeService = null;
	
	/**
	 * get echart data
	 * @return
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/homeDatas")
	@ResponseBody
	public OutModel loadHomeDatas() throws SessionTimeOutException{
		return this.getHomeService().loadHomeDatas();
	}

	/**
	 * get echart data
	 * @return
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/checkInfo")
	@ResponseBody
	public OutModel getSomeDateCheckInfo(String beginDateStr, String endDateStr,String ruleCode) throws SessionTimeOutException{
		if("1001".equals(ruleCode)){
			return this.getHomeService().getSomeDateCheckInfo(beginDateStr, endDateStr);
		}else{
			return this.getHomeService().getAllCheckInfo(beginDateStr, endDateStr);
		}
	}
	
	@RequestMapping("/getID2code")
	@ResponseBody
	public String getID2code() throws IOException{
		String bRuleConfig = "weixin.imike.com";
		//B规则二维码显示
		bRuleConfig = HmsFileUtils.getSysContentItem(ContentUtils.RULE_B_URL);
		return bRuleConfig;
	}
	
	private HomeService getHomeService() {
		return homeService;
	}
}
