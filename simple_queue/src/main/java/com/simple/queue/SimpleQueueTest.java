package com.simple.queue;

import java.util.concurrent.TimeUnit;

public class SimpleQueueTest {
	
	public static void main(String[] args) {
		
		final SimpleQueue queue = new SimpleQueue(5);
		queue.put("a");
		queue.put("b");
		queue.put("c");
		queue.put("d");
		queue.put("e");
		
		System.out.println("当前容器的长度:" + queue.size());
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				queue.put("f");
				queue.put("g");
			}
		},"t1");
		
		t1.start();
		
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Object o1 = queue.take();
				System.out.println("移除的元素为:" + o1);
				Object o2 = queue.take();
				System.out.println("移除的元素为:" + o2);
			}
		},"t2");
		
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t2.start();
		
	}
	
}
