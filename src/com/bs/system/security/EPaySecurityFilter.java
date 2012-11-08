package com.bs.system.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bs.restframework.context.util.SystemContextUtil;


/**
 * <p>
 * Title:  安全体测过滤类
 * </p>
 * 
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: EPay团队
 * </p>
 * 
 * <p>
 * 建立时间: 2011-06-28 上午11:22:30
 * </p>
 * 
 * @author 林光延
 * 
 * @version 1.0
 * 
 *          修订记录: 2011-06-28 上午11:22:30  林光延  建立
 * 
 */

public class EPaySecurityFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(EPaySecurityFilter.class);
	
	
	/**
	 * 拦截bean的名称
	 */
	protected String interceptorName = null;
	
	
	

    
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		DefaultResCheck check = (DefaultResCheck) SystemContextUtil.getSystemContext().getBean(this.interceptorName);
		if(check.doCheck(request, response)){
			chain.doFilter(request, response);
		}
        
	}

	/**
	 * 初始化过滤器
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.interceptorName = filterConfig.getInitParameter("beanName");
		Assert.notNull(this.interceptorName, "请先设定安全检测Spring Bean 名称");
		Assert.hasLength(this.interceptorName.trim(), "请先设定安全检测Spring Bean 名称");
	}

}
