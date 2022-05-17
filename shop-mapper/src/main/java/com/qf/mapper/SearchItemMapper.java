package com.qf.mapper;

import com.qf.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {

    List<SearchItem> getSearchItem();

    SearchItem getSearchItemById(Long message);
}
