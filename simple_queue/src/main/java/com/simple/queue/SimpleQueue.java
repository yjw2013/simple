package com.simple.queue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简易队列
 * @author 于继伟
 *
 */
public class SimpleQueue {
	
	// 承装元素的集合，必须是有序的，建议LinkedList
	private LinkedList<Object> list = new LinkedList<Object>();
	
	// 计数器，考虑到有多线程并发的情况，用原子类进行操作
	private AtomicInteger count = new AtomicInteger(0);
	
	// 指定队列的上限和下限
	private final int minSize = 0;
	
	private final int maxSize;
	
	public SimpleQueue(int size){
		this.maxSize = size;
	}
	
	// 初始化一个对象用于加锁
	private final Object lock = new Object();
	
	// put(anObject):把anObject加到BlockingQueue里，如果BlockQueue没有空间，则调用此方法的线程被阻断，直到BlockingQueue里面有空间再继续.
	public void put(Object obj){
		synchronized(lock){
			while(count.get() == this.maxSize){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 1.加入元素
			list.add(obj);
			// 2.计数器累加
			count.incrementAndGet();
			// 3.通知另外一个线程（唤醒）
			lock.notify();
			System.out.println("新加入的元素为:" + obj);
		}
	}
	
	// take: 取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到BlockingQueue有新的数据被加入
	public Object take(){
		Object ret = null;
		synchronized(lock){
			while(count.get() == this.minSize){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 1.移除元素的操作
			ret = list.removeFirst();
			// 2.计数器递减
			count.decrementAndGet();
			// 3.唤醒另外一个线程
			lock.notify();
		}
		return ret;
	}
	
	// 返回队列的长度
	public int size(){
		return this.count.get();
	}
	
}	
