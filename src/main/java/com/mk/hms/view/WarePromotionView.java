package com.mk.hms.view;

import java.math.BigDecimal;

public class WarePromotionView {
	/** 商品ID */
	private Long wareId;
	/** 元数据  界面传过来的 */
	private BigDecimal sourceData;
	/** 促销数据 */
	private BigDecimal promotionData;
	/** 最后实际数据 */
	private BigDecimal actualData;
	/** 促销类型 */
	private Integer promotionType;

	/** 促销数据 */
	private String promotionDesc;
	
	public Long getWareId() {
		return wareId;
	}

	public void setWareId(Long wareId) {
		this.wareId = wareId;
	}

	public BigDecimal getSourceData() {
		return sourceData;
	}

	public void setSourceData(BigDecimal sourceData) {
		this.sourceData = sourceData;
	}

	public BigDecimal getPromotionData() {
		return promotionData;
	}

	public void setPromotionData(BigDecimal promotionData) {
		this.promotionData = promotionData;
	}

	public BigDecimal getActualData() {
		return actualData;
	}

	public void setActualData(BigDecimal actualData) {
		this.actualData = actualData;
	}

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public Integer getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(Integer promotionType) {
		this.promotionType = promotionType;
	}

	@Override
	public String toString() {
		return "WarePromotionView{" +
				"wareId=" + wareId +
				", sourceData=" + sourceData +
				", promotionData=" + promotionData +
				", actualData=" + actualData +
				", promotionDesc='" + promotionDesc + '\'' +
				'}';
	}
}