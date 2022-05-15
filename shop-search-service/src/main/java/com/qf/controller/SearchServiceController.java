package com.qf.controller;

import com.qf.pojo.SearchItem;
import com.qf.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchServiceController {
    @Autowired
    SearchItemService searchItemService;

    @RequestMapping("search/service/importAll")
    public boolean importAll() throws Exception {
        return searchItemService.importAll();
    }

    @RequestMapping("search/service/list")
    List<SearchItem> list(String q, Integer page, Integer pageSize) throws Exception {
        return searchItemService.list(q, page, pageSize);
    }
}
