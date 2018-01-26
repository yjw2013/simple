package com.simple.listener.impl;

import com.simple.action.SellerAction;
import com.simple.event.Event;
import com.simple.listener.ShoppingListener;

/**
 * 监听事件的具体实现，下单前和下单后的分别实现
 * @author 于继伟
 *
 */
public class ShoppingListenerImpl implements ShoppingListener{

	@Override
	public void buyBefore(Event event) {
		System.out.println("下单前做一些事情...");
	}

	@Override
	public void buyAfter(Event event) {
		SellerAction action = new SellerAction();
		action.sendGoods();
	}
	
}
