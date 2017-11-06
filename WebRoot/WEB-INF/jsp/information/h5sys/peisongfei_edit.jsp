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
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	$(top.hangge());
	//保存
	function save(){
		/* $("#zhongxin").hide();
		$("#zhongxin2").show(); */
		if($("#gongli1").val()==""){
			
			$("#gongli1").tips({
				side:3,
	            msg:'请输入配送费一',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#gongli1").focus();
			return false;
		}
		if($("#gongli2").val()==""){
			
			$("#gongli2").tips({
				side:3,
	            msg:'请输入配送费二',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#gongli2").focus();
			return false;
		}
		if($("#gongli3").val()==""){
			
			$("#gongli3").tips({
				side:3,
	            msg:'请输入配送费三',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#gongli3").focus();
			return false;
		}
		if($("#gongli4").val()==""){
			
			$("#gongli4").tips({
				side:3,
	            msg:'请输入配送费四',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gongli4").focus();
			return false;
		}
		$("#Form").submit();
	}
	
</script>
	</head>
<body>
	<form action="h5sys/${msg}.do" name="Form" id="Form" method="post">
		<input type="hidden" name="peisongfei_id" id="peisongfei_id" value="${pd.peisongfei_id}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td>配送费规则一:</td>
				<td><input type="text" style="width:95%;" name="gongli1" id="gongli1" value="${pd.gongli1}" placeholder="请输入配送费一" title="配送距离范围一"/></td>
			</tr>
			
			<tr>
				<td>配送费规则二:</td>
				<td><input type="text" style="width:95%;" name="gongli2" id="gongli2" value="${pd.gongli2}" placeholder="请输入配送费二 " title="配送距离费二"/></td>
			</tr>
			<tr>
				<td>配送费规则三:</td>
				<td><input type="text" style="width:95%;" name="gongli3" id="gongli3" value="${pd.gongli3}" placeholder="请输入配送费 三" title="配送距离费三"/></td>
			</tr>
			<tr>
				<td>配送费规则四:</td>
				<td><input type="text" style="width:95%;" name="gongli4" id="gongli4" value="${pd.gongli4}" placeholder="请输入配送费四 " title="配送距离费四"/></td>
			</tr>
			<tr>
				<td style="text-align: center;"colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>