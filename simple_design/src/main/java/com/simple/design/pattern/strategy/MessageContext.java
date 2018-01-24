package com.simple.design.pattern.strategy;

public class MessageContext {
	
	private MessageStrategy strategy;

	public MessageContext(MessageStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void sendMessage(String message, String phone) {
		strategy.sendMesssage(message, phone);
	}
	
}
