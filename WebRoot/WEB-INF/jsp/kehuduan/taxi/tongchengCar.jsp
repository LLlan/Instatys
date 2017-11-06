<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE >
<html>
<head>
    <title>同城打车</title>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
	<link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" type="text/css" href="static/css/wmpc/location.css">
    <script type="text/javascript" src='https://webapi.amap.com/maps?v=1.3&plugin=AMap.Geolocation,AMap.ToolBar,AMap.Geocoder,AMap.PlaceSearch,AMap.Autocomplete,AMap.Driving&key=4d1d0eb988e48fd810cc099266971e22'></script>
<script type="text/javascript">
	//保存
	function hujiao(){
		var departurePlace=$("#origin").html();//出发地
		var destination=$("#destination").html();//目的地
		var inp1=$("#inp1").val();
		var inp2=$("#inp2").val();
		var inp3=$("#inp3").val();
		var inp4=$("#inp4").val();
		var latitude_longitude_start = inp1+","+inp2;
		var latitude_longitude_end = inp3+","+inp4;
		$("#departurePlace").val(departurePlace);
		$("#destinations").val(destination);
		$("#inps1").val(inp1);
		$("#inps2").val(inp2);
		$("#latitude_longitude_start").val(latitude_longitude_start);
		$("#latitude_longitude_end").val(latitude_longitude_end);
		$("#inps3").val(inp3);
		$("#inps4").val(inp4);
		if(departurePlace=="你的位置"){
			layer.tips('请您选择出发地！', '#origin', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		if(destination=="你要去哪儿"){
			layer.tips('请您选择目的地！', '#destination', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		$("#FORM").submit();
	}
	
</script>
<style type="text/css">
	.bottom {
            color: grey;
            text-align: left;
            height:45px;
            line-height:40px;
            width: 100%;
            position: absolute;
            border-top:none;
            background-color: #f8f8f8;
        }
        .start{
            bottom:105px;
        }
        .map{
            bottom:150px;
        }
        .bottomright {
            width:92%;
            margin:3px auto;;
            left: 35px;
            border-bottom:1px solid #ccc;
        }
        .bottom img {
            margin-right: 15px;
            margin-left:0;
            float: left;
            width: 10px;
            margin-top: 14px;
        }
        .end {
    		bottom:60px;
		}
		 .right input {
            color: #333;
        }
</style>
</head>
<body ontouchstart onload='startLocate()'>
<!--主体-->
<!--头部开始-->

<!--头部结束-->
<!--主体-->
<!--<div class="dacheOrder">
    <a href="" style=";display:block;"><div class="orderBox anquanchu">
        <h3 class="anquan">安全出行，我们一直在您身边</h3>
    </div></a>
    <a href="" style=";display:block;padding-top:10px;"><div class="orderBox dizhi12">
        <ul>
            <li>出发地点</li>
            <li>您要去哪</li>
        </ul>
    </div></a>
    <div class="yanzheng" style="background-color: #C7C6CC;width:100%;"><a href="tongchenghujiao.html" style="width:100%;margin:10px auto;border-radius: 5px;">呼叫</a></div>
</div>-->
	<div  style="width:100%;height:100%;position:absolute;overflow:hidden;">
	    <div class='wrap'  style="width:200%;height:100%;position:absolute">
	        <div class='left'>
	            <!--<header class ='title1 top'></header>-->
	         
	            <div id="header" style="background-color: #068dff;">
				  <h1 class="title" style="font-size:17px;">同城打车</h1>
				  <a href="api/h5KeHu/dcpc.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
				</div>
	            
	            <div class = 'info top' style="border-bottom: none;margin-top: 20px;">
	            </div>
	            <div id="container" class="map" tabindex="0" style="margin-top: 20px;"></div>
	            <div class='bottom start'  >
	                <div class='bottomright' >
	                <img src="static/images/wmpc/hotNew.png" />
	                    <div id='origin'>你的位置</div>
	                    <input type="text" id="inp1" name="inp1" style="display:none;"/>
                    	<input type="text" id="inp2" name="inp2" style="display:none;"/>
	                </div>
	            </div>
	            <div class='bottom end'>
	                <div class='bottomright' >
	                <img src="static/images/wmpc/hotNewRed.png" />
	                    <div id='destination'>你要去哪儿</div>
	                    <input type="text" id="inp3" name="inp3" style="display:none;"/>
                    	<input type="text" id="inp4" name="inp4" style="display:none;"/>
	                </div>
	            </div>
	            <div class='middle'>
	                <div class="yanzhen" ><a href="javascript:void(0);" onclick="hujiao();" >呼叫</a></div>
	            </div>
	            <!-- <button id='driving'>开车去</button> -->
	        </div>
	        <div class='right'>
	            <header class ='title1 top'>
	                <a href='api/h5KeHu/tongchengCar.do' id='back'>返回</a>
	                <div>地点搜索</div>
	            </header>
	            <div class='top' style='height:40px;margin-top:20px;'>
	                <div style='margin-top:2px'>
	                    <input id='search' value=''/>
	                </div>
	                <img id='searchButton' src="<%=basePath%>static/images/wmpc/search.gif"/>
	            </div>
	            <div id="searchResult" style="top:94px;"></div>
	        </div>
	        <div id='locating' style='display:none'>
	            <img src='static/images/wmpc/loading (2).gif'/><div>定位中,请稍候...</div>
	        </div>
	    </div>
	</div>

<form action="api/h5KeHu/${msg}.do" method="get" id="FORM">
	<input type="hidden" id="departurePlace" name="departurePlace">
	<input type="hidden" id="destinations" name="destinations">
	<input type="hidden" id="latitude_longitude_start" name="latitude_longitude_start">
	<input type="hidden" id="inps1" name="inps1">
	<input type="hidden" id="inps2" name="inps2">
	<input type="hidden" id="latitude_longitude_end" name="latitude_longitude_end">
	<input type="hidden" id="inps3" name="inps3">
	<input type="hidden" id="inps4" name="inps4">
</form>
<script type="text/javascript">
    // 创建地图
    var map = new AMap.Map('container', {
        zoom:17
    });

    // 給地图添加缩放工具条,默认显示在右下角
    var toolBar = new AMap.ToolBar({});
    map.addControl(toolBar);

    //起点（用户位置）的marker标记
    var startMarker = new AMap.Marker({
        content:"<img style='width:19px;height:32px' src='static/images/wmpc/starts.png'/>",
        offset:new AMap.Pixel(-10,-32)
    })
    //目的地的marker标记
    var endMarker = new AMap.Marker({
        content:"<img style='width:19px;height:32px' src='static/images/wmpc/ends.png'/>",
        offset:new AMap.Pixel(-10,-32)
    })
    //创建驾车路线规划组件
    var driving = new AMap.Driving({
        map:map,
        hideMarkers:true
    });

    var  wrap= document.getElementsByClassName('wrap')[0];
    //显示控制，执行后显示地图页面
    var showLeftView = function(){
        AMap.event.removeListener(placeSearch.listElementClickHandler);
        AMap.event.removeListener(autoComplete.selectHandler);
        placeSearch.clear();
        wrap.className = 'wrap';
    }
    //显示控制，执行后显示搜索页面
    var showRightView = function(onShowed){
        wrap.className = 'wrap rightShow';
    }
    //点击返回，页面由搜索页面返回显示页面
    AMap.event.addDomListener(document.getElementById('back'),'click',showLeftView);

</script>
<script type="text/javascript" src='static/js/wmpc/locate.js'></script>
<script type="text/javascript" src='static/js/wmpc/search.js'></script>
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
</body>
</html>
