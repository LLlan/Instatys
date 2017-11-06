<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE>
<html>
<head>
    <title>评论列表</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
        .pic_rt>p:nth-child(2){
            font-size: 12px;
            color:#a0a0a0;;
        }
		 .pingBox .chakan{
		    width: 100%;
		 
		}
		 .pingBox:last .chakan{
			margin-bottom:55px!important;
		}
		.pingBox{
		    width: 100%;
		    margin-top:0;
		}
		.pic_rt p:nth-child(4) {
		    position: absolute;
		    width: 120px;
		    height:30px;
		    top: 0;
		    right:0;
		    font-size: 14px;
		    overflow:hidden;
		}
.pic_rt p:nth-child(4)>a {
    display: inline-block;
    width: 25px;
    height: 30px;
    color: #888;
    padding-left:0;
    padding-bottom:0;
    padding-left:20px;
    float:left;
    text-align:center;
}
.pic_rt p:nth-child(4)>a:nth-child(1) {
    display: inline-block;
    background: url(static/images/wmpc/pppp_03.png) 10px 22px no-repeat;
}
.chakan{
            margin-top:-1px;;
        }
        .pic_rt>p:nth-child(1){
            font-size:16px;;
        }
        .pic_rt>p:nth-child(3){
            padding-right:0;
            width:94%;
            line-height: 20px;
            margin-top: 10px;
            padding-bottom:15px;
            border-bottom:1px solid #f4f4f4;
        }
        .nichengB a:nth-child(1) span {
            width: 100%;
            left: 0;
        }
        .pic_rt p:nth-child(4)>a:nth-child(1) {
		    background: url(static/images/wmpc/pinglun.png) 0 3px no-repeat;background-size:15px 15px; position: absolute;left:0;top: 0px;
		}
        .pic_rt p:nth-child(4)>a:nth-child(2) {
		    background: url(static/images/wmpc/dianzan1.png) 0 3px  no-repeat;background-size:15px 15px;position: absolute;right:0;top: 0px;
		}
		.nichengB a:nth-child(2){
			right: 14px;
			
		}
		.bottomP>a:nth-child(1) {
		    margin-left:0;
		    background: url(static/images/wmpc/pinglun.png) 60px 16px no-repeat;
		    background-size: 17px 15px;
		    border-right: 1px solid #d4d4d4;
		}
		.bottomP>a {
		    display: block;
		    float: left;
		    width:49.5%;
		    height:45px;
		    padding:0;
		    color: #454545;
		    text-align:center;
		}
		.bottomP>a:nth-child(2){
			margin-left:0;
			background: url(static/images/wmpc/dianzan1.png) 60px 15px no-repeat;
			background-size: 17px 15px;
		}
		.nicheng+.pingBox{
			margin-top: 35px;
		}
		.bigp>.pingBox:nth-child(2){
			margin-top:35px;!important;
		}
		.title{
			font-size:17px;
		}
		.icon1{
			margin-top:15px;
		}
		.bottomP>a:nth-child(1)>span{
			display:block;width:60px;height:20px;line-height:20px;margin-top:12px;margin-left:70px;
		}
		.bottomP>a:nth-child(2)>span{
			display:block;width:40px;height:20px;line-height:20px;margin-top:12px;margin-left:70px;
		}
		.pic_rt p:nth-child(4)>a{
			display:block;width:35px;height:20px;
		}
    </style>
</head>
<body ontouchstart>
<!--主体-api/h5KeHu/hdsq.do->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
    <h1 class="title">${sdData.userName }</h1>
    <a href="api/h5KeHu/hdsq.do" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<!--主体-->
<div style="margin-bottom:50px;width:100%;overflow:hidden;margin-top:60px;" class="bigp">
	<div class="nichengB">
    <div class="chenghu">
        <a href="javascript:void(0);">评论 ${count.counts }<span></span></a>
        <a href="javascript:void(0);">赞 ${sdDatas.zannum }</a>
    </div>
</div>
<c:forEach items="${pd}" var="pd">
	<div class="pingBox">
	    <div class="chakan">
	        <div class="pic_lf">
	           <c:if test="${ pd.headImg != '' && pd.headImg != null}">
	            <img src="<%=basePath%>${pd.headImg }" alt=""/>
	           </c:if>
	           <c:if test="${ pd.headImg == '' || pd.headImg == null}">
	          		<img src="static/images/wmpc/u132.png" alt=""/>
	           </c:if>
	        </div>
	        <div class="pic_rt">
	            <p>${pd.userName }</p>
	            <p>${pd.pinglunTime }</p>
	            <p>${pd.pinglunContent }</p>
	            <p>
		            <a href="api/h5KeHu/pinglunBieren.do?hudong_pinglun_fid=${pd.hudong_pinglun_id }">
			            <span>
			            	<c:if test="${ pd.pltwocounts != '' && pd.pltwocounts != null}">
                           		${pd.pltwocounts }
                            </c:if>
                            <c:if test="${ pd.pltwocounts == '' || pd.pltwocounts == null}">
                           		评论
                            </c:if>
			            </span>
			        </a>
		            <a class="ok" href="javascript:void(0);" onclick="dianzan('${pd.hudong_pinglun_id}');" id="zan_id${pd.hudong_pinglun_id}"><span>${pd.zanNumber }</span></a>
	            </p>
	        </div>
	    </div>
	</div>
</c:forEach>

</div>
    
<div class="bottomP">
    <a href="api/h5KeHu/fabuPinglun.do?hudong_dongtai_fid=${id }"><span>评论</span></a>
    <a href="javascript:void(0);" " onclick="dianzans('${id }');" id="zan_ids${id }"  class="ok"><span>赞</span></a>
</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script src="static/js/wmpc/layer/mobile/layer.js"></script>
<script type="text/javascript">
	
	function dianzans(id){
		var url = 'api/h5KeHu/getOneDianzanNumber.do';
	 	$.ajax({
			url:url,
			type:"post",
			data:{
    			"hudong_dongtai_fid":id
    		},
    		dataType:"json",
			success:function(data){
				console.log(data.respCode);
				if(data.respCode=="01"){
					$("#zan_ids"+id).text(data.zanNumber);
				}else if(data.respCode=="03"){
					   //提示
					  layer.open({
					    content: '您已赞过了！'
					    ,skin: 'msg'
					    ,time: 1.5 //2秒后自动关闭
					  });
						/* layer.msg("您已赞过了！",{
    					//skin: 'layui-layer-molv',
    		            time:2000,//单位毫秒
    		            shade: [0.8, '#393D49'], // 透明度  颜色
    		            title:'温馨提示',
    		            //style: 'background: rgba(216,100,125,0.9); color:#fff; border:none;',
    		            icon:7,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        }); */
				}
				else{
					layer.alert("您还没有登录，马上去？",{
			            title:"温馨提示"//提示标题，默认为：信息
			            ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
			            ,anim: 0 //动画类型0-6，默认为：0
			            ,closeBtn: 1//是否显示关闭按钮，0-不显示，1-显示，默认为1
			            ,btn: ['确定','取消'] //按钮
			            ,icon: 6    // icon
			            ,yes:function(){
			                location.href="api/h5kehu/Login.do";
			            }
			          
			        })

				}
				
			}
		})
    		
	}

	
	function dianzan(id){
		var url = 'api/h5KeHu/getTwoDianzanNumber.do';
	 	$.ajax({
			url:url,
			type:"post",
			data:{
    			"hudong_pinglun_fid":id
    		},
    		dataType:"json",
			success:function(data){
				console.log(data.respCode);
				if(data.respCode=="01"){
					$("#zan_id"+id).text(data.zanNumbers);
				}else if(data.respCode=="03"){
						//提示
					  layer.open({
					    content: '您已赞过了！'
					    ,skin: 'msg'
					    ,time: 1.5 //2秒后自动关闭
					  });
						/* layer.msg("您已赞过了！",{
    					//skin: 'layui-layer-molv',
    		            time:2000,//单位毫秒
    		            shade: [0.8, '#393D49'], // 透明度  颜色
    		            title:'温馨提示',
    		            //style: 'background: rgba(216,100,125,0.9); color:#fff; border:none;',
    		            icon:7,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        }); */
				}
				else{
					layer.alert("您还没有登录，马上去？",{
			            title:"温馨提示"//提示标题，默认为：信息
			            ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
			            ,anim: 0 //动画类型0-6，默认为：0
			            ,closeBtn: 1//是否显示关闭按钮，0-不显示，1-显示，默认为1
			            ,btn: ['确定','取消'] //按钮
			            ,icon: 6    // icon
			            ,yes:function(){
			                location.href="api/h5kehu/Login.do";
			            }
			          
			        })

				}
				
			}
		})
    		
	}

</script>

<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<!--点赞-->
<script>
    $(function(){
        $(".ok").click(function(){
            if($(this).hasClass("light")){
                $(this).removeClass("light")
            }else{
                $(this).addClass("light")
            }
        })
    })
</script>
</body>
</html>
