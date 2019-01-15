package com.seckkill.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    private boolean isError=false;
    private Map<String,String> errorMsgMap=new HashMap<>();

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    //返回包含错误信息的字符串
    public String getErrorMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
