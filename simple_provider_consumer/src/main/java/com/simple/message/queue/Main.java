package com.simple.message.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	
	public static void main(String[] args) {
		// �ڴ滺����
		BlockingQueue<Data> queue = new LinkedBlockingQueue<Data>();
		
		// ������
		Provider p1 = new Provider(queue);
		Provider p2 = new Provider(queue);
		Provider p3 = new Provider(queue);
		
		// ������
		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);
		Consumer c3 = new Consumer(queue);
		
		// �����̳߳�����,����һ��������̳߳أ����Դ����������̣߳�û�������ʱ�򲻴����̡߳������̴߳��ʱ��Ϊ60s��Ĭ��ֵ��
		ExecutorService cachePool = Executors.newCachedThreadPool();
		cachePool.execute(p1);
		cachePool.execute(p2);
		cachePool.execute(p3);
		cachePool.execute(c1);
		cachePool.execute(c2); 
		cachePool.execute(c3);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		p1.stop();
		p2.stop();
		p3.stop();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
	}
	
}
