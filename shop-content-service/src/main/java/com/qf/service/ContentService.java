package com.qf.service;

import com.qf.pojo.TbContent;
import com.qf.pojo.TbContentCategory;
import com.qf.utils.PageResult;

import java.util.List;

public interface ContentService {
    //根据父id查询对应的内容分类信息
    List<TbContentCategory> selectContentCategoryByParentId(Long id);

    //新增内容节点
    void insertContentCategory(TbContentCategory tbContentCategory);

    //删除内容节点
    void deleteContentCategoryById(Long categoryId);

    //分页查询内容分类节点信息
    PageResult selectTbContentAllByCategoryId(Long categoryId, Integer page, Integer rows);

    //新增内容节点信息
    void insertTbContent(TbContent tbcontent);
}
