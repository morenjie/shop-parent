package com.qf.service;

import com.qf.pojo.TbItemCat;
import com.qf.utils.CatResult;

import java.util.List;

public interface ItemCategoryService {
    List<TbItemCat> selectItemCategoryByParentId(Long id);

    CatResult selectItemCategoryAll();

}
