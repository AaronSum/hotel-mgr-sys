package com.mk.hms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 规则枚举表
 * @author hao.jiang 2015-7-21
 *
 */
@Entity
@Table(name = "b_area_rule")
public class HmsBAreaRule {
	
	private long id;
	private int ruleCode;
	private String ruleName;
	private String description;
	private int createBy;
	private int updateBy;
	private Date createTime;
	private Date updateTime;
	
	@Id
	@Column(name = "id", nullable = false, length = 30)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "rulecode", nullable = false, length = 11)
	public int getRuleCode() {
		return ruleCode;
	}
	
	public void setRuleCode(int ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	@Column(name = "rulename", nullable = true, length = 30)
	public String getRuleName() {
		return ruleName;
	}
	
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	@Column(name = "description", nullable = false, length = 200)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "createby", nullable = false, length = 11)
	public int getCreateBy() {
		return createBy;
	}
	
	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}
	
	@Column(name = "updateby", nullable = true, length = 11)
	public int getUpdateBy() {
		return updateBy;
	}
	
	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "createtime", nullable = false, length = 20)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updatetime", nullable = true, length = 20)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
