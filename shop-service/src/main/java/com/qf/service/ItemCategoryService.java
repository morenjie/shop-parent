package com.qf.service;

import com.qf.pojo.TbItemCat;

import java.util.List;

public interface ItemCategoryService {
    List<TbItemCat> selectItemCategoryByParentId(Long id);
}
