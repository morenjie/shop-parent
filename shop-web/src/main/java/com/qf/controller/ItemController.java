package com.qf.controller;

import com.qf.pojo.TbItem;
import com.qf.feign.ItemFeign;
import com.qf.utils.PageResult;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/backend/item")
public class ItemController {

    @Autowired
    ItemFeign itemFeign;

    //根据商品id查询商品信息
    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(@RequestParam("itemId") Long itemId) {
        TbItem tbItem = itemFeign.selectItemInfo(itemId);
        if (tbItem != null) {
            return Result.ok(tbItem);
        } else {
            return Result.error("查询商品信息失败");
        }

    }

    //分页查询商品信息
    @RequestMapping("selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "rows", required = false, defaultValue = "8") Integer rows) {
        PageResult pageResult = itemFeign.selectTbItemAllByPage(page, rows);
        if (pageResult != null) {
            return Result.ok(pageResult);
        } else {
            return Result.error("分页查询商品信息失败");
        }
    }

    //新增商品
    @RequestMapping("insertTbItem")
    public Result insertTbItem(TbItem tbItem, String desc, String itemParams) {
        try {
            itemFeign.insertTbItem(tbItem, desc, itemParams);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增商品失败");
        }
    }

    //回显商品信息
    @RequestMapping("preUpdateItem")
    public Result preUpdateItem(@RequestParam("itemId") Long itemId) {
        Map<String, Object> map = itemFeign.preUpdateItem(itemId);
        if (map != null) {
            return Result.ok(map);
        } else {
            return Result.error("回显商品失败");
        }
    }
}
