package com.qf.controller;

import com.qf.entity.TbItem;
import com.qf.feign.ItemFeign;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/item")
public class ItemController {

    @Autowired
    ItemFeign itemFeign;

    //根据商品id查询商品信息
    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(@RequestParam("itemId") Long itemId) {
        TbItem tbItem = itemFeign.selectItemInfo(itemId);
        if (tbItem != null) {
            return Result.ok(tbItem);
        }else {
            return  Result.error("查询商品信息失败");
        }
    }
}
