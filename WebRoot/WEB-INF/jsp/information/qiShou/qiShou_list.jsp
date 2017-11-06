<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8" />
	<link rel="stylesheet" href="static/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="static/css/bootstrap-responsive.min.css"/>
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
	<!--开始引入查看图片插件 -->
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
    <script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
	<!--结束引入查看图片插件 -->
<script type="text/javascript">
	$(top.hangge());
	$(function() {
		//复选框
		$('table th input:checkbox').on('click' , function(){
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox')
			.each(function(){
				this.checked = that.checked;
			});	
		});
	});
	//审核不通过
	function checkedNo(tagID){
		bootbox.confirm("确定要要执行拒绝操作吗?", function(result) {
			if(result) {
				top.jzts();
				var url = '<%=basePath%>qishou/syschecked.do?ids='+tagID+'&num=0';
				$.get(url,function(data){
					nextPage('${page.currentPage}');
				});
			}
		});
	}
	//审核通过
	function checkedYes(tagID){
		bootbox.confirm("确定要要执行通过操作吗?", function(result) {
			if(result) {
				top.jzts();
				var url = '<%=basePath%>qishou/syschecked.do?ids='+tagID+'&num=1';
				$.get(url,function(data){
					nextPage('${page.currentPage}');
				});
			}
		});
	}
	//批量通过和拒绝
	function checkedAll(msg) {
		bootbox.confirm(msg,function(result){
			if(result){
				var str='';
				for ( var i = 0; i < document.getElementsByName('ids').length; i++) {
					if(document.getElementsByName('ids')[i].checked){
						if(str==''){
							str+=document.getElementsByName('ids')[i].value;
						}else{
							str+=','+document.getElementsByName('ids')[i].value;
						}
					}
				}
				if(str==''){
					bootbox.dialog("您没有选择任何内容!", 
						[
						  {
							"label" : "关闭",
							"class" : "btn-small btn-success",
							"callback": function() {
								//Example.show("great success");
								}
							}
						 ]
					);
					$("#zcheckbox").tips({
						side:3,
			            msg:'点这里全选',
			            bg:'#AE81FF',
			            time:8
			        });
					return;
				}else{
					var num="";
					if(msg=="确定批量拒绝吗?"){
						num="0";
					}else if(msg=="确定批量通过吗?"){
						num="1";
					}
					$.ajax({
						type:'post',
						url:'<%=basePath%>qishou/syschecked.do',
						dataTyoe:'json',
						cache: false,
						data:{
							"ids":str,
							"num":num
						},
						success:function(data){
							nextPage('${page.currentPage}');
						},
					});
				}
			}
		});	
	}
</script>
</head>
<body>
<div id="page-content" class="clearfix">
<div class="row-fluid">
	<!-- 检索  -->
	<form action="<%=basePath%>/qishou/getlistPage.do" method="post" name="userForm" id="userForm">
	<table>
		<tr>
			<td>
				<span class="input-icon">
					<input autocomplete="off" id="nav-search-input" type="text" name="searchName" value="${pd.searchName }" placeholder="这里输入关键词" />
					<i id="nav-search-icon" class="icon-search"></i>
				</span>
			</td>
			<c:if test="${QX.cha == 1 }">
			<td style="vertical-align:top;">
				<button class="btn btn-mini btn-light" title="检索">
					<i id="nav-search-icon" class="icon-search"></i>
				</button>
			</td>
			</c:if>
		</tr>
	</table>
	<!-- 检索  -->
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center">
				<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
			</th>
			<th class="center"  style="width: 50px;">序号</th>
			<th class='center'>姓名</th>
			<th class='center'>手机号</th>
			<th class='center'>性别</th>
			<th class='center'>所在地区</th>
			<th class='center'>本人身份证号</th>
			<th class='center'>身份证正面照</th>
			<th class='center'>手持正面身份证照</th>
			<th class='center'>代步工具照</th>
			<th class='center'>资料提交时间</th>
			<th class='center'>认证状态</th>
			<th class='center'>认证审核时间</th>
			<th class='center'>操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach items="${list}" var="var" varStatus="vs">
				<tr>
					<td class='center' style="width: 30px;">
				 		<label><input type='checkbox' name='ids' value="${var.user_qishou_id }"/><span class="lbl"></span></label>
					</td>
					<td class='center' style="width: 30px;">${vs.index+1}</td>
					<td class='center'>${var.realName}</td>
					<td class='center'>${var.phone}</td>
					<td class='center'>${var.sex}</td>
					<td class='center'>${var.area}</td>
					<td class='center'>${var.identityCard}</td>
					<%-- <td class='center'>${var.identityFrontImg}</td>
					<td class='center'>${var.identityFrontHoldImg}</td>
					<td class='center'>${var.daibutoolImg}</td> --%>
					<td class='center'>
						<a href="<%=basePath%>${var.identityFrontImg}" class="bwGal"><img alt="图片" title="身份证正面照" src="<%=basePath%>${var.identityFrontImg}" style="width: 50px;height: 30px;"></a>
					</td>
					<td class='center'>
						<a href="<%=basePath%>${var.identityFrontHoldImg}" class="bwGal"><img alt="图片" title="手持正面身份证照" src="<%=basePath%>${var.identityFrontHoldImg}" style="width: 50px;height: 30px;"></a>
					</td>
					<td class='center'>
						<a href="<%=basePath%>${var.daibutoolImg}" class="bwGal"><img alt="图片" title="代步工具照" src="<%=basePath%>${var.daibutoolImg}" style="width: 50px;height: 30px;"></a>
					</td>
					<td class='center'>
						<c:if test="${var.submitTime=='' || var.submitTime==null}">还未进行认证...</c:if>
						<c:if test="${var.submitTime!='' && var.submitTime!=null}">${fn:substring(var.submitTime,0,16)}</c:if>
					</td>
					<td class='center'>
						<c:if test="${var.authenticationState=='0' }"><div style='background-color: red; color: white;'>认证失败</div></c:if>
						<c:if test="${var.authenticationState=='1' }"><div style='background-color: #15c300; color: white;'>认证通过</div></c:if>
						<c:if test="${var.authenticationState=='2' }">等待审核</c:if>
						<c:if test="${var.authenticationState=='' || var.authenticationState==null }">等待认证</c:if>
					</td>
					<td class='center'>
						<c:if test="${var.authenticationTime=='' || var.authenticationTime==null}">等待中...</c:if>
						<c:if test="${var.authenticationTime!='' && var.authenticationTime!=null}">${fn:substring(var.authenticationTime,0,16)}</c:if>
					</td>
					<%-- <td class='center'><a href="javascript:void(0)" onclick="lookRenZhengInformation('${var.user_shangjia_id }')">查看</a></td>
					<td class='center'><a href="javascript:void(0)" onclick="lookRenZhengInformation('${var.user_shangjia_id }')">查看</a></td> --%>
					<td style="width: 80px;">
						<a class='btn btn-mini btn-info' title="拒绝" onclick="checkedNo('${var.user_qishou_id }')" >拒绝</a>
						<a class='btn btn-mini btn-danger' title="通过"  onclick="checkedYes('${var.user_qishou_id }')">通过</a>
					</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100" class="center">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;width:50px;">
					<a class="btn btn-small btn-success" onclick="checkedAll('确定批量通过吗?');">批量通过</a>
					<a class="btn btn-small btn-danger" onclick="checkedAll('确定批量拒绝吗?');" style="margin: -55px 0 0 88px;width: 52px;text-align: center;">批量拒绝</a>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
	</form>
</div>
</div>
</body>
<%-- 查看图片使用 --%>
	<style type="text/css">
		li {list-style-type:none;}
	</style>
	<ul class="navigationTabs">
           <li><a></a></li>
           <li></li>
    </ul>
<%-- 查看图片使用 --%>
</html>