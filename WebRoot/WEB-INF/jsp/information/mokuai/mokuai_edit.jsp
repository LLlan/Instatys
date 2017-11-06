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
	}
</script>
<body>
	<form action="api/mokuai/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="mokuai_id" value="${pd.mokuai_id}"/>
		<input type="hidden" name="headImg" value="${pd.headImg}"/>
		<div id="zhongxin">
		<table>
			<tr class="info">
				<td>类型</td>
				<td>
					<select name="type" id="type">
						<option value="1" <c:if test="${pd.type=='1' }">selected="selected"</c:if>>旅游景点</option>
						<option value="2" <c:if test="${pd.type=='2' }">selected="selected"</c:if>>汽车服务</option>
						<option value="3" <c:if test="${pd.type=='3' }">selected="selected"</c:if>>休闲娱乐</option>
						<option value="4" <c:if test="${pd.type=='4' }">selected="selected"</c:if>>酒店宾馆</option>
						<option value="5" <c:if test="${pd.type=='5' }">selected="selected"</c:if>>婚礼庆典</option>
						<option value="6" <c:if test="${pd.type=='6' }">selected="selected"</c:if>>生鲜超市</option>
					</select>
				</td>
			</tr>
			<tr class="info">
				<td>头像</td>
				<td>
					<input type="file" name="file" id="file"/>
				</td>
			</tr>
			<tr class="info">
				<td>名称</td>
				<td>
					<input type="text" name="title" id="title" placeholder="输入标题名称" value="${pd.title }"/>
				</td>
			</tr>
			<tr class="info">
				<td>介绍</td>
				<td>
					<textarea name="introduct" placeholder="输入介绍内容">${pd.introduct }</textarea>
				</td>
			</tr>
			<tr style="position: absolute;margin: 25px 0 0 66px;">
				<td style="text-align: center;">
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