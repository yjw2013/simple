package com.simple.event;

import com.simple.action.BuyerAction;

/**
 * 买家买东西事件
 * @author 于继伟
 *
 */
public class Event {
	
	// 基于买家买东西的行为触发
	private BuyerAction buyerAction;
	
	public Event(BuyerAction buyerAction){
		this.buyerAction = buyerAction;
	}
	
	public Event(){
	}
	
	public void setBuyerAction(BuyerAction buyerAction) {
		this.buyerAction = buyerAction;
	}
	
	public BuyerAction getBuyerAction() {
		return buyerAction;
	}
	
}
