package com.fangcloud.noah.dao.entity.api;

import java.math.BigDecimal;

/**
 * Created by chenke on  Date: 15-12-7
 */
public class UsOrder {

    private Integer id;
    private String orderSeq;
    private String userId;
    private String userMobile;

    private BigDecimal shouldPayPrice;

    private BigDecimal extraFeePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getShouldPayPrice() {
        return shouldPayPrice;
    }

    public void setShouldPayPrice(BigDecimal shouldPayPrice) {
        this.shouldPayPrice = shouldPayPrice;
    }

    public BigDecimal getExtraFeePrice() {
        return extraFeePrice;
    }

    public void setExtraFeePrice(BigDecimal extraFeePrice) {
        this.extraFeePrice = extraFeePrice;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
