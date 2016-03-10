package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.utils.SessionUtils;

/**
 * log日志纪录实体
 * @author hdy
 *
 */
@Entity
@Table(name = "h_operate_log")
public class HmsHOperateLogModel {

	private long id; // 主键
	private long hotelid; // 酒店主键
	private String tatablename; // 操作表名
	private String usercode; // 用户名称登录名
	private String username; // 用户名称
	private String ip; // ip地址
	private String functioncode; // 操作功能编码
	private String functionname; // 操作功能名称
	private Date operatetime; // 操作时间
	private String usertype; // 操作用户类型，hms、pms

	/**
	 * 无参构造
	 */
	public HmsHOperateLogModel() {}
	
	/**
	 * 构造方法
	 * @param tatablename 表名
	 * @param functioncode 操作功能code
	 * @param functionname 操作功能名称
	 * @throws SessionTimeOutException 
	 */
	public HmsHOperateLogModel(String tatablename, String functioncode, String functionname) throws SessionTimeOutException {
		this.hotelid = SessionUtils.getThisHotelId(); //当前操作酒店Id
		this.tatablename = tatablename;
		this.functioncode = functioncode;
		this.functionname = functionname;
	}
	
	/**
	 * 构造方法
	 * @param hotelId 当前操作酒店Id
	 * @param tatablename 表名
	 * @param functioncode 操作功能code
	 * @param functionname 操作功能名称
	 */
	public HmsHOperateLogModel(long hotelid,String tatablename, String functioncode, String functionname) {
		this.hotelid = hotelid;
		this.tatablename = tatablename;
		this.functioncode = functioncode;
		this.functionname = functionname;
	}

	@Id
	@Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "hotelid", nullable = false, length = 20)
	public long getHotelid() {
		return hotelid;
	}

	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}

	@Column(name = "tatablename", nullable = true, length = 40)
	public String getTatablename() {
		return tatablename;
	}

	public void setTatablename(String tatablename) {
		this.tatablename = tatablename;
	}

	@Column(name = "usercode", nullable = true, length = 20)
	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	@Column(name = "username", nullable = true, length = 40)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "ip", nullable = true, length = 20)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "functioncode", nullable = true, length = 20)
	public String getFunctioncode() {
		return functioncode;
	}

	public void setFunctioncode(String functioncode) {
		this.functioncode = functioncode;
	}

	@Column(name = "functionname", nullable = true, length = 40)
	public String getFunctionname() {
		return functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	@Column(name = "operatetime", nullable = true)
	public Date getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	@Column(name = "usertype", nullable = true, length = 10)
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
}
