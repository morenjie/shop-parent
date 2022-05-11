package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.mapper.TbContentCategoryMapper;
import com.qf.mapper.TbContentMapper;
import com.qf.pojo.TbContent;
import com.qf.pojo.TbContentCategory;
import com.qf.pojo.TbContentCategoryExample;
import com.qf.pojo.TbContentExample;
import com.qf.service.ContentService;
import com.qf.utils.PageResult;
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

    @Autowired
    TbContentMapper tbContentMapper;

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
        Boolean isParent = parentContentCategory.getIsParent();
        if (!isParent) {
            //当前新增节点的父节点的is_parent字段为0 本身就是一个子节点点 需要修改is_parent的值
            parentContentCategory.setIsParent(true);
            parentContentCategory.setUpdated(new Date());
            //执行修改操作
            tbContentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }
    }

    @Override
    public void deleteContentCategoryById(Long categoryId) {
        //判断当前节点是否可以删除
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(categoryId);
        List<TbContentCategory> categoryList = tbContentCategoryMapper.selectByExample(example);
        if (categoryList.size() > 0) {
            //说明当前节点存在子节点，不允许删除
            throw new RuntimeException("当前节点不允许删除");
        }
        //可以删除当前子节点
        //获取当前节点对象
        TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(categoryId);
        //获取当前节点的父id
        Long parentId = contentCategory.getParentId();
        //查询当前节点的兄弟节点
        TbContentCategoryExample example1 = new TbContentCategoryExample();
        example1.createCriteria().andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList1 = tbContentCategoryMapper.selectByExample(example1);
        //删除节点
        tbContentCategoryMapper.deleteByPrimaryKey(categoryId);
        //有些只有自己本身这个节点存在
        if (categoryList1.size() == 1) {
            //获取当前节点的父节点
            TbContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
            parentContentCategory.setUpdated(new Date());
            parentContentCategory.setIsParent(false);
            //修改父节点的状态消息
            tbContentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
        }
    }

    @Override
    public PageResult selectTbContentAllByCategoryId(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        //根据内容分类id查询对应的消息
        System.out.println(categoryId + "~~" + page + "~~" + rows);
        TbContentExample example = new TbContentExample();
        example.setOrderByClause("updated DESC");
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>();
        PageResult pageResult = new PageResult();
        pageResult.setPageIndex(pageInfo.getPageNum());
        pageResult.setTotalPage(pageInfo.getTotal());
        pageResult.setResult(tbContents);
        return pageResult;
    }

    @Override
    public void insertTbContent(TbContent tbcontent) {
        tbcontent.setCreated(new Date());
        tbcontent.setUpdated(new Date());
        tbContentMapper.insertSelective(tbcontent);
    }
}