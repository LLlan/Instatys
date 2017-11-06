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
<link rel="stylesheet" href="static/css/wmpc/iconfont.css">
<link rel="stylesheet" href="static/css/wmpc/weui.css">
<link rel="stylesheet" href="static/css/wmpc/weui.min.css">
<link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
<link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
<link rel="stylesheet" href="static/css/wmpc/liMarquee.css">
<link rel="stylesheet" href="static/css/wmpc/yahu.css">
<link rel="stylesheet" href="static/css/wmpc/index.css"/>
<link rel="stylesheet" href="static/css/wmpc/style-mine.css">
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/jquery.liMarquee.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script src="static/js/wmpc/iconfont.js"></script>
<script src="static/js/wmpc/swiper.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<title>首页</title>
<style>
.nav1 {
	float: left;
	margin: 0 auto 0;
	padding: 5px 0 20px;
	width: 100%;
	background: #ffffff;
}

.nav1 ul {
	margin: 0 0 0;
}

.nav1 li {
	float: left;
	display: block;
	margin-top: 15px;
	width: 25%;
	text-align: center;
	color: #333333;
}

.nav1 li i {
	display: block;
	wtext-align: center;
	margin: 3px auto 0;
}

.nav1 li i img {
	width: 50%;
	height: 50%;
	margin: 0 auto 0;
	display: block;
}

.nav1 li a {
	display: block;
	cursor: pointer;
	color: #666;
	font-size:14px;
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
.weui-media-box_appmsg1 {
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    align-items: left;
    border-top: 1px solid #eee;
    padding: 10px 2px;
}
.weui-media-box__thumb{
    width:70px;height:70px;
}
.headlines .head_lt {
    width: 80px;
}
.settle ul li .pic {
    margin-left: 10px;
    float: right;
}
.gift {
    display: flex;
    display: -webkit-box;
    display: -webkit-flex;
    align-items: inherit;
}
.settle ul li span {
    display: block;
    width: 50%;
    height: 20px;
    line-height: 20px;
    margin-top: 20px;
    float: left;
    color: #fff;
    background-color: #068dff;
    border-radius: 5px;
}
.searchBox {
    width: 92%;
    margin-top: 10px;
}
.hasManyCity{
	height:60px;
}
.cityBtn{
	width:92%;
	margin-left:5%;
	padding-top: 15px;
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
.cityBtn img{
	width:18px;
	height:18px;
}
#baidu_geo{
	font-size:14px;
	padding-left:5px;
}
</style>
</head>
<body ontouchstart>
	<!--头部 开始-->
	<div class="hasManyCity">
		<div class="cityBtn">
			<img src="static/images/wmpc/location1.png" alt="" /><span id="baidu_geo"></span>
		</div>
	</div>
<!--主体-->
<div class="cotainer" style="padding-top: 60px;">
		<!--<section class="section" >
        <div class="item">
            <ul class="text-center row">
                <li class="col-xs-3"><a href="shopcart/waimai.html">美食外卖</a></li>
                <li class="col-xs-3"><a href="">旅游景点</a></li>
                <li class="col-xs-3"><a href="">汽车服务</a></li>
                <li class="col-xs-3"><a href="">休闲娱乐</a></li>
                <li class="col-xs-3"><a href="">酒店宾馆</a></li>
                <li class="col-xs-3"><a href="">婚礼庆典</a></li>
                <li class="col-xs-3"><a href="">生鲜超市</a></li>
                <li class="col-xs-3"><a href="">互动社区</a></li>
            </ul>
        </div>
    </section>-->
    
    <!--轮播图-->
		<div class="swiper-container">
			<!-- Additional required wrapper -->
			<div class="swiper-wrapper">
				<!-- Slides -->
			<c:forEach items="${varList }" var="varList">
				<div class="swiper-slide">
					<!-- <img src="static/images/wmpc/pic1.jpg" /> -->
					<img  src="<%=basePath%>${varList.PATH }"/>
				</div>
			</c:forEach>
				<!-- <div class="swiper-slide">
					<img src="static/images/wmpc/pic2.jpg" />
				</div>
				<div class="swiper-slide">
					<img src="static/images/wmpc/pic3.jpg" />
				</div> -->
			</div>
			<!-- If we need pagination -->
			<div class="swiper-pagination"></div>
		</div>
		<div class="weui-flex">
			<div class="weui-flex__item">
				<div class="nav1">
					<ul>
						<li><a href="api/h5KeHu/shops.do"> <i><img
									src="static/images/wmpc/meishiwaimai22.png" alt="美食外卖">
							</i> 美食外卖 </a></li>
						<li><a href="api/mokuai/getListOfMokuai.do?type=1" id="lyjd"> <i><img
									src="static/images/wmpc/lvyoujigndian22.png" alt="旅游景点">
							</i> 旅游景点 </a></li>
						<li><a href="api/mokuai/getListOfMokuai.do?type=2" id="qcfw"> <i><img
									src="static/images/wmpc/qichefuwu22.png" alt="汽车服务">
							</i> 汽车服务 </a></li>
						<li><a href="api/mokuai/getListOfMokuai.do?type=3" id="xxyl"> <i><img
									src="static/images/wmpc/xiuxianyule22.png" alt="休闲娱乐">
							</i> 休闲娱乐 </a></li>

						<li><a href="api/mokuai/getListOfMokuai.do?type=4" id="jdbg"> <i><img
									src="static/images/wmpc/jiudianbingguan22.png" alt="酒店宾馆">
							</i> 酒店宾馆 </a></li>
						<li><a href="api/mokuai/getListOfMokuai.do?type=5" id="hlqd"> <i><img
									src="static/images/wmpc/hunliqindian22.png" alt="婚礼庆典">
							</i> 婚礼庆典 </a></li>
						<li><a href="api/mokuai/getListOfMokuai.do?type=6" id="sxcs"> <i><img
									src="static/images/wmpc/shengxianchaoshi22.png" alt="生鲜超市">
							</i> 生鲜超市 </a></li>
						<li><a href="api/h5KeHu/hdsq.do"> <i><img
									src="static/images/wmpc/hudongshequ22.png" alt="互动社区">
							</i>互动社区 </a></li>
						<div class="clear"></div>

					</ul>
					<div class="swiper-pagination"></div>
				</div>
			</div>
		</div>
		<!--今日头条-->
		<!-- <div class="headlines" style="margin: 10px 0;">
			<div class="head_lt">今日头条</div>
			<div class="head_rt">
				<a href="javascript:void(0)">可平移的jQuery幻灯片插件</a> <a href="javascript:void(0)"> 让页面滚动更有趣</a> <a
					href="javascript:void(0)">jQuery Loading效果插件</a>
			</div>
		</div> -->
		
		<!--入驻申请-->
		<div class="settle clearfix">
			<ul class="text-center row">
				<li class="col-xs-6"><a href="javascript:;" id="shangjiaS" >
						<p class="apply">商家入驻申请</p>
						<div class="gift">
							<span>查看有礼</span>
							<div class="pic">
								<img src="static/images/wmpc/timg.jpg" alt="" />
							</div>
						</div> </a></li>
				<li class="col-xs-6"><a href="javascript:;" id="sijiS">
						<p class="apply">司机入驻申请</p>
						<div class="gift">
							<span>查看有礼</span>
							<div class="pic">
								<img src="static/images/wmpc/timg.jpg" alt="" />
							</div>
						</div> </a></li>
				<li class="col-xs-6"><a href="api/h5KeHu/hdsq.do">
						<p class="apply">进入互动社区</p>
						<div class="gift">
							<span>查看有礼</span>
							<div class="pic">
								<img src="static/images/wmpc/timg.jpg" alt="" />
							</div>
						</div> </a></li>
				<li class="col-xs-6"><a href="api/h5KeHu/dcpc.do">
						<p class="apply">打车拼车查询</p>
						<div class="gift">
							<span>查看有礼</span>
							<div class="pic">
								<img src="static/images/wmpc/timg.jpg" alt="" />
							</div>
						</div> </a></li>
			</ul>
		</div>
<div class="mask" style="display: none;"></div>
<div class="weui-cells1" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai1" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
   请先下载外卖顺风车商家版！
  </div>
  <div id="quxiao" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel1">确定</p>
  </div>
</div>
<div class="weui-cells2" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai2" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
    请先下载外卖顺风车司机版！
  </div>
  <div id="quxiao2" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel">确定</p>
  </div>
</div>
<div class="weui-cells3" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai3" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
    <b>“旅游景点”正在开发中!</b>
  </div>
  <div id="quxiao3" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel3">确定</p>
  </div>
</div>
<div class="weui-cells4" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai4" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
    <b>“汽车服务”正在开发中!</b>
  </div>
  <div id="quxiao4" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel4">确定</p>
  </div>
</div>
<div class="weui-cells5" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai5" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
    <b>“休闲娱乐”正在开发中!</b>
  </div>
  <div id="quxiao5" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel5">确定</p>
  </div>
</div>
<div class="weui-cells6" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai6" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
    <b>“酒店宾馆”正在开发中!</b>
  </div>
  <div id="quxiao6" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel6">确定</p>
  </div>
</div>
<div class="weui-cells7" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai7" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
    <b>“婚礼庆典”正在开发中!</b>
  </div>
  <div id="quxiao7" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel7">确定</p>
  </div>
</div>
<div class="weui-cells8" style="display:none;width:73%;height:165px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazai8" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:110px;line-height: 79px">
    <b>“生鲜超市”正在开发中!</b>
  </div>
  <div id="quxiao8" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancel8">确定</p>
  </div>
</div>
  <!--猜你喜欢-->
    <div class="like">
        <p class="you_like">猜你喜欢</p>
        <div class="merchant_list">
        	<c:forEach items="${pd}" var="pd">
	                <a href="api/h5KeHu/shop.do?user_shangjia_id=${pd.user_shangjia_id }" class="weui-media-boxt weui-media-box_appmsg1 delicious">
	                    <div class="weui-media-box__hd">
	                        <!-- <a href="api/h5KeHu/shop.do"><img class="weui-media-box__thumb" src="static/images/wmpc/nm.jpeg" alt=""></a> -->
	                        <c:if test="${ pd.logoImg != '' && pd.logoImg != null}">
				           		<img  style="max-width: 100%;height: 65px;" src="<%=basePath%>${pd.logoImg }" alt=""/>
				            </c:if>
				            <c:if test="${ pd.logoImg == '' || pd.logoImg == null}">
				           		<!-- <img src="static/images/wmpc/u132.png" alt=""/> -->
				           		<img  style="max-width: 100%;height: 65px;" class="weui-media-box__thumb" src="static/images/wmpc/nm.jpeg" alt="">
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
	                           <!--  <div class="weui-flex__item right" style="width:60px;float:right;">1.5km</div> -->
	                        </div>
	                        <div class="weui-flex distribute">
	                            <span>快餐配送</span>
	                            <span>网上超市</span>
	                            <span>免费配送</span>
	                        </div>
	                    </div>	                
               		</a>
               </c:forEach>
            <!-- <a href="api/h5KeHu/shop.do" class="weui-media-boxt weui-media-box_appmsg1">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" src="static/images/wmpc/nm.jpeg" alt="">
                </div>
                <div class="weui-media-box__bd">
                    <div class="weui-flex">
                        <div class="weui-flex__item tqf1" >好食人家</div>
                        <div class="weui-flex__item " style="text-align:right;color:#4CCF58;">营业中</div>
                    </div>
                    <div class="weui-flex " style="display: block;overflow: hidden;">
                        <div class="weui-flex__item" style="width:60px;float:left;">30元起送</div>
                        <div class="weui-flex__item distance" style="width:60px;float:left;">免费配送</div>
                        <div class="weui-flex__item right" style="width:60px;float:right;">1.5km</div>
                    </div>
                    <div class="weui-flex distribute">
	                    <span>快餐配送</span>
	                    <span>网上超市</span>
	                    <span>免费配送</span>
                    </div>
                </div>
            </a> -->
        </div>
    </div>		
	<!--底部间隙60px-->
	<div class="weui-flex clearfix">
		<div class="bot60"></div>
	</div>
</div>
	
<!--底部导航-->
<div class="weui-tabbar wy-foot-menu">
    <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/index.do'" class="weui-tabbar__item weui-bar__item--on">
        <div class="weui-tabbar__icon foot-menu-home"></div>
        <p class="weui-tabbar__label">首页</p>
    </a>
    <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/hdsq.do'"class="weui-tabbar__item">
        <div class="weui-tabbar__icon foot-menu-cart"></div>
        <p class="weui-tabbar__label">互动社区</p>
    </a>
    <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/dcpc.do'"class="weui-tabbar__item">
        <div class="weui-tabbar__icon  foot-menu-member"></div>
        <p class="weui-tabbar__label">打车拼车</p>
    </a>
    <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/wd.do'"class="weui-tabbar__item">
        <div class="weui-tabbar__icon foot-menu-car"></div>
        <p class="weui-tabbar__label">我的</p>
    </a>
</div>
<!-- 弹窗提示 begin -->
<div class="mask" style="display: none;"></div>
<div class="weui-cellss" style="display:none;width:73%;height:135px;position: fixed;top:220px;left:13%;background-color: #fff;z-index: 99999;border-radius: 5px;">
  <div id="xiazais" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center;height:79px;line-height:55px">
    	您还没有登录，请您去登录？
  </div>
  <div id="quxiaos" style="color: #000;width: 100%;height:56px;line-height:56px;text-align: center;">
    <p class="cancels">确定</p>
  </div>
</div>
<script src="static/js/wmpc/layer/layer.js"></script>
<!-- 弹窗提示 end -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=f2C9QaeY2zU9joj3Z34DG2gThH70Pwzl"></script>
<script type="text/javascript">
    $(function(){
      // 百度地图API功能
        var map = new BMap.Map("allmap");
        var point = new BMap.Point(116.331398,39.897445);
        function myFun(result){
            var cityName = result.name;
           // alert("当前定位城市:"+cityName);
            $("#baidu_geo").text(cityName)
        }
        var myCity = new BMap.LocalCity();
        myCity.get(myFun);

        //demo示例一到四 通过lass调取，一句可以搞定，用于页面中可能有多个导航的情况
       // $('.wrapper').navbarscroll();

    });
   
</script>
	<script>
		
	 $(function () {
        FastClick.attach(document.body);
      	 abc();
      	 checkSession();
    });
    
      	function abc(e1){
			$(e1).addClass("weui-bar__item--on").siblings().removeClass("weui-bar__item--on");
		}
		
	//判断session是否存在 做相应的提示
		function checkSession(){
			var respCode = "${respCode}";
			var tag = "${tag}";
			if(respCode=="01"){
				<%-- if(tag=="1"){
					layer.msg("登录成功！",{
    		            time:1500,//单位毫秒
    		            shade: [0.8, '#393D49'], // 透明度  颜色
    		            //style: 'background: rgba(216,100,125,0.9); color:#fff; border:none;',
    		            icon:1,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        });
    		        setTimeout(function(){
    		        	location.href="<%=basePath%>api/h5KeHu/index.do";
    		        },1500);
				} --%>
				
			}else{
		 			/* $(".mask").fadeIn();
			     	 $(".weui-cellss").fadeIn();
			     	 $(".cancels").click(function(){
			        	 $(".mask").fadeOut();
			        	 $(".weui-cellss").fadeOut();
			        	 window.location.href='api/h5KeHu/toLogin.do';
			      	});
		 */
			}
		}
	</script>
	<!--轮播图-->
	<script>
		$(".swiper-container").swiper({
			loop : true,
			autoplay : 3000
		});
	</script>
	<!--滚动文字-->
	<script>
		$(function() {
			$('.head_rt').liMarquee();
		})
	</script>
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
  $(function(){//司机端提示框
    $("#sijiS").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells2").fadeIn();
      $(".cancel").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells2").fadeOut();
      })
    })
  });
 /* $(function(){//旅游景点提示框
    $("#lyjd").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells3").fadeIn();
      $(".cancel3").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells3").fadeOut();
      })
    })
  });
  $(function(){//汽车服务提示框
    $("#qcfw").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells4").fadeIn();
      $(".cancel4").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells4").fadeOut();
      })
    })
  });
  $(function(){//休闲娱乐提示框
    $("#xxyl").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells5").fadeIn();
      $(".cancel5").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells5").fadeOut();
      })
    })
  });
  $(function(){//酒店宾馆提示框
    $("#jdbg").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells6").fadeIn();
      $(".cancel6").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells6").fadeOut();
      })
    })
  });
  $(function(){//婚礼庆典提示框
    $("#hlqd").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells7").fadeIn();
      $(".cancel7").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells7").fadeOut();
      })
    })
  });
  $(function(){//生鲜超市提示框
    $("#sxcs").click(function(){
      $(".mask").fadeIn();
      $(".weui-cells8").fadeIn();
      $(".cancel8").click(function(){
        $(".mask").fadeOut();
        $(".weui-cells8").fadeOut();
      })
    })
  })*/
</script>
</body>
</html>