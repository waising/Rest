/**
* easyui公用脚本
*/
var lastOper = "";//上一次操作 add,del,update,qry,addOrUpdate (增、删除、改、查,添加或修改)
/**
 *  删除记录
 *  参数说明：
 * gridId 网格ID
 * idsId 存放被删除记录ID的隐藏域的ID
 * formId 表单ID（删除的表单）
 * idName 记录中ID字段名，多个ID以逗号隔开
 * hint 删除时提示信息
 */
function del(gridId,idsId,formId,idName,hint,sucHandle,failHandle){
	if(!hint) hint = "确定删除这些记录?";
	$.messager.confirm('提示', hint, function(r){
		if (r){
			lastOper ="del";
			var ids=getSelections(gridId,idName);
			$("#"+idsId).val(ids);
			if(ids.length<1){
				$.messager.alert('错误','请选择要删除的记录。','error');
			}else{
				loading();
				$('#'+formId).form('submit',{
	                success:function(data){
	            		var obj = (new Function( "return " + data))();
	            		var info="";
	                	if(obj.msg) info="<h5>"+obj.msg+"</h5>";
	                	if(obj.error){
	                		info+="<p style='color:red;border:solid 1px #ccc'>"+obj.error+"</p>";
	                	}
	                	if(obj.opERROR){
	                		info+="<p style='color:red;border:solid 1px #ccc'>"+obj.opERROR+"</p>";
	                	}
	                	if(obj.opERROR_Message){
	                		var id = "id_"+parseInt(Math.random()*100);
	                		info+="<br/><a href='javascript:' onclick='$(\"#"+id+"\").slideToggle(\"fast\")'>[详细信息]</a><p id='"+id+"' style='color:red;border:solid 1px #red;display:none;'>"+obj.opERROR_Message+"</p>";
	                	}
	                	
	                	if(obj.tag){//成功时
	                		$.messager.show({
	            				title:'消息',
	            				msg:info,
	            				timeout:5000,
	            				showType:'slide',
	            				width:350,
	            				height:180
	            			});
	                		if(sucHandle) sucHandle(); 
	                		$("#"+gridId).datagrid('reload');
	                	}else{
	                		parent.$.messager.alert('消息',info,'info',function(){
		                	});
		                	$("a[class='l-btn']").focus();
		                	if(failHandle) failHandle();
	                	}
	                	removeLoading();
	                },error:function(){
	                	removeLoading();
	    			}
	            });
			}
		}
	});
}
/**
 * 从网格中取选中行
 * @param gridId 网格ID
 * @param keys 网格字段名，多个以逗号隔开
 * @return
 */
function getSelections(gridId,keys){
	var ids = [];
	if(!keys) keys = "id";
	var akey=keys.split(",");
	var rows = $('#'+gridId).datagrid('getSelections');
	for(var i=0;i<rows.length;i++){
		var sp="",tmp="",row=rows[i];
		for(var j=0;j<akey.length;j++){
			tmp=tmp+sp+row[akey[j]];
			sp=",";
		}
		ids.push(tmp);
	}
	return ids.join(':');
}
/**
 * 打开添加或修改对话框
 * @param title 对话框标题
 * @param dialogId 对话框ID
 * @param gridId 网格ID
 * @param submitFrmId 添加或删除的表单ID
 * @param params 对话框参数json对象,json说明：width为宽、height为高、modal为是否为模式对话框，如{width:200,height:250}
 * @param sucHandle 服务端返回成功后处理
 * @param failHandle 服务端返回失败后处理
 * @param beforeHandle 表单提交前处理
 * @return
 */
function openDialog(title,dialogId,gridId,submitFrmId,params,sucHandle,failHandle,beforeHandle){
	lastOper ="addOrUpdate";
	if(!params){
		params={};
		params.width=400;
		params.height=250;
		params.modal=true;
	}else{
		if(!params.width) params.width=400;
		if(!params.height) params.height=250;
		if(!params.modal) params.modal=true;
	}
	
	if(!title) title="对话框";
	if(!dialogId) dialogId="dlg";	
	if(!gridId) gridId="grid";
	if(!submitFrmId) submitFrmId="frm";
	
	if(params.clearForm==true){
		$('#'+submitFrmId).form('clear');
	}
	
	$('#'+dialogId).show();
	$('#'+dialogId+" .easyui-combotree").combotree();
	$('#'+dialogId).dialog({
	    title:title,
	    width:params.width,
	    height:params.height,
	    modal:params.modal,
	    buttons:[{
	    	id:"easyuiSave",
	    	text:'确定',
	        iconCls:'icon-ok',
	        handler:function(){
	    		$('#easyuiSave').linkbutton("disable");
		    	$('#'+submitFrmId).form('submit',{
	            	onSubmit:function(){  
		    			if(beforeHandle) beforeHandle();
		    			$('#easyuiSave').linkbutton("enable");
	            	    return $("#"+submitFrmId).form('validate');  
	            	},
	                success:function(data){
	            		var obj = (new Function( "return " + data))();
	            		var info="";
	                	if(obj.msg) info="<h5>"+obj.msg+"</h5>";
	                	if(obj.error){
	                		info+="<p style='color:red;border:solid 1px #ccc'>"+obj.error+"</p>";
	                	}
	                	if(obj.opERROR){
	                		info+="<p style='color:red;border:solid 1px #ccc'>"+obj.opERROR+"</p>";
	                	}
	                	if(obj.opERROR_Message){
	                		var id = "id_"+parseInt(Math.random()*100);
	                		info+="<br/><a href='javascript:' onclick='$(\"#"+id+"\").slideToggle(\"fast\")'>[详细信息]</a><p id='"+id+"' style='color:red;border:solid 1px #red;display:none;'>"+obj.opERROR_Message+"</p>";
	                	}
	                	if(obj.tag){//成功时
	                		window.parent.$.messager.show({
	            				title:'消息',
	            				msg:info,
	            				timeout:5000,
	            				showType:'slide',
	            				width:350,
	            				height:180
	            			});
	                		$('#'+dialogId).dialog('close');
	                		$("#"+gridId).datagrid('reload');
	                		$('#'+dialogId).form('clear');
	                		if(sucHandle) sucHandle(obj);
	                	}else{
	                		$.messager.alert('消息',info,'info');
		                    $("a[class='l-btn']").focus();
		                    if(failHandle) failHandle();
	                	}
	                	$('#easyuiSave').linkbutton("enable");
	                },error:function(){
	    				$('#easyuiSave').linkbutton("enable");
	    			}
	            });
	    	}
	    },{
	        text:'取消',
	        iconCls:'icon-cancel',
	        handler:function(){
	            $('#'+dialogId).dialog('close');
	        }
	    }]
	});
}
/**
 * 网格查询
 * @param handler 查询处理方法
 * @param dlgParams 对话框参数，json对象
 * @param dialogId 对话框ID
 * @param formId 查询表单的ID
 * @return
 */
function query(handler,dlgParams,dialogId,formId){
	if(!dlgParams){
		dlgParams={};
		dlgParams.width=400;
		dlgParams.height=230;
		dlgParams.modal=true;
	}
	if(!dialogId) dialogId="dlg";
	if(!formId) formId="frm";
	if(!dlgParams.width) dlgParams.width=400;
	if(!dlgParams.height) dlgParams.height=230;
	if(!dlgParams.modal) dlgParams.modal=true;
	var fid="",tag="",forms=formId.split(",");
	for(var i=0;i<forms.length;i++){
		fid=fid+tag+"#"+forms[i];
		tag=",";
	}
	
	if(lastOper != "qry") $(fid).form('clear');//上一次不是查询时，清除表单信息
	lastOper ="qry";
	
	$('#'+dialogId).show();
	$('#'+dialogId+" .easyui-combotree").combotree();
	$('#'+dialogId).dialog({
	    title:'查询',
	    width:dlgParams.width,
	    height:dlgParams.height,
	    modal:dlgParams.modal,
	    buttons:[{
	        text:'确定',
	        iconCls:'icon-ok',
	        handler:handler
	    },{
	    	 text:'清除',
		        iconCls:'icon-remove',
		        handler:function(){
	    			$(fid).form('clear');
		        }	
	    },{
	        text:'取消',
	        iconCls:'icon-cancel',
	        handler:function(){
	            $('#'+dialogId).dialog('close');
	        }
	    }]
	});
}
/**
 * 显示进度条
 * @return
 */
function loading(){
	removeLoading();
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 100) / 2});	
}
/**
 * 隐藏进度条
 * @return
 */
function removeLoading(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}