<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE>
<html >
<head>
    <title>在线支付</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style type="text/css">
    	.zhifu1{
    		margin-top:90px;
    	}
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">在线支付</h1>
  <a href="javascript:history.go(-1)" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->

<p class="zhifu1">合计<span>￥38</span></p>
<p class="fangshi">选择支付方式</p>
<div class="weixinZ">
    <p>
        <img src="static/images/wmpc/weixin_03.png" alt=""/>
        <span>微信支付</span>
        <a class=" weui-cells_checkbox">
           <label class="weui-cell weui-check__label" for="s11">
               <input type="checkbox" class="weui-check" name="checkbox1" id="s11" checked="checked">
               <i class="weui-icon-checked"></i>
           </label>
        </a>
    </p>
</div>
<a href="javascript:void(0)" class="querenZ">确认支付<span>￥38.00</span></a>


<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>

<script src="static/js/wmpc/jquery-weui.js"></script>
</body>
</html>
