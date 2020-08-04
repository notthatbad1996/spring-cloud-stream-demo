package com.yls.shop.mq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @author joe 2020-08-04 10:39
 */
@EnableBinding(StockSink.class)
public class StockListener {

    private final Logger logger = LoggerFactory.getLogger(StockListener.class);

    /**
     * 接收来自订单服务的库存解锁消息，查询订单实时状态之后才解锁
     * --为了避免网络延迟导致库存解锁失败，所以订单关闭时会给库存服务发送库存解锁消息
     */
    @StreamListener(value = StockSink.STOCK_RELEASE_DELAY_INPUT,
            condition = "headers['orderTimeOut']==true")
    public void stockLocked(Message<StockTo> stockTo,
                            @Header(AmqpHeaders.CHANNEL) Channel channel,
                            @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        logger.info("====[订单关闭，准备解锁库存]====:{}", stockTo.getPayload().toString());
        // 手动ack
        channel.basicAck(deliveryTag, false);
    }

    /**
     * 接收来自延迟队列的库存解锁消息，查询订单实时状态之后才解锁
     */
    @StreamListener(value = StockSink.STOCK_RELEASE_DELAY_INPUT,
            condition = "headers['orderTimeOut']==false")
    public void stockLocked1(Message<OrderTo> orderTo,
                             @Header(AmqpHeaders.CHANNEL) Channel channel,
                             @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        logger.info("====[准备解锁死信队列的订单库存]====:{}", orderTo.getPayload().toString());
        // 手动ack
        channel.basicAck(deliveryTag, false);
    }

}
