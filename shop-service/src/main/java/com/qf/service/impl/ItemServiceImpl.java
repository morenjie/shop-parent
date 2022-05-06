package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.mapper.TbItemMapper;
import com.qf.pojo.TbItem;
import com.qf.pojo.TbItemExample;
import com.qf.service.ItemService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    TbItemMapper tbItemMapper;

    @Override
    public TbItem selectItemInfo(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public PageResult selectTbItemAllByPage(Integer page, Integer rows) {
        //分页插件进行分页 PageHelper
        PageHelper.startPage(page,rows);
        TbItemExample example = new TbItemExample();
        example.setOrderByClause("updated DESC");
        example.createCriteria().andStatusEqualTo((byte)1);
        List<TbItem> tbItemList = tbItemMapper.selectByExample(example);
        for(TbItem tbItem : tbItemList){
            tbItem.setPrice(tbItem.getPrice() / 100);
        }
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItemList);
        //封装分页模型
        PageResult pageResult = new PageResult();
        pageResult.setTotalPage(pageInfo.getTotal());
        pageResult.setPageIndex(pageInfo.getPageNum());
        pageResult.setResult(pageInfo.getList());
        return pageResult;
    }
}
