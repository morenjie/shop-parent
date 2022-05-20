package com.qf.controller;

import com.qf.pojo.TbItem;
import com.qf.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping()
public class CartServiceController {
    @Autowired
    CartService cartService;

    @RequestMapping("service/cart/getCartFromRedis")
    Map<String, TbItem> getCartFromRedis(@RequestParam("userId") Long userId) {
        return cartService.getCartFromRedis(userId);
    }
    @RequestMapping("service/cart/addCartToRedis")
    void addCartToRedis(@RequestParam("userId") Long userId, @RequestBody Map<String, TbItem> cart){
         cartService.addCartToRedis(userId,cart);
    }
}
