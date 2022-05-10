package com.qf.feign;

import com.qf.pojo.TbContentCategory;
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


}
