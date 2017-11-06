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
		/*if($("#NAME").val()==""){
			$("#NAME").tips({
				side:3,
	            msg:'请输入网站名称',
	            bg:'#AE81FF',
	            time:1
	        });
			$("#NAME").focus();
			return false;
		}
		//判断是否已经存在
		var url='hudongCategory/selectByName.do';
		$.post(url,{"categoryName":$("#NAME").val()},function(data){
			if("已存在" == data.result){
				$("#NAME").tips({
					side:3,
		            msg:'该名称已存在',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#NAME").focus();
			}else{
				$("#Form").submit();
			}
		});*/
		$("#Form").submit();
	}
</script>
<body>
	<form action="shangjia/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="goods_id" value="${pd.goods_id}"/>
		<div id="zhongxin">
		<table style="margin-left: 56px;">
			<tr class="info">
				<td>商品名称:</td>
				<td>
					<input type="text" name="goodsName" id="goodsName" value="${pd.goodsName }" placeholder="商品名称"/>
				</td>
			</tr>
			<tr class="info">
				<td>商品分类:</td>
				<td>
					<select name="categoryName">
						<option value="">请选择商品所属类别</option>
						<c:forEach items="${list }" var="list">
							<option value="${list.categoryName }" <c:if test="${list.categoryName==pd.categoryName }">selected="selected"</c:if>>${list.categoryName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="info">
				<td>商品图片:</td>
				<td>
					<input type="file" name="goodsImg" id="goodsImg" value="${pd.goodsImg }"/>
				</td>
			</tr>
			<tr class="info">
				<td>商品详情:</td>
				<td>
					<textarea rows="" cols="" name="goodsDetail" id="goodsDetail">${pd.goodsDetail }</textarea>
				</td>
			</tr>
			<tr class="info">
				<td>原价:</td>
				<td>
					<input type="text" name="originalPrice" id="originalPrice" value="${pd.originalPrice }" placeholder="商品原价"/>
				</td>
			</tr>
			<tr class="info">
				<td>现价:</td>
				<td>
					<input type="text" name="presentPrice" id="presentPrice" value="${pd.presentPrice }" placeholder="商品现价"/>
				</td>
			</tr>
			<tr class="info">
				<td>餐盒费:</td>
				<td>
					<input type="text" name="canhefei" id="canhefei" value="${pd.canhefei }" placeholder="餐盒费"/>
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