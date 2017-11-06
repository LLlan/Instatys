<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE >
<html>
<head>
    <title>同城呼叫</title>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style type="text/css">
    	.hujiaoCir>p:nth-child(1){
    		margin-top:56px;
    	}
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">同城呼叫</h1>
  <a href="javascript:history.go(-1)" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>

<!--头部结束-->
<!--主体-->
<div class="dacheOrder" style="margin-top:60px;">
    <div class="hujiaoCir">
       <!--  <p class="time">
        	 <span id="t_m"></span><span id="t_s"></span>
        </p> -->
        <p>司机已接单正在赶来...</p>
        <p>大约${pds.about_Amount }元</p>
    </div>
    <div class="yanzheng" style="background-color: #C7C6CC;width:100%;"><a href="javascript:void(0);" onclick="quxiao();" style="width:100%;margin:10px auto;border-radius: 10px;">取消呼叫</a></div>
	<input type="hidden" id="tid" value="${information_tongcheng_id}">
</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script>
	function quxiao(){
		var id =$("#tid").val();
		var respCode ="${respCode}";
		if (respCode =="01") {
			layer.alert("您确定取消呼叫吗？司机正在赶来！",{
		            title:"温馨提示"//提示标题，默认为：信息
		            ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
		            ,anim: 0 //动画类型0-6，默认为：0
		            ,closeBtn: 1//是否显示关闭按钮，0-不显示，1-显示，默认为1
		            ,btn: ['确定','取消'] //按钮
		            ,icon: 6    // icon
		            ,yes:function(){
		                location.href="api/h5KeHu/SetorderStatus.do?information_tongcheng_id="+id;
		            }
		            ,btn2:function(){
		            }
		        })
		} else {

		}
		//var url = 'api/h5KeHu/cancelTongchenghujiao.do';
		/* var url = 'api/h5KeHu/tongchenghujiao.do';
		$.ajax({
			url:url,
			type:"post",
			data:{
    			"information_tongcheng_id":id
    		},
    		dataType:"json",
			success:function(data){
				console.log(data.respCode)
				if(respCode=="01"){
						layer.alert("您确定取消呼叫！",{
			            title:"温馨提示"//提示标题，默认为：信息
			            ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
			            ,anim: 0 //动画类型0-6，默认为：0
			            ,closeBtn: 1//是否显示关闭按钮，0-不显示，1-显示，默认为1
			            ,btn: ['确定','取消'] //按钮
			            ,icon: 6    // icon
			            ,yes:function(){
			                location.href="api/h5KeHu/cancelTongchenghujiao.do?information_tongcheng_id="+id;
			            }
			            ,btn2:function(){
			            	location.href="api/h5KeHu/tongchenghujiao.do?information_tongcheng_id="+id;
			            }
			        })
				}
			}
		}); */
	}

$(function() {
    FastClick.attach(document.body);
});
</script>
<script type="text/javascript">
      var intDiff = parseInt(180);//倒计时总秒数量(180秒=3分钟)
      function timer(intDiff){
          window.setInterval(function(){
              var day=0,
                      hour=0,
                      minute=0,
                      second=0;//时间默认值
              if(intDiff > 0){
                  minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                  second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
              }
             /*  else if(intDiff == 0){
                 var id =$("#tid").val();
              	 location.href="api/h5KeHu/cancelTongchenghujiaoDelete.do?information_tongcheng_id="+id;
              	//关闭定时器
              	 clearInterval(timer);
              } */
              if (minute <= 9) minute = '0' + minute;
              if (second <= 9) second = '0' + second;
              $('#day_show').html(day+"天");
              $('#hour_show').html('<s id="h"></s>'+hour+'时');
              $('#t_m').html('<s></s>'+minute+':');
              $('#t_s').html('<s></s>'+second+'');
          
              if(intDiff%30==0 || intDiff==20 || intDiff==10 || intDiff==1){
               	var id =$("#tid").val();
              	$.post('api/h5KeHu/tongchenghuStatus.do',{information_tongcheng_id:id},function(data){
	              	if (data.respCode =="03") {
						location.href="api/h5KeHu/tongchenghujiaos.do?information_tongcheng_id="+id;
	              	}
              	});
              	$.post('api/h5KeHu/orderStatusjiedao.do',{information_tongcheng_id:id},function(data){
	              	if (data.respCode =="04") {
						location.href="api/h5KeHu/tongchenghujiaos.do?information_tongcheng_id="+id;
						//关闭定时器
              	 		clearInterval(timer);
	              	}
              	});
              }
               intDiff--;
          }, 1000);
      }
      $(function(){
          timer(intDiff);
      }); 
</script>
</body>
</html>
