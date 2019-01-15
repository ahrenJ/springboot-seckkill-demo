package com.seckkill.dao;

import com.seckkill.domain.UserPassword;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(UserPassword record);
    int insertSelective(UserPassword record);
    UserPassword selectByPrimaryKey(Integer id);
    UserPassword selectByUserId(Integer userId);
    int updateByPrimaryKeySelective(UserPassword record);
    int updateByPrimaryKey(UserPassword record);
}