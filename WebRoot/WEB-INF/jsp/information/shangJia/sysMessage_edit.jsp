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
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</head>
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save(){
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
<body>
	<form action="shangjia/${msg }.do" name="Form" id="Form" method="post" >
	<input type="hidden" name="sys_message_id" value="${pd.sys_message_id}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr class="info">
				<td style="width: 178px;">系统消息标题:</td>
				<td><input type="text" style="width:95%;" name="title" id="title" placeholder="请输入系统消息标题" value="${pd.title }" title="请输入系统消息标题"/></td>
			</tr>
			<tr class="info">
				<td style="width: 178px;">系统消息简介:</td>
				<td><input type="text" style="width:95%;" name="profiles" id="profiles" value="${pd.profiles}" placeholder="请输入系统消息简介" title="请输入系统消息简介"/></td>
			</tr>
			<tr class="info">
				<td style="width: 178px;">系统消息详细内容:</td>
				<td>
					<%-- <input type="text" name="message_content" id="message_content" placeholder="请输入系统消息详细内容" value="${pd.message_content }" title="系统消息详细内容"/> --%>
					<textarea name="message_content"  placeholder="请输入系统消息详细内容...." id="message_content">${pd.message_content}</textarea>
				</td>
			</tr>
			<tr>
				<td class="center" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
	</form>
</body>

</html>