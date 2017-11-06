<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE>
<html>
<head>
    <title>同城打车</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
        .hujiaoCir>p:nth-child(1){
            color:#0077fd;text-align: center;margin-top:46px;font-size: 16px;
        }
        .hujiaoCir>p:nth-child(2){
            color:#fd8800;text-align: center;margin-top: 16px;font-size:22px;font-weight: 600;
        }
        .hujiaoCir>p:nth-child(3){
            color:#fd8800;text-align: center;margin-top:0;font-size: 16px;
        }
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div class="wy-header header">
    <div class="wy-header-icon-back"><a href="javascript:history.go(-1)"></a></div>
    <div class="wy-header-title">同城打车</div>
</div>
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">同城打车</h1>
  <a href="api/h5KeHu/dcpc.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<!--主体-->
<div class="dacheOrder" style="margin-top:60px;">
    <div class="hujiaoCir">
        <p>进行中...</p>
        <p>￥15.00</p>
        <p>车费</p>
    </div>
    <div class="yanzheng" style="background-color: #C7C6CC;"><a href="javascript:void(0);" style="margin:10px auto;border-radius: 10px;">确认订单</a></div>

</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
</body>
</html>
