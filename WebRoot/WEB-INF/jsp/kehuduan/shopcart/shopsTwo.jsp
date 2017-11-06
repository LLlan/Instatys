<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
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
    <script src="static/js/wmpc/swiper.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <title>美食外卖</title>
    <style>
       
        img {
		    max-width: 100%;
		    height: 65px;
		}
		.weui-media-box_appmsg1 {
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;
            align-items: left;
            border-top: 1px solid #eee;
            padding: 10px 2px;
        }
        .merchant_list>.weui-media-box_appmsg1:nth-child(1){
            border-top:none;
        }
        .weui-media-box__thumb {
            width: 70px;
            height: 70px;
        }
        .distribute>span{
            display:block;
		    width:60px;
		    text-align:center;
		    float:left;
		    margin-top:3px;
            border: 1px solid #068dff;
            border-radius: 5px;
            color: #068dff;
            font-size: 10px;
            margin-right: 5px;
        }
        .weui-navbar + .weui-tab__bd {
            padding-top: 40px;
        }
        .weui-navbar__item.weui-bar__item--on {
		    background-color: #fff;
		}
		.weui-flex{
		    display: block;clear: both;
		}
		.tqf1{
		    width:80%;float:left; overflow: hidden;text-overflow: ellipsis;white-space: nowrap;
		
		}
		.tqf1+div{
		    width:20%;float:right;
		}
		.tqf1{
			font-size: 16px;
		    font-weight: bold;
		}
    </style>
</head>
<body ontouchstart>
<!--顶部-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">美食外卖</h1>
    <a href="api/h5KeHu/index.do" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<div class="container">
    <!--轮播图-->
   <!--  <div class="swiper-container">
        Additional required wrapper
        <div class="swiper-wrapper">
            Slides
            <div class="swiper-slide"><img src="static/images/wmpc/pic1.jpg"/></div>
            <div class="swiper-slide"><img src="static/images/wmpc/pic2.jpg"/></div>
            <div class="swiper-slide"><img src="static/images/wmpc/pic3.jpg"/></div>
        </div>
        If we need pagination
        <div class="swiper-pagination"></div>
    </div> -->
   <!-- 商铺详情-->
    <div class="weui-tab" >
        <div class="weui-navbar" style="background-color: #fff;position:fixed;left:0;top:60px;z-index:999;">
            <a class="weui-navbar__item  boxShadow1" href="api/h5KeHu/shops.do" style="padding:10px 0">
                销售量
            </a>
            <a class="weui-navbar__item weui-bar__item--on boxShadow1" href="api/h5KeHu/shopsTwo.do" style="padding:10px 0">
                距离最近
            </a>

        </div>
        <div class="weui-tab__bd bigBox" style="padding-top:40px;">
            <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item&#45;&#45;active  boxShadow3" >
            <div class="merchant_list" style="margin-top: 0;background-color: #fff;padding: 0 5px; margin-top:15px;">
	               <c:forEach items="${pd}" var="pd">
		                <a href="api/h5KeHu/shop.do?user_shangjia_id=${pd.user_shangjia_id }" class="weui-media-boxt weui-media-box_appmsg1 delicious">
		                    <div class="weui-media-box__hd">
		                        <!-- <a href="api/h5KeHu/shop.do"><img class="weui-media-box__thumb" src="static/images/wmpc/nm.jpeg" alt=""></a> -->
		                        <c:if test="${ pd.logoImg != '' && pd.logoImg != null}">
					           		<img src="<%=basePath%>${pd.logoImg }" alt=""/>
					            </c:if>
					            <c:if test="${ pd.logoImg == '' || pd.logoImg == null}">
					           		<!-- <img src="static/images/wmpc/u132.png" alt=""/> -->
					           		<img class="weui-media-box__thumb" src="static/images/wmpc/nm.jpeg" alt="">
					            </c:if>
		                    </div>
		                    <div class="weui-media-box__bd">
		                       <div class="weui-flex">
		                            <div class="weui-flex__item tqf1" >${pd.shopName}</div>
		                            <div class="weui-flex__item "  style="text-align:right;">
			                            <span style="color:#4CCF58;"><c:if test="${pd.isOpen=='1' }">营业中</c:if></span>
			                            <span  style="color:#000;"><c:if test="${pd.isOpen=='0' }">休息中</c:if></span>
									</div>
		                        </div>
		                         <div class="weui-flex mile" style="display: block;overflow: hidden;">
		                            <div class="weui-flex__item" style="width:80px;float:left;color:#999;">${pd.deliveryAmount }元起送</div>
		                            <div class="weui-flex__item distance" style="width:60px;float:left;color:#999;">免费配送</div>
		                            <!-- <div class="weui-flex__item right" style="width:60px;float:right;color:#999;">1.5km</div> -->
		                        </div>
			                    <div class="weui-flex distribute">
		                            <span>快餐配送</span>
		                            <span>网上超市</span>
		                            <span>免费配送</span>
		                        </div>
		                    </div>
		                </a>
	               </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <!--猜你喜欢-->
</div>
<script>
    $(function () {
        FastClick.attach(document.body);
    });

</script>
<!--轮播图-->
<script>
    $(".swiper-container").swiper({
        loop: true,
        autoplay: 3000
    });
   
</script>
</body>
</html>