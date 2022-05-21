package com.qf.job;

import com.qf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderJob {
    @Autowired
    OrderService orderService;

    //quartz执行任务调度的方法
    public void closeTimeoutOrder() {
        /**
         * 扫描到超时订单 并且去处理超时订单
         */
        orderService.closeTimeoutOrder();
    }
}
