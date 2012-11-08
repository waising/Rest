package com.bs.system.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bs.restframework.adaptive.util.DataAdaptiveUtil;
import com.bs.restframework.context.util.SystemContextUtil;



/**
 * <p>
 * Title:  权限认证类
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

public class DefaultResCheck {

	private static Logger log = LoggerFactory.getLogger(DefaultResCheck .class);
	
    /**
	 * 不检测Web资源
	 */
	public  List<String> noCheckWebRes = null;
	
	/**
	 * 
	 */
	public HashMap<String,List<String>> sysServiceList = null;

	public HashMap<String, List<String>> getSysServiceList() {
		return sysServiceList;
	}

	public void setSysServiceList(HashMap<String, List<String>> sysServiceList) {
		this.sysServiceList = sysServiceList;
	}
	
	/**
	 * 不能访问的服务
	 */
	public List<String> noVisitServiceList;

	public void setNoVisitServiceList(List<String> noVisitServiceList) {
		this.noVisitServiceList = noVisitServiceList;
	}
	
	/**
	 * 非法跳转页全路径
	 */
	public String defaultPage = null;
	/**
	 * 
	 * <p>函数名称：发送页面        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param request
	 * @param response
	 * @param dot
	 * @param code
	 * @param msg
	 *
	 * @date   创建时间：2011-7-13
	 * @author 作者：林光延
	 */
	private void sendView(ServletRequest request, ServletResponse response,String dot,String code ,String msg){
		if(dot.equalsIgnoreCase("do") || dot.equalsIgnoreCase("file") || dot.equalsIgnoreCase("jsp")){
			sendToDefault(request,response,defaultPage,msg);
		}else if(dot.equalsIgnoreCase("json") || dot.equalsIgnoreCase("xml")||dot.equalsIgnoreCase("String")){
			writeResponse(request,response,dot,code,msg);
		}
	}

	/**urlDot[1]
	 * 
	 * <p>函数名称：  登录合法性校验      </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param request
	 * @param response
	 * @return
	 *
	 * @date   创建时间：2011-7-13
	 * @author 作者：林光延
	 */
	public boolean doCheck(ServletRequest request, ServletResponse response) {
		
		
		 String url = ((HttpServletRequest)request).getRequestURI();
		 log.debug("调用地址 URL: " + url);
		 String url2 = url.replace(".", "#");
		 String[] urlDot =(url2.trim()+"#").split("#");
			
		// 如果url为不可访问的服务，则跳到出错页面
		if(this.noVisit(url, urlDot[0].split("/")[1])){
			sendView(request, response,urlDot[1],"LOGIN_000","对不起，您没有此操作权限!!!");
			return false;
		}
			
		// 不是无需安全检查
		if (!this.noCheck(url)) {
			//如果用户没有登录
			//WebSystemContextUtil.getUserSession().getUserType()
			if(!SystemContextUtil.getSystemContext().getUserSession().isLogin()){
				sendView(request, response,urlDot[1],"LOGIN_001","您未登录系统或登录已过期<br/>请重新登录!!!");
				return false;
			}
			//用户登录类型判断及他的用户类型有没有调用此服务的权限
			String usrType = SystemContextUtil.getSystemContext().getUserSession().getUserType();
			String beanName = (urlDot[0].split("/")[urlDot[0].split("/").length - 2] + "action").toLowerCase();
			if("1".equalsIgnoreCase(usrType) ){ //个人
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_001","您没有个人用户中的这个操作权限!!!");
					return false;
				}
			}else if("2".equalsIgnoreCase(usrType) ){ //单位
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_002","您没有单位用户中的这个操作权限!!!");
					return false;
				}
			}else if("3".equalsIgnoreCase(usrType) ){ //网站
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_003","您没有网站用户中的这个操作权限!!!");
					return false;
				}
			}else if("5".equalsIgnoreCase(usrType) ){ //后台
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_005","您没有后台用户中的这个操作权限!!!");
					return false;
				}
			}else if("6".equalsIgnoreCase(usrType) ){ //客服
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_006","您没有客服用户中的这个操作权限!!!");
					return false;
				}
			}
		} 
		
		return true;
	}

	public String getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	public  List<String> getNoCheckWebRes() {
		return this.noCheckWebRes;
	}

	public  void setNoCheckWebRes(List<String> noCheckWebRes) {
		this.noCheckWebRes = noCheckWebRes;
	}

	/**
	 * 
	 * <p>函数名称：输出流        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param request
	 * @param response
	 *
	 * @date   创建时间：2011-6-29
	 * @author 作者：林光延
	 */
	private void writeResponse(ServletRequest request,ServletResponse response,String urlDot,String code, String msg){
		HashMap<String, Object> result2 = new HashMap<String, Object>();
		result2.put("CODE", code);
		result2.put("MSG", msg);

		if ("json".equalsIgnoreCase(urlDot)
				|| "String".equalsIgnoreCase(urlDot)) {
			response.setContentType("text/html; charset=gbk");
			try {

				response.getWriter().print(
						DataAdaptiveUtil.getDataAdaptive("json")
								.toString(result2).getBytes("GBK"));
			} catch (IOException e) {
				log.debug("写入Response的时候出错: ", e.getMessage());
				e.printStackTrace();
			}
		} else if ("xml".equalsIgnoreCase(urlDot)) {
			response.setContentType("text/html; charset=gbk");
			try {

				response.getWriter().print(
						DataAdaptiveUtil.getDataAdaptive("xml")
								.toString(result2).getBytes("GBK"));
			} catch (IOException e) {
				log.debug("写入Response的时候出错: ", e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * <p>函数名称：     检测是否为不用授权服务    </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param url
	 * @return
	 *
	 * @date   创建时间：2011-6-29
	 * @author 作者：林光延
	 */
	private boolean noCheck(String url){
		for(int i=0 ;i< noCheckWebRes.size();i++){
			String noCheckUrl = noCheckWebRes.get(i);
			if(noCheckUrl.equals("/*")){
				return true;
			}else if(noCheckUrl.equals(url)){
				return true;
			}else{
				if(noCheckUrl.substring(noCheckUrl.length() - 1, noCheckUrl.length()).equals('*')){
					String tmp = noCheckUrl.substring(0, noCheckUrl.length() - 2);
					if(url.indexOf(tmp)>0)
						return true;
				}else if(url.indexOf(noCheckUrl)>0){
					return true;
				}
			}
			
		}
		return false;
	}
	
	/**
	 * 
	 * <p>函数名称：    跳转到出错页    </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param request
	 * @param response
	 * @param pageName
	 *
	 * @date   创建时间：2011-6-29
	 * @author 作者：林光延
	 */
	private void sendToDefault(ServletRequest request, ServletResponse response,String pageName,String msg){
		String path = ((HttpServletRequest) request).getContextPath();
		if(pageName.substring(0,1).equals("/"))
			pageName = path+pageName;
		else
			pageName = path+"/"+pageName;
		try{
			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse res = (HttpServletResponse)response;
			req.setAttribute("msg", msg);
			RequestDispatcher rd = ((HttpServletRequest)request).getRequestDispatcher("/login_error.jsp");
			rd.forward(req, res);

//			((HttpServletResponse)response).sendRedirect(pageName+"?msg="+URLEncoder.encode(msg,"GBK"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * <p>函数名称：noVisit        </p>
	 * <p>功能说明：是否有不可访问的服务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param url 访问的服务地址
	 * @param itemName 项目名称
	 * @return true 服务不可访问，false 服务可以访问
	 *
	 * @date   创建时间：Apr 16, 2012
	 * @author 作者：余威威
	 */
	private boolean noVisit(String url, String itemName){
		for (int i = 0; i < noVisitServiceList.size(); i++) {
			String noVisitUrl = noVisitServiceList.get(i);
			if(noVisitUrl.equals("/*")){
				return true;
			}else if(url.equals("/" + itemName + noVisitUrl)){
				return true;
			}
		}
		return false;
	}
}
