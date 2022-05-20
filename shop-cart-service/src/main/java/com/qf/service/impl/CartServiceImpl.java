package com.qf.service.impl;

import com.qf.client.RedisClient;
import com.qf.pojo.TbItem;
import com.qf.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    RedisClient redisClient;

    @Override
    public Map<String, TbItem> getCartFromRedis(Long userId) {
        Map<String, TbItem> map = (Map<String, TbItem>) redisClient.hget("CART_REDIS_KEY", userId.toString());
        return map;
    }

    @Override
    public void addCartToRedis(Long userId, Map<String, TbItem> cart) {
        redisClient.hset("CART_REDIS_KEY", userId.toString(), cart);
    }
}
