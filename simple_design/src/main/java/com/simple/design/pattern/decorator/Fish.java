package com.simple.design.pattern.decorator;

public class Fish extends Change{
	
	public Fish(TheGreatestSage sage) {
		super(sage);
	}

	@Override
	public void move() {
		// 代码
		System.out.println("Fish Move");
	}
	
}
