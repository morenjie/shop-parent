package com.qf.controller;

import com.qf.pojo.TbContent;
import com.qf.pojo.TbContentCategory;
import com.qf.service.ContentService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContentServiceController {

    @Autowired
    ContentService contentService;

    //展示内容分类信息
    @RequestMapping("service/content/selectContentCategoryByParentId")
    List<TbContentCategory> selectContentCategoryByParentId(@RequestParam("id") Long id) {
        return contentService.selectContentCategoryByParentId(id);
    }

    //新增内容分类信息
    @RequestMapping("service/content/insertContentCategory")
    void insertContentCategory(@RequestBody TbContentCategory tbContentCategory) {
        contentService.insertContentCategory(tbContentCategory);
    }

    @RequestMapping("service/content/deleteContentCategoryById")
    void deleteContentCategoryById(@RequestParam("categoryId") Long categoryId) {
        contentService.deleteContentCategoryById(categoryId);
    }

    @RequestMapping("service/content/selectTbContentAllByCategoryId")
    PageResult selectTbContentAllByCategoryId(@RequestParam("categoryId") Long categoryId,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {
        return contentService.selectTbContentAllByCategoryId(categoryId, page, rows);
    }

    @RequestMapping("service/content/insertTbContent")
    void insertTbContent(@RequestBody TbContent tbcontent){
        contentService.insertTbContent(tbcontent);
    }

}
