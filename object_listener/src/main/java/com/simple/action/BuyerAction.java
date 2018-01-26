package com.simple.action;

import com.simple.event.Event;
import com.simple.listener.ShoppingListener;

/**
 * 买家行为
 * @author 于继伟
 *
 */
public class BuyerAction {
	
	private ShoppingListener shoppingListener;
	
	public void registerListener(ShoppingListener shoppingListener){
		this.shoppingListener = shoppingListener;
	}
	
	public void buy(){
		Event event = null;
		if(shoppingListener != null){
			event = new Event(this);
			shoppingListener.buyBefore(event);
		}
		System.out.println("----------买家买东西--------");
		
		shoppingListener.buyAfter(event);
	}
	
}
