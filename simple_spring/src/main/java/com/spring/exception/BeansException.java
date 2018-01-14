package com.spring.exception;

/**
 * 构建Bean过程中出现的异常
 * @author 于继伟
 *
 */
public class BeansException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 250749932967286534L;
	
	private String message;
	
	public BeansException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
