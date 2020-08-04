package com.yls.shop.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author joe 2020-08-04 11:40
 */
@EnableBinding(StockSink.class)
public class StockLockProvider {

    private final Logger logger = LoggerFactory.getLogger(StockLockProvider.class);

    @Autowired
    private StockSink stockSink;

    /**
     * 接收来自订单服务的库存锁定消息：
     * 2分钟之后放入死信队列，因为订单1分钟过期，在此之前此前用户可能会改变订单状态
     */
    public void lockStock(OrderTo order) {
        logger.info("------[锁定]库存-----:{}", order.toString());
        MessageBuilder<OrderTo> orderToMessageBuilder = MessageBuilder.withPayload(order);
        orderToMessageBuilder.setHeader("orderTimeOut", false);
        stockSink.delayStockLockedOutput().send(orderToMessageBuilder.build());
    }
}
