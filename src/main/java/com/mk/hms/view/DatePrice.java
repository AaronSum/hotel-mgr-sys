package com.mk.hms.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author shellingford
 * @version 2015年3月5日
 */
public class DatePrice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date time;
	private BigDecimal price;

	public DatePrice(Date time, BigDecimal price) {
		super();
		this.time = time;
		this.price = price;
	}

	public DatePrice() {
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
