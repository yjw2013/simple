package com.simple.message.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
	
public class Consumer implements Runnable{
	
	private BlockingQueue<Data> queue;
	
	public Consumer(BlockingQueue<Data> queue){
		this.queue = queue;
	}
	
	// 随机对象
	private static Random r = new Random(); 

	public void run() {
		while(true){
			try {
				// 获取数据
				Data data = this.queue.take();
				// 进行数据处理。处理0 - 1000毫秒模拟耗时
				Thread.sleep(r.nextInt(1000));
				System.out.println("当前消费线程：" + Thread.currentThread().getName() + "， 消费成功，消费数据为id: " + data.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
