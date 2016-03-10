package com.mk.hms.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.log.Execute4CtrlLog;
import com.mk.hms.utils.HmsDateUtils;

@Aspect
public class RequestLogAOP {

	private static final Logger logger = LoggerFactory.getLogger(RequestLogAOP.class);
	
	/*
	 * @ModelAttribute public void record(Model model, HttpServletRequest
	 * request) { SessionUser currUser =
	 * SpringContextUtil.getCurrentLoginUser(); if (null == currUser) { return;
	 * } String url = request.getRequestURL().toString();
	 *
	 * Map<String, String> map = new HashMap<String, String>();
	 * Enumeration<String> enums = request.getParameterNames(); while
	 * (enums.hasMoreElements()) { String paramName = (String)
	 * enums.nextElement(); String paramValue = request.getParameter(paramName);
	 *
	 * map.put(paramName, paramValue); }
	 *
	 * RequestLogTask reqLogTask = new RequestLogTask(currUser, url, map);
	 *
	 * HmsThreadPool.getDefaultPool().execute(reqLogTask); }
	 */

	public RequestLogAOP() {
		this.getClass();
	}

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controller() {
	}
	
	/*@AfterReturning("controller()")
	public void doAccessCheck(JoinPoint joinPoint) {
		SessionUser currUser = SpringContextUtil.getCurrentLoginUser();
		if (null == currUser) {
			return;
		}
		execute(joinPoint);
	}*/
	
	/**
     * 环绕通知需要携带ProceedingJoinPoint类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法。
     * 而且环绕通知必须有返回值，返回值即为目标方法的返回值
	 * @throws Throwable 
     */
    @Around("controller()")
    public Object aroundMethod(ProceedingJoinPoint pjd) throws Throwable {
        Object result = null;
        String methodName = pjd.getSignature().getName();
        // 执行目标方法
     // 前置通知
    	long beginTime = System.currentTimeMillis();
    	// 执行方法
        result = pjd.proceed();
        // 后置通知
        long time = System.currentTimeMillis() - beginTime;
        if (time >= 1000) {
        	RequestLogAOP.logger.info("======>>>>>> 方法" + methodName + "执行时间大于1秒: " + time + "ms,调用时间：" + HmsDateUtils.getDatetime(beginTime) + " <<<<<<======");
        }
        
        execute(pjd, result);
        return result;
    }
    
	/**
	 * 执行方法
	 * @param pjd
	 * @param result 返回类型
	 * @throws SessionTimeOutException 
	 */
	private void execute(ProceedingJoinPoint pjd, Object result) throws SessionTimeOutException {
		// 策略价
		String methodName = pjd.getSignature().getName();
		if (methodName.equals("addPrice")) {
			Execute4CtrlLog.addLog4AddPrice(pjd, result);
		// 眯客价
		} else if (methodName.equals("saveBasePrice")) {
			Execute4CtrlLog.addLog4SaveBasePrice(pjd, result);
		// pms 获取token	
		} else if (methodName.equals("getToken4Pms")) {
			Execute4CtrlLog.addLog4GetToken4Pms(pjd, result);
		// pms通过token登录	
		} else if (methodName.equals("loginHms4Pms")) {
			Execute4CtrlLog.addLog4LoginHms4Pms(pjd, result);
		}
	}
}
