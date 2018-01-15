package com.simple.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simple.config.ActionConfig;
import com.simple.config.ConfigurationManager;
import com.simple.context.ActionContext;
import com.simple.invocation.ActionInvocation;

public class StrutsPrepareAndExcuteFilter implements Filter {
	
	// 配置文件中的过滤器配置信息
	private List<String> InterceptorList;
	
	// struts处理的action后缀
	private String extension;
	
	// 配置文件中action配置信息
	private Map<String, ActionConfig> actionConfigs;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 1.准备过滤器链配置
		InterceptorList = ConfigurationManager.getInterceptors();
		// 2.准备constant配置=> 访问后缀的配置信息
		extension = ConfigurationManager.getConstant("struts.action.extension");
		// 3.加载action配置
		actionConfigs = ConfigurationManager.getActionConfig();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 1.强转request和response为 HttpServletRequest 和 HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse  resp = (HttpServletResponse) response;
		
		// 2.获得请求路径
		String path = req.getServletPath();
		
		// 3.判断请求是否需要访问action
		if(!path.endsWith(extension)){
			// 后缀不以".action"结尾 => 不需要访问action资源 => chain.doFIlter()放行
			chain.doFilter(request, response);
			return;
		} else {
			// 后缀以".action"结尾 => 需要访问action 
			// 3.获得需要访问的action名称=>提取需要访问的action名称
			path = path.substring(1);// HellAction.action
			path = path.replace("."+extension, "");// HellAction
			// 4.查找action对应的配置信息
			ActionConfig config = actionConfigs.get(path);
			if(config == null){
				//未找到配置信息 => 抛出异常提示访问的action不存在
				throw new RuntimeException("访问的action不存在!");
			}
			// 找到配置信息  => 获得到配置信息=>继续
			// 5.创建actionInvocation实例,完成对拦截器器链以及action的方法
			ActionInvocation invocation = new ActionInvocation(InterceptorList,config,req,resp);
			// 6.获得结果串 
			String result = invocation.invoke(invocation); //success
			// 7.从配置信息找到结果串对应的路径
			String dispatcherPath = config.getResult().get(result);
			// 找不到结果路径=> 抛出异常提示返回的路径找不到对应页面
			if(dispatcherPath ==null || "".equals(dispatcherPath)){
				throw new RuntimeException("您要访问的结果没有找到配置!");
			}
			// 8.将请求转发到配置的路径
			req.getRequestDispatcher(dispatcherPath).forward(req, resp);
			// 释放资源
			ActionContext.tl.remove();
		}
	}

	@Override
	public void destroy() {
		
	}
	
}
