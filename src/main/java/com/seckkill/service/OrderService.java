package com.seckkill.service;

import com.seckkill.dto.OrderModel;
import com.seckkill.error.BusinessException;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
