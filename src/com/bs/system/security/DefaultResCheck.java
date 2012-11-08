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
 * Title:  Ȩ����֤��
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

public class DefaultResCheck {

	private static Logger log = LoggerFactory.getLogger(DefaultResCheck .class);
	
    /**
	 * �����Web��Դ
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
	 * ���ܷ��ʵķ���
	 */
	public List<String> noVisitServiceList;

	public void setNoVisitServiceList(List<String> noVisitServiceList) {
		this.noVisitServiceList = noVisitServiceList;
	}
	
	/**
	 * �Ƿ���תҳȫ·��
	 */
	public String defaultPage = null;
	/**
	 * 
	 * <p>�������ƣ�����ҳ��        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param request
	 * @param response
	 * @param dot
	 * @param code
	 * @param msg
	 *
	 * @date   ����ʱ�䣺2011-7-13
	 * @author ���ߣ��ֹ���
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
	 * <p>�������ƣ�  ��¼�Ϸ���У��      </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param request
	 * @param response
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-7-13
	 * @author ���ߣ��ֹ���
	 */
	public boolean doCheck(ServletRequest request, ServletResponse response) {
		
		
		 String url = ((HttpServletRequest)request).getRequestURI();
		 log.debug("���õ�ַ URL: " + url);
		 String url2 = url.replace(".", "#");
		 String[] urlDot =(url2.trim()+"#").split("#");
			
		// ���urlΪ���ɷ��ʵķ�������������ҳ��
		if(this.noVisit(url, urlDot[0].split("/")[1])){
			sendView(request, response,urlDot[1],"LOGIN_000","�Բ�����û�д˲���Ȩ��!!!");
			return false;
		}
			
		// �������谲ȫ���
		if (!this.noCheck(url)) {
			//����û�û�е�¼
			//WebSystemContextUtil.getUserSession().getUserType()
			if(!SystemContextUtil.getSystemContext().getUserSession().isLogin()){
				sendView(request, response,urlDot[1],"LOGIN_001","��δ��¼ϵͳ���¼�ѹ���<br/>�����µ�¼!!!");
				return false;
			}
			//�û���¼�����жϼ������û�������û�е��ô˷����Ȩ��
			String usrType = SystemContextUtil.getSystemContext().getUserSession().getUserType();
			String beanName = (urlDot[0].split("/")[urlDot[0].split("/").length - 2] + "action").toLowerCase();
			if("1".equalsIgnoreCase(usrType) ){ //����
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_001","��û�и����û��е��������Ȩ��!!!");
					return false;
				}
			}else if("2".equalsIgnoreCase(usrType) ){ //��λ
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_002","��û�е�λ�û��е��������Ȩ��!!!");
					return false;
				}
			}else if("3".equalsIgnoreCase(usrType) ){ //��վ
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_003","��û����վ�û��е��������Ȩ��!!!");
					return false;
				}
			}else if("5".equalsIgnoreCase(usrType) ){ //��̨
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_005","��û�к�̨�û��е��������Ȩ��!!!");
					return false;
				}
			}else if("6".equalsIgnoreCase(usrType) ){ //�ͷ�
				if(!sysServiceList.get(usrType).contains(beanName)){
					sendView(request, response,urlDot[1],"LOGIN_USER_006","��û�пͷ��û��е��������Ȩ��!!!");
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
	 * <p>�������ƣ������        </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param request
	 * @param response
	 *
	 * @date   ����ʱ�䣺2011-6-29
	 * @author ���ߣ��ֹ���
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
				log.debug("д��Response��ʱ�����: ", e.getMessage());
				e.printStackTrace();
			}
		} else if ("xml".equalsIgnoreCase(urlDot)) {
			response.setContentType("text/html; charset=gbk");
			try {

				response.getWriter().print(
						DataAdaptiveUtil.getDataAdaptive("xml")
								.toString(result2).getBytes("GBK"));
			} catch (IOException e) {
				log.debug("д��Response��ʱ�����: ", e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * <p>�������ƣ�     ����Ƿ�Ϊ������Ȩ����    </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param url
	 * @return
	 *
	 * @date   ����ʱ�䣺2011-6-29
	 * @author ���ߣ��ֹ���
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
	 * <p>�������ƣ�    ��ת������ҳ    </p>
	 * <p>����˵����
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param request
	 * @param response
	 * @param pageName
	 *
	 * @date   ����ʱ�䣺2011-6-29
	 * @author ���ߣ��ֹ���
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
	 * <p>�������ƣ�noVisit        </p>
	 * <p>����˵�����Ƿ��в��ɷ��ʵķ���
	 *
	 * </p>
	 *<p>����˵����</p>
	 * @param url ���ʵķ����ַ
	 * @param itemName ��Ŀ����
	 * @return true ���񲻿ɷ��ʣ�false ������Է���
	 *
	 * @date   ����ʱ�䣺Apr 16, 2012
	 * @author ���ߣ�������
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
