package com.qf.service;

import com.qf.pojo.OrderInfo;

public interface OrderService {
    String insertOrder(OrderInfo orderInfo);

    void closeTimeoutOrder();

}
