package com.qf.feign;

import com.qf.pojo.TbItem;
import com.qf.pojo.TbItemCat;
import com.qf.pojo.TbItemParam;
import com.qf.utils.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

//需要服务提供方的名称
@FeignClient(value = "item-service")
public interface ItemFeign {
    //根据id查询商品信息
    @RequestMapping("/service/item/selectItemInfo")
    TbItem selectItemInfo(@RequestParam("itemId") Long itemId);

    //分页查询商品信息
    @RequestMapping("/service/item/selectTbItemAllByPage")
    PageResult selectTbItemAllByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", required = false, defaultValue = "8") Integer rows);

    @RequestMapping("/service/itemCategory/selectItemCategoryByParentId")
    List<TbItemCat> selectItemCategoryByParentId(@RequestParam(value = "id", required = false, defaultValue = "0") Long id);

    @RequestMapping("/service/itemParam/{itemCatId}")
    TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId);

    @RequestMapping("/service/item/insertTbItem")
    void insertTbItem(@RequestBody TbItem tbItem, @RequestParam("desc") String desc, @RequestParam("itemParams") String itemParams);

    @RequestMapping("/service/item/preUpdateItem")
    Map<String, Object> preUpdateItem(@RequestParam("itemId") Long itemId);

    @RequestMapping("/service/item/updateTbItem")
    void updateTbItem(@RequestBody TbItem tbItem, @RequestParam String desc, @RequestParam String itemParams);

    @RequestMapping("/service/item/deleteItemById")
    void deleteItemById(@RequestParam("itemId") Long itemId);
}
