package com.mk.hms.view;

import java.io.Serializable;
import java.util.List;

import com.mk.hms.model.Cart;

/**
 * 页面展示的数据(cart + cartDetail)
 */
public class ShowCartData extends Cart implements Serializable{
	
	private static final long serialVersionUID = 1L;
 
	private List<ShowCartDetailData> cartDetails = null;

	public List<ShowCartDetailData> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<ShowCartDetailData> cartDetails) {
		this.cartDetails = cartDetails;
	}
	 
}