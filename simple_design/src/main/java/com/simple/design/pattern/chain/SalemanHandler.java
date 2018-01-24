package com.simple.design.pattern.chain;

public class SalemanHandler extends DiscountHandler{
	
	@Override
	public void handle(double discount) {
		if(discount > 0.9){
			System.out.println("Saleman处理折扣:" + discount);
		} else {
			this.getSuccessor().handle(discount);
		}
	}
	
}
