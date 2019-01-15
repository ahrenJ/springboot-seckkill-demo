package com.seckkill.error;

public interface CommonError {
    int getErrorCode();
    String getErrorMsg();
    CommonError setErrorMsg(String errorMsg);
}
