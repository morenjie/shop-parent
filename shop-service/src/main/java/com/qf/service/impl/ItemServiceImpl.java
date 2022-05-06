package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.mapper.TbItemDescMapper;
import com.qf.mapper.TbItemMapper;
import com.qf.mapper.TbItemParamItemMapper;
import com.qf.pojo.TbItem;
import com.qf.pojo.TbItemDesc;
import com.qf.pojo.TbItemExample;
import com.qf.pojo.TbItemParamItem;
import com.qf.service.ItemService;
import com.qf.utils.IDUtils;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("all")
public class ItemServiceImpl implements ItemService {

    @Autowired
    TbItemMapper tbItemMapper;

    @Autowired
    TbItemDescMapper tbItemDescMapper;

    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    //根据主键查询
    @Override
    public TbItem selectItemInfo(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public PageResult selectTbItemAllByPage(Integer page, Integer rows) {
        //分页插件进行分页 PageHelper
        PageHelper.startPage(page, rows);
        TbItemExample example = new TbItemExample();
        example.setOrderByClause("updated DESC");
        example.createCriteria().andStatusEqualTo((byte) 1);
        List<TbItem> tbItemList = tbItemMapper.selectByExample(example);
        for (TbItem tbItem : tbItemList) {
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

    //新增商品
    @Override
    public void insertTbItem(TbItem tbItem, String desc, String itemParams) {
        //新增tb_item
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //价格变成分为单位
        tbItem.setPrice(tbItem.getPrice() * 100);
        tbItemMapper.insertSelective(tbItem);
        //新增tb_item_desc
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.insertSelective(tbItemDesc);
        //新增tb_jtem_param_param
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setCreated(new Date());
        tbItemParamItem.setUpdated(new Date());
        tbItemParamItemMapper.insertSelective(tbItemParamItem);
    }
}
