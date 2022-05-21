package com.qf.service.impl;

import com.qf.client.RedisClient;
import com.qf.mapper.TbItemMapper;
import com.qf.mapper.TbOrderItemMapper;
import com.qf.mapper.TbOrderMapper;
import com.qf.mapper.TbOrderShippingMapper;
import com.qf.pojo.*;
import com.qf.service.OrderService;
import com.qf.utils.JsonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
@SuppressWarnings("all")
public class OrderServiceImpl implements OrderService {
    @Autowired
    TbOrderMapper tbOrderMapper;

    @Autowired
    TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    RedisClient redisClient;
    @Autowired
    TbItemMapper tbItemMapper;

    @Value("${ORDER_ID_KEY}")
    private String ORDER_ID_KEY;

    @Value("${ORDER_ID_BEGIN}")
    private Long ORDER_ID_BEGIN;

    @Value("${ORDER_ITEM_ID_KEY}")
    private String ORDER_ITEM_ID_KEY;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public String insertOrder(OrderInfo orderInfo) {
        //解析orderInfo中的TbOrder   TbOrdershipping  TbOrderItem
        TbOrder tbOrder = orderInfo.getTbOrder();
        TbOrderShipping tbOrderShipping = orderInfo.getTbOrderShipping();
        String orderItem = orderInfo.getOrderItem();
        //需要将json串转换成list集合 List<TbItem>
        List<TbOrderItem> tbOrderItemList = JsonUtils.jsonToList(orderItem, TbOrderItem.class);

        //保存订单的本身信息  tbOrder
        //生成订单id 从缓存里面去取，通过自增的方式生成订单id 保证订单id的唯一性。就是通过redis key的自增长实现唯一
        if (redisClient.exists(ORDER_ID_KEY)) {
            redisClient.set(ORDER_ID_KEY, ORDER_ID_BEGIN);
        }
        //通过自增的方式保证生成的订单是唯一的
        Long orderId = redisClient.incr(ORDER_ID_KEY, 1L);
        tbOrder.setOrderId(orderId.toString());
        tbOrder.setCreateTime(new Date());
        tbOrder.setUpdateTime(new Date());
        tbOrder.setStatus(1);//未付款
        tbOrderMapper.insertSelective(tbOrder);
        //保存订单的明细信息
        if (!redisClient.exists(ORDER_ITEM_ID_KEY)) {
            redisClient.set(ORDER_ITEM_ID_KEY, 0);
        }
        for (TbOrderItem tbOrderItem : tbOrderItemList) {
            Long orderItemId = redisClient.incr(ORDER_ITEM_ID_KEY, 1);
            tbOrderItem.setOrderId(orderId.toString());
            tbOrderItem.setId(orderItemId.toString());
            tbOrderItemMapper.insertSelective(tbOrderItem);
        }
        //保存物流信息
        tbOrderShipping.setOrderId(orderId.toString());
        tbOrderShipping.setCreated(new Date());
        tbOrderShippingMapper.insertSelective(tbOrderShipping);
        //消息的发送     参数1 交换机 参数2 路由key  参数3 消息主题
        amqpTemplate.convertAndSend("order_exchange", "order.add", orderId.toString());
        //将购物车列表里面添加到订单的商品删除
        return orderId.toString();
    }

    /**
     * 超时订单的处理
     */
    @Override
    public void closeTimeoutOrder() {
        //查询超时订单
        List<TbOrder> tbOrderList = tbOrderMapper.selectTimeOutOrders();
        //关闭超时订单
        for (TbOrder tbOrder : tbOrderList) {
            //设置订单的创建时间 修改时间 结束时间
            tbOrder.setCreateTime(new Date());
            tbOrder.setUpdateTime(new Date());
            tbOrder.setCloseTime(new Date());
            //修改订单状态为已关闭
            tbOrder.setStatus(6);
            //修改订单表的信息tb_order
            tbOrderMapper.updateByPrimaryKeySelective(tbOrder);
            //还原库存数量
            //获取订单项 tb_order_item
            TbOrderItemExample example = new TbOrderItemExample();
            example.createCriteria().andOrderIdEqualTo(tbOrder.getOrderId());
            List<TbOrderItem> tbOrderItemList = tbOrderItemMapper.selectByExample(example);
            for (TbOrderItem tbOrderItem : tbOrderItemList) {
                //查询商品信息 tb_item
                TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.valueOf(tbOrderItem.getItemId()));
                //修改库存
                tbItem.setNum(tbItem.getNum() + tbOrderItem.getNum());
                //执行修改操作
                tbItemMapper.updateByPrimaryKeySelective(tbItem);
            }
        }
    }
}
