package com.mk.hms.exception;

/**
 * session 超时异常信息
 * @author hdy
 *
 */
public class SessionTimeOutException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public SessionTimeOutException(int errorCode, Throwable t) {
		super(t);
		this.errorCode = errorCode;
	}
}
