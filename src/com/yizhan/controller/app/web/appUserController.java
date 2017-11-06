package com.yizhan.controller.app.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.sun.net.httpserver.Authenticator.Success;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.weixin.model.Member;
import com.weixin.model.UserEntity;
import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.entity.system.AppUser;
import com.yizhan.entity.system.Cache;
import com.yizhan.entity.system.CacheManager;
import com.yizhan.entity.system.Memory;
import com.yizhan.entity.system.Proprietor;
import com.yizhan.entity.system.ThreadTokenHolder;
import com.yizhan.entity.system.User;
import com.yizhan.service.system.appuser.AppuserService;
import com.yizhan.service.system.pictures.PicturesService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.Const;
import com.yizhan.util.DateUtil;
import com.yizhan.util.Logger;
import com.yizhan.util.MD5;
import com.yizhan.util.PageData;
import com.yizhan.util.SmsUtil;
import com.yizhan.util.Tools;


/**
  * app用户-接口类 
  *    
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/api/appUser")
public class appUserController extends BaseController{
	@Resource(name="appuserService")
	private AppuserService appuserService;
	//验证码
	Map<String, Object> dataMap = new HashMap<String, Object>();
	
	/**
	 * 发送验证码
	 */
	@RequestMapping(value="/sendSecurityCode")
	@ResponseBody
	public Object sendSecurityCode(){
		logBefore(logger, "发送验证码");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		String securityCode = "";
		//电话号码
		String phone = pd.getString("phone");
		if(phone!=null && phone!=""){
			//发送验证码
			Map mapResult  = SmsUtil.sendMsM(phone);
			if(mapResult.get("result").equals("验证码发送成功")){
				String md5Phone = MD5.md5(phone);
				securityCode =  (String)mapResult.get("yanzhengma");
				//将电话加密作为建，验证码作为值存进缓存Map中,用于注册验证
				CacheManager.putCacheInfo(md5Phone, securityCode, 1);
				//请求成功信息
				result = "01";
				map.put("respCode", result);
				//注册请求识别码
				map.put("yanzhengma", securityCode);
				map.put("md5Phone", md5Phone);
				map.put("respMsg", "请求成功,yanzhengma是我返回给你的正确的验证码，存于你们本地，最后注册请求时将该码与用户填的验证码一起传来。");
			}else{
				//请求失败
				result = "00";
				map.put("result", result);
				map.put("respMsg", "失败");
			}
			
		}else{ 
				//请求失败
				result = "00";
				map.put("result", result);
				map.put("respMsg", "电话号码不能为空");
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 注册
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object register(HttpServletRequest request){
		logBefore(logger, "register");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData pda = new PageData();
		pd = this.getPageData();
		String result = "00";
		String msg = "";
		String yzm ="";
		System.out.println("------正确的验证码-----"+pd.getString("yanzhengma"));
		System.out.println("------用户输入的验证码-----"+pd.getString("securityCode"));
		System.out.println("------md5Phone-----"+pd.getString("md5Phone"));
		try{
			if(pd.get("phone")==null||pd.get("pwd")==null){
				result = "00";
				msg = "手机号码或者密码不能为空";
				map.put("respCode", result);
				map.put("respMsg", msg);
			}else if(pd.getString("md5Phone")==null && Tools.isEmpty(pd.getString("md5Phone"))){   
				map.put("respMsg", "md5Phone不能为空");	
			}else{
				//获取键
				//Cache cache = CacheManager.getCacheInfo(pd.getString("md5Phone"));	
				//得到缓存中的验证码
				//yzm = cache.getValue().toString();
				yzm = pd.getString("yanzhengma");
				//自定义超时返回规则
				 if(yzm.equals("abcd")){
						map.put("respMsg", "验证码超时");
				 }
				 else if(!pd.getString("securityCode").equals(yzm)){            
						msg = "验证码不正确";
						map.put("respMsg", msg);
				 }else{
					 System.out.println("------正确的验证码----"+yzm);
						System.out.println("-----电话-----"+pd.getString("phone"));
						System.out.println("-----密码----"+pd.getString("pwd"));
						String phone = pd.getString("phone");
						String pwd = pd.getString("pwd");
						//查询电话号码是否重复，即该用户是否注册过
						pda.put("phone", phone);
						PageData pds = this.appuserService.findByPhone(pda);
						if(pds!=null){
							result = "00";
							msg = "该手机号已经注册";
							map.put("respCode", result);
							map.put("respMsg", msg);
						}else{
							pd.put("USER_ID", this.get32UUID());	//主键
							pd.put("USERNAME", "");	
							pd.put("PASSWORD", MD5.md5(pwd));	
							pd.put("NAME", "");	
							pd.put("RIGHTS", "");	
							pd.put("ROLE_ID", 1);	
							pd.put("LAST_LOGIN_TIME", Tools.date2Str(new Date()));	
							pd.put("IP", request.getRemoteHost());	//IP
							pd.put("STATUS", 1); //1 使用中 0 已停用
							pd.put("BZ", "app用户");	
							pd.put("PHONE", phone);	
							pd.put("EMAIL", "");	
							pd.put("OPENID", "");	
							pd.put("SOURCE", "1");	
							pd.put("NICKNAME", "");	
							pd.put("CITY", "");	
							pd.put("PROVINCE", "");	
							pd.put("COUNTRY", "");	
							pd.put("HEADIMGURL", "");	
							pd.put("INTEGRAL", "");	
							pd.put("BALANCE", 0);	
							pd.put("SUBSCRIBE_TIME", "");	
							//保存app用户
							appuserService.saveU(pd);
							PageData nickPd = new PageData();
							nickPd.put("app_nk_id", this.get32UUID());
							nickPd.put("app_nickname", pda.get("phone"));
							nickPd.put("app_phone", pda.get("phone"));
							//保存该用户的昵称
							appuserService.saveAppNickName(nickPd);
							result = "01";
							msg = "注册成功";
							map.put("respCode", result);
							map.put("respMsg", msg);
						}
					 
				 }
				
			}
			
		}catch(Exception e){
				logger.error(e.toString(), e);
				result = "00";
				msg = "注册失败";
				map.put("respCode", result);
				map.put("respMsg", msg);
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 登录
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Object login(HttpServletRequest request){
		logBefore(logger, "login");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData pda = new PageData();
		String result = "00";
		String msg = "";
		String mobile ="";
		String password="";
		String backCode= "";
		String md5pwd ="";
		try{
			pd = this.getPageData();
			//接收参数================================
			if(pd.get("phone")==null||pd.get("pwd")==null){
				result = "00";
				msg = "手机号码或者密码不能为空";
				map.put("respCode", result);
				map.put("respMsg", msg);
			}else{
				 mobile = pd.getString("phone");
				 password = pd.getString("pwd");
				 md5pwd = MD5.md5(password); //密码加密
				
				pda.put("PHONE", mobile);
				pda.put("PASSWORD", md5pwd);
				//根据手机号码和密码判断登录，查到有该用户则登录成功
				PageData pds= this.appuserService.getUserByNameAndPwd(pda);
				
				//每一次登录，都要修改这次登录时间为最后一次登录时间
				pd.put("LAST_LOGIN_TIME",DateUtil.getTime().toString());
				appuserService.updateLastLogin(pd);
				
				if(pds==null){
					result = "02";
					msg = "登录失败，手机号码或密码不正确";
					map.put("respCode", result);
					map.put("respMsg", msg);
				}else{
					String isComplete = new String("0");
					//查该用户是否完善资料
					List<PageData> pds2 = appuserService.getCompleteDataByPhone(pda);
					if(pds2!=null){
						isComplete="1";
						//是否完善资料标识
						map.put("isComplete_id", isComplete);
						map.put("isComplete_msg", "用户已完善资料");
					}else{
						map.put("isComplete_id", isComplete);
						map.put("isComplete_msg", "该用户未完善资料");
					}
					//加密后的返回码，用于请求识别
					backCode = MD5.md5(mobile);
					result = "01";
					msg="登录成功！";
					map.put("respCode", result);
					map.put("backCode", backCode);
					map.put("respMsg", msg);
					map.put("phone", mobile);
					//获取用户信息
					PageData pdData = appuserService.getProprietorByPhone(pda);
					PageData putData  = new PageData();
					putData.put("backCode", backCode);
					
					//根据用户名和密码获取用户缓存信息
					PageData resultData = appuserService.getCacheInfoByPhoneAndPwd(pda);          
					if(resultData!=null){
						PageData cachePD = new PageData();
						if(Tools.isEmpty(resultData.getString("proprietor_id"))&& 
								Tools.isEmpty(resultData.getString("residence_id")) &&
								Tools.isEmpty(resultData.getString("proprietor_name"))){
							cachePD.put("proprietor_id", pdData.get("proprietorId"));
							cachePD.put("residence_id", pdData.get("residence_id"));
							cachePD.put("proprietor_name", pdData.get("proprietor_name"));
							cachePD.put("phone", pda.get("PHONE"));
							cachePD.put("password", pda.get("PASSWORD"));
							//修改用户缓存信息
						//	appuserService.updateCacheInfo(cachePD);
						}
						
						System.out.println("--------登录时，验证查询用户缓存数据，用户信息存在！！---------");
						System.out.println("--------登录时，验证查询用户缓存数据，用户信息存在！！---------");
					}else{
						//添加缓存
						PageData cachePD = new PageData();
						cachePD.put("cache_id", this.get32UUID());
						cachePD.put("phone", mobile);
						cachePD.put("passWord", md5pwd);
						cachePD.put("backCode", backCode);
						cachePD.put("residence_id", pdData.getString("residence"));
						cachePD.put("proprietor_name", pdData.getString("proprietorName"));
						cachePD.put("proprietor_id", pdData.getString("proprietorId"));
						//将用户信息存进缓存表中
						appuserService.putCacheInfo(cachePD);
					}
			
				}
			}

		} catch(Exception e){
			logger.error(e.toString(), e);
			result = "00";
			msg = "登录失败";
			map.put("respCode", result);
			map.put("respMsg", msg);
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	
	
	/**
	 * 修改密码  忘记密码同一个接口
	 */
	@RequestMapping(value="/changepwd")
	@ResponseBody
	public Object changepwd(HttpServletRequest request){
		logBefore(logger, "changepwd");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData pda = new PageData();
		String result = "0";
		String msg = "";
		String yzm = "";
		//Subject currentSession = SecurityUtils.getSubject();  
		//Session session = currentSession.getSession();
		//System.out.println("-------------本地验证码-------------"+session.getAttribute(Const.SESSION_USER_SECURITY_CODE));    
		try{
			pd = this.getPageData();
			pda = this.getPageData();
			yzm = pd.getString("yanzhengma");
			//自定义超时返回规则
			 if(yzm.equals("abcd")){
				map.put("respMsg", "验证码超时");
			 }else if(pd.get("phone")==null||pd.get("pwd")==null){
				result = "00";
				msg = "手机号码或者密码不能为空";
				map.put("respCode", result);
				map.put("respMsg", msg);
			}else if(!pd.get("securityCode").equals(yzm)){
				msg = "验证码不正确";
				map.put("respMsg", msg);
			}else{
				String phone = pd.getString("phone");
				String pwd = pd.getString("pwd");
				//根据电话号码查询该用户是否存在
				pda.put("phone", phone);
				PageData pds = this.appuserService.findByPhone(pda);
				if(pds!=null){
					pd.put("PASSWORD", MD5.md5(pwd));	//密码
					pd.put("PHONE", phone);	//手机号
					//修改密码
					appuserService.updatePwd(pd);
					result = "01";
					msg="修改成功";
					map.put("respCode", result);
					map.put("respMsg", msg);
				}else{
					result = "00";
					msg = "该手机号暂未注册";
					map.put("respCode", result);
					map.put("respMsg", msg);
				}
			}
			
			
		} catch(Exception e){
			logger.error(e.toString(), e);
			result = "0";
			msg = e.getMessage();
			map.put("respCode", result);
			map.put("respMsg", msg);
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * app用户完善资料
	 * @throws Exception 
	 */
	@RequestMapping(value="/CompleteData")
	@ResponseBody
	public Object CompleteData(Page page,HttpServletRequest request){
		logBefore(logger, "---完善资料--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		int proprietorList = 0;
		String result = "00";
		try {
			String currentTime = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String currentYear = new  SimpleDateFormat("yyyy").format(new Date());
			//生成主键id
			pd.put("proprietorId", this.get32UUID());	//ID
			pd.put("age", Integer.parseInt(currentYear)-Integer.parseInt(pd.getString("birthday").substring(0, 4)));	//ID
			pd.put("checkInTime", currentTime);	//插入时间
			//proprietorList = proprietorService.saveProprietor(pd);
			if(proprietorList==1){
				result = "01";
				map.put("respCode", result);
				map.put("msg", "保存成功");
			}
			
		}catch (Exception e){
				result = "00";
				e.printStackTrace();
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("msg", "保存失败，请求协议参数不完整");
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 退出，注销 清除用户登录信息缓存
	 * @throws Exception 
	 */
	@RequestMapping(value="/clearLoginInfo")
	@ResponseBody
	public Object clearLoginInfo(Page page,HttpServletRequest request){
		logBefore(logger, "---清除用户登录信息--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				map.put("msg", "backCode不能为空");
			}else{
				appuserService.deleteCacheInfo(pd);
				result = "01";
				map.put("respCode", result);
				map.put("msg", "清除成功");
			}
				
		}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("msg", "清除失败，请求协议参数不完整");
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	
}
