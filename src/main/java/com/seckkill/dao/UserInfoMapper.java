package com.seckkill.dao;

import com.seckkill.domain.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(UserInfo record);
    int insertSelective(UserInfo record);
    UserInfo selectByTelphone(String telphone);
    UserInfo selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(UserInfo record);
    int updateByPrimaryKey(UserInfo record);
}