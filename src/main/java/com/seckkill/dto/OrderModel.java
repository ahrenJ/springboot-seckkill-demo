package com.seckkill.dto;

import java.math.BigDecimal;

//Trade Model
public class OrderModel {

    //Order number,such as 2018012300017283
    private String id;
    //User's id of trade
    private Integer userId;
    //Item's id of trade
    private Integer itemId;
    //Promo ID(If Not Null)
    private Integer promoId;
    //Price of promo or normal item
    private BigDecimal itemPrice;
    //Number of trade
    private Integer amount;
    //Amount money of trade
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}
