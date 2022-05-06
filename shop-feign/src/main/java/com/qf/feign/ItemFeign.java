package com.qf.feign;

import com.qf.entity.TbItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//需要服务提供方的名称
@FeignClient(value = "item-service")
public interface ItemFeign {
    //根据id查询商品信息
    @RequestMapping("/service/item/selectItemInfo")
    TbItem selectItemInfo(@RequestParam("itemId") Long itemId);

}
