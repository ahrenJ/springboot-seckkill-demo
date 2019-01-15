package com.seckkill.service.impl;

import com.seckkill.dao.PromoMapper;
import com.seckkill.domain.Promo;
import com.seckkill.dto.PromoModel;
import com.seckkill.service.PromoService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@CacheConfig(cacheNames = "promo")
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoMapper promoMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        Promo promo=promoMapper.selectByItemId(itemId);
        PromoModel promoModel=convertFromPromo(promo);
        if (promoModel==null){
            return null;
        }

        if (promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if (promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    private PromoModel convertFromPromo(Promo promo){
        if (promo==null){
            return null;
        }
        PromoModel promoModel=new PromoModel();
        BeanUtils.copyProperties(promo,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promo.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promo.getStartDate()));
        promoModel.setEndDate(new DateTime(promo.getEndDate()));

        return promoModel;
    }
}
