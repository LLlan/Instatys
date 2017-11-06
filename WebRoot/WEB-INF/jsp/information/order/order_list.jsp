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
			<form action="order/orderList.do" method="post" name=orderForm" id="orderForm">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="order_id" value="${pd.order_id }" placeholder="请输入订单号" />
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
						<th>序号</th>
						<th>编号</th>
						<th>客户姓名</th>
						<th>配送员</th>
						<th>总收费金额</th>
						<th>配送费用</th>
						<th>额外费用</th>
						<th>创建时间</th>
						<th>订单状态</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
			<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty orderList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${orderList }" var="rlist" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${rlist.order_id }</td>
								<td>${rlist.customer_name }</td>
								<td></td>
								<td></td>
								<td>${rlist.peisonfei }</td>
								<td></td>
								<td>${rlist.order_time }</td>
								<td>
								<c:choose>
								 <c:when test="${rlist.order_result == '0'}">订单取消</c:when>
								 <c:when test="${rlist.order_result == '1'}">代付款</c:when>
								 <c:when test="${rlist.order_result == '2'}">代发货</c:when>
								 <c:when test="${rlist.order_result == '3'}">待收货</c:when>
								 <c:when test="${rlist.order_result == '4'}">已完成</c:when>
								 <c:when test="${rlist.order_result == '5'}">已结束</c:when>
								 <c:otherwise>系统取消</c:otherwise>
								</c:choose>
								</td>
								<td>
								<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${rlist.order_id }')" ><i class='icon-edit'></i></a>
								
								<a class='btn btn-mini btn-danger' title="删除"  onclick="del('${rlist.order_id }',true)"><i class='icon-trash'></i></a>
								</td>
								
							</tr>
						
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="13" class="center">您无权查看</td>
							</tr>
						</c:if>
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
		
		
		
				
		
		//检索
		function search(){
			top.jzts();
			$("#orderForm").submit();
		}
		
		//新增
		function add(){
		if("${QX.add}"==1){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>order/goAdd.do';
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
			}else{
				bootbox.confirm("您无权执行该操作！（有疑问请联系系统管理员）");
			}
		}
		
		
		//修改动态信息
		function edit(tid){
			if("${QX.edit}"==1){
				 top.jzts();
			   	 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = '<%=basePath%>order/toEdit.do?tid='+tid;
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
			if("${QX.del}"==1){
			bootbox.confirm("确定要删除该条数据吗?", function(result) {
					if(result) {
						top.jzts();
						var url = '<%=basePath%>order/del.do?tid='+tid;
						 $.get(url,function(data){
							nextPage('${page.currentPage}');
				
						}); 
					}
				});
			}else{
				bootbox.confirm("您无权执行该操作！（有疑问请联系系统管理员）");
			}
		}
		
		
		</script>
		
		
	</body>
</html>

