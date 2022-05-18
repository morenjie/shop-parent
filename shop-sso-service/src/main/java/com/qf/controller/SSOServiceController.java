package com.qf.controller;

import com.qf.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSOServiceController {
    @Autowired
    SSOService ssoService;

    @RequestMapping("sso/service/checkUserInfo/{checkValue}/{checkFlag}")
    boolean checkUserInfo(@PathVariable("checkValue") String checkValue,
                          @PathVariable("checkFlag") int checkFlag) {
        return ssoService.checkUserInfo(checkValue, checkFlag);
    }
}
