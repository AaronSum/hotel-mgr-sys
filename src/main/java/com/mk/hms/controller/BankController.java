package com.mk.hms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.HotelBank;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.BankService;
import com.mk.hms.view.SaveBank;

/**
 * 银行 控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/bank")
public class BankController {

	@Autowired
	private BankService  bankService = null;

	 /**
     * 根据酒店Id获取酒店银行账号信息
     * @return 房型列表
	 * @throws SessionTimeOutException 
     */
    @RequestMapping("/find")
    @ResponseBody
    public HotelBank find() throws SessionTimeOutException{
    	return this.getBankService().find(0);
    }
    
    /**
     * 保存酒店银行账号信息
     * @return
     * @throws SessionTimeOutException 
     * @throws IOException 
     */
    @RequestMapping("/save")
    @ResponseBody
    public OutModel save(SaveBank sb) throws SessionTimeOutException, IOException{
    	return this.getBankService().save(sb);
    }
    
    /**
	 * 发送手机验证码
	 * @return 发送手机验证码
     * @throws IOException 
     * @throws SessionTimeOutException 
	 */
	@RequestMapping("/verifyCode")
	@ResponseBody
	public OutModel sendVerifyCode() throws IOException, SessionTimeOutException {
		return this.getBankService().sendVerifyCode();
	}
	
	private BankService getBankService() {
		return bankService;
	}
	
}
