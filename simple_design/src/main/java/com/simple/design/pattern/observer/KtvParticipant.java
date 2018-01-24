package com.simple.design.pattern.observer;

import java.util.Observable;
import java.util.Observer;

public class KtvParticipant implements Observer{
	
	private String name;

	public KtvParticipant(String name) {
		this.name = name;
	}

	@Override
	public void update(Observable o, Object arg) {
		KtvOrder order = null;
		
		if(o instanceof KtvOrder){
			order = (KtvOrder) o;
			System.out.println(String.format("[%s]收到来自[%s]的通知信息==> : %s", name,
					order.getName(), arg.toString()));
		}
	}
	
}
