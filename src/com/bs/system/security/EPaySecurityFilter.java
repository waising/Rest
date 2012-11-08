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
 * Title:  ��ȫ��������
 * </p>
 * 
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: EPay�Ŷ�
 * </p>
 * 
 * <p>
 * ����ʱ��: 2011-06-28 ����11:22:30
 * </p>
 * 
 * @author �ֹ���
 * 
 * @version 1.0
 * 
 *          �޶���¼: 2011-06-28 ����11:22:30  �ֹ���  ����
 * 
 */

public class EPaySecurityFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(EPaySecurityFilter.class);
	
	
	/**
	 * ����bean������
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
	 * ��ʼ��������
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.interceptorName = filterConfig.getInitParameter("beanName");
		Assert.notNull(this.interceptorName, "�����趨��ȫ���Spring Bean ����");
		Assert.hasLength(this.interceptorName.trim(), "�����趨��ȫ���Spring Bean ����");
	}

}
