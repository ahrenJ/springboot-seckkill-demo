package com.seckkill.controller;

import com.seckkill.error.BusinessException;
import com.seckkill.error.EnumBussinessError;
import com.seckkill.reponse.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    //定义异常处理器
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception e){
        Map<String,Object> responseData=new HashMap<>();
        if (e instanceof BusinessException){
            BusinessException businessException=(BusinessException)e;
            responseData.put("errorCode",businessException.getErrorCode());
            responseData.put("errorMsg",businessException.getErrorMsg());
        }else {
            responseData.put("errorCode",EnumBussinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMsg",EnumBussinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
