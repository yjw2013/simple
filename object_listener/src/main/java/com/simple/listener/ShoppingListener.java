package com.simple.listener;

import com.simple.event.Event;

/**
 * 基于买卖交易行为的监听器
 * @author 于继伟
 *
 */
public interface ShoppingListener {
	
	/**
	 * 下单前的信息校验确认
	 * @param event
	 */
	void buyBefore(Event event);
	
	/**
	 * 下单后的发货动作
	 * @param event
	 */
	void buyAfter(Event event);
	
}	
