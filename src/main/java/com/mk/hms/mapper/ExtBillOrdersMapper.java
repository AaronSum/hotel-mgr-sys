package com.mk.hms.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.hms.model.ExtBillOrders;
import com.mk.hms.model.ExtBillOrdersCriteria;

public interface ExtBillOrdersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int countByExample(ExtBillOrdersCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int deleteByExample(ExtBillOrdersCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int insert(ExtBillOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int insertSelective(ExtBillOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    List<ExtBillOrders> selectByExample(ExtBillOrdersCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    ExtBillOrders selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") ExtBillOrders record, @Param("example") ExtBillOrders example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int updateByExample(@Param("record") ExtBillOrders record, @Param("example") ExtBillOrdersCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int updateByPrimaryKeySelective(ExtBillOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bill_orders
     *
     * @mbggenerated Sun Aug 02 10:58:17 CST 2015
     */
    int updateByPrimaryKey(ExtBillOrders record);
    
    int selectOrderNumByType(ExtBillOrders record);
    
    String getIsFreeze(@Param("hotelId")long hotelId);
    
    Float getBillCosts(@Param("begintime") Date begintime,@Param("endtime") Date endtime,@Param("hotelId")long hotelId);
}