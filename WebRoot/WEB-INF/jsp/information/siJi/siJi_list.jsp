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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索开始  -->
			<form action="sijiUser/sijiUserlistPage.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td><span class="input-icon"> <input
							autocomplete="off" id="nav-search-input" type="text"
							name="searchName" value="${pd.searchName }"
							placeholder="输入车主真实姓名!" /> <i id="nav-search-icon"
							class="icon-search"></i> </span>
					</td>
					<td><c:if test=""></c:if>
						 <select name="selectName"style="border-radius: 4px!important;border-color: #6fb3e0;height: 28px!important;font-size: 12px;width: 120px;">
								<option value="">请选择审核状态</option>
								<option value="0"<c:if test="${pd.selectName!='' && pd.selectName=='0' }">selected="selected"</c:if>>已失败</option>
								<option value="1"<c:if test="${pd.selectName!='' && pd.selectName=='1' }">selected="selected"</c:if>>已通过</option>
								<option value="2"<c:if test="${pd.selectName!='' && pd.selectName=='2' }">selected="selected"</c:if>>待审核</option>
						</select>
					</td>
					<td style="vertical-align:top;">
						<button class="btn btn-mini btn-light" onclick="search();"title="检索">
							<i id="nav-search-icon" class="icon-search"></i>
						</button>
					</td>
				</tr>
			</table>
			<!-- 检索结束  -->
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" onclick="selectAll()"/><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th class="center">用户名</th>
						<th class="center">手机号码</th>
						<th class="center">车型</th>
						<th class="center">车辆的颜色</th>
						<th class="center">车牌号</th>
						<th class="center">车主的真实姓名</th>
						<th class="center">车主的身份证号</th>
						<th class="center">身份证正面照</th>
						<th class="center">身份证背面照</th>
						<th class="center">驾驶证正面照</th>
						<th class="center">行驶证正面照</th>
						<th class="center">实名认证通过时间</th>
						<th class="center">认证审核状态</th>
						<th class="center">认证提交时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty sijiUserList}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${sijiUserList}" var="var" varStatus="vs">
								<tr>
									<td class='center' style="width: 30px;">
										<label><input type='checkbox' name='ids' value="${var.user_siji_id}" /><span class="lbl"></span></label>
									</td>
									<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class="center">${fn:substring(var.realName,0,1)}师傅</td>
											<td class="center">${var.phone}</td>
											<td class="center">${var.carType}</td>
											<td class="center">${var.carColor}</td>
											<td class="center">${var.carNumber}</td>
											<td class="center">${var.realName}</td>
											<td class="center">${var.identityCard}</td>
											<td class='center'>
												<a href="<%=basePath%>${var.identityFrontImg}" class="bwGal"><img alt="图片" title="身份证正面照" src="<%=basePath%>${var.identityFrontImg}" style="width: 50px;height: 30px;"></a>
											</td>
											<td class='center'>
												<a href="<%=basePath%>${var.identityReverseImg}" class="bwGal"><img alt="图片" title="身份证背面照" src="<%=basePath%>${var.identityReverseImg}" style="width: 50px;height: 30px;"></a>
											</td>
											<td class='center'>
												<a href="<%=basePath%>${var.drivingLicenceImg}" class="bwGal"><img alt="图片" title="驾驶证正面照" src="<%=basePath%>${var.drivingLicenceImg}" style="width: 50px;height: 30px;"></a>
											</td>
											<td class='center'>
												<a href="<%=basePath%>${var.carIdentityImg}" class="bwGal"><img alt="图片" title="行驶证正面照" src="<%=basePath%>${var.carIdentityImg}" style="width: 50px;height: 30px;"></a>
											</td>
											<td class='center'>
												<c:if test="${var.authenticationTime=='' || var.authenticationTime==null}">等待中...</c:if>
												<c:if test="${var.authenticationTime!='' && var.authenticationTime!=null}">${fn:substring(var.authenticationTime,0,16)}</c:if>
											</td>
											<td class='center' id="${var.user_siji_id }">
												<c:if test="${var.authenticationState=='0' }">已失败</c:if>
												<c:if test="${var.authenticationState=='1' }">已通过</c:if>
												<c:if test="${var.authenticationState=='2' }">待审核</c:if>
											</td>
											<td class="center">
												<c:if test="${var.submitTime=='' || var.submitTime==null}">等待中...</c:if>
												<c:if test="${var.submitTime!='' && var.submitTime!=null}">${fn:substring(var.submitTime,0,16)}</c:if>
											</td>
											<td style="width: 128px;">
												<a class='btn btn-mini btn-danger' title="认证失败" onclick="checkedNo(this,'${var.user_siji_id }')" >认证失败</a>
												<a class='btn btn-mini btn-info' title="认证通过"  onclick="checkedYes(this,'${var.user_siji_id }')">认证通过</a>
											</td>
								</tr>
							
							</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<!-- 结束循环 -->	
				</tbody>
			</table>
			<div class="page-header position-relative">
			<table style="width:100%;">
				<tr>
					<td style="vertical-align:top;width:50px;">
						<a class="btn btn-small btn-success" onclick="checkedAll('确定批量认证通过吗?');">批量认证通过</a>
						<a class="btn btn-small btn-danger" onclick="checkedAll('确定批量认证失败吗?');" style="margin: -55px 0 0 115px;width:80px;text-align: center;">批量认证失败</a>
					</td>
					
					<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
				</tr>
			</table>
			</div>
		</form>
	</div>
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
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
		
				//全选 （是/否）
			function selectAll(){
				 var checklist = document.getElementsByName ("ids");
				   if(document.getElementById("zcheckbox").checked){
				   for(var i=0;i<checklist.length;i++){
				      checklist[i].checked = 1;
				   } 
				 }else{
				  for(var j=0;j<checklist.length;j++){
				     checklist[j].checked = 0;
				  }
				 }
			}
			//审核不通过
			function checkedNo(obj,tagID){
				 bootbox.confirm("确定要执行认证失败操作吗?", function(result) {
					if(result) {
						top.jzts();
						 var url='sijiUser/sijiAuthenticationState?tagID='+tagID+'&num=1';
						 $.get(url,function(data){
							 /* $("#"+tagID).html("已失败"); */
							 nextPage('${page.currentPage}');
						 });
					}
				});
			}
			
			//审核通过
			function checkedYes(obj,tagID){
				bootbox.confirm("确定要执行认证通过操作吗?", function(result) {
					if(result) {
						 var url='sijiUser/sijiAuthenticationState?tagID='+tagID+'&num=2';
						 $.get(url,function(data){
							 /* $("#"+tagID).html("已通过"); */
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
							var text="";
							if(msg=="确定批量认证失败吗?"){
								num="3";
								text="已失败";
							}else if(msg=="确定批量认证通过吗?"){
								num="4";
								text="已通过";
							}
							$.ajax({
								type:'post',
								url:'<%=basePath%>sijiUser/sijiAuthenticationState.do',
								dataTyoe:'json',
								cache: false,
								data:{
									"ids":str,
									"num":num
								},
								success:function(data){
									var arrayid=str.split(",");
									for(var i = 0;i < arrayid.length;i++){
										$("#"+arrayid[i]).html(text);
									}
									$.each(data.sijiUserList, function(i, sijiUserList){
										nextPage('${page.currentPage}');
								 });
								},
							});
						}
					}
				});	
			}
		</script>
		
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

