package com.qf.feign;

import com.qf.pojo.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("order-service")
public interface OrderFeign {
    @RequestMapping("service/order/insertOrder")
    String insertOrder(@RequestBody OrderInfo orderInfo);
}
