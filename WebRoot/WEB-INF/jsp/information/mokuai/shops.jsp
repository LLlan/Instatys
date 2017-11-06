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
    <title>${msg }</title>
    <style>
        .boxShadow3{
            box-shadow: 0 0 8px #000;;
        }
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
            padding: 3px;
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
		.weui-media-boxt{
			background-color: #fff;
   			 margin-top: 10px;
		}
		.weui-media-boxt:last-child{
			margin-bottom: 10px;
		}
    </style>
</head>
<body ontouchstart>
<!--顶部-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">${msg }</h1>
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
        <div class="weui-tab__bd bigBox">
            <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item&#45;&#45;active  " >
            <div class="merchant_list" style="margin-top: 0;padding: 0 5px; margin-top:15px;">
               <c:forEach items="${list}" var="pd">
	                <a href="javascript:void(0)" class="weui-media-boxt weui-media-box_appmsg1 delicious">
	                    <div class="weui-media-box__hd">
				           	<img src="<%=basePath%>${pd.headImg }" alt=""/>
	                    </div>
	                    <div class="weui-media-box__bd">
	                        <div class="weui-flex">
	                            <div class="weui-flex__item tqf1" >${pd.title}</div>
	                        </div>
	                        <div class="weui-flex mile" style="display: block;overflow: hidden;">
	                        	${pd.introduct}
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
    $(function(){
        $(".boxShadow1").click(function(){
            var num0=$(this).index();
            var sha=$(".bigBox").children().eq(num0);
            sha.addClass("boxShadow3");
        })
    })
</script>
</body>
</html>