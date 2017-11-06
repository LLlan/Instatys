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
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
  <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
  <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
  <link rel="stylesheet" href="static/css/wmpc/style.css">
  <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
  <title>会员中心</title>
  <style>
  	.center-list-icon{
		Width:15px;
	}
	  .weui-cell_access:active{
	   	background-color:#fff;
	   }	
  </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">会员中心</h1>
  <a href="api/h5KeHu/wd.do" class="icon1" "><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<div class="weui-content" style="margin-top:60px;">
  <div class="weui-panel">
    <div class="weui-panel__ft">
      <a href="api/h5KeHu/gotouxiang.do"  class="weui-cell weui-cell_access weui-cell_link none-borT">
        <div class="weui-cell__bd picT1">头像  </div>
        <span class="weui-cell__ft">
        <!-- <img src="static/images/wmpc/u132.png" alt="" class="picT"/> -->
          	<c:if test="${pds.headImg!='' && pds.headImg!=null }">
          		<img src="${pds.headImg }" alt="" class="picT"/>
          	</c:if>
           	<c:if test="${pds.headImg=='' || pds.headImg==null }">
           		 <img src="static/images/wmpc/u132.png" alt="" class="picT"/>
           	</c:if>
        </span>
      </a>    
    </div>
    <div class="weui-panel__ft">
      <a href="api/h5KeHu/goyonghuming.do" class="weui-cell weui-cell_access weui-cell_link">
        <div class="weui-cell__bd picT1">用户名 
        	<span class="name1">
        		<c:if test="${pds.userName!='' && pds.userName!=null }">
		          	${pds.userName}
	          	</c:if>
	           	<c:if test="${pds.userName=='' || pds.userName==null }">
	           		 未设置
	           	</c:if>
        	</span>
        </div>
        <span class="weui-cell__ft"></span>
      </a>    
    </div>
  </div>
 <div class="bangding">账户绑定</div>
  <div class="weui-panel">
    <div class="weui-panel__bd">
      <div class="weui-media-box weui-media-box_small-appmsg">
        <div class="weui-cells">
          <a class="weui-cell weui-cell_access" href="api/h5KeHu/changeTel.do">
            <div class="weui-cell__hd"><img src="static/images/wmpc/05_03.gif" alt="" class="center-list-icon"></div>
            <div class="weui-cell__bd weui-cell_primary">
              <p class="center-list-txt pos">手机号 <span class="shoujihao">${pds.phone}</span></p>
            </div>
            <span class="weui-cell__ft"></span>
          </a>
        </div>
      </div>
    </div>
  </div>
 <%--  <div class="bangding">安全设置</div>
  <div class="weui-panel">
    <div class="weui-panel__bd">
      <div class="weui-media-box weui-media-box_small-appmsg">
        <div class="weui-cells">
          <a class="weui-cell weui-cell_access" href="api/h5KeHu/changePsds.do">
            <div class="weui-cell__hd"><img src="static/images/wmpc/05_06.png" alt="" class="center-list-icon"></div>
            <div class="weui-cell__bd weui-cell_primary">
              <p class="center-list-txt pos">支付密码
	               <span class="shoujihao ">
	               	<c:if test="${pds.payPassword!='' && pds.payPassword!=null }">
		          		已设置
		          	</c:if>
		           	<c:if test="${pds.payPassword=='' || pds.payPassword==null }">
		           		 未设置
		           	</c:if>
	               </span>
              </p>
            </div>
            <span class="weui-cell__ft"></span>
          </a>
        </div>
      </div>
    </div>
  </div> --%>

</div>
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>

<script>
	function touxiang(phone){
		location.href="api/h5login/gotouxiang.do";
	};
	function yonghuming(backCode,phone){
		location.href="api/h5login/goyonghuming.do?backCode='"+backCode+"'&phone='"+phone+"'";
	};
	function xgphone(backCode,phone){
		location.href="api/h5login/xgphone.do?backCode='"+backCode+"'&phone='"+phone+"'";
	};
	
	

  $(function() {
    FastClick.attach(document.body);
  });
</script>
</body>
</html>
