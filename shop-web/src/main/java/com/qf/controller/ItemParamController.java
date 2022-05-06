package com.qf.controller;

import com.qf.feign.ItemFeign;
import com.qf.pojo.TbItemParam;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/itemParam")
public class ItemParamController {
    @Autowired
    ItemFeign itemFeign;

    @RequestMapping("selectItemParamByItemCatId/{itemCatId}")
    public Result selectItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId) {
        System.out.println(itemCatId);
        TbItemParam tbItemParam = itemFeign.selectItemParamByItemCatId(itemCatId);
        if (tbItemParam != null) {
            return Result.ok(tbItemParam);
        } else {
            return Result.error("商品规格参数查询失败");
        }
    }
}
