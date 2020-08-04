package com.yls.shop.controller;

import com.yls.shop.mq.OrderTo;
import com.yls.shop.mq.StockLockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author joe 2020-08-04 11:37
 */
@RestController
@RequestMapping("/stock")
public class StockLockController {

    @Autowired
    private StockLockProvider stockLockProvider;

    @PostMapping("/lock")
    public Integer lockStock(@RequestBody OrderTo orderTo) {
        stockLockProvider.lockStock(orderTo);
        return 200;
    }
}
