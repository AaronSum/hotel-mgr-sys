/*
 * Copyright (C) 2008 Happy Fish / YuQing
 *
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
 */

package com.mk.ots.fs;

/**
 * My Exception
 * 
 * @author Happy Fish / YuQing
 * @version Version 1.0
 */
public class FSException extends Exception {

	private static final long serialVersionUID = 6932120718625989601L;

	public FSException() {
	}

	public FSException(String message) {
		super(message);
	}
}
