<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <title>乘车人数</title>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">乘车人数</h1>
  <a href="javascript:history.go(-1)" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<form id="Form" action="api/h5KeHu/${msg}.do"  method="post" style="margin-top:90px;">
	<input type="hidden" value="${id }" name="information_changtu_id">
	<div class="changeU">
	    <input value="" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" type="text" id="renshu" name="renshu" placeholder="请您输入乘车人数"/>
	    <a href="javascript:void(0);" onclick="queding('${userNum}');" class="sure">确定</a>
	</div>
</form>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script>
  /*   $(function() {
        FastClick.attach(document.body);
        checkSession();
    }); */
    
    function queding(userNum){
    	//var userNum="${userNum}";//司机发布的可乘人数
     	var pincheUserNum="${pincheUserNum}";//用户拼车人数
    	var renshu=$("#renshu").val();//用户输入的拼车人数
    	var synum  =parseInt(userNum)-pincheUserNum;//剩余乘坐人数
    	if(renshu==""){
			layer.tips('请您输入乘车人数！', '#renshu', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
    	 if(renshu>userNum){
			layer.tips('乘车人数超过拼车人数！', '#renshu', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		 if(renshu>synum){
			layer.tips('剩余可乘车人数为：'+synum, '#renshu', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		$("#Form").submit();
    }
</script>

<script src="static/js/wmpc/jquery-weui.js"></script>
</body>
</html>
