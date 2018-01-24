package com.simple.design.pattern.builder;

public class ResultBuilder {
	
	private ResultType type;

	private String code;

	private String message;

	private Object data;
	
	public static ResultBuilder newInstance() {
		return new ResultBuilder();
	}

	public ResultBuilder type(ResultType type) {
		this.type = type;
		return this;
	}

	public ResultBuilder code(String code) {
		this.code = code;
		return this;
	}

	public ResultBuilder message(String message) {
		this.message = message;
		return this;
	}

	public ResultBuilder data(Object data) {
		this.data = data;
		return this;
	}

	public Result build() {
		Result result = new Result();
		result.setCode(code);
		result.setData(data);
		result.setMessage(message);
		result.setType(type);
		return result;
	}
	
}
