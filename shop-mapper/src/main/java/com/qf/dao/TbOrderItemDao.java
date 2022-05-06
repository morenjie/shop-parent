package com.qf.dao;

import com.qf.entity.TbOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbOrderItem)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-05 21:40:46
 */
public interface TbOrderItemDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbOrderItem queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbOrderItem> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbOrderItem 实例对象
     * @return 对象列表
     */
    List<TbOrderItem> queryAll(TbOrderItem tbOrderItem);

    /**
     * 新增数据
     *
     * @param tbOrderItem 实例对象
     * @return 影响行数
     */
    int insert(TbOrderItem tbOrderItem);

    /**
     * 修改数据
     *
     * @param tbOrderItem 实例对象
     * @return 影响行数
     */
    int update(TbOrderItem tbOrderItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}