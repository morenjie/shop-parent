package com.qf.service;

import com.qf.pojo.TbContentCategory;

import java.util.List;

public interface ContentService {
    //根据父id查询对应的内容分类信息
    List<TbContentCategory> selectContentCategoryByParentId(Long id);

    //新增内容节点
    void insertContentCategory(TbContentCategory tbContentCategory);

    //删除内容节点
    void deleteContentCategoryById(Long categoryId);

}
