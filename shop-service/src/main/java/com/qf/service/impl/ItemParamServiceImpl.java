package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.mapper.TbItemParamMapper;
import com.qf.pojo.TbItemParam;
import com.qf.pojo.TbItemParamExample;
import com.qf.service.ItemParamService;
import com.qf.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    TbItemParamMapper tbItemParamMapper;

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
}
