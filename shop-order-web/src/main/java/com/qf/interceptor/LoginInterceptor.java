package com.qf.interceptor;

import com.qf.feign.SSOFeign;
import com.qf.pojo.TbUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    SSOFeign ssoFeign;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户存储在浏览器端的token信息
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            //用户没有登录
            return false;//不放行
        }
        //远程调用登录微服务，根据token查询用户信息
        TbUser tbUser = ssoFeign.getUserByToken(token);
        if (tbUser == null) {
            //缓存在redis里面失效了
            return false;
        }
        return true;
    }
}
