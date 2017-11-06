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
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索开始  -->
			<form action="sijiUser/informationChangtulistPage.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="searchName" value="${pd.searchName }" placeholder="用户名!" /> <i id="nav-search-icon"class="icon-search"></i> </span>
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
						<th class="center">车主姓名</th>
						<th class="center">联系电话</th>
						<th class="center">出发时间</th>
						<th class="center">出发城市</th>
						<th class="center">出发地</th>
						<th class="center">到达城市</th>
						<th class="center">目的地</th>
						<th class="center">拼车费用</th>
						<th class="center">里程数</th>
						<th class="center">可乘人数</th>
						<th class="center">发布时间</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty sijiInformationChangTuList}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${sijiInformationChangTuList}" var="var" varStatus="vs">
								<tr>
									<td class='center' style="width: 30px;">
										<label><input type='checkbox' name='ids' value="${var.information_changtu_id}" /><span class="lbl"></span></label>
									</td>
									<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class="center">${var.userName}</td>
											<td class="center">${var.phone}</td>
											<td class="center">${var.departureTime}</td>
											<td class="center">${var.departureCity}</td>
											<td class="center">${var.departurePlace}</td>
											<td class="center">${var.arrivalCity}</td>
											<td class="center">${var.destination}</td>
											<td class="center">${var.carpoolFee}</td>
											<td class="center">${var.mileage}</td>
											<td class="center">${var.userNum}</td>
											<td class="center">${var.releaseTime}</td>
										<%-- 	<td style="width: 68px;">
												<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.bank_card_tixian_id }')" ><i class='icon-edit'></i></a>
												<a class='btn btn-mini btn-danger' title="删除"  onclick="del('${var.bank_card_tixian_id }')"><i class='icon-trash'></i></a>
											</td> --%>
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
						<%-- <td style="vertical-align:top;">
							<c:if test="${QX.add == 1 }">
							<a class="btn btn-small btn-success" onclick="add();">新增</a>
							</c:if>
							<c:if test="${QX.del == 1 }">
							<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >批量删除<!-- <i class='icon-trash'> --></i></a>
							</c:if>
						</td> --%>
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
		 diag.Width = 400;
		 diag.Height = 400;
		 diag.CancelEvent = function(){ //关闭事件
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				var num = '${page.currentPage}';
			 	if(num == '0'){
			 		top.jzts();
			 		location.href = location.href;
			 	}else{
			 		nextPage('${page.currentPage}');
			 	}
			}
			 diag.close();
		 };
		 diag.show();
	}
	//修改
	function edit(Id){
		 top.jzts();
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑";
		 diag.URL = '<%=basePath%>orderTongcheng/goEdit.do?Id='+Id;
		 diag.Width = 400;
		 diag.Height = 400;
		 diag.CancelEvent = function(){ //关闭事件
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				 nextPage('${page.currentPage}');
			}
			 diag.close();
		 };
		 diag.show();
	}
	//删除
		function del(Id){
			bootbox.confirm("确定要删除该条数据吗?", function(result) {
				if(result) {
					top.jzts();
					var url = '<%=basePath%>orderChangtu/del.do?order_changtu_id='+Id;
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}
		$(function() {
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					//$(this).closest('tr').toggleClass('selected');
				});	
			});
		});
		//批量删除
		function makeAll() {
			bootbox.confirm('确定执行批量删除操作吗？',function(result){
				if(result){
					var str='';
					for ( var i = 0; i < document.getElementsByName('ids').length; i++) {
						//alert(document.getElementsByName('ids').length);
						if(document.getElementsByName('ids')[i].checked){
							if(str==''){
								str+=document.getElementsByName('ids')[i].value;
							}else{
								str+=','+document.getElementsByName('ids')[i].value;
							}
						}
					}
					//alert("str:"+str);
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
				            time:4
				        });
						return;
					}else{
						$.ajax({
							type:'post',
							url:'<%=basePath%>shangjia/goodsdelete.do',
							dataTyoe:'json',
							cache: false,
							data:{
								"ids":str
							},
							success:function(data){
								nextPage('${page.currentPage}');
							},
						});
					}
				}
			});	
		}
</script>
		</script>
	</body>
</html>

