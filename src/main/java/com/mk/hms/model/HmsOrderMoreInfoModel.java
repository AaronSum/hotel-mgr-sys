package com.mk.hms.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单更多信息实体
 * @author qhdhiman
 *
 */
@Entity
@Table(name = "b_orderbusiness_log")
public class HmsOrderMoreInfoModel {

	private long orderId; // 订单id
	private long orderMethod; //订单来源
	private BigDecimal allCost; //原价
	private BigDecimal yhje; //眯客优惠券金额
	private BigDecimal cjje; //成交价
	private BigDecimal yfje; //预付金额
	private BigDecimal dfje; //到付金额
	private long orderType; //支付方式
	private long pmsSend;//乐住币下发状态 ; 200:已下发，150:未下发，50:未触发,0:--
	private String sendReason;//乐住币下发失败原因
	private BigDecimal yfbtje;//预付补贴金额
	private BigDecimal dfbtje;//到付补贴金额
	private BigDecimal lzbbtje;//乐住币补贴金额
	private int invalidreason;
	@Id
	@Column(name = "orderId", nullable = false, length = 20)
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "ordermethod", nullable = true, length = 10)
	public long getOrderMethod() {
		return orderMethod;
	}

	public void setOrderMethod(long orderMethod) {
		this.orderMethod = orderMethod;
	}

	@Column(name = "allcost", nullable = true, length = 10)
	public BigDecimal getAllCost() {
		return allCost;
	}

	public void setAllCost(BigDecimal allCost) {
		this.allCost = allCost;
	}

	@Column(name = "yhje", nullable = true, length = 10)
	public BigDecimal getYhje() {
		return yhje;
	}

	public void setYhje(BigDecimal yhje) {
		this.yhje = yhje;
	}

	@Column(name = "cjje", nullable = true, length = 10)
	public BigDecimal getCjje() {
		return cjje;
	}

	public void setCjje(BigDecimal cjje) {
		this.cjje = cjje;
	}

	@Column(name = "yfje", nullable = true, length = 10)
	public BigDecimal getYfje() {
		return yfje;
	}

	public void setYfje(BigDecimal yfje) {
		this.yfje = yfje;
	}

	@Column(name = "dfje", nullable = true, length = 10)
	public BigDecimal getDfje() {
		return dfje;
	}

	public void setDfje(BigDecimal dfje) {
		this.dfje = dfje;
	}

	@Column(name = "orderType", nullable = true, length = 10)
	public long getOrderType() {
		return orderType;
	}

	public void setOrderType(long orderType) {
		this.orderType = orderType;
	}

	@Column(name = "pmsSend", nullable = true, length = 10)
	public long getPmsSend() {
		return pmsSend;
	}

	public void setPmsSend(long pmsSend) {
		this.pmsSend = pmsSend;
	}

	@Column(name = "sendReason", nullable = true, length = 200)
	public String getSendReason() {
		return sendReason;
	}

	public void setSendReason(String sendReason) {
		this.sendReason = sendReason;
	}

	public BigDecimal getYfbtje() {
		return yfbtje;
	}

	public void setYfbtje(BigDecimal yfbtje) {
		this.yfbtje = yfbtje;
	}

	public BigDecimal getDfbtje() {
		return dfbtje;
	}

	public void setDfbtje(BigDecimal dfbtje) {
		this.dfbtje = dfbtje;
	}

	public BigDecimal getLzbbtje() {
		return lzbbtje;
	}

	public void setLzbbtje(BigDecimal lzbbtje) {
		this.lzbbtje = lzbbtje;
	}

	public int getInvalidreason() {
		return invalidreason;
	}

	public void setInvalidreason(int invalidreason) {
		this.invalidreason = invalidreason;
	}	
	
	
}
