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
    <link rel="stylesheet" href="static/css/wmpc/yahu.css">
    <link href="static/css/wmpc/mobiscroll.scroller.css" rel="stylesheet" />
    <link href="static/css/wmpc/mobiscroll.scroller.android-ics.css" rel="stylesheet" />
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/city-picker.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery.uploadView.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <title>修改头像</title>
    <style>
        .save{
            display:block;width:90%;height:50px;line-height:50px;text-align:center;font-size:18px;color:#fff;maigin:20px auto auto;border-radius: 25px;background-color: #068dFF;
        }
        a{
        	cursor:none;
        }
    </style>
</head>
<body ontouchstart>
<!--头部-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">修改头像</h1>
    <a href="javascript:history.go(-1)" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<div class="container">
        <!--上传照片-->
        <div class="upload" style="background-color: #fff;margin-bottom: 10%;">
            <div class="picture">
                <div class="pic_lf js_showBox">
                    <img class="js_logoBox"  src="${pds.headImg }" style="width:150px;height:150px;">
                </div>
                <div class="pic_rt">
                    <form id="uploadForm" action="api/h5KeHu/${msg}.do" method="post" enctype="multipart/form-data">
                        <div class="btn-upload">
                            <a href="javascript:void(0);">上传图片</a>
                            <input type="hidden" value=""name="headImg"/>
                            <input class="js_upFile" type="file" name="imgFile" value="" accept="image/*">
                        </div>
                    </form>
                </div>
            </div>
        </div>
	<a href="javascript:void(0);" onclick="savetouxiang();" class="save">保存头像</a>
</div>
<script>
		function savetouxiang(){
		var form = new FormData($("#uploadForm")[0]);
			/* $.ajax({
				url:"api/h5KeHu/savetouxiang.do",
				type:"post",
				dataType:"json",
				data:form,
				cache: false,    
         	 	contentType: false,    
          		processData: false, 
				success:function(data){
				}
			}); */
			$("#uploadForm").submit();
		}
    $(function () {
        FastClick.attach(document.body);
    });
</script>
<script>
    $(document).ready(function(){
        //图片上传
        $(".js_upFile").uploadView({
            uploadBox: '.picture', //设置上传框容器
            showBox: '.js_showBox',  //设置显示预览图片的容器
            width: 150, //设置预览图片的宽度
            height: 150, //设置预览图片的高度
            allowType: ["gif", "jpeg", "jpg", "bmp", "png"],
            maxSize: 4, //设置允许上传图片的最大尺寸，单位M
            success:function(e){//上传成功时的回调函数
                $(".js_logoBox").show();
                //alert("图片上传成功");
            }
        });

    });
</script>
<script src="static/js/wmpc/zepto.js"></script>
<script src="static/js/wmpc/mobiscroll.zepto.js" type="text/javascript"></script>
<script src="static/js/wmpc/mobiscroll.core.js" type="text/javascript"></script>
<script src="static/js/wmpc/mobiscroll.scroller.js" type="text/javascript"></script>
<script src="static/js/wmpc/mobiscroll.area.js"></script>
<script src="static/js/wmpc/mobiscroll.scroller.android-ics.js"></script>
<script src="static/js/wmpc/mobiscroll.i18n.zh.js"></script>
<script>
    var valo = $("#area").attr("areaid");
    $('#area').scroller('destroy').scroller({ preset: 'area', theme: 'android-ics light', display: 'bottom',valueo:valo });


</script>
</body>
</html>