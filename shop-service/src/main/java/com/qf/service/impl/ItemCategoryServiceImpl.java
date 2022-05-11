package com.qf.service.impl;

import com.qf.mapper.TbItemCatMapper;
import com.qf.pojo.TbItemCat;
import com.qf.pojo.TbItemCatExample;
import com.qf.service.ItemCategoryService;
import com.qf.utils.CatNode;
import com.qf.utils.CatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("all")
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

    //首页展示商品分类消息
    @Override
    public CatResult selectItemCategoryAll() {
        CatResult catResult = new CatResult();
        //默认查询一级分类
        catResult.setData(getCatList(0L));
        return catResult;
    }

    //根据parentId展示商品的分类信息
    private List<?> getCatList(long parentId) {
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCatList = tbItemCatMapper.selectByExample(example);
        List list = new ArrayList();
        int count = 0;
        for (TbItemCat tbItemCat : tbItemCatList) {
            //判断是否是父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                catNode.setName(tbItemCat.getName());
                //递归思想 查询子节点
                catNode.setItem(getCatList(tbItemCat.getId()));
                list.add(catNode);
                count++;
                //只展示18行数据
                if (count == 18) {
                    break;
                }
            } else {
                //如果是子节点也放到集合里面
                list.add(tbItemCat.getName());
            }
        }
        return list;
    }
}
