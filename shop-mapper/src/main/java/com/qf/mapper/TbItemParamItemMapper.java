package com.qf.mapper;

import com.qf.pojo.TbItemParamItem;
import com.qf.pojo.TbItemParamItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbItemParamItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int countByExample(TbItemParamItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int deleteByExample(TbItemParamItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int insert(TbItemParamItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int insertSelective(TbItemParamItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    List<TbItemParamItem> selectByExampleWithBLOBs(TbItemParamItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    List<TbItemParamItem> selectByExample(TbItemParamItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    TbItemParamItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExampleSelective(@Param("record") TbItemParamItem record, @Param("example") TbItemParamItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExampleWithBLOBs(@Param("record") TbItemParamItem record, @Param("example") TbItemParamItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExample(@Param("record") TbItemParamItem record, @Param("example") TbItemParamItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(TbItemParamItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKeyWithBLOBs(TbItemParamItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item_param_item
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKey(TbItemParamItem record);
}