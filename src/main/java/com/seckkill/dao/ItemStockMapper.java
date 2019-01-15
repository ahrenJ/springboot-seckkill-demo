package com.seckkill.dao;

import com.seckkill.domain.ItemStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStockMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ItemStock record);

    int insertSelective(ItemStock record);

    ItemStock selectByPrimaryKey(Integer id);

    ItemStock selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(ItemStock record);

    int declineStock(@Param("itemId")Integer itemId,@Param("amount")Integer amount);

    int updateByPrimaryKey(ItemStock record);
}