package com.simple.design.pattern.strategy;

public class StrategyMain {
	
	public static void main(String[] args) {
		String message = "Hello, Eric";
		String phone = "18867103602";
		MessageStrategy strategy = new HySmsStrategy();
		MessageContext context = new MessageContext(strategy);
		// 使用HY短信 Gateway~~
		context.sendMessage(message, phone);

		strategy = new Sms10086Strategy();
		context = new MessageContext(strategy);
		// 10086 sms gateway~~
		context.sendMessage(message, phone);
	}
	
}
