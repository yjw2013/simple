package com.simple.message.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
	
public class Consumer implements Runnable{
	
	private BlockingQueue<Data> queue;
	
	public Consumer(BlockingQueue<Data> queue){
		this.queue = queue;
	}
	
	// �������
	private static Random r = new Random(); 

	public void run() {
		while(true){
			try {
				// ��ȡ����
				Data data = this.queue.take();
				// �������ݴ�������0 - 1000����ģ���ʱ
				Thread.sleep(r.nextInt(1000));
				System.out.println("��ǰ�����̣߳�" + Thread.currentThread().getName() + "�� ���ѳɹ�����������Ϊid: " + data.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
