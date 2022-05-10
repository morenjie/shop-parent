package com.qf.controller;

import com.qf.feign.ContentFeign;
import com.qf.pojo.TbContentCategory;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("backend/content")
public class ContentController {
    @Autowired
    ContentFeign contentFeign;

    //展示内容分类信息
    @RequestMapping("selectContentCategoryByParentId")
    public Result selectContentCategoryByParentId(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        try {
            List<TbContentCategory> tbContentCategoryList = contentFeign.selectContentCategoryByParentId(id);
            return Result.ok(tbContentCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询内容分类信息失败");
        }
    }

    //新增内容分类信息
    @RequestMapping("insertContentCategory")
    public Result insertContentCategory(TbContentCategory tbContentCategory) {
        try {
            contentFeign.insertContentCategory(tbContentCategory);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增内容分类信息失败");
        }
    }

    @RequestMapping("deleteContentCategoryById")
    public Result deleteContentCategoryById(Long categoryId) {
        try {
            contentFeign.deleteContentCategoryById(categoryId);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除内容分类信息失败");
        }
    }
}
