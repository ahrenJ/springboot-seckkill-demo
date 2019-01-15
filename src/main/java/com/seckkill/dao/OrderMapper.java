package com.seckkill.dao;

import com.seckkill.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}