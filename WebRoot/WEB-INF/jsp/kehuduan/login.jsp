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
    <link rel="stylesheet" href="<%=basePath%>static/css/wmpc/yahu.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/wmpc/index.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/wmpc/font-awesome.css">
    <script src="<%=basePath%>static/js/wmpc/jquery-2.1.4.js"></script>
    <link rel="stylesheet" href="static/css/wmpc/yahu.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    
    <title>登录</title>
    <style>
        input{
            border: none;
            outline: none;
        }
        .main{
        	display:block;
        }
        .main_box_top {
		    width:100%;
		}
		.main_box {
		    margin: 20% auto auto;
		}
		.main .main_box_top .logo {
		    width: 80px;
		    height: 80px;
		    margin: 0 auto;
		}
		.next div {
		    font-size: 1em;
		    color: #fff;
		}
		.log-st-i {
		    width: 40px;
		}
		.log-input {
		    padding-left: 10px;
		}
		.i-pwd {
		    background: url(static/images/wmpc/mima.png) no-repeat center right;
		    background-size: 22px;
		}
    </style>
</head>
<body ontouchstart style="background: url('static/images/wmpc/bg.png') no-repeat;background-size: 100%;">
<div id="header">
    <a style="cursor: text" href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left cancel"></i></a>
</div>
<div class="main">
    <div class="main_box_top">
        <div class="logo">
            <img src="static/images/wmpc/dog.png" alt=""/>
        </div>
    </div>
    <form id="login_from" class="main_box" action="">
        <div class="login-star clearfix" style="margin-bottom: 10%;">
            <div class="log-step">
                <span class="log-st-i i-user"></span>
                <input type="text" placeholder="输入手机号" class="log-input" maxlength="11" id="login_phone" name="login_phone" style="border-right: 0; width: 80%">
            </div>
        </div>
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step">
                <span class="log-st-i i-pwd"></span>
                <input type="password" placeholder="输入密码" id="loginPassword" class="log-input" name="loginPassword" style="border-right: 0;width: 80%">
            </div>
        </div>
        <div class="next" style=""overflow:hidden;>
            <div class="check" style="float:left">
                <input type="checkbox" style="position:relative;top:2px;left:0;"/>下次自动登录
            </div>
            <div class="forget" style="float:right">
                <a style="cursor: text" href="api/h5KeHu/wangjimima.do">忘记密码?</a>
            </div>
        </div>
        <div class="login" id="login" style="background-color: #C7C6CC"><a style="cursor: text">登 录</a></div>
        <div class="zhuce" style="text-align: center;height: 30px;line-height:30px;">
            <a href="javascript:void(0)" id="toRegister" style="color: #fff;cursor: text;font-size:16px;">— 注 册 —</a>
        </div>
    </form>
</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script type="text/javascript">
	//登录
	var mobileRule=/^(1[3|4|5|8])[0-9]{9}$/;
	$("#login").click(function(){
		var login_phone=$("#login_phone").val();
		var loginPassword=$("#loginPassword").val();
		if(login_phone==""){
			layer.tips('登录账号不能为空', '#login_phone', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		if (!mobileRule.test(login_phone)) {
			layer.tips('手机号码格式不正确!', '#login_phone', {
     		  tips: [1, '#D9006C'],
     		  time: 3000
	     	});
			return;
		}
		if(loginPassword==""){
			layer.tips('密码不能为空', '#loginPassword', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		} 
		if(loginPassword.length>20 || loginPassword.length<6){//判断密码是否合法
		  layer.tips('请注意密码长度:长度6-20', '#loginPassword', {
		   		  tips: [1, '#D9006C'],
		   		  time: 2000
		   	});
			return;
		 }
		$.ajax({
    		type:"post",
    		url:"api/h5KeHu/login.do",
    		data:{
    			"login_phone":login_phone,
    			"loginPassword":loginPassword
    		},
    		dataType:"json",
    		success:function(data){
    			if(data.respCode=='01'){//登录成功！
    			/* 	layer.msg(""+data.respMsg,{
    					skin: 'layui-layer-molv',
    		            time:2000,//单位毫秒
    		            shade: [0.8, '#393D49'], // 透明度  颜色
    		            title:'温馨提示!',
    		            //style: 'background: rgba(216,100,125,0.9); color:#fff; border:none;',
    		            icon:6,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        })  */
    		        setTimeout(function(){
    		        	location.href="<%=basePath%>api/h5KeHu/index.do";
    		        },1500);
    				/* location.href="api/h5KeHu/index.do"; */
    			}else if(data.respCode=='00'){//登录失败！
    				layer.msg(""+data.respMsg,{
    					skin: 'layui-layer-molv',
    		            time:2000,//单位毫秒
    		            shade: [0.8, '#393D49'], // 透明度  颜色
    		            title:'温馨提示!',
    		            //style: 'background: rgba(216,100,125,0.9); color:#fff; border:none;',
    		            icon:5,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        })
    				//console.log(data.respMsg);
    			}
    		}
    	})
	});
	//进入登录页面
	$("#toLogin").click(function(){
		   window.location.href='api/h5KeHu/toLogin.do';
	});
	//进入注册页面
	$("#toRegister").click(function(){
		   window.location.href='api/h5KeHu/toRegister.do';
	});
	//点击忘记密码
	function toMissPassword(){
		window.location.href="api/shangHu/toLoginMissPassword1.do";
	}

</script>
</body>
</html>