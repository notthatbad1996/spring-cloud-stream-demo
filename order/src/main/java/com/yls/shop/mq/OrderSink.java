package com.yls.shop.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @author shuaishuai
 */
@Component
public interface OrderSink {

    String DELAY_OUTPUT = "order-delay-output-queue";

    @Output(OrderSink.DELAY_OUTPUT)
    MessageChannel delayOrderOutput();

    String STOCK_RELEASE_DELAY_OUTPUT = "stock-release-output-queue";

    @Output(OrderSink.STOCK_RELEASE_DELAY_OUTPUT)
    MessageChannel stockReleaseOutput();

    String DELAY_INPUT = "order-delay-input-queue";

    @Input(OrderSink.DELAY_INPUT)
    SubscribableChannel delayOrderInput();

}
