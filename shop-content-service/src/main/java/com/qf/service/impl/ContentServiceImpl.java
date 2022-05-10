package com.qf.service.impl;

import com.qf.mapper.TbContentCategoryMapper;
import com.qf.pojo.TbContentCategory;
import com.qf.pojo.TbContentCategoryExample;
import com.qf.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("all")
public class ContentServiceImpl implements ContentService {
    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selectContentCategoryByParentId(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<TbContentCategory> tbContentCategoryList = tbContentCategoryMapper.selectByExample(example);
        return tbContentCategoryList;
    }

    @Override
    public void insertContentCategory(TbContentCategory tbContentCategory) {
        //新增节点信息
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        //判断当前新增节点的父节点的isParent的值是1 还是 0
        //获取当前新增节点的父节点
        Long parentId = tbContentCategory.getParentId();
        TbContentCategory parentContentCategory = this.tbContentCategoryMapper.selectByPrimaryKey(parentId);
        Boolean isParent =parentContentCategory.getIsParent();
        if(!isParent){
            //当前新增节点的父节点的is_parent字段为0 本身就是一个子节点点 需要修改is_parent的值
            parentContentCategory.setIsParent(true);
            parentContentCategory.setUpdated(new Date());
            //执行修改操作
            tbContentCategoryMapper.updateByPrimaryKey (parentContentCategory);
        }
    }
}
