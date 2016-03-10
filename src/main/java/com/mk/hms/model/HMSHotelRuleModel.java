package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "h_hotel_rule")
public class HMSHotelRuleModel {
	
	private Integer id ;//自增id
	private Long hotelid;//酒店id
	private Integer rulecode;//酒店规则
	private String isthreshold;//阈值结算标识
	private Date effectdate;//生效时间
	private Integer state;//执行状态，1为执行成功，0执行等待执行，-1执行中
	private Date createtime;//创建时间
	private Date updatetime;//更新时间
	/**
	 * @return the id
	 */
	@Id
	@Column(name = "id", nullable = false, length = 10)
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the hotelid
	 */
	@Column(name = "hotelid", nullable = true, length = 20)
	public Long getHotelid() {
		return hotelid;
	}
	/**
	 * @param hotelid the hotelid to set
	 */
	public void setHotelid(Long hotelid) {
		this.hotelid = hotelid;
	}
	/**
	 * @return the rulecode
	 */
	@Column(name = "rulecode", nullable = true, length = 20)
	public Integer getRulecode() {
		return rulecode;
	}
	/**
	 * @param rulecode the rulecode to set
	 */
	public void setRulecode(Integer rulecode) {
		this.rulecode = rulecode;
	}
	/**
	 * @return the isthreshold
	 */
	@Column(name = "isthreshold", nullable = true, length = 1)
	public String getIsthreshold() {
		return isthreshold;
	}
	/**
	 * @param isthreshold the isthreshold to set
	 */
	public void setIsthreshold(String isthreshold) {
		this.isthreshold = isthreshold;
	}
	/**
	 * @return the effectdate
	 */
	@Column(name = "effectdate", nullable = true, length = 40)
	public Date getEffectdate() {
		return effectdate;
	}
	/**
	 * @param effectdate the effectdate to set
	 */
	public void setEffectdate(Date effectdate) {
		this.effectdate = effectdate;
	}
	/**
	 * @return the state
	 */
	@Column(name = "state", nullable = true, length = 3)
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @return the createtime
	 */
	@Column(name = "createtime", nullable = true, length = 40)
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the updatetime
	 */
	@Column(name = "updatetime", nullable = true, length = 40)
	public Date getUpdatetime() {
		return updatetime;
	}
	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
