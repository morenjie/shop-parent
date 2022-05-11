package com.qf.controller;

import com.qf.feign.ItemFeign;
import com.qf.pojo.TbItemCat;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("backend/itemCategory")
public class ItemCategoryController {

    @Autowired
    ItemFeign itemFeign;

    //根据商品分类的parentId查询对应的商品分类信息
    @RequestMapping("selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        List<TbItemCat> tbItemCats = itemFeign.selectItemCategoryByParentId(id);
        if (tbItemCats != null) {
            return Result.ok(tbItemCats);
        }
        return Result.error("商品分类查询失败");

    }
}
