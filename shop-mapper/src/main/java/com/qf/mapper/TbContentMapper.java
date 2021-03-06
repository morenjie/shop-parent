package com.qf.mapper;

import com.qf.pojo.TbContent;
import com.qf.pojo.TbContentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbContentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int countByExample(TbContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int deleteByExample(TbContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int insert(TbContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int insertSelective(TbContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    List<TbContent> selectByExampleWithBLOBs(TbContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    List<TbContent> selectByExample(TbContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    TbContent selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExampleSelective(@Param("record") TbContent record, @Param("example") TbContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExampleWithBLOBs(@Param("record") TbContent record, @Param("example") TbContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByExample(@Param("record") TbContent record, @Param("example") TbContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(TbContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKeyWithBLOBs(TbContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_content
     *
     * @mbggenerated Thu May 05 14:08:56 GMT+08:00 2022
     */
    int updateByPrimaryKey(TbContent record);
}