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
	<link rel="stylesheet" href="static/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="static/css/bootstrap-responsive.min.css"/>
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
	<script type="text/javascript" src="static/js/wmpc/layer/layer.js"></script>
</head>
<body>
<div id="page-content" class="clearfix">
<div class="row-fluid">
	<!-- 检索  -->
	<form action="<%=basePath%>/shangjia/getlistPage.do" method="post" name="userForm" id="userForm">
	<table>
		<tr>
			<td>
				<span class="input-icon">
					<input autocomplete="off" id="nav-search-input" type="text" name="searchName" value="${pd.searchName }" placeholder="这里输入关键词" />
					<i id="nav-search-icon" class="icon-search"></i>
				</span>
			</td>
			<c:if test="${QX.cha == 1 }">
			<td style="vertical-align:top;">
				<button class="btn btn-mini btn-light" title="检索">
					<i id="nav-search-icon" class="icon-search"></i>
				</button>
			</td>
			</c:if>
		</tr>
	</table>
	<!-- 检索  -->
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center">
				<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
			</th>
			<th class="center"  style="width: 50px;">序号</th>
			<th class='center'>用户名</th>
			<th class='center'>店铺名称</th>
			<th class='center'>商家电话</th>
			<%--<th class='center'>店铺logo</th>
			<th class='center'>商家电话</th>
			<th class='center'>商家公告</th>
			<th class='center'>总资产</th>
			<th class='center'>账户余额</th>
			<th class='center'>起送金额</th>--%>
			<th class='center'>注册时间</th>
			<th class='center'>最近登录时间</th>
			<th class='center'>登录IP</th>
			<th class='center'>备注</th>
			<th class='center'>账号状态</th>
			<th class='center'>认证提交时间</th>
			<th class='center'>认证审核时间</th>
			<th class='center'>认证状态</th>
			<th class='center'>认证资料</th>
			<%--<th class='center'>基本信息</th>--%>
			<th class='center'>操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach items="${list}" var="var" varStatus="vs">
				<tr>
					<td class='center' style="width: 30px;">
				 		<label><input type='checkbox' name='ids' value="${var.user_shangjia_id }"/><span class="lbl"></span></label>
					</td>
					<td class='center' style="width: 30px;">${vs.index+1}</td>
					<td class='center'>${var.phone}</td>
					<td class='center'>${var.shopName}</td>
					<td class='center'>${var.tel_phone}</td>
					<%--<td class='center'>
						<img alt="" src="${var.logoImg}" width="40px;" height="40px;">
					</td>
					<td class='center'>${var.tel_phone}</td>
					<td class='center'>${var.shopNotice}</td>
					<td class='center'>${var.zongzichan}</td>
					<td class='center'>${var.zhanghuyue}</td>
					<td class='center'>${var.deliveryAmount}</td>--%>
					<td class='center'>${var.registerTime}</td>
					<td class='center'>${var.last_login_time}</td>
					<td class='center'>${var.ip}</td>
					<td class='center'>${var.bz}</td>
					<td class='center'>
						<c:if test="${var.status=='0'}"><div style='background-color: red; color: white;'>禁用</div></c:if>
						<c:if test="${var.status=='1'}"><div style='background-color: #15c300; color: white;'>正常</div></c:if>
					</td>
					<td class='center'>
						<c:if test="${var.submitTime=='' || var.submitTime==null}">还未进行认证...</c:if>
						<c:if test="${var.submitTime!='' && var.submitTime!=null}">${var.submitTime}</c:if>
					</td>
					<td class='center'>
						<c:if test="${var.authenticationTime=='' || var.authenticationTime==null}">等待中...</c:if>
						<c:if test="${var.authenticationTime!='' && var.authenticationTime!=null}">${var.authenticationTime}</c:if>
					</td>
					<td class='center'>
						<c:if test="${var.authenticationState=='0' }"><div style='background-color: red; color: white;'>认证失败</div></c:if>
						<c:if test="${var.authenticationState=='1' }"><div style='background-color: #15c300; color: white;'>认证通过</div></c:if>
						<c:if test="${var.authenticationState=='2' }">等待审核</c:if>
						<c:if test="${var.authenticationState=='' || var.authenticationState==null }">等待认证</c:if>
					</td>
					<td class='center'><a href="javascript:void(0)" onclick="lookRenZhengInformation('${var.user_shangjia_id }')">查看</a></td>
					<%--
					<td class='center'><a href="javascript:void(0)" onclick="lookBaseInformation('${var.user_shangjia_id }')">查看</a></td>
					--%>
					<td style="width: 80px;">
						<a class='btn btn-mini btn-info' title="拒绝" onclick="checkedNo('${var.user_shangjia_id }')" >拒绝</a>
						<a class='btn btn-mini btn-danger' title="通过"  onclick="checkedYes('${var.user_shangjia_id }')">通过</a>
					</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100" class="center">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;width:50px;">
					<a class="btn btn-small btn-success" onclick="checkedAll('确定批量通过吗?');">批量通过</a>
					<a class="btn btn-small btn-danger" onclick="checkedAll('确定批量拒绝吗?');" style="margin: -55px 0 0 88px;width: 52px;text-align: center;">批量拒绝</a>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
	</form>
</div>
</div>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(function() {
		//复选框
		$('table th input:checkbox').on('click' , function(){
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox')
			.each(function(){
				this.checked = that.checked;
			});	
		});
	});
	//审核不通过
	function checkedNo(tagID){
		bootbox.confirm("确定要要执行拒绝操作吗?", function(result) {
			if(result) {
				top.jzts();
				var url = '<%=basePath%>shangjia/syschecked.do?ids='+tagID+'&num=0';
				$.get(url,function(data){
					nextPage('${page.currentPage}');
				});
			}
		});
	}
	//审核通过
	function checkedYes(tagID){
		bootbox.confirm("确定要要执行通过操作吗?", function(result) {
			if(result) {
				top.jzts();
				var url = '<%=basePath%>shangjia/syschecked.do?ids='+tagID+'&num=1';
				$.get(url,function(data){
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
					if(msg=="确定批量拒绝吗?"){
						num="0";
					}else if(msg=="确定批量通过吗?"){
						num="1";
					}
					$.ajax({
						type:'post',
						url:'<%=basePath%>shangjia/syschecked.do',
						dataTyoe:'json',
						cache: false,
						data:{
							"ids":str,
							"num":num
						},
						success:function(data){
							nextPage('${page.currentPage}');
						},
					});
				}
			}
		});	
	}
	//查看基本信息
	/*function lookBaseInformation(id){
		//获取基本信息
		$.post('shangjia/getRenZhengInforById.do',{user_shangjia_id:id},function(data){
			var str='';
			var pd=data.pd;
			str+='<div id="lookBaseInformation" style="display: none;">';
			str+='<table>';
			str+='<tr>';
			str+='<td class="firsttd">头像：</td>';
			str+='<td class="secondtd"><img src="<%=basePath %>'+pd.headImg+'" width="100px" height="100px"></td>';
			str+='</tr>';
			str+='<tr>';
			str+='<td>名称：</td>';
			str+='<td class="secondtd">'+pd.youName+'</td>';
			str+='</tr>';
			if(pd.identity=='1'){
				str+='<tr>';
				str+='<td>职位：</td>';
				str+='<td class="secondtd">'+pd.position+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>标注：</td>';
				str+='<td class="secondtd">'+pd.mark+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>联系方式：</td>';
				str+='<td class="secondtd">'+pd.linkMethod+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>就诊地址：</td>';
				str+='<td class="secondtd">'+pd.address+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>个人简介：</td>';
				str+='<td class="secondtd">'+pd.introduct+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>擅长领域：</td>';
				str+='<td class="secondtd">'+pd.goodField+'</td>';
				str+='</tr>';
			}else if(pd.identity=='2'){
				str+='<tr>';
				str+='<td>等级：</td>';
				if(pd.dengji=='1'){
					str+='<td class="secondtd">一级乙等</td>';
				}else if(pd.dengji=='2'){
					str+='<td class="secondtd">一级甲等</td>';
				}else if(pd.dengji=='3'){
					str+='<td class="secondtd">二级乙等</td>';
				}else if(pd.dengji=='4'){
					str+='<td class="secondtd">二级甲等</td>';
				}else if(pd.dengji=='5'){
					str+='<td class="secondtd">三级乙等</td>';
				}else {
					str+='<td class="secondtd">三级甲等</td>';
				}
				
				str+='</tr>';
				str+='<tr>';
				str+='<td>标注：</td>';
				str+='<td class="secondtd">'+pd.mark+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>联系方式：</td>';
				str+='<td class="secondtd">'+pd.phone+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>地址：</td>';
				str+='<td class="secondtd">'+pd.city+pd.address+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>简介：</td>';
				str+='<td class="secondtd">'+pd.introduct+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>擅长领域：</td>';
				str+='<td class="secondtd">'+pd.goodField+'</td>';
				str+='</tr>';
			}else{
				str+='<tr>';
				str+='<td>类型：</td>';
				if(pd.category=='1'){
					str+='<td class="secondtd">中药</td>';
				}else if(pd.category=='2'){
					str+='<td class="secondtd">西药</td>';
				}else{
					str+='<td class="secondtd">中药/西药</td>';
				}
				
				str+='</tr>';
				str+='<tr>';
				str+='<td>标注：</td>';
				str+='<td class="secondtd">'+pd.mark+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>联系方式：</td>';
				str+='<td class="secondtd">'+pd.phone+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>地址：</td>';
				str+='<td class="secondtd">'+pd.city+pd.address+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>简介：</td>';
				str+='<td class="secondtd">'+pd.introduct+'</td>';
				str+='</tr>';
				str+='<tr>';
				str+='<td>经营范围：</td>';
				str+='<td class="secondtd">'+pd.goodField+'</td>';
				str+='</tr>';
			}
			str+='</table>';
			str+='</div>';
			$("#lookBaseInformation").remove();
			$("body").append(str);
			layer.open({
		        type: 1
		        ,title: "基本信息" //不显示标题栏   title : false/标题
		        ,closeBtn: 1//是否显示关闭按钮0和1
		        ,area:['620px', '540px']//宽高
		        ,shade: 0.5//透明度
		        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
		        ,resize: false
		        ,skin: 'layui-layer-lan'
		        ,btn: ['退出']
		        ,btnAlign: 'c'
		        ,moveType: 1 //拖拽模式，0或者1
		        ,shadeClose: false //开启遮罩关闭
		        ,content: $("#lookBaseInformation")
		    });
		});
	}*/
	//查看认证资料
	function lookRenZhengInformation(id){
		$.post('shangjia/getRenZhengInforById.do',{user_shangjia_id:id},function(data){
			var resultData=data.resultData;
			if(JSON.stringify(resultData) != "{}"){
				var str='<div style="width: 800px;height: auto;display: none;" id="lookRenZhengInformation">';
				
				str+='<div style="margin-bottom: -20px;margin-left: 20px;">门脸图</div>';
				str+='<p style="margin:20px 20px 20px 20px;">';
				str+='<img src="<%=basePath %>'+ resultData.menlianImg +'">';
				str+='</p>';
				str+='<div style="border: 3px dashed #6cb708;margin-left: 20px;"></div>';
				
				str+='<div style="margin-bottom: -20px;margin-left: 20px;">手持身份证合影图片</div>';
				str+='<p style="margin:20px 20px 20px 20px;">';
				str+='<img src="<%=basePath %>'+ resultData.handIdentityImg +'">';
				str+='</p>';
				str+='<div style="border: 3px dashed #6cb708;margin-left: 20px;"></div>';
				
				str+='<div style="margin-bottom: -20px;margin-left: 20px;">营业执照照片</div>';
				str+='<p style="margin:20px 20px 20px 20px;">';
				str+='<img src="<%=basePath %>'+ resultData.businessLicenceImg +'">';
				str+='</p>';
				str+='<div style="border: 3px dashed #6cb708;margin-left: 20px;"></div>';
				
				str+='<div style="margin-bottom: -20px;margin-left: 20px;">餐饮服务许可证照片</div>';
				str+='<p style="margin:20px 20px 20px 20px;">';
				str+='<img src="<%=basePath %>'+ resultData.licenceImg +'">';
				str+='</p>';
				str+='<div style="border: 3px dashed #6cb708;margin-left: 20px;"></div>';
				
				str+='<div style="margin-bottom: -20px;margin-left: 20px;">店铺logo</div>';
				str+='<p style="margin:20px 20px 20px 20px;">';
				str+='<img src="<%=basePath %>'+ resultData.logoImg +'">';
				str+='</p>';
				str+='<div style="border: 3px dashed #6cb708;margin-left: 20px;"></div>';
				
				$.each(resultData.imgPaths1,function(i,j){
					str+='<div style="margin-bottom: -20px;margin-left: 20px;">店内图</div>';
					str+='<p style="margin:20px 20px 20px 20px;">';
					str+='<img src="<%=basePath %>'+ j.imgPath +'">';
					str+='</p>';
					str+='<div style="border: 3px dashed #6cb708;margin-left: 20px;"></div>';
				});
				
				$.each(resultData.imgPaths2,function(i,j){
					str+='<div style="margin-bottom: -20px;margin-left: 20px;">商品图</div>';
					str+='<p style="margin:20px 20px 20px 20px;">';
					str+='<img src="<%=basePath %>'+ j.imgPath +'">';
					str+='</p>';
					str+='<div style="border: 3px dashed #6cb708;margin-left: 20px;"></div>';
				});
				
				str+='</div>';
				$("#lookRenZhengInformation").remove();
				$("body").append(str);
			}else{
				var str='<div style="width: 800px;height: auto;font-size: 30px;font-family: cursive;margin-left: 126px;margin-top: 208px;display:none;" id="lookRenZhengInformation">';
				str+='<span>该用户还未进行开店申请,无法看到认证资料!</span>';
				str+='</div>';
				$("#lookRenZhengInformation").remove();
				$("body").append(str);
			}
			layer.open({
		        type: 1
		        ,title: "认证资料" //不显示标题栏   title : false/标题
		        ,closeBtn: 1//是否显示关闭按钮0和1
		        ,area:['850px', '600px']//宽高
		        ,shade: 0.5//透明度
		        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
		        ,resize: false
		        ,skin: 'layui-layer-lan'
		        ,btn: ['退出']
		        ,btnAlign: 'c'
		        ,moveType: 1 //拖拽模式，0或者1
		        ,shadeClose: false //开启遮罩关闭
		        ,content: $("#lookRenZhengInformation")
		    });
		});
	}
</script>
</html>