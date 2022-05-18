package com.qf.service;

import com.qf.pojo.TbUser;

import java.util.Map;

public interface SSOService {
    boolean checkUserInfo(String checkValue, int checkFlag);

    Map<String, Object> userLogin(TbUser tbUser);

    void userRegister(TbUser tbUser);

    TbUser getUserByToken(String token);

    Boolean logOut(String token);
}
