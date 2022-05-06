package com.qf.service.impl;

import com.qf.mapper.TbItemParamMapper;
import com.qf.pojo.TbItemParam;
import com.qf.pojo.TbItemParamExample;
import com.qf.service.ItemParamService;
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
}
