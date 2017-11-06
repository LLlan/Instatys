<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE >
<html >
<head>
    <title>互动社区</title>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no minimum-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine1.css">
    <link rel="stylesheet" href="static/css/wmpc/swiper-3.4.2.min.css">
   
    <style type="text/css">
     body {
            background: #eee;
            font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
            font-size: 14px;
            color:#000;
            margin: 0;
            padding: 0;
        }
        .hudongB{
        	margin-bottom:48px;
        }
        .pr{position:relative;}
        .abs{position: absolute}
        .fl{float:left;display:inline-block;}
        .fr{float:right;display:inline-block;}
        .clearfix:after{content:".";display:block;height:0;clear: both;visibility:hidden;}
        .clearfix{*zoom:1;}
        .wrap{min-width: 320px;max-width:768px;margin:0 auto;overflow:hidden;position:relative;}
        .htit{line-height:1rem;padding:0 .3rem;color:#ff8e2e;font-size: .4rem;margin:1rem 0 .4rem 0}
        .wrapper03 {position:relative;height: 1rem;width: 100%;overflow: hidden;margin:0 auto;border-bottom:1px solid #ccc}
        .wrapper03 .scroller {position:absolute}
        .wrapper03 .scroller li {height: 1rem;color:#333;float: left;line-height: 1rem;font-size: .4rem;text-align: center}
        .wrapper03 .scroller li a{color:#333;display:block;margin:0 .3rem;padding:0 .1rem}
       /*  .wrapper03 .scroller li.cur a{color:#1cbb9b;border-bottom:.1rem solid #1cbb9b} */
        
        .wrapper03 .scroller .chooseState a{
        	color: #fff;
        	background-color: #f69c07;
        	/* color:#1cbb9b;border-bottom:.1rem solid #1cbb9b */
        }
        .wrapper03 .scroller li a {
		    color: #333;
		    display: block;
		    margin: 0 .1rem;
		    padding: 0 .4rem;
		}
		/* .wrapper03 .scroller li.cur a {
		    color: #fff;
		    background-color: #f69c07;
		} */
		.boxP>a{
            display: block;height:44px;line-height: 44px;float:left;font-size: 14px;color: #929292;
        }
        .boxP>a:nth-child(1) {
            width:40%;
            background: url(static/images/wmpc/pinglun.png) 35% center no-repeat;
            border-right: 1px solid #d4d4d4;
            background-size:18px 16px;
            padding:0;
            padding-left:9%;
            color: #929292;
            text-align: center;

        }
        .boxP>a:nth-child(2) {
            width:40%;
            background: url(static/images/wmpc/dianzan1.png) 60% center no-repeat;
            background-size:18px 16px;
            padding:0;
            margin:0;
            color: #929292;
            text-align: right;
        }
        .boxP>a:nth-child(1)>span{
        	display:block;width:40px;height:20px;line-height:20px;margin-left:35%;margin-top:12px;text-align:center;
        }
        .boxP>a:nth-child(2)>span{
        	display:block;width:40px;height:20px;line-height:20px;margin-left:65%;margin-top:12px;text-align:center;
        }
        .boxP>a.light {
            background: url(static/images/wmpc/dianzan.png) 70px center no-repeat;
            background-size: 18px 16px;
            color: #af0b0b;
        }
		.hudongBS>.faB {
		    font-size: 14px;
		    line-height: 30px;
		}
		.hudongBS>.faB {
		    width: 90%;
		    margin: 0 auto;
		    word-wrap: break-word;
		    line-height: 20px;
		    font-size: 16px;
		    margin-top: 10px;
		}
		.jiugongG {
            width: 96%;
            margin: 0 auto;
        }
        .dongtai1 {
            width: 90%;
            margin: 0 auto;
        }
        .dongtai1>img {
            left: 0;
        }
        .dongtai1>p:nth-child(2) {
            left: 60px;
        }
        .dongtai1>p:nth-child(3) {
            left: 60px;
        }
        .dongtai1>p:nth-child(4) {
       		width:60px;
       		text-align:center;
		    color: #507386;
		    font-size: 13px;
		    position: absolute;
		    top: 24px;
		    right: 11px;
		    border: 1px solid #507386;
		    padding: 0 3px;
		    top: 15px;
            right:0;
            
		}
		.weui-tab__bd .weui-tab__bd-item{
		    overflow: inherit;
		}
		.dongtai1>p:nth-child(3)>span {
		    color: #999;
		}
		.wy-header{
			padding:8px 0;position:fixed;top:0;left:0;width:100%;z-index:999;
		}
		.jiugongG{
			width:93%;
		}
		.jiugongG>img{
			width:32%;
			margin-left:0.8%;
		}
		.jiugongG>img:nth-child(1){
			margin-left:0;
		}
		.jiugongG>img:nth-child(4){
			margin-left:0;
		}
		.jiugongG>img:nth-child(7){
			margin-left:0;
		}
		
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div class="wy-header header">
    <div class="wy-header-title" style="position: relative">互动社区 <a href="api/h5KeHu/gofabudongtai.do" class="fabiaoDong" style="background-color: #fff;color:#4779aa;padding:3px 10px;font-size:14px;border-radius: 4px;position: absolute;right:-40px;bottom:7px;;line-height: 25px;">发表动态</a></div>
</div>
<!--头部结束-->

<form action="" method="post" name="Form" id="Form">
	<!--搜索框-->
	<div class="sousuoK" style="height:60px;align-items:center;margin-top:60px;background-color: #F4F4F4">
	    <!--<a href="#" class="zhaoGongzuo">找工作 找房子</a>-->
	    <div class="sear" style="width: 90%;height: 40px;margin:auto;padding-top:12px;">
	        <input type="text" name="content" value="${pd.content }" class="zgz" placeholder="找工作 找房子"/>
	        <button  onclick="search();" style="background-color: #F4F4F4"><img src="static/images/wmpc/sear.png" alt="" style="width: 30px;height: 30px;"/></button>
	    </div>
	</div>
</form>
<!--主体-->
<div class="hudongBox">
    <div class="wrapper wrapper03" id="wrapper03" style="background-color: #fff;">
        <div class="scroller">
            <ul class="clearfix">
               <!--  <li><a href="javascript:void(0)">今日头条</a></li>
                <li><a href="javascript:void(0)">征婚交友</a></li>
                <li><a href="javascript:void(0)">商业推广</a></li>
                <li><a href="javascript:void(0)">招聘求职</a></li>
                <li><a href="javascript:void(0)">税务师</a></li>
                <li><a href="javascript:void(0)">国际证书</a></li> -->
				<c:forEach items="${mabiaoList}" var="clist" varStatus="vs">
                	<li id="tid${vs.index+1}" class="lic" ttid="${clist.hudong_category_id}" onclick="getListByType('${clist.hudong_category_id}',this)" >
                		<a  href="javascript:void(0)" style="margin-left: 0;" value="${clist.hudong_category_id}" >${clist.categoryName }</a>
                			<input type="hidden"  value="${clist.hudong_category_id}" id="hudong_category_id" name="hudong_category_id"/>
                	</li>
           		</c:forEach>
            </ul>
        </div>
    </div>
    <div class="weui-tab huadongT" id="huadongT" style="padding-bottom: 50px;">
        <div class="weui-tab__bd fenlei">
            <div id="tab0" class="weui-tab__bd-item" style="display: block;">
                <div class="hudongB">
                <c:forEach items="${hdsqList}" var="hdsqLists" >
                    <div class="hudongBS" style="margin-top:10px">
                        <div class="dongtai1">
                            <c:if test="${ hdsqLists.headImg != '' && hdsqLists.headImg != null}">
	                            <img src="<%=basePath%>${hdsqLists.headImg }" alt=""/>
                            </c:if>
                            <c:if test="${ hdsqLists.headImg == '' || hdsqLists.headImg == null}">
                           		<img src="static/images/wmpc/u132.png" alt=""/>
                            </c:if>
                            <p>${hdsqLists.userName}</p>
                            <%-- <c:choose>
                            	<c:when test="${hdsqLists.isTop =='1'}">
                            		<span></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span></span>
                            	</c:otherwise>
                            </c:choose> --%>
                            <p>
	                            <!-- <span style="padding:5px;background-color:#f69c07;color:#fff;border-radius:4px;margin-right:10px;">
	                            	置顶
	                            </span> -->
	                            <span>${hdsqLists.fabuTime }</span>
                            </p>
                            <p>${hdsqLists.categoryName }</p>
                        </div>
                        <p class="faB">${hdsqLists.content}</p>
                        <div class="jiugongG">
                            <%-- <c:if test="${ hdsqLists.imgPath != '' && hdsqLists.imgPath != null}">
	                            <img src="${hdsqLists.imgPath}" alt=""/>
                            </c:if>
                            <c:if test="${ hdsqLists.imgPath == '' || hdsqLists.imgPath == null}">
                           		<!-- <img src="static/images/wmpc/u132.png" alt=""/> -->
                            </c:if> --%>
                            <c:if test="${not empty hdsqLists.imgList}">
				                  <c:forEach items="${hdsqLists.imgList}" var="img">
				                       <img style="" src="${img.imgPath}" alt=""/>
				                  </c:forEach>
				             </c:if>
                            <!-- <img src="static/images/wmpc/u132.png" alt=""/>
                            <img src="static/images/wmpc/u132.png" alt=""/>
                            <img src="static/images/wmpc/u132.png" alt=""/>
                            <img src="static/images/wmpc/u132.png" alt=""/>
                            <img src="static/images/wmpc/u132.png" alt=""/>
                            <img src="static/images/wmpc/u132.png" alt=""/>
                            <img src="static/images/wmpc/u132.png" alt=""/>
                            <img src="static/images/wmpc/u132.png" alt=""/> -->
                        </div>
                        <div class="boxP">
                            <a href="api/h5KeHu/nichengMou.do?hudong_dongtai_fid=${hdsqLists.hudong_dongtai_id }">
	                            <span>
	                            	<c:if test="${ hdsqLists.plcounts != '' && hdsqLists.plcounts != null}">
	                            		${hdsqLists.plcounts}
		                            </c:if>
		                            <c:if test="${ hdsqLists.plcounts == '' || hdsqLists.plcounts == null}">
		                           		评论
		                            </c:if>
	                            </span>
                            </a>
                            <a href="javascript:void(0);" onclick="dianzan('${hdsqLists.hudong_dongtai_id }');" id="zan_id${hdsqLists.hudong_dongtai_id }" class="ok" style="font-size: 15px;"><span>${hdsqLists.zanNumber }</span></a>
                        </div>
                    </div>
                 </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<!--底部导航-->
<div class="weui-tabbar wy-foot-menu">
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/index.do'" class="weui-tabbar__item ">
    <div class="weui-tabbar__icon foot-menu-home"></div>
    <p class="weui-tabbar__label">首页</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/hdsq.do'" class="weui-tabbar__item weui-bar__item--on">
    <div class="weui-tabbar__icon foot-menu-cart"></div>
    <p class="weui-tabbar__label">互动社区</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/dcpc.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon  foot-menu-member"></div>
    <p class="weui-tabbar__label">打车拼车</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/wd.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon foot-menu-car"></div>
    <p class="weui-tabbar__label">我的</p>
  </a>
</div>
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script type="text/javascript" src="static/js/wmpc/flexible.js"></script>
<script type="text/javascript" src="static/js/wmpc/iscroll.js"></script>
<script type="text/javascript" src="static/js/wmpc/navbarscroll.js"></script>
<script type="text/javascript" src="static/js/wmpc/jroll.js"></script>
<script type="text/javascript" src="static/js/wmpc/layer/mobile/layer.js"></script>
<script type="text/javascript">
	//检索
	function search(content){
		//top.jzts();
		$("#Form").submit();
	}
</script>
<script type="text/javascript">
	$(function(){
	var tid = "${tid}";
	if(tid==""){
		$("#tid1").addClass("chooseState");
		var ttid=$("#tid1").attr("ttid");
		var pid = "tid1";
		location.href="api/h5KeHu/hdsq.do?hudong_category_id="+ttid+"&tid="+pid;
	}else{
		$("#"+tid).addClass("chooseState");
	}
		
	});

	
	function dianzan(id){
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
					$("#zan_id"+id).children("span").text(data.zanNumber);
				}else if(data.respCode=="03"){
				 	 //提示
					  layer.open({
					    content: '您已赞过了！'
					    ,skin: 'msg'
					    ,time: 1.5 //2秒后自动关闭
					  });
					
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
			          
			        });

				}
				
			}
		});
    		
	}
	
	function getListByType(id,e1){
		
		location.href="api/h5KeHu/hdsq.do?hudong_category_id="+id+"&tid="+$(e1).attr("id");
	}
	
    $(function(){

        //demo示例一到四 通过lass调取，一句可以搞定，用于页面中可能有多个导航的情况
      $('.wrapper').navbarscroll(); 

        //demo示例五 通过id调取
       /*  $('#demo05').navbarscroll({
            defaultSelect:6,
            endClickScroll:function(obj){
                console.log(obj.text())
            }
        }); */
	//ar jroll = new JRoll("#wrapper01", {scrollBarX:false});
	var mjroll = new JRoll("#wrapper03",{
		scroll:true,
		scrollX: true,
		scrollY: false,
		preventDefault:false
	}); 

    });
</script>

<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<script>
    $(function(){
        $(".scroller ul li").click(function(){
        	$(this).addClass("chooseState").siblings().removeClass("chooseState");
           // $(".fenlei>div").eq($(this).index()).addClass("weui-tab__bd-item--active").show().siblings().removeClass("weui-tab__bd-item--active").hide();
        });
    });
</script>
<script>
    $(function(){
        $(".scroller ul li").click(function(){
            $(".fenlei>div").eq($(this).index()).find(".dongtai1>p:last-child").text($(this).find("a").text());
        });
    });
</script>
<!--<script type="text/javascript">
    var lists = $('.scroller ul li');
    var contents = $('.fenlei>div');

    function bindEvent(){
        lists.each(function(index_li, li){
            $(this).on('click', function(event){
                contents.each(function(index_content, content){
                    if(index_li === index_content){
                        $(this).addClass("weui-tab__bd-item&#45;&#45;active").show();
                    }else{
                        $(this).removeClass("weui-tab__bd-item&#45;&#45;active").hide();
                    }
                });
            });
        });
    }

    function init(){
        bindEvent();
    }

    init();
</script>-->
<!--点赞-->
<!-- <script>
    $(function(){
        $(".ok").click(function(){
            if($(this).hasClass("light")){
                $(this).removeClass("light")
            }else{
                $(this).addClass("light")
            }
        });
    });
</script> -->
<script>
    $(function(){
        $(window).bind('scroll',function() {
            var len = $(this).scrollTop();
            if (len>= 60) {
                $(".sousuoK").hide();
                $(".wrapper").css("position","fixed");
                $(".wrapper").css("z-index","995");
                $(".wrapper").css("top","60px");
                $(".hudongBox").css("margin-top","167px");
            } else {
                $(".sousuoK").show();
                $(".wrapper").css("position","relative");
                $(".wrapper").css("top","0");
                $(".hudongBox").css("margin-top","0");
            }
        });
    });
</script>
<script type="text/javascript">
	$(function(){
		var wid = $(".jiugongG>img").width();
		$(".jiugongG>img").height(wid);
	});
</script>
</body>
</html>
