function chkpwd(obj,targetId){
	var t=obj.value;
	var id=getResult(t);
	
	//�����Ӧ����Ϣ��ʾ
	var msg=["�������","����ǿ�Ȳ�","����ǿ������","����ǿ�ȸ�"];
	var sty=[-45,-30,-15,0];
	var col=["gray","red","#ff6600","Green"];
	//������ʾЧ��
	var bImg="images/pub/pwdChk.gif";//һ����ʾ�õ�ͼƬ
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
	Bobj.html("�����ʾ��" + msg[id]);
	Bobj.show();
}

//�����⺯��,����0/1/2/3�ֱ������Ч/��/һ��/ǿ
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