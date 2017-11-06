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
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
		
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
	});
	
	//保存
	function save(){
			$("#residenceForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
		
	
	
</script>
<style type="text/css">
	.inputClass{
		width: 290px;
	}
</style>
	</head>
<body>
	<form action="shoufei/${msg }.do" id="residenceForm" method="post">
		<input type="hidden" name="collect_cost_id" id="collect_cost_id" value="${pd.collect_cost_id }"/>
		<input type="hidden" name="type" id="type" value="${pd.type}"/>
		<input type="hidden" name="YearMonths" id="YearMonths" value="${pd.YearMonths}"/>
		<input type="hidden" name="residence_id" id="residence_id" value="${pd.residence_id}"/>
		
		
		<div id="zhongxin">
		<table class="table table-striped table-bordered">
			<tr>
				<td colspan="2">
					<span style="color: red;font-size: 14px">提示：在输入单元(楼栋)，房号，业主姓名时请仔细核对正确，以免缴错费用!</span>
				</td>
			</tr>
			<tr>
				<td>楼栋：</td>
				<td>
				 <input type="text" name="loudong" id="loudong" value="${pd.loudong }" maxlength="32" class="inputClass" placeholder="请输入楼栋(如1栋)" />                
					${pd.loudong }
				</td>
			</tr>
			
			<tr>
				<td>单元：</td>
				<td>
				 <input type="text" name="unit" id="unit" value="${pd.unit }" maxlength="32" class="inputClass" placeholder="请输入单元(如1单元)" /> 
					${pd.unit }
				</td>
			</tr>
			
			<tr>
				<td>房号：</td>
				<td>
				<input type="text" name="fanghao" id="fanghao" value="${pd.fanghao }" maxlength="32" class="inputClass" placeholder="请输入房号(如201)" /> 
					${pd.fanghao }
				</td>
			</tr>
			
			<tr>
				<td>业主：</td>
				<td>
				<input type="text" name="proprietor_name" id="proprietor_name" value="${pd.proprietor_name }" maxlength="32" class="inputClass" placeholder="请输入业主姓名" /> 
				
				</td>
			</tr>
			
			<tr>
				<td>需缴总额：</td>
				<td>
					
				     <input type="text" name="totol" id="totol" value="${pd.totol }" maxlength="32" class="inputClass" placeholder="请输入合计" title=""/>             
				</td>
			</tr>
			
			
			<tr>
			<td>缴费人：</td>
				<td>
				     <input type="text" name="jiaofei_name" id="jiaofei_name" value="${pd.jiaofei_name }" maxlength="32" class="inputClass" placeholder="请输入缴费人姓名" title=""/>             
					  
				</td>
				
			</tr>
			
			<tr>
			<td>操作人：</td>
				<td>
					${pd.name }
				     <input type="hidden" name="operation" id="operation" value="${pd.name }"/>             
				</td>
			</tr>
			
			<td>缴费方式：</td>
				<td>
					物业缴费处
				     <input type="hidden" name="resource" id="resource" value="物业缴费处"/>             
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
		<!-- 日期控件  -->
		<script type="text/javascript" src="static/js/common/datePicker/DatePicker.js"></script>
		<script type="text/javascript" src="static/js/common/datePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
		});
		
		</script>
	
</body>
</html>