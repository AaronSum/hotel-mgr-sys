package com.mk.hms.mapper;

import com.mk.hms.model.OrderShopping;
import com.mk.hms.model.OrderShoppingCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderShoppingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int countByExample(OrderShoppingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int deleteByExample(OrderShoppingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int insert(OrderShopping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int insertSelective(OrderShopping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    List<OrderShopping> selectByExample(OrderShoppingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    OrderShopping selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int updateByExampleSelective(@Param("record") OrderShopping record, @Param("example") OrderShoppingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int updateByExample(@Param("record") OrderShopping record, @Param("example") OrderShoppingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int updateByPrimaryKeySelective(OrderShopping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_order
     *
     * @mbggenerated Thu Jul 30 12:09:14 CST 2015
     */
    int updateByPrimaryKey(OrderShopping record);
}