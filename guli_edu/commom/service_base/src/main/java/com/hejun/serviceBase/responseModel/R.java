package com.hejun.serviceBase.responseModel;

import java.util.HashMap;
import java.util.Map;

import com.hejun.serviceBase.constant.MyExceptionConst;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("R统一返回结果类")
public class R {
	@ApiModelProperty("是否成功")
	private Boolean success;
	@ApiModelProperty("返回码")
	private Integer code;
	@ApiModelProperty("返回消息")
	private String message;
	@ApiModelProperty("返回数据")
	private Map<String, Object> data = new HashMap<String, Object>();
	
	
	public static R error() {
		
		R r = new R();
		r.setSuccess(false);	
		r.setCode(MyExceptionConst.ERROR.getCode());
		r.setMessage(MyExceptionConst.ERROR.getMessage());
		return r;
	}
	
	public static R error(String message) {
		R r = new R();
		r.setSuccess(false);
		r.setCode(MyExceptionConst.ERROR.getCode());
		r.setMessage(message);
		return r;
	}
	
	public static R error(MyExceptionConst e) {
		R r = new R();
		r.setSuccess(false);
		r.setCode(e.getCode());
		r.setMessage(e.getMessage());
		return r;
	}
	
	public static R ok() {
		
		R r = new R();
		r.setSuccess(true);
		r.setCode(MyExceptionConst.SUCCESS.getCode());
		r.setMessage(MyExceptionConst.SUCCESS.getMessage());
	
		return r;
	}
	public static R ok(String message) {
		
		R r = new R();
		r.setSuccess(true);
		r.setCode(MyExceptionConst.SUCCESS.getCode());
		r.setMessage(message);
	
		return r;
	}
	
	public R message(String message) {
		this.setMessage(message);		
		return this;
	}
	
	
	public R code(Integer code) {
		this.setCode(code);
		return this;
	}
	
	public R data(Map<String, Object> map) {
		this.setData(map);
		return this;
	}
	
	public R data(String key,Object value) {
		this.data.put(key, value);	
		return this;
	}
}
