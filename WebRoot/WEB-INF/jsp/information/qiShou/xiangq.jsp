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
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <style>
        body{
            background-color:#fff;;
        }
        .Boxback{
            width:94%;margin:0 auto;
        }
        .box111>h2{
            font-size:20px;margin:0;padding:20px 0;white-space:nowrap;text-overflow:ellipsis;overflow: hidden;
        }
        .box111>h3{
            font-size:16px;margin:0;padding:8px 0;white-space:nowrap;text-overflow:ellipsis;overflow: hidden;
        }
        .box111>p{
            font-size:16px;margin:0;line-height:25px;word-spacing:5px;;
        }
        ul{
            list-style: disc;margin:10px 0;
            font-size:16px;line-height:25px;word-spacing:5px;
        }
    </style>
</head>
<body ontouchstart>
<!--主体-->
<!--主体-->
<div class="Boxback">
    <div class="box111">
        <h2>联系不上用户异常订单操作指导</h2>
        <h3>一、操作说明</h3>
        <p>1) 骑手首次拨打顾客电话联系不上客户时5分钟内联系联系不上用户使用异常报备功能</p>
        <p style="margin-top:15px;">2) 发送短信</p>
        <p style="margin-top:15px;">3) 点击"<a href="javascript:void(0)" style="color:#dc1c22;">拨打系统电话验证</a>"待系统校验上报异常</p>
        <p style="text-indent:1em;">(<a href="javascript:void(0)" style="color:#dc1c22;">成功上报异常的订单将不影响骑手后续接单，如果上报异常成功后此单本身为超时不影响考核</a>)，验证成功后骑手可离开，<a href="javascript:void(0)" style="color:#dc1c22;">上报成功后C端会发通知</a></p>
        <h3>二、上报异常后续的动作</h3>
        <p style="margin:10px 0;">1、 顾客后续需要餐品</p>
        <p style="text-indent:1em;">上报异常成功后骑手需先保留餐品如顾客后续联系需要餐品，骑手需与顾客协商安排时间另行配送。</p>
        <p style="margin:10px 0;">2、 顾客不需要餐品</p>
        <ul style="padding-left:40px;">
            <li>是顾客原因</li>
        </ul>
        <p style="text-indent:1em;">上报异常成功后骑手需先保留餐品如顾客后续联系需要餐品，骑手需与顾客协商安排时间另行配送。</p>
        <ul style="padding-left:40px;">
            <li>骑手原因</li>
        </ul>
        <p style="text-indent:1em;">上报异常时此单超时，属于骑手原因。</p>
    </div>

</div>



<script src="../lib/jquery-2.1.4.js"></script>
<script src="../lib/fastclick.js"></script>
<script src="../js/wmpc/jquery-weui.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
</body>
</html>
