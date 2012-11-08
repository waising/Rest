<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@include file="/pub/jspvar.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<base href="${basePath}" />
	<title>${bs:getConfig('appName')}</title>
	${bs:getConfig("description_keywords")}
	${bs:getConfig('charset')}
	<%@include file="/pub/jsppubjs.jsp" %>
	<%@include file="/pub/jsppubcss.jsp" %>
  </head>
  <body> 
  	<div style="padding:20px;"> 
    welcome，这里是一些示例！ 
    <fieldset>
    	<legend>取json值示例</legend>
    	<p>
    	<a href="javascript:void(0)" onclick="get()">[取json]</a>
    	</p>
    	<p>
    		<b>获取结果：</b><span id="test"></span>
    	</p> 
    </fieldset>
    <br/>
    <fieldset>
    	<legend>页面跳转示例</legend>
    	<p>
    	<a href="bs/buzz/demo/turn.do">[跳转]</a>
    	</p>
    </fieldset>
    </div>
    <script>
    	function get(){
	    	$.getJSON("bs/buzz/demo/getJson.json?rnd="+Math.random(),function(data){
				if(data){
					$("#test").html("<br/><b>姓名：</b>"+data.name+"<br/><b>性别：</b>"+data.sex);
			}
	        });
    	}
    	
    </script>
  </body>
</html>
