package com.yizhan.controller.app.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.entity.system.CacheUserSiji;
import com.yizhan.service.information.h5sys.h5sysService;
import com.yizhan.service.information.keHu.KeHuService;
import com.yizhan.service.information.orderChangTu.OrderChangtuService;
import com.yizhan.service.information.orderTongCheng.OrderTongchengService;
import com.yizhan.service.information.siJi.SijiService;
import com.yizhan.service.information.sijiInformationChangTu.SijiInformationChangTuService;
import com.yizhan.service.information.sijiInformationTongCheng.SijiInformationTongChengService;
import com.yizhan.service.information.sijiOrderTongCheng.SijiOrderTongChengService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.Const;
import com.yizhan.util.DateUtil;
import com.yizhan.util.FileUpload;
import com.yizhan.util.FormateUtil;
import com.yizhan.util.MD5;
import com.yizhan.util.PageData;
import com.yizhan.util.PathUtil;
import com.yizhan.util.SmsUtil;
import com.yizhan.util.Tools;


/**
  * appSiji用户-接口类 
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
@RequestMapping(value="/api/siji")
public class appSijiController extends BaseController{
	
	@Resource(name="sijiUserService")
	private SijiService sijiUserService;

	@Resource(name="sijiInformationTongChengService")
	private SijiInformationTongChengService sijiInformationTongChengService;
	
	@Resource(name="sijiInformationChangTuService")
	private SijiInformationChangTuService sijiInformationChangTuService;
	
	@Resource(name="sijiOrderTongChengService")
	private SijiOrderTongChengService sijiOrderTongChengService;
	
	@Resource(name="orderChangtuService")
	private OrderChangtuService orderChangtuService;
	
	@Resource(name="orderTongchengService")
	private OrderTongchengService orderTongchengService;
	
	@Resource(name="keHuService")
	private KeHuService keHuService;
	
	@Resource(name="h5sysService")
	private h5sysService h5sysService;
	/**
	 * 判断用户登录状态的缓存是否还存在
	 * @throws Exception 
	 */
	@RequestMapping(value="/judgeAgainDeploy")
	@ResponseBody
	public Object judgeAgainDeploy(Page page) throws Exception{
		logBefore(logger, "--司机判断重新部署--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		System.out.println("----------backCode-------"+pd.getString("backCode"));
		try{
			if(Tools.isEmpty(pd.getString("backCode"))||pd.getString("backCode")==null){
				map.put("loginStatuCode", "00");
				map.put("msg", "backCode不能为空");
			}else{
				CacheUserSiji relustPD = sijiUserService.getCacheInfo(pd);//根据backCode获取缓存信息
				if(relustPD==null){
					map.put("loginStatuCode", "00");
					map.put("msg", "缓存信息失效，请重新登录");
				}else{
					map.put("loginStatuCode", "01");
					map.put("msg", "缓存信息有效");
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("msg", "未知错误，请联系管理员");
			map.put("msg", e.getMessage());
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 发送验证码
	 */
	@RequestMapping(value="/sendSecurityCode")
	@ResponseBody
	public Object sendSecurityCode(){
		logBefore(logger, "---司机短信发送验证码---");
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
				//请求成功信息
				result = "01";
				map.put("respCode", result);
				//注册请求识别码
				map.put("md5Phone", md5Phone);
				map.put("securityCode", securityCode);
				map.put("respMsg", "验证码发送成功");
			}else{
				//请求失败
				result = "00";
				map.put("result", result);
				map.put("respMsg", "验证码发送失败");
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
	 * 司机app注册接口
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object register(HttpServletRequest request){
		logBefore(logger, "---司机用户注册---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData pda = new PageData();
		pd = this.getPageData();
		String result = "00";
		String msg = "";
		//用户输入的验证码
		String yhsr = pd.getString("yhsr");
		
		System.out.println("------正确的验证码-----"+pd.getString("securityCode"));
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
			    if(!pd.getString("securityCode").equals(yhsr)){
			    		result = "00";
						msg = "验证码不正确";
						map.put("respMsg", msg);
				 }else{
						String phone = pd.getString("phone");
						String pwd = pd.getString("pwd");
						//查询电话号码是否重复，即该用户是否注册过
						pda.put("phone", phone);
						PageData pds = this.sijiUserService.findByPhone(pda);
						if(pds!=null){
							result = "00";
							msg = "该手机号已经注册";
							map.put("respCode", result);
							map.put("respMsg", msg);
							
						}else{
							pd.put("user_siji_id", this.get32UUID());	//主键
							pd.put("userName", "");	
							pd.put("loginPassword", MD5.md5(pwd));	
							pd.put("headImg", "");	
							pd.put("last_login_time", Tools.date2Str(new Date()));	
							pd.put("ip", request.getRemoteHost());	//IP
							pd.put("status", 1); //1 使用中 0 已停用
							pd.put("tixianstatus", 0);	
							pd.put("bz", "司机app用户");	
							pd.put("phone", phone);	
							//保存app用户
							sijiUserService.saveU(pd);
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
	 * 司机端登录接口
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Object login(HttpServletRequest request){
		logBefore(logger, "-----司机端登录接口-----");
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
				
				pda.put("phone", mobile);
				pda.put("loginPassword", md5pwd);
				//根据手机号码和密码判断登录，查到有该用户则登录成功
				PageData pds= this.sijiUserService.getUserByNameAndPwd(pda);
				
				//每一次登录，都要修改这次登录时间为最后一次登录时间
				pd.put("last_login_time",DateUtil.getTime().toString());
				sijiUserService.updateLastLogin(pd);
				
				if(pds==null){
					result = "02";
					msg = "登录失败，手机号码或密码不正确";
					map.put("respCode", result);
					map.put("respMsg", msg);
				}else{
					String isComplete = new String("0");
					//查该司机用户是否完善资料
					PageData pdsi = sijiUserService.getCompleteDataByPhone(pda);
					if(pdsi!=null){
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
					//根据电话号码查出该用户的所有信息
					PageData pdData = sijiUserService.getUserSijiByPhone(pda);
					PageData putData  = new PageData();
					putData.put("backCode", backCode);
					pda.put("passWord", pdData.getString("loginPassword"));
					//根据用户名和密码获取用户缓存信息
					PageData resultData = sijiUserService.getCacheInfoByPhoneAndPwd(pda);          
					if(resultData!=null){
						System.out.println("--------登录时，验证查询用户缓存数据，用户信息存在！！---------");
						System.out.println("--------登录时，验证查询用户缓存数据，用户信息存在！！---------");
					}else{
						
						//添加缓存
						PageData cachePD = new PageData();
						cachePD.put("cache_id", this.get32UUID());
						cachePD.put("phone", mobile);
						cachePD.put("passWord", md5pwd);
						cachePD.put("backCode", backCode);
						cachePD.put("user_siji_fid", pdData.getString("user_siji_id"));
						cachePD.put("create_time", Tools.date2Str(new Date()));
						//将用户信息存进缓存表中
						sijiUserService.putCacheInfo(cachePD);
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
	 * 司机端换绑手机号
	 * @return
	 */
	@RequestMapping(value="/changePhone")
	@ResponseBody
	public Object changePhone(){
		logBefore(logger, "---司机端换绑手机号--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String phone = pd.getString("phone");
		String result = "";
		String respMsg = "";
		String securityCode = "";
		try {
			if (phone!=null && phone != "") {
				//查询电话号码是否重复，即该用户是否注册过
				PageData pds = this.sijiUserService.findByPhone(pd);
				if (pds!=null) {
					Map mapResult  = SmsUtil.sendMsM(phone);//发送验证码
					if(mapResult.get("result").equals("验证码发送成功")){
						securityCode =  (String)mapResult.get("yanzhengma");
						//请求成功信息
						result = "01";
						map.put("result", result);
						//注册请求识别码
						map.put("securityCode", securityCode);
						respMsg = "验证码发送成功";
					}else{
						//请求失败
						result = "00";
						map.put("result", result);
						respMsg = "验证码发送失败";
					}
				}else{
					result = "00";
					respMsg = "该用户还未注册,请注册！";
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 司机换绑手机号：进行“获取短信验证码”和“输入验证码”的验证
	 * @return
	 */
	@RequestMapping(value="/Verification")
	@ResponseBody
	public Object Verification(){
		logBefore(logger, "---司机换绑手机号：进行“获取短信验证码”和“输入验证码”的验证--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respMsg = "";
		String result = "";
		String securityCode = "";
		//用户输入的验证码
		String yanzhengma = pd.getString("yanzhengma");
		System.out.println("------正确的验证码-----"+pd.getString("securityCode"));
		try {
			if(Tools.isEmpty(pd.getString("phone")) || Tools.isEmpty(pd.getString("securityCode")) || Tools.isEmpty(yanzhengma)){
				result = "00";
				respMsg = "手机号码或者验证不能为空！";
			}else{
			    if(!pd.getString("securityCode").equals(yanzhengma)){
			    	result = "00";
			    	respMsg = "验证码不正确！";
				 }else{
						//查询电话号码是否重复，即该用户是否注册过
//						PageData pds = this.sijiUserService.findByPhone(pd);
//						if (pds!=null) {
//					 			result = "01";
//								respMsg = "成功";
//							}else{
//								respCode = "00";
//								respMsg = "该用户还未注册,请注册！";
//							}
								
						Map mapResult  = SmsUtil.sendMsM(pd.getString("phone"));//发送验证码
						if(mapResult.get("result").equals("验证码发送成功")){
							securityCode =  (String)mapResult.get("yanzhengma");
							//请求成功信息
							result = "01";
							map.put("result", result);
							//注册请求识别码
							map.put("securityCode", securityCode);
							respMsg = "验证码发送成功";
						}else{
							//请求失败
							result = "00";
							map.put("result", result);
							respMsg = "验证码发送失败";
						}
				 	}
				 }

		}catch (Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 换绑新的手机号
	 * @return
	 */
	@RequestMapping(value="/changingTiesPhone")
	@ResponseBody
	public Object changingTiesPhone(){
		logBefore(logger, "---换绑新的手机号--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respCode = "";
		String respMsg = "";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))||
					Tools.isEmpty(pd.getString("phone"))||
					Tools.isEmpty(pd.getString("securityCode"))||
					Tools.isEmpty(pd.getString("yanzhengma"))){
				respCode = "00";
				respMsg="请求参数与backCode不能为空！";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if (cacheData == null) {
					respCode = "00";
					respMsg="backCode出错！";
				}else
				if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){ 
					respCode = "00";
					respMsg = "验证码不正确";
				}else {
					//查询电话号码是否重复，即该用户是否注册过
					PageData pds = this.sijiUserService.findByPhone(pd);
					if(pds!=null){
						respCode="00";
						respMsg="换绑失败,该手机号已注册!";
					}else{
						//换绑新的手机号
						pd.put("user_siji_id", cacheData.getString("user_siji_fid"));
						sijiUserService.updateUserphone(pd);
						sijiUserService.deleteCacheInfo(pd);//清除缓存信息
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

	
	/**
	 * 忘记密码的发送验证码验证用户是否存在
	 * @return
	 */
	@RequestMapping(value="/forgetPassword")
	@ResponseBody
	public Object forgetPassword(){
		logBefore(logger, "---忘记密码的发送验证码验证用户是否存在---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respCode = "";
		String respMsg = "";
		String securityCode = "";
		try {
			if(Tools.isEmpty(pd.getString("phone"))){
				respCode = "00";
				respMsg="请求参数phone不能为空！";
			}else{
					//查询电话号码是否重复，即该用户是否注册过
					PageData pds = this.sijiUserService.findByPhone(pd);
					if(pds!=null){
						//发送验证码
						Map mapResult  = SmsUtil.sendMsM(pd.getString("phone"));
						if(mapResult.get("result").equals("验证码发送成功")){
							securityCode =  (String)mapResult.get("yanzhengma");
							//请求成功信息
							respCode = "01";
							map.put("respCode", respCode);
							//注册请求识别码
							map.put("securityCode", securityCode);
							respMsg = "验证码发送成功";
						}else{
							//请求失败
							respCode = "00";
							map.put("result", respCode);
							respMsg = "验证码发送失败";
						}
					}else {
						respCode = "00";
						respMsg = "该用户还未注册,请注册！";
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
	 * 忘记密码的参数验证
	 * @return
	 */
	@RequestMapping(value="/forgetPasswords")
	@ResponseBody
	public Object forgetPasswords(){
		logBefore(logger, "---忘记密码的参数验证---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respCode = "";
		String respMsg = "";
		try {
			 if (Tools.isEmpty(pd.getString("phone"))||
						Tools.isEmpty(pd.getString("securityCode"))||
						Tools.isEmpty(pd.getString("yanzhengma"))) {
					respCode = "00";
					respMsg="请求参数手机号码与验证码不能为空！";
				} else{
						//查询电话号码是否重复，即该用户是否注册过
						PageData pds = this.sijiUserService.findByPhone(pd);
						if(pds!=null){
							if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){
								respCode = "00";
								respMsg = "验证码不正确";
							}else {
								respCode = "01";
								respMsg = "验证码成功！";
							}
						}else {
							respCode = "00";
							respMsg = "该用户还未注册,请注册！";
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
	 * 修改密码 
	 */
	@RequestMapping(value="/changepwd")
	@ResponseBody
	public Object changepwd(HttpServletRequest request){
		logBefore(logger, "----修改密码接口-----");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData pda = new PageData();
		String result = "";
		String respMsg = "";
		pd = this.getPageData();
		try{
			String phone = pd.getString("phone");
			String pwd = pd.getString("pwd");
			pda.put("phone", phone);	//手机号
			PageData pds = this.sijiUserService.findByPhone(pda);//查询判断用户是否存在
				if (Tools.isEmpty(phone)||Tools.isEmpty(pwd)) {
					result = "00";
					respMsg="手机号码或者密码不能为空";
				}else if(pds!=null){
					pd.put("loginPassword", MD5.md5(pwd));	//密码
					sijiUserService.updatePwd(pd);//修改密码
					result = "01";
					respMsg="密码修改成功";
				}else{
					result = "00";
					respMsg = "该手机号暂未注册";
				}
		} catch(Exception e){
			logger.error(e.toString(), e);
			result = "00";
			respMsg = e.getMessage();
			map.put("result", result);
			map.put("respMsg", respMsg);
		}
		map.put("result", result);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 添加车辆信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/insertVehicleInfo")
	@ResponseBody
	public Object updateVehicleInfo(){
		logBefore(logger, "---添加车辆信息---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respMsg = "";
		String result = "00";
		String city = pd.getString("city");
		String carType = pd.getString("carType");
		String carNumber = pd.getString("carNumber");
		String carColor = pd.getString("carColor");
		
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				respMsg="backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheUserSiji = sijiUserService.getDataByBackCode(pd);
				if(cacheUserSiji==null){
					result = "00";
					respMsg="缓存信息失效,请重新登录";
				}else{
					if(Tools.isEmpty(city)||Tools.isEmpty(carType)|| Tools.isEmpty(carNumber)|| Tools.isEmpty(carColor)){
							result = "00";
							respMsg="请求参数不能为空！";
					}else{
							pd.put("user_siji_id", cacheUserSiji.getString("user_siji_fid"));
							pd.put("city", city);//城市
							pd.put("carType", carType);//车型
							pd.put("carNumber", carNumber);//车牌
							pd.put("carColor", carColor);//车颜色
							pd.put("authenticationState", 2);//认证状态(0-认证失败，1-认证通过，2-待认证)
							sijiUserService.updateVehicleInfo(pd);
							result = "01";
							respMsg="成功";
						}
				}
			}
		}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
				respMsg="失败";
		}
		map.put("result", result);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 司机app用户完善资料
	 * @throws Exception 
	 */
	@RequestMapping(value="/CompleteData")
	@ResponseBody
	public Object CompleteData(HttpServletRequest request,
			@RequestParam(value="realName",required=false) String realName,
			@RequestParam(value="phone",required=false) String phone,
			@RequestParam(value="identityCard",required=false) String identityCard,
			@RequestParam(value="identityFrontImg",required=false) MultipartFile identityFrontImg,
			@RequestParam(value="identityReverseImg",required=false) MultipartFile identityReverseImg,
			@RequestParam(value="drivingLicenceImg",required=false) MultipartFile drivingLicenceImg,
			@RequestParam(value="carIdentityImg",required=false) MultipartFile carIdentityImg){
		logBefore(logger, "---司机app用户完善资料---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String perfect = "perfect/";
		String result = "00";
		String respMsg = "";
		String file = DateUtil.getDays(), fileName1 = "",fileName2 = "",fileName3 = "",fileName4 = "";
		String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + perfect;//文件上传路径
		
		//图片上传
		try {
			
			if(Tools.isEmpty(request.getParameter("backCode"))){
				map.put("respCode", "00");
				map.put("respMsg", "操作失败,backCode不能为空");
			}else{
					pd.put("backCode", request.getParameter("backCode"));
					//根据backCode获取缓存信息
					PageData cacheUserSiji = sijiUserService.getDataByBackCode(pd);
					if(cacheUserSiji==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}else{
						if(identityFrontImg!=null &&
								identityReverseImg!=null &&
									drivingLicenceImg!=null &&
										carIdentityImg!=null){
							fileName1 = FileUpload.fileUp(identityFrontImg, filePath, this.get32UUID());
							fileName2 = FileUpload.fileUp(identityReverseImg, filePath, this.get32UUID());	
							fileName3 = FileUpload.fileUp(drivingLicenceImg, filePath, this.get32UUID());	
							fileName4 = FileUpload.fileUp(carIdentityImg, filePath, this.get32UUID());
							pd.put("realName", realName);//车主真是姓名
							pd.put("userName", realName.substring(0, 1)+"师傅");//车主用户名
							pd.put("phone", phone);//电话号码
							pd.put("identityCard", identityCard);//身份证号码
							pd.put("authenticationState", 2);//认证状态(0-认证失败，1-认证通过，2-待认证)
							pd.put("submitTime", DateUtil.getTime());//认证提交时间
							pd.put("identityFrontImg", Const.FILEPATHIMG + perfect+ fileName1);//执行身份证正面照上传
							pd.put("identityReverseImg", Const.FILEPATHIMG + perfect+ fileName2);//执行身份证背面照上传
							pd.put("drivingLicenceImg", Const.FILEPATHIMG + perfect+ fileName3);//执行驾驶证正面照上传
							pd.put("carIdentityImg", Const.FILEPATHIMG + perfect+ fileName4);//执行行驶证正面照(车辆的身份证)上传
							//上传身份证正反面、驾驶证、行驶证、图片
							sijiUserService.updateCompleteSiji(pd);
							result="01";
							map.put("respCode", result);
							map.put("respMsg", "图片上传成功");
						}else{
							map.put("respMsg", "图片文件不能为空");
						}
					
				}
			}
		}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
				result = "00";
				map.put("respCode", result);
				map.put("respMsg", "请求失败，程序出现异常，请联系管理人员");
		}
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 上传用户头像和修改头像同一接口
	 * @throws Exception 
	 */
	@RequestMapping(value="/saveAppImage")
	@ResponseBody
	public Object saveAppImage(HttpServletRequest request,
			@RequestParam(value="imgFile",required=false) MultipartFile imgFile) throws Exception{
		logBefore(logger, "---上传用户头像和修改头像同一接口--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String appHead = "appHead/";
		String result = "00";
		//图片上传
		String  file = DateUtil.getDays(), fileName = "";
		if( request.getParameter("backCode")==null || request.getParameter("backCode").equals("")){
			map.put("respCode", result);
			map.put("respMsg", "请求参数backCode不能为空");
		}else if(imgFile==null){
			map.put("respCode", result);
			map.put("respMsg", "请求参数imgFile不能为空");
		}else{
			pd.put("backCode", request.getParameter("backCode"));
			CacheUserSiji cacheUserSiji = sijiUserService.getCacheInfo(pd);//根据backCode获取缓存信息
			pd.put("phone", cacheUserSiji.getPhone());
			if (null != imgFile && !imgFile.isEmpty()){
					//头像图片上传
					String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + appHead;	//文件上传路径
					fileName = FileUpload.fileUp(imgFile, filePath, this.get32UUID());	
					pd.put("headImg", Const.FILEPATHIMG + appHead + fileName);//执行上传
					//修改头像图片
					sijiUserService.updateAppHeadImage(pd);
					result="01";
					map.put("respCode", result);
					map.put("respMsg", "头像图片修改成功");
				
			}else{
				logBefore(logger, "保存失败");
				map.put("respCode", result);
				map.put("respMsg", "图片文件为空，上传失败");
			}
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
				sijiUserService.deleteCacheInfo(pd);//删除缓存信息  司机端app注销的用户
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
	
	/**
	 * 修改用户名
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateAppNickName")
	@ResponseBody
	public Object updateAppNickName(Page page,HttpServletRequest request) throws Exception{
		logBefore(logger, "---修改用户名昵称--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String respMsg = "";
		String phone = pd.get("phone") != null ? pd.get("phone").toString() : "";
		String userName = pd.get("userName") != null ? pd.get("userName").toString() : "";
		if(Tools.isEmpty(pd.getString("backCode"))){
			result = "00";
			respMsg="操作失败，请先登录，backCode不能为空";
		}else{
			//根据backCode获取缓存信息
			PageData cacheData=sijiUserService.getDataByBackCode(pd);
			if(cacheData==null){
				result = "00";
				respMsg="缓存信息失效,请重新登录";
			}else{
				if (Tools.isEmpty(phone) || Tools.isEmpty(userName)) {
					result = "00";
					respMsg = "请求参数不能为空!";
				}else{
					PageData pds = this.sijiUserService.findByPhone(pd);//查询判断用户是否存在
					if(pds!=null){
						PageData isUserName = sijiUserService.queryByUserName(pd);//查询判断用户名是否已被占用 
						if (isUserName!=null) {
								result = "02";
								respMsg="用户名已被占用！";
							}else{
									result = "01";
									respMsg = "修改成功";
									pd.put("phone", cacheData.getString("phone"));	//手机号
									pd.put("userName", userName);
									sijiUserService.updateAppNickName(pd);
								}
									
							}else{
								result = "00";
								respMsg = "该手机号暂未注册!";
							}
				}	
			
		}
	}
		map.put("result", result);
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 司机发布长途路线信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/insertSijiInformationChangTu")
	@ResponseBody
	public Object insertSijiInformationChangTu(HttpServletRequest request) throws Exception{
		logBefore(logger, "---司机长途发布路线信息--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "";
		String respMsg = "";
		String userName = pd.getString("userName");//车主姓名
		String phone = pd.getString("phone");
		String departureTime = pd.getString("departureTime");
		String departurePlace = pd.getString("departurePlace");
		String destination = pd.getString("destination");
		String carpoolFee = pd.getString("carpoolFee");
		String userNum = pd.getString("userNum");
		String departureCity = pd.getString("departureCity");
		String arrivalCity = pd.getString("arrivalCity");
		
		if (userName!=null && 
				phone!=null && 
					departureTime!=null &&
						departurePlace !=null && 
							destination !=null &&
								carpoolFee !=null &&
									userNum !=null &&
										departureCity !=null &&
											arrivalCity !=null) {
			
			pd.put("backCode", request.getParameter("backCode"));
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
				}else{
					pd.put("information_changtu_id", this.get32UUID());
					pd.put("releaseTime",DateUtil.getTime());//发布时间
					pd.put("userName",userName);//车主姓名
					pd.put("phone",phone);//手机号码
					pd.put("departureTime", departureTime);//出发时间	
//					pd.put("departureTime", DateUtil.getTime());//出发时间	
					pd.put("userNum",userNum);//可乘人数
					pd.put("departureCity",departureCity);//出发城市
					pd.put("departurePlace",departurePlace);//出发点
					pd.put("arrivalCity",arrivalCity);//目的城市
					pd.put("destination",destination);//目的地
					pd.put("carpoolFee",carpoolFee);//拼车费
					pd.put("roleMark", 2); //发布信息人的身份(1-客户发布，2-司机发布)
					pd.put("user_fid", cacheData.getString("user_siji_fid"));
					sijiInformationChangTuService.insertSijiInformationChangTu(pd);
					result="01";
					respMsg="保存成功";
					}
				}
			
		}else{
			result="00";
			respMsg="保存失败，请求协议或参数不完整";
		}
		map.put("respCode", result);
		map.put("respMsg", respMsg);	
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 获取司机端长途打车我的发布列表信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryInformationChangTu")
	@ResponseBody
	public Object queryInformationChangTu(Page page)throws Exception{
		logBefore(logger, "---获取司机端长途打车我的发布列表信息--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}else{
						pd.put("user_fid", cacheData.getString("user_siji_fid"));
						List<PageData> ChangTuList = sijiInformationChangTuService.querySijiInformationChangTuList(pd);
						List<PageData>  resultList = new ArrayList<PageData>();
						if (ChangTuList.isEmpty()) {
							result = "00";
							map.put("result", result);
							msg="无数据加载！";
						}else{
							for(int i=0;i<ChangTuList.size();i++){
								PageData ChangTu = new PageData();
								ChangTu.put("information_changtu_id",ChangTuList.get(i).get("information_changtu_id"));//长途打车列表信息id
								ChangTu.put("userName",ChangTuList.get(i).get("userName"));//姓名(乘客姓名或车主姓名)
								ChangTu.put("phone",ChangTuList.get(i).get("phone"));//联系电话
								ChangTu.put("departureTime",ChangTuList.get(i).get("departureTime"));//出发时间
								ChangTu.put("departurePlace",ChangTuList.get(i).get("departurePlace"));//出发地
								ChangTu.put("destination",ChangTuList.get(i).get("destination"));//目的地
								ChangTu.put("carpoolFee",ChangTuList.get(i).get("carpoolFee"));//拼车费用
								ChangTu.put("userNum",ChangTuList.get(i).get("userNum"));//剩余可乘人数
								ChangTu.put("departureCity",ChangTuList.get(i).get("departureCity"));//出发城市
								ChangTu.put("arrivalCity",ChangTuList.get(i).get("arrivalCity"));//到达城市
								ChangTu.put("releaseTime",ChangTuList.get(i).get("releaseTime"));//发布时间
								ChangTu.put("user_fid",ChangTuList.get(i).get("user_fid"));//外键ID(客户端用户的主键ID，司机端用户的主键ID)
								//把最新的结果放入到List中
								resultList.add(ChangTu);
							}
							result = "01";
							map.put("result", result);
							map.put("resultList", resultList);
							msg="成功";
						}
					}
				}
		}catch (Exception e){
			logger.error(e.toString(), e);
			result = "00";
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 我的发布详情
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryInformationChangTuID")
	@ResponseBody
	public Object queryInformationChangTuID(HttpServletRequest request)throws Exception{
		logBefore(logger, "---我的发布详情---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("information_changtu_id"))) {
						result = "00";
						map.put("result", result);
						msg="请求参数information_changtu_id（我的发布列表信息id）不能为空";
					}else{
							PageData PostingDetails = sijiInformationChangTuService.queryInformationChangTuID(pd);//我的发布详情
							result = "01";
							map.put("result", result);
							map.put("resultList", PostingDetails);
							msg="成功";
					}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 查看客户端发布的长途路线列表
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryInformationChangTukehu")
	@ResponseBody
	public Object queryInformationChangTukehu(Page page)throws Exception{
		logBefore(logger, "---查看客户端发布的长途路线列表--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "";
		
		try {
			List<PageData> ChangTuList = sijiInformationChangTuService.queryKehuInformationChangTuPage(pd);
			List<PageData>  resultList = new ArrayList<PageData>();
			for(int i=0;i<ChangTuList.size();i++){
				PageData ChangTu = new PageData();
				ChangTu.put("information_kehu_changtu_id",ChangTuList.get(i).get("information_kehu_changtu_id"));//长途打车列表信息id
				ChangTu.put("userName",ChangTuList.get(i).get("userName"));//姓名(乘客姓名或车主姓名)
				ChangTu.put("phone",ChangTuList.get(i).get("phone"));//联系电话
				ChangTu.put("departureTime",ChangTuList.get(i).get("departureTime"));//出发时间
				ChangTu.put("departurePlace",ChangTuList.get(i).get("departurePlace"));//出发地
				ChangTu.put("destination",ChangTuList.get(i).get("destination"));//目的地
				ChangTu.put("carpoolFee",ChangTuList.get(i).get("carpoolFee"));//拼车费用
				ChangTu.put("userNum",ChangTuList.get(i).get("userNum"));//剩余可乘人数
				ChangTu.put("departureCity",ChangTuList.get(i).get("departureCity"));//出发城市
				ChangTu.put("arrivalCity",ChangTuList.get(i).get("arrivalCity"));//到达城市
				ChangTu.put("releaseTime",ChangTuList.get(i).get("releaseTime"));//发布时间
				ChangTu.put("user_kehu_id",ChangTuList.get(i).get("user_kehu_id"));//外键ID(客户端用户的主键ID，司机端用户的主键ID)
				//把最新的结果放入到List中
				resultList.add(ChangTu);
			}
			result = "01";
			map.put("result", result);
			map.put("resultList", resultList);
			msg="成功";
		}catch (Exception e){
			logger.error(e.toString(), e);
			result = "00";
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	/**
	 * 司机端长途打车我要下单
	 * @throws Exception 
	 */
	@RequestMapping(value="/insertOrderChangTu")
	@ResponseBody
	public Object insertOrderChangTu(Page page,  HttpServletRequest request)throws Exception{
		logBefore(logger, "---司机端长途打车下单---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "失败";
		String result = "00";
		
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("information_kehu_changtu_id"))) {
						result = "00";
						map.put("result", result);
						msg="订单已取消！";
					}else{
						PageData pdsData = new PageData();
						pdsData.put("information_kehu_changtu_id", pd.getString("information_kehu_changtu_id"));
						pdsData.put("user_siji_fid", cacheData.getString("user_siji_fid"));
						PageData sDatas = sijiInformationChangTuService.queryInformationChangTuIDkuhu(pdsData);
//						PageData sDatas = sijiInformationChangTuService.queryInformationChangTuID(pdsData);
						PageData haveDatas = sijiOrderTongChengService.queryOrderTongChengHaveInHand(pdsData);//判断司机端你已一条正在进行中的同城订单与长途订单
						int keys = Integer.parseInt(haveDatas.get("haveInHands").toString());
						System.out.println(sDatas);
						if (sDatas == null) {
							result = "00";
							map.put("result", result);
							msg="同城订单已取消！";
						}else{
							int key = Integer.parseInt(sDatas.getString("status"));
							System.out.println(key);
							if (keys == 1) {
								result = "02";
								map.put("responseCode", result);
								msg="您已有进行中的订单";
							}else if (key == 1) {
								result = "01";
								map.put("responseCode", result);
								msg="已被抢单！";
							}else if(key == 2){
								result = "01";
								map.put("responseCode", result);
								msg="客户已取消！";
							}else {
								sijiInformationChangTuService.updateStatus(pd);//客户端发布的长途信息已被抢
								sDatas.put("order_changtu_id", this.get32UUID());
								sDatas.put("order_changtu_Time", Tools.date2Str(new Date()));//长途拼车下单时间
								sDatas.put("departureTime", sDatas.get("departureTime"));//长途拼车出发时间
								sDatas.put("departureCity", sDatas.getString("departureCity"));//长途拼车出发城市
								sDatas.put("changtu_departurePlace", sDatas.getString("departurePlace"));//长途拼车出发地
								sDatas.put("arrivalCity", sDatas.getString("arrivalCity"));//目的地城市
								sDatas.put("userNum", sDatas.getString("userNum"));//客户长途发布的可乘人数
								sDatas.put("changtu_destination", sDatas.getString("destination"));//目的地
								sDatas.put("changtu_mileage", sDatas.getString("mileage"));//里程数
								sDatas.put("changtu_radeAmount", sDatas.getString("carpoolFee"));//乘车金额
								sDatas.put("user_kehu_fid", sDatas.getString("user_kehu_id"));//外键ID(司机端用户主键ID)
								sDatas.put("user_siji_fid", cacheData.getString("user_siji_fid"));//外键ID(司机端用户主键ID)
								sDatas.put("order_changtu_status", 1);//长途订单状态（1-进行中 2-已完成  3-已取消）
								sDatas.put("information_changtu_fid", pd.getString("information_kehu_changtu_id"));//外键ID(长途拼车信息表的主键ID号)
								orderChangtuService.insertOrderChangTu(sDatas);//我要下单
								result = "01";
								msg="成功";
								map.put("result", result);
							}
						}
					}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
 			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 获取司机端长途订单列表信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryOrderChangtuListPage")
	@ResponseBody
	public Object queryOrderChangtuListPage(Page page,  HttpServletRequest request)throws Exception{
		logBefore(logger, "---获取司机端长途订单列表信息---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("user_siji_fid"))) {
					result = "00";
					map.put("result", result);
					msg="请求参数user_siji_fid（司机id）不能为空";
				}else{
						List<PageData> ChangTuOrderList = orderChangtuService.queryOrderChangtuList(pd);//长途订单列表
						result = "01";
						msg="成功";
						map.put("result", result);
						map.put("resultList", ChangTuOrderList);
				
					}
				}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 获取司机端长途订单详情
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryOrderChangTu")
	@ResponseBody
	public Object queryOrderChangTu(HttpServletRequest request)throws Exception{
		logBefore(logger, "---获取司机端长途订单详情---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("order_changtu_id"))) {
						result = "00";
						map.put("result", result);
						msg="请求参数order_changtu_id（长途订单id）不能为空";
					}else{
							PageData OrderChangTu =	orderChangtuService.queryOrderChangTu(pd);//长途订单详情
							result = "01";
							map.put("result", result);
							map.put("OrderChangTu", OrderChangTu);
							msg="成功";
					}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 司机端长途订单确认已送达
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateOrderChangTuStatus")
	@ResponseBody
	public Object updateOrderChangTuStatus(HttpServletRequest request)throws Exception{
		logBefore(logger, "---司机端长途订单确认已送达---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("order_changtu_id"))) {
					result = "00";
					map.put("result", result);
					msg="请求参数order_changtu_id（长途订单id）不能为空";
				}else{
					orderChangtuService.updateOrderChangTuStatus(pd);//司机端长途订单确认已送达（执行状态改成 2-已完成）
//					PageData OrderChangTu =	orderChangtuService.queryOrderChangTu(pd);//长途订单详情
					result = "01";
					map.put("result", result);
//					map.put("OrderChangTu", OrderChangTu);
					msg="订单已送达";
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 获取司机端同城打车列表信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryInformationTongCheng")
	@ResponseBody
	public Object queryInformationTongCheng(Page page)throws Exception{
		logBefore(logger, "---获取司机端同城打车列表信息---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respMsg = "";
		String result = "00";
		
		try {
				List<PageData> tongChengList = sijiInformationTongChengService.queryInformationTongChengPage(pd);
				List<PageData>  resultList = new ArrayList<PageData>();
				for(int i=0;i<tongChengList.size();i++){
				    PageData tongCheng = new PageData();
				    tongCheng.put("information_tongcheng_id",tongChengList.get(i).get("information_tongcheng_id"));
				    tongCheng.put("departurePlace",tongChengList.get(i).get("departurePlace"));
				    tongCheng.put("destination",tongChengList.get(i).get("destination"));
				    tongCheng.put("user_kehu_fid",tongChengList.get(i).get("user_kehu_fid"));
				    tongCheng.put("latitude_longitude_start",tongChengList.get(i).get("latitude_longitude_start"));//精度
				    tongCheng.put("latitude_longitude_end",tongChengList.get(i).get("latitude_longitude_end"));//纬度
				    tongCheng.put("create_time",tongChengList.get(i).get("create_time"));//发布时间
				    //把最新的结果放入到List中
				    resultList.add(tongCheng);
				}
				result = "01";
				map.put("responseCode", result);
				map.put("resultList", resultList);
				respMsg="成功";
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 获取司机端同城打车列表信息和查看客户端发布的长途路线列表
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryInformationTongChengAndChangTukehu")
	@ResponseBody
	public Object queryInformationTongChengAndChangTukehu(Page page)throws Exception{
		logBefore(logger, "---获取司机端同城打车列表信息和查看客户端发布的长途路线列表---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respMsg = "";
		String result = "00";
		try {
			List<PageData> tongChengList = sijiInformationTongChengService.queryInformationTongChengPage(pd);
			List<PageData>  resultList = new ArrayList<PageData>();
			for(int i=0;i<tongChengList.size();i++){
				PageData tongCheng = new PageData();
				tongCheng.put("information_tongcheng_id",tongChengList.get(i).get("information_tongcheng_id"));
				tongCheng.put("departurePlace",tongChengList.get(i).get("departurePlace"));
				tongCheng.put("destination",tongChengList.get(i).get("destination"));
				tongCheng.put("user_kehu_fid",tongChengList.get(i).get("user_kehu_fid"));
				tongCheng.put("latitude_longitude_start",tongChengList.get(i).get("latitude_longitude_start"));//精度
				tongCheng.put("latitude_longitude_end",tongChengList.get(i).get("latitude_longitude_end"));//纬度
				tongCheng.put("create_time",tongChengList.get(i).get("create_time"));//发布时间
				//把最新的结果放入到List中
				resultList.add(tongCheng);
			}
			
			List<PageData> ChangTuList = sijiInformationChangTuService.queryKehuInformationChangTuPage(pd);
			List<PageData>  resultLists = new ArrayList<PageData>();
			for(int i=0;i<ChangTuList.size();i++){
				PageData ChangTu = new PageData();
				ChangTu.put("information_kehu_changtu_id",ChangTuList.get(i).get("information_kehu_changtu_id"));//长途打车列表信息id
				ChangTu.put("userName",ChangTuList.get(i).get("userName"));//姓名(乘客姓名或车主姓名)
				ChangTu.put("phone",ChangTuList.get(i).get("phone"));//联系电话
				ChangTu.put("departureTime",ChangTuList.get(i).get("departureTime"));//出发时间
				ChangTu.put("departurePlace",ChangTuList.get(i).get("departurePlace"));//出发地
				ChangTu.put("destination",ChangTuList.get(i).get("destination"));//目的地
				ChangTu.put("carpoolFee",ChangTuList.get(i).get("carpoolFee"));//拼车费用
				ChangTu.put("userNum",ChangTuList.get(i).get("userNum"));//剩余可乘人数
				ChangTu.put("departureCity",ChangTuList.get(i).get("departureCity"));//出发城市
				ChangTu.put("arrivalCity",ChangTuList.get(i).get("arrivalCity"));//到达城市
				ChangTu.put("releaseTime",ChangTuList.get(i).get("releaseTime"));//发布时间
				ChangTu.put("user_kehu_id",ChangTuList.get(i).get("user_kehu_id"));//外键ID(客户端用户的主键ID，司机端用户的主键ID)
				//把最新的结果放入到List中
				resultLists.add(ChangTu);
			}
			result = "01";
			map.put("result", result);
			map.put("resultList", resultList);
			map.put("resultLists", resultLists);
			respMsg="成功！";
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 司机端同城打车抢单
	 * @throws Exception 
	 */
	@RequestMapping(value="/insertOrderTongCheng")
	@ResponseBody
	public Object insertOrderTongCheng(Page page)throws Exception{
		logBefore(logger, "---司机端同城打车抢单---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respMsg = "";
		String result = "00";
		
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				respMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					respMsg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("information_tongcheng_id"))|| Tools.isEmpty(pd.getString("about"))) {
						result = "00";
						map.put("responseCode", result);
						respMsg="请求参数information_tongcheng_id；about不能为空";
					}else{
						PageData pdsData = new PageData();
						pdsData.put("information_tongcheng_id", pd.getString("information_tongcheng_id"));
						pdsData.put("user_siji_fid", cacheData.getString("user_siji_fid"));
						PageData sDatas = sijiInformationTongChengService.queryInformationTongChengByPhone(pdsData);
						PageData haveDatas = sijiOrderTongChengService.queryOrderTongChengHaveInHand(pdsData);//判断司机端你已一条正在进行中的同城订单与长途订单
						int keys = Integer.parseInt(haveDatas.get("haveInHand").toString());
						if (sDatas == null) {
							result = "00";
							map.put("responseCode", result);
							respMsg="订单已取消！";
						}else{
							int key = Integer.parseInt(sDatas.getString("hujiao_status"));
							System.out.println(key);
							if (keys == 1) {
								result = "02";
								map.put("responseCode", result);
								respMsg="您已有进行中的订单";
							}else if (key == 2) {
								result = "01";
								map.put("responseCode", result);
								respMsg="已被抢单！";
							}else if(key == 1){
								result = "01";
								map.put("responseCode", result);
								respMsg="客户已取消！";
							}else {
									sijiInformationTongChengService.updateHujiaoStatus(pd);//修改已被抢单状态
									
									//获取当前的服务比例
									PageData fuwuData  = h5sysService.getDateByfuwufeiId(pd);
									String fuwubili="0";
									if(fuwuData!=null){
										if(Tools.notEmpty(fuwuData.getString("fuwubili2"))){
											fuwubili=fuwuData.getString("fuwubili2");
										}
									}
									
									//添加同城订单
									sDatas.put("order_tongcheng_id", this.get32UUID());
									sDatas.put("orderTime", Tools.date2Str(new Date()));
									sDatas.put("departurePlace", sDatas.getString("departurePlace"));//出发地
									sDatas.put("destination", sDatas.getString("destination"));//目的地
									sDatas.put("mileage", sDatas.getString("mileage"));//里程数
									sDatas.put("about", pd.getString("about"));//大约距离（米）
									sDatas.put("about_Amount", sDatas.get("about_Amount").toString());//大约乘车金额
									sDatas.put("radeAmount", sDatas.getString("radeAmount"));//乘车金额
									sDatas.put("order_tongcheng_status", 1);//同城订单状态（1-进行中 2-已完成  3-已取消）
									sDatas.put("user_kehu_fid", sDatas.getString("user_kehu_fid"));//客户id
									sDatas.put("information_tongcheng_id", pd.getString("information_tongcheng_id"));
									sDatas.put("user_siji_fid", cacheData.getString("user_siji_fid"));//司机id
									sDatas.put("fuwubili", fuwubili);//服务比例
									
									sijiOrderTongChengService.insertOrderTongCheng(sDatas);
									result = "01";
									map.put("responseCode", result);
									respMsg="成功";
								}
							
						}
					}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
 			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 获取司机端同城订单列表信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryOrderTongChengListPage")
	@ResponseBody
	public Object queryOrderTongChengListPage(Page page)throws Exception{
		logBefore(logger, "---获取司机端同城订单列表信息---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("user_siji_fid"))) {
					result = "00";
					map.put("result", result);
					msg="请求参数user_siji_fid（司机id）不能为空";
				}else{
						List<PageData> TongChengOrderList = sijiOrderTongChengService.queryOrderTongChengLists(pd);//同城订单列表
						result = "01";
						msg="成功";
						map.put("result", result);
						map.put("resultList", TongChengOrderList);
					}
				}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 司机端同城订单详情
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryOrderTongCheng")
	@ResponseBody
	public Object queryOrderTongCheng(HttpServletRequest request)throws Exception{
		logBefore(logger, "---获取司机端同城订单详情---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("order_tongcheng_id"))) {
					result = "00";
					map.put("result", result);
					msg="请求参数order_tongcheng_id（同城订单id）不能为空";
				}else{
					PageData OrderTongCheng = sijiOrderTongChengService.queryOrderTongCheng(pd);//同城订单详情
					result = "01";
					map.put("result", result);
					map.put("resultList", OrderTongCheng);
					msg="成功";
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 司机端已接到乘客
	 * @throws Exception 
	 */
	@RequestMapping(value="/SetOrderTongChengStatus")
	@ResponseBody
	public Object SetOrderTongChengStatus(HttpServletRequest request)throws Exception{
		logBefore(logger, "---司机端已接到乘客---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "失败";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("order_tongcheng_id"))) {
					result = "00";
					map.put("result", result);
					msg="请求参数order_tongcheng_id（同城订单id）不能为空";
				}else{
					sijiOrderTongChengService.SetOrderTongChengStatus(pd);
					result = "01";
					map.put("result", result);
					msg="已接到乘客";
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 司机端同城订单确认已送达
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateOrderTongChengStatus")
	@ResponseBody
	public Object updateOrderTongChengStatus(HttpServletRequest request)throws Exception{
		logBefore(logger, "---司机端同城订单确认已送达---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "失败";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("order_tongcheng_id"))|| Tools.isEmpty(pd.getString("mileage"))) {
						result = "00";
						map.put("result", result);
						msg="请求参数order_tongcheng_id（同城订单id）mileage（里程数）不能为空";
					}else{
						double jifeis = 0.0;
						String radeAmount ="";
						PageData jine = keHuService.tongchengjifeijine(pd);
						double mileage = Double.parseDouble(pd.getString("mileage"));
						if (mileage<=Double.parseDouble(jine.getString("qibugongli"))) {
							jifeis = Double.parseDouble(jine.getString("qibujia"));//起步费（元）
							pd.put("mileage", Double.parseDouble(jine.getString("qibugongli")));//已送达里程数
							radeAmount = String .format("%.2f",jifeis);
						}else {
							////超出公里算法    超出公里费*（超出公里-起步公里）+起步费
							jifeis = Double.parseDouble(jine.getString("jifei_Amount")) * (mileage-Double.parseDouble(jine.getString("qibugongli")))+Double.parseDouble(jine.getString("qibujia"));//
							radeAmount = String .format("%.2f",jifeis);
							pd.put("mileage", pd.getString("mileage"));//已送达里程数
							System.out.println(radeAmount);//大约乘车金额
						}
							pd.put("radeAmount", radeAmount);//乘车金额
							sijiOrderTongChengService.updateOrderTongChengStatus(pd);
							result = "01";
							map.put("result", result);
							map.put("radeAmount", radeAmount);
							msg="订单已送达";
					}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 获取司机端所有的个人资料信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/querySijidGeRZL")
	@ResponseBody
	public Object querySijidGeRZL(HttpServletRequest request)throws Exception{
		logBefore(logger, "---获取司机端所有的个人资料信息---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData pdresult= new PageData();
		pd = this.getPageData();
		String msg = "失败";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
					}else{
						pd.put("phone", cacheData.getString("phone"));
						PageData SijidGeRZL = sijiUserService.querySijidGeRZL(pd);
						pdresult.put("headImg",  BaseController.getPath(request)+SijidGeRZL.getString("headImg"));
						pdresult.put("identityFrontImg",  BaseController.getPath(request)+SijidGeRZL.getString("identityFrontImg"));
						pdresult.put("identityReverseImg",  BaseController.getPath(request)+SijidGeRZL.getString("identityReverseImg"));
						pdresult.put("drivingLicenceImg",  BaseController.getPath(request)+SijidGeRZL.getString("drivingLicenceImg"));
						pdresult.put("carIdentityImg",  BaseController.getPath(request)+SijidGeRZL.getString("carIdentityImg"));
						pdresult.put("city", SijidGeRZL.getString("city"));
						pdresult.put("phone", SijidGeRZL.getString("phone"));
						pdresult.put("carNumber", SijidGeRZL.getString("carNumber"));
						pdresult.put("userName", SijidGeRZL.getString("userName"));
						pdresult.put("carType", SijidGeRZL.getString("carType"));
						pdresult.put("carColor", SijidGeRZL.getString("carColor"));
						pdresult.put("user_siji_id", SijidGeRZL.getString("user_siji_id"));
						pdresult.put("realName", SijidGeRZL.getString("realName"));
						pdresult.put("identityCard", SijidGeRZL.getString("identityCard"));
						pdresult.put("authenticationState", SijidGeRZL.getString("authenticationState"));//认证状态(0-认证失败，1-认证通过，2-待认证)
						pdresult.put("tixianstatus", SijidGeRZL.getString("tixianstatus"));//提现设置密码状态（0-未设置，1-已设置）
						result = "01";
						map.put("result", result);
						map.put("resultList", pdresult);
						msg="成功";
					}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 获取司机端所有的个人资料信息和接单总数
	 * @throws Exception 
	 */
	@RequestMapping(value="/querySijidGeRZLAndOrderCount")
	@ResponseBody
	public Object querySijidGeRZLAndOrderCount(HttpServletRequest request)throws Exception{
		logBefore(logger, "---获取司机端所有的个人资料信息和接单总数---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData pdresult= new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "02";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}else{
					pd.put("phone", cacheData.getString("phone"));
					String isComplete = new String("0");
					//查该司机用户是否完善资料
					PageData pdsi = sijiUserService.getCompleteDataByPhone(pd);
					if(pdsi!=null){
						isComplete="1";
						//是否完善资料标识
//						map.put("isComplete_id", isComplete);
//						map.put("isComplete_msg", "用户已完善资料");
					}else{
						isComplete="0";
//						map.put("isComplete_id", isComplete);
//						map.put("isComplete_msg", "该用户未完善资料");
					}
					PageData SijidGeRZL = sijiUserService.querySijidGeRZL(pd);
					pdresult.put("headImg",  BaseController.getPath(request)+SijidGeRZL.getString("headImg"));
					pdresult.put("identityFrontImg",  BaseController.getPath(request)+SijidGeRZL.getString("identityFrontImg"));
					pdresult.put("identityReverseImg",  BaseController.getPath(request)+SijidGeRZL.getString("identityReverseImg"));
					pdresult.put("drivingLicenceImg",  BaseController.getPath(request)+SijidGeRZL.getString("drivingLicenceImg"));
					pdresult.put("carIdentityImg",  BaseController.getPath(request)+SijidGeRZL.getString("carIdentityImg"));
					pdresult.put("city", SijidGeRZL.getString("city"));
					pdresult.put("phone", SijidGeRZL.getString("phone"));
					pdresult.put("carNumber", SijidGeRZL.getString("carNumber"));
					pdresult.put("userName", SijidGeRZL.getString("userName"));
					pdresult.put("carType", SijidGeRZL.getString("carType"));
					pdresult.put("carColor", SijidGeRZL.getString("carColor"));
					pdresult.put("user_siji_id", SijidGeRZL.getString("user_siji_id"));
					pdresult.put("realName", SijidGeRZL.getString("realName"));//真实姓名
					pdresult.put("identityCard", SijidGeRZL.getString("identityCard"));
					pdresult.put("authenticationState", SijidGeRZL.getString("authenticationState"));//认证状态(0-认证失败，1-认证通过，2-待认证)
					pdresult.put("tixianstatus", SijidGeRZL.getString("tixianstatus"));//提现设置密码状态（0-未设置，1-已设置）
					pdresult.put("isComplete_id", isComplete);//判断司机是否完善个人资料（0-未完善，1-已完善）
					pd.put("user_siji_fid", cacheData.getString("user_siji_fid"));
					PageData OrderCount = sijiOrderTongChengService.queryOrderTongChengAndChangTuOrderCount(pd);//司机端同城订单和长途订单，接单总数
					result = "01";
					map.put("result", result);
					map.put("resultList", pdresult);
					map.put("resultLists", OrderCount);
					msg="成功!";
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("respMsg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 司机端同城订单和长途订单，接单总数
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryOrderTongChengAndChangTuOrderCount")
	@ResponseBody
	public Object queryOrderTongChengAndChangTuOrderCount(HttpServletRequest request)throws Exception{
		logBefore(logger, "---司机端同城订单和长途订单，接单总数---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("user_siji_fid"))) {
					result = "00";
					map.put("result", result);
					msg="请求参数user_siji_fid（司机id）不能为空";
				}else{
					PageData OrderCount = sijiOrderTongChengService.queryOrderTongChengAndChangTuOrderCount(pd);//司机端同城订单和长途订单，接单总数
					result = "01";
					map.put("result", result);
					map.put("resultList", OrderCount);
					msg="成功";
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 添加银行卡信息确认
	 * @throws Exception 
	 */
	@RequestMapping(value="/insertBankCard")
	@ResponseBody
	public Object insertBankCard(){
		logBefore(logger, "---添加银行卡信息确认---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "";
		String bankName = pd.getString("bankName");//银行卡的类型(所属银行)
		String cardNumber = pd.getString("cardNumber");//银行卡号
		String userName = pd.getString("userName");//姓名
		String phone = pd.getString("phone");//手机号码
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheUserSiji = sijiUserService.getDataByBackCode(pd);
				if(cacheUserSiji==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}else{
					if(Tools.isEmpty(bankName)||Tools.isEmpty(cardNumber)|| Tools.isEmpty(userName)|| Tools.isEmpty(phone)){
							result = "00";
							map.put("result", result);
							msg="请求参数不能为空！";
					}else{
//							if (!cacheUserSiji.getString("phone").equals(pd.getString("phone"))) {//判断手机号码是否匹配
//								result = "00";
//								map.put("result", result);
//								msg="请您输入本人手机号码！";
//							}else{
								pd.put("phone", cacheUserSiji.getString("phone"));
								PageData SijidGeRZL = sijiUserService.querySijidGeRZL(pd);//查询姓名是否匹配
								if (!SijidGeRZL.getString("realName").equals(pd.getString("userName"))) {
									result = "00";
									map.put("result", result);
									msg="请您输入真实的姓名！";
								}else{
									PageData BankCard = sijiUserService.queryBankCardCardNumber(pd);//查询银行卡信息是否从复
									if (BankCard != null) {
										result = "00";
										map.put("result", result);
										msg="该银行卡号已经存在！";
									}else{
										pd.put("userName", SijidGeRZL.getString("realName"));//姓名
										pd.put("phone", phone);//手机号
										pd.put("bank_card_id", this.get32UUID());
										pd.put("bankName", bankName);//银行卡的类型(所属银行)
										pd.put("cardNumber", cardNumber);//银行卡号
										pd.put("user_fid", cacheUserSiji.getString("user_siji_fid"));
										pd.put("create_time", DateUtil.getTime());//创建时间
										//批量设置成正常使用
										String ids=cacheUserSiji.getString("user_siji_fid");
										String Arrayids[]=ids.split(",");//分割成数组
										sijiUserService.setdefaultON(Arrayids);
										pd.put("status", 1);//(0-正常使用中，1-默认使用中）
										pd.put("bz", "司机端app用户");//手机号
										sijiUserService.insertBankCard(pd);
										result = "01";
										map.put("result", result);
										msg="成功";
									}
								}
//							}
						}
				}
			}
		}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
				msg="失败";
		}
		map.put("result", result);
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 我的银行卡管理列表
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryBankCardList")
	@ResponseBody
	public Object queryBankCardList()throws Exception{
		logBefore(logger, "---我的银行卡管理列表---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}else{
					pd.put("user_fid", cacheData.getString("user_siji_fid"));
					List<PageData> List = sijiUserService.queryBankCardList(pd);//我的银行卡管理列表
					result = "01";
					msg="成功";
					map.put("result", result);
					map.put("resultList", List);
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 我的银行卡管理详情
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryBankCard")
	@ResponseBody
	public Object queryBankCard()throws Exception{
		logBefore(logger, "---我的银行卡管理详情---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result="00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("bank_card_id"))) {
					result = "00";
					map.put("result", result);
					msg="bank_card_id（银行卡id）请求参数不能为空";
				}else{
					PageData BankCard = sijiUserService.queryBankCard(pd);//我的银行卡管理详情
					if (BankCard == null) {
						result = "00";
						map.put("result", result);
						msg="bank_card_id（银行卡id）不存在！";
					}else{
						result = "01";
						map.put("result", result);
						map.put("resultList", BankCard);
						msg="成功";
						
					}
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 银行卡解绑
	 * @throws Exception 
	 */
	@RequestMapping(value="/unbundlingCardNumber")
	@ResponseBody
	public Object unbundlingCardNumber()throws Exception{
		logBefore(logger, "---银行卡解绑---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("bank_card_id"))) {
						result = "00";
						map.put("result", result);
						msg="bank_card_id（银行卡id）请求参数不能为空";
					}else{
						PageData BankCard = sijiUserService.queryBankCard(pd);
						if (BankCard == null) {
							result = "00";
							map.put("result", result);
							msg="bank_card_id（银行卡id）出错啦！";
						}else{
							sijiUserService.unbundlingCardNumber(pd);//解绑
							result = "01";
							map.put("result", result);
							msg="解绑成功！";
						}
					}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 设置提现密码
	 * @throws Exception 
	 */
	@RequestMapping(value="/tixianpwd")
	@ResponseBody
	public Object tixianpwd()throws Exception{
		logBefore(logger, "---设置提现密码--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
					}
						String phone = pd.getString("phone");
						String pwd = pd.getString("txpwd");
						pd.put("phone", phone);	//手机号
						PageData pds = sijiUserService.findByPhone(pd);//查询判断用户是否存在
						if (Tools.isEmpty(pwd)) {
							result = "00";
							map.put("result", result);
							msg="提现密码不能为空";
						}else if(pds!=null){
							pd.put("payPassword", MD5.md5(pwd));//密码
							sijiUserService.updateTXPwd(pd);//修改密码
							result = "01";
							map.put("result", result);
							msg="提现密码设置成功！";
						}else{
							result = "00";
							map.put("result", result);
							msg = "该手机号暂未注册";
						}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 设置成默认银行卡 
	 * @throws Exception 
	 */
	@RequestMapping(value="/setdefault")
	@ResponseBody
	public Object setdefault()throws Exception{
		logBefore(logger, "---设置成默认银行卡 --");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}
				pd.put("bank_card_id", pd.getString("bank_card_id"));//银行卡id
				PageData bankCardId = sijiUserService.querybankCardId(pd);//查询银行卡id是否存在
				if (Tools.isEmpty(pd.getString("bank_card_id"))) {
					result = "00";
					map.put("result", result);
					msg="bank_card_id不能为空";
				}else if(bankCardId!=null){
					//批量设置成正常使用
					String ids=cacheData.getString("user_siji_fid");
					String Arrayids[]=ids.split(",");//分割成数组
					sijiUserService.setdefaultON(Arrayids);
					
					pd.put("status", "1");
					sijiUserService.setdefault(pd);//设置成默认银行卡 
					result = "01";
					map.put("result", result);
					msg="设置成功！";
				}else{
					result = "00";
					map.put("result", result);
					msg = "bank_card_id不存在！";
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 获取默认银行卡 
	 * @throws Exception 
	 */
	@RequestMapping(value="/querydefaultBankCard")
	@ResponseBody
	public Object querydefaultBankCard()throws Exception{
		logBefore(logger, "---获取默认银行卡 --");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}
				pd.put("user_fid", cacheData.getString("user_siji_fid"));
				PageData obtain = sijiUserService.querydefaultBankCard(pd);//获取默认银行卡
				if (obtain == null) {
					result = "00";
					map.put("result", result);
					map.put("resultList", obtain);
					msg="请添加银行卡！";
				}else {
					result = "01";
					map.put("result", result);
					map.put("resultList", obtain);
					msg="成功！";
				}
				
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 提取现金
	 * @throws Exception 
	 */
	@RequestMapping(value="/insertWithdrawCash")
	@ResponseBody
	public Object insertWithdrawCash(){
		logBefore(logger, "---提取现金---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		String zhichu_amount = pd.getString("zhichu_amount");//支出金额
		String cardNumber = pd.getString("cardNumber");//银行卡号
		String txpwd = pd.getString("txpwd");//提现密码
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheUserSiji = sijiUserService.getDataByBackCode(pd);
				if(cacheUserSiji==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}else{
					if(Tools.isEmpty(zhichu_amount)||Tools.isEmpty(cardNumber)){
							result = "00";
							map.put("result", result);
							msg="请求参数不能为空！";
					}else{
							pd.put("phone", cacheUserSiji.getString("phone"));
							PageData BankCard = sijiUserService.queryBankCardCardNumber(pd);//查询银行卡信息是否匹配
							if (BankCard == null) {
								result = "00";
								map.put("result", result);
								msg="选择正确的银行卡！";
							}else{
								pd.put("user_siji_fid", cacheUserSiji.getString("user_siji_fid"));
								PageData Account = sijiUserService.queryAccountbalance(pd);//我的账户余额
								String s = Account.get("Amount").toString();//我的账户余额
								String e = pd.get("zhichu_amount").toString();//提现余额
								double Amount = Double.parseDouble(s);
								double zhichuamount = Double.parseDouble(e);//提现金额
								System.out.println(zhichuamount);
								
								if (zhichuamount >= Amount) {//判断提现金额是否超过账户余额
									result = "00";
									map.put("result", result);
									msg="金额已超过可提现余额！";
								}else{
									String txpwds="";
									txpwds = MD5.md5(txpwd); //密码加密
									PageData payPassword = sijiUserService.querySijidGeRZL(pd);//查询密码
									if (!payPassword.getString("payPassword").equals(txpwds)) {//判断提现密码是否匹配
										result = "00";
										map.put("result", result);
										msg="提现密码错误！";
									}else{
										double zhichuamounts = Amount - zhichuamount;//账户金额 = 账户余额 - 支出金额
										pd.put("serial_number", this.getNumberForPK());//流水号
										pd.put("bank_card_tixian_id", this.get32UUID());
										pd.put("tixian_type", "提现");//类型
										pd.put("zhichu_amount", zhichuamount);//提现金额
										pd.put("user_siji_fid", cacheUserSiji.getString("user_siji_fid"));//司机外键id
										pd.put("bank_card_fid", BankCard.getString("bank_card_id"));//银行卡外键id
										pd.put("bankName", BankCard.getString("bankName"));//银行卡的类型(所属银行)
										pd.put("cardNumber", BankCard.getString("cardNumber"));//银行卡号
										pd.put("phone", BankCard.getString("phone"));//提现手机号码
										pd.put("realName", BankCard.getString("realName"));//提现姓名
										pd.put("tixian_time", DateUtil.getTime());//提现时间
										pd.put("bank_card_status", "受理中");//提现受理状态(0-正在受理中，1-已受理完成)
										pd.put("Amount", zhichuamounts);//我的账户余额
										sijiUserService.insertWithdrawCash(pd);//提取现金
										
										pd.put("totalassets", zhichuamounts);//总资产
										sijiUserService.setAccountAndAssets(pd);//存入计算出，提现后的剩下的余额
										result = "01";
										map.put("result", result);
										msg="提现成功!";
									}
									
								}
								
							}
						}
				}
			}
		}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
				msg="失败";
		}
		map.put("result", result);
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 我的账单明细列表
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryBillingDetailsList")
	@ResponseBody
	public Object queryBillingDetailsList()throws Exception{
		logBefore(logger, "---我的账单明细列表---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}else{
					pd.put("user_siji_fid", cacheData.getString("user_siji_fid"));
					List<PageData> List = sijiUserService.queryBillingDetailsList(pd);//我的账单明细列表
					result = "01";
					msg="成功!";
					map.put("result", result);
					map.put("resultList", List);
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 我的明细账单详情
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryBillDetails")
	@ResponseBody
	public Object queryBillDetails()throws Exception{
		logBefore(logger, "---我的明细账单详情---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result="00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("bank_card_tixian_id"))) {
					result = "00";
					map.put("result", result);
					msg="bank_card_tixian_id（提现明细表id）请求参数不能为空";
				}else{
					PageData BankCard = sijiUserService.queryBillDetails(pd);//我的明细账单详情
					if (BankCard == null) {
						result = "00";
						map.put("result", result);
						msg="bank_card_tixian_id（提现明细表id）不存在！";
					}else{
						result = "01";
						map.put("result", result);
						map.put("resultList", BankCard);
						msg="成功!";
						
					}
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 提现说明
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryCashDeclaration")
	@ResponseBody
	public Object queryCashDeclaration()throws Exception{
		logBefore(logger, "---提现说明---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = "";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result="00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
				}
				if (Tools.isEmpty(pd.getString("bank_card_id"))) {
					result = "00";
					map.put("result", result);
					msg="bank_card_id（银行卡id）请求参数不能为空";
				}else{
					pd.put("user_fid", cacheData.getString("user_siji_fid"));
					PageData BankCard = sijiUserService.queryCashDeclaration(pd);//提现说明
					if (BankCard == null) {
						result = "00";
						map.put("result", result);
						msg="bank_card_id（银行卡id）不存在！";
					}else{
						result = "01";
						map.put("result", result);
						map.put("resultList", BankCard);
						msg="成功!";
						
					}
				}
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 我的钱包
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryMywallet")
	@ResponseBody
	public Object queryMywallet()throws Exception{
		logBefore(logger, "---我的钱包---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdresult= new PageData();
		String msg = "失败";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				map.put("result", result);
				msg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					map.put("result", result);
					msg="缓存信息失效,请重新登录";
					}
				pd.put("user_siji_fid", cacheData.getString("user_siji_fid"));
				//同城今日总收入
				PageData tongchengincome = sijiUserService.querytongchengIncomeToday(pd);
//				if (!tongchengincome.get("count").equals(0)) {
//					tongchengincome.put("sum", 0);
//					System.out.println(tongchengincome);
//					result = "01";
//					map.put("result", result);
//					msg="计算成功！";
//				}
				
				//长途今日总收入
//				PageData changtuincome = sijiUserService.querychangtuIncomeToday(pd);
//				if (!changtuincome.get("count").equals(0)) {
//					changtuincome.put("sum", 0);
//					System.out.println(changtuincome);
//					result = "01";
//					map.put("result", result);
//					msg="计算成功！";
//				}
				
				//我的账户余额
//				PageData Accounts = sijiUserService.queryAccountbalance(pd);
//				if (Accounts==null) {
//					pd.put("Amount", 0);
//					pd.put("Incometoday", 0);
//					pd.put("totalassets", 0);
//					sijiUserService.setMywallet(pd);
//					result = "01";
//					map.put("result", result);
//					msg="计算成功！";
//				}
				//我的账户余额
				PageData Account = sijiUserService.queryAccountbalance(pd);
				String a = tongchengincome.get("sum").toString();//同城今日收入
//				String b = changtuincome.get("sum").toString();//长途今日收入
				String s = Account.get("Amount").toString();//我的账户余额
				String c = Account.get("Incometoday").toString();//显示今日总额收入
				String d = Account.get("totalassets").toString();//显示总资产
				
				
				double yuanshi =Double.parseDouble(c);
				
				double today = Double.parseDouble(a); //+ Double.parseDouble(b);//今日总收入 = 同城今日总收入 + 长途今日总收入
				if (today == 0) {
					double jiri = today - yuanshi*0;
					System.out.println(jiri);
					double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
					pd.put("Incometoday", jiri);//今日收入
					pd.put("Amount", Accountsum);//我的账户余额
					pd.put("totalassets", Accountsum);//总资产
					sijiUserService.setMywallet(pd);
					result = "01";
					map.put("result", result);
					msg="计算成功！";
				}else{
					if (yuanshi > today) {
//						double jiri = yuanshi - today;
						double Accountsum = today +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
						double jiris = yuanshi + today;
						pd.put("Incometoday", jiris);//今日收入
						pd.put("Amount", Accountsum);//我的账户余额
						pd.put("totalassets", Accountsum);//总资产
						sijiUserService.setMywallet(pd);//存入账户余额和今日收入
						result = "01";
						map.put("result", result);
						msg="计算成功！";
					}else{
						if (!Account.get("Incometoday").equals(today)) {
//							double jiri = yuanshi - today;
							double jiri = today - yuanshi;
							double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
							double jiris = yuanshi + jiri;
							pd.put("Incometoday", jiris);//今日收入
							pd.put("Amount", Accountsum);//我的账户余额
							pd.put("totalassets", Accountsum);//总资产
							sijiUserService.setMywallet(pd);//存入账户余额和今日收入
							result = "01";
							map.put("result", result);
							msg="计算成功！";
						}else{
							if (today > yuanshi) {
								double jiri = today - yuanshi;
								double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
								double jiris = yuanshi + jiri;
								pd.put("Incometoday", jiris);//今日收入
								pd.put("Amount", Accountsum);//我的账户余额
								pd.put("totalassets", Accountsum);//总资产
								sijiUserService.setMywallet(pd);//存入账户余额和今日收入
								result = "01";
								map.put("result", result);
								msg="计算成功！";
							}
							double jiri = today - yuanshi;
							if (!Account.get("Incometoday").equals(jiri)) {//判断今日收入金额是否
								double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
								pd.put("Incometoday", yuanshi);//今日收入
								pd.put("Amount", Accountsum);//我的账户余额
								pd.put("totalassets", Accountsum);//总资产
								sijiUserService.setMywallet(pd);//存入账户余额和今日收入
								result = "01";
								map.put("result", result);
								msg="计算成功！";
							}else{
								result = "00";
								map.put("result", result);
								msg="失败！";
							}
							
						}
					}
				}
				pd.put("phone", cacheData.getString("phone"));
				PageData SijidGeRZL = sijiUserService.querySijidGeRZL(pd);
				pdresult.put("Amount", SijidGeRZL.get("Amount"));//我的账户余额
				pdresult.put("Incometoday", SijidGeRZL.get("Incometoday"));//今日收入
				pdresult.put("totalassets", SijidGeRZL.get("totalassets"));//总资产
				result = "01";
				map.put("result", result);
				map.put("resultList", pdresult);
				msg="计算成功！";
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			map.put("result", result);
			map.put("msg", "失败");
			map.put("msg", e.getMessage());
		}
		map.put("msg", msg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 司机端更新设备标识ID
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-10
	 */
	@RequestMapping(value="/updateSijiRegistrationID")
	@ResponseBody
	public Object updateSijiRegistrationID(HttpServletRequest request)throws Exception{
		logBefore(logger, "---司机端更新设备标识ID---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String respMsg = "失败";
		String result = "00";
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				respMsg="操作失败，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData = sijiUserService.getDataByBackCode(pd);
				pd.put("RegistrationID", pd.get("RegistrationID"));
				pd.put("RegistrationType", pd.get("RegistrationType"));//设备类型
				pd.put("user_siji_id", cacheData.getString("user_siji_fid"));
				sijiUserService.updateSijiRegistrationID(pd);
				result = "01";
				map.put("respCode", result);
				respMsg="成功";
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
		}
		map.put("respMsg", respMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
}
