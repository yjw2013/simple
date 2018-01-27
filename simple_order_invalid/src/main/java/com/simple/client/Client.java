package com.simple.client;

import com.simple.tmail.Tmail;
import com.simple.util.UUID;

/**
 * 双十一APP客户端
 * @author 于继伟
 *
 */
public class Client {
	
	public static void main(String[] args) {
		System.out.println("双十一开始...");
		Tmail tmail = new Tmail();
		Thread thread = new Thread(tmail);
		thread.start();
		
		tmail.order(UUID.randomUUID().toString(), 1000); // 订单1
		tmail.order(UUID.randomUUID().toString(), 2000); // 订单2
		tmail.order(UUID.randomUUID().toString(), 3000); // 订单3 
		tmail.order(UUID.randomUUID().toString(), 4000); // 订单4
		tmail.order(UUID.randomUUID().toString(), 5000); // 订单5
	}
	
}
