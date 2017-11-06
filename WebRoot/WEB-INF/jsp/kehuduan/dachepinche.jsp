<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>打车拼车</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
   <base href="<%=basePath%>">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
        .dacheOX{
            display:block;padding:10px 0;border:1px solid #d0d0d0;border-radius:10px;background-color: #fff;
        }
        .dacheOX1{
            margin-top:10px;
        }
        .dacheOX p{
            font-size: 18px;color:#000;margin-top:5px;margin-left:15px;
        }
        .dacheOX ul{
            padding:4px 0;margin-left:15px;font-size: 16px;
        }
        .layui-layer{
        	width:80%;!important;
        	left:10%;!important;
        }
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">打车拼车</h1>
  <a href="api/h5KeHu/index.do" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->

<!--主体-->
<div class="cheBox" style="margin-top:60px;margin-bottom:48px;">
    <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/tongchengCar.do'" class="dache1">
        <h2>同城打车</h2>
        <p>我们一直在您身边</p>
    </a>
    <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/changtuCar.do'"  class="pinche1">
        <h2>长途拼车</h2>
        <p>美好的旅途我们一直陪伴您的身边</p>
    </a>
</div>
<!--底部导航-->
<div class="weui-tabbar wy-foot-menu">
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/index.do'" class="weui-tabbar__item ">
    <div class="weui-tabbar__icon foot-menu-home"></div>
    <p class="weui-tabbar__label">首页</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/hdsq.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon foot-menu-cart"></div>
    <p class="weui-tabbar__label">互动社区</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/dcpc.do'" class="weui-tabbar__item weui-bar__item--on">
    <div class="weui-tabbar__icon  foot-menu-member"></div>
    <p class="weui-tabbar__label">打车拼车</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/wd.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon foot-menu-car"></div>
    <p class="weui-tabbar__label">我的</p>
  </a>
  <input type="hidden" id="tid" value="${information_tongcheng_id}">
  <input type="hidden" id="order_tongcheng_id" value="${order_tongcheng_id}">
</div>
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
        var id =$("#tid").val();
        var order_tongcheng_id =$("#order_tongcheng_id").val();
        var respCode ="${respCode}";
        if(respCode =="01"){//存在进行中的同城订单，并且给予提示框
	        layer.alert("司机已接单正拼命赶过来...",{
	            title:"温馨提示"//提示标题，默认为：信息
	          //  ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
	            ,anim: 0 //动画类型0-6，默认为：0
	            ,closeBtn: 0//是否显示关闭按钮，0-不显示，1-显示，默认为1
	            ,btn: ['确定','取消'] //按钮
	            ,icon: 6    // icon
	            ,yes:function(){
	            	location.href="api/h5KeHu/tongchenghujiaos.do?information_tongcheng_id="+id
	            }
	            ,btn2:function(){
	            	location.href="api/h5KeHu/index.do";
	            }
	        });
        }
        if(respCode =="02"){//存在未付款的同城订单，并且给予提示框
	        layer.alert("有未支付订单，请您支付！",{
	            title:"温馨提示"//提示标题，默认为：信息
	          //  ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
	            ,anim: 0 //动画类型0-6，默认为：0
	            ,closeBtn: 0//是否显示关闭按钮，0-不显示，1-显示，默认为1
	            ,btn: ['确定','取消'] //按钮
	            ,icon: 6    // icon
	            ,yes:function(){
	            	location.href="api/h5KeHu/tongchengcome.do?order_tongcheng_id="+order_tongcheng_id
	            }
	            ,btn2:function(){
	            	location.href="api/h5KeHu/index.do";
	            }
	        });
        }
        if(respCode =="04"){//存在乘客已被司机接驾的同城订单，并给予提示框
	        layer.alert("同城打车订单正在进行中！",{
	            title:"温馨提示"//提示标题，默认为：信息
	          //  ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
	            ,anim: 0 //动画类型0-6，默认为：0
	            ,closeBtn: 0//是否显示关闭按钮，0-不显示，1-显示，默认为1
	            ,btn: ['确定','取消'] //按钮
	            ,icon: 6    // icon
	            ,yes:function(){
	            	location.href="api/h5KeHu/tongchengcome.do?order_tongcheng_id="+order_tongcheng_id
	            }
	            ,btn2:function(){
	            	location.href="api/h5KeHu/index.do";
	            }
	        });
        }
    });
</script>

<script src="static/js/wmpc/jquery-weui.js"></script>
</body>
</html>
