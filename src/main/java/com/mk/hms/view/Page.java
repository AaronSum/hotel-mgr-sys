package com.mk.hms.view;

/**
 * 分页对象
 * @author hdy
 *
 */
public class Page {

	private int pageNum;
	private int pageSize;
	private int total;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartIndex() {
		return (this.pageNum - 1) * this.pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
