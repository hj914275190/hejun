package com.hejun.serviceBase.exception;

import org.springframework.stereotype.Component;

import com.hejun.serviceBase.constant.MyExceptionConst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
public class InnerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
		
	public InnerException() {
		super(MyExceptionConst.INNER_ERROR.getMessage());
	}


}
