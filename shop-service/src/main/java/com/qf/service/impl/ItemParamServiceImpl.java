package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.client.RedisClient;
import com.qf.mapper.TbItemCatMapper;
import com.qf.mapper.TbItemParamItemMapper;
import com.qf.mapper.TbItemParamMapper;
import com.qf.pojo.TbItemParam;
import com.qf.pojo.TbItemParamExample;
import com.qf.pojo.TbItemParamItem;
import com.qf.pojo.TbItemParamItemExample;
import com.qf.service.ItemParamService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("all")
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    TbItemParamMapper tbItemParamMapper;

    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    @Autowired
    RedisClient redisClient;

    @Value("${ITEM_INFO}")
    private String ITEM_INFO;

    @Value("${PARAM}")
    private String PARAM;

    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;


    @Override
    public TbItemParam selectItemParamByItemCatId(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(itemCatId);
        /**
         *   selectByExample不能查出字段数据类型是text的大字段类型
         *   selectByExampleWithBLOBs才能查出大字段类型
         *   List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExample(example);
         */
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        return tbItemParams.get(0);
    }

    @Override
    public PageResult selectItemParamAll(Integer page, Integer rows) {
        //分页插件进行分页 PageHelper
        PageHelper.startPage(page, rows);
        TbItemParamExample example = new TbItemParamExample();
        //进行降序排列
        example.setOrderByClause("updated DESC");
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItemParams);
        //封装分页模型
        PageResult pageResult = new PageResult();
        pageResult.setTotalPage(pageInfo.getTotal());
        pageResult.setPageIndex(pageInfo.getPageNum());
        pageResult.setResult(pageInfo.getList());
        return pageResult;
    }

    @Override
    public void insertItemParam(TbItemParam tbItemParam) {
        //判断当前商品分类id对应的规格参数xx信息是否存在
        Long itemCatId = tbItemParam.getItemCatId();
        //如果不存在 就直接新增
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if (tbItemParams.size() > 0) {
            throw new RuntimeException("对应商品 分类的规格参数存在");
        }
        //如果存在 就不允许新增
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        tbItemParamMapper.insertSelective(tbItemParam);
    }

    @Override
    public void deleteItemParamById(Long id) {
        //根据主键删除
        tbItemParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TbItemParamItem selectTbItemParamItemByItemId(Long itemId) {
        //查询缓存
        TbItemParamItem tbItemParamItem = (TbItemParamItem) redisClient.get(ITEM_INFO + ":" + itemId + ":" + PARAM);
        if (tbItemParamItem != null) {
            return tbItemParamItem;
        }
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExample(example);
        if (tbItemParamItems != null && tbItemParamItems.size() > 0) {
            //设置缓存
            redisClient.set(ITEM_INFO + ":" + itemId + ":" + PARAM, tbItemParamItems.get(0));
            //设置缓存有效期
            redisClient.expire(ITEM_INFO + ":" + itemId + ":" + PARAM, ITEM_INFO_EXPIRE);
            return tbItemParamItems.get(0);
        }
        return null;
    }
}
