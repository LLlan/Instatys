<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui1.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery.nav.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <title>在线支付</title>
    <style>
        body{
            background-color: #F0F0F0;
        }
        .icon1 {
		    top: 13;
		}
		.querenZ {
		    background-color: #42da62;
		    font-size: 16px;
		}
		.weui-panel__hd .time {
		    font-size: 26px;
		    margin-top: 10px;
		}
		.weui-panel__hd {
		    height: 75px;
		}
		.title {
		    margin-left: -30px;
		}
    </style>
</head>
<body>
<div id="header" style="background-color: #068dff;">
    <h1 class="title">确认订单</h1>
    <a href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--主体-->
<div class="weui-content" style="padding-top: 60px;">
    <div class="weui-panel weui-panel_access">
        <div class="weui-panel__hd">
            <div class="payment"></div>
             <p class="time">
             <c:if test="${pds.order_tongcheng_status=='4' }"><span id="t_m">￥${pds.about_Amount }</span></c:if>
        	 <c:if test="${pds.order_tongcheng_status=='2' }"><span id="t_m">￥${pds.radeAmount }</span></c:if>
       		 </p>
        </div>
    </div>
    <%--<p class="zhifu1" style="margin-top: 2px;">合计<span>￥${radeAmount }</span></p>--%>
    <p class="fangshi">选择支付方式</p>
    <div class="weixinZ" style="height: 60px;margin-bottom: 10px;">
        <p style="top: 7px;">
            <img src="static/images/wmpc/weixin_03.png" alt=""/>
            <span style="top: 11px;font-size: 16px;">微信支付</span>
            <a class=" weui-cells_checkbox" style="top: 0;">
                <label class="weui-cell weui-check__label check1" for="s11">
                    <input type="checkbox" class="weui-check" name="checkbox1" id="s11">
                    <i class="weui-icon-checked"></i>
                </label>
            </a>
        </p>
    </div>
    <div class="weixinZ" style="height: 60px;">
        <p style="top: 7px;">
            <img src="static/images/wmpc/zfb.png" alt=""/>
            <span style="top: 11px;font-size: 16px;">支付宝支付</span>
            <a class=" weui-cells_checkbox" style="top: 0;">
                <label class="weui-cell weui-check__label check2" for="s12">
                    <input type="checkbox" class="weui-check" name="checkbox2" id="s12">
                    <i class="weui-icon-checked"></i>
                </label>
            </a>
        </p>
    </div>
    <input type="hidden" id="zffs" value="0">
    <a href="javascript:void(0)" onclick="pay('${order_tongcheng_id}','${pds.radeAmount }')" class="querenZ">确认支付<span>￥${pds.radeAmount }</span></a>
</div>
<!--底部-->
<!--<div class="weui-btn-area">
    <input class="weui-btn weui-btn_warn" type="button" value="确定支付">
</div>-->
<script src="static/js/wmpc/layer/mobile/layer.js"></script>
<script type="text/javascript">
	//定义浏览器的类型
	var browser = {
		    versions: function () {
		        var u = navigator.userAgent, app = navigator.appVersion;
		        return {         //移动终端浏览器版本信息
		            trident: u.indexOf('Trident') > -1, //IE内核
		            presto: u.indexOf('Presto') > -1, //opera内核
		            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
		            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
		            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
		            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
		            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
		            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
		            iPad: u.indexOf('iPad') > -1, //是否iPad
		            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
		        };
		    }(),
		    language: (navigator.browserLanguage || navigator.language).toLowerCase()
	};
	//获取订单编号
	function getOrderNumber(){
		var vNow = new Date();
		var sNow = "";
		
		//获取年月日时分秒毫秒数
		sNow += String(vNow.getFullYear());
		(vNow.getMonth() + 1)<10?sNow += "0"+String(vNow.getMonth() + 1):sNow += String(vNow.getMonth() + 1);
		(vNow.getDate())<10?sNow += "0"+String(vNow.getDate()):sNow += String(vNow.getDate());
		(vNow.getHours())<10?sNow += "0"+String(vNow.getHours()):sNow += String(vNow.getHours());
		(vNow.getMinutes())<10?sNow += "0"+String(vNow.getMinutes()):sNow += String(vNow.getMinutes());
		(vNow.getSeconds())<10?sNow += "0"+String(vNow.getSeconds()):sNow += String(vNow.getSeconds());
		(vNow.getMilliseconds())<10?sNow += "00"+String(vNow.getMilliseconds()):(vNow.getMilliseconds())<100?sNow += "0"+String(vNow.getMilliseconds()):sNow += String(vNow.getMilliseconds());
	
		//获取随机六位数
		for(var i=0;i<6;i++)
		{
		    sNow += Math.floor(Math.random()*10);
		}
		
		return sNow;
	}
	//去支付
	function pay(id,total_fee){
		//id="8b1603427243461fae69ceb35b7bfe1b";//测试的时候用的
		if($("#zffs").val()=="0"){
			  //提示
			  layer.open({
			    content: '请选择支付方式！'
			    ,skin: 'msg'
			    ,time: 2 //2秒后自动关闭
			  });
			  return;
		}
		if($("#zffs").val()=="1"){
			  layer.open({
			    content: '微信支付方式暂未开放！'
			    ,skin: 'msg'
			    ,time: 2 //2秒后自动关闭
			  });
			  return;
		}
		var zf_type = $("#zffs").val();
		if (browser.versions.mobile) {//移动设备中打开
			var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
			if (ua.match(/MicroMessenger/i) == "micromessenger") {//在微信中打开
				window.location.href="api/alipay_tcdc/pay.do?order_tongcheng_id="+id+"&orderNumber="+getOrderNumber(); 	
			}else{//非微信内打开
				window.location.href="api/alipay_tcdc/alipay.do?order_tongcheng_id="+id+"&orderNumber="+getOrderNumber()+"&total_fee="+total_fee;
			}
		} else {//PC浏览器打开
				    
		}
	}
	$("#s11").click(function(){
		$("#zffs").val("1");  
		 //提示
			  layer.open({
			    content: '微信支付方式暂未开放！'
			    ,skin: 'msg'
			    ,time: 2 //2秒后自动关闭
			  });
			  //赋值
			
	});
	$("#s12").click(function(){
		$("#zffs").val("2");
	});
</script>

<script>
    $(function () {
        FastClick.attach(document.body);
    });
    
    $("#zhifu").select({
        title: "选择支付方式",
        items: [
            { title: "微信支付", value: "1"},
            {title: "支付宝",value: "2"},
        ],
        onChange:function(arg){
            console.log(arg);
           /* $('#pay_method').val( arg['values'] );*/
            $("#zhifu").text( arg['titles'] );
        }
    });
</script>
<script>
    $(function(){
        $(".check1 input").click(function(){
            $(".check1 input").attr("checked",true);
            $(".check2 input").attr("checked",false);
        })
        $(".check2 input").click(function(){
            $(".check2 input").attr("checked",true);
            $(".check1 input").attr("checked",false);
        })
    })
</script>
</body>
</html>