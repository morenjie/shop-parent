package com.qf.controller;

import com.qf.pojo.TbItem;
import com.qf.service.ItemService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    //根据id查询商品信息
    @RequestMapping("/service/item/selectItemInfo")
    TbItem selectItemInfo(@RequestParam("itemId") Long itemId) {
        return itemService.selectItemInfo(itemId);
    }

    //分页查询商品信息
    @RequestMapping("/service/item/selectTbItemAllByPage")
    PageResult selectTbItemAllByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", required = false, defaultValue = "8") Integer rows) {
        return itemService.selectTbItemAllByPage(page, rows);
    }

    //新增商品
    @RequestMapping("/service/item/insertTbItem")
    void insertTbItem(@RequestBody TbItem tbItem, @RequestParam("desc") String desc, @RequestParam("itemParams") String itemParams) {
        itemService.insertTbItem(tbItem, desc, itemParams);
    }

    //预更新商品信息
    @RequestMapping("/service/item/preUpdateItem")
    Map<String, Object> preUpdateItem(@RequestParam("itemId") Long itemId) {
        return itemService.preUpdateItem(itemId);
    }

    //修改商品信息
    @RequestMapping("/service/item/updateTbItem")
    void updateTbItem(@RequestBody TbItem tbItem, @RequestParam String desc, @RequestParam String itemParams) {
        itemService.updateTbItem(tbItem, desc, itemParams);
    }
}
