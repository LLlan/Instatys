<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE >
<html >
<head>
    <title>打车拼车</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/lib/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">打车拼车</h1>
  <a href="javascript:history.go(-1)" class="icon1" style="margin-top:10px;"><i class="icon-angle-left "></i></a>
</div>

<!--头部结束-->
<!--主体-->
<div class="cheBox" style="margin-top:60px;">
    <a href="tongchengCar.html" class="dache1">
        <h2>同城打车</h2>
        <p>我们一直在您身边</p>
    </a>
    <a href="changtuCar.html"  class="pinche1">
        <h2>长途拼车</h2>
        <p>美好的旅途我们一直陪伴您的身边</p>
    </a>
</div>

<!--底部导航-->
<div class="weui-tabbar wy-foot-menu">
    <a href="static/index.html" class="weui-tabbar__item ">
        <div class="weui-tabbar__icon foot-menu-home"></div>
        <p class="weui-tabbar__label">首页</p>
    </a>
    <a href="static/Interaction/hudongZone.html" class="weui-tabbar__item">
        <div class="weui-tabbar__icon foot-menu-cart "></div>
        <p class="weui-tabbar__label">互动社区</p>
    </a>
    <a href="dachepinche.html" class="weui-tabbar__item weui-bar__item--on">
        <div class="weui-tabbar__icon foot-menu-member "></div>
        <p class="weui-tabbar__label">打车拼车</p>
    </a>
    <a href="static/user/mine.html" class="weui-tabbar__item  ">
        <div class="weui-tabbar__icon foot-menu-car"></div>
        <p class="weui-tabbar__label">我的</p>
    </a>
</div>
<script src="static/lib/jquery-2.1.4.js"></script>
<script src="static/lib/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
</body>
</html>
