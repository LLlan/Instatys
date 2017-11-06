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
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link href="static/css/wmpc/common1.css" type="text/css" rel="stylesheet"/>
    <link href="static/css/wmpc/index1.css" type="text/css" rel="stylesheet"/>

    <style>
        .mask{
            position:fixed;z-index:100;left:0;top:0;width:100%;height:100%;background:rgba(0,0,0,0.5)
        }
        .bground{
            background:url(static/images/wmpc/wyes1_06.png) no-repeat;background-size: 21px 21px;;;
        }
        .xiangfa {
            height: 180px;
        }
        .xiangfa textarea {
            color:#333;
        }
        .z_file .add-img {
            border:1px dashed #ccc;
        }
        .z_photo .up-img {
		    display: block;
		}
		.z_photo .up-section:nth-child(6){
			margin-right:0;
		}
		.z_photo .up-section:nth-child(12){
			margin-right:0;
		}
		.z_photo .up-section:nth-child(18){
			margin-right:0;
		}
		.z_photo .up-section {
            margin-right:1%;
            margin-bottom:5px;
        }
        .wy-header-icon-back a {
		    margin: 10px 0 0 10px;
		}
		.amask .mask-content {
		    width: 80%;
		    position: absolute;
		    top: 30%;
		    left: 10%;
		    margin-left:0;
		    margin-top:0;
		    background: white;
		    height: 160px;
		    text-align: center;
		}
		.upimg-div .up-section{
			width:32.6%;
		}
		.upimg-div .up-section img{
			width:100%;
		}
		.img-box .upimg-div .z_file{
			width:32.6%;
		}
		.img-box .upimg-div .z_file img{
			width:100%;
		}
		.up-section .up-span{
			width:100%;
		}
		.upimg-div .up-section .close-upimg{
			width:15px;height:15px;
		}
		.up-section:hover{
			border:none;
		}
    </style>
<script type="text/javascript">
	//保存
	function fabu(){
		var imgPath=$("#imgPath").val();//图片
		var content=$("#content").val();//发布内容
		var categoryName=$("#cName").val();//类型
		if(content==0){
			layer.tips('请填写您发布内容！', '#content', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		if(categoryName==""){
			layer.tips('请选择标签！', '#qxx', {
	     		  tips: [1, '#D9006C'],
	     		  time: 3000
	     	});
			return;
		}
		$("#uploadForm").submit();
	}
	
</script>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div class="wy-header header" style="height:65px;">
    <div class="wy-header-icon-back"><a href="javascript:history.go(-1)" style="background:none;width:100px;color:#fff;font-size: 16px;">取消</a></div>
    <div class="wy-header-title" style="position: relative">发布动态 <a href="javascript:void(0);" onclick="fabu();" class="fabiaoDong" style="background-color: #fff;color:#4779aa;padding:0 10px;font-size:14px;border-radius: 4px;position: absolute;right:-35px;bottom:10px;;line-height: 25px;">发布</a></div>
</div>
<!--头部结束-->
<!--主体-->

<form id="uploadForm" action="api/h5KeHu/${msg}.do" method="post" enctype="multipart/form-data">
	<input type="hidden" id="cid" name="hudong_category_id">
	<input type="hidden" id="cName" name="categoryName">
	<div class="xiangfa">
	    <textarea name="content" id="content" style="border:none; height: 100%" placeholder="这一刻的想法..."></textarea>
	</div>
	<div class="img-box full" style="margin:0;width:100%;background-color: #fff;">
	    <section class="img-section">
	        <div class="z_photo upimg-div clear" style="border:none; ">
	            <section class="z_file fl"  style="margin:0;">
	                <img src="static/images/wmpc/add1.png" class="add-img" style="margin:0;">
	                <input type="file"  name="imgPath" onchange="imgUponchange(this);" class="file"  accept="image/*" multiple style="margin:0;width:100%;"/>
	            </section>
	        </div>
	    </section>
	</div>
	<aside class="amask works-mask">
	    <div class="mask-content">
	        <p class="del-p">您确定要删除作品图片吗？</p>
	        <p class="check-p"><span class="del-com wsdel-ok" onclick="ok()">确定</span><span class="wsdel-no" onclick="no()">取消</span></p>
	    </div>
	</aside>
	<!-- <div style="width:100%;height:17px;border-top: 1px solid #f4f4f4;background-color: #fff;"></div> -->
	<%-- <p class="zhiD">置顶 <a href="javascript:;" id='show-actions'></a></p>
	<div class="mask" style="display: none;"></div>
	<div class="weui-cells" style="display:none;position: fixed;bottom:0;left: 0;background-color: #fff;z-index: 99999;width: 100%;">
	    <div class="ma h" style="position:relative;width: 100%;height: 56px;line-height: 56px;text-align: center;font-size: 16px;font-weight: bold;border-bottom: 1px solid #ddd;">置顶收费标准<span class="cancel" style="position: absolute;top: 0;right: 5px;">取消</span></div>
		    <select name="isTop" title="是否置顶" class="zhiD">
				<option value="1" <c:if test="${pd.isTop == '0' }">selected</c:if> >不置顶</option>
				<option value="1" <c:if test="${pd.isTop == '1' }">selected</c:if> >置顶一天</option>
			</select>
	     <div class="weui-flex" style="padding: 15px 20px;">
	        <div class="weui-flex__item">一天</div>
	        <div class="weui-flex__item">￥2</div>
	        <div class="weui-flex__item">
	            <label  for="s11">
	                <div class="weui-cell__ft">
	                    <input type="radio" class="weui-check" name="checkbox1" id="s11" >
	                    <i class="weui-icon-checked"></i>
	                </div>
	            </label>
	        </div>
	    </div>
	    <div style="background-color: #42e264;color: #fff;width: 100%;height:56px;line-height:56px;text-align: center;">
	        <a href="api/h5KeHu/zaixianZhifu.do" style="color:#fff;">立即支付</a>
	    </div>
	</div> --%>
	<p class="biaoQ">标签
		<a id='show-actions1' id="qxx">请选择</a>
	</p>
	<div class="weui-cells1"  style="display:none;position: fixed;bottom:0;left: 0;background-color: #fff;z-index: 99999;width: 100%;height:250px;overflow-y:auto;">
		   <!-- 标签码表循环 -->
		   <c:forEach items="${mabiaoList}" var="pd">
			    <div class="xiala" style="padding: 15px 20px;border-bottom: 1px solid #eee;text-align: center">
			        <%-- <option value="${pd.hudong_category_id}" tid="${pd.categoryName}" <c:if test="${pd.hudong_category_id == '0' }"></c:if> >${pd.categoryName}</option> --%>
			    	<option value="${pd.hudong_category_id }" tName="${pd.categoryName}" onclick="getCategoryId(this)">${pd.categoryName }</option>
			    </div>
		    </c:forEach>
			    <div id="quxiao" style="background-color: #42e264;color: #fff;width: 100%;height:56px;line-height:56px;text-align: center;">
			        <p class="cancel1">取消</p>
			    </div>
	</div>
</form>
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script src="static/js/wmpc/imgUp-h5.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script>
	function getCategoryId(e1){
		$("#cid").val($(e1).val());
		$("#cName").val($(e1).attr("tName"));
	}

    $(function() {
        FastClick.attach(document.body);
    });
    $(function(){
        $(".zhiD>a").click(function(){
            $(this).addClass("bground");
            $(".mask").fadeIn();
            $(".weui-cells").slideDown();
            $(".cancel").click(function(){
                $(".zhiD>a").removeClass("bground");
                $(".mask").fadeOut();
                $(".weui-cells").slideUp();
            })
        })
    });
</script>
<script>

	var sid;
    $(function(){
        $(function(){
            $(".biaoQ>a").click(function(){
                var obj;
                obj=document.getElementById("show-actions1");
                $(".mask").fadeIn();
                $(".weui-cells1").slideDown();
                $(".xiala").click(function(){
                    $(this).css("background-color","#42e264");
                    $(this).siblings().css("background-color","#fff");
                    sid=$(this).find("#tid").val();
                    $("#quxiao").css("color","#000");
                    obj.innerHTML=$(this).children().text();
                    $(".mask").fadeOut();
                    $(".weui-cells1").slideUp();
                });
                $(".cancel1").click(function(){
                    $("#quxiao").css("background-color","#42e264");
                    $("#quxiao").siblings().css("background-color","#fff");
                    $("#quxiao").css("color","#000");
                    $(".mask").fadeOut();
                    $(".weui-cells1").slideUp();
                })
            })
        });
    });
    
  /*   function fabu(){
    var content=$("#content").val();
    if(sid){
    	alert(sid);
    	alert(content);
     var form = new FormData($("#uploadForm")[0]);
     alert(form)
     	form.append("sid",sid);
     	form.append("content",content);
    		$.ajax({
				url:"api/h5login/savedongtai.do",
				type:"post",
				dataType:"json",
				data:form,
				cache: false,    
         	 	contentType: false,    
          		processData: false, 
				success:function(data){
					if(data.respCode=="01"){
						alert(data.respMsg);
						alert(data.phone);
						alert(data.backCode);
						location.href="api/h5login/hdsq.do?backCode='"+data.backCode+"'&phone='"+data.phone+"'";
					}
				}
    	}); 
    	}
    } */

</script>
</body>
</html>
