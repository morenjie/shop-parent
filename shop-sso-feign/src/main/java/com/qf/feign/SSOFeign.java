package com.qf.feign;

import com.qf.pojo.TbUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("sso-service")
public interface SSOFeign {
    @RequestMapping("sso/service/checkUserInfo/{checkValue}/{checkFlag}")
    boolean checkUserInfo(@PathVariable("checkValue") String checkValue,
                          @PathVariable("checkFlag") int checkFlag);

    @RequestMapping("sso/service/userRegister")
    void userRegister(@RequestBody TbUser tbUser);

    @RequestMapping("sso/service/userLogin")
    Map<String, Object> userLogin(@RequestBody TbUser tbUser);

    @RequestMapping("sso/service/getUserByToken")
    TbUser getUserByToken(@PathVariable("token") String token);

    @RequestMapping("sso/service/logOut")
    Boolean logOut(String token);
}
