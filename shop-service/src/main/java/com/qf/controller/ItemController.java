package com.qf.controller;

import com.qf.pojo.TbItem;
import com.qf.service.ItemService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    //根据id查询商品信息
    @RequestMapping("/service/item/selectItemInfo")
    TbItem selectItemInfo(@RequestParam("itemId") Long itemId) {
        return itemService.selectItemInfo(itemId);
    }

    @RequestMapping("/service/item/selectTbItemAllByPage")
    PageResult selectTbItemAllByPage(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows",required = false,defaultValue = "8") Integer rows){
        return itemService.selectTbItemAllByPage(page,rows);
    }


}