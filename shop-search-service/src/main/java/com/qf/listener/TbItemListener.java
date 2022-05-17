package com.qf.listener;

import com.qf.service.SearchItemService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TbItemListener {
    @Autowired
    SearchItemService searchItemService;

    /**
     * @RabbitListener描述修饰的当前方法是一个消息方的监听器，来专门监听生产者发送过来的消息 bindings：描述交换机和队列之间的绑定关系
     * @QueueBinding 描述交换机和队列的详情信息
     * @Queue 描述队列
     * name：队列名称
     * durable：描述队列是否持久化
     * @Exchange：描述交换机 name:交换机名称
     * type：交换机类型
     * key：描述路由key
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(name = "search_queue", durable = "true"),
                    exchange = @Exchange(name = "item_exchange", type = ExchangeTypes.TOPIC), key = "item.*")
    })
    public void listener(String message) throws Exception {
        //message其实就是生产者发送过来的消息 也就是商品id
        System.out.println("商品id是:" + message);
        //新增文档
        searchItemService.insertDocument(Long.valueOf(message));
    }
}
