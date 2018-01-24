package com.simple.design.pattern.chain;

public abstract class DiscountHandler {
		
	private DiscountHandler successor;
	
	public abstract void handle(double discount);
	
	public void setSuccessor(DiscountHandler successor) {
		this.successor = successor;
	}
	
	public DiscountHandler getSuccessor() {
		return successor;
	}
	
}
