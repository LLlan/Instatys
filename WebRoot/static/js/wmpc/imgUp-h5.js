var delParent;//删除.z_file元素   file
var delParent1;//删除.up-section元素    close-upimg
var defaults = {
	fileType         : ["jpg","png","bmp","jpeg"],   // 上传文件的类型
	fileSize         : 1024 * 1024 * 20 ,            // 上传文件的大小 20M
	fileNum			 :9								 //	上传文件的数量
};
var widUp = $(".z_file").width();
/*点击图片的文本框*/
function imgUponchange(obj){
	//验证文件的格式以及大小是否符合要求
	var fileList = validateUp(obj.files);
	if(fileList.length>0){
		//存放图片的父亲元素
		var imgContainer = $(obj).parents(".z_photo"); 
		//遍历得到的图片文件
		var numUp = imgContainer.find(".up-section").length;
		
		//创建显示图片的区域---star
		var $section = $("<section class='up-section fl loading'>");
		     imgContainer.append($section);
		var $span = $("<span class='up-span'>");
		     $span.appendTo($section);
	    var $img0 = $("<img onclick='del(this);' class='close-upimg' tid='0'>").on("click",function(event){
			    event.preventDefault();
				event.stopPropagation();
				$(".works-mask").show();
				delParent = $(obj).parent();
			});
			$img0.attr("src","static/images/wmpc/a7.png").appendTo($section);
		var imgUrl = window.URL.createObjectURL(fileList[0]);
	    var $img = $("<img class='up-img up-opcity'>");
	         $img.attr("src",imgUrl);
	         $img.appendTo($section);
	    $(obj).parent().hide();//---end
	     
	    //H5部分
	    if(numUp < (defaults.fileNum-1)){
	    	 $(obj).parent().parent().append(
	    			'<section class="z_file fl" >'+
	 				'<img src="static/images/wmpc/add1.png" class="add-img">'+
	 				'<input onchange="imgUponchange(this);" type="file" name="imgPath" class="file" accept="image/*" />'+
	 				'</section>');	 
	    }
	    //H5end
		$(".z_file").height(widUp);
		$(".upimg-div .up-section").height(widUp);
		$(".z_photo .up-img").height(widUp);
		$(".z_file fl").height(widUp);
		$(".z_file .add-img").height(widUp);
		$(".up-section .up-span").height(widUp);
	}
	
    setTimeout(function(){
         $(".up-section").removeClass("loading");
	 	 $(".up-img").removeClass("up-opcity");
	 },450);
};


//点击X号删除图片
function del(obj){
	$(".works-mask").show();
	delParent = $(obj).parent().prev();//指定位置元素的上一个元素.prev()
	delParent1 = $(obj).parent();
}	
function ok(){
	$(".works-mask").hide();
	var num=delParent.parent().find(".up-section").length;
	if(num == defaults.fileNum){
		delParent.parent().append(
    			'<section class="z_file fl" >'+
 				'<img src="static/images/wmpc/add1.png" class="add-img">'+
 				'<input onchange="imgUponchange(this);" type="file" name="imgPath" class="file" accept="image/*"/>'+
 				'</section>');	
	}
	delParent.remove();
	delParent1.remove();
}
function no(){
	$(".works-mask").hide();
}
//点击X号删除图片end
/////////////////////////////////////根据自己的功能自定义该部分方法////////////////////////////////////////
//1.注意设置processData和contentType属性为false,防止JQ胡乱解析文件格式
/*
function submit1(){
	console.log(fileList);
	console.log(curFiles);
	var formData = new FormData($("form")[0]); // 使用某个表单作为初始项
	for (var i = 0, j = curFiles.length; i < j; ++i) {
		formData.append('imgPathFile', curFiles[i]);
	}
	$.ajax({
		url: "api/keHuPc/ceshi2.do",
		type: "POST",
		data: formData,
		dataType:"json",
		processData: false, // 告诉jQuery不要去处理发送的数据
		contentType: false, // 告诉jQuery不要去设置Content-Type请求头
		success:function(){
			
		}
	});
}
*/
/////////////////////////////////////根据自己的功能自定义该部分方法////////////////////////////////////////
//判断所选文件是否符合条件
function validateUp(files){
	var arrFiles = [];//替换的文件数组
	for(var i = 0, file; file = files[i]; i++){
		//获取文件上传的后缀名
		var newStr = file.name.split("").reverse().join("");
		if(newStr.split(".")[0] != null){
				var type = newStr.split(".")[0].split("").reverse().join("");
				if(jQuery.inArray(type, defaults.fileType) > -1){
					// 类型符合，可以上传
					if (file.size >= defaults.fileSize) {
						alert(file.size);
						alert('您这个"'+ file.name +'"文件大小过大');
					} else {
						// 在这里需要判断当前所有文件中
						arrFiles.push(file);
					}
				}else{
					alert('您这个"'+ file.name +'"上传类型不符合');
				}
			}else{
				alert('您这个"'+ file.name +'"没有类型, 无法识别');
			}
	}
	return arrFiles;
}