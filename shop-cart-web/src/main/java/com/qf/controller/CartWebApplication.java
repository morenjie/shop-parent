package com.qf.controller;

import com.qf.feign.ItemFeign;
import com.qf.pojo.TbItem;
import com.qf.utils.CookieUtils;
import com.qf.utils.JsonUtils;
import com.qf.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@RequestMapping("/frontend/cart")
public class CartWebApplication {
    @Autowired
    ItemFeign itemFeign;

    /**
     * 添加商品到购物车
     *
     * @param itemId  商品id
     * @param userId  用户id
     * @param num     商品购买数量
     * @param request
     * @return
     */
    @RequestMapping("addItem")
    public Result addItem(@RequestParam("itemId") Long itemId, @RequestParam("userId") Long userId, @RequestParam(value = "num", defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登录 如果没有登录 那么购物车信息是保存在cookie端；如果用户登录了，购物车信息是保存在redis缓存中
        try {
            if (userId == null) {
                //没有登录
                //需要将购物车信息查询出来Map<itemId,TbItem>
                Map<String, TbItem> cart = getCartFromCookie(request);
                //将商品添加到购物车
                addItemToCart(cart, itemId, num);
                //将购物车信息写到cookie端
                addCartToCookie(request, response, cart);
            } else {
                //用户已经登录
            }
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("购物车添加失败");
        }
    }

    //将购物车信息写入到cookie
    private void addCartToCookie(HttpServletRequest request, HttpServletResponse response, Map<String, TbItem> cart) {
        //将购物车转换成json数据
        String cart_json = JsonUtils.objectToJson(cart);
        CookieUtils.setCookie(request, response, "CART_COOKIE_KEY", cart_json, 604800, true);
    }

    //将商品信息添加到购物车
    private void addItemToCart(Map<String, TbItem> cart, Long itemId, Integer num) {
        //根据商品id查询购物车信息
        TbItem tbItem = cart.get(itemId.toString());
        if (tbItem != null) {
            //之前的购物车存在同样的商品信息 我们只需要修改商品信息的购买数量即可
            tbItem.setNum(tbItem.getNum() + num);
        } else {
            //说明购物车里面不存在这个商品，这个商品是第一次添加
            tbItem = itemFeign.selectItemInfo(itemId);
            tbItem.setNum(num);
        }
        cart.put(itemId.toString(), tbItem);
    }

    //从cookie端获取购物车信息
    private Map<String, TbItem> getCartFromCookie(HttpServletRequest request) {
        String cart_cookie_key = CookieUtils.getCookieValue(request, "CART_COOKIE_KEY", true);
        //将购物车的json串转换为Map
        if (StringUtils.isNotBlank(cart_cookie_key)) {
            //购物车已经存在
            Map<String, TbItem> map = JsonUtils.jsonToMap(cart_cookie_key, TbItem.class);
            return map;
        } else {
            //购物车不存在，第一次添加商品到购物车,那么我就初始化一个空的购物车
            return new HashMap<String, TbItem>();
        }
    }

    @RequestMapping("showCart")
    public Result showCart(@RequestParam("userId") Long userId, HttpServletRequest request) {
        //判断用户是否登录，如果用户没有登录，就将购物车信息从cookie中取出来展示
        try {
            if (userId == null) {
                //没有登录的
                Map<String, TbItem> cart = getCartFromCookie(request);
                List<TbItem>tbItemList = new ArrayList<>();
                //获取cart中的所有value值
                Set<String> keys = cart.keySet();   //获取所有key的集合
                for (String key : keys) {
                    TbItem tbItem = cart.get(key);
                    tbItemList.add(tbItem);
                }
                return Result.ok(tbItemList);
            } else {
                //登录的
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("展示购物车信息失败");
        }

    }
}
