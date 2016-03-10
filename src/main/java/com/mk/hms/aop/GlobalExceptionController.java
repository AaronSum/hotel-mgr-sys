package com.mk.hms.aop;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.enums.ErrorCodeEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.utils.SessionUtils;


/**
 * 异常信息
 *
 * @author hdy
 */
@ControllerAdvice
public class GlobalExceptionController {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

    /**
     * session 超时异常处理
     *
     * @param stot session超时
     * @return 跳转页面
     */
    @ExceptionHandler(SessionTimeOutException.class)
    @ResponseBody
    public OutModel handleSessionException(SessionTimeOutException stot) {
        OutModel out = new OutModel(true);
        out.setErrorCode(stot.getErrorCode());
        out.setErrorMsg(ErrorCodeEnum.getByValue(stot.getErrorCode()).getText());
        out.setSystem(true);
        if (StringUtils.isNoneBlank(SessionUtils.getToken4PmsUser())) {
        	Map<String, Object> attr = new HashMap<String, Object>();
        	attr.put("isToken", true);
        	out.setAttribute(attr);
        }
        logger.error(ErrorCodeEnum.getByValue(stot.getErrorCode()).getText(), stot);

        return out;
    }
}
