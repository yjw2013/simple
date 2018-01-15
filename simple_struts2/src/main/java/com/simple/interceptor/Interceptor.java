package com.simple.interceptor;

import com.simple.invocation.ActionInvocation;

public interface Interceptor {
	
	public void init();
	
	public String interceptor(ActionInvocation invocation);
	
	public void destory();
	
}
