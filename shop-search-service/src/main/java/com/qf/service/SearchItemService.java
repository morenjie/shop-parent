package com.qf.service;

import com.qf.pojo.SearchItem;

import java.util.List;

public interface SearchItemService {
    boolean importAll() throws Exception;

    List<SearchItem> list(String q, Integer page, Integer pageSize) throws Exception;
}
