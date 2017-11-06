<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/yahu.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <title>忘记密码</title>
    <style>
        input{
            border: none;
            outline: none;
        }
    </style>
</head>
<body ontouchstart style="background: url('static/images/wmpc/bg.png') no-repeat;background-size: 100%;">
<div id="header">
    <a style="cursor: text;" href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left cancel"></i></a>
</div>
<div class="main">
    <div class="main_box_top">
        <div class="logo">
            <img src="static/images/wmpc/dog.png" alt=""/>
        </div>
    </div>
    <form class="main_box" action="" id="myForm">
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step" >
                <span class="log-st-i i-user"></span>
                <input type="text" placeholder="手机号" class="log-input" value="${phone}" name="phone" id="phone" maxlength="11" name="mobile" style="border-right: none;width: 80%">
            </div>

        </div>
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step">
                <span class="log-st-i i-ver"></span>
                <%-- 正确的验证码 --%>
    			<input type="hidden" id="zhengqueyzm" name="zhengqueyzm" value="">
                <input type="text" placeholder="验证码" name="shuruyzm" id="shuruyzm" class="log-input" name="mobile" id="mobile">
                <input type="button" class="img-ver1 weui-input yanzheng" id="yanzheng" value="获取验证码" onclick="clickButton(this)">
            </div>
        </div>
        <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step">
                <span class="log-st-i i-pwd"></span>
                <input type="password" placeholder="输入新密码" name="newPassword" id="newPassword" class="log-input" name="mobile" style="border-right: 0;width: 80%">
            </div>
        </div>
        <div class="login" style="background-color: #C7C6CC"><a style="cursor: text;" href="javascript:void(0)" onclick="wancheng();">确认修改</a></div>
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
        alert(phone)
        if(phoneReg.test(phone)){
        	obj.attr("disabled","disabled");/*按钮倒计时*/
            var time = 60;
            var set=setInterval(function(){
                obj.val(--time+"(s)");
            }, 1000);/*等待时间*/
            setTimeout(function(){
                obj.attr("disabled",false).val("请您重新获取验证码");/*倒计时*/
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
          				layer.tips('获取验证码失败,倒计时结束后,点击重新获取', '#yanzheng', {
                  		  tips: [1, '#D9006C'],
                  		  time: 3000
                  		});
          			}
          		}
          	});
        }else{
        	layer.tips('请您输入正确手机号码！', '#phone', {
        		  tips: [1, '#D9006C'],
        		  time: 3000
        	});
        }
    }
    //点击完成操作
	function wancheng(){
		var phone=$("#phone").val();//手机号
		var newPassword=$("#newPassword").val();
		var zhengqueyzm=$("#zhengqueyzm").val();//正确的验证码
		var shuruyzm=$("#shuruyzm").val();//输入的验证码
		 if(phone==""){
			layer.tips('请输入您的手机号码！', '#phone', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		 //判断手机号码是否合法
	   if(!phoneReg.test(phone)){
		   layer.tips('手机号码格式不正确!', '#phone', {
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
	   if(shuruyzm==""){
			layer.tips('请您输入正确的验证码！', '#shuruyzm', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		 //判断确认验证码是否合法
	   if(zhengqueyzm!=shuruyzm){
		   layer.tips('您输入的验证码不正确！', '#shuruyzm', {
	     		  tips: [1, '#D9006C'],
	     		  time: 2000
	     	});
			return;
	   }
		if(newPassword.length > 20 || newPassword.length < 6){
			layer.tips('请输入密码长度为:6~20位密码！', '#newPassword', {
	      		  tips: [1, '#D9006C'],
	      		  time: 3000
	      	});
	 		return;
		}
		//$("#myForm").submit();
		//去执行修改密码
	   $.ajax({
    		type:"post",
    		url:"api/h5KeHu/changepwd.do",
    		data:{
    			"phone":phone,
    			"newPassword":newPassword
    		},
    		dataType:"json",
    		success:function(data){
    			if(data.respCode=='01'){
    				/* layer.msg(" "+data.respMsg,{//修改成功！
    		            time:3000,//单位毫秒
    		            shade: [0.8, '#393D49'],
    		            icon:2,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        }) */
    		        layer.confirm('恭喜您密码修改成功!'+data.respMsg, {
       						skin: 'layui-layer-molv',
  			                btn: ['下一步'],
  			                icon: 6,  // icon
  			                title:'温馨提示!',
  			            	},function () {
  			            		window.location.href='api/h5KeHu/toLogin.do';
  			            	});
    		      /*   layer.alert('墨绿风格，点击确认看深蓝', {
					  skin: 'layui-layer-molv' //样式类名 自定义样式
					  ,closeBtn: 1  // 是否显示关闭按钮
					  ,anim: 1 //动画类型
					  ,btn: ['重要','奇葩'] //按钮
					  ,icon: 6  // icon
					  ,yes:function(){
					    layer.msg('按钮1')
					  }
					  ,btn2:function(){
					    layer.msg('按钮2')
					  }}); */
					 //修改成功后进入登录页面
    				//location.href="api/h5KeHu/toLogin.do";
    			}else if(data.respCode=='00'){
    				layer.msg("	"+data.respMsg,{//该账号还未注册，请您去注册！
    		            time:3000,//单位毫秒
    		            shade: [0.8, '#393D49'],
    		            icon:2,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        })
    			}
    		}
    	})
	}
	//进入登录页面
	$("#toLogin").click(function(){
		   window.location.href='api/h5KeHu/toLogin.do';
	});
	//进入注册页面
	$("#toRegister").click(function(){
		   window.location.href='api/h5KeHu/toRegister.do';
	});
   </script>
</body>
</html>