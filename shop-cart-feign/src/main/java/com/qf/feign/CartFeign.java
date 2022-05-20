package com.qf.feign;

import com.qf.pojo.TbItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "cart-service")
public interface CartFeign {
    @RequestMapping("service/cart/getCartFromRedis")
    Map<String, TbItem> getCartFromRedis(@RequestParam("userId") Long userId);

    @RequestMapping("service/cart/addCartToRedis")
    void addCartToRedis(@RequestParam("userId") Long userId, @RequestBody Map<String, TbItem> cart);
}
