<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>我的</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
   <base href="<%=basePath%>">
  <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
  <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
  <link rel="stylesheet" href="static/css/wmpc/style.css">
  <link rel="stylesheet" href="static/css/wmpc/index.css">
  <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
  <script src="static/js/wmpc/jquery-2.1.4.js"></script>
  <script src="static/js/wmpc/fastclick.js"></script>
  <script src="static/js/wmpc/jquery-weui.js"></script>
  <title>会员中心</title>
  <style type="text/css">
  	.center-ordersModule .name {
	    font-size: 16px;
	    color: #6e6e6e;
	}
	.weui-cell {
	    margin-left: -15px;
	    margin-right: -15px;
	}
	.center-list-txt {
	    font-size: 16px;
	    color: #333;
	    line-height: 20px;
	    margin-left: 10px;
	}
	.tuichu {
	    display: block;
	    width: 100%;
	    height: 50px;
	    line-height: 50px;
	    font-size: 16px;
	    text-align: center;
	    color: #ec4646;
	}
	.weui-cell__hd img {
	    margin-left: 0;
	}
	
	.weui-cell {
	    padding: 15px 15px;
	}
	.touxiang img {
	    margin-top: 34px;
	}
	.weui-panel__bd {
      padding: 0 0 0 15px;
    }
	.numD {
      top:0;
      color: #333;
      font-size: 22px;
    }
    .center-ordersModule .name {
      height:14px;
      line-height: 14px;;
      font-size: 14px;
      color: #ababab;
      margin-top:15px;
    }
    .center-ordersModule .imgicon {
      height: 20px;
      line-height: 20px;;
       margin-bottom:0;
    }
    .center-ordersModule {
      padding-top: 20px;
    }
    .dingdan{
      height:88px;
    }
    .weui-cell {
      margin-right: 0;
    }
   .weui-cell_access:active{
   	background-color:#fff;
   }
	.weui-media-box_small-appmsg .weui-cells{
		margin-top:1px;
	}
  </style>
  
  
</head>
<body ontouchstart>
<!--主体-->
<div class='weui-content'>
  <div class="wy-center-top">
    <div class="touxiang">
      <div >
      	<!-- <img class="weui-media-box__thumb radius" src="static/images/wmpc/u132.png" alt=""> -->
	            	<c:if test="${pds.headImg!='' && pds.headImg!=null }">
	            		<img class="weui-media-box__thumb radius" src="${pds.headImg }" alt="">
	            	</c:if>
	            	<c:if test="${pds.headImg=='' || pds.headImg==null }">
	            		 <img class="weui-media-box__thumb radius" src="static/images/wmpc/u132.png" alt="">
	            	</c:if>
      </div>
      <div >
        <h4 class="weui-media-box__title user-name" style="display: none;">飞翔的小土豆</h4>
        <h4 class="weui-media-box__title user-login" style="display: block;">已登录</h4>
      </div>
    </div>
  </div>
  <div class="weui-panel weui-panel_access dingdan">
    <div class="weui-panel__bd" style="padding:0;">
      <div class="weui-flex">
        <div class="weui-flex__item">
          <a href="api/h5KeHu/myorder.do" class="center-ordersModule">
            <div class="imgicon"><span class="numD">${pd.haveInHands }</span><span style="color:#333;font-size:13px;;">单</span></div>
            <div class="name">外卖订单</div>
          </a>
        </div>
        <!-- <div class="weui-flex__item">
          <a href="javascript:void(0)" class="center-ordersModule">
            <div class="imgicon">
	            <span class="numD">2</span>
	            <span style="color:#333;font-size:13px;;">单</span>
	        </div>
            <div class="name">点餐订单</div>
          </a>
        </div> -->
        <div class="weui-flex__item">
          <a href="api/h5KeHu/dachedingdan.do" class="center-ordersModule">
            <div class="imgicon">
            	<span class="numD">${ss }
            	</span><span style="color:#333;font-size:13px;;">单</span></div>
            <div class="name">打车订单</div>
          </a>
        </div>
      </div>
    </div>
  </div>
  <div class="weui-panel" style="margin-top: 15px;">
        <div class="weui-panel__bd">
          <div class="weui-media-box weui-media-box_small-appmsg">
            <div class="weui-cells">
              <a class="weui-cell weui-cell_access" href="api/h5KeHu/ziliao.do">
                <div class="weui-cell__hd"><img src="static/images/wmpc/02_03.png" alt="" class="center-list-icon"></div>
                <div class="weui-cell__bd weui-cell_primary">
                  <p class="center-list-txt">会员资料</p>
                </div>
                <span class="weui-cell__ft"></span>
              </a>
              <a class="weui-cell weui-cell_access" href="javascript:;" id="shangjiaS">
                <div class="weui-cell__hd"><img src="static/images/wmpc/02_08.png" alt="" class="center-list-icon"></div>
                <div class="weui-cell__bd weui-cell_primary">
                  <p class="center-list-txt">商家申请</p>
                </div>
                <span class="weui-cell__ft"></span>
              </a>
              <a class="weui-cell weui-cell_access" href="javascript:;" id="sijiS">
                <div class="weui-cell__hd"><img src="static/images/wmpc/02_10.png" alt="" class="center-list-icon"></div>
                <div class="weui-cell__bd weui-cell_primary">
                  <p class="center-list-txt">司机申请</p>
                </div>
                <span class="weui-cell__ft"></span>
              </a>
              <a class="weui-cell weui-cell_access" href="api/h5KeHu/address.do?tag=2">
                <div class="weui-cell__hd"><img src="static/images/wmpc/02_12.png" alt="" class="center-list-icon"></div>
                <div class="weui-cell__bd weui-cell_primary">
                  <p class="center-list-txt">收货地址</p>
                </div>
                <span class="weui-cell__ft"></span>
              </a>
              <a class="weui-cell weui-cell_access" href="#">
                <div class="weui-cell__hd"><img src="static/images/wmpc/02_14.png" alt="" class="center-list-icon"></div>
                <div class="weui-cell__bd weui-cell_primary">
                  <p class="center-list-txt">清除缓存</p>
                </div>
                <!--<span style="font-size: 14px;">23M</span>-->
              </a>
            </div>
          </div>
        </div>
      </div>
      <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/gotuichu.do'">
		  <div class="tuichu">
		  		退出账号
		  </div>
 	  </a>
</div>
<!--底部导航-->
<!-- <div class="weui-tabbar wy-foot-menu">
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/index.do'" class="weui-tabbar__item ">
    <div class="weui-tabbar__icon foot-menu-home"></div>
    <p class="weui-tabbar__label">首页</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/hdsq.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon foot-menu-cart"></div>
    <p class="weui-tabbar__label">互动社区</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/dcpc.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon  foot-menu-member"></div>
    <p class="weui-tabbar__label">打车拼车</p>
  </a>
  <a href="href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/wd.do'" class="weui-tabbar__item weui-bar__item--on">
    <div class="weui-tabbar__icon foot-menu-car"></div>
    <p class="weui-tabbar__label">我的</p>
  </a>
</div> -->



<div class="mask" style="display: none;"></div>
<div class="weui-cells1" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai1" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:79px;line-height: 79px">
   请先下载外卖顺风车商家版！
  </div>
  <div id="quxiao" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel1">确定</p>
  </div>
</div>
<div class="weui-cells2" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai2" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:79px;line-height: 79px">
    请先下载外卖顺风车司机版！
  </div>
  <div id="quxiao2" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel">确定</p>
  </div>
</div>
<!--底部导航-->
<div class="weui-tabbar wy-foot-menu">
     <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/index.do'" class="weui-tabbar__item ">
    <div class="weui-tabbar__icon foot-menu-home"></div>
    <p class="weui-tabbar__label">首页</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/hdsq.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon foot-menu-cart"></div>
    <p class="weui-tabbar__label">互动社区</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/dcpc.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon  foot-menu-member"></div>
    <p class="weui-tabbar__label">打车拼车</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/wd.do'" class="weui-tabbar__item weui-bar__item--on">
    <div class="weui-tabbar__icon foot-menu-car"></div>
    <p class="weui-tabbar__label">我的</p>
  </a>
</div>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
  $(function(){
    $("#shangjiaS").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells1").fadeIn();
      $(".cancel1").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells1").fadeOut();
      })
    })
  });
  $(function(){
    $("#sijiS").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells2").fadeIn();
      $(".cancel").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells2").fadeOut();
      })
    })
  });
</script>
</body>
</html>
