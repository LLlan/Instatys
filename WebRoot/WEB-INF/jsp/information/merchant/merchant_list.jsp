<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 引入标签库 -->
<%@include file="/common/include/taglibs.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	</head> 
<body>
		
<div class="container-fluid" id="main-container">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="merchant/merchantListPage.do" method="post" name=merchantForm" id="merchantForm">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="proprietorName" value="${pd.proprietorName }" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if> 
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
				
					<tr>
						<th class="center" onclick="selectAll()">
							<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>联系人</th>
						<th>联系电话</th>
						<th>门店照片</th>
						<th>手持身份证照片</th>
						<th>营业执照</th>
						<th>产品LOGO照片</th>
						<th>店铺名称</th>
						<th>经营品类</th>
						<th>店铺地址</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
			<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty merchantList}">
						<c:forEach items="${merchantList }" var="rlist" varStatus="vs">
							<tr style="height: 10px">
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${rlist.merchant_id}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<%-- <td>
									<c:choose>
										<c:when test="${not empty rlist.image_url }">
											<img alt="" src="${rlist.image_url}" width="100" height="100">
											<br/>
											<a href="javascript:void(0);" onclick="getAllImageById('${rlist.residence_dynamic_id}')">更多图片</a>                            
										</c:when>
										<c:otherwise>
											无
										</c:otherwise>
									</c:choose>
								</td> --%>
								<td>${rlist.merchant_staff}</td>
								<td>${rlist.merchant_tel }</td>
								<td><img src="${rlist.merchant_picture }" style="height: 50px"> </td>
								<td><img src="${rlist.merchant_photo }" style="height: 50px"></td>	
								<td><img src="${rlist.merchant_license }" style="height: 50px"></td>
								<td><img src="${rlist.merchant_logo }" style="height: 50px"></td>
								<td>${rlist.merchant_name }</td>
								<td>${rlist.merchant_type }</td>
								<td>${rlist.merchant_address }</td>	
								<td>
								<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${rlist.merchant_id }')" ><i class='icon-edit'></i></a>
								
								<a class='btn btn-mini btn-danger' title="删除"  onclick="del('${rlist.merchant_id }',true)"><i class='icon-trash'></i></a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="14" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				
				</tbody>
	</table>
	<!-- 分页列表 -->
		<div class="page-header position-relative">
		 <table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		 </table>
		</div>
	</form>
	</div>
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 页面消息 -->	
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		$(top.hangge());
		
		function getAllImageById(id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="图片展示";
			 diag.URL = '<%=basePath%>dynamic/getImagePage.do?dynamic_id='+id;
			 diag.Width = 755;
			 diag.Height = 525;
			 diag.CancelEvent = function(){ //关闭事件
				 /* if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					  if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						nextPage("${page.currentPage}");
					 }; 
				} */
				diag.close();
			 };
			 diag.show();
		};
		
		
		
		//检索
		function search(){
			top.jzts();
			$("#merchantForm").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>merchant/goAdd.do';
			 diag.Width = 755;
			 diag.Height = 555;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					  if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						nextPage('${page.currentPage}');
					 } 
				}
				diag.close();
			 };
			 diag.show();
		}
		
		
		//修改动态信息
		function edit(tid){
			if("${QX.edit}"==1){
				 top.jzts();
			   	 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = '<%=basePath%>merchant/toEdit.do?tid='+tid;
				 diag.Width = 755;
				 diag.Height = 555;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage('${page.currentPage}');
					}
					diag.close();
				 };
				 diag.show();
			}else{
				bootbox.confirm("您无权执行该操作！（有疑问请联系系统管理员）");
			}
		}
		
		
		//删除
		function del(tid,msg){
			bootbox.confirm("确定要删除该条数据吗?", function(result){
					if(result) {
						top.jzts();
						var url = '<%=basePath%>merchant/del.do?tid='+tid;
						 $.get(url,function(data){
							nextPage('${page.currentPage}');
						}); 
					};
				});
		}
		
		//全选（是/否）
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
		
		//批量删除
		
		function makeAll(msg){
			
			if(confirm(msg)){ 
				
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						alert("您没有选择任何内容!"); 
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>merchant/deleteAll.do?',
								//tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage('${page.currentPage}');
									 });
								}
							});
						}
					}
			}
		}
		
		</script>
		
	</body>
</html>

