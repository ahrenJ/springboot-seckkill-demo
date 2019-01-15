package com.seckkill.service;

import com.seckkill.domain.UserInfo;
import com.seckkill.dto.UserModel;
import com.seckkill.error.BusinessException;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel)throws BusinessException;
    UserModel validateLogin(String telphone,String encryptPassword)throws BusinessException;
}
