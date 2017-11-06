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
    <title>打车订单</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
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
        .orderBox > p:nth-child(1){
            width:90%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;
        }
        .orderBox > p:nth-child(2){
            width:50%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;
        }
        .fan{
            position: relative;
        }
        .orderBox > span:nth-child(3) {
            position: absolute;top:48px;;right:10px;;
        }
        .orderBox > p:nth-child(1) > span {
		    position: absolute;
		    top: 50px;
		    right: 16px;
		    font-size: 14px;
		    color: #565656;
		    font-weight: 400;
		}
		.dacheOrder>a {
		    display: block;
		    padding: 10px;
		    background-color: #f4f4f4;
		    border:none;
		    border-radius: 5px;
		    margin-top: 10px;
		    position: relative;
		}
		.fan{
			width:96%;
			padding-left:4%;
		}
		.wy-header {
		    height: 44px;
		    width: 100%;
		    position: fixed;
		    top: 0;
		    left: 0;
		    z-index: 600;
		    background-color: #0284fe;
		    padding: 10px 0;
		}
		.weui-tab {
		    position: relative;
		    height: 100%;
		    width: 100%;
		    margin-top: 60px;
		}
	
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">打车订单</h1>
  <a href="api/h5KeHu/wd.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<div class="weui-tab"  style="background-color:#f4f4f4;">
    <div class="weui-navbar" style="background-color: #fff;position:fixed;top:60px;">
        <a class="weui-navbar__item weui-bar__item--on" href="api/h5KeHu/dachedingdan.do" style="padding:10px 0">
            同城订单
        </a>
        <a class="weui-navbar__item" href="api/h5KeHu/dachedingdans.do" style="padding:10px 0">
            长途订单
        </a>

    </div>
    <div class="weui-tab__bd">
        <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
            <div class="dacheOrder">
            <c:forEach items="${pd}" var="pd">
                <span>${pd.timeshow }</span>
                <a href="api/h5KeHu/tongchengcome.do?order_tongcheng_id=${pd.order_tongcheng_id}"><div class="orderBox fan">
                    <p>${pd.departurePlace}-${pd.destination} 
                    	<span style="color:#006afb;">
							<c:if test="${pd.order_tongcheng_status=='1' }">等待接驾</c:if>
							<c:if test="${pd.order_tongcheng_status=='2' }">未付款</c:if>
							<c:if test="${pd.order_tongcheng_status=='4' }">待送达</c:if>
							<c:if test="${pd.order_tongcheng_status=='5' }">已完成</c:if>
                    	</span>
                    </p>
                    <p style="color:#feb200;">车费：约${pd.about_Amount}元</p>
                </div></a>
            </c:forEach>
            </div>
        </div>
    </div>
</div>



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
