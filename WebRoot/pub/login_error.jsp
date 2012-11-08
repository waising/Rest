<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>系统提示信息</title>
<style type=text/css>
body{
	margin:0;
	padding:0;
	background: url("images/pub/prompt_bg.gif") repeat-x top;
	font-family: '雅黑';
}
.msg{
	width:662px;
	height:388px;
	margin:0 auto;
	border:none;
	background: url("images/pub/loginTimeout.gif") no-repeat;
}
.msgDialog{
	padding:130px 170px 0 229px;
}
h1{
	font-size: 18px;
}
p{
	font-size:16px;
	text-indent:30px;
}
</style>
</head>
<body>
<div class="msg">
	<div class="msgDialog">
		<h1>系统提示信息</h1>
		<p>
		${msg}
		</p>
	</div>
</div>
</html>