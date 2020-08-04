package com.yls.shop.mq;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @author shuaishuai
 */
@EnableBinding(OrderSink.class)
public class ReleaseOrderConsumer {

    private final Logger logger = LoggerFactory.getLogger(ReleaseOrderConsumer.class);

    @Autowired
    private StockReleaseProvider stockReleaseProvider;

    @StreamListener(OrderSink.DELAY_INPUT)
    public void process(Message<OrderTo> order,
                        @Header(AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        OrderTo payload = order.getPayload();
        logger.info("关闭订单={}", payload);
        // 手动ack
        channel.basicAck(deliveryTag, false);

        //
        StockTo stockTo = new StockTo();
        stockTo.setLockNum(5);
        stockTo.setOrderId(payload.getOrderId());
        stockTo.setWareId("666");
        stockReleaseProvider.stockRelease(stockTo);
    }
}
