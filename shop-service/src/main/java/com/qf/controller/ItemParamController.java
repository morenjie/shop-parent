package com.qf.controller;

import com.qf.pojo.TbItemParam;
import com.qf.service.ItemParamService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    //根据分类id查询商品规格参数名称
    @RequestMapping("/service/itemParam/{itemCatId}")
    TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId) {
        return itemParamService.selectItemParamByItemCatId(itemCatId);
    }

    //分页查询所有规格参数名称
    @RequestMapping("/service/itemParam/selectItemParamAll")
    PageResult selectItemParamAll(Integer page, Integer rows) {
        return itemParamService.selectItemParamAll(page,rows);
    }
}
