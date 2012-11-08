<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- ERR:<s:property value="%{exception.message}"/> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<base href="<%=basePath%>"/>
<title>您的访问出错了</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk"></meta>

<style type="text/css">

</style>
</head>
<body>
<div class="msg">
	<div class="title"><b>您的访问出错了！！！</b></div>
	<h4 class="tip">很抱歉，您要访问的页面不存在。</h4>
</div>
  
</body>
</html>