package com.qf.dao;

import com.qf.entity.TbItemDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品描述表(TbItemDesc)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-05 21:40:45
 */
public interface TbItemDescDao {

    /**
     * 通过ID查询单条数据
     *
     * @param itemId 主键
     * @return 实例对象
     */
    TbItemDesc queryById(Long itemId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbItemDesc> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbItemDesc 实例对象
     * @return 对象列表
     */
    List<TbItemDesc> queryAll(TbItemDesc tbItemDesc);

    /**
     * 新增数据
     *
     * @param tbItemDesc 实例对象
     * @return 影响行数
     */
    int insert(TbItemDesc tbItemDesc);

    /**
     * 修改数据
     *
     * @param tbItemDesc 实例对象
     * @return 影响行数
     */
    int update(TbItemDesc tbItemDesc);

    /**
     * 通过主键删除数据
     *
     * @param itemId 主键
     * @return 影响行数
     */
    int deleteById(Long itemId);

}