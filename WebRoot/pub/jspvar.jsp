<%@ page trimDirectiveWhitespaces="true" %><%@ page language="java" import="java.util.*" pageEncoding="GBK"%><%@taglib prefix="s" uri="/struts-tags" %><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="bs" uri="/bstag" %><%
String path = request.getContextPath();
String port=":"+request.getServerPort();
if(":80".equals(port)) port="";
String basePath = request.getScheme()+"://"+request.getServerName()+port+path+"/";
pageContext.setAttribute("basePath",basePath);%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">