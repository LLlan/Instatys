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
    <link rel="stylesheet" href="static/css/wmpc/common.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">

    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <script src="static/js/wmpc/layer/layer.js"></script>
    <title>订单详情</title>
    <style>
        body{
            background-color: #F4F4F4;
        }
        .icon1 {
		    top: 13px;
		}
		.title{
			margin-left:0;
		}
		.quxiaoDing{
			width:80px;
			height:30px;
			line-height:30px;
			color:#000;
			text-align:center;
			float:right;
			background-color:#fff;
			color:#000;
			border:1px solid #e5e5e5;
			border-radius:3px;
		}
		.quxiaoDing:hover{
			color:#000;
		}
    </style>
</head>
<body ontouchstart>
<!--顶部-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">全部订单</h1>
    <a href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--主体-->
<div class="container" style="padding-top: 60px;">
    <div class="statushead" style="display: block;">
        <div class="statuscircle">
           <!--  <img  class="circleimage" src="static/images/wmpc/dog.png"> -->
            <c:if test="${ pd.logoImg != '' && pd.logoImg != null}">
  				<img class="circleimage" src="<%=basePath%>${pd.logoImg }" alt="">
            </c:if>
            <c:if test="${ pd.logoImg == '' || pd.logoImg == null}">
            	<img class="circleimage"  src="static/images/wmpc/dog.png" alt="">
            </c:if>
        </div>
        <div class="order1" style="display: block;">
            <h1 class="statustext">
	            <c:if test="${pd.orderStateKehu=='0' }">订单已取消</c:if>
				<c:if test="${pd.orderStateKehu=='1' }">待付款</c:if>
				<c:if test="${pd.orderStateKehu=='2' }">骑手正在派送中</c:if>
				<c:if test="${pd.orderStateKehu=='3' }">待发货</c:if>
				<c:if test="${pd.orderStateKehu=='4' }">待收货</c:if>
				<c:if test="${pd.orderStateKehu=='5' }">订单已完成</c:if>
				<c:if test="${pd.orderStateKehu=='6' }">订单已结束</c:if>
            </h1>
            <!-- <p class="helptext">系统取消了订单,理由是“超过15分钟未支付”</p> -->
        </div>
        <div class="order2" style="display: none;text-align: center">
            <h1 class="complete">
                <c:if test="${pd.orderStateKehu=='0' }">订单已取消</c:if>
				<c:if test="${pd.orderStateKehu=='1' }">待付款</c:if>
				<c:if test="${pd.orderStateKehu=='2' }">已付款</c:if>
				<c:if test="${pd.orderStateKehu=='3' }">待发货</c:if>
				<c:if test="${pd.orderStateKehu=='4' }">待收货</c:if>
				<c:if test="${pd.orderStateKehu=='5' }">订单已完成 </c:if>
				<c:if test="${pd.orderStateKehu=='6' }">订单已结束</c:if>
            </h1>
            <p class="thank">感谢您对我们的信任，期待再次光临</p>
            <button class="again_order">再来一单</button>
        </div>

    </div>

    </div>
    <div  class="restaurant-card" >
        <div  class="head listitem">
            <div class="name-wrap">
                <img  class="avatar" src="static/images/wmpc/tb.jpeg">
                <span  class="name">${pd.shopName }</span>
            </div>
            <img  src="static/images/wmpc/rt_arrow.png" class="icon-arrowright">
        </div>
        <c:forEach items="${goodsList}" var="pds">
        <div  class="product-list listitem">
            <ul  class="cart-item">
                <li class="product-item">
                    <div  class="profile">
                        <p  class="name">${pds.goodsName }</p>
                    </div>
                    <div  class="price-wrap">
                        <span  class="quantity">x${pds.goodsNum }</span>
                        <span >¥${pds.presentPrice }</span>
                    </div>
                </li>
            </ul>
        </div>
        <ul class="listitem">
            <li  class="product-item">
                <span  class="name">餐盒费</span>
                <div class="price-wrap">
                    <span  class="quantity">x${pds.goodsNum }</span>
                    <span >¥${pds.canhefei }</span>
                </div>
            </li>
        </ul><!---->
       </c:forEach>
            <li  class="product-item">
                <span  class="name">配送费</span>
                <span >¥${pd.peisongfei }</span>
            </li>
        <div  class="finalprice listitem">
            	实付 ¥${pd.paySum }
        </div>
        <div  class=" listitem" style="overflow:hidden;">
            <a href="tel:${pd.tel_phone }" style="display:block;float:left;line-height: 60px;width:100px;height:40px">
                <img src="static/images/wmpc/contact.png" alt=""/>
            </a>
            <c:if test="${pd.orderStateKehu=='1' }"><a href="javascript:void(0);" onclick="quxiaoOrder('${pd.order_takeou_id}');" class="quxiaoDing">取消订单</a></c:if>
        </div>
    </div>
    <div  class="detailcard" >
        <div class="detailcard-delivery card">
            <div  class="xinxin">配送信息</div>
            <ul  class="cardlist">
            <c:if test="${not empty pd.qurysdTime and pd.qurysdTime!='' }">
            	<li  class="listitem">
                    <span >送达时间：</span>
                   	 ${pd.qurysdTime }
                 </li>
            </c:if>
              
                <li  class="listitem">
                    <span >姓名：</span>
                    ${pd.linkmanName }
                </li>
                <li  class="listitem">
                    <span>电话：</span>
                    ${pd.phone }
                </li>
                <li  class="listitem">
                    <span>地址：</span>
                    ${pd.detailAddress }
                </li>
            </ul>
        </div>
        <div  class="detailcard-order card">
            <div  class="xinxin">订单信息</div>
            <ul class="cardlist">
                <li  class="listitem">
                    <span >订单号：</span>${pd.orderNumber }
                </li>
                <li  class="listitem">
                    <span >支付方式：</span>${pd.payMethod }
                 </li>
                <li  class="listitem">
                    <span >下单时间：</span>${fn:substring(pd.orderTime,0,16)}
                 </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
	function quxiaoOrder(id){
		layer.alert("您确定取消订单！",{
	            title:"温馨提示"//提示标题，默认为：信息
	            ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
	            ,anim: 0 //动画类型0-6，默认为：0
	            ,closeBtn: 1//是否显示关闭按钮，0-不显示，1-显示，默认为1
	            ,btn: ['确定','取消'] //按钮
	            ,icon: 6    // icon
	            ,yes:function(){
	                location.href="api/h5KeHu/quxiaoOrderDelete.do?order_takeou_id="+id;
	            }
	            ,btn2:function(){
	            }
	        })
	}

</script>
</body>
</html>