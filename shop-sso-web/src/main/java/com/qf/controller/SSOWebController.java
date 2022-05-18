package com.qf.controller;

import com.qf.feign.SSOFeign;
import com.qf.pojo.TbUser;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("frontend/sso")
public class SSOWebController {

    @Autowired
    SSOFeign ssoFeign;

    /**
     * 校验用户信息是否正确
     *
     * @param checkValue 用户输入的注册信息
     * @param checkFlag  用户输入信息的类型（1.用户名 2.手机号）
     * @return
     */
    @RequestMapping("checkUserInfo/{checkValue},{checkFlag}")
    public Result checkUserInfo(@PathVariable("checkValue") String checkValue,
                                @PathVariable("checkFlag") int checkFlag) {
        boolean flag = ssoFeign.checkUserInfo(checkValue, checkFlag);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error("用户信息校验失败");
        }
    }

    @RequestMapping("userRegister")
    public Result userRegister(@RequestBody TbUser tbUser) {
        try {
            ssoFeign.userRegister(tbUser);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("注册失败");
        }
    }

    /**
     * 用户登录
     *
     * @param tbUser
     * @return
     */
    @RequestMapping("userLogin")
    public Result userLogin(@RequestBody TbUser tbUser) {
        try {
            Map<String, Object> map = ssoFeign.userLogin(tbUser);
            return Result.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("登录失败");
        }
    }

    /**
     * 根据token验证用户是否登录
     *
     * @param token
     * @return
     */
    @RequestMapping("getUserByToken/{token}")
    public Result getUserByToken(@PathVariable("token") String token) {
        TbUser tbUser = ssoFeign.getUserByToken(token);
        if (tbUser != null) {
            return Result.ok(tbUser);
        } else {
            return Result.error("用户没有登录");
        }
    }

    /**
     * 用户退出登录
     */
    @RequestMapping("/logOut")
    public Result logOut(String token) {
        Boolean logOut = ssoFeign.logOut(token);
        if (logOut) {
            return Result.ok();
        } else {
            return Result.error("退出失败");
        }
    }
}