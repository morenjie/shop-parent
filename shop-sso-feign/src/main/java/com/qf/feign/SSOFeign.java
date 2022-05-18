package com.qf.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("sso-service")
public interface SSOFeign {
    @RequestMapping("sso/service/checkUserInfo/{checkValue}/{checkFlag}")
    boolean checkUserInfo(@PathVariable("checkValue") String checkValue,
                          @PathVariable("checkFlag") int checkFlag);
}
