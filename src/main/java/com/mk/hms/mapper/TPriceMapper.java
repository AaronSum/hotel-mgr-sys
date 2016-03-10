package com.mk.hms.mapper;

import com.mk.hms.model.TPrice;
import com.mk.hms.model.TPriceCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int countByExample(TPriceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int deleteByExample(TPriceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int insert(TPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int insertSelective(TPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    List<TPrice> selectByExample(TPriceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    TPrice selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int updateByExampleSelective(@Param("record") TPrice record, @Param("example") TPriceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int updateByExample(@Param("record") TPrice record, @Param("example") TPriceCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int updateByPrimaryKeySelective(TPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_price
     *
     * @mbggenerated Fri Jul 24 18:13:25 CST 2015
     */
    int updateByPrimaryKey(TPrice record);
}