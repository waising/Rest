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
    welcome��������һЩʾ���� 
    <fieldset>
    	<legend>ȡjsonֵʾ��</legend>
    	<p>
    	<a href="javascript:void(0)" onclick="get()">[ȡjson]</a>
    	</p>
    	<p>
    		<b>��ȡ�����</b><span id="test"></span>
    	</p> 
    </fieldset>
    <br/>
    <fieldset>
    	<legend>ҳ����תʾ��</legend>
    	<p>
    	<a href="bs/buzz/demo/turn.do">[��ת]</a>
    	</p>
    </fieldset>
    </div>
    <script>
    	function get(){
	    	$.getJSON("bs/buzz/demo/getJson.json?rnd="+Math.random(),function(data){
				if(data){
					$("#test").html("<br/><b>������</b>"+data.name+"<br/><b>�Ա�</b>"+data.sex);
			}
	        });
    	}
    	
    </script>
  </body>
</html>
