package com.simple.order;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 订单实体
 * @author 于继伟
 *
 */
public class Order implements Delayed {
	
	// 订单编号
	private String orderId;
	
	// 截止时间
	private long endTime;
	
	// 定义时间工具类
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	
	public Order(String orderId, long endTime) {
		this.orderId = orderId;
		this.endTime = endTime;
	}
	
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 用来判断是否到了截止时间
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return endTime - System.currentTimeMillis();
	}
	
	/**
	 * 相互比较排序用
	 */
	@Override
	public int compareTo(Delayed delayed) {
		Order order = (Order) delayed;
		return this.getDelay(this.timeUnit) - order.getDelay(this.timeUnit) > 0 ? 1:0;  
	}
	
}
