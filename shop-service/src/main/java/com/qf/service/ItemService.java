package com.qf.service;

import com.qf.pojo.TbItem;
import com.qf.utils.PageResult;

import java.util.Map;

public interface ItemService {

    TbItem selectItemInfo(Long itemId);

    PageResult selectTbItemAllByPage(Integer page, Integer rows);

    void insertTbItem(TbItem tbItem, String desc, String itemParams);

    Map<String, Object> preUpdateItem(Long itemId);

    void updateTbItem(TbItem tbItem, String desc, String itemParams);

    void deleteItemById(Long itemId);
}
