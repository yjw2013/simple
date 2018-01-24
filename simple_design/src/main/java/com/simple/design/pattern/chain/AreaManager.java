package com.simple.design.pattern.chain;

public class AreaManager extends DiscountHandler{
	
	@Override
	public void handle(double discount) {
		if(discount > 0.5) {
			System.out.println("AreaManager处理折扣:" + discount);
		}else {
			System.out.println("折扣太低了，真不能卖了～");
		}
	}
	
}
