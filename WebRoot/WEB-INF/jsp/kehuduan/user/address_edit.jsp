<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/weui1.css">
    <link rel="stylesheet" href="static/css/wmpc/yahu.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet"  href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/city-picker.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <title>编辑地址</title>
     <style type="text/css">
    	.icon1 {	   
		    top: 15px;		   
		}
    </style>
</head>
<body>
<!--顶部-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">编辑地址</h1>
    <a href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left "></i></a>
</div>
	<!--主体-->
<form id="Form" action="api/h5KeHu/${msg }.do?shouhuo_address_id=${pd.shouhuo_address_id}&tag=${tag }&user_shangjia_id=${user_shangjia_id}" method="post" >
	
	<div class="container" >
	    <div class="weui-cells weui-cells_form wy-address-edit">
	        <div class="weui-cell">
	            <div class="weui-cell__hd"><label class="weui-label wy-lab">联系人 : </label></div>
	            <input type="text" name="linkmanName" id="linkmanName" value="${pd.linkmanName}" placeholder="联系人姓名"/>
	            <!-- <div class="weui-cell__bd"><input class="weui-input" type="number" pattern="[0-9]*" placeholder=""></div> -->
	        </div>
	        <div class="weui-cell">
	            <div class="weui-cell__hd"><label class="weui-label wy-lab">手机号 : </label></div>
	             <input type="text" name="phone" id="phone" value="${pd.phone}" maxlength="11" placeholder="联系手机号"/>
	        </div>
	       <%--  <div class="weui-cell">
	            <div class="weui-cell__hd"><span>所在地址：</span></div>
	            <div class="weui-cell__bd"><input class="weui-input" id="address"  name="address" value="${pd.address}" readonly="" data-code="420106" data-codes="420000,420100,420106"></div>
	        </div> --%>
	        <div class="weui-cell">
	            <div class="weui-cell__hd"><span>所在地址 : </span></div>
	            <div class="weui-cell__bd"><input class="weui-input wab"  name="address" value=" ${pd.address}" readonly="" data-code="420106" data-codes="420000,420100,420106"></div>
	        	<input type="hidden" value="${pd.latitude}" name="lat" class="lat" />
	        	<input type="hidden" value="${pd.longitude}" name="lng" class="lng" />
	        </div>
	        <div class="weui-cell">
	           <!--  <div class="weui-cell__hd"><label class="weui-label wy-lab"></label></div> -->
	            <div class="weui-cell__bd">
	                <!--<textarea class="weui-textarea" placeholder="国贸路景瑞大厦A座13楼C室"></textarea>-->
	                <!-- <input class="weui-input" type="text" placeholder=""> -->
	                <span>详细地址 :</span>
	                <input type="text" value="${pd.detailAddress}" style="width: 75%;" name="detailAddress" id="detailAddress" placeholder="输入详细地址" id="baidu_geo"/>
	                <%-- <input type="text" value="${pd.detailAddress}" name="detailAddress" id="detailAddress" placeholder="输入详细地址" id="baidu_geo"/> --%>
	            </div>
	        </div>
	        <div class="weui-cell">
	            <div class="weui-cell__hd"><label class="weui-label wy-lab">身份 :</label></div>
	            <div class="weui-cell__bd">
	                <!-- <span class="add onx">先生</span>
	                <span class="add">女士</span> -->
		            <input type="radio" class="form-con1" name="identity" value="先生" <c:if test="${pd.identity=='先生' || pd.identity==''}">checked="checked"</c:if>/>先生
		            &nbsp;&nbsp;
		            <input type="radio" class="form-con2" name="identity" value="女士" <c:if test="${pd.identity=='女士' }">checked="checked"</c:if>/>女士
	            </div>
	            
	        </div>
	        <div class="weui-cell">
	            <div class="weui-cell__hd"><label class="weui-label wy-lab">标签 :</label></div>
	            <div class="weui-cell__bd">
	                <!-- <span class="add onx">家</span>
	                <span class="add">公司</span>
	                <span class="add">学校</span> -->
	                <input type="radio" class="form-con1" name="lable" value="家" <c:if test="${pd.lable=='家' || pd.lable==''}">checked="checked"</c:if>/>家 
		            &nbsp;&nbsp;
		            <input type="radio" class="form-con2" name="lable" value="公司" <c:if test="${pd.lable=='公司' }">checked="checked"</c:if>/>公司
		            &nbsp;&nbsp;
		            <input type="radio" class="form-con2" name="lable" value="学校" <c:if test="${pd.lable=='学校' }">checked="checked"</c:if>/>学校
	            </div>
	        </div>
	        <div class="weui-cell weui-cell_switch" style="padding:10px 15px;">
	            <div class="weui-cell__hd">设为默认地址 :</div>&nbsp;&nbsp;
	            	<input type="radio" class="form-con1" name="isDefault" value="0" <c:if test="${pd.isDefault=='0' || pd.isDefault==''}">checked="checked"</c:if>/>否
		            &nbsp;&nbsp;
		            <input type="radio" class="form-con2" name="isDefault" value="1" <c:if test="${pd.isDefault=='1' }">checked="checked"</c:if>/>是&nbsp;&nbsp;
	            <!-- <div class="weui-cell__ft">
	            	<input class="weui-switch" name="isDefault" type="checkbox">
	            </div> -->
	        </div>
	    </div>
	</div>
	<iframe id="mapPage" width="100%" height="" frameborder=0 style="display: none;"
            src="http://apis.map.qq.com/tools/locpicker?search=1&type=1&policy=1&coordtype=5&backurl=http://3gimg.qq.com/lightmap/components/locationPicker2/back.html&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp">
    </iframe>
</form>
    <div class="weui-btn-area">
        <a class="weui-btn weui-btn_primary" href="javascript:void(0)" onclick="edit();" id="showTooltips">保存地址</a>
        <a href="api/h5KeHu/address.do?tag=2" class="weui-btn weui-btn_warn">取消</a>
    </div>
<script>
	//验证手机格式的正则表达是
	var phoneReg=/^1[3-9]\d{9}$/;
	var mobileRule=/^(1[3|4|5|8])[0-9]{9}$/;
	//保存
	function edit(){
		var linkmanName=$("#linkmanName").val();//联系人
		var phone=$("#phone").val();//联系电话
		var address=$("#address").val();//所有小区
		var detailAddress=$("#detailAddress").val();//详细地址
		var identity=$("#identity").val();//身份
		var lable=$("#lable").val();//标签
		if(linkmanName==""){//乘客姓名
			layer.tips('请输入您的真实姓名！', '#linkmanName', {
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
	  
		$("#Form").submit();
	}

    $(function () {
        FastClick.attach(document.body);
    });
</script>
<script>
    $("#address").cityPicker({
        title: "选择出发地",
        onChange: function (picker, values, displayValues) {
            console.log(values, displayValues);
        }
    });
    $(function(){
        $(".weui-cell__bd span.add").click(function(){
            $(this).addClass("onx").siblings(".add").removeClass("onx");
        })
    })
</script>
<script>
    $(function(){
        $("#mapPage").height($(window).height());
    });
    $(".wab").click(function(){
        $("#mapPage").show();
        $(".container").hide();
         $("#header").hide();
        window.addEventListener('message', function(event) {
            // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
            var loc = event.data;
            if (loc && loc.module == 'locationPicker') {//防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
                $(".wab").val(loc.poiaddress);
              /*   console.log(loc.latlng.lat);
                console.log(loc.latlng.lng); */
                $(".lng").val(loc.latlng.lng);
                 $(".lat").val(loc.latlng.lat);
                $("#mapPage").hide();
                $(".container").show();
                $("#header").show();
            }
        }, false);
    });

</script>
</body>
</html>
