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
<title>E缴通--公共缴费网--您的访问出错了</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk"></meta>

<style type="text/css">
body{
	margin:0;
	padding:0;
	background: url("images/pub/prompt_bg.gif") repeat-x top;
	font-family: '雅黑';
}
.msg{
	width:647px;
	height:289px;
	margin:0 auto;
}
.msgbk{
	background: url("images/pub/sys_error.gif") no-repeat;
	height: 289px;
}
.msgDialog{
	padding:120px 0px 0 270px;
	font-weight: bold;
}
h1{
	font-size: 18px;
}
p{ 
	font-size:16px;
	text-indent:30px;
}
.top{
border-bottom: solid 1px #ccc;
padding-top:50px; 
}
.top .left{
float:left;
}
.top .left .img{ 
margin-right:20px;
}
.top .right{
float:right;padding-top:20px;
}
a{
text-decoration: none;
}
.foot{
	margin-top:50px;
	text-align: center;
	font-size: 12px;
}
.foot span{
	font-weight:bold;
}
#clear {
	content: ".";
	clear: both;
	border: none;
	font: 1;
	height: 0;
	widht: 0;
	display: block;
	overflow: hidden;
	margin: 0;
	padding: 0;
	visibility: hidden;
}
.split{
	height:30px;
}
</style>
</head>
<body>
<div class="msg">
	<div class="top">
		<div class="left"><img src="images/pub/logo.gif" align="absmiddle"/>&nbsp;</div>
		<div class="right"><a href="<%=basePath%>" target="top">[首页]</a></div>
		<div id="clear"></div>
	</div>
	<div class="msgbk">
	<div class="msgDialog">
		<div class="title"><b>您的访问出错了！！！</b></div>
		<h4 class="tip">很抱歉，您要访问的页面不存在500。</h4>
	</div>
	</div>
	<div class="foot">
		<address>客服电话：<span>400-8877-945</span>、
		<span>0591-87814055</span>、
		<span>0591-87664012</span>、
		<span>0591-87664011</span>&nbsp;&nbsp;
		<a href="help/payPhones.html" target="_blank">更多客服电话...</a>&nbsp;&nbsp;服务时间：<span>8:00-22:00</span></address>	
		<address>http://www.ggjfw.com/  @2011 闽ICP备11009526号 </address>
	</div>
</div>
  
</body>
</html>