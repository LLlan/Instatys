package com.yizhan.controller.app.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.yizhan.controller.base.BaseController;
import com.yizhan.service.information.keHu.KeHuService;
import com.yizhan.service.information.shouhuoAddress.ShouhuoAddressService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.Const;
import com.yizhan.util.DateUtil;
import com.yizhan.util.FileUpload;
import com.yizhan.util.MD5;
import com.yizhan.util.PageData;
import com.yizhan.util.PathUtil;
import com.yizhan.util.SmsUtil;
import com.yizhan.util.Tools;

/**
  * app用户-接口类 
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/api/kehu")
public class appKehuController extends BaseController{
	@Resource(name="keHuService")
	private KeHuService keHuService;
	@Resource(name="shouhuoAddressService")
	private ShouhuoAddressService shouhuoAddressService;
	
	/**
	 * 判断用户登录状态,缓存是否有效
	 * @throws Exception 
	 */
	@RequestMapping(value="/judgeAgainDeploy")
	@ResponseBody
	public Object judgeAgainDeploy() throws Exception{
		logBefore(logger, "--判断重新部署--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");
		
		String respCode = "00";
		String respMsg="请求失败，请联系管理员";
		try{
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="backCode为空,请重新登录";	
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效，请重新登录";
				}else{
					map.put("cacheData", cacheData);
					respCode="01";
					respMsg="缓存信息有效";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 发送验证码
	 */
	@RequestMapping(value="/sendSecurityCode")
	@ResponseBody
	public Object sendSecurityCode(){
		logBefore(logger, "---发送验证码---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respCode = "00";
		String respMsg="";
		//测试时,传递的参数,之后删除
		//pd.put("phone", "18289763525");
		
		//电话号码
		String phone = pd.getString("phone");
		if(Tools.notEmpty(phone)){
			//发送验证码
			Map mapResult  = SmsUtil.sendMsM(phone);
			if(mapResult.get("result").equals("验证码发送成功")){
				String securityCode =  (String)mapResult.get("yanzhengma");
				//请求成功信息
				respCode = "01";
				//注册请求识别码
				map.put("securityCode", securityCode);
				respMsg="请求成功,securityCode是我返回给你的正确的验证码，存于你们本地，最后注册请求时将该码与用户填的验证码一起传来。";
			}else{
				//请求失败
				respMsg="失败";
			}
			
		}else{ 
			//请求失败
			respMsg="电话号码不能为空";
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 注册
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object register(HttpServletRequest request){
		logBefore(logger, "---注册--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		//pd.put("phone", "18289763525");
		//pd.put("securityCode", "123456");
		//pd.put("yanzhengma", "123456");
		
		String respCode = "00";
		String respMsg = "注册失败";
		try{
			//判断是否已经获取验证码
			if(Tools.isEmpty(pd.getString("securityCode"))){
				respMsg = "请先获取短信验证码";
			}else {
				if(Tools.isEmpty(pd.getString("phone")) || Tools.isEmpty(pd.getString("yanzhengma"))){
					respMsg = "手机号码或者验证码不能为空";
				}else{
					//自定义超时返回规则
					 if(pd.getString("yanzhengma").equals("abcd")){
						 	respMsg = "验证码超时";
					 }else if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){ 
						 	respMsg = "验证码不正确";
					 }else{
					 	//判断用户是否存在
						PageData pds = keHuService.getDataByPhone(pd);
						if(pds!=null){
							respMsg = "该手机号已经注册";
						}else{
							pd.put("user_kehu_id", this.get32UUID());//主键
							pd.put("userName", pd.getString("phone"));//默认用户名为手机号
							pd.put("registerTime", DateUtil.getTime());//注册时间	
							pd.put("last_login_time", DateUtil.getTime());//最近登陆时间	
							pd.put("ip", request.getRemoteHost());//ip地址	
							pd.put("status", "1");//1 使用中 0 已停用
							pd.put("bz", "app用户");
							//保存客户端用户信息
							keHuService.saveKeHuUser(pd);
							respCode = "01";
							respMsg = "注册成功";
						}
					 }
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 登录
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Object login(HttpServletRequest request){
		logBefore(logger, "---登录--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("phone", "18289763525");
		pd.put("securityCode", "123456");
		pd.put("yanzhengma", "123456");
		
		String respCode = "00";
		String respMsg = "登录失败";
		try{
			//判断是否已经获取验证码
			if(Tools.isEmpty(pd.getString("securityCode"))){
				respMsg = "请先获取短信验证码";
			}else {
				if(Tools.isEmpty(pd.getString("phone")) || Tools.isEmpty(pd.getString("yanzhengma"))){
					respMsg = "手机号码或者验证码不能为空";
				}else{
					if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){
						respMsg = "验证码不正确";
					}else{
						//判断用户是否存在
						PageData pds = keHuService.getDataByPhone(pd);
						if(pds==null){
							respMsg = "该手机号还未注册";
						}else{
							respCode = "01";
							respMsg="登录成功！";
							//加密后的返回码，用于请求识别
							String backCode=MD5.md5(pd.getString("phone"));
							map.put("backCode", backCode);
							
							//每一次登录，都要修改这次登录时间为最后一次登录时间
							pd.put("last_login_time",DateUtil.getTime());
							pd.put("ip",request.getRemoteHost());
							keHuService.updateLoginTimeAndIp(pd);
							
							//根据返回码获取缓存信息(存在则更新、不存在则添加)
							pd.put("backCode", backCode);
							PageData cacheData = keHuService.getDataByBackCode(pd);      
							if(cacheData==null){
								//添加缓存
								PageData cachePD = new PageData();
								cachePD.put("backCode", backCode);
								cachePD.put("phone", pds.getString("phone"));
								cachePD.put("user_kehu_fid", pds.getString("user_kehu_id"));
								cachePD.put("cache_user_kehu_id", this.get32UUID());
								//将用户信息存进缓存表中
								keHuService.saveCacheData(cachePD);
							}else{
								//更新缓存
								PageData cachePD = new PageData();
								cachePD.put("backCode", backCode);
								cachePD.put("phone", pds.getString("phone"));
								cachePD.put("user_kehu_fid", pds.getString("user_kehu_id"));
								//将用户信息存进缓存表中
								keHuService.updateCacheData(cachePD);
							}
						}
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(new PageData(), map);
	}
	/**
	 * 退出，注销 清除用户登录信息缓存
	 * @throws Exception 
	 */
	@RequestMapping(value="/clearLoginInfo")
	@ResponseBody
	public Object clearLoginInfo(){
		logBefore(logger, "---清除用户登录信息--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");
		
		String respCode = "00";
		String respMsg = "清除失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="backCode不能为空";
			}else{
				keHuService.deleteCacheData(pd);
				respCode = "01";
				respMsg="清除成功";
			}
				
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////换绑手机号部分（star）
	/**
	 * 换绑手机号第一步：进入短信验证页面
	 * @return
	 */
	@RequestMapping(value="/changePhone1")
	@ResponseBody
	public Object changePhone1(){
		logBefore(logger, "---换绑手机号第一步：进入短信验证页面--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");//返回码
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					respCode = "01";
					respMsg="成功";
					map.put("phone", cacheData.getString("phone"));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 换绑手机号第二步：进行短信验证
	 * @return
	 */
	@RequestMapping(value="/changePhone2")
	@ResponseBody
	public Object changePhone2(){
		logBefore(logger, "---换绑手机号第二步：进行短信验证--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("securityCode", "123456");
		pd.put("yanzhengma", "123456");
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){ 
			 	respMsg = "验证码不正确";
			}else {
				respCode = "01";
				respMsg = "成功";
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 换绑手机号第三步：换绑新的手机号
	 * @return
	 */
	@RequestMapping(value="/changePhone3")
	@ResponseBody
	public Object changePhone3(){
		logBefore(logger, "---换绑手机号第三步：换绑新的手机号--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");//返回码
		pd.put("phone", "18808917736");
		pd.put("securityCode", "123456");
		pd.put("yanzhengma", "123456");
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){ 
					 	respMsg = "验证码不正确";
					}else {
						//更新用户的手机号信息
						pd.put("user_kehu_id", cacheData.getString("user_kehu_fid"));
						keHuService.updateUserData(pd);
						//更新缓存信息
						String backCode=MD5.md5(pd.getString("phone"));
						PageData tempPd=new PageData();
						tempPd.put("phone", pd.getString("phone"));
						tempPd.put("backCode", backCode);
						tempPd.put("cache_user_kehu_id", cacheData.getString("cache_user_kehu_id"));
						keHuService.updateCacheData(tempPd);
						respCode = "01";
						respMsg="成功";
						map.put("backCode", backCode);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////换绑手机号部分（end）
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////修改用户名部分（star）
	/**
	 * 修改用户名第一步：进入修改用户名页面
	 * @return
	 */
	@RequestMapping(value="/changeUserName1")
	@ResponseBody
	public Object changeUserName1(){
		logBefore(logger, "---修改用户名第一步：进入修改用户名页面--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");//返回码
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					//根据手机获取用户信息(这里获取的是用户名)
					PageData tempPd=keHuService.getDataByPhone(cacheData);
					map.put("userName", tempPd.getString("userName"));
					respCode = "01";
					respMsg="成功";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 修改用户名第二步：修改用户名
	 * @return
	 */
	@RequestMapping(value="/changeUserName2")
	@ResponseBody
	public Object changeUserName2(){
		logBefore(logger, "---修改用户名第二步：修改用户名--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");//返回码
		pd.put("userName", "张建华");
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					//判断该用户名是否存在
					PageData tempPd=keHuService.getDataByUserName(pd);
					if(tempPd!=null){
						respMsg="该用户名已存在,请重新选择用户名";
					}else {
						//更新用户名
						pd.put("user_kehu_id", cacheData.getString("user_kehu_fid"));
						keHuService.updateUserData(pd);
						respCode = "01";
						respMsg="成功";
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////修改用户名部分（end）
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////修改头像部分（star）
	/**
	 * 修改用户头像
	 * @return
	 */
	@RequestMapping(value="/changeHeadImg")
	@ResponseBody
	public Object changeHeadImg(@RequestParam(required=false) MultipartFile headImgFile,String backCode){
		logBefore(logger, "---修改用户头像--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(backCode)){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				if (null != headImgFile && !headImgFile.isEmpty()){
					//根据backCode获取缓存信息
					PageData cacheData=keHuService.getDataByBackCode(pd);
					if(cacheData==null){
						respMsg="缓存信息失效,请重新登录";
					}else{
						//获取当前用户的头像信息
						PageData tempPd=keHuService.getDataByPhone(cacheData);
						if(Tools.notEmpty(tempPd.getString("headImg"))){
							//删除已存在的图片
							File oldFile=new File(PathUtil.getClasspath()+tempPd.getString("headImg"));
							if(oldFile.exists()){
								oldFile.delete();
							}
						}
						//自定义头像的保存路径
						String  headImgfolder = Const.FILEPATHIMG + "kehu/touxiang/" + DateUtil.getDays() + "/";
						String filePath = PathUtil.getClasspath() + headImgfolder;	//文件上传路径
						//执行上传操作,获取文件名
						String headImgName = FileUpload.fileUp(headImgFile, filePath, this.get32UUID());
						//更新用户头像
						pd.put("headImg", headImgfolder + headImgName);
						pd.put("user_kehu_id", cacheData.getString("user_kehu_fid"));
						keHuService.updateUserData(pd);
						respCode = "01";
						respMsg="成功";
					}
				}else{
					respMsg="操作失败,图片文件不能为空";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////修改头像部分（end）
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////收货地址部分（star）
	/**
	 * 获取收货地址列表
	 * @throws Exception 
	 */
	@RequestMapping(value="/getListShouHuoAddress")
	@ResponseBody
	public Object getListShouHuoAddress(){
		logBefore(logger, "---获取收货地址列表--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		//pd.put("backCode", "d1d52e685369685c870983147fe98d48");
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					//获取指定用户的收货地址列表
					pd.put("user_kehu_fid", cacheData.getString("user_kehu_fid"));
					List<PageData> dataList = shouhuoAddressService.getListShouHuoAddress(pd);
					map.put("dataList", dataList);
					respCode = "01";
					respMsg="成功";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 新增收货地址
	 * @throws Exception 
	 */
	@RequestMapping(value="/addShouHuoAddress")
	@ResponseBody
	public Object addShouHuoAddress(){
		logBefore(logger, "---新增收货地址--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		/*
		pd.put("linkmanName", "张建华");
		pd.put("phone", "18289763525");
		pd.put("detailAddress", "海南省、海口市、龙华区、国贸路、景瑞大厦A座13楼C室");
		pd.put("identity", "先生");
		pd.put("lable", "公司");
		pd.put("isDefault", "1");
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");
		*/
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					pd.put("shouhuo_address_id", this.get32UUID());
					pd.put("user_kehu_fid", cacheData.getString("user_kehu_fid"));
					shouhuoAddressService.saveShouHuoAddress(pd);
					respCode = "01";
					respMsg="成功";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 跳转到编辑收货地址页
	 * @throws Exception 
	 */
	@RequestMapping(value="/toEditShouHuoAddress")
	@ResponseBody
	public Object toEditShouHuoAddress(){
		logBefore(logger, "---跳转到编辑收货地址页--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		/*
		pd.put("shouhuo_address_id", "856c00f8945d4d148df72a31842898e0");
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");
		*/
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					//获取指定的收货地址对象
					PageData pdData = shouhuoAddressService.toEditShouHuoAddress(pd);
					map.put("pdData", pdData);
					respCode = "01";
					respMsg="成功";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 更新收货地址
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateShouHuoAddress")
	@ResponseBody
	public Object updateShouHuoAddress(){
		logBefore(logger, "---更新收货地址--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		/*
		pd.put("linkmanName", "张建华");
		pd.put("phone", "18289763525");
		pd.put("detailAddress", "海南省、海口市、龙华区、国贸路、景瑞大厦A座13楼C室");
		pd.put("identity", "先生");
		pd.put("lable", "公司");
		pd.put("isDefault", "0");
		pd.put("shouhuo_address_id", "856c00f8945d4d148df72a31842898e0");//对象的主键ID
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");//返回码
		*/
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					shouhuoAddressService.updateShouHuoAddress(pd);
					respCode = "01";
					respMsg="成功";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 删除收货地址
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteShouHuoAddress")
	@ResponseBody
	public Object deleteShouHuoAddress(){
		logBefore(logger, "---删除收货地址--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//测试时,传递的参数,之后删除
		/*
		pd.put("shouhuo_address_id", "856c00f8945d4d148df72a31842898e0");//对象的主键ID
		pd.put("backCode", "d1d52e685369685c870983147fe98d48");//返回码
		*/
		
		String respCode = "00";
		String respMsg = "失败";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=keHuService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					shouhuoAddressService.deleteShouHuoAddress(pd);
					respCode = "01";
					respMsg="成功";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////收货地址部分（end）
}
