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
    <title>评论别人的评论</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
     <style>
      .pic_rt p:nth-child(4) {
		    position: absolute;
		    width: 120px;
		    top: 0;
		    right:0;
		    font-size: 14px;
		}
		.pic_rt p:nth-child(4)>a {
		    display: inline-block;
		    width: 30px;
		    height: 30px;
		    color: #888;
		    padding-left:0;
		    padding-bottom:0;
		    padding-left:20px;
		    float:left;
		    text-align:center;
		}
        .pic_rt p:nth-child(4)>a:nth-child(2) {
		    background: url(static/images/wmpc/dianzan1.png) 0 3px  no-repeat;background-size:15px 15px;position: absolute;right:0;top:0;
		}
		.title{
			font-size:17px;
		}
		.icon1{
			margin-top:15px;
		}
		.pingBox{
			margin-top:60px;
		}
		.pic_rt p:nth-child(4)>a{
			display:block;width:35px;height:20px;
		}
		.pic_rt p:nth-child(4)>a:nth-child(1)>span{
			display:block;height:20px;line-height:20px;
		}
		.pic_rt p:nth-child(4)>a:nth-child(2)>span{
			display:block;width:20px;height:20px;line-height:20px;
		}
		.fengexian{
			width:83%;
		}
		.fengexian>p:nth-child(4)>a.ok{
			width:25px;
			float:right;background: url(static/images/wmpc/dianzan1.png) 0 3px no-repeat;
    		background-size: 15px 15px;
		}
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<%-- <div class="wy-header header">
    <div class="wy-header-icon-back"><a href="api/h5KeHu/nichengMou.do?hudong_dongtai_fid=${pd.hudong_dongtai_fid }"></a></div>
    <div class="wy-header-title">${count.counts }条回复</div>
</div> --%>
<div id="header" style="background-color: #068dff;">
    <h1 class="title">${count.counts }条回复</h1>
    <a href="api/h5KeHu/nichengMou.do?hudong_dongtai_fid=${pd.hudong_dongtai_fid }" class="icon1"><i class="icon-angle-left"></i></a>
</div>
<!--头部结束-->
<!--主体-->
<div class="pingBox" >
        <div class="chakan" >
            <div class="pic_lf">
                <!-- <img src="static/images/wmpc/u132.png" alt=""/> -->
                <c:if test="${ pd.headImg != '' && pd.headImg != null}">
	           		 <img src="<%=basePath%>${pd.headImg }" alt=""/>
	           	</c:if>
	           	<c:if test="${ pd.headImg == '' || pd.headImg == null}">
	          		<img src="static/images/wmpc/u132.png" alt=""/>
	           	</c:if>
            </div>
            <div class="pic_rt">
                <a style="color:#000 "><p style="font-size: 16px;">${pd.userName }</p></a>
                <a style="color:#000 "><p>${pd.pinglunTime }</p></a>
                <a style="color:#000 "><p style="padding-right: 10px;">${pd.pinglunContent }</p></a>
                <p>
	                <a href="api/h5KeHu/fabuPinglunTwo.do?hudong_pinglun_fid=${pd.hudong_pinglun_id }" style="background: url(static/images/wmpc/pinglun.png) 0 3px  no-repeat;background-size:15px 15px;position: absolute;top: 0px;">
	                	<span>
			            	<c:if test="${ count.counts != '' && count.counts != null}">
                           		${count.counts}
                            </c:if>
                            <c:if test="${ count.counts == '' || count.counts == null}">
                           		评论
                            </c:if>
			            </span>
	                </a>
	                <a class="ok" href="javascript:void(0);"onclick="dianzans('${pd.hudong_pinglun_id}');" id="zan_ids${pd.hudong_pinglun_id}"><span>${sdDatas.zannum }</span></a>
                </p>
            </div>
        </div>
    <div class="pinglun2">
        <div class="pinglun" >
            全部评论
        </div>
        <c:forEach items="${pds}" var="pds">
	        <div class="chakan chakan2 chakan3">
	            <div class="pic_lf">
	                <!-- <img src="static/images/wmpc/u132.png" alt=""/> -->
	                <c:if test="${ pds.headImg != '' && pds.headImg != null}">
		           		 <img src="<%=basePath%>${pds.headImg }" alt=""/>
		           	</c:if>
		           	<c:if test="${ pds.headImg == '' || pds.headImg == null}">
		          		<img src="static/images/wmpc/u132.png" alt=""/>
		           	</c:if>
	            </div>
	            <div class="pic_rt fengexian pic_rt1">
	                <p style="font-size: 16px;">${pds.userName }</p>
	                <p>${pds.pinglunTime }</p>
	                <p>${pds.pinglunContent }</p>
	                <p>
		                <a class="ok" href="javascript:void(0);" onclick="dianzan('${pds.hudong_pinglun_two_id }');" id="zan_id${pds.hudong_pinglun_two_id }"><span>${pds.zanNumber }</span></a>
	                </p>
	            </div>
	        </div>
       </c:forEach>
    </div>
</div>
<!--<div class="bottomP">
    <a href="fabuPinglun.html">评论</a>
    <a href="">1</a>
</div>-->



<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script src="static/js/wmpc/layer/mobile/layer.js"></script>
<script type="text/javascript">
	
	
	function dianzans(id){
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
					$("#zan_ids"+id).text(data.zanNumbers);
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
		var url = 'api/h5KeHu/getThreeDianzanNumber.do';
	 	$.ajax({
			url:url,
			type:"post",
			data:{
    			"hudong_pinglun_two_fid":id
    		},
    		dataType:"json",
			success:function(data){
				console.log(data.respCode);
				if(data.respCode=="01"){
					$("#zan_id"+id).text(data.zanNumber);
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
