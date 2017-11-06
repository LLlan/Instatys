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
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		if($("#qibugongli").val()==""){
			$("#qibugongli").tips({
				side:3,
				msg:'请输入姓名',
				bg:'#AE81FF',
				time:2
			});
			$("#qibugongli").focus();
			return false;
		}
		//判断是否已经存在
		var url='suigong/selectBySuigongName.do';
		$.post(url,{"suigong_name":$("#Suigong_name").val()},function(data){
			if("已存在" == data.result){
				$("#Suigong_name").tips({
					side:3,
		            msg:'该名称已存在',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#Suigong_name").focus();
			}else{
				$("#Form").submit();
			}
		});
	
	}
	
</script>
	</head>
<body>
	<form action="userKehu/${msg}.do" name="Form" id="Form" method="post">
		<input type="hidden" name="jifei_id" id="jifei_id" value="${pd.jifei_id}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td>同城打车起步公里:</td>
				<td><input type="text" style="width:95%;" name="qibugongli" id="qibugongli" value="${pd.qibugongli}" placeholder="请设置起步公里" title="请设置起步公里"/></td>
			</tr>
			<tr>
				<td>起步费（元）:</td>
				<td><input type="text" style="width:95%;" name="qibujia" id="qibujia" value="${pd.qibujia}" placeholder="请设置起步费（元） " title="请设置起步费（元）"/></td>
			</tr>
			<tr>
				<td>超公里费（元/公里）:</td>
				<td><input type="text" style="width:95%;" name="jifei_Amount" id="jifei_Amount" value="${pd.jifei_Amount}" placeholder="请设置超公里费（元/公里）" title="请设置超公里费（元/公里）"/></td>
			</tr>
		<%-- 	<tr>
				<td>是否设为默认:</td>
				<td>
				<input type="radio" class="form-con1" name="isDefault" value="0" <c:if test="${pd.isDefault=='0' || pd.isDefault==''}">checked="checked"</c:if>/>
				<input type="radio" class="form-con2" name="isDefault" value="1" <c:if test="${pd.isDefault=='1' }">checked="checked"</c:if>/>
					 <select name="isDefault"style="border-radius: 4px!important;border-color: #6fb3e0;height: 28px!important;font-size: 12px;width: 120px;">
							<option value="">请选择</option>
							<option value="0"<c:if test="${pd.isDefault=='是' || pd.isDefault==''}">checked="checked"</c:if>>是</option>
							<option value="1"<c:if test="${pd.isDefault=='否' }">checked="checked"</c:if>>否</option>
					</select>
					<select name="isDefault" title="状态">
						<option value="">请选择</option>
						<option value="0"<c:if test="${pd.isDefault=='是' || pd.isDefault==''}">checked="checked"</c:if>>是</option>
						<option value="1"<c:if test="${pd.isDefault=='否' }">checked="checked"</c:if>>否</option>
					</select>
				</td>
			</tr> --%>
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