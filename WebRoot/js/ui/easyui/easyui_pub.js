/**
* easyui���ýű�
*/
var lastOper = "";//��һ�β��� add,del,update,qry,addOrUpdate (����ɾ�����ġ���,��ӻ��޸�)
/**
 *  ɾ����¼
 *  ����˵����
 * gridId ����ID
 * idsId ��ű�ɾ����¼ID���������ID
 * formId ��ID��ɾ���ı���
 * idName ��¼��ID�ֶ��������ID�Զ��Ÿ���
 * hint ɾ��ʱ��ʾ��Ϣ
 */
function del(gridId,idsId,formId,idName,hint,sucHandle,failHandle){
	if(!hint) hint = "ȷ��ɾ����Щ��¼?";
	$.messager.confirm('��ʾ', hint, function(r){
		if (r){
			lastOper ="del";
			var ids=getSelections(gridId,idName);
			$("#"+idsId).val(ids);
			if(ids.length<1){
				$.messager.alert('����','��ѡ��Ҫɾ���ļ�¼��','error');
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
	                		info+="<br/><a href='javascript:' onclick='$(\"#"+id+"\").slideToggle(\"fast\")'>[��ϸ��Ϣ]</a><p id='"+id+"' style='color:red;border:solid 1px #red;display:none;'>"+obj.opERROR_Message+"</p>";
	                	}
	                	
	                	if(obj.tag){//�ɹ�ʱ
	                		$.messager.show({
	            				title:'��Ϣ',
	            				msg:info,
	            				timeout:5000,
	            				showType:'slide',
	            				width:350,
	            				height:180
	            			});
	                		if(sucHandle) sucHandle(); 
	                		$("#"+gridId).datagrid('reload');
	                	}else{
	                		parent.$.messager.alert('��Ϣ',info,'info',function(){
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
 * ��������ȡѡ����
 * @param gridId ����ID
 * @param keys �����ֶ���������Զ��Ÿ���
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
 * ����ӻ��޸ĶԻ���
 * @param title �Ի������
 * @param dialogId �Ի���ID
 * @param gridId ����ID
 * @param submitFrmId ��ӻ�ɾ���ı�ID
 * @param params �Ի������json����,json˵����widthΪ��heightΪ�ߡ�modalΪ�Ƿ�Ϊģʽ�Ի�����{width:200,height:250}
 * @param sucHandle ����˷��سɹ�����
 * @param failHandle ����˷���ʧ�ܺ���
 * @param beforeHandle ���ύǰ����
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
	
	if(!title) title="�Ի���";
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
	    	text:'ȷ��',
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
	                		info+="<br/><a href='javascript:' onclick='$(\"#"+id+"\").slideToggle(\"fast\")'>[��ϸ��Ϣ]</a><p id='"+id+"' style='color:red;border:solid 1px #red;display:none;'>"+obj.opERROR_Message+"</p>";
	                	}
	                	if(obj.tag){//�ɹ�ʱ
	                		window.parent.$.messager.show({
	            				title:'��Ϣ',
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
	                		$.messager.alert('��Ϣ',info,'info');
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
	        text:'ȡ��',
	        iconCls:'icon-cancel',
	        handler:function(){
	            $('#'+dialogId).dialog('close');
	        }
	    }]
	});
}
/**
 * �����ѯ
 * @param handler ��ѯ������
 * @param dlgParams �Ի��������json����
 * @param dialogId �Ի���ID
 * @param formId ��ѯ����ID
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
	
	if(lastOper != "qry") $(fid).form('clear');//��һ�β��ǲ�ѯʱ���������Ϣ
	lastOper ="qry";
	
	$('#'+dialogId).show();
	$('#'+dialogId+" .easyui-combotree").combotree();
	$('#'+dialogId).dialog({
	    title:'��ѯ',
	    width:dlgParams.width,
	    height:dlgParams.height,
	    modal:dlgParams.modal,
	    buttons:[{
	        text:'ȷ��',
	        iconCls:'icon-ok',
	        handler:handler
	    },{
	    	 text:'���',
		        iconCls:'icon-remove',
		        handler:function(){
	    			$(fid).form('clear');
		        }	
	    },{
	        text:'ȡ��',
	        iconCls:'icon-cancel',
	        handler:function(){
	            $('#'+dialogId).dialog('close');
	        }
	    }]
	});
}
/**
 * ��ʾ������
 * @return
 */
function loading(){
	removeLoading();
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("���ڴ������Ժ򡣡���").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 100) / 2});	
}
/**
 * ���ؽ�����
 * @return
 */
function removeLoading(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}