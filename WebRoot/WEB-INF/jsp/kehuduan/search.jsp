<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>搜索</title>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width">
    <link rel="stylesheet" href="static/css/wmpc/yahu.css"/>
    <link rel="stylesheet" href="static/css/wmpc/index.css"/>
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <style>
        body{
            background-color: #fff;
        }
    </style>
</head>
<body ontouchstart>
<div class="header_search">
    <div class="go">
        <a href="javascript:history.back()"></a>
    </div>
    <div class="search">
        <form action="" method="post">
            <input type="search" class="col-xs-10" name="keywords" id="keywords" autocomplete="off" placeholder="搜索您需要的商品"/>
            <input type="submit" class="col-xs-2" value="搜索"/>
        </form>
    </div>
</div>
<script>
    $(function () {
        FastClick.attach(document.body);
    });
</script>
</body>
</html>