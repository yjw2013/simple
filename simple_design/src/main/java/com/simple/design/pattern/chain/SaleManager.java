package com.simple.design.pattern.chain;

public class SaleManager extends DiscountHandler{
	
	@Override
	public void handle(double discount) {
		if(discount > 0.7){
			System.out.println("SaleManager处理折扣:" +discount);
		} else {
			this.getSuccessor().handle(discount);
		}
	}
	
}
