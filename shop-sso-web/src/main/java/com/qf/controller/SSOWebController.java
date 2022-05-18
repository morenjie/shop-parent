package com.qf.controller;

import com.qf.feign.SSOFeign;
import com.qf.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
