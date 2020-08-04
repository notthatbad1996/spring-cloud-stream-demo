package com.yls.shop.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author shuaishuai
 */
@EnableBinding(OrderSink.class)
public class CreateOrderProvider {

    private final Logger logger = LoggerFactory.getLogger(CreateOrderProvider.class);

    private final OrderSink processor;

    public CreateOrderProvider(OrderSink processor) {
        this.processor = processor;
    }

    public boolean createOrderMessage(OrderTo order) {
        logger.info("创建订单={}", order);
        return processor.delayOrderOutput().send(MessageBuilder.withPayload(order).build());
    }
}
