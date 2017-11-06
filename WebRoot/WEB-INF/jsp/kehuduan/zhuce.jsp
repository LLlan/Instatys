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
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <title>注册</title>
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
		.log-input{
			border-right:none;
		}
		.img-ver1{
			border-left:1px solid #fff;
			margin-top:6px;
		}
		.i-ver {
		    background: url(static/images/wmpc/yanzhengma.png) no-repeat center right;
		    background-size: 25px;
		}
		.log-input{
			width:46%;
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
    <form class="main_box" id="sform" action="" method="post">
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div id="id" class="log-step">
                <span class="log-st-i i-user"></span>
                <input type="text" placeholder="手机号" class="log-input" maxlength="11" name="phone" id="phone">
                <input type="button"  class="img-ver1 weui-input yanzheng" id="yanzheng" value="获取验证码" onclick="clickButton(this)" >
            </div>
        </div>
        <%-- 正确的验证码 --%>
    	<input type="hidden" id="zhengqueyzm" name="zhengqueyzm" value="">
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step">
                <span class="log-st-i i-ver"></span>
                <input type="text" placeholder="验证码" class="log-input" id="code" name="code" style="border:none;outline:none;border-right: 0; width: 80%">
            </div>
        </div>
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step">
                <span class="log-st-i i-pwd"></span>
                <input type="password" placeholder="设置密码" class="log-input" id="loginPassword" name="loginPassword" style="border-right: 0; width: 80%">
            </div>
        </div>
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step">
                <span class="log-st-i i-pwd"></span>
                <input type="password" placeholder="确认密码" class="log-input" id="loginPassword1" name="loginPassword1" style="border-right: 0; width: 80%">
            </div>
        </div>
        <div class="login" style="background-color: #C7C6CC"><a style="cursor: text" href="javascript:void(0)" onclick="register();">注 册</a></div>
    </form>
</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script type="text/javascript">
	//验证手机格式的正则表达是
	var phoneReg=/^1[3-9]\d{9}$/;
    function clickButton(obj){
        var obj = $(obj);
        var phone=$("#phone").val();
        if(phone==""){
			layer.tips('请输入您的手机号码！', '#phone', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
        if(phoneReg.test(phone)){
        	 obj.attr("disabled","disabled");/*按钮倒计时*/
             var time = 60;
             var set=setInterval(function(){
                 obj.val(--time+"(s)");
             }, 1000);/*等待时间*/
             setTimeout(function(){
                 obj.attr("disabled",false).val("重新获取");/*倒计时*/
                 clearInterval(set);
             }, 60000);
             $.ajax({
           		type:"post",
           		url:"api/h5KeHu/getSms.do",
           		data:{
           			"phone":phone
           		},
           		dataType:"json",
           		success:function(data){
           			if(data.reqCode=='01'){
           				$("#zhengqueyzm").val(data.yanzhengma);
           			}else{
           				console.log("获取验证码失败");
           			}
           			//console.log("成功进入");
           		}
           	})
        }else{
        	layer.tips('手机号码格式不正确！', '#phone', {
        		  tips: [1, '#D9006C'],
        		  time: 3000
        	});
        	//console.log("手机号码格式不正确");
        }
    }
    $(".danxuan").click(function(){
        $(this).addClass("bgSelected");
        $(this).siblings(".danxuan").removeClass("bgSelected");
        $("#identity").val($(this).attr("title"));
       /* if($(".shanghu").hasClass("bgSelected")){
            $(".shangX").css("display","block");
        }else{
            $(".shangX").css("display","none");
        }*/
    });
   /* $(".danxuan").click(function(){
        if($(this).hasClass("bgSelected")){
            $(this).removeClass("bgSelected");
        }else{
            $(this).addClass("bgSelected");
        }
    })*/
    //点击注册
    function register(){
	   var phone=$("#phone").val();//手机号
	//   var userName=$("#userName").val();//用户名
	   var loginPassword=$("#loginPassword").val();//登录密码
	   var loginPassword1=$("#loginPassword1").val();//确认密码
	   var zhengqueyzm=$("#zhengqueyzm").val();//正确的验证码
	   var code=$("#code").val();//输入的验证码
	   //var identity=$("#identity").val();//身份
	   if(phone==""){
			layer.tips('手机号码不能为空', '#phone', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		 //判断手机号码是否合法
	   if(!phoneReg.test(phone)){
		   layer.tips('手机号码格式不正确,请正确输入手机号', '#phone', {
     		  tips: [1, '#D9006C'],
     		  time: 3000
     		});
		   return;
	   }
	   //判断是否获取验证码
	  if(zhengqueyzm==''){
		   layer.tips('请您点击获取验证码!', '#yanzheng', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
	   }
	   if(code==""){
			layer.tips('请您输入正确的验证码！', '#code', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		 //判断确认验证码是否合法
	   if(zhengqueyzm!=code){
		   layer.tips('您输入的验证码不正确！', '#code', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     	});
			return;
	   }
	   //判断用户名是否合法
	 /*   if(!isNaN(userName) || userName.length>20 || userName.length<0 || userName==''){
		   layer.tips('请注意用户名格式:不能为空、不能全为数字、长度0-20', '#userName', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
	   } */
	   //判断设置密码是否合法
	   if(loginPassword.length>20 || loginPassword.length<6){
		   layer.tips('请注意密码长度:长度0-20', '#loginPassword', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     	});
			return;
	   }
	   //判断确认密码是否合法
	   if(loginPassword1.length>20 || loginPassword1.length<6){
		   layer.tips('请注意密码长度:长度0-20', '#loginPassword1', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     	});
			return;
	   }
	   //判断确认密码是否一至
	   if(loginPassword!=loginPassword1){
		   layer.tips('两次密码输入不一致,请重新输入', '#loginPassword1', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     	});
			return;
	   }
	   //判断手机号码是否合法
	   if(!phoneReg.test(phone)){
		   layer.tips('手机号码格式不正确,请正确输入手机号', '#phone', {
     		  tips: [1, '#D9006C'],
     		  time: 3000
     		});
		   return;
	   }
	   //判断验证码输入是否正确
	   if(zhengqueyzm!=code){
		   layer.tips('验证码输入不正确,请重新输入', '#code', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
		   return;
	   }
	   //去执行注册
	   $.ajax({
    		type:"post",
    		url:"api/h5KeHu/register.do",
    		data:{
    			"phone":phone,
    			"loginPassword":loginPassword
    			/*,"identity":identity*/
    		},
    		dataType:"json",
    		success:function(data){
    			if(data.respCode=='01'){
    				layer.msg("注册："+data.respMsg,{//注册成功！
    		            time:3000,//单位毫秒
    		            shade: [0.8, '#393D49'],
    		            icon:6,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        })
//    		        location.href="api/h5KeHu/index.do";
    				//注册成功后进入登录页面
    				//location.href="api/h5KeHu/toLogin.do";
    				 setTimeout(function(){
    		        	location.href="<%=basePath%>api/h5KeHu/toLogin.do";
    		        },1500);
    			}else if(data.respCode=='00'){
    				layer.msg("注册："+data.respMsg,{
    		            time:3000,//单位毫秒
    		            shade: [0.8, '#393D49'],
    		            icon:2,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        });
    				//console.log(data.respMsg);
    			}
    		}
    	})
   }
   /*$("#userName").blur(function(){
	   var userName=$("#userName").val();//用户名
	   if(!isNaN(userName) || userName.length>20 || userName.length<0 || userName==''){
		   layer.tips('请注意用户名格式:不能为空、不能全为数字、长度0-20', '#userName', {
	     		  tips: [1, '#D9006C'],
	     		  time: 4000
	     	});
	   }
   });
   $("#loginPassword").blur(function(){
	   var loginPassword=$("#loginPassword").val();//登录密码
	   if(loginPassword.length>20 || loginPassword.length<6){
		   layer.tips('请注意密码长度:长度0-20', '#loginPassword', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     		});
			   return;
	   }
   })*/
   //进入登录页面
  /*  $("#toLogin").click(function(){
	   window.location.href='api/h5KeHu/toLogin.do';
   }); */
   //进入注册页面
   $("#toRegister").click(function(){
	   window.location.href='api/h5KeHu/toRegister.do';
   });

</script>
</body>
</html>