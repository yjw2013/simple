package com.simple.design.pattern.chain;

public class ChainMain {
	
	public static void main(String[] args) {
		DiscountHandler saleman = new SalemanHandler();
		DiscountHandler saleManager = new SaleManager();
		DiscountHandler areaManager = new AreaManager();
		
		saleman.setSuccessor(saleManager);
		saleManager.setSuccessor(areaManager);
		
		double discount = 0.3;
		/**
		 * 打三折
		 */
		saleman.handle(discount);
		
		/**
		 * 九五折
		 */
		discount = 0.95;
		saleman.handle(discount);
		
		/**
		 * 七五折
		 */
		discount = 0.75;
		saleman.handle(discount);
		
		/**
		 * 五五折
		 */
		discount = 0.55;
		saleman.handle(discount);
	}
	
}
