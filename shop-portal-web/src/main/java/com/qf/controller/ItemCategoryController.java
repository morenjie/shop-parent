package com.qf.controller;

import com.qf.feign.ItemFeign;
import com.qf.utils.CatResult;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frontend/itemCategory")
public class ItemCategoryController {

    @Autowired
    ItemFeign itemFeign;

    @RequestMapping("selectItemCategoryAll")
    public Result selectItemCategoryAll(){
        CatResult catResult = itemFeign.selectItemCategoryAll();
        if(catResult != null){
            return Result.ok(catResult);
        }else{
            return Result.error("查询商品分类信息失败");
        }
    }
}
