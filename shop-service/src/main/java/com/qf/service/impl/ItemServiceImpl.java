package com.qf.service.impl;

import com.qf.dao.TbItemDao;
import com.qf.entity.TbItem;
import com.qf.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    TbItemDao tbItemDao;

    @Override
    public TbItem selectItemInfo(Long itemId) {
        return tbItemDao.queryById(itemId);
    }
}
