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
    <title>发布路线</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/foundation.min.css" >
    <link rel="stylesheet" href="static/css/wmpc/foundation-datepicker.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">

    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">

    <style>
        .fabu01{
            width:94%;height:54px;line-height:54px;border:1px solid #e7e7e7;color:#000;font-size:16px;border-radius: 25px;margin-left:3%;margin-top:26px;background-color: #fff;;
        }
        .fabu01>span{
            display:inline-block;width:26%;text-align:center;height:54px;line-height: 54px;margin-left:3%;;
        }
        .fabu01>input{
            width:63%;height:34px;line-height: 34px;font-size: 16px;;
        }
        .fabu01-1{
            margin-top:10px;
        }

    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->

<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">发布路线</h1>
  <a href="javascript:history.go(-1)" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<!--主体-->
	<form id="Form" action="api/h5KeHu/${msg}.do" method="post" style="margin-top:85px;">
		<div class="fabu01" style="">
		    <span>乘客姓名</span>
		    <input type="text" name="userName" id="userName" value="${pd.userName}" maxlength="6" placeholder="乘客真实姓名"/>
		</div>
		<div class="fabu01 fabu01-1" style="">
		    <span>联系电话</span>
		    <input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="11" placeholder="乘客手机号"/>
		</div>
		<div class="fabu01 fabu01-1 sang_Calender" style="">
		    <span>出发时间</span>
		    <input placeholder="xx年xx月xx时xx分" class="yesc weui-input" readonly="readonly" name="departureTime" id="time-format" type="text" />
		</div>
		<div class="fabu01 fabu01-1" style="">
		    <span>乘车人数</span>
		    <input type="text" value="${pd.userNum}" name="userNum" maxlength="2" id="userNum" placeholder="输入要乘坐的人数"/>
		</div>
		<div class="fabu01 fabu01-1" style="">
		    <span>出发城市</span>
		    <input type="text" value="${pd.departureCity}" name="departureCity" id="departureCity" style="background-color: #fff;" placeholder="输入出发城市" id="address1"  data-code="420106" data-codes="420000,420100,420106"/>
		</div>
		<div class="fabu01 fabu01-1" id='' style="">
		    <span>出发地点</span>
		    <input type="text" value="${pd.departurePlace}" name="departurePlace" id="departurePlace" placeholder="输入详细出发地点" id="baidu_geo"/>
		</div>
		<div class="fabu01 fabu01-1" style="">
		    <span>到达城市</span>
		    <input style="background-color: #fff;" type="text" name="arrivalCity" id="arrivalCity" value="${pd.arrivalCity}" placeholder="输入所到达的城市" id="address2"  />
		</div>
		<div class="fabu01 fabu01-1" style="">
		    <span>到达地点</span>
		    <input type="text" value="${pd.destination}" name="destination" id="destination" placeholder="输入详细所到达的目的地"/>
		</div>
		<div class="fabu01 fabu01-1" style="">
		    <span>拼车费</span>
		    <input type="text" value="${pd.carpoolFee}" name="carpoolFee" id="carpoolFee" maxlength="5" placeholder="输入你理想中的拼车费"/>
		</div>
	<div style="width:94%;color:red;font-size: 12px;margin-top:48px;margin-bottom:3px;margin-left:3%;text-align:center;">
	    提醒：请确认所填信息无误后发布，以免影响您的行程！
	</div>
	<div style="width:94%;color:red;font-size: 12px;padding-bottom:78px;margin-left:3%;text-align:center;">
	    <a href="javascript:void(0)" onclick="fabu()" style="display: block;width:100%;height:54px;line-height: 54px;background-color: #0284fc;border-radius: 25px;color:#fff;font-size: 18px;">确认发布</a>
	</div>
</form>
<div class='right' style="display: none;width: 100%;">
    <header class ='title1 top'>
        <a href='javascript:history.go(-1);' id='back'>返回</a>
        <div>地点搜索</div>
    </header>
    <div class='top' style='height:40px;' >
        <div style='margin-top:2px'>
            <input id='search' value=''/>
        </div>
        <img id='searchButton' src="static/images/wmpc/search.gif"/>
    </div>
    <div id="searchResult"></div>
</div>
<div id='locating' style='display:none'>
    <img src='static/images/wmpc/loading (2).gif'/><div>定位中,请稍候static.</div>
</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/foundation-datepicker.js"></script>
<script src="static/js/wmpc/foundation-datepicker.zh-CN.js"></script>
<script src="static/js/wmpc/city-picker.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery-weui.js"></script>
<script type="text/javascript" src='static/js/wmpc/search.js'></script>

<script src="static/js/wmpc/layer/layer.js"></script>
<script>
    $("#time-format").datetimePicker({
        title: '自定义格式',
        yearSplit: '-',
        monthSplit: '-',
        dateSplit: '',
        times: function () {
            return [  // 自定义的时间
                {
                    values: (function () {
                        var hours = [];
                        for (var i=0; i<24; i++) hours.push(i > 9 ? i : '0'+i);
                        return hours;
                    })()
                },
                {
                    divider: true,  // 这是一个分隔符
                    content: ':'
                },
                {
                    values: (function () {
                        var minutes = [];
                        for (var i=0; i<59; i++) minutes.push(i > 9 ? i : '0'+i);
                        return minutes;
                    })()
                },
                {
                    divider: true,  // 这是一个分隔符
                    content: ''
                }
            ];
        },
        onChange: function (picker, values, displayValues) {
            console.log(values);
        }
    });
</script>
<script>
    $(function(){
        $('#demo-2').fdatepicker({
            format: 'yyyy-mm-dd hh:ii',
            startDate:new Date(),
            pickTime: true
        });
    })

</script>
<script>
	//验证手机格式的正则表达是
	var phoneReg=/^1[3-9]\d{9}$/;
	var mobileRule=/^(1[3|4|5|8])[0-9]{9}$/;
	//保存
	function fabu(){
		var phone=$("#phone").val();
		var userName=$("#userName").val();
		var departureTime=$("#departureTime").val();//出发时间
		var userNum=$("#userNum").val();//可乘人数
		var departureCity=$("#departureCity").val();//出发城市
		var departurePlace=$("#departurePlace").val();//出发地
		var arrivalCity=$("#arrivalCity").val();//到达城市
		var destination=$("#destination").val();//目的地
		var carpoolFee=$("#carpoolFee").val();//拼车费
		if(userName==""){//乘客姓名
			layer.tips('请输入您的真实姓名！', '#userName', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		if(phone==""){//手机号码
			layer.tips('请您输入正确的手机号码！', '#phone', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		 //判断手机号码是否合法
	   if(!phoneReg.test(phone)){
		   layer.tips('手机号码格式不正确！', '#phone', {
     		  tips: [1, '#D9006C'],
     		  time: 3000
     		});
		   return;
	   }
	   if(departureTime==""){//请选择出发时间
			layer.tips('请您选择出发时间！', '#departureTime', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
	   if(userNum==""){
			layer.tips('请您输入可乘人数！', '#userNum', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
	   if(departureCity==""){
			layer.tips('请您选择出发城市！', '#departureCity', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
	   if(departurePlace==""){
			layer.tips('请您输入详细出发地点！', '#departurePlace', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
	   if(arrivalCity==""){
			layer.tips('请您选择到达城市！', '#arrivalCity', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
	   if(destination==""){
			layer.tips('请您输入详细目的地点！', '#destination', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
	   if(carpoolFee==""){
			layer.tips('请您输入拼车费！', '#carpoolFee', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		$("#Form").submit();
	}
	
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<script>
    $("#address1").cityPicker({
        title: "选择出发地",
        onChange: function (picker, values, displayValues) {
            console.log(values, displayValues);
        }
    });
    $("#address2").cityPicker({
        title: "选择出发地",
        onChange: function (picker, values, displayValues) {
            console.log(values, displayValues);
        }
    });

</script>

<!-- <script>
    function getLocation(){
        if (navigator.geolocation){
            navigator.geolocation.getCurrentPosition(showPosition,showError);
        }else{
            alert("浏览器不支持地理定位。");
        }
    }

    function showPosition(position){
        $("#latlon").val("纬度:"+position.coords.latitude +'，经度:'+ position.coords.longitude);
        var latlon = position.coords.latitude+','+position.coords.longitude;
        //baidu
        var url = "http://api.map.baidu.com/geocoder/v2/?ak=C93b5178d7a8ebdb830b9b557abce78b&callback=renderReverse&location="+latlon+"&output=json&pois=0";
        $.ajax({
            type: "GET",
            dataType: "jsonp",
            url: url,
            beforeSend: function(){
                $("#baidu_geo").val('正在定位static.');
            },
            success: function (json) {
                if(json.status==0){
                    $("#baidu_geo").val(json.result.formatted_address);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $("#baidu_geo").val(latlon+"地址位置获取失败");
            }
        });
    }

    function showError(error){
        switch(error.code) {
            case error.PERMISSION_DENIED:
                alert("定位失败,用户拒绝请求地理定位");
                break;
            case error.POSITION_UNAVAILABLE:
                alert("定位失败,位置信息是不可用");
                break;
            case error.TIMEOUT:
                alert("定位失败,请求获取用户位置超时");
                break;
            case error.UNKNOWN_ERROR:
                alert("定位失败,定位系统失效");
                break;
        }
    }

    getLocation();

    /*navigator.geolocation.getCurrentPosition(function (position) {
     var lat = position.coords.latitude;
     var lon = position.coords.longitude;
     var point = new BMap.Point(lon, lat);  // 创建坐标点
     // 根据坐标得到地址描述
     var myGeo = new BMap.Geocoder();
     myGeo.getLocation(point, function (result) {
     var city = result.addressComponents.city;
     var provinceName = result.addressComponents.province;
     var districtName = result.addressComponents.district;
     $('#baidu_geo').html(districtName);
     });
     });
*/

</script> -->
<!--<script type="text/javascript">
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

</script>-->
</body>
</html>
