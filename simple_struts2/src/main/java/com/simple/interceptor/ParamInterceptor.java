package com.simple.interceptor;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.simple.context.ActionContext;
import com.simple.invocation.ActionInvocation;
import com.simple.stack.ValueStack;

public class ParamInterceptor implements Interceptor {

	@Override
	public void init() {
		
	}

	@Override
	public String interceptor(ActionInvocation invocation) {
		// 1.获得参数
		// 2.获得action对象
		ActionContext ac = invocation.getActionContext();
		ValueStack vs = ac.getStack();
		Object action = vs.seek();
		// 3.封装
		try {
			BeanUtils.populate(action, ac.getRequest().getParameterMap());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// 4.放行
		return invocation.invoke(invocation);
	}

	@Override
	public void destory() {
		
	}
	
}
