package com.qf.controller;

import com.qf.pojo.TbUser;
import com.qf.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SSOServiceController {
    @Autowired
    SSOService ssoService;

    @RequestMapping("sso/service/checkUserInfo/{checkValue}/{checkFlag}")
    boolean checkUserInfo(@PathVariable("checkValue") String checkValue,
                          @PathVariable("checkFlag") int checkFlag) {
        return ssoService.checkUserInfo(checkValue, checkFlag);
    }

    @RequestMapping("sso/service/userRegister")
    void userRegister(@RequestBody TbUser tbUser){
         ssoService.userRegister(tbUser);
    }
    @RequestMapping("sso/service/userLogin")
    Map<String, Object> userLogin(@RequestBody TbUser tbUser){
        return ssoService.userLogin(tbUser);
    }

    @RequestMapping("sso/service/getUserByToken")
    TbUser getUserByToken(@PathVariable("token") String token){
        return ssoService.getUserByToken(token);
    }

    @RequestMapping("sso/service/logOut")
    Boolean logOut(String token){
        return ssoService.logOut(token);
    }
}
