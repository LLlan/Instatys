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
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	//保存
	function save(){
			if($("#NAME").val()==""){
			$("#NAME").tips({
				side:3,
	            msg:'请输入商家名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NAME").focus();
			return false;
		}
		if($("#LONGITUDE").val()==""){
			$("#LONGITUDE").tips({
				side:3,
	            msg:'请输入经度',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LONGITUDE").focus();
			return false;
		}
		if($("#LATITUDE").val()==""){
			$("#LATITUDE").tips({
				side:3,
	            msg:'请输入纬度',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LATITUDE").focus();
			return false;
		}
		if($("#TYPE").val()==""){
			$("#TYPE").tips({
				side:3,
	            msg:'请输入商家类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TYPE").focus();
			return false;
		}
		if($("#SALESTIME").val()==""){
			$("#SALESTIME").tips({
				side:3,
	            msg:'请输入营业时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SALESTIME").focus();
			return false;
		}
		if($("#TELEPHONE").val()==""){
			$("#TELEPHONE").tips({
				side:3,
	            msg:'请输入联系电话',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TELEPHONE").focus();
			return false;
		}
		if($("#WEBSITE").val()==""){
			$("#WEBSITE").tips({
				side:3,
	            msg:'请输入网址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#WEBSITE").focus();
			return false;
		}
		if($("#LOGOURL").val()==""){
			$("#LOGOURL").tips({
				side:3,
	            msg:'请输入图片',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LOGOURL").focus();
			return false;
		}
		if($("#ADDRESS").val()==""){
			$("#ADDRESS").tips({
				side:3,
	            msg:'请输入商家地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ADDRESS").focus();
			return false;
		}
		if($("#DESCRIBES").val()==""){
			$("#DESCRIBES").tips({
				side:3,
	            msg:'请输入商家描述',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#DESCRIBES").focus();
			return false;
		}
		if($("#INTRODUCE").val()==""){
			$("#INTRODUCE").tips({
				side:3,
	            msg:'请输入商家介绍',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INTRODUCE").focus();
			return false;
		}
		if($("#CONFIGURE").val()==""){
			$("#CONFIGURE").tips({
				side:3,
	            msg:'请输入商家配置',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CONFIGURE").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	//删除图片
	function delP(tpurl,id){
		 if(confirm("确定要删除图片？")){
			var url = "<%=basePath%>company/deltp.do?tpurl="+tpurl+"&COMPANY_ID="+id+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功!");
					document.location.reload();
				}
			});
		} 
	}
	
</script>
	</head>
<body>
	<form action="company/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="COMPANY_ID" id="COMPANY_ID" value="${pd.COMPANY_ID}"/>
		<div id="zhongxin">
		
		<table id="table_report" class="table table-striped table-bordered table-hover">

			<tr>
				<td>商家名称:</td>
				<td><input type="text" style="width:95%;" name="NAME" id="NAME" value="${pd.NAME}" placeholder="这里输入商家名称" title="商家名称"/></td>
			</tr>
			<tr>
				<td>商家类型:</td>
				<td>
				     <select class="chzn-select" name="TYPE" id="TYPE" data-placeholder="请选择商家类别" style="vertical-align:top;"  title="商家类别">
				            <option value=""></option>
				            <c:forEach items="${varCates}" var="cate">
					          <option value="${cate.CATEGORY_ID }" <c:if test="${cate.CATEGORY_ID == pd.TYPE }">selected</c:if>>${cate.NAME }</option>
				            </c:forEach>
				     </select>
				</td>
			</tr>
			<tr>
				<td>所处经纬度:</td>
				<td><input type="text" style="width:40%;" name="LONGITUDE" id="LONGITUDE" value="${pd.LONGITUDE}" placeholder="这里输入商家所处经度" title="所处经度"/><input type="text" style="width:40%;" name="LATITUDE" id="LATITUDE" value="${pd.LATITUDE}" placeholder="这里输入商家所处纬度" title="所处纬度"/><a class="btn btn-app btn-light btn-mini"  href="http://lbs.qq.com/tool/getpoint/" target="_blank">
						<i class="icon-globe"></i>
				</td>
			</tr>
			<tr>
				<td>营业时间:</td>
				<td><input type="text" style="width:95%;" name="SALESTIME" id="SALESTIME" value="${pd.SALESTIME}" placeholder="这里输入商家营业时间" title="营业时间"/></td>
			</tr>
			<tr>
				<td>联系方式:</td>
				<td><input type="text" style="width:95%;" name="TELEPHONE" id="TELEPHONE" value="${pd.TELEPHONE}" placeholder="这里输入商家联系方式" title="联系方式"/></td>
			</tr>
			<tr>
				<td>商家网址:</td>
				<td><input type="text" style="width:95%;" name="WEBSITE" id="WEBSITE" value="${pd.WEBSITE}" placeholder="这里输入商家网址" title="商家网址"/></td>
			</tr>
			<tr>
				<td>商家LOGO:</td>
				<td>
					<c:if test="${pd == null || pd.LOGOURL == '' || pd.LOGOURL == null }">
					<input type="file" id="tp" name="tp"/>
					</c:if>
					<c:if test="${pd != null && pd.LOGOURL != '' && pd.LOGOURL != null }">
						<a href="${pd.LOGOURL}" target="_blank"><img src="${pd.LOGOURL}" width="210"/></a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('${pd.LOGOURL}','${pd.COMPANY_ID }');" />
						<input type="hidden" name="tpz" id="tpz" value="${pd.LOGOURL }"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td>商家地址:</td>
				<td><input type="text" style="width:95%;" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" placeholder="这里输入商家地址" title="商家地址"/></td>
			</tr>
			<tr>
				<td>商家描述:</td>
				<td><input type="text" style="width:95%;" name="DESCRIBES" id="DESCRIBES" value="${pd.DESCRIBES}" placeholder="这里输入简单的描述该商家" title="商家描述"/></td>
			</tr>
			<tr>
				<td>商家介绍:</td>
				<td id="nr">
					<textarea  style="width:93%;height:290px" name="INTRODUCE" id="INTRODUCE">${pd.INTRODUCE }</textarea>
				</td>
			</tr>
			<tr>
				<td>商家配置:</td>
				<td><input type="text" style="width:95%;" name="CONFIGURE" id="CONFIGURE" value="${pd.CONFIGURE}" placeholder="这里输入商家配置" title="商家配置"/></td>
			</tr>
			<tr>
				<td>选择:</td>
				<td>
					<select name="ISHOME" title="是否首页推广">
					<option value="0" <c:if test="${pd.ISHOME == '0' }">selected</c:if> >首页不推荐</option>
					<option value="1" <c:if test="${pd.ISHOME == '1' }">selected</c:if> >首页推荐</option>
					</select>
					<select name="ISREC" title="是否推荐">
					<option value="0" <c:if test="${pd.ISREC == '0' }">selected</c:if> >不推荐</option>
					<option value="1" <c:if test="${pd.ISREC == '1' }">selected</c:if> >推荐</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>商家状态:</td>
				<td>
					<select name="STATUS" title="商家状态">
					<option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >正常</option>
					<option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >冻结</option>
					</select>
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
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 单选框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->

		<!-- 编辑框-->
		<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
		<!-- 编辑框-->
		
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//上传
			$('#tp').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false //| true | large
				//whitelist:'gif|png|jpg|jpeg'
				//blacklist:'exe|php'
				//onchange:''
				//
			});
			
		});
		
		function reurl(){ 
			UE.getEditor('INTRODUCE');
			} 
		setTimeout('reurl()',500);
		</script>
	
</body>
</html>