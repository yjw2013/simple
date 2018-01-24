package com.simple.design.pattern.decorator;

public class Client {
	
	public static void main(String[] args) {
		TheGreatestSage sage = new Monkey();
		// 第一种写法 单层装饰
		TheGreatestSage bird = new Bird(sage);

		TheGreatestSage fish = new Fish(bird);
		// 第二种写法 双层装饰
		TheGreatestSage fish1 = new Fish(new Bird(sage));
		fish.move();
	}
	
}
