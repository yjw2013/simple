package com.simple.design.pattern.observer;

public class ObserverMain {
	
	public static void main(String[] args) {
		KtvOrder order = new KtvOrder("Eric");

		KtvParticipant john = new KtvParticipant("John");
		KtvParticipant lucy = new KtvParticipant("Lucy");
		
		order.addObserver(john);
		order.addObserver(lucy);
		
		order.sendMessage("西城广场银乐迪506唱歌");
	}
	
}
