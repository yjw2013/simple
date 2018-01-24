package com.simple.design.pattern.builder;

import java.util.Arrays;

public class BuilderMain {
	
	public static void main(String[] args) {
		Result result = ResultBuilder.newInstance().code("code12345678")
				.message("msg12345678").type(ResultType.SUCCESS)
				.data(new User("Eric", "Wang")).build();
		
		System.out.println(result);
		
		result = ResultBuilder
				.newInstance()
				.code("code12345678")
				.message("msg12345678")
				.type(ResultType.SUCCESS)
				.data(Arrays.asList(new User("Eric", "Wang"), new User("John",
						"john"))).build();
		System.out.println(result);
	}
	
}
