<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE>
<html >
<head>
    <title>长途拼车</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">

    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
        .sureSongda{
            display:inline-block;position:absolute;top:8px;right:18%;padding: 5px 10px;font-size:16px;
            text-align:center;border:1px solid #6bafe5;color:#fff;border-radius: 4px;background-color: #0086fb;
        }
        .callShifu{
            display:inline-block;position:absolute;top:8px;left:38%;padding: 5px 10px;font-size:16px;text-align:center;
            border:1px solid #7e7e7e;color:#000;border-radius: 4px;
        }
        .lianxiS{
            width:100%;height:54px;background-color: #fff;position: relative;
        }
       .mask{
           width:100%;height:100%;background-color: rgba(0,0,0,0.3);display: none;
       }
        .callBox{
            width:230px;height:144px;position: fixed;left:50%;top:50%;margin-left:-115px;margin-top:-72px;z-index:999;background-color: #fff;border-radius: 5px;display: none;
        }
        .telNum{
            width:100%;height:88px;line-height:88px;text-align:center;border-bottom: 1px solid #eee;;
        }
        .callBox>a{
            display:block;width:49%;height:55px;text-align: center;line-height: 55px;float: left;color: #666;
        }
        .cancel{
            color: #000;border-right: 1px solid #eee;;
        }
		.changtuUl>li{
			width:100%;overflow:hidden;white-space:no-wrap;
		}
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">长途订单详情</h1>
  <a href="api/h5KeHu/dachedingdans.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<div class="dacheOrder" style="padding:10px 0;margin-top:60px;">
    <div style="width:100%;padding:10px 0;background-color: #fff;border-bottom:2px solid #f5f5f5;">
        <div class="orderBox shifuZiliao shifuZiliao2">
            <p style="font-size: 18px;color:#000;margin-top:5px;margin-left:15px;">
            	${pd.departureCity}-${pd.arrivalCity}
            	<span style="color:#0068fc;top: 15;">
            		<c:if test="${pd.order_changtu_status=='1' }">进行中...</c:if>
					<c:if test="${pd.order_changtu_status=='2' }">已完成</c:if>
					<c:if test="${pd.order_changtu_status=='3' }">已取消</c:if>
            	</span>
            </p>
            <ul style="padding:4px 0;margin-left:15px;font-size: 16px;" class="changtuUl">
                <li>发布时间: <span>${fn:substring(pd.departureTime,0,16)}</span></li>
                <li>出发地点 : <span>${pd.changtu_departurePlace}</span></li>
                <li>到达地点 : <span>${pd.changtu_destination}</span></li>
                <li>车主姓名 : <span>${pd.userNamesiji}</span></li>
                <li>车主电话 : <span>${pd.phonesiji}</span></li>
                <li>车 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型 : <span>${pd.carType }</span></li>
                <li>颜  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色 : <span>${pd.carColor }</span></li>
                <li>牌  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号 : <span>${pd.carNumber }</span></li>
                <li style="color:#ff7d00;">拼车费 : <span style="color:#ff7d00;">￥${pd.changtu_radeAmount}</span></li>
            </ul>
        </div>
    </div>
    <div class="lianxiS">
        <a href="javascript:void(0)" class="callShifu">联系师傅</a>
       <%--  <a href="api/h5KeHu/changtuSure.do?order_changtu_id=${pd.order_changtu_id }" class="sureSongda">确认送达</a> --%>
       <%--  <c:if test="${pd.order_changtu_status=='1' }">进行中...</c:if>
		<c:if test="${pd.order_changtu_status=='2' }">已完成</c:if> --%>
    </div>

</div>


<div class="mask"></div>
<div class="callBox">
    <div class="telNum">${pd.phonesiji}</div>
	    <a href="javascript:void(0)" class="cancel">取消</a>
	    <a href="tel:${pd.phonesiji}">呼叫</a>
	</div>

<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
    $(".callShifu").click(function(){
        $(".mask").fadeIn();
        $(".callBox").fadeIn();
        $(".cancel").click(function(){
            $(".mask").fadeOut();
            $(".callBox").fadeOut();
        })
    })
</script>

<script src="static/js/wmpc/jquery-weui.js"></script>
</body>
</html>
