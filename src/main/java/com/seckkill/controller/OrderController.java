package com.seckkill.controller;

import com.seckkill.dto.OrderModel;
import com.seckkill.dto.UserModel;
import com.seckkill.error.BusinessException;
import com.seckkill.error.EnumBussinessError;
import com.seckkill.reponse.CommonReturnType;
import com.seckkill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowedHeaders = "true",origins = "*")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createorder",method = RequestMethod.POST,consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType ceateOrder(@RequestParam("itemId")Integer itemId,
                                       @RequestParam("amount")Integer amount,
                                       @RequestParam(value = "promoId",required = false)Integer promoId)
                                        throws BusinessException {
        Boolean isLogin= (Boolean) httpServletRequest.getSession().getAttribute("LOGIN_SUCCESS");
        if (isLogin==null||isLogin.booleanValue()==false){
            throw new BusinessException(EnumBussinessError.USER_NOT_LOGIN,"用户未登录，无法提交订单");
        }
        //获取用户信息
        UserModel userModel= (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel=orderService.createOrder(userModel.getId(),itemId,promoId,amount);

        return CommonReturnType.create(orderModel);
    }
}
