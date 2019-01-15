package com.seckkill.service.impl;

import com.seckkill.dao.OrderMapper;
import com.seckkill.dao.SequenceMapper;
import com.seckkill.domain.Order;
import com.seckkill.domain.Sequence;
import com.seckkill.dto.ItemModel;
import com.seckkill.dto.OrderModel;
import com.seckkill.dto.UserModel;
import com.seckkill.error.BusinessException;
import com.seckkill.error.EnumBussinessError;
import com.seckkill.service.ItemService;
import com.seckkill.service.OrderService;
import com.seckkill.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId,Integer promoId,Integer amount) throws BusinessException {
        //校验订单参数是否合法:用户是否存在、商品是否存在、商品购买的数量是否合法
        ItemModel itemModel=itemService.getItemById(itemId);
        if (itemModel==null){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"Item Don't Exist");
        }
        UserModel userModel=userService.getUserById(userId);
        if (itemModel==null){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"User Dont't Exist");
        }
        if (amount<=0||amount>99){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"Amount Is Illegal");
        }

        //校验商品是否属于秒杀活动
        if (promoId!=null){
            //校验对应秒杀活动是否存在该商品
            if (promoId.intValue()!=itemModel.getPromoModel().getId().intValue()){
                throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"秒杀活动信息错误");
            }else if(itemModel.getPromoModel().getStatus()!=2){
                //检验秒杀活动是否正在进行
                throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"秒杀活动未开始");
            }
        }

        //提交订单后减库存
        boolean orderStatus=itemService.declineStock(itemId,amount);
        if (!orderStatus){
            throw new BusinessException(EnumBussinessError.STOCK_NOT_ENOUGH);
        }

        //订单写入数据库
        OrderModel orderModel=new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setPromoId(promoId);
        orderModel.setAmount(amount);
        if (promoId!=null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        //3.1.generate order number
        orderModel.setId(generateOrderNumber());
        Order order=convertFromOrderModel(orderModel);
        orderMapper.insertSelective(order);
        //3.2.increase item's sales
        itemService.increaseSales(itemId,amount);
        //4.return to front
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected String generateOrderNumber(){
        //include 16 bit
        StringBuilder builder=new StringBuilder();
        //1-8:Date
        LocalDateTime now=LocalDateTime.now();
        String nowDate=now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        builder.append(nowDate);
        //9-14:Auto Increment Sequence
        //get current value
        int currentValue=0;
        Sequence sequence=sequenceMapper.getSequenceByName("order_info");
        currentValue=sequence.getCurrentValue();
        sequence.setCurrentValue(sequence.getCurrentValue()+sequence.getStep());
        sequenceMapper.updateByPrimaryKeySelective(sequence);

        String strVal=String.valueOf(currentValue);
        for (int i=0;i<6-strVal.length();i++){
            builder.append(0);
        }
        builder.append(strVal);
        //15-16:The number of Database and Tabel
        builder.append("00");
        return builder.toString();
    }

    private Order convertFromOrderModel(OrderModel orderModel){
        if (orderModel==null){
            return null;
        }
        Order order=new Order();
        BeanUtils.copyProperties(orderModel,order);
        order.setItemPrice(orderModel.getItemPrice().doubleValue());
        order.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return order;
    }
}
