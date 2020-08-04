package com.yls.shop.mq;

/**
 * @author joe 2020-08-04 10:37
 */
public class StockTo {

    private String orderId;
    private long lockNum;
    private String wareId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getLockNum() {
        return lockNum;
    }

    public void setLockNum(long lockNum) {
        this.lockNum = lockNum;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }

    @Override
    public String toString() {
        return "StockTo{" +
                "orderId='" + orderId + '\'' +
                ", lockNum=" + lockNum +
                ", wareId='" + wareId + '\'' +
                '}';
    }
}
