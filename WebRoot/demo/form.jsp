<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@include file="/pub/jspvar.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<base href="${basePath}" />
	<title>${bs:getConfig('appName')}-ҳ����ת����</title>
	${bs:getConfig("description_keywords")}
	${bs:getConfig('charset')}
	<%@include file="/pub/jsppubjs.jsp" %>
	<%@include file="/pub/jsppubcss.jsp" %>
  </head>
  <body>
    <h1>��ת�����ˣ�������ֵ�ǣ�[${__resultModle.outObject}]</h1> 
  </body>
</html>
