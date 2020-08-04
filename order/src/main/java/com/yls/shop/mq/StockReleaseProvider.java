package com.yls.shop.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author joe 2020-08-04 11:10
 */
@EnableBinding(OrderSink.class)
public class StockReleaseProvider {

    private final Logger logger = LoggerFactory.getLogger(StockReleaseProvider.class);

    @Autowired
    private OrderSink orderSink;

    public void stockRelease(StockTo stockTo) {
        logger.info("订单关闭成功，可以释放库存={}", stockTo);
        MessageBuilder<StockTo> stockToMessageBuilder = MessageBuilder.withPayload(stockTo);
        stockToMessageBuilder.setHeader("orderTimeOut", true);
        orderSink.stockReleaseOutput().send(stockToMessageBuilder.build());
    }
}
