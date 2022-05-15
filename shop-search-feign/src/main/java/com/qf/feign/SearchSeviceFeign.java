package com.qf.feign;

import com.qf.pojo.SearchItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "search-service")
public interface SearchSeviceFeign {
    @RequestMapping("search/service/importAll")
    public boolean importAll();

    @RequestMapping("search/service/list")
    List<SearchItem> list(@RequestParam("q") String q,
                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize);

}
