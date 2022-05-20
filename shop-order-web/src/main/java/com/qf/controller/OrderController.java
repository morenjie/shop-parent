package com.qf.controller;

import com.qf.feign.CartFeign;
import com.qf.feign.OrderFeign;
import com.qf.pojo.OrderInfo;
import com.qf.pojo.TbItem;
import com.qf.pojo.TbOrder;
import com.qf.pojo.TbOrderShipping;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("frontend/order")
@SuppressWarnings("all")
public class OrderController {
    @Autowired
    CartFeign cartFeign;

    @Autowired
    OrderFeign orderFeign;

    /**
     * 生成确认订单信息
     *
     * @param ids
     * @param userId
     * @param token
     * @return
     */
    @RequestMapping("goSettlement")
    public Result goSettlement(Long[] ids, Long userId, String token) {
        //从redis缓存里面获取购物车信息         map中的key就是商品id
        try {
            Map<String, TbItem> cart = cartFeign.getCartFromRedis(userId);
            List<TbItem> tbItemList = new ArrayList<>();
            for (Long itemId : ids) {
                TbItem tbItem = cart.get(itemId.toString());
                tbItemList.add(tbItem);
            }
            return Result.ok(tbItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("订单确认信息加载失败");
        }
    }

    /**
     * 新增订单信息
     *
     * @param orderitem
     * @param tbOrder
     * @param tbOrderShipping
     * @return
     */
    @RequestMapping("insertOrder")
    public Result insertOrder(String orderitem, TbOrder tbOrder, TbOrderShipping tbOrderShipping) {
        /**
         * 由于我们需要通过feign传递多个pojo实体，但是feign不允许我们的参数携带多个参数@RequestBody
         * 所以我们只能对我们提交的数据进行进一步的封装。这就是为什么我们要设置OrderInfo数据模型的原因
         */
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderItem(orderitem);
        orderInfo.setTbOrder(tbOrder);
        orderInfo.setTbOrderShipping(tbOrderShipping);
        String orderId = orderFeign.insertOrder(orderInfo);
        if (orderId != null) {
            return Result.ok(orderId);
        } else {
            return Result.error("订单新增失败");
        }

    }

}
