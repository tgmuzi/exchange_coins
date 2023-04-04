package com.zeus.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String msg;
	private String i18n;
	private int code = 500;
	private Object data;// 异常返回的data数据节点. add for xiaobin for 20180628
	private Object[] args;// 占位符参数

	public BusinessException(int code, String msg, Object data) {
		super(msg);
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public BusinessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BusinessException(String msg, String i18n) {
		super(msg);
		this.msg = msg;
		this.i18n = i18n;
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public BusinessException(int code, String msg, String i18n) {
		super(msg);
		this.msg = msg;
		this.code = code;
		this.i18n = i18n;
	}

	public BusinessException(int code, String msg, String i18n, Object[] args) {
		super(msg);
		this.msg = msg;
		this.code = code;
		this.i18n = i18n;
		this.args = args;
	}

	public BusinessException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getI18n() {
		return i18n;
	}

	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

	public static String getCause(Exception e) {
		if (null == e) {
			return "";
		}
		StackTraceElement[] stackTrace = e.getStackTrace();
		if (null == stackTrace || stackTrace.length == 0) {
			return "";
		}
		StackTraceElement stackTraceElement = stackTrace[0];
		String cause = " " + stackTraceElement.getFileName() + " " + stackTraceElement.getLineNumber() + " ";
		return cause;

	}
}
