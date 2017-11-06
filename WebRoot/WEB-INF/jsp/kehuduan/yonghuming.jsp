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
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <title>修改用户名</title>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->

<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">修改用户名</h1>
  <a href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left "></i></a>
</div>

	
<!--头部结束-->
<form id="uploadForm" action="api/h5KeHu/${msg}.do" method="post" style="margin-top:88px;">
	<div class="changeU">
	    <input type="text" name="userName" value="${pds.userName }" minlength="4" maxlength="9" placeholder="用户名" id="userName"/>
	    <p class="name2">请输入4-9位数字、字母或中文</p>
	    <a href="javascript:void(0)" onclick="yonghuming()" class="sure">确认修改</a>
	</div>
</form>
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script type="text/javascript">

  function yonghuming(){
    	var userName=$("#userName").val();
	    	if(userName==0){
				layer.tips('请输入您的真实姓名！', '#userName', {
		     		  tips: [1, '#D9006C'],
		     		  time: 3000
		     	});
				return;
			}else if(userName.length<2){
				layer.tips('请输入至少二个字！', '#userName', {
		     		  tips: [1, '#D9006C'],
		     		  time: 3000
		     	});
				return;
			}
    	$("#uploadForm").submit();
    }
</script>
</body>
</html>
