<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE >
<html >
<head>
    <title>长途拼车 </title>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc//weui.css">
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
        .dacheOrder>span{
            padding:0 10px;border-radius: 10px;background-color: #d2d2d2;color:#fff;font-size: 14px;
        }
        .chezu{
            width:100%;
        }
        .chezu>a{
            display: inline-block;width:50%;height:50px;line-height:50px;text-align:center;border-bottom:2px solid #fff;
            color:#000;float: left;overflow: hidden;background-color: #fff;font-size: 16px;;
        }
       .act{
            color:#0284fc;border-bottom: 1px solid #0284fc;
        }
      /*  .mask{
            width:100%;height:100%;background-color: rgba(0,0,0,0.3);
        }*/
        .shifuZiliao ul a.store{
		    margin-top:-30px;
		}
		.fabuNews{
		    font-size: 16px;;
		}
		.orderBox > p:nth-child(1) > span{
		    right:0;
		}
			.wy-header {
		    position: fixed;
		    width:100%;
		    height:60px;
			z-index: 500;;
		
		}
		.weui-navbar{
		    position: fixed;
		    z-index: 500;
		    top: 60px;
		    width: 100%;
		}
		.weui-navbar + .weui-tab__bd {
		    padding-top: 106px;
		}
		.fabuNews{
			right:20px;
		}
		/* .wodefabuUl>li{
			width:100%;overflow:hidden;
		} */
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->

<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">长途拼车<a href="api/h5KeHu/fabuNews.do" class="fabuNews">发布路线</a></h1>
  <a href="api/h5KeHu/dcpc.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<!--主体-->
<div class="weui-tab" >
    <div class="weui-navbar" style="background-color: #fff;">
    	<!-- api/h5KeHu/changtuCar.do -->
        <a class="weui-navbar__item weui-bar__item--on"  href="api/h5KeHu/changtuCar.do" style="padding:10px 0">
            	长途路线
        </a>
       	<a class="weui-navbar__item" href="api/h5KeHu/wdfbchangtuCarList.do" style="padding:10px 0">
         	  	我的发布
       	</a>
<!-- api/h5KeHu/wdfbchangtuCarList.do -->
    </div>
    <div class="weui-tab__bd">
        <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
            <div class="dacheOrder" >
                    <c:forEach items="${changtuCarList}" var="changtuCarList">
	                	<span>${changtuCarList.releaseTime }</span>
	                    <div class="orderBox shifuZiliao shifuZiliao2">
	                        <p style="font-size: 18px;color:#333;margin-top:5px;">${changtuCarList.departureCity}-${changtuCarList.arrivalCity}</p>
	                        <ul class="wodefabuUl">
	                            <li>车主姓名 : <span>${changtuCarList.userName}</span></li>
	                            <li>联系电话 : <span>${changtuCarList.phone}</span> <a href="javascript:void(0);" onclick="checkXiadanNum('${changtuCarList.information_changtu_id}','${changtuCarList.userNum}')" class="store" >我要下单</a></li>
	                            <li>出发时间 : <span>${fn:substring(changtuCarList.departureTime,0,16)}</span></li>
	                            <li>可乘人数 : <span>${changtuCarList.userNum}</span></li>
	                            <li>出发地 : <span>${changtuCarList.departurePlace}</span></li>
	                            <li>目的地 : <span>${changtuCarList.destination}</span></li>
	                            <li style="color:#ff7d00;">拼车费 : <span style="color:#ff7d00;">￥${changtuCarList.carpoolFee}</span></li>
	                        </ul>
	                    </div>
                    </c:forEach>
		    </div>
		</div>
<!--底部导航-->
<!--底部导航-->
<!-- <div class="weui-tabbar wy-foot-menu">
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/index.do'" class="weui-tabbar__item ">
    <div class="weui-tabbar__icon foot-menu-home"></div>
    <p class="weui-tabbar__label">首页</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/hdsq.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon foot-menu-cart"></div>
    <p class="weui-tabbar__label">互动社区</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/dcpc.do'" class="weui-tabbar__item weui-bar__item--on">
    <div class="weui-tabbar__icon  foot-menu-member"></div>
    <p class="weui-tabbar__label">打车拼车</p>
  </a>
  <a href="javascript:void(0)" onclick="window.location.href='api/h5KeHu/wd.do'" class="weui-tabbar__item">
    <div class="weui-tabbar__icon foot-menu-car"></div>
    <p class="weui-tabbar__label">我的</p>
  </a>
</div> -->
<!--<div class="mask"></div>
<div class="telHujiao">

</div>-->
<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script src="static/js/wmpc/jquery-weui.js"></script>
<script src="static/js/wmpc/layer/layer.js"></script>
<script>
	function checkXiadanNum(changtu_info_id,userNum){
			var url = 'api/h5KeHu/checkUserNum.do';
			$.ajax({
				url:url,
				type:"post",
				data:{
	    			"information_changtu_id":changtu_info_id
	    		},
	    		dataType:"json",
				success:function(data){
					console.log(data.respCode)
					if(data.respCode=="01"){
						if(data.pincheUserNum==userNum){
							layer.msg("拼车人数已满坐！",{
	    					//skin: 'layui-layer-molv',
	    		            time:3000,//单位毫秒
	    		            shade: [0.8, '#393D49'], // 透明度  颜色
	    		            title:'温馨提示',
	    		            //style: 'background: rgba(216,100,125,0.9); color:#fff; border:none;',
	    		            icon:7,//1:绿色对号,2：红色差号,3：黄色问好,4：灰色锁,5：红色不开心,6：绿色开心,7：黄色感叹号
    		        });
						}else{
							location.href="api/h5KeHu/chengke.do?userNum="+userNum+"&information_changtu_id="+changtu_info_id;
						}
					}else{
						location.href="api/h5KeHu/chengke.do?userNum="+userNum+"&information_changtu_id="+changtu_info_id;
					}
				}
			});
	}


    $(function() {
        FastClick.attach(document.body);
        var respCode ="${respCode}";
        if(respCode =="03"){//长途订单
	        layer.alert("您已有长途打车订单，请您与司机联系！",{
	            title:"温馨提示"//提示标题，默认为：信息
	          //  ,skin: 'layui-layer-molv'//默认为：无色，layui-layer-molv：墨绿，layui-layer-lan：深蓝
	            ,anim: 0 //动画类型0-6，默认为：0
	            ,closeBtn: 0//是否显示关闭按钮，0-不显示，1-显示，默认为1
	            ,btn: ['确定','取消'] //按钮
	            ,icon: 6    // icon
	            ,yes:function(){
	            	location.href="api/h5KeHu/dachedingdans.do";
	            }
	            ,btn2:function(){
	            	location.href="api/h5KeHu/dcpc.do";
	            }
	        })
        }
    });
</script>
</body>
</html>
