package com.qf.service.impl;

import com.qf.mapper.TbUserMapper;
import com.qf.pojo.TbUser;
import com.qf.pojo.TbUserExample;
import com.qf.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("all")
public class SSOServiceImpl implements SSOService {
    @Autowired
    TbUserMapper tbUserMapper;

    @Override
    public boolean checkUserInfo(String checkValue, int checkFlag) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (checkFlag == 1) {
            criteria.andUsernameEqualTo(checkValue);
        }
        if (checkFlag == 2) {
            criteria.andPhoneEqualTo(checkValue);
        }
        List<TbUser> userList = tbUserMapper.selectByExample(example);
        if (userList.size() > 0) {
            return false;
        }
        return true;
    }
}
