<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui1.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
   <!-- <link rel="stylesheet" href="static/css/wmpc/demo.css">-->
    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <script src="static/js/wmpc/layer/mobile/layer.js"></script>
    <title>提交订单</title>
    <style>
        body{
            background-color: #F0F0F0;
        }
        .icon1 {
		    top: 13;
		}
		.title {
		    margin-left: -30px;
		}
		
		.plet {
		    font-size: 20px;
		    color: #fff;
		    height: 20px;
		    line-height: 46px;
		    margin-left: 60px;
		    margin-top: 0;
		}
		.total {
		    color: #fff;
		    font-size: 20px;
		}
		.weui_cells {
		    margin-top: 1.17647059em;
		    background-color: #fff;
		    line-height: 1.41176471;
		    font-size: 13px;
		    overflow: hidden;
		    position: relative;
		}
    </style>
</head>
<body ontouchstart>
<!--<div class="wy-header" style="position:fixed; top:0; left:0; right:0; z-index:200;">
    <div class="wy-header-icon-back"><a href="javascript:history.go(-1)"><span></span></a></div>
    <div class="wy-header-title">购物车</div>
</div>-->
<div id="header" style="background-color: #068dff;">
<input type="hidden" id="user_kehu_id" value="${user_kehu_id}">
<input type="hidden" id="user_shangjia_id" value="${user_shangjia_id}">
<input type="hidden" id="shouhuo_address_id" value="${pds.shouhuo_address_id}">
<input type="hidden" id="isDefault" value="${pds.isDefault}">
    <h1 class="title">提交订单</h1>
    <a href="javascript:void()" onclick="backTips()" class="icon1"><i class="icon-angle-left "></i></a>
</div>
<!--主体-->
<div class="weui-content" style="padding-top: 60px;">
    <div class="weui_cells weui_cells_access" style="margin-top: 5px;">
        <a class="weui_cell" href="api/h5KeHu/address.do?tag=1&user_shangjia_id=${user_shangjia_id}">
            <div class="weui_cell_bd weui_cell_primary ">
                <c:if test="${pds.isDefault!='' && pds.isDefault!=null }">
            		<h4 class="weui-media-box__title"><span>${pds.linkmanName }</span><span>${pds.phone }</span><span class="identity">${pds.identity }</span></h4>
                	<p class="weui-media-box__desc "><span class="default-add">${pds.lable }</span>${pds.address }</p>
            	</c:if>
            	<c:if test="${pds.isDefault=='' || pds.isDefault==null }">
            		 请您先设置默认收货地址<span style="float: right"><i class="icon-angle-right" style="font-size: 18px"></i></span>
            	</c:if>
            </div>
        </a>
    </div>
<!--     <div class="wy-media-box  address-select" style="display: none;">
        <div class="weui-media-box_appmsg">
            <div class="weui-media-box__bd">
                <a href="javascript:void(0);" class="weui-cell_access">
                    <div class="weui-media-box  address-list-box">
                        <h4 class="weui-media-box__title"><span>陈大鹏</span><span>189****3850</span><span class="identity">先生</span></h4>
                        <p class="weui-media-box__desc "><span class="default-add">公司</span>国贸路景瑞大厦A座13楼C室</p>
                    </div>
                </a>
            </div>
            <div class="weui-media-box__hd proinfo-txt-l" style="width:16px;"><div class="weui-cell_access"><span class="weui-cell__ft"></span></div></div>
        </div>
    </div> -->
    <div class="weui_cells weui_cells_access" style="margin-top: 5px">
        <a class="weui_cell" href="javascript:void(0);">
            <div class="weui_cell_bd weui_cell_primary ">
                <p><span>|</span>预计送达时间：</p>
            </div>
            15-30分钟内
       <!--      <select name="send_time_bf" class="send_time_select" id="days" style="border-color: #ddd">
                <option value="今天">今天</option>
                <option value="明天">明天</option>
                <option value="后天">后天</option>
            </select> -->
           <!--  <select name="send_time" class="send_time_select select1">
                <option class="today" value="尽快送达">尽快送达</option>
                <option class="today" value="下午15:00">下午15:00</option>
                <option class="today" value="下午15:15">下午15:15</option>
                <option class="today" value="下午15:30">下午15:30</option>
                <option class="today" value="下午15:45">下午15:45</option>
                <option class="today" value="下午16:00">下午16:00</option>
                <option class="today" value="下午16:15">下午16:15</option>
                <option class="today" value="下午16:30">下午16:30</option>
                <option class="today" value="下午16:45">下午16:45</option>
                <option class="today" value="下午17:00">下午17:00</option>
                <option class="today" value="下午17:15">下午17:15</option>
                <option class="today" value="下午17:30">下午17:30</option>
                <option class="today" value="下午17:45">下午17:45</option>
                <option class="today" value="下午18:00">下午18:00</option>
                <option class="today" value="下午18:15">下午18:15</option>
                <option class="today" value="下午18:30">下午18:30</option>
                <option class="today" value="下午18:45">下午18:45</option>
                <option class="today" value="下午19:00">下午19:00</option>
                <option class="today" value="下午19:15">下午19:15</option>
                <option class="today" value="下午19:30">下午19:30</option>
                <option class="today" value="下午19:45">下午19:45</option>
                <option class="today" value="晚上20:00">晚上20:00</option>
                <option class="today" value="晚上20:15">晚上20:15</option>
                <option class="today" value="晚上20:30">晚上20:30</option>
                <option class="today" value="晚上20:45">晚上20:45</option>
                <option class="today" value="晚上21:00">晚上21:00</option>
                <option class="today" value="晚上21:15">晚上21:15</option>
                <option class="today" value="晚上21:30">晚上21:30</option>
                <option class="today" value="晚上21:45">晚上21:45</option>
                <option class="today" value="晚上22:00">晚上22:00</option>
                <option class="today" value="晚上22:15">晚上22:15</option>
                <option class="today" value="晚上22:30">晚上22:30</option>
                <option class="today" value="晚上22:45">晚上22:45</option>
                <option class="today" value="晚上23:00">晚上23:00</option>
                <option class="today" value="晚上23:15">晚上23:15</option>
                <option class="today" value="晚上23:30">晚上23:30</option>
                <option class="today" value="晚上23:45">晚上23:45</option>
                <option class="today" value="晚上24:00">晚上24:00</option>
                <option class="today" value="晚上24:00">晚上24:00</option>
            </select>
            明天、后天送达时间
            <select name="send_time" class="send_time_select select2" style="display: none;">
                <option class="tom" value="凌晨1:00">凌晨1:00</option>
                <option class="tom" value="凌晨1:30">凌晨1:30</option>
                <option class="tom" value="凌晨2:00">凌晨2:00</option>
                <option class="tom" value="凌晨2:30">凌晨2:30</option>
                <option class="tom" value="凌晨3:00">凌晨3:00</option>
                <option class="tom" value="凌晨3:30">凌晨3:30</option>
                <option class="tom" value="凌晨4:00">凌晨4:00</option>
                <option class="tom" value="凌晨4:30">凌晨4:30</option>
                <option class="tom" value="早上5:00">早上5:00</option>
                <option class="tom" value="早上5:30">早上5:30</option>
                <option class="tom" value="早上6:00">早上6:00</option>
                <option class="tom" value="早上6:30">早上6:30</option>
                <option class="tom" value="早上7:00">早上7:00</option>
                <option class="tom" value="早上7:30">早上7:30</option>
                <option class="tom" value="上午8:00">上午8:00</option>
                <option class="tom" value="上午8:30">上午8:30</option>
                <option class="tom" value="上午9:00">上午9:00</option>
                <option class="tom" value="上午9:30">上午9:30</option>
                <option class="tom" value="上午10:00">上午10:00</option>
                <option class="tom" value="上午10:30">上午10:30</option>
                <option class="tom" value="上午11:00">上午11:00</option>
                <option class="tom" value="上午11:30">上午11:30</option>
                <option class="tom" value="中午12:00">中午12:00</option>
                <option class="tom" value="中午12:30">中午12:30</option>
                <option class="tom" value="下午13:00">下午13:00</option>
                <option class="tom" value="下午13:30">下午13:30</option>
                <option class="tom" value="下午14:00">下午14:00</option>
                <option class="tom" value="下午14:30">下午14:30</option>
                <option class="tom" value="下午15:00">下午15:00</option>
                <option class="tom" value="下午15:30">下午15:30</option>
                <option class="tom" value="下午16:00">下午16:00</option>
                <option class="tom" value="下午16:30">下午16:30</option>
                <option class="tom" value="下午17:00">下午17:00</option>
                <option class="tom" value="下午17:30">下午17:30</option>
                <option class="tom" value="下午18:00">下午18:00</option>
                <option class="tom" value="下午18:30">下午18:30</option>
                <option class="tom" value="下午19:00">下午19:00</option>
                <option class="tom" value="下午19:30">下午19:30</option>
                <option class="tom" value="晚上20:00">晚上20:00</option>
                <option class="tom" value="晚上20:30">晚上20:30</option>
                <option class="tom" value="晚上21:00">晚上21:00</option>
                <option class="tom" value="晚上21:30">晚上21:30</option>
                <option class="tom" value="晚上22:00">晚上22:00</option>
                <option class="tom" value="晚上22:30">晚上22:30</option>
                <option class="tom" value="晚上23:00">晚上23:00</option>
                <option class="tom" value="晚上23:30">晚上23:30</option>
                <option class="tom" value="晚上24:00">晚上24:00</option>
                <option class="tom" value="晚上24:00">晚上24:00</option>
            </select> -->
        </a>
    </div>
    <div class="weui_cells weui_cells_access" style="margin-top: 5px">
        <a class="weui_cell" href="javascript:void(0)">
            <div class="weui_cell_bd weui_cell_primary ">
                <p><span>|</span>支付方式</p>
            </div>
            <div class="weui_cell_ft">在线支付</div>
        </a>
    </div>
    <div class="weui-panel weui-panel_access">
        <div class="weui-panel__bd" style="padding-left: 15px;">
                <h4 class="weui-media-box__title" style="padding-left:2px;
                border-left: 2px solid #068dff;font-size: 14px;color: #000;">已选商品</h4>
                <p class="weui-media-box__desc">
                <table class="table table_select" style="font-size: 13px">
                    <tbody>
                    <c:forEach items="${list }" var="list">
                        <tr>
                            <td>${list.name}</td>
                            <td><span>x</span>${list.num }</td>
                            <td>￥<span>${list.price }</span></td>
                        </tr>
                     </c:forEach>
                     
                        <tr></tr>
                        <tr>
                            <td>餐盒费</td>
                            <td></td>
                            <td>￥<span>${canhefeiData.canhefei}</span></td>
                        </tr>
                        <tr>
                            <td>配送费</td>
                            <td></td>
                            <td>￥<span>3</span></td>
                        </tr>
                        <tr>
                            <td>合计：</td>
                            <td></td>
                            <c:if test="${empty total}">
                            	<td>￥<span>${totolData.totol+3+canhefeiData.canhefei}</span></td>
                            </c:if>
                            <c:if test="${ not empty total}">
                            	<td>￥<span>${totolData.totol+3+canhefeiData.canhefei}</span></td>
                            </c:if>
                        </tr>
                    </tbody>
                </table>
        </div>
    </div>
<div class="weui_cells weui_cells_access" style="margin-top: 5px;margin-bottom:58px;">
    <div class="" href="javascript:void(0);">
        <div style="width:20%;float:left;margin-left:15px;height:48px;line-height:48px;"><label class="weui-label wy-lab" for="liuyan">留言：</label></div>
        <div style="width:70%;float:left;">
            <textarea class="weui-textarea" id="liuyan" style="padding:16px;line-height: 20px; font-size: 14px;margin-left: -33px;"></textarea>
            <!--<input class="weui-input" type="text" placeholder="">-->
        </div>
    </div>
</div>

<!--底部-->
<div class="tqfooter">
    <div class="ft-lt" style="width: 70%;">
        <div class="price plet" style="margin-left: 10px;">￥<span id="total" class="total">${totolData.totol+3+canhefeiData.canhefei}</span>
            <!-- <p class="font5">另需配送费￥<span class="share">3</span></p> -->
        </div>
    </div>
    <div class="ft" style="width: 30%;">
        <p><a href="javascript:void(0);" onclick="submitOrder()">提交订单</a></p>
    </div>
</div>

</div>
<script>
    $(function () {
        FastClick.attach(document.body);
    });
    
    
    //提交订单
    function submitOrder(){
    	
    	var isDefault="${pds.isDefault}";
    	if(isDefault=='1'){
    		var liuyan = $("#liuyan").val();
	    	var totol = $("#total").text();
	    	var user_shangjia_id = $("#user_shangjia_id").val();
	    	var shouhuo_address_id = $("#shouhuo_address_id").val();
	    	window.location.href="api/h5KeHu/payOrder.do?liuyan="+liuyan+"&totol="+totol+"&user_shangjia_id="+user_shangjia_id+"&shouhuo_address_id="+shouhuo_address_id;
    	}else{
    		//询问框
		  layer.open({
		    content: '请选择收货地址后再提交订单！'
		    ,btn: ['确定', '取消']
		    ,yes: function(){
		    //删除临时订单
		     	window.location.href='api/h5KeHu/address.do?tag=1&user_shangjia_id=${user_shangjia_id}';
		    },no: function(index){
		    	 layer.close(index);
		    }
		  });
    	}
    	
    }
</script>
<!--预计送达时间-->
<script>
    $(function(){
        $("#days").change(function(){
            if($(this).val()=="今天"){
                $(this).siblings(".select1").show();
                $(this).siblings(".select2").hide();
            }else if($(this).val()=="明天"){
                $(this).siblings(".select2").show();
                $(this).siblings(".select1").hide();
            }else{
                $(this).siblings(".select2").show();
                $(this).siblings(".select1").hide();
            }
        })
    })
    
    //返回提示
    function backTips(){
    	var user_kehu_id = $("#user_kehu_id").val();
    	var user_shangjia_id = $("#user_shangjia_id").val();
    	//询问框
		  layer.open({
		    content: '您确定不提交订单吗？'
		    ,btn: ['确定', '取消']
		    ,yes: function(){
		    //删除临时订单
		     	window.location.href='api/h5KeHu/delTempOrder.do?user_kehu_id='+user_kehu_id+'&user_shangjia_id='+user_shangjia_id;
		    },no: function(index){
		    	 layer.close(index);
		    }
		  });
		  
    }
    
</script>
</body>
</html>