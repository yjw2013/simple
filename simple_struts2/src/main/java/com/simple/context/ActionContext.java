package com.simple.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simple.stack.ValueStack;

/**
 * struts2的数据中心
 * @author 于继伟
 *
 */
public class ActionContext {
		
	public static ThreadLocal<ActionContext> tl = new ThreadLocal<ActionContext>();
	
	private Map<String, Object> context;
	
	public ActionContext(HttpServletRequest request,
			HttpServletResponse response, Object action){
		// 准备域
		context = new HashMap<String, Object>();
		// 1.request
		context.put(Constant.REQUEST, request);
		// 2. response
		context.put(Constant.RESPONSE, response);
		// 3. param
		context.put(Constant.PARAM, request.getParameterMap());
		// 4.session
		context.put(Constant.SESSION, request.getSession());
		// 5.application
		context.put(Constant.APPLICATION, request.getSession()
				.getServletContext());
		// 6.valuestack 值栈
		ValueStack vs = new ValueStack();
		// 将action压入栈顶
		vs.push(action);
		// 将值栈放入request域
		request.setAttribute(Constant.VALUE_STACK, vs);
		// 将值栈放入数据中心
		context.put(Constant.VALUE_STACK, vs);
		tl.set(this);
	}
	
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) context.get(Constant.REQUEST);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) context.get(Constant.RESPONSE);
	}

	public HttpSession getSession() {
		return (HttpSession) context.get(Constant.SESSION);
	}

	public ServletContext getApplication() {
		return (ServletContext) context.get(Constant.APPLICATION);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String[]> getParam() {
		return (Map<String, String[]>) context.get(Constant.PARAM);
	}

	public ValueStack getStack() {
		return (ValueStack) context.get(Constant.VALUE_STACK);
	}

	public static ActionContext getActionContext(){
		return ActionContext.tl.get();
	}
	
}
