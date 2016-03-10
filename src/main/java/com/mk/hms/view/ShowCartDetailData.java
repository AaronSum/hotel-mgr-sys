package com.mk.hms.view;

import java.io.Serializable;

import com.mk.hms.model.CartDetail;
import com.mk.hms.model.WareShopping;

/**
 * 页面展示的数据(cartDetail)
 */
public class ShowCartDetailData extends CartDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
 
	private WareShopping wareShopping;

	private double promotionAmount;
	
	public WareShopping getWareShopping() {
		return wareShopping;
	}

	public void setWareShopping(WareShopping wareShopping) {
		this.wareShopping = wareShopping;
	}

	public double getPromotionAmount() {
		return promotionAmount;
	}

	public void setPromotionAmount(double promotionAmount) {
		this.promotionAmount = promotionAmount;
	}
 
}