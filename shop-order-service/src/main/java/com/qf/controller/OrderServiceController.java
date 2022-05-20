package com.qf.controller;

import com.qf.pojo.OrderInfo;
import com.qf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderServiceController {
    @Autowired
    OrderService orderService;

    @RequestMapping("service/order/insertOrder")
    String insertOrder(@RequestBody OrderInfo orderInfo) {
        return orderService.insertOrder(orderInfo);
    }
}
