package com.simple.design.pattern.strategy;

public class Sms10086Strategy implements MessageStrategy{
	
	@Override
	public void sendMesssage(String message, String phone) {
		System.out.println("10086 sms gateway~~");
	}
}
