package com.hejun.serviceBase.handler;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.hejun.serviceBase.constant.MyExceptionConst;
import com.hejun.serviceBase.exception.InnerException;
import com.hejun.serviceBase.responseModel.R;
import lombok.extern.slf4j.Slf4j;
/**
 * 全局异常处理
 * @author HeJun
 *
 */
//@ControllerAdvice，是Spring3.2提供的新注解,它是一个Controller增强器,可对controller中被 @RequestMapping注解的方法加一些逻辑处理。最常用的就是异常处理
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	//@exceptionHandler注解用来指明异常的处理类型，即如果这里指定为 NullpointerException，则数组越界异常就不会进到这个方法中来。
	@ExceptionHandler(Exception.class)
	/**
	 * 捕获服务器内部异常
	 * @param e
	 * @return
	 */
	public R excpetion(Exception e) {
		//将异常信息记录到日志文件中
		log.error(e.getMessage(), e);
		e.printStackTrace();
		return R.error().code(MyExceptionConst.SYSTEM_ERROR.getCode()).message(MyExceptionConst.SYSTEM_ERROR.getMessage()+e.toString());
		
	}
	
	@ExceptionHandler(ArithmeticException.class)
	public R arithmeticException(ArithmeticException e) {
		log.error(e.getMessage(), e);
		e.printStackTrace();
		return R.error("算术运算异常"+e.toString());
	}
	
	/**
	 * 捕获自定义异常
	 * @param e
	 * @return
	 * @throws FileNotFoundException 
	 */
	@ExceptionHandler(InnerException.class)
	public R innerException(InnerException e) {	
		log.error(e.getMessage(), e);
		e.printStackTrace();
		return R.error().code(MyExceptionConst.INNER_ERROR.getCode()).message(e.getMessage());
	}
	/**
	 * 捕获 Spring框架数据验证失败异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public R bindException(BindException e) {
		log.error(e.getMessage(), e);
		e.printStackTrace();
		return R.error("数据格式异常"+e.toString());
	}
}	
