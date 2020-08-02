package com.hejun.serviceBase.constant;

public enum MyExceptionConst {
	SUCCESS(200,"成功"),
	USER_INPT_ERROR(400,"用户输入异常"),
	SYSTEM_ERROR(500,"服务器内部异常"),
	INNER_ERROR(600,"自定义异常"),
	ERROR(999,"失败");
	
	private int code;
	private String message;
	
	//枚举类中的构造函数默认是private修饰的，且不能改成public
	MyExceptionConst(int code,String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
