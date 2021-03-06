package com.mk.hms.mapper;

import com.mk.hms.model.Qrcode;
import com.mk.hms.model.QrcodeCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QrcodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int countByExample(QrcodeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int deleteByExample(QrcodeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int insert(Qrcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int insertSelective(Qrcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    List<Qrcode> selectByExample(QrcodeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    Qrcode selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int updateByExampleSelective(@Param("record") Qrcode record, @Param("example") QrcodeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int updateByExample(@Param("record") Qrcode record, @Param("example") QrcodeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int updateByPrimaryKeySelective(Qrcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_qrcode
     *
     * @mbggenerated Tue Jul 21 15:05:28 CST 2015
     */
    int updateByPrimaryKey(Qrcode record);
}