<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    <link rel="stylesheet" href="static/css/wmpc/yahu.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <title>全部订单</title>
<style>
        .thumb {
            top: 10px;
            left: 10px;
            border-radius:6px;
        }
        .weui-media-box8 {
           margin:0;;
            height:120px;
        }
        .weui-panel__bd {
            padding: 10px 10px 0 10px;
        }
        .ord-pro>p:nth-child(1){
            font-size: 12px;;color:#999;
        }
        .ord-pro>p:nth-child(2){
            margin-top:10px;color:#666;
        }
        .ord-pro>p:nth-child(3){
            margin-top:10px;font-size:16px;color:#333;
        }
        .ord-pro-link h3 {
            color: #666;
            font-weight:400;
        }
        .ord-pro-link>span{
            display: block;width:120px;height:30px;line-height:30px;;overflow: hidden;text-overflow:ellipsis;
            white-space: nowrap;
        }
        .icon1 {
		    margin-top: 13;
		}
		.title{
			margin-left:0;
		}
 </style>
</head>
<body>
<!--顶部-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">全部订单</h1>
    <a href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--主体-->
<div class="contaner" style="padding-top: 60px;">
<c:forEach items="${OrderList}" var="pds">
    <div class="weui-panel2">
        <div class="weui-panel__bd">
            <div class="weui-media-box8 weui-media-box_text">
                <c:if test="${ pds.logoImg != '' && pds.logoImg != null}">
           			<img class="weui_media_appmsg_thumb thumb" src="<%=basePath%>${pds.logoImg }" alt="">
	            </c:if>
	            <c:if test="${ pds.logoImg == '' || pds.logoImg == null}">
	            	<img class="weui_media_appmsg_thumb thumb"  src="static/images/wmpc/dog.png" alt="">
	            </c:if>
                <div style="margin-left: 65px;">
                    <div class="weui-media-box__title1">
                        <h1 class="weui-media-box__desc">
                            <a href="api/h5KeHu/orderDetail.do?order_takeou_id=${pds.order_takeou_id }" class="ord-pro-link">
                                <i></i>
                              	<span>${pds.shopName }</span>  
                                <h3>
                                	<c:if test="${pds.orderStateKehu=='0' }">订单已取消</c:if>
									<c:if test="${pds.orderStateKehu=='1' }">待付款</c:if>
									<c:if test="${pds.orderStateKehu=='2' }">已付款</c:if>
									<c:if test="${pds.orderStateKehu=='3' }">待发货</c:if>
									<c:if test="${pds.orderStateKehu=='4' }">待收货</c:if>
									<c:if test="${pds.orderStateKehu=='5' }">已完成</c:if>
									<c:if test="${pds.orderStateKehu=='6' }">已结束</c:if>
                                </h3>
                            </a>
                        </h1>
                    </div>
                    <a href="api/h5KeHu/orderDetail.do?order_takeou_id=${pds.order_takeou_id }" class="ord-pro">
                        <p >${fn:substring(pds.orderTime,0,16)}</p>
                        <p class="">
                            <span>${pds.goodsName }等<span class="number">${pds.goodsNum }</span>件商品</span>
                        </p>
                        <p class="price">￥<span>${pds.totalSum }</span></p>
                    </a>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
</div>

</body>
</html>