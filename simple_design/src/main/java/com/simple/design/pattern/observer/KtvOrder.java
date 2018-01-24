package com.simple.design.pattern.observer;

import java.util.Observable;

public class KtvOrder extends Observable{
	
	private String name;
	
	public KtvOrder(String name){
		this.name = name;
	}
	
	public void sendMessage(String message){
		System.out.println(String.format("[%s]发送通知：%s", name, message));
		this.setChanged();
		this.notifyObservers(message);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
