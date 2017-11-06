<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
<head lang="en">
     <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/yahu.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/weui1.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <title>新增地址</title>
    <style type="text/css">
	    .delete{
	            width: 24px;
	            height: 24px;
	            display: block;
	            position: absolute;
	            right: 15px;
	            bottom: 15px;
	            background: url(static/images/wmpc/delete.png) no-repeat;
	            background-size: 24px;
	        }
    	 .mask{
            width:100%;height:100%;position:fixed;top:0;left:0;background-color: rgba(0,0,0,0.3);display: none;
        }
        .noneBox,.noneBox02,.noneBox03{
            width:73%;height:144px;position: fixed;left:13%;top:218px;background-color: #fff;border-radius: 5px;
            z-index: 100;display: none;
        }
        .noneBox>p:nth-child(1){
            width:100%;height:88px;line-height:88px;text-align:center;border-bottom: 1px solid #c1c1c1;color:#000;
        }
        .noneBox>p:nth-child(2){
            text-align: center;line-height:55px;
        }
        .noneBox>p:nth-child(2)>a{
            padding:17px 37px;color:#71b2d0;
        }
        .cancel01{
            border-right: 1px solid #ddd;
        }
         .noneBox>p:nth-child(2)>a {
		    display: block;
		    width: 50%;
		    text-align:center;
		    float: left;
		    padding:0;
		    color: #71b2d0;
		}
    </style>
    <style type="text/css">
    	.icon1 {	   
		    top: 15px;		   
		}
			.mrImg{
			   position:fixed;
			   width: 32px;
	           height: 32px;
	           margin-top:-2px;
		}
    </style>
</head>
<body>
<!--顶部-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">管理收货地址</h1>
    <a href="api/h5KeHu/wd.do" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--主体-->
<div class="container" >
    <div class="weui-panel address-box" style="display: block;margin-bottom: 60px;">
        <div class="weui-panel__bd" style="padding:0;">
	             <c:forEach items="${pd}" var="pd">
            <div class="weui-media-box ">
	                <a href="api/h5KeHu/addressEdit.do?shouhuo_address_id=${pd.shouhuo_address_id}&tag=2" class="address-edit"></a>
	                <h4 class="weui-media-box__title"><span>${pd.linkmanName }</span><span>${pd.phone }</span><span class="identity">${pd.identity }</span></h4>
	                <p class="weui-media-box__desc" style="padding-top: 5px;">${pd.detailAddress }</p>
	                <span class="default-add">${pd.lable }</span>
                   	<c:if test="${pd.isDefault eq '1'}">
                	 <img class="mrImg" src="static/images/wmpc/moren.png"/>
		             </c:if>
	                <a href="javascript:void(0);" onclick="deletes('${pd.shouhuo_address_id}');" class="delete"></a>
            </div>
	             </c:forEach>
        </div>
    </div>
    <a href="api/h5KeHu/addressAdd.do?tag=2" class="address">
        <i></i>
        <span>新增地址</span>
    </a>
</div>
<div class="mask"></div>
<div class="noneBox">
    <p>您确定要删除该地址？</p>
    <p><a href="javascript:void(0)" class="cancel01" style="color:#000;">取消</a> <a href="javascript:void(0);" class="sure">确定</a></p>
</div>
<script type="text/javascript" >
	function deletes(id){//执行删除
		$(".delete").click(function(){
                var del=$(this);
                $(".mask").fadeIn();
                $(".noneBox").fadeIn();
                $(".cancel01").click(function(){
                    $(".mask").fadeOut();
                    $(".noneBox").fadeOut();
                });
                $(".sure").click(function(){
                    $(".mask").fadeOut();
                    del.parent().remove();
                    $(".noneBox").fadeOut();
				location.href="api/h5KeHu/addressDelete.do?shouhuo_address_id="+id+"&tag=2";
                });
            });
	} 
    $(function () {
        FastClick.attach(document.body);
    });
</script>
</body>
</html>
