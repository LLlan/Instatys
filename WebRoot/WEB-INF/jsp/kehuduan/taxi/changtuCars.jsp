<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE >
<html >
<head>
    <title>长途拼车 </title>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc//weui.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
        .dacheOrder>span{
            padding:0 10px;border-radius: 10px;background-color: #d2d2d2;color:#fff;font-size: 14px;
        }
        .chezu{
            width:100%;
        }
        .chezu>a{
            display: inline-block;width:50%;height:50px;line-height:50px;text-align:center;border-bottom:2px solid #fff;
            color:#000;float: left;overflow: hidden;background-color: #fff;font-size: 16px;;
        }
       .act{
            color:#0284fc;border-bottom: 1px solid #0284fc;
        }
        .fabuNews {
		    right: 20px;
		    font-size:16px;
		}
		.wy-header {
		    position: fixed;
		    width:100%;
		    height:60px;
			z-index: 500;;
		
		}
		.weui-navbar {
		    position: fixed;
		    z-index: 500;
		    top: 60px;
		    width: 100%;
		}
		.wodefabuUl>li{
			width:100%;overflow:hidden;
		}
      /*  .mask{
            width:100%;height:100%;background-color: rgba(0,0,0,0.3);
        }*/
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">长途拼车<a href="api/h5KeHu/fabuNews.do" class="fabuNews">发布路线</a></h1>
  <a href="api/h5KeHu/dcpc.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<!--主体-->
<div class="weui-tab" style="margin-top:64px;background-color:#f4f4f4;">
    <div class="weui-navbar" style="background-color: #fff;">
    	<!-- api/h5KeHu/changtuCar.do -->
        <a class="weui-navbar__item" href="api/h5KeHu/changtuCar.do" style="padding:10px 0">
            	长途路线
        </a>
       	<a class="weui-navbar__item weui-bar__item--on" href="api/h5KeHu/wdfbchangtuCarList.do" style="padding:10px 0">
         	  	我的发布
       	</a>
<!-- api/h5KeHu/wdfbchangtuCarList.do -->
    </div>
    <div class="weui-tab__bd">
	        <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active" style="margin-top:10px;">
	            <div class="dacheOrder" >
	                     <c:forEach items="${wdfbchangtuList}" var="wdfbchangtuList">
			                <a href="api/h5KeHu/changtuXiangdan.do?information_kehu_changtu_id=${wdfbchangtuList.information_kehu_changtu_id}" class="dacheOX">
				                <div class="orderBox shifuZiliao shifuZiliao2">
				                    <p>${wdfbchangtuList.departureCity}-${wdfbchangtuList.arrivalCity} 
				                    	<span style="color:#0068fc;">进行中...</span>
				                    </p>
				                    <ul class="wodefabuUl">
				                        <li>出发时间: <span>${fn:substring(wdfbchangtuList.departureTime,0,16)}</span></li>
				                        <li>出发地点 : <span>${wdfbchangtuList.departurePlace}</span></li>
				                        <li>到达地点 : <span>${wdfbchangtuList.destination}</span></li>
				                        <li style="color:#ff7d00;">拼车费 : <span style="color:#ff7d00;">￥${wdfbchangtuList.carpoolFee}</span></li>
				                    </ul>
				                </div></a>
		                 </c:forEach>
			    </div>
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
