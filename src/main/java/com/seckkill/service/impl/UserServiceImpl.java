package com.seckkill.service.impl;

import com.seckkill.dao.UserInfoMapper;
import com.seckkill.dao.UserPasswordMapper;
import com.seckkill.domain.UserInfo;
import com.seckkill.domain.UserPassword;
import com.seckkill.dto.UserModel;
import com.seckkill.error.BusinessException;
import com.seckkill.error.EnumBussinessError;
import com.seckkill.service.UserService;
import com.seckkill.validator.ValidationResult;
import com.seckkill.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.xml.validation.Validator;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserPasswordMapper passwordMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserInfo userInfo=userInfoMapper.selectByPrimaryKey(id);
        if(userInfo==null){
            return null;
        }
        UserPassword password=passwordMapper.selectByUserId(userInfo.getId());
        return convertFromDataObject(userInfo,password);
    }

    @Override
    @Transactional
    public void register(UserModel userModel)throws BusinessException {
        if (userModel==null){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR);
        }

        ValidationResult result=validator.validate(userModel);
        if (result.isError()){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,result.getErrorMsg());
        }
        UserInfo userInfo=convertUserInfoFromModel(userModel);
        try{
            userInfoMapper.insertSelective(userInfo);
        }catch (DuplicateKeyException e){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"手机号已被注册");
        }

        userModel.setId(userInfo.getId());
        UserPassword userPassword=convertUserPasswordFromModel(userModel);
        passwordMapper.insertSelective(userPassword);
    }

    @Override
    public UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException {
        //通过用户手机获取用户信息
        UserInfo userInfo=userInfoMapper.selectByTelphone(telphone);
        if (userInfo==null){
            throw new BusinessException(EnumBussinessError.USER_LOGIN_FAIL);
        }
        UserPassword userPassword=passwordMapper.selectByUserId(userInfo.getId());
        UserModel userModel=convertFromDataObject(userInfo,userPassword);
        //密码对比
        if (!StringUtils.equals(userModel.getEncrptPassword(),encryptPassword)){
            throw new BusinessException(EnumBussinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserPassword convertUserPasswordFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserPassword password=new UserPassword();
        password.setEncrptPassword(userModel.getEncrptPassword());
        password.setUserId(userModel.getId());
        return password;
    }

    private UserInfo convertUserInfoFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(userModel,userInfo);
        return userInfo;
    }

    private UserModel convertFromDataObject(UserInfo userInfo, UserPassword password){
        if(userInfo==null){
            return null;
        }
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(userInfo,userModel);
        if(password==null){
            return  null;
        }
        userModel.setEncrptPassword(password.getEncrptPassword());
        return userModel;
    }
}
