package com.qf.controller;

import com.qf.pojo.TbItemParam;
import com.qf.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;
    @RequestMapping("/service/itemParam/{itemCatId}")
    TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId) {
return itemParamService.selectItemParamByItemCatId(itemCatId);
    }
}
