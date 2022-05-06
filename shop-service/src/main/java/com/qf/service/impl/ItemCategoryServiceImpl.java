package com.qf.service.impl;

import com.qf.mapper.TbItemCatMapper;
import com.qf.pojo.TbItemCat;
import com.qf.pojo.TbItemCatExample;
import com.qf.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemCategoryServiceImpl implements ItemCategoryService {
    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Override
    //根据类目父id查询对应的商品分类消息
    public List<TbItemCat> selectItemCategoryByParentId(Long id) {
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<TbItemCat> tbItemCatList = tbItemCatMapper.selectByExample(example);
        return tbItemCatList;
    }
}
