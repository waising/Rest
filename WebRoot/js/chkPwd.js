function chkpwd(obj,targetId){
	var t=obj.value;
	var id=getResult(t);
	
	//定义对应的消息提示
	var msg=["密码过短","密码强度差","密码强度良好","密码强度高"];
	var sty=[-45,-30,-15,0];
	var col=["gray","red","#ff6600","Green"];
	//设置显示效果
	var bImg="images/pub/pwdChk.gif";//一张显示用的图片
	var sWidth=170;
	var sHeight=15;
	var Bobj=$("#"+targetId);
	Bobj.css("fontSize","12px");
	Bobj.css("color",col[id]);
	Bobj.width(sWidth + "px");
	Bobj.height(sHeight + "px");
	Bobj.css("lineHeight",sHeight + "px");
	Bobj.css("background","url(" + bImg + ") no-repeat left " + sty[id] + "px");
	Bobj.css("text-indent","20px");
	Bobj.html("检测提示：" + msg[id]);
	Bobj.show();
}

//定义检测函数,返回0/1/2/3分别代表无效/差/一般/强
function getResult(s){
	if(s.length < 4){
		return 0;
	}
	var ls = 0;
	if (s.match(/[a-z]/ig)){
		ls++;
	}
	if (s.match(/[0-9]/ig)){
		ls++;
	}
 	if (s.match(/[^a-z0-9]/ig)){
		ls++;
	}
	if (s.length < 6 && ls > 0){
		ls--;
	}
	return ls
}