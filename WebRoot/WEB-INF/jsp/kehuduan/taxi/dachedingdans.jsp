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
    <title>打车订单</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link rel="stylesheet" href="static/css/wmpc/style-mine.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <style>
        .dacheOX{
            display:block;padding:10px 0;border:1px solid #d0d0d0;border-radius:10px;background-color: #fff;
        }
        .dacheOX1{
            margin-top:10px;
        }
        .dacheOX p{
            font-size: 18px;color:#000;margin-top:5px;margin-left:15px;
        }
        .dacheOX ul{
            padding:4px 0;margin-left:15px;font-size: 16px;
        }
        .orderBox > p:nth-child(1) > span {
		    position: absolute;
		    top: 40px;
		    right: 16px;
		    font-size: 14px;
		    color: #565656;
		    font-weight: 400;
		}
		.wy-header {
		    height: 44px;
		    width: 100%;
		    position: fixed;
		    top: 0;
		    left: 0;
		    z-index: 600;
		    background-color: #0284fe;
		    padding: 10px 0;
		}
		.weui-tab {
		    position: relative;
		    height: 100%;
		    width: 100%;
		    margin-top: 60px;
		}
		.changtuUl li{
			width:100%;overflow:hidden;white-space:no-wrap;
		}
		.dacheOrder>a{
			padding:0;
		}
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--头部开始-->
<div id="header" style="background-color: #068dff;">
  <h1 class="title" style="font-size:17px;">打车订单</h1>
  <a href="api/h5KeHu/wd.do" class="icon1" style="margin-top:14px;"><i class="icon-angle-left "></i></a>
</div>
<!--头部结束-->
<div class="weui-tab" style="background-color:#f4f4f4;">
    <div class="weui-navbar" style="background-color: #fff;position:fixed;top:60px;">
        <a class="weui-navbar__item" href="api/h5KeHu/dachedingdan.do" style="padding:10px 0">
            同城订单
        </a>
        <a class="weui-navbar__item weui-bar__item--on" href="api/h5KeHu/dachedingdans.do" style="padding:10px 0">
            长途订单
        </a>

    </div>
    <div class="weui-tab__bd">
        <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
            <div class="dacheOrder">
            <c:forEach items="${pd}" var="pd">
                <%-- <span>5分钟前</span>
                <a href=""><div class="orderBox fan">
                    <p>${pd.departurePlace}-${pd.destination} <span style="color:#006afb;">进行中…</span></p>
                    <p style="color:#feb200;">车费：约${pd.radeAmount}元</p>
                </div></a> --%>
                <a href="api/h5KeHu/changtuXiangdans.do?order_changtu_id=${pd.order_changtu_id}" class="dacheOX"><div class="orderBox shifuZiliao shifuZiliao2">
                    <p>${pd.departureCity }-${pd.arrivalCity } 
                    	<span style="color:#0068fc;">
                    		<span style="color:#006afb;">
	                    		<c:if test="${pd.order_changtu_status=='1' }">进行中</c:if>
								<c:if test="${pd.order_changtu_status=='2' }">已完成</c:if>
								<c:if test="${pd.order_changtu_status=='3' }">已取消</c:if>
                    		</span>
                    	</span>
                    </p>
                    <ul class="wodefabuUl changtuUl">
                        <li>出发时间: <span>${fn:substring(pd.departureTime,0,16)}</span></li>
                        <li>出发地点 : <span>${pd.changtu_departurePlace }</span></li>
                        <li>到达地点 : <span>${pd.changtu_destination }</span></li>
                        <li style="color:#ff7d00;">拼车费 : <span style="color:#ff7d00;">￥${pd.changtu_radeAmount }</span></li>
                    </ul>
                </div></a>
               <!--
                <span>3天前</span>
                 <a href="tongchengcome.html"><div class="orderBox fan">
                    <p>景瑞大厦-万国大都会 <span>已完成</span></p>
                    <p>车费：12元</p>
                </div></a>
                <span>10天前</span>
                <a href="tongchengcome.html"><div class="orderBox fan">
                    <p>景瑞大厦-万国大都会 <span>已完成</span></p>
                    <p>车费：12元</p>
                </div></a> -->
               </c:forEach>
            </div>
        </div>

        <div id="tab2" class="weui-tab__bd-item " style="margin-top:10px;">
            <div class="dacheOrder">
                <a href="changtuXiangdan.html" class="dacheOX"><div class="orderBox shifuZiliao shifuZiliao2">
                    <p>海口-三亚 <span style="color:#0068fc;">进行中...</span></p>
                    <ul class="wodefabuUl">
                        <li>出发时间: <span>17-04-21  10:30</span></li>
                        <li>出发地点 : <span>海口市**************</span></li>
                        <li>到达地点 : <span>三亚市**************</span></li>
                        <li style="color:#ff7d00;">拼车费 : <span style="color:#ff7d00;">￥230</span></li>
                    </ul>
                </div></a>
                <a href="changtuXiangdan.html" class="dacheOX dacheOX1"><div class="orderBox shifuZiliao shifuZiliao2">
                    <p>海口-三亚 <span>已完成</span></p>
                    <ul class="wodefabuUl">
                        <li>出发时间: <span>17-04-21  10:30</span></li>
                        <li>出发地点 : <span>海口市**************</span></li>
                        <li>到达地点 : <span>三亚市**************</span></li>
                        <li>拼车费 : <span>￥230</span></li>
                    </ul>
                </div></a>

            </div>
        </div>
    </div>
</div>



<script src="static/js/wmpc/jquery-2.1.4.js"></script>
<script src="static/js/wmpc/fastclick.js"></script>
<script type="text/javascript" src="static/js/wmpc/jquery.Spinner.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>

<script src="static/js/wmpc/jquery-weui.js"></script>
</body>
</html>
