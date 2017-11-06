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
            <div class="payment">支付剩余时间</div>
       <!--      <div class="time">
                <span id="t_m">00分</span>
                <span id="t_s">00秒</span>
            </div> -->
             <p class="time">
        	 	<span id="t_m"></span><span id="t_s"></span>
       		 </p>
        </div>
    </div>
  <!--  <div class="weui_cells weui_cells_access" style="margin-top: 0;">
        <a class="weui_cell" href="#">
            <div class="weui_cell_bd weui_cell_primary ">
                <p>合计：</p>
            </div>
            <div class="price_ft cart-total-txt"><i>￥</i><em class="num font-16" >172</em></div>
        </a>
    </div>
    <div class="weui-cells weui-cell_access" style="margin-top: 5px;">
        <a class="weui-cell" href="javascript:;" style="color: #000;">
            <div class="weui-cell__bd weui-cell_primary">
                <p>支付方式</p>
            </div>
            <div class="weui-cell__ft" id="zhifu">微信支付</div>
        </a>
    </div>-->
    <p class="zhifu1" style="margin-top: 2px;">合计<span>￥${totol }</span></p>
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
    <a href="javascript:void(0)" onclick="pay('${totol}')" class="querenZ">确认支付<span>￥${totol }</span></a>
</div>
<!--底部-->
<!--<div class="weui-btn-area">
    <input class="weui-btn weui-btn_warn" type="button" value="确定支付">
</div>-->
<script src="static/js/wmpc/layer/mobile/layer.js"></script>
<script type="text/javascript">
		
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
		function pay(totol){
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
			//判断是否是移动设备打开
			if (browser.versions.mobile) {//移动设备中打开
				  var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
				  //在微信中打开
				  //alert("微信浏览器中不支持支付宝支付");引导客户用手机浏览器支付
			     if (ua.match(/MicroMessenger/i) == "micromessenger"){
			     		//选择微信支付
			     		if(zf_type=="1"){
			     			
			     		}else{
				     		//支付宝支付
				    		var zf_type = $("#zffs").val();
							//window.location.href="api/alipay/pay.do";
						}
			     
			     }else{
			     //alert("非微信浏览器打开")
			     		//选择微信支付
			     		if(zf_type=="1"){
			     			
			     		}else{
				     		//选择支付宝支付
					    	var zf_type = $("#zffs").val();
							window.location.href="api/alipay/alipay.do?totol="+totol+"&zf_type="+zf_type;
						}
				  } 
			
			//pc 打开
			}else{
				alert("pc上打开");
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
   // setInterval(GetRTime,0);
</script>

<script type="text/javascript">
      var intDiff = parseInt(900);//倒计时总秒数量(900秒=15分钟)
      function timer(intDiff){
          window.setInterval(function(){
             		 var day=0,
                      hour=0,
                      minute=0,
                      second=0;//时间默认值
              if(intDiff > 0){
                  minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                  second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
              }else if(intDiff == 0){
                 //var id =$("#tid").val();
              	 //location.href="api/h5KeHu/cancelTongchenghujiaoDelete.do?information_tongcheng_id="+id;
              	//关闭定时器
              	 clearInterval(timer);
              }
              if (minute <= 9) minute = '0' + minute;
              if (second <= 9) second = '0' + second;
              $('#day_show').html(day+"天");
              $('#hour_show').html('<s id="h"></s>'+hour+'时');
              $('#t_m').html('<s></s>'+minute+':');
              $('#t_s').html('<s></s>'+second+'');
              intDiff--;
              
          }, 1000);
      }
      $(function(){
          timer(intDiff);
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