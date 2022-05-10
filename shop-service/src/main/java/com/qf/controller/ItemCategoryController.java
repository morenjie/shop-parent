package com.qf.controller;

import com.qf.pojo.TbItemCat;
import com.qf.service.ItemCategoryService;
import com.qf.utils.CatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemCategoryController {
    @Autowired
    ItemCategoryService itemCategoryService;

    //根据类目父id查询对应的商品分类消息
    @RequestMapping("/service/itemCategory/selectItemCategoryByParentId")
    List<TbItemCat> selectItemCategoryByParentId(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        return itemCategoryService.selectItemCategoryByParentId(id);
    }

    @RequestMapping("/service/itemCategory/selectItemCategoryAll")
    CatResult selectItemCategoryAll(){
        return itemCategoryService.selectItemCategoryAll();
    }
}
