package com.seckkill.dao;

import com.seckkill.domain.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    List<Item> selectAllItem();

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    void increaseSales(@Param("id")Integer id,@Param("amount")Integer amount);
}