package com.simple.design.pattern.singleton;

public class SingleTon {
	
	private volatile static SingleTon instance = null;
	
	private SingleTon(){
	}
	
	public synchronized static SingleTon getInstance(){
		if(null == instance){
			instance = new SingleTon();
		}
		return instance;
	}
	
}
