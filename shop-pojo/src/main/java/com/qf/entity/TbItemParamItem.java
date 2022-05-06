package com.qf.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格和商品的关系表(TbItemParamItem)实体类
 *
 * @author makejava
 * @since 2022-05-05 21:40:45
 */
public class TbItemParamItem implements Serializable {
    private static final long serialVersionUID = 153751995823149680L;
    
    private Long id;
    /**
    * 商品ID
    */
    private Long itemId;
    /**
    * 参数数据，格式为json格式
    */
    private String paramData;
    
    private Date created;
    
    private Date updated;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}