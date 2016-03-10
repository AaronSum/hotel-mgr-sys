package com.mk.hms.mapper;

import com.mk.hms.model.WarePromotionMapping;
import com.mk.hms.model.WarePromotionMappingCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarePromotionMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int countByExample(WarePromotionMappingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int deleteByExample(WarePromotionMappingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int insert(WarePromotionMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int insertSelective(WarePromotionMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    List<WarePromotionMapping> selectByExample(WarePromotionMappingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    WarePromotionMapping selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int updateByExampleSelective(@Param("record") WarePromotionMapping record, @Param("example") WarePromotionMappingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int updateByExample(@Param("record") WarePromotionMapping record, @Param("example") WarePromotionMappingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int updateByPrimaryKeySelective(WarePromotionMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_promotion_mapping
     *
     * @mbggenerated Fri Aug 14 12:29:06 CST 2015
     */
    int updateByPrimaryKey(WarePromotionMapping record);
}