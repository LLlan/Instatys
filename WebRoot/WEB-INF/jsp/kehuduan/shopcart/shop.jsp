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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <link rel="stylesheet" href="static/css/wmpc/weui.css">
    <link rel="stylesheet" href="static/css/wmpc/jquery-weui.css">
    <link rel="stylesheet" href="static/css/wmpc/weui.min.css">
    <link rel="stylesheet" href="static/css/wmpc/font-awesome.css">
    <link rel="stylesheet" href="static/css/wmpc/demo.css">
    <link rel="stylesheet" href="static/css/wmpc/shop.css">

    <link rel="stylesheet" href="static/css/wmpc/index.css">
    <script src="static/js/wmpc/jquery-2.1.4.js"></script>
    <script src="static/js/wmpc/fastclick.js"></script>
    <script src="static/js/wmpc/jquery-weui.js"></script>
    <!--<script type="text/javascript" src="static/js/wmpc/waypoints.min.js"></script>-->
    <title>商家详情</title>
    <style>
        a{
            text-decoration: none;;
        }
        .mask{
            position:fixed;z-index:100;left:0;top:0;width:100%;height:100%;background:rgba(0,0,0,0.3);display: none;;
        }
        .gouwuche{
            width:100%;position: fixed;left:0;bottom:50px;z-index:101;font-size: 16px;color:#000;display: none;    
            max-height: 309px;
    		overflow-y: auto;
        }
        .first1{
            padding:10px 3%;background-color: #ededed;border-bottom:1px solid #cfcfcf;
        }
        .first1>span:nth-child(1){
            padding-left:3px;border-left:3px solid #0080ff;
        }
        .clearB{
            float:right;padding-right:18px;background: url(static/images/wmpc/lajitong.png) right no-repeat;background-size:16px 16px;
        }
        .gouwuche>div:not(.first1){
            padding:12px 3%;background-color: #fff;border-bottom:1px solid #cfcfcf;
        }
        /*.danjia{
            margin-left:28%;
        }*/
        .jian1{
            display:inline-block;width:27px;height: 27px;line-height: 27px;border:1px solid #dbdbdb;border-radius: 50%;text-align: center;
        }
        .jia1{
            display:inline-block;width:27px;height: 27px;line-height: 27px;border:1px solid #dbdbdb;border-radius: 50%;text-align: center;background-color: #0284fe;color:#fff;
        }
        .d-stock>span{
            display: inline-block;
            width: 32%;
        }
        .d-stock>span:first-child{
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .d-stock>span:nth-child(2){
            text-align: center;
        }
        .d-stock>span:nth-child(3){
            text-align: center;
        }
        .ovh{position:relative;}
         img {
		    max-width: 100%;
		    height: 65px;
		}
		.cart {
		    width: 35px;
		    height: 35px;
		    background: url(static/images/wmpc/gouwuche22.png) no-repeat;
		    background-size: 31px 31px;
		    position: absolute;
		    top: 8px;
		    left: 10px;
		}
		.plet {
		    font-size: 15px;
		    color: #fff;
		    height: 20px;
		    line-height: 20px;
		    margin-left: 60px;
		    margin-top: 5px;
		}
		.total {
		    color: #fff;
		    font-size: 20px;
		}
		.curr {
		    color: #333;
		}
		.finalprice, .price{
		 font-size: 17px;
		}
	
		#food_img_list li {
		    border-bottom: 1px solid #F1F1F1;
		    line-height: 19px;
		    margin: 0 5px 0;
		    padding-bottom: 5px;
		    clear: both;
		    overflow: hidden;
		}
		.shop-top img{
			border-radius:5px;
		}
		 .img_list_box{
            width:66px;
        }	
        .img_list_box img{
            height:66px;
        }
        .pcontext{
        	color: white;
		    float: left;
		    width: 25%;
		    height: 50px;
		    background-color: #000;
		    opacity: 0.5;
		    display: block;
		    text-align: center;
		    font-size: 15px;
        }
        .appmsg .weui-media-box__hd{
        	width:60px;
        	height:60px;
        }
        .priceDel{
        	color:#999;
        	font-size:14px;
        }
        div::-webkit-scrollbar{
            width:0;height:0;
        }
        .guanbi1 {
		    background: url(static/images/wmpc/error.png) no-repeat;
		    width: 35px;
		    height: 35px;
		    z-index: 1000;
		    display: block;
		    position: absolute;
		    top: 15px;
		    left: 15px;
		    cursor: pointer;
		}
		#food_detail p{
			overflow:hidden; 
			text-overflow:ellipsis;
			display:-webkit-box; 
			-webkit-box-orient:vertical;
			-webkit-line-clamp:5; 
		}
		.font6{
		    color: #666;
    		margin-top: 0px; 
		}
		.num{
			text-align:center;
		}
    </style>
</head>
<body style="background:#fff;">
<!-- 商家的起送价格 -->
<input type="hidden" id="qisonjia" value="${pds.deliveryAmount }">
<input type="hidden" id="goodsCategoryList" value="${goodsCategoryList.size()}">
<input type="hidden" id="glist" value="${goodsCategoryList}">


<div style="height:125px;background:#068dff url(static/images/wmpc/shangjiabeijingtu.png) center no-repeat;background-size:cover; position: fixed;top:0;left:0;z-index:100;width:100%;">
    <div class="" style="padding-left: 10px;padding-bottom: 25px;">
        <a class="icon1" href="api/h5KeHu/shops.do"><i class="icon-angle-left"></i></a>
    </div>
    <div class=" appmsg" >
        <div class="weui-media-box__hd shop-top">
            <c:if test="${ pd.logoImg != '' && pd.logoImg != null}">
           		<a href="javascript:void(0);"><img class="weui-media-box__thumb" src="<%=basePath%>${pd.logoImg }" alt=""></a>
            </c:if>
            <c:if test="${ pd.logoImg == '' || pd.logoImg == null}">
           		<a href="javascript:void(0);"><img class="weui-media-box__thumb" src="static/images/wmpc/dog.png" alt=""></a>
            </c:if>
        </div>
        <div class="weui-media-box__bd">
            <div class="weui-flex">
                <div class="weui-flex__item tgf1-1" style="margin-bottom: 10px;"><h5><a href="javascript:void(0);">${pd.shopName }</a></h5></div>
            </div>
            <div class="weui-flex ">
                <div class="weui-flex__item tgf1-1"><a href="javascript:void(0);" style="font-size: 14px;">${pd.address }</a></div>
            </div>
        </div>
    </div>
</div>
<div class="ovh" >
    <div  class="bgf4" style="float: left;margin-top: 125px;overflow-y:scroll;">
	    <div>
	    	<ul id="food_sort" style="height:100%;">
	            <c:forEach items="${goodsCategoryList}" var="clist" varStatus="vs">
	           <!--  <li class="foodsort hover"><a data-tpye="#st1" class="curr" >新品老盐系列</a>
	                <span class="res_sort_num">0</span>
	            </li> -->
	               	<li class="foodsort" sid="st${vs.index+1}" data-type="#st${vs.index+1}" tid="${clist.goods_category_id}" onclick="getListByType('${clist.goods_category_id}','${user_shangjia_id}','${clist.categoryName}','st${vs.index+1}')" >
	               		<a  href="javascript:void(0);" value="${clist.categoryName}" >${clist.categoryName }</a>
	            		<input type="hidden"  value="${clist.categoryName}" id="categoryName" name="categoryName"/>
	            		<input type="hidden" value="${user_shangjia_id}"  id="user_shangjia_id" name="user_shangjia_id"/>
	               	</li>
	         	</c:forEach>
	         </ul>
	    </div>
    </div>
    
    <div id="scroll_food" class="right" style="width:75%;overflow-y:scroll;margin-top:125px;margin-bottom:50px;">
        <div id="food_img_list" class="bgf" style="height:100%;overflow-y:scroll;">
            <div class="foodlist_box" style="height:100%;overflow-y:scroll;">
            	<c:forEach items="${goodsList}" var="good" varStatus="vs">
            	<div id="st${vs.index+1}" style="margin-top: 4px;">
            	<span style="font-size: 11px;margin-left: 6px;">${good[0].categoryName}</span>
            		<c:forEach items="${good}" var="good">
                	<ul style="padding-top: 10px;">
                    <li>
                        <div class="img_list_box" onclick="goDetail('${good.goods_id }','${good.goodsNum}')">
	                            <a href="javascript:void(0)"><img src="<%=basePath%>${good.goodsImg }" alt=""/></a>
                        </div>
                        <div id="shop_style">
                            <h3 class="food_img_name">${good.goodsName }</h3>
                            <p class="font12 clo9">销量：${good.goodsNums }</p><!--无大小份-->
                            <div class="weui-flex">
                                <div class="weui-flex__item redprice">
                                ¥<span class="price" style="margin-right:10px;">${good.presentPrice }</span>
                                <span class="price priceDel"> ¥<del>${good.originalPrice }</del></span>
                                </div> 
                                <c:if test="${good.goodsState==1 }">
                                <div class="weui-flex__item shop-cart">
                                    <div class="d-stock">
                                        <a class="minus decrease" onclick="reduce('${good.goods_id}','${good.goodsName }','${good.presentPrice }','${good.canhefei }',this)">-</a>
                                        <input id="num" class="result text_box" type="text" value="0">
                                        <a class="increase" onclick="addCar('${good.goods_id}','${good.goodsName }','${good.presentPrice }','${good.canhefei }',this)">+</a>
                                    </div>
                                </div>
                               </c:if>
                                <c:if test="${good.goodsState==0}">
                           		    <span style="font-size: 13px;color: red;font-weight: bolder;">暂停销售</span> 
                                 </c:if>
                            </div>
                        </div>
                    </li>
                	</ul>
                	</c:forEach>
                	</div>
                   </c:forEach> 
               <%--  <div id="food_img" style="left: 20.7px;">
				    <div id="fullbg" class="fullbg" style="display: none;"></div>
				    <div class="food_img_box pop" style="display: none;">
				        <i class="guanbi"></i>
				        <p class="food_img_see ">
				             <c:if test="${ pds.goodsImg != '' && pds.goodsImg != null}">
				                    <img src="<%=basePath%>${pds.goodsImg }" alt="" id="food_img_path" style="width: 100%; height: 80%;"/>
				             </c:if>
				             <c:if test="${ pds.goodsImg == '' || pds.goodsImg == null}">
				            		<!-- <img  class="img_list_img" src="static/images/wmpc/nm.jpeg" > -->
				            		<img src="static/images/wmpc/nm.jpeg" id="food_img_path" style="width: 100%; height: 80%;">
				             </c:if>
				        </p>
				        <div id="cainame" class="center ovh font16">${pds.goodsName }</div>
				        <div id="img_list_price_box">2786573</div>
				        <p id="food_desc">144537</p>
				    </div>
				</div> --%>
            </div>
        </div>
    </div>
</div>

<!--底部-->
 <div class="mask"></div>
<div class="gouwuche">
    <div class="first1"><span class="ydsp">已点商品</span><span class="clearB">清空购物车</span></div>
    
    <!-- <div class="d-stock">
        <span>烧腊卤肉饭</span>
        <span class="danjia">￥15 </span>
        <span class="d-stock" style="display: inline-block;">
            <a class="jian1">-</a>
            <input   type="text" value="0" style="width:15px;border:none;outline: none;">
            <a  class="jia1">+</a>
        </span>
    </div>
    <div class="d-stock">
        <span>烧腊卤肉饭</span>
        <span class="danjia">￥15 </span>
        <span class="d-stock" style="display: inline-block;">
            <a class="jian1">-</a>
            <input type="text" value="0" style="width:15px;border:none;outline: none;">
            <a  class="jia1">+</a>
        </span>
    </div> -->
    
</div>


<div class="tqfooter">
    <div class="ft-lt">
        <div class="cart">
            <a class="nm" href="javascript:void(0);">
                <div class="cart-num"></div>
            </a>
        </div>
        <div class="price plet" >
            <div class="peis" style="display: none;">
                <span>￥</span>
                <span id="total" class="total"></span>
            </div>
            <p class="font6">
          		<span class="share" style="font-size: 12px;">另需配送费￥3</span>
          		   <span style="float:right;margin-right:20px;margin-top:3px;font-size: 14px;" class="qis">￥${pds.deliveryAmount }起送</span>
            </p>
           
        </div>
    </div>
 <!--    <div class="ft-rt" style="display: block;">
        <p><a style="text-decoration: none;" href="javascript:void(0);" data-target="#join_cart" class="open-popup">加入购物车</a></p>
    </div> -->
    <div class="ft"  style="display: none;">
    	<input type="hidden" id="pstr" name="pstr"/>
    	<input type="hidden" id="name" name="name"/>
    	<input type="hidden" id="num" name="num"/>
    	<input type="hidden" id="presentPrice" name="presentPrice"/>
    	<input type="hidden" id="canhefei" name="canhefei"/>
    	
        <p><a id="jiesuan" href="javascript:void(0);" onclick="goTotol()">去结算</a></p>
    </div>
    <div class="pcontext" >
    </div>
</div>
<!-- <div id="join_cart" class='weui-popup__container popup-bottom'  >
    <div class="weui-popup__overlay" style="opacity:0.5;"></div>
    <div class="weui-popup__modal" >
        <div class="modal-content">
            <div class="weui-msg" style="padding-top:0;">
                <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
                <div class="weui-msg__text-area">
                    <h2 class="weui-msg__title">成功加入购物车</h2>
                    <p class="weui-msg__desc">亲爱的用户，您的商品已成功加入购物车，为了保证您的商品快速送达，请您尽快到购物车结算。</p>
                </div>
                <div class="weui-msg__opr-area">
                    <p class="weui-btn-area">
                        <a href="static/shopcart/" class="weui-btn weui-btn_primary">去购物车结算</a>
                        <a href="javascript:;" class="weui-btn weui-btn_default close-popup">不，我再看看</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div> -->

<div>
	
</div>

<script>
//数组的包含
/* 	Array.prototype.contains = function (obj) {  
    var i = this.length;  
    while (i--) {  
        if (this[i] === obj) {  
            return true;  
        }  
    }  
    return false;  
	};  */
	//var arr = new Array();
	function goDetail(goods_id,goodsNum){
		var url= 'api/h5KeHu/shopi.do?goods_id='+goods_id+'&goodsNum='+goodsNum;
		window.location.href=url;
	}




	$(function(){
		$("#food_sort").find("li:first-child").find("a").addClass("curr");
		$("#food_sort").find("li:first-child").addClass("hover");
		var category_id = $("#food_sort").find("li:first-child").attr("tid");
		var sid = $("#food_sort").find("li:first-child").attr("sid");
		var user_shangjia_id = $("#user_shangjia_id").val();
		var categoryName = $("#categoryName").val();
		var url="api/h5KeHu/getDataByCategoryNameAndId.do";
		var url2="api/h5KeHu/goodsCategoryList.do";
		$(".foodsort:first-child>a:first-child").addClass("curr"); 
		arr.push(categoryName);
		/* 
		$.ajax({
			url:url2,
			type:"post",
			data:{
    			"user_shangjia_id":user_shangjia_id
    		},
    		dataType:"json",
			success:function(data){
				//根据种类遍历查询出来商品
				for(var i=0;i<data.goodsCategoryList.length;i++){
					console.log(data.goodsCategoryList[i].goods_category_id)
					setTimeout(aaa,1500);
					 sid = "st"+(i+1);
					 var fhtml = "<div id='"+sid+"' class='fb2'></div>";
					 $(".foodlist_box").append(fhtml);
					RendenceData(url,data.goodsCategoryList[i].goods_category_id,data.goodsCategoryList[i].user_shangjia_fid,data.goodsCategoryList[i].categoryName,sid);                    
				}
			}
			}); */
			
			
			
			   // shop-cart text_box .find(".food_img_name")
			
		
	});

	function getListByType(category_id,user_shangjia_id,category_name,sid){
		//$(".foodlist_box").empty();
			/* for(var i in arr){
			//console.log("i是："+arr[i])
				if(arr.contains(category_name)){
					//alert("已经存在了")
					return;
				}else{
					arr.push(category_name);
					var url="api/h5KeHu/getDataByCategoryNameAndId.do";
					//RendenceData(url,category_id,user_shangjia_id,category_name,sid);
					return;
				}
			} */
		
	
	} 
	
	
	function RendenceData(url,category_id,user_shangjia_id,category_name,sid){
	var category_name = category_name;
		$.ajax({
			url:url,
			type:"post",
			data:{
    			"user_shangjia_id":user_shangjia_id,
    			"category_id":category_id
    		},
    		dataType:"json",
			success:function(data){
			//顶部名称
				var fhtml = "<span style='font-size:11px;margin-left:6px;'>"+category_name+"</span>";
			    $(".fb2").append(fhtml);
				for(var i=0;i<data.shangpinList.length;i++){
					var appendHtml = '<ul style="padding-top:10px;"><li>';
					appendHtml+='<div class="img_list_box"><img  src="<%=basePath%>'+data.shangpinList[i].goodsImg+'"/></div>';
					appendHtml+='<div id="shop_style"><h3 class="food_img_name">'+data.shangpinList[i].goodsName+'</h3><p class="font12 clo9">销量：1</p>';
					appendHtml+='<div class="weui-flex" style="margin-top: 5px;">';
					appendHtml+='<div class="weui-flex__item redprice">¥<span class="price">'+data.shangpinList[i].presentPrice+'</span><span class="price priceDel"> ¥<del>'+data.shangpinList[i].originalPrice+'</del></span></div>';
					appendHtml+='<div class="weui-flex__item shop-cart">';
					appendHtml+='<div class="d-stock">';
					appendHtml+='<a class="minus decrease" onclick="reduce(this)">-</a>';
					appendHtml+='<input id="num" class="result text_box" type="text" value="0">';
					appendHtml+='<a class="increase"  onclick="addCar(\''+data.shangpinList[i].goods_id+'\',\''+data.shangpinList[i].goodsName+'\',\''+data.shangpinList[i].presentPrice+'\',\''+data.shangpinList[i].canhefei+'\',this)">+</a>';
					appendHtml+='</div></div></div></div></li></ul>';
	                $(".fb2").append(appendHtml);
                 }  
                 
                
			}
			
		});
	}
	
	/*************************页面遍历模式*********************************/
	  /*购物车增加*/
        /* $(".increase").click(function(){
            var add=$(this).parent().find(".text_box").val();
            add++;
            if(add>0){
                $(this).parent().find(".minus").fadeIn();
                $(this).parent().find(".text_box").fadeIn();
            }
            $(this).parent().find(".text_box").val(add);
            Total();
        });
         */
        
       /*购物车减少*/
    /*     $(".minus").click(function(){
            var reduce=$(this).parent().find(".text_box").val();
            if(reduce>1){
                reduce--;
                $(this).parent().find(".text_box").val( reduce)
            }else{
                $(this).fadeOut();
                $(this).parent().find(".text_box").fadeOut();
                $(this).parent().find(".text_box").val(0);
                $("#total").html("");
            }
            Total();
        });
         */
    	/*************************页面遍历模式*********************************/    
        
        
	/******************************下面是ajax方式*******************************/
	 /*购物车减少*/
	function reduce(id,name,presentPrice,canhefei,e1){
            var reduce=$(e1).parent().find(".text_box").val();
            if(reduce>1){
                reduce--;
                $(e1).parent().find(".text_box").val( reduce);
            }else{
                $(e1).fadeOut();
                $(e1).parent().find(".text_box").fadeOut();
                $(e1).parent().find(".text_box").val(0);
                $("#total").html("");
            }
            Total();
            var num=$(e1).parent().find(".text_box").val();//数量
            var user_shangjia_id = $("#user_shangjia_id").val();
            $.post('api/h5KeHu/saveOrderTemp.do',
           {
	           id:id,
	           name:name,
	           price:presentPrice,
	           canhefei:canhefei,
	           user_shangjia_id:user_shangjia_id,
	           num:num,
	           tag:2
           },function(data){
           		
           }); 
	}
	
	
	
		/*购物车增加*/
		 function addCar(id,name,presentPrice,canhefei,e1){
            var add=$(e1).parent().find(".text_box").val();
            add++;
            if(add>0){
                $(e1).parent().find(".minus").fadeIn();
                $(e1).parent().find(".text_box").fadeIn();
            }
            $(e1).parent().find(".text_box").val(add);
            Total();
          
            var goods_id = id;//商品id
            var name = name;//商品名
            var presentPrice = presentPrice;//现价
            var canhefei = canhefei;//餐盒费
			var num=$(e1).parent().find(".text_box").val();//数量
            var user_shangjia_id = $("#user_shangjia_id").val();
			 $.post('api/h5KeHu/saveOrderTemp.do',
           {
	           id:id,
	           name:name,
	           price:presentPrice,
	           canhefei:canhefei,
	           user_shangjia_id:user_shangjia_id,
	           num:num,
	           tag:1
           },function(data){
           		console.log(data);
           }); 
		//	var pstr = goods_id+","+name+","+num+","+presentPrice+","+canhefei+","+"yizhan"+",";//拼接字符串
	} 
	
	
	
	/******************************ajax方式*******************************/
	//计算合计
	 function Total(){
            var price=0;
            $(".shop-cart").each(function(){
                price += parseInt($(this).find('input[class*=result]').val()) * parseFloat($(this).siblings().find('span[class*=price]').text());

            });
            var n=0;
            var nIn = $("li.foodsort a.curr").attr("data-tpye");
            $(nIn + " input[class*=result]").each(function (){
                n+=parseInt($(this).val());
                if(n > 0){
                    $(".curr").next().html(n).show();
                }else{
                    $(".curr").next().hide();
                }
            });


            var num=0;
            $(".text_box").each(function(){
                num+=parseInt($(this).val());
            });
            if(num>0){
                $(".ft-lt").css("width","75%");
                $(".ft-rt").show();
                $(".ft").show();
                $(".font6").css("margin-top","0px");
                $(".qis").hide();
                $(".peis").show();
                $(".cart-num").show().html(num);
                
                $("#total").html(price.toFixed(2));
	            //判断是否大于起送价
	            var chajia = parseInt($("#total").html()) - parseInt($("#qisonjia").val());
	            var chajiax =  parseInt($("#qisonjia").val()) - parseInt($("#total").html());
	            if(chajia>=0){
	            	 // $(".ft-lt").css("width","100%");
	            }else{
	           		 $(".ft").hide();
	           		 $(".pcontext").html("还差￥"+chajiax);
	            }
            }else{
                $(".ft-lt").css("width","100%");
                $(".ft-rt").hide();
                $(".ft").hide();
                 $(".qis").show();
                $(".font6").css("margin-top","13px").show();
                $(".peis").hide();
                $(".cart-num").hide().html("");
                $(".pcontext").text();
            }
           
        }   
	
		
		//去结算详情页面
		function goTotol(){
			var total = $("#total").html();
			var pstr=$("#pstr").val();
			window.location.href="<%=basePath%>api/h5KeHu/orderInfo.do?strs="+pstr+"&total="+total+"&user_shangjia_id="+$("#user_shangjia_id").val();
		}
		
		
	
    $(function () {
       // FastClick.attach(document.body);
    });
</script>
<script>
    $(".img_list_box img").click(function(e){
    	var hei1 = $(window).height()-385;
    	$("#food_detail").height(hei1);
        /* $("#fullbg").show(); */
        $(".food_img_box").show();
        /* $(".food_img_box").css("position","absolute");
        $(".food_img_box").css("top","0");
        $(".food_img_box").css("left","0");
        $(".food_img_box").css("z-index","9999"); */
       // e.stopPropagation();
        
    });
    $(".food_img_box .guanbi1").click(function(){
        $("#fullbg").hide();
        $(".food_img_box").hide();
    });
</script>
<script type="text/javascript">
	//a是以st开头的id的集合
    var a=$('[id^="st"]');
    var arr1=[];
    var arr=[];
    var hei=$('body>div:first-child').height();
    for(var i=0;i<a.length;i++){
        var l=$(a[i]).offset().top;
        arr1.push(l);
        var m=$(a[i]).position().top;
        arr.push(m);
    }
    $('li.foodsort').click(function(){
        	$('.foodlist_box').scrollTop(arr[$(this).index()]-125);
        	$(this).addClass('hover').siblings().removeClass('hover');
            $(this).find("a").addClass('curr').parent().siblings().find("a").removeClass('curr');
    	});
   /*  $('.foodlist_box').scroll(function(e){
    	e.preventDefault();
        var n=$('.foodlist_box').scrollTop();
        for(var i=0;i< a.length;i++){
	        if(n>=0&&n<=arr1[0]){
                	console.log(n);
                    $('.foodsort:first-child').addClass('hover').siblings().removeClass('hover');
                    $('.foodsort:first-child').find("a").addClass('curr').parent().siblings().find("a").removeClass('curr');
            }
            if(n>arr1[i]-hei&&n<arr1[i+1]-hei){
                if(n>arr1[arr1.length-2]&&n<arr1[arr1.length-1]){
                    $('.foodsort:last-child').addClass('hover').siblings().removeClass('hover');
                    $('.foodsort:last-child').find("a").addClass('curr').parent().siblings().find("a").removeClass('curr');
                }else{
                    $('.foodsort').eq(i+1).addClass('hover').siblings().removeClass('hover');
                    $('.foodsort').eq(i+1).find("a").addClass('curr').parent().siblings().find("a").removeClass('curr');
                }
            }
        }
    }); */
</script>

<!--控制下方购物车JS-->
<script>
	//购物车点击事件
    $(".cart").click(function(){
        if($(".gouwuche").is(":hidden")){
            $(".mask").fadeIn();
            $(".gouwuche").slideDown();
           	//清空
               $("div.gouwuche *").not(".first1").not(".ydsp").not(".clearB").each(function() { // "*"表示div.gouwuche下的所有元素
          		  $(this).remove();
       		 });
       		 
       		var user_shangjia_id = $("#user_shangjia_id").val();
       		//定义购物车变量
       		var carNum = 0;
       		var totol = 0;
             //请求
           $.post('api/h5KeHu/gwcList.do',
           {},function(data){
          
           	if(data.finalList.length>0){
           		for(var i=0;i<data.finalList.length;i++){
		    		var appendHtml = '<div class="d-stock">';
		   			 appendHtml+='<span>'+data.finalList[i].name+'</span>';
		   			 appendHtml+='<span class="danjia">￥'+data.finalList[i].price+'</span>';
		   			 appendHtml+='<span class="d-stock" style="display: inline-block;">';
		   			 appendHtml+='<a class="jian1" onclick="jian1(\''+data.finalList[i].sp_id+'\',\''+data.finalList[i].name+'\',\''+data.finalList[i].price+'\',\''+data.finalList[i].num+'\',\''+data.finalList[i].canhefei+'\',\''+user_shangjia_id+'\',this)">-</a>';
		   			 appendHtml+='<input type="text" class="num" value="'+data.finalList[i].num+'" style="width:15px;border:none;outline: none;">';
		   			 appendHtml+='<a class="jia1" onclick="jia1(\''+data.finalList[i].sp_id+'\',\''+data.finalList[i].name+'\',\''+data.finalList[i].price+'\',\''+data.finalList[i].num+'\',\''+data.finalList[i].canhefei+'\',\''+user_shangjia_id+'\',this)">+</a>';
		   			 appendHtml+='</span></div>';
           			$(".first1").after(appendHtml);
           			
           			carNum +=data.finalList[i].num;
           			totol +=data.finalList[i].price * data.finalList[i].num;
           		}
           		   //购物车数量
           		  $(".cart-num").show().html(carNum);
           		  console.log("合计是"+totol);
           		//合计
   		 		 $(".peis").show();
   		 		 $("#total").html(totol);
           		
           	 }else{
           	 	var appendHtml = '<div class="d-stock" style="text-align:center;font-size:14px">';
           	 	 appendHtml+='您还没点餐呢';
           	 	 appendHtml+='</div>';
           	 	 $(".first1").after(appendHtml);
           	 }
           });  
        }else{
            $(".mask").fadeOut();
            $(".gouwuche").slideUp();
          
        }
       
        
    });
    
   //重新渲染 
    function xuanran(name,tag){
    	 	//清空
            $("div.gouwuche *").not(".first1").not(".ydsp").not(".clearB").each(function() { 
       		  $(this).remove();
    		 });
       		var user_shangjia_id = $("#user_shangjia_id").val();
       		//定义购物车变量
       		var carNum = 0;
       		var totol = 0;
       		var sname =name;
           //请求
           $.post('api/h5KeHu/gwcList.do',
           {},function(data){
           //显示购物车数量
             $(".cart-num").show().html(data.finalList.length);
             //判断购物车有东西
           	if(data.finalList.length>0){
           		for(var i=0;i<data.finalList.length;i++){
		    		var appendHtml = '<div class="d-stock">';
		   			 appendHtml+='<span>'+data.finalList[i].name+'</span>';
		   			 appendHtml+='<span class="danjia">￥'+data.finalList[i].price+'</span>';
		   			 appendHtml+='<span class="d-stock" style="display: inline-block;">';
		   			 appendHtml+='<a class="jian1" onclick="jian1(\''+data.finalList[i].sp_id+'\',\''+data.finalList[i].name+'\',\''+data.finalList[i].price+'\',\''+data.finalList[i].num+'\',\''+data.finalList[i].canhefei+'\',\''+user_shangjia_id+'\',this)">-</a>';
		   			 appendHtml+='<input type="text" class="num" value="'+data.finalList[i].num+'" style="width:15px;border:none;outline: none;">';
		   			 appendHtml+='<a class="jia1" onclick="jia1(\''+data.finalList[i].sp_id+'\',\''+data.finalList[i].name+'\',\''+data.finalList[i].price+'\',\''+data.finalList[i].num+'\',\''+data.finalList[i].canhefei+'\',\''+user_shangjia_id+'\',this)">+</a>';
		   			 appendHtml+='</span></div>';
           			$(".first1").after(appendHtml);
           		     carNum +=data.finalList[i].num;
           		     totol +=data.finalList[i].price * data.finalList[i].num;
           		}
           		console.log("合计是"+totol);
           		//购物车数量
           		 $(".cart-num").show().html(carNum);
           		//合计
   		 		 $(".peis").show();
   		 		 $("#total").html(totol);
		         //判断是否大于起送价
		          var chajia = parseInt(totol) - parseInt($("#qisonjia").val());
		         var chajiax =  parseInt($("#qisonjia").val()) - parseInt(totol);
		         if(chajia>=0){
		         	    $(".ft-lt").css("width","75%");
		                $(".ft-rt").show();
		                $(".ft").show();
		                $(".font6").css("margin-top","0px");
		                $(".qis").hide();
		                $(".peis").show();
		         }else{
	        		 $(".ft").hide();
	        		 $(".pcontext").html("还差￥"+chajiax);
		         } 
		         if(tag==1){
		         		//查找原来所有的物品 改变上边物品的数量
					    var all = $(".foodlist_box #shop_style");
						for(var i=0;i<all.length;i++){
							console.log(all.eq(i).find("h3").html());
							if(all.eq(i).find("h3").html()==sname){
								console.log(all.eq(i).find(".shop-cart").find(".text_box").val())
								var num = all.eq(i).find(".shop-cart").find(".text_box").val();
								num++;
								//alert(num)
								all.eq(i).find(".shop-cart").find(".text_box").val(num)
							}
						} 
		         }else{
				        //--------------查找原来所有的物品 改变上边物品的数量begin-----------// 
					    var all = $(".foodlist_box #shop_style");
						for(var i=0;i<all.length;i++){
							console.log(all.eq(i).find("h3").html());
							if(all.eq(i).find("h3").html()==sname){
								console.log(all.eq(i).find(".shop-cart").find(".text_box").val())
								var num = all.eq(i).find(".shop-cart").find(".text_box").val();
								num--;
								//alert(num)
								all.eq(i).find(".shop-cart").find(".text_box").val(num)
								if(num==0){
									all.eq(i).find(".shop-cart").find(".text_box").hide();
									all.eq(i).find(".shop-cart").find(".text_box").parent().find(".decrease").hide();
								}
							}
						}
					 //--------------查找原来所有的物品 改变上边物品的数量end-----------// 
           		}	
           			
           	 }else{
           	 	
           	 	var appendHtml = '<div class="d-stock" style="text-align:center;font-size:14px">';
           	 	 appendHtml+='您还没点餐呢';
           	 	 appendHtml+='</div>';
           	 	 $(".first1").after(appendHtml);
           	 	 
           	 	 //收起购物车
           	 	$(".gouwuche").hide();
                $(".mask").hide();
                $(".peis").hide();
                //隐藏右下脚的还差几元起送字样
                $(".ft-lt").css("width","100%");
                $(".ft-rt").hide();
                $(".ft").hide();
                $(".qis").show();
                //购物车隐藏
                $(".cart-num").hide();
                
	                //查找原来所有的物品 改变上边物品的数量
				    var all = $(".foodlist_box #shop_style");
					for(var i=0;i<all.length;i++){
						//输出菜品名字
						console.log(all.eq(i).find("h3").html());
						if(all.eq(i).find("h3").html()==sname){
						//输出菜品的数量
							console.log(all.eq(i).find(".shop-cart").find(".text_box").val())
							var num = all.eq(i).find(".shop-cart").find(".text_box").val();
							num--;
							//alert(num)
							all.eq(i).find(".shop-cart").find(".text_box").val(num)
							if(num==0){
								all.eq(i).find(".shop-cart").find(".text_box").hide();
								all.eq(i).find(".shop-cart").find(".text_box").parent().find(".decrease").hide();
							}
						}
					} 
				
           			
           	 }
           }); 
    }
    
    
     //底部清空购物车
	  $(".clearB").click(function(){
                $(".gouwuche").hide();
                $(".mask").hide();
                 $.post('api/h5KeHu/clearCar.do',
	           {},function(data){
	           		if(data.respMsg=="01"){
	           			location.href=location.href;
	           		}
	           });
                
       });

		//底部购物车加
		function jia1(id,name,price,num,canhefei,user_shangjia_id,e1){
			var sname = name;
			$.post('api/h5KeHu/saveOrderTemp.do',
           {
	           id:id,
	           name:name,
	           price:price,
	           canhefei:canhefei,
	           user_shangjia_id:user_shangjia_id,
	           num:num,
	           tag:1
           },function(data){
           		//console.log(data)
           }); 
           setTimeout('xuanran(\''+sname+'\',1)',100);
		
		  /*  var num = $(e1).parent().find(".num").val();
		    num++;
		    $(e1).parent().find(".num").val(num); */
		
			/*  $(this).css("text-decoration","none");
                var jia=$(this).prev().val();
                jia++;
                $(this).prev().val(jia);
                $(this).prev().prev().prev().text("￥"+jia*15); */
		}
               
          //底部购物车减
          function jian1(id,name,price,num,canhefei,user_shangjia_id,e1){
          var sname = name;
          $.post('api/h5KeHu/saveOrderTemp.do',
           {
	           id:id,
	           name:name,
	           price:price,
	           canhefei:canhefei,
	           user_shangjia_id:user_shangjia_id,
	           num:num,
	           tag:2
           },function(data){
           		console.log(data)
           }); 
           //停顿 防止ajax延迟导致前面一个请求先执行了
           setTimeout('xuanran(\''+sname+'\',2)',100);
           
          }
          
</script>
<script>
	$(function(){
		$(".bgf4").height($(window).height()-175);
		$(".right").height($(window).height()-175);
	});
</script>
</body>
</html>