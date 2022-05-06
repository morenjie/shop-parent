package com.qf.dao;

import com.qf.entity.TbItemParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品规则参数(TbItemParam)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-05 21:40:45
 */
public interface TbItemParamDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbItemParam queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbItemParam> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbItemParam 实例对象
     * @return 对象列表
     */
    List<TbItemParam> queryAll(TbItemParam tbItemParam);

    /**
     * 新增数据
     *
     * @param tbItemParam 实例对象
     * @return 影响行数
     */
    int insert(TbItemParam tbItemParam);

    /**
     * 修改数据
     *
     * @param tbItemParam 实例对象
     * @return 影响行数
     */
    int update(TbItemParam tbItemParam);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}