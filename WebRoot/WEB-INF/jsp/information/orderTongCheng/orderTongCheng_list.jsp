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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	
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
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索开始  -->
			<form action="orderTongcheng/orderTongchengListPage.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="searchName" value="${pd.searchName }" placeholder="输入车主真实姓名!" /> <i id="nav-search-icon"class="icon-search"></i> </span>
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
						<th class="center">乘客姓名</th>
						<th class="center">乘客手机号码</th>
						<th class="center">乘车距离</th>
						<th class="center">目的地</th>
						<th class="center">出发地</th>
						<th class="center">乘车金额</th>
						<th class="center">司机姓名</th>
						<th class="center">司机手机号码</th>
						<th class="center">车型</th>
						<th class="center">车辆的颜色</th>
						<th class="center">车牌号</th>
						<th class="center">接单时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty orderTongchengList}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${orderTongchengList}" var="var" varStatus="vs">
								<tr>
									<td class='center' style="width: 30px;">
										<label><input type='checkbox' name='ids' value="${var.order_tongcheng_id}" /><span class="lbl"></span></label>
									</td>
									<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class="center">${var.userName}</td>
											<td class="center">${var.phone}</td>
											<td class="center">${var.mileage}</td>
											<td class="center">${var.departurePlace}</td>
											<td class="center">${var.destination}</td>
											<td class="center">${var.radeAmount}</td>
											<td class="center">${var.userNamesiji}</td>
											<td class="center">${var.phonesiji}</td>
											<td class="center">${var.carType}</td>
											<td class="center">${var.carColor}</td>
											<td class="center">${var.carNumber}</td>
											<td class="center">${var.orderTime}</td>
											<td style="width: 68px;">
												<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.order_tongcheng_id }')" ><i class='icon-edit'></i></a>
												<a class='btn btn-mini btn-danger' title="删除"  onclick="del('${var.order_tongcheng_id }')"><i class='icon-trash'></i></a>
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
						<td style="vertical-align:top;">
							<c:if test="${QX.add == 1 }">
							<a class="btn btn-small btn-success" onclick="add();">新增</a>
							</c:if>
							<c:if test="${QX.del == 1 }">
							<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >批量删除<!-- <i class='icon-trash'> --></i></a>
							</c:if>
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
		
		<script type="text/javascript">
			$(top.hangge());
			//检索
			function search(){
				top.jzts();
				$("#Form").submit();
			}
			
			//新增
			function add(){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="新增";
				 diag.URL = '<%=basePath%>orderTongcheng/goAdd.do';
				 diag.Width = 450;
				 diag.Height = 355;
				 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 var num = '${page.currentPage}';
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
			
			//删除
			function del(Id){
				bootbox.confirm("确定要删除吗?", function(result) {
					if(result) {
						top.jzts();
						var url = "<%=basePath%>orderTongcheng/del.do?order_tongcheng_id="+Id;
						$.get(url,function(data){
							nextPage('${page.currentPage}');
						});
					}
				});
			}
			
			//修改
			function edit(Id){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = '<%=basePath%>orderTongcheng/goEdit.do?order_tongcheng_id='+Id;
				 diag.Width = 450;
				 diag.Height = 355;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 nextPage('${page.currentPage}');
					}
					diag.close();
				 };
				 diag.show();
			}
			<%-- $.ajax({
					type:'post',
					url:'<%=basePath%>orderTongcheng/orderTongchengListPage.do',
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
						$.each(data.orderTongchengList, function(i, orderTongchengList){
							nextPage('${page.currentPage}');
					 });
					},
				}); --%>
		</script>
	</body>
</html>

