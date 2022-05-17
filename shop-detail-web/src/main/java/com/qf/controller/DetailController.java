package com.qf.controller;

import com.qf.feign.ItemFeign;
import com.qf.pojo.TbItem;
import com.qf.pojo.TbItemDesc;
import com.qf.pojo.TbItemParamItem;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frontend/detail")
@SuppressWarnings("all")
public class DetailController {
    @Autowired
    ItemFeign itemFeign;

    //查询商品详情 --商品本身的详情信息
    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(Long itemId) {
        TbItem tbItem = itemFeign.selectItemInfo(itemId);
        if (tbItem != null) {
            return Result.ok(tbItem);
        } else {
            return Result.error("查询商品详情失败");
        }
    }

    //查询商品详情 --商品描述详情
    @RequestMapping("selectItemDescByItemId")
    public Result selectItemDescByItemId(Long itemId) {
        TbItemDesc tbItemDesc = itemFeign.selectItemDescByItemId(itemId);
        if (tbItemDesc!=null){
            return Result.ok(tbItemDesc);
        }else{
            return Result.error("查询商品描述详情失败");
        }
    }

    //查询商品详情 --规格参数信息
    @RequestMapping("selectTbItemParamItemByItemId")
    public Result selectTbItemParamItemByItemId(Long itemId) {
      TbItemParamItem TbItemParamItem =itemFeign.selectTbItemParamItemByItemId(itemId);
      if (TbItemParamItem!=null){
          return Result.ok(TbItemParamItem);
      }else {
          return Result.error("查询商品规格参数值详情失败");
      }

    }
}
