package com.simple.client;

import com.simple.action.BuyerAction;
import com.simple.listener.impl.ShoppingListenerImpl;

/**
 * 客户端操作
 * @author 于继伟
 *
 */
public class Client {
	
	public static void main(String[] args) {
		
		// 买家行为
		BuyerAction buyerAction = new BuyerAction();
		
		// 注册监听器
		buyerAction.registerListener(new ShoppingListenerImpl());
		
		// 具体行为
		buyerAction.buy();
		
	}
	
}
