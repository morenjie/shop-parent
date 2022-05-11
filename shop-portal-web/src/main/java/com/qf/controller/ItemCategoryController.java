package com.qf.controller;

import com.qf.feign.ContentFeign;
import com.qf.feign.ItemFeign;
import com.qf.utils.AdNode;
import com.qf.utils.CatResult;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("frontend/itemCategory")
public class ItemCategoryController {

    @Autowired
    ItemFeign itemFeign;

    @Autowired
    ContentFeign contentFeign;

    //在前台首页展示商品分类信息
    @RequestMapping("selectItemCategoryAll")
    public Result selectItemCategoryAll() {
        CatResult catResult = itemFeign.selectItemCategoryAll();
        if (catResult != null) {
            return Result.ok(catResult);
        } else {
            return Result.error("查询商品分类信息失败");
        }
    }

    //在前台展示大广告图片信息
    @RequestMapping("selectFrontendContentByAD")
    public Result selectFrontendContentByAD() {
        List<AdNode> adNodeList = contentFeign.selectFrontendContentByAD();
        if (adNodeList != null) {
            return Result.ok(adNodeList);
        } else {
            return Result.error("首页大广告加载失败");
        }
    }
}
