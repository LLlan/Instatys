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
    <title>同城打车</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style type="text/css">
   	.chexinxi1 ul>li:nth-child(2)>a:nth-child(2) {
	    display: block;
	    padding: 0 8px;
	    text-align: center;
	    float: right;
	    background-color: #0284fe;
	    border-radius: 5px;
	    color: #fff;
	    margin-right: 10px;
	    font-size: 15px;
	}
   
   	.changtuUl li{
		width:100%;overflow:hidden;white-space:no-wrap;
	}
   	.qufukuan{
		display: inline-block;
	    padding: 0 8px;
	    background-color: #0284fe;
	    border-radius: 5px;
	    color: #fff;
	    font-size: 18px;
	    width: 160px;
	    height: 50px;
	    text-align: center;
	    line-height: 50px;
	}
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">同城打车</h1>
  <a href="api/h5KeHu/dachedingdan.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<!--主体-->
<div class="dacheOrder" style="margin-top:60px;">
    <div class="chexinxi1" href="javascript:void(0);">
	    <div class="orderBox shifuZiliao">
	        <ul class="changtuUl">
	            <li>师傅 : <span>${pd.userNamesiji }</span></li>
	            <li>电话 : <span>${pd.phonesiji }</span> <a href="tel:${pd.phonesiji }">联系师傅</a></li>
	            <li>车型 : <span>${pd.carType }</span></li>
	            <li>颜色 : <span>${pd.carColor }</span></li>
	            <li>牌号 : <span>${pd.carNumber }</span></li>
	        </ul>
	    </div>
    </div>
    <div class="sijiG">
	    <c:if test="${pd.order_tongcheng_status=='1' }">
	    	<p>司机正拼命赶过来...</p>
	    	<p>距离 您大约<span>${pd.about }</span>米</p>
	    	<p>预计<span>1</span>分钟到达</p>
	    </c:if>
	    <c:if test="${pd.order_tongcheng_status=='5' }">
	    	<p style="width: 100%;height: 33px;line-height: 33px;text-align: center;color: #fa8200;">全程<span>${pd.mileage }</span>公里</p>
	    	<p>历时<span>15</span>分钟</p>
	    	<p style="margin: 10px 0 10px 0;">
	    		<a class="qufukuan" href="javascript:void(0);">已付款￥${pd.radeAmount }</a>
	    	</p>
	    </c:if>
	    <c:if test="${pd.order_tongcheng_status=='2' }">
	    	<p style="width: 100%;height: 33px;line-height: 33px;text-align: center;color: #fa8200;">全程<span>${pd.mileage }</span>公里</p>
	    	<p>历时<span>15</span>分钟</p>
	    	<p style="margin: 10px 0 10px 0;">
	    		<a class="qufukuan" href="<%=basePath %>api/h5KeHu/topaytcdc.do?order_tongcheng_id=${pd.order_tongcheng_id}&radeAmount=${pd.radeAmount }">去付款￥${pd.radeAmount }</a>
	    	</p>
	    </c:if>
	    <c:if test="${pd.order_tongcheng_status=='4' }">
	    	<%-- <p style="width: 100%;height: 33px;line-height: 33px;text-align: center;color: #fa8200;">全程<span>${pd.about }</span>米</p>
	    	<p>历时<span>15</span>分钟</p>
	    	<p style="margin: 10px 0 10px 0;"> --%>
	    		<p>同城打车订单正在进行中!</p>
		    	<p>距离目的还有<span>${pd.about }</span>米</p>
		    	<p>预计<span>15</span>分钟到达</p>
	    		<a class="qufukuan" href="<%=basePath %>api/h5KeHu/topaytcdc.do?order_tongcheng_id=${pd.order_tongcheng_id}&radeAmount=${pd.radeAmount }">大约￥${pd.about_Amount }</a>
	    	</p>
	    </c:if>
    </div>
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
