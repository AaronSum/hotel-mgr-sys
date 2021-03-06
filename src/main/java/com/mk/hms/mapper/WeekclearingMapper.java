package com.mk.hms.mapper;

import com.mk.hms.model.Weekclearing;
import com.mk.hms.model.WeekclearingCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeekclearingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int countByExample(WeekclearingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int deleteByExample(WeekclearingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int insert(Weekclearing record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int insertSelective(Weekclearing record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    List<Weekclearing> selectByExample(WeekclearingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    Weekclearing selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int updateByExampleSelective(@Param("record") Weekclearing record, @Param("example") WeekclearingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int updateByExample(@Param("record") Weekclearing record, @Param("example") WeekclearingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int updateByPrimaryKeySelective(Weekclearing record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weekclearing
     *
     * @mbggenerated Tue Oct 20 13:44:56 CST 2015
     */
    int updateByPrimaryKey(Weekclearing record);
}