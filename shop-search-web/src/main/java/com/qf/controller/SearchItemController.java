package com.qf.controller;

import com.qf.feign.SearchSeviceFeign;
import com.qf.pojo.SearchItem;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("frontend/searchItem")
public class SearchItemController {
    @Autowired
    SearchSeviceFeign searchSeviceFeign;

    //将商品信息导入到索引库中
    @RequestMapping("/importAll")
    public Result importAll() {
        boolean flag = searchSeviceFeign.importAll();
        if (flag) {
            return Result.ok("商品导入索引库成功");
        } else {
            return Result.error("商品导入索引库失败");
        }
    }

    //查询索引库
    @RequestMapping("list")
    public List<SearchItem> list(@RequestParam("q") String q,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return searchSeviceFeign.list(q, page, pageSize);
    }
}
