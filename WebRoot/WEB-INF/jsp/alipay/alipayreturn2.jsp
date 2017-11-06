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
    	<a href="api/h5KeHu/orderDetail.do?order_takeou_id=${order_takeou_id }" style="color: #38c4ff;">订单详情</a>
    	<a href="api/h5KeHu/index.do" style="margin-left: 25%;color: #38c4ff;">返回主页</a>
    </div>
	</div>
    <br>
</div>
</body>
</html>