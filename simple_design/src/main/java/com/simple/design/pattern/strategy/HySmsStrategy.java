package com.simple.design.pattern.strategy;

public class HySmsStrategy implements MessageStrategy{
	
	@Override
	public void sendMesssage(String message, String phone) {
		System.out.println("使用HY短信 Gateway~~");
	}
}
