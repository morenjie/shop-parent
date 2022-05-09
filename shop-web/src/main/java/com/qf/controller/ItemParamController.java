package com.qf.controller;

import com.qf.feign.ItemFeign;
import com.qf.pojo.TbItemParam;
import com.qf.utils.PageResult;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/itemParam")
public class ItemParamController {
    @Autowired
    ItemFeign itemFeign;

    //根据商品分类id查询对应的规格参数名称
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

    //分页查询规格参数名称
    @RequestMapping("selectItemParamAll")
    public Result selectItemParamAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {
        PageResult pageResult = itemFeign.selectItemParamAll(page, rows);
        if (pageResult != null) {
            return Result.ok(pageResult);
        } else {
            return Result.error("分页查询商品规格参数名称失败");
        }

    }

    //新增规格参数信息
    @RequestMapping("insertItemParam")
    public Result insertItemParam(TbItemParam tbItemParam) {
        try {
            itemFeign.insertItemParam(tbItemParam);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增商品规格参数名称失败");
        }
    }



}
