package com.qf.service;

import com.qf.pojo.TbItem;
import com.qf.utils.PageResult;

public interface ItemService {

    TbItem selectItemInfo(Long itemId);

    PageResult selectTbItemAllByPage(Integer page, Integer rows);

    void insertTbItem(TbItem tbItem, String desc, String itemParams);
}
