package com.qf.controller;

import com.qf.pojo.TbItemParam;
import com.qf.pojo.TbItemParamItem;
import com.qf.service.ItemParamService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return itemParamService.selectItemParamAll(page, rows);
    }

    //新增商品规格参数
    @RequestMapping("/service/itemParam/insertItemParam")
    void insertItemParam(@RequestBody TbItemParam tbItemParam) {
        itemParamService.insertItemParam(tbItemParam);
    }

    //删除商品规格参数信息
    @RequestMapping("/service/itemParam/deleteItemParamById")
    void deleteItemParamById(@RequestParam("id") Long id) {
        itemParamService.deleteItemParamById(id);
    }

    //根据商品idc查询对应的规格参数信息
    @RequestMapping("/service/itemParam/selectTbItemParamItemByItemId")
    TbItemParamItem selectTbItemParamItemByItemId(@RequestParam("itemId") Long itemId) {
        return itemParamService.selectTbItemParamItemByItemId(itemId);
    }
}
