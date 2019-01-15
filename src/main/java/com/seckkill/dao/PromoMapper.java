package com.seckkill.dao;

import com.seckkill.domain.Promo;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Promo record);

    int insertSelective(Promo record);

    Promo selectByPrimaryKey(Integer id);

    Promo selectByItemId(Integer itemid);

    int updateByPrimaryKeySelective(Promo record);

    int updateByPrimaryKey(Promo record);
}