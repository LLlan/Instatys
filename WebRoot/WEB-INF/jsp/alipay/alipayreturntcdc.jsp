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
		.connectBox{
			margin-top:70px;
			background-color:#fff;
		}
    </style>
</head>
<body>
<!-- <div id="header" style="background-color: #068dff;">
    <div class="wy-header-title">付款成功</div>
</div> -->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">付款成功</h1>
    <a href="api/h5KeHu/index.do" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--主体开始-->
<div class="connectBox">
	<br>
	<div style="width:70%;margin:0 auto;text-align: center;">
		<div >
    	<img src="static/images/wmpc/lvseduihao.png" style="width: 40px;vertical-align: middle;">
    	<span style="margin-left: 10px;font-size:18px;">付款成功</span>
    </div><br><br>
    <div>
    	<a href="api/h5KeHu/tongchengcome.do?order_tongcheng_id=${order_tongcheng_id}" style="color: #38c4ff;">订单详情</a>
    	<a href="api/h5KeHu/index.do" style="margin-left: 25%;color: #38c4ff;">返回主页</a>
    </div>
	</div>
   
    <input type="hidden" value="${order_xxx_id }" name="order_xxx_id">
    <input type="hidden" value="${type }" name="type">
    <br>
</div>
<script src="static/js/wmpc/layer/mobile/layer.js"></script>
<script type="text/javascript">
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
			var zf_type = $("#zffs").val();
			window.location.href="api/alipay/alipay.do?totol="+totol+"&zf_type="+zf_type;
			
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