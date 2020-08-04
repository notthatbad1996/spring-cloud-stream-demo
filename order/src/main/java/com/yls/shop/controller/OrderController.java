package com.yls.shop.controller;

import com.yls.shop.mq.CreateOrderProvider;
import com.yls.shop.mq.OrderTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CreateOrderProvider createOrderProvider;

    @GetMapping("/create")
    public OrderTo createOrder() {
        String orderId = UUID.randomUUID().toString();
        OrderTo order = new OrderTo();
        order.setOrderId(orderId);
        order.setCreateTime(LocalDateTime.now());
        createOrderProvider.createOrderMessage(order);

        lockStock(order);
        // 锁定库存
        return order;
    }

    public boolean lockStock(OrderTo order) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/stock/lock";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity<OrderTo> tHttpEntity = new HttpEntity<>(order, headers);
        Integer integer = restTemplate.postForObject(url, tHttpEntity, Integer.class);
        if (integer.equals(200)) {
            return true;
        }
        return false;
    }
}
