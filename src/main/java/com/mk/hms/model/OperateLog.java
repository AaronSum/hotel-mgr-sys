package com.mk.hms.model;

import java.util.Date;

public class OperateLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.id
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.hotelid
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private Long hotelid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.tatablename
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private String tatablename;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.usercode
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private String usercode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.username
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.ip
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.functioncode
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private String functioncode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.functionname
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private String functionname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.operatetime
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private Date operatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column h_operate_log.usertype
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    private String usertype;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.id
     *
     * @return the value of h_operate_log.id
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.id
     *
     * @param id the value for h_operate_log.id
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.hotelid
     *
     * @return the value of h_operate_log.hotelid
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public Long getHotelid() {
        return hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.hotelid
     *
     * @param hotelid the value for h_operate_log.hotelid
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.tatablename
     *
     * @return the value of h_operate_log.tatablename
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public String getTatablename() {
        return tatablename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.tatablename
     *
     * @param tatablename the value for h_operate_log.tatablename
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setTatablename(String tatablename) {
        this.tatablename = tatablename == null ? null : tatablename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.usercode
     *
     * @return the value of h_operate_log.usercode
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public String getUsercode() {
        return usercode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.usercode
     *
     * @param usercode the value for h_operate_log.usercode
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.username
     *
     * @return the value of h_operate_log.username
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.username
     *
     * @param username the value for h_operate_log.username
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.ip
     *
     * @return the value of h_operate_log.ip
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.ip
     *
     * @param ip the value for h_operate_log.ip
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.functioncode
     *
     * @return the value of h_operate_log.functioncode
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public String getFunctioncode() {
        return functioncode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.functioncode
     *
     * @param functioncode the value for h_operate_log.functioncode
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setFunctioncode(String functioncode) {
        this.functioncode = functioncode == null ? null : functioncode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.functionname
     *
     * @return the value of h_operate_log.functionname
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public String getFunctionname() {
        return functionname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.functionname
     *
     * @param functionname the value for h_operate_log.functionname
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setFunctionname(String functionname) {
        this.functionname = functionname == null ? null : functionname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.operatetime
     *
     * @return the value of h_operate_log.operatetime
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public Date getOperatetime() {
        return operatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.operatetime
     *
     * @param operatetime the value for h_operate_log.operatetime
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column h_operate_log.usertype
     *
     * @return the value of h_operate_log.usertype
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column h_operate_log.usertype
     *
     * @param usertype the value for h_operate_log.usertype
     *
     * @mbggenerated Tue Jul 21 13:07:06 CST 2015
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }
}