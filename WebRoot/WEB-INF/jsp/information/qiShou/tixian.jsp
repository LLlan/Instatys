<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <title>提现说明</title>
    <style>
        body{
            background-color: #EEEEEE;
        }
        .container{
            padding: 15px;
            width: 100%;
            overflow: hidden;
        }
        .tixian{
            width: 100%;
            font-size: 15px;
            color: #3C3C3C;
            overflow: hidden;
            margin-bottom:20px;
        }
        .tixian h1,.jinfen h1{
            width: 100%;
            height: 30px;
            line-height: 30px;
            margin-bottom: 15px;
        }
        .tixian p,.jinfen p{
           margin-bottom: 15px;
            line-height: 20px;
        }
        .jinfen{
            width: 100%;
            font-size: 15px;
            color: #3C3C3C;
            overflow: hidden;
        }
        *{
            border: none;
            outline: none;
            padding: 0;
            margin: 0;
            -webkit-box-sizing: border-box !important;
            -moz-box-sizing: border-box !important;
            box-sizing: border-box !important;
        }
        h1, h2, h3, h4, h5, h6 { font-weight:normal; font-size:100%; }
        body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,textarea,p,blockquote,th,td { margin:0; padding:0; }

    </style>
</head>
<body>
    <div class="container">
        <div class="tixian">
            <h1>一、提现规则</h1>
            <p>1.提现账号姓名需要与账号实名认证过的身份一致。</p>
            <p>2.每周一、周四为固定提现日，每个提现日只能发起一次提现，每次提现金额的上限为3500元。</p>
            <p>3.提现申请提交后预计2-3个工作日内到账。</p>
            <p>4.如有异常，请在[侧边栏]-[联系客服]中，与在线客服进行沟通。</p>
        </div>
        <div class="jinfen">
            <h1>二、积分兑换规则</h1>
            <p>1.积分可兑换1元人民币</p>
            <p>2.积分兑换不设限制</p>
            <p>3.兑换立即生效，相应余额增加</p>
        </div>
    </div>
</body>
</html>