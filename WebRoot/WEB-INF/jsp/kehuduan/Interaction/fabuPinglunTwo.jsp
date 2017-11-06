<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>发布评论</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div class="wy-header header">
    <div class="wy-header-icon-back"><a href="javascript:history.go(-1)" style="background:none;width:100px;color:#fff;font-size: 14px;">取消</a></div>
    <div class="wy-header-title" style="position: relative">发布评论 <a href="javascript:void(0);"  onclick="fabu();" class="fabiaoDong" style="background-color: #fff;color:#4779aa;padding:0 10px;font-size:14px;border-radius: 4px;position: absolute;right:-35px;bottom:10px;;line-height: 25px;">发布</a></div>
</div>
<!--头部结束-->
<form id="uploadForm" action="api/h5KeHu/${msg}.do" method="post">
	<!--主体-->
	<input type="hidden" value="${hudong_pinglun_id }" name="hudong_pinglun_fid">
	<div class="xiangfa" style="height:600px;">
	    <textarea name="content" placeholder="写评论...." id="content"></textarea>
	</div>
</form>
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <script src="static/js/wmpc/layer/layer.js"></script>
    <script>
        $(function() {
            FastClick.attach(document.body);
        });
    </script>
    <script type="text/javascript">
	//保存
	function fabu(){
		var content=$("#content").val();//发布内容
		if(content==""){
			layer.tips('请填写您发布评论内容！', '#content', {
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
