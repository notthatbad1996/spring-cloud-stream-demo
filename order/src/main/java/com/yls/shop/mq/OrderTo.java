package com.yls.shop.mq;

import java.time.LocalDateTime;

public class OrderTo {

    private String orderId;
    private LocalDateTime createTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OrderTo{" +
                "orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
