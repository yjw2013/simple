package com.simple.tmail;

import java.util.Date;
import java.util.concurrent.DelayQueue;

import com.simple.order.Order;

/**
 * 天猫商城
 * @author 于继伟
 *
 */
public class Tmail implements Runnable {
	
	private DelayQueue<Order> queue = new DelayQueue<Order>();
	
	// 天猫商城是否营业的标识，有可能他们有不做生意的时候
	public boolean isOpen = true;
	
	/**
	 * 客户拍下后但是没有付款的时候调用，开始计时
	 * @param orderId
	 * @param validTime
	 */
	public void order(String orderId, long validTime){
		// 淘宝商城拍下30分钟未付款，自动取消订单
		// Order order = new Order(orderId, 1000 * 60 * 30 + System.currentTimeMillis());
		// 为了测试方便，我们将取消时间变为30秒
		Order order = new Order(orderId, validTime + System.currentTimeMillis());
		System.out.println("订单编号为:" + orderId + "的订单已经拍下..."+ new Date() +"开始计时...");
		this.queue.add(order);
	}
	
	/**
	 * 订单失效
	 * @param order
	 */
	public void invalid(Order order){
		System.out.println("订单编号为:" + order.getOrderId() + "期限到，"+ new Date() +"已经失效...");
	}
	
	@Override
	public void run() {
		while(isOpen){ // 天猫商城正在营业中...
			try {
				Order order = this.queue.take();
				invalid(order);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
