<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE >
<html>
<head>
    <title>修改支付密码</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
    	.backG .pswQ .input{
    		width:91%;
    	}
    	.backG .pswQ .input input{
    		width:16.9%;
    	}
    	.backG .pswQ .input input:nth-child(1){
    		margin-left:0;
    	}
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">修改支付密码</h1>
  <a href="javascript:history.go(-1)" class="icon1" style="margin-top:15px;"><i class="icon-angle-left "></i></a>
</div>

<!--头部结束-->
<div class="backG" style="margin-top:90px;">
    <div class="pswQ">
        <p class="sure-ps" style="font-size: 16px;color:#000;">请输入支付密码</p>
        <form action="api/h5KeHu/${msg }.do" id="Form" name="Form">
	        <div class="input" id="input">
	            <input id="beginBtn" type="password"  name="a" placeholder="" maxlength="1">
	            <input id="beginBtn" type="password"  name="b" placeholder="" maxlength="1">
	            <input id="beginBtn" type="password"  name="c" placeholder="" maxlength="1">
	            <input id="beginBtn" type="password"  name="d" placeholder="" maxlength="1">
	            <input id="beginBtn" type="password"  name="e" placeholder="" maxlength="1">
	            <input id="beginBtn" type="password"  name="f" placeholder="" maxlength="1">
	        </div>
		</form>
        <a href="javascript:void(0)" onclick="Next();"  class="sure sureM">下一步</a>
    </div>
</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
    /**
     * 模拟支付宝的密码输入形式
     */
    (function (window, document) {
        var active = 0,
                inputBtn = document.querySelectorAll('input');
        for (var i = 0; i < inputBtn.length; i++) {
            inputBtn[i].addEventListener('click', function () {
                inputBtn[active].focus();
            }, false);
            inputBtn[i].addEventListener('focus', function () {
                this.addEventListener('keyup', listenKeyUp, false);
            }, false);
            inputBtn[i].addEventListener('blur', function () {
                this.removeEventListener('keyup', listenKeyUp, false);
            }, false);
        }

        /**
         * 监听键盘的敲击事件
         */
        function listenKeyUp() {
            var beginBtn = document.querySelector('#beginBtn');
            if (!isNaN(this.value) && this.value.length != 0) {
                if (active < 5) {
                    active += 1;
                }
                inputBtn[active].focus();
            } else if (this.value.length == 0) {
                if (active > 0) {
                    active -= 1;
                }
                inputBtn[active].focus();
            }
            if (active >= 5) {
                var _value = inputBtn[active].value;
                if (beginBtn.className == 'begin-no' && !isNaN(_value) && _value.length != 0) {
                    beginBtn.className = 'begin';
                    beginBtn.addEventListener('click', function () {
                        calculate.begin();
                    }, false);
                }
            } else {
                if (beginBtn.className == 'begin') {
                    beginBtn.className = 'begin-no';
                }
            }
        }
    })(window, document);
    
       function Next(){
    	var beginBtn=$("#beginBtn").val();
	   if(beginBtn==""){
		   layer.tips('请注意设置支付密码长度为:6位', '#beginBtn', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     	});
			return;
	   }
		//判断设置密码是否合法
	  /*  if(beginBtn.length>5 || beginBtn.length<5){
		   layer.tips('请注意设置支付密码长度为:6位', '#beginBtn', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     	});
			return;
	   } */
    	
    	$("#Form").submit();
    }
</script>

<script src="static/js/wmpc/jquery-weui.js"></script>
</body>
</html>
