package com.simple.design.pattern.strategy;

public class CustomerStrategy implements MessageStrategy{

	@Override
	public void sendMesssage(String message, String phone) {
		System.out.println("使用省份普通sms Gate way~~");
	}
	
}
