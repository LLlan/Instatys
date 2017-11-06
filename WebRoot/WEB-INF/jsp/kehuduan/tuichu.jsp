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
   <link rel="stylesheet" href="static/css/wmpc/yahu.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <title>主页</title>
    <style>
        input{
            border: none;
            outline: none;
        }
        a:focus{
            outline: none;
            -moz-outline:none;
        }
    </style>
</head>
<body ontouchstart style="background: url('static/images/wmpc/bg.png') no-repeat;background-size: 100%;">
<div id="header">
    <a style="cursor: text" href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<div class="main">
    <div class="main_box_top">
        <div class="logo">
            <img src="static/images/wmpc/dog.png" alt=""/>
        </div>
    </div>
    <div class="main_box">
        <div class="register"><a style="cursor: text" href="api/h5KeHu/toRegister.do">注册</a></div>
        <div class="login"><a  style="cursor: text" href="api/h5KeHu/toLogin.do">登录</a></div>
    </div>
</div>
<script>
	function zhuce(){
		location.href="api/h5login/zhuce.do";
	}
	
	function login(){
		location.href="api/h5login/login.do";
	}

    /* $(function () {
        FastClick.attach(document.body);
    }); */
</script>
</body>
</html>