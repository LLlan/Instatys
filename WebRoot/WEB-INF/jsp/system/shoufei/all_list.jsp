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
	<%@ include file="../admin/top.jsp"%> 
	</head> 
<body>
		
<div class="container-fluid" id="main-container">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form  method="post" name="allForm" id="allForm">
			<input type="hidden" id="type" value="${pd.type}">
			<input type="hidden" id="tagtype">
			<table class="search_table">
				<tr>
					<td>
						 <select class="chzn-select" name="residence_id" id="residence_id" data-placeholder="请选择小区">
							<option value=""></option>
							<c:forEach items="${residenceList}" var="reList">
								<option value="${reList.RESIDENCE_ID}" <c:if test="${reList.RESIDENCE_ID eq pd.residence_id}">selected </c:if>>${reList.RESIDENCE_NAME}</option>
							</c:forEach>
						</select>
					</td>
					
					<td>
						<input type="text" class="dateMonth" name="YearMonths" value="${pd.YearMonths }" id="YearMonths" style="width: 150px;" placeholder="请选择月份">
					</td>
					
					<td style="vertical-align:top;">
						<select class="chzn-select changeWidth" name="resource" id="resource" data-placeholder="请选择缴费方式" style="vertical-align:top;">
							<option value=""></option>
							<option value="App缴费" <c:if test="${pd.resource eq 'App缴费' }">selected</c:if> >App缴费</option>
							<option value="物业缴费处" <c:if test="${pd.resource eq '物业缴费处' }">selected</c:if>>物业缴费处</option>
						</select>
					</td>
					
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="searchName" value="${pd.searchName}" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					
					<td>
						<a class='btn btn-primary btn-mini' title="查询" onclick="gosearch();" >查询</a>   
					</td>
					
				</tr>
			</table>
			<!-- 检索  -->
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>小区</th>
						<th>月份</th>
						<th>业主</th>
						<th>楼栋</th>
						<th>单元</th>
						<th>房号</th>	
						<th>需缴总额</th>
						<th>缴费时间</th>
						<th>缴费人</th>
						<th>操作人</th>
						<th>缴费方式</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
		<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty allList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${allList}" var="result" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${result.residence_name }</td>
								<td>${result.year_months }</td>
								<td>${result.proprietor_name }</td>
								<td>${result.loudong }</td>
								<td>${result.unit }</td>
								<td>${result.fanghao}</td>
								<td>${result.totol}</td>
								<td>
									${result.jiaofei_time}
								</td>
								<td>
									${result.jiaofei_name}
								</td>
								<td>
									${result.operation}
								</td>
								
								<td>
									${result.resource}
								</td>
								
								<td>
								<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${result.collect_cost_id }')" ><i class='icon-edit'></i></a>
								<a class='btn btn-mini btn-danger' title="删除"  onclick="del('${result.collect_cost_id }',true)"><i class='icon-trash'></i></a>  
								</td>
							</tr>
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="14" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="15" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				
				</tbody>
		</table>
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
		<script src="static/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入日期控件 -->
		<script type="text/javascript" src="static/js/common/datePicker/DatePicker.js"></script>
		<script type="text/javascript" src="static/js/common/datePicker/WdatePicker.js" defer="defer" ></script>
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function gosearch(){
			top.jzts();
			$("#allForm").submit();
		}
	
		//新增
		function add(){
			var type = $("#type").val();
			var residence_id=$("#residence_id").val();
			var YearMonths=$("#YearMonths").val();
			if(residence_id==""){
				bootbox.dialog("请选择小区", 
						[
						  {
							"label" : "关闭",
							"class" : "btn-small btn-success",
							"callback": function(){
								//Example.show("great success");
								}
							}
						 ]
					);//end
			}else if(YearMonths==""){
				bootbox.dialog("请选择月份", 
						[
						  {
							"label" : "关闭",
							"class" : "btn-small btn-success",
							"callback": function(){
								//Example.show("great success");
								}
							}
						 ]
					);//end
			}else{
				top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="新增";
				 diag.URL = '<%=basePath%>shoufei/goAdd.do?type='+type+'&residence_id='+residence_id+'&YearMonths='+YearMonths;
				 diag.Width = 505;
				 diag.Height = 525;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 top.jzts();
							 setTimeout("self.location.reload()",100);
						 }else{
							 var t = $("#type").val();
							 $("#tagtype").val(t);
							 $("#allForm").attr("action","shoufei/allListPage.do?type="+t);
							 nextPage(${page.currentPage});
						 }
					}
					diag.close();
				 };
				 diag.show();
			}
			 
		}
		
		//查看详情
		function edit(tid){
		 var type = $("#type").val();
			 top.jzts();
		   	 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="详情";
			 diag.URL = '<%=basePath%>shoufei/toEdit.do?tagID='+tid+'&type='+type;
			 diag.Width =505;
			 diag.Height = 525;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 var t = $("#type").val();
					 $("#tagtype").val(t);
					 $("#allForm").attr("action","shoufei/allListPage.do?type="+t);
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		
		//删除
		function del(tid,msg){
		 var type = $("#type").val();
			bootbox.confirm("确定要删除该条数据吗?", function(result){
				if(result){
					top.jzts();
					var url = '<%=basePath%>shoufei/del.do?tagID='+tid+'&type='+type;
					$.get(url,function(data){
					 	$("#allForm").attr("action","shoufei/allListPage.do?type="+data.type);
						nextPage(${page.currentPage});
					});
				}
			});
			
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result){
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
						  	else emstr += ';' + document.getElementsByName('ids')[i].id;
						  	
						  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
						  	else phones += ';' + document.getElementsByName('ids')[i].alt;
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
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
						    	data: {USER_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}else if(msg == '确定要给选中的用户发送邮件吗?'){
							sendEmail(emstr);
						}else if(msg == '确定要给选中的用户发送短信吗?'){
							sendSms(phones);
						}
						
					}
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		//导出excel
		function toExcel(){
			var USERNAME = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var ROLE_ID = $("#role_id").val();
			window.location.href='<%=basePath%>user/excel.do?USERNAME='+USERNAME+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
		}
		
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL 导入到数据库";
			 diag.URL = '<%=basePath%>user/goUploadExcel.do';
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		</script>
		
	</body>
</html>

