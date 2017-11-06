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
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<!-- 引入jquery.js -->
		<script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
		<!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	
		<script type="text/javascript" src="static/js/ace-elements.min.js"></script>
	    <script type="text/javascript" src="static/js/ace.min.js"></script>
	
		<!-- 下拉框 -->
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
		
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
		<!-- 日期控件  -->
		<script type="text/javascript" src="static/js/common/datePicker/DatePicker.js"></script>
		<script type="text/javascript" src="static/js/common/datePicker/WdatePicker.js"></script>
		 
			<script type="text/javascript">
			$(top.hangge());
			//保存
			function save(){
				if($("#uploadDetection").val()=='0'){
				bootbox.confirm("您还未选择图片或还未点击“开始上传”按钮，您确定不上传图片?", function(result){
					if(result) {
						$("#dynamicForm").submit();
						$("#zhongxin").hide();
						$("#zhongxin2").show();
					}else{
						
					}
				});
			}else{
					$("#dynamicForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				}
			}
			
			
		</script>
		<style type="text/css">
			.changeWidth{
				width: 220px;
			}
			.photo_msg label{
				display:inline-block;
				background: #ebecee;
				border-radius: 5px;
				padding:0 5%;
			}
		</style>
	</head>
<body>
	<form action="order/${msg }.do" id="dynamicForm" method="post">
		<input type="hidden" name="residence_dynamic_id" id="residence_dynamic_id" value=""/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td>
				    <label>客户姓名：</label>
				</td>
				<td>
				      <input style="width:95%;" type="text" name="customer_name" id="customer_name" value="${pd2.customer_name }" maxlength="32" placeholder="客户姓名" />
				</td>
			</tr>
			<tr>
				<td>
				    <label >客户地址：</label>
				</td	>
				<td>
				      <input style="width:95%;" type="text" name="customer_adress" id="customer_adress" value="${pd2.customer_adress }" maxlength="32" placeholder="客户地址" />
				</td>
			</tr>
			
			<tr>
				<td>
				    <label >客户手机：</label>
				</td>
				<td>
				      <input style="width:95%;" type="text" name="customer_tel" id="customer_tel" value="${pd2.customer_tel }" maxlength="32" placeholder="客户手机" />
				</td>
			</tr>
			
			<tr>
				<td>
				    <label >商家名称：</label>
				</td>
				<td>
				      <input style="width:95%;" type="text" name="merchant_name" id="merchant_name" value="" maxlength="32" placeholder="商家名称" />
				</td>
			</tr>
			
			<tr>
				<td>
				    <label >联系电话：</label>
				</td>
				<td>
				      <input style="width:95%;" type="text" name="merchant_tel" id="merchant_tel" value="" maxlength="32" placeholder="联系电话" />
				</td>
			</tr>
			
			
			<tr>
				<td>
				    <label >店铺地址：</label>
				 </td>
				 <td>
				    <textarea class="form-control" name="merchant_address"   rows="6" cols="21" style="width: 580px;"></textarea>
				 </td>
			</tr>
			
			

		<!-- 发布人隐藏 传递-->
			<input type="hidden"  name="publish_person" value=""/>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">确定</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
		<script type="text/javascript">
		$(function() {  
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			if($("#residence_dynamic_id").val()!=""){
				$("#uploadDetection").val("1");
			}
			
		});
		
		</script>
	
</body>
</html>