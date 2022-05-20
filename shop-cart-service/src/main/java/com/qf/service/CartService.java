package com.qf.service;

import com.qf.pojo.TbItem;

import java.util.Map;

public interface CartService {
    Map<String, TbItem> getCartFromRedis(Long userId);


    void addCartToRedis(Long userId, Map<String, TbItem> cart);
}
