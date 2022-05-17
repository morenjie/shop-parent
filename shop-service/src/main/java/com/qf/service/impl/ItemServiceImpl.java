package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.mapper.TbItemCatMapper;
import com.qf.mapper.TbItemDescMapper;
import com.qf.mapper.TbItemMapper;
import com.qf.mapper.TbItemParamItemMapper;
import com.qf.pojo.*;
import com.qf.service.ItemService;
import com.qf.utils.IDUtils;
import com.qf.utils.PageResult;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Autowired
    AmqpTemplate amqpTemplate;

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
        //新增完成之后 需要通过rabbitMQ向搜索微服务发送一条消息 搜索微服务收到这条消息之后 需要执行更新索引库的操作
        amqpTemplate.convertAndSend("item_exchange", "item.add", itemId);
    }

    //回显商品信息
    @Override
    public Map<String, Object> preUpdateItem(Long itemId) {
        Map<String, Object> map = new HashMap<>();
        //根据商品id查询对应的商品信息
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        map.put("item", tbItem);
        //获取分类名称
        Long cid = tbItem.getCid();
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(cid);
        map.put("itemCat", tbItemCat.getName());
        //封装商品描述信息
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        map.put("itemDesc", tbItemDesc.getItemDesc());
        //封装规格参数值信息
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        TbItemParamItem tbItemParamItem = tbItemParamItems.get(0);
        map.put("itemParamItem", tbItemParamItem.getParamData());
        return map;
    }

    //修改商品信息
    @Override
    public void updateTbItem(TbItem tbItem, String desc, String itemParams) {
        //修改商品本身的消息
        tbItem.setUpdated(new Date());
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        //修改商品描述的消息
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        //修改商品规格参数值的消息
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(new Date());
        tbItemParamItem.setItemId(tbItem.getId());
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(tbItem.getId());
        tbItemParamItemMapper.updateByExampleSelective(tbItemParamItem, example);
    }

    @Override
    public void deleteItemById(Long itemId) {
        tbItemMapper.deleteByPrimaryKey(itemId);
    }
}
