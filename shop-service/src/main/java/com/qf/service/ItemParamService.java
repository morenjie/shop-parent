package com.qf.service;

import com.qf.pojo.TbItemParam;
import com.qf.utils.PageResult;

public interface ItemParamService {
    TbItemParam selectItemParamByItemCatId(Long itemCatId);


    PageResult selectItemParamAll(Integer page, Integer rows);


    void insertItemParam(TbItemParam tbItemParam);
}
