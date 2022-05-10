package com.qf.feign;

import com.qf.pojo.TbContentCategory;
import com.qf.utils.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "content-service")
public interface ContentFeign {
    @RequestMapping("service/content/selectContentCategoryByParentId")
    List<TbContentCategory> selectContentCategoryByParentId(@RequestParam("id") Long id);

    @RequestMapping("service/content/insertContentCategory")
    void insertContentCategory(@RequestBody TbContentCategory tbContentCategory);


    @RequestMapping("service/content/deleteContentCategoryById")
    void deleteContentCategoryById(@RequestParam("categoryId") Long categoryId);

    @RequestMapping("service/content/selectTbContentAllByCategoryId")
    PageResult selectTbContentAllByCategoryId(@RequestParam("categoryId") Long categoryId,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows);

}
