package com.qf.mapper;

import com.qf.pojo.TbOrderItem;
import com.qf.pojo.TbOrderItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbOrderItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int countByExample(TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int deleteByExample(TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int insert(TbOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int insertSelective(TbOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    List<TbOrderItem> selectByExample(TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    TbOrderItem selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExampleSelective(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExample(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(TbOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKey(TbOrderItem record);
}