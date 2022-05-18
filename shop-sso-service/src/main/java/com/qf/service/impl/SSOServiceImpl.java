package com.qf.service.impl;

import com.qf.client.RedisClient;
import com.qf.mapper.TbUserMapper;
import com.qf.pojo.TbUser;
import com.qf.pojo.TbUserExample;
import com.qf.service.SSOService;
import com.qf.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@SuppressWarnings("all")
public class SSOServiceImpl implements SSOService {
    @Autowired
    TbUserMapper tbUserMapper;
    @Autowired
    RedisClient redisClient;

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

    @Override
    public void userRegister(TbUser tbUser) {
        //将密码进行加密处理
        String digest = MD5Utils.digest(tbUser.getPassword());
        tbUser.setPassword(digest);
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUserMapper.insert(tbUser);
    }

    @Override
    public Map<String, Object> userLogin(TbUser tbUser) {
        //根据用户输入的用户名和密码判断用户是否存在
        String password = tbUser.getPassword();
        //对用户的明文进行加密
        String digest = MD5Utils.digest(password);
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(digest);
        List<TbUser> tbUserList = tbUserMapper.selectByExample(tbUserExample);
        if (tbUserList.size() == 0) {
            //用户名或密码输入有误
            return null;
        }
        //根据用户名和密码从数据库里面获取的用户信息
        TbUser user = tbUserList.get(0);
        //生成token，将用户信息缓存到redis里面去，并且将token信息响应给浏览器端
        String token = UUID.randomUUID().toString();
        //为了安全期间，密码不要缓存
        user.setPassword(null);
        //缓存用户信息到redis
        redisClient.set("USER-INFO" + ":" + token, tbUser);
        //设置redis的key的过期时间
        redisClient.expire("USER-INFO" + ":" + token, 60 * 60 * 24 * 7);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userid", user.getId().toString());
        map.put("username", user.getUsername());
        return map;
    }

    @Override
    public TbUser getUserByToken(String token) {
        //从缓存里面查询用户信息
        TbUser tbUser = (TbUser) redisClient.get("USER-INFO" + ":" + token);
        if (tbUser!=null){
            //重置key的过期时间
            redisClient.expire("USER-INFO" + ":" + token, 60 * 60 * 24 * 7);
            return tbUser;
        }

        return null;
    }

}
