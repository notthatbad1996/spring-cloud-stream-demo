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
public interface StockSink {

    String STOCK_LOCKED_DELAY_OUTPUT = "stock-locked-delay-output-queue";

    @Output(StockSink.STOCK_LOCKED_DELAY_OUTPUT)
    MessageChannel delayStockLockedOutput();

    String STOCK_RELEASE_DELAY_INPUT = "stock-release-delay-input-queue";

    @Input(StockSink.STOCK_RELEASE_DELAY_INPUT)
    SubscribableChannel delayStockReleaseInput();

}
