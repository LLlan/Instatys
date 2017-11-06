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
	<!-- 	
		 <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
		<link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
		图片上传
		<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
		<script type="text/javascript" src="plugins/webuploader/upload_dynamic.js"></script> -->
		 
		<script type="text/javascript">
			$(top.hangge());
			//保存
			function save(){
					$("#dynamicForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
			}
			
			//点击上传后，将上传状态标为1
			$(".uploadBtn").click(function(){	
				$("#uploadDetection").val("1");
			});
		
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
	<form action="merchant/${msg }.do" id="dynamicForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="merchant_id" id="merchant_id" value="${pd.merchant_id }"/>
		<div id="zhongxin">
			 <!-- <div id="wrapper">
	        	<div id="container">
	            	<div id="uploader">
	             	 <div class="queueList">
	                    <div id="dndArea" class="placeholder">
	                        <div id="filePicker" class="webuploader-container"><div class="webuploader-pick">点击选择图片</div><div id="rt_rt_1b3j88s9m88o13ank1j74419491" style="position: absolute; top: 20px; left: 268.4px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;"><input name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*" type="file"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255) none repeat scroll 0% 0%;"></label></div></div>
	                        <p>或将照片拖到这里，单次最多可选300张</p>
	                    </div>
	                	<ul class="filelist"></ul>
	                </div>
	                <div class="statusBar" style="display: none;">
	                    <div class="progress" >
	                        <span class="text">0%</span>
	                        <span class="percentage" style="width: 0%;"></span>
	                    </div><div class="info">共0张（0B），已上传0张</div>
	                    <div class="btns">
	                        <div id="filePicker2" class="webuploader-container">
		                        <div class="webuploader-pick" style="">继续添加</div>
		                        <div id="rt_rt_1b3j88s9svm8lh61n0brnd1a5k6" style="position: absolute; top: 0px; left: 0px; width: 37.6px; height: 1.6px; overflow: hidden; bottom: auto; right: auto;">
			                        <input name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*" type="file">
			                        <label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255) none repeat scroll 0% 0%;"></label>
		                        </div>
	                        </div>
	                        <div class="uploadBtn state-pedding">
	                        	<input type="hidden" id="uploadDetection" value="0">
	                        		开始上传
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>  -->
	    
		<table id="table_report" class="table table-striped table-bordered table-hover">
			
			<tr>
				<td style="width: 120px;">
					联系人：
				</td>
				<td>
					<input style="width:95%;" type="text" name="merchant_staff" id="merchant_staff" maxlength="32" value="${pd.merchant_staff }" />
				</td>
			</tr>
			
			<tr>
				<td>
				    <label >联系电话：</label>
				</td>
				<td>
				      <input style="width:95%;" type="text" name="merchant_tel" id="merchant_tel" maxlength="32" value="${pd.merchant_tel }" />
				</td>
			</tr>
			
			<tr>
				<td>
				    <label >门店照片：</label>
				 </td>
				 <td>
					<c:if test="${pd == null || pd.merchant_picture == '' || pd.merchant_picture == null }">
					<input type="file" id="file1" name="file" onchange="fileType(this)"/>
					</c:if>
					<c:if test="${pd != null && pd.merchant_picture != '' && pd.merchant_picture != null }">
						<a href="<%=basePath%>${pd.merchant_picture}" target="_blank"><img src="<%=basePath%>${pd.merchant_picture}" width="210"/></a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('${pd.merchant_picture}','${pd.merchant_id }');" />
						<input type="hidden" name="merchant_picture" id="tpz" value="${pd.merchant_picture }"/>
					</c:if>
				</td>
				 </td>
			</tr>
			
			<tr>
				<td>
				    <label >手持身份证照片：</label>
				</td>
				<td>
					 <c:if test="${pd == null || pd.merchant_photo == '' || pd.merchant_photo == null }">
					<input type="file" id="file2" name="file" onchange="fileType(this)"/>
					</c:if>
					<c:if test="${pd != null && pd.merchant_photo != '' && pd.merchant_photo != null }">
						<a href="<%=basePath%>${pd.merchant_photo}" target="_blank"><img src="<%=basePath%>${pd.merchant_photo}" width="210"/></a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delPhoto('${pd.merchant_photo}','${pd.merchant_id }');" />
						<input type="hidden" name="merchant_photo" id="tpz" value="${pd.merchant_photo }"/>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td>
				    <label >营业执照：</label>
				 </td>
				 <td>
				    <c:if test="${pd == null || pd.merchant_license == '' || pd.merchant_license == null }">
					<input type="file" id="file3" name="file" onchange="fileType(this)"/>
					</c:if>
					<c:if test="${pd != null && pd.merchant_license != '' && pd.merchant_license != null }">
						<a href="<%=basePath%>uploadFiles/uploadImgs/${pd.merchant_license}" target="_blank"><img src="<%=basePath%>${pd.merchant_license}" width="210"/></a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delLice('${pd.merchant_license}','${pd.merchant_id }');" />
						<input type="hidden" name="merchant_license" id="tpz" value="${pd.merchant_license }"/>
					</c:if>
				 </td>
			</tr>
			
			<tr>
				<td>
				    <label >产品LOGO：</label>
				 </td>
				 <td>
				    <c:if test="${pd == null || pd.merchant_logo == '' || pd.merchant_logo == null }">
					<input type="file" id="file4" name="file" onchange="fileType(this)"/>
					</c:if>
					<c:if test="${pd != null && pd.merchant_logo != '' && pd.merchant_logo != null }">
						<a href="<%=basePath%>uploadFiles/uploadImgs/${pd.merchant_logo}" target="_blank"><img src="<%=basePath%>${pd.merchant_logo}" width="210"/></a>
						<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delLo('${pd.merchant_logo}','${pd.merchant_id }');" />
						<input type="hidden" name="merchant_logo" id="tpz" value="${pd.merchant_logo }"/>
					</c:if>
				 </td>
			</tr>
			
			<tr>
				<td>
				    <label >店铺名称：</label>
				 </td>
				 <td>
				    <input style="width:95%;" type="text" name="merchant_name" id="merchant_name" maxlength="32" value="${pd.merchant_name}" />
				 </td>
			</tr>
			
			<tr>
				<td>
				    <label >经营品类：</label>
				 </td>
				 <td>
				    <input style="width:95%;" type="text" name="merchant_type" id="merchant_type" maxlength="32" value="${pd.merchant_type }" />
				 </td>
			</tr>
			
			<tr>
				<td>
				    <label >店铺地址：</label>
				 </td>
				 <td>
				    <input style="width:95%;" type="text" name="merchant_address" id="merchant_address" maxlength="32" value="${pd.merchant_address }" />
				 </td>
			</tr>
			
			
			<!-- 发布人隐藏 传递-->
			<input type="hidden"  name="publish_person" value=""/>
			
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
		<script type="text/javascript">
		$(function() {  
			//上传
			$('#file1').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'gif|png|jpg|jpeg',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			$('#file2').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'gif|png|jpg|jpeg',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			$('#file3').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'gif|png|jpg|jpeg',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			$('#file4').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'gif|png|jpg|jpeg',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			if($("#residence_dynamic_id").val()!=""){
				$("#uploadDetection").val("1");
			}
			
		});
		
		//删除店面图片
	function delP(merchant_picture,merchant_id){
		 if(confirm("确定要删除图片？")){
			var url = "merchant/deltp.do?merchant_picture="+merchant_picture+"&merchant_id="+merchant_id;
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功!");
					document.location.reload();
				}
			});
		} 
	}
	
	//删除身份证照
	function delPhoto(merchant_photo,merchant_id){
		 if(confirm("确定要删除图片？")){
			var url = "merchant/deltphoto.do?merchant_photo="+merchant_photo+"&merchant_id="+merchant_id;
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功!");
					document.location.reload();
				}
			});
		} 
	}
	
	
	//删除营业执照
	function delLice(merchant_license,merchant_id){
		 if(confirm("确定要删除图片？")){
			var url = "merchant/dellicence.do?merchant_license="+merchant_license+"&merchant_id="+merchant_id;
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功!");
					document.location.reload();
				}
			});
		} 
	}
	//删除商品LOGO
	function delLo(merchant_logo,merchant_id){
		 if(confirm("确定要删除图片？")){
			var url = "merchant/deltlogo.do?merchant_logo="+merchant_logo+"&merchant_id="+merchant_id;
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功!");
					document.location.reload();
				}
			});
		} 
	}
		
		</script>
	
</body>
</html>