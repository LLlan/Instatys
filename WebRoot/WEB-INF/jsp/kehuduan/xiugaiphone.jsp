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
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <title>修改手机号</title>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div class="wy-header header">
    <div class="wy-header-icon-back"><a href="javascript:history.go(-1)"></a></div>
    <div class="wy-header-title">修改手机号</div>
</div>
<!--头部结束-->
<!--<div>
    <input type="text" placeholder="手机号"/>
    <input type="text" placeholder="请输入验证码"/>
    <a href="" class="sure">确认修改</a>
</div>-->
<form id="uploadForm" class="main_box" action="" method="post">
        <div class="login-star clearfix" id="id">
            <div class="log-step">
                <input type="text" placeholder="手机号" class="log-input" maxlength="11" name="phone" id="phone" >
                <input type="button" class="img-ver1 weui-input yanzheng" id="yanzheng"  value="获取验证码" onclick="clickButton(this)">
            </div>
            
        </div>
         <div class="login-star clearfix" style="margin-bottom: 5%;">
            <div class="log-step">
                <span class="log-st-i i-ver"></span>
                <%-- 正确的验证码 --%>
    			<input type="hidden" id="zhengqueyzm" name="zhengqueyzm" value="">
                <input type="text" placeholder="验证码" class="log-input" id="code" name="code" style="border:none;outline:none;border-right: 0;width: 80%">
            </div>
        </div>

    <div class="yanzheng" style="background-color: #C7C6CC"><a href="javascript:void(0)" onclick="savephone()">验证后绑定新手机号</a></div>
</form>

<script src="static/js/wmpc/layer/layer.js"></script>
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script type="text/javascript">
   /*  $(function() {
        FastClick.attach(document.body);
    }); */
    //验证手机格式的正则表达是
	var phoneReg=/^1[3-9]\d{9}$/;
        function clickButton(obj){
        var obj = $(obj);
        var phone=$("#phone").val();
        alert(phone);
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
   	//验证后绑定新手机号
    function savephone(){
    	var phone= $("#phone").val();
    	alert(phone);
    	var zhengqueyzm=$("#zhengqueyzm").val();//正确的验证码
		var code=$("#code").val();//输入的验证码
    	 if(phone==""){
			layer.tips('请输入您的手机号码！', '#phone', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
    	
    	//去执行修改密码
	   $.ajax({
    		type:"post",
    		url:"api/h5KeHu/savexgphone.do",
    		data:{
    			"phone":phone
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
    	
    	/* $("#uploadForm").submit(); */
    
    }
</script>

<script src="static/js/wmpc/jquery-weui.js"></script>
</body>
</html>
