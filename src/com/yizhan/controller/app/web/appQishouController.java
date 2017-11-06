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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yizhan.controller.app.jpushModel.JpushClientUtil;
import com.yizhan.controller.base.BaseController;
import com.yizhan.service.information.h5sys.h5sysService;
import com.yizhan.service.information.qishou.QiShouService;
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
@RequestMapping(value="/api/qishou")
public class appQishouController extends BaseController{
	
	@Resource(name="qishouService")
	private QiShouService qishouService;
	
	@Resource(name = "h5sysService")
	private h5sysService h5sysService;
	
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
		String resprespMsg="";
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
				resprespMsg="请求成功,securityCode是我返回给你的正确的验证码，存于你们本地，最后注册请求时将该码与用户填的验证码一起传来。";
			}else{
				//请求失败
				resprespMsg="失败";
			}
		}else{ 
			//请求失败
			resprespMsg="电话号码不能为空";
		}
		map.put("respCode", respCode);
		map.put("respMsg", resprespMsg);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
		
		/**
		 * 登录
		 */
		@RequestMapping(value="/login")
		@ResponseBody
		public Object login(HttpServletRequest request){
			logBefore(logger, "---登录---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String result = "00";
			String resprespMsg = "";
			try{
				//判断是否已经获取验证码
				if(Tools.isEmpty(pd.getString("securityCode"))){
					resprespMsg = "请先获取短信验证码";
					}else {
						if(Tools.isEmpty(pd.getString("phone")) || Tools.isEmpty(pd.getString("yanzhengma"))){
							resprespMsg = "手机号码或者验证码不能为空";
						}else{
						if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){
							resprespMsg = "验证码不正确";
							}else{
							//判断用户是否存在
							PageData pds = this.qishouService.findByPhone(pd);
							String user_qishou_id= this.get32UUID();
							if(pds==null){
								pd.put("user_qishou_id", user_qishou_id);//主键
								pd.put("headImg","");	
								pd.put("phone", pd.getString("phone"));//手机号
								pd.put("userName",pd.getString("phone"));//默认昵称	
								pd.put("myState", 0);//我的状态(0-休息，1-接单状态)	
								pd.put("payPassword","");
								pd.put("payPwdStatus",0);//支付密码状态（0-未设置，1-已设置）
								pd.put("last_login_time",Tools.date2Str(new Date()));	
								pd.put("ip", request.getRemoteHost());//IP
								pd.put("status", 1); //1 使用中 0 已停用
								pd.put("Amount", 0); //账户余额
								pd.put("Incometoday", 0); //今天收入
								pd.put("totalassets", 0); //总资产
								pd.put("bz", "骑手端app用户");	
								//执行保存app用户
								qishouService.saveU(pd);
								result = "01";
								resprespMsg = "登录成功!";
								map.put("respCode", result);
								map.put("respMsg", resprespMsg);
							}else{
								user_qishou_id=pds.getString("user_qishou_id");
								String isComplete = new String("0");
								//查该骑手用户是否申请入驻
								PageData pdsi = qishouService.getCompleteData(pd);
								if(pdsi!=null){
									isComplete="1";
									//是否完善资料标识
									map.put("isComplete_id", isComplete);
									map.put("isComplete_respMsg", "用户已完善资料");
								}else{
									map.put("isComplete_id", isComplete);
									map.put("isComplete_respMsg", "该用户未完善资料");
								}
							}
							//加密后的返回码，用于请求识别
							String backCode=MD5.md5(pd.getString("phone"));
							//每一次登录，都要修改这次登录时间为最后一次登录时间
							pd.put("last_login_time",DateUtil.getTime());
							pd.put("ip",request.getRemoteHost());
							qishouService.updateLoginTimeAndIp(pd);
							//根据返回码获取缓存信息(存在则更新、不存在则添加)
							pd.put("backCode", backCode);
							PageData cacheData = qishouService.getDataByBackCode(pd);    
							if (cacheData == null) {
								//添加缓存
								PageData cachePD = new PageData();
								cachePD.put("cache_id", this.get32UUID());
								cachePD.put("backCode", backCode);
								cachePD.put("phone", pd.getString("phone"));
								cachePD.put("user_qishou_fid", user_qishou_id);
								cachePD.put("create_time", Tools.date2Str(new Date()));
								//将用户信息存进缓存表中
								qishouService.saveCacheData(cachePD);
							}else {
								//更新缓存
								PageData cachePD = new PageData();
								cachePD.put("backCode", backCode);
								cachePD.put("phone", pd.getString("phone"));
								cachePD.put("user_qishou_fid", user_qishou_id);
								cachePD.put("cache_id", cacheData.getString("cache_id"));
								cachePD.put("create_time", Tools.date2Str(new Date()));
								//将用户信息存进缓存表中
								qishouService.updateCacheData(cachePD);
							}
							result = "01";
							resprespMsg="登录成功";
							map.put("backCode", backCode);
						}
				 }
				}
			} catch(Exception e){
				logger.error(e.toString(), e);
				result = "00";
				resprespMsg = "登录失败";
				map.put("respCode", result);
				map.put("respMsg", resprespMsg);
			}
			map.put("respCode", result);
			map.put("respMsg", resprespMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(new PageData(), map);
		}
		
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
			String respCode = "00";
			String resprespMsg="请求失败，请联系管理员";
			try{
				if(Tools.isEmpty(pd.getString("backCode"))){
					respCode="00";
					resprespMsg="backCode为空,请重新登录";	
				}else{
					//根据backCode获取缓存信息
					PageData cacheData= qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						respCode="00";
						resprespMsg="缓存信息失效，请重新登录";
					}else{
						pd.put("phone", cacheData.getString("phone"));
						String isComplete = new String("0");
						//查该骑手用户是否申请入驻
						PageData pdsi = qishouService.getCompleteData(pd);
						if(pdsi!=null){
							isComplete="1";
							//是否入驻标识
							map.put("isComplete_id", isComplete);
							map.put("isComplete_respMsg", "用户已入驻");
						}else{
							map.put("isComplete_id", isComplete);
							map.put("isComplete_respMsg", "该用户还未入驻");
						}
						respCode="01";
						resprespMsg="缓存信息有效";
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
			}
			map.put("respCode", respCode);
			map.put("respMsg", resprespMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 判断用户是否认证、认证状态以及信息是否完善通过
		 * @throws Exception 
		 */
		@RequestMapping(value="/isComplete")
		@ResponseBody
		public Object isComplete(){
			logBefore(logger, "--判断用户是否认证、认证状态以及信息是否完善--");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String respCode = "00";
			String authenticationState = "00";
			String resprespMsg="请求失败，请联系管理员";
			try{
				if(Tools.isEmpty(pd.getString("backCode"))){
					respCode = "00";
					resprespMsg="backCode为空,请重新登录";	
				}else{
					//根据backCode获取缓存信息
					PageData cacheData= qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						respCode = "00";
						resprespMsg="缓存信息失效，请重新登录";
					}else{
						pd.put("phone", cacheData.getString("phone"));
						PageData tempPd=qishouService.getCompleteDataByPhone(pd);;
						//判断是否认证，若没有认证，则提示去认证
						if(Tools.isEmpty(tempPd.getString("authenticationState"))){
							authenticationState = "04";
							resprespMsg="认证状态(该用户尚未进行认证)";
						}else{
							//判断认证状态
							if(tempPd.getString("authenticationState").equals("0")){
								authenticationState = "03";
								resprespMsg="认证状态(失败),请重新认证";
							}else if(tempPd.getString("authenticationState").equals("2")){
								//判断信息是否完善
								if(Tools.isEmpty(tempPd.getString("realName")) || 
										Tools.isEmpty(tempPd.getString("sex")) ||
										Tools.isEmpty(tempPd.getString("area"))||
										Tools.isEmpty(tempPd.getString("identityCard"))||
										Tools.isEmpty(tempPd.getString("identityFrontImg"))||
										Tools.isEmpty(tempPd.getString("identityFrontHoldImg"))){
									authenticationState = "05";
									resprespMsg="认证状态(等待审核),用户信息状态(尚未完善)";
								}else{
									authenticationState = "02";
									resprespMsg="认证状态(等待审核),用户信息状态(已完善)";
								}
							}else{
								//判断信息是否完善
								if(Tools.isEmpty(tempPd.getString("realName")) ||
										Tools.isEmpty(tempPd.getString("sex")) ||
										Tools.isEmpty(tempPd.getString("area")) ||
										Tools.isEmpty(tempPd.getString("identityCard")) ||
										Tools.isEmpty(tempPd.getString("identityFrontImg")) ||
										Tools.isEmpty(tempPd.getString("identityFrontHoldImg"))){
									authenticationState = "06";
									resprespMsg="认证状态(认证通过),用户信息状态(尚未完善)";
								}else{
									authenticationState="01";
									resprespMsg="认证状态(认证通过),用户信息状态(已完善)";
								}
							}
						}
						
					}
					respCode ="01";
				}
			}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
				map.put("respCode", respCode);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respCode", respCode);
			map.put("authenticationState", authenticationState);
			map.put("respMsg", resprespMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
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
			String respCode = "00";
			String resprespMsg = "清除失败";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					resprespMsg="backCode不能为空";
				}else{
					qishouService.deleteCacheData(pd);
					respCode = "01";
					resprespMsg="清除成功";
				}
					
			}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
			}
			map.put("respCode", respCode);
			map.put("respMsg", resprespMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
	
		/**
		 * 骑手app用入驻申请资料
		 * @throws Exception 
		 */
		@RequestMapping(value="/CompleteDataQishou")
		@ResponseBody
		public Object CompleteData(HttpServletRequest request,
				@RequestParam(value="realName",required=false) String realName,
				@RequestParam(value="sex",required=false) String sex,
				@RequestParam(value="area",required=false) String area,
				@RequestParam(value="identityCard",required=false) String identityCard,
				@RequestParam(value="identityFrontImg",required=false) MultipartFile identityFrontImg,
				@RequestParam(value="daibutoolImg",required=false) MultipartFile daibutoolImg,
				@RequestParam(value="identityFrontHoldImg",required=false) MultipartFile identityFrontHoldImg){
			logBefore(logger, "---骑手app用入驻申请资料---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String qishouxinxizl = "qishouxinxizl/";
			String result = "00";
			String resprespMsg = "";
			String file = DateUtil.getDays(), fileName1 = "",fileName2 = "",fileName3 = "";
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + qishouxinxizl + file;//文件上传路径
			//图片上传
			try {
				if(Tools.isEmpty(request.getParameter("backCode"))){
					map.put("respCode", "00");
					map.put("respMsg", "操作失败,backCode不能为空");
				}else{
						pd.put("backCode", request.getParameter("backCode"));
						//根据backCode获取缓存信息
						PageData cacheData= qishouService.getDataByBackCode(pd);
						if(cacheData==null){
							result = "00";
							resprespMsg="缓存信息失效,请重新登录";
						}else{
							if(identityFrontImg!=null && identityFrontHoldImg!=null){
								fileName1 = FileUpload.fileUp(identityFrontImg, filePath, this.get32UUID());
								fileName2 = FileUpload.fileUp(identityFrontHoldImg, filePath, this.get32UUID());	
								fileName3 = FileUpload.fileUp(daibutoolImg, filePath, this.get32UUID());	
								pd.put("realName", realName);//真实姓名
								pd.put("sex", sex);//性别
								pd.put("area", area);//地区
								pd.put("identityCard", identityCard);//身份证号码
								pd.put("identityFrontImg", Const.FILEPATHIMG + qishouxinxizl + file + "/" + fileName1);//身份证正面照上传
								pd.put("identityFrontHoldImg", Const.FILEPATHIMG + qishouxinxizl + file + "/"+ fileName2);//手持身份证面照上传
								pd.put("daibutoolImg", Const.FILEPATHIMG + qishouxinxizl + file + "/"+ fileName3);//代步工具
								pd.put("authenticationState", 2);//认证状态(0-认证失败，1-认证通过，2-待认证)
								pd.put("submitTime", DateUtil.getTime());//认证提交时间
								pd.put("user_qishou_id", cacheData.getString("user_qishou_fid"));
								//执行上传信息资料
								qishouService.updateCompleteQishou(pd);
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
			map.put("respCode", result);
			map.put("respMsg", resprespMsg);
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
			String result = "00";
			String appHead = "qiShouappHead/";
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
				//根据backCode获取缓存信息
				PageData cacheData = qishouService.getDataByBackCode(pd);
				if (cacheData == null) {
					map.put("respCode", result);
					map.put("respMsg", "缓存信息失效,请重新登录");
				}else{
					if (null != imgFile && !imgFile.isEmpty()){
						//头像图片上传
						String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + appHead + file;	//文件上传路径
						fileName = FileUpload.fileUp(imgFile, filePath, this.get32UUID());	
						pd.put("headImg", Const.FILEPATHIMG + appHead + file + "/" +fileName);//执行上传
						pd.put("user_qishou_id", cacheData.getString("user_qishou_fid"));
						//修改头像图片
						qishouService.updateAppHeadImage(pd);
						result="01";
						map.put("respCode", result);
						map.put("respMsg", "头像图片修改成功");
						
					}else{
						logBefore(logger, "保存失败");
						map.put("respCode", result);
						map.put("respMsg", "图片文件为空，上传失败");
					}
				}
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
		public Object updateAppNickName(HttpServletRequest request) throws Exception{
			logBefore(logger, "---修改用户名昵称--");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String result = "";
			String resprespMsg = "";
			String userName = pd.get("userName") != null ? pd.get("userName").toString() : "";
			if(Tools.isEmpty(pd.getString("backCode"))){
				result = "00";
				resprespMsg="操作失败，请先登录，backCode不能为空";
			}else{
				//根据backCode获取缓存信息
				PageData cacheData=qishouService.getDataByBackCode(pd);
				if(cacheData==null){
					result = "00";
					resprespMsg="缓存信息失效,请重新登录";
				}else{
					if (Tools.isEmpty(userName)) {
						result = "00";
						resprespMsg = "(userName)请求参数不能为空!";
					}else{
						pd.put("phone", cacheData.getString("phone"));
//						PageData pds = this.qishouService.findByPhone(pd);//查询判断用户是否存在
//						if(pds!=null){
							PageData isUserName = qishouService.queryByUserName(pd);//查询判断用户名是否已被占用 
							if (isUserName!=null) {
									result = "00";
									resprespMsg="用户名已被占用";
								}else{
										result = "01";
										resprespMsg = "修改成功!";
										pd.put("userName", userName);
										qishouService.updateAppNickName(pd);
									}
										
//								}else{
//									result = "00";
//									resprespMsg = "该手机号暂未注册!";
//								}
					}	
				
			}
		}
			map.put("respCode", result);
			map.put("respMsg", resprespMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		
		/**
		 * 获取个人资料信息
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryGeRZL")
		@ResponseBody
		public Object queryGeRZL(HttpServletRequest request)throws Exception{
			logBefore(logger, "---获取个人资料信息---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			PageData pdresult= new PageData();
			pd = this.getPageData();
			String respMsg = "";
			String result = "00";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					map.put("respCode", result);
					respMsg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						respMsg="缓存信息失效,请重新登录";
						}else {
							pd.put("phone", cacheData.getString("phone"));
							PageData GeRZL = qishouService.queryGeRZL(pd);
							pdresult.put("headImg",  BaseController.getPath(request)+GeRZL.getString("headImg"));
							pdresult.put("phone", GeRZL.getString("phone"));
							pdresult.put("userName", GeRZL.getString("userName"));
							pdresult.put("user_qishou_id", GeRZL.getString("user_qishou_id"));
							pdresult.put("realName", GeRZL.getString("realName"));
							pdresult.put("identityCard", GeRZL.getString("identityCard"));
							pdresult.put("payPwdStatus", GeRZL.get("payPwdStatus"));//支付密码状态（0-未设置，1-已设置）
							pdresult.put("myState", GeRZL.getString("myState"));//我的状态(0-休息，1-接单状态)
							pdresult.put("authenticationState", GeRZL.getString("authenticationState"));//认证状态(0-认证失败，1-认证通过，2-待认证)
							result = "01";
							map.put("respCode", result);
							map.put("resultList", pdresult);
							respMsg="成功!";
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 获取新任务
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryOrderTakeou")
		@ResponseBody
		public Object queryOrderTakeou()throws Exception{
			logBefore(logger, "---获取新任务---");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
						List<PageData> qishouOrderList = qishouService.queryOrderqishouLists(pd);//获取新任务
						List<PageData>  resultList = new ArrayList<PageData>();
						for(int i=0;i<qishouOrderList.size();i++){
						    PageData qishouOrder = new PageData();
						    qishouOrder.put("order_takeou_id",qishouOrderList.get(i).get("order_takeou_id"));//订单主键id
						    qishouOrder.put("shopName",qishouOrderList.get(i).get("shopName"));//取单门店地址名
						    qishouOrder.put("latitude_sj",qishouOrderList.get(i).get("latitude_sj"));//经度
						    qishouOrder.put("longitude_sj",qishouOrderList.get(i).get("longitude_sj"));//纬度
						    qishouOrder.put("qucan_number",qishouOrderList.get(i).get("qucan_number"));//取餐编号,标识+数字(S12)
						    qishouOrder.put("peisongfei",qishouOrderList.get(i).get("peisongfei"));//当前订单的配送费
						    qishouOrder.put("order_remark",qishouOrderList.get(i).get("order_remark"));//订单备注(要求，口味等)
						    qishouOrder.put("orderTime",qishouOrderList.get(i).get("orderTime"));//下单时间
						    qishouOrder.put("detailAddress",qishouOrderList.get(i).get("detailAddress"));//配送地址
						    qishouOrder.put("latitude",qishouOrderList.get(i).get("latitude"));//配送地址经度
						    qishouOrder.put("longitude",qishouOrderList.get(i).get("longitude"));//配送地址纬度
						    qishouOrder.put("totalSum",qishouOrderList.get(i).get("totalSum"));//合计总价
						    qishouOrder.put("paySum",qishouOrderList.get(i).get("paySum"));//实际支付金额
						    qishouOrder.put("payMethod",qishouOrderList.get(i).get("payMethod"));//支付方式
						    //把最新的结果放入到List中
						    resultList.add(qishouOrder);
						}
						result = "01";
						respMsg="成功";
						map.put("respCode", result);
						map.put("resultList", resultList);
					}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 新任务抢单
		 * @throws Exception 
		 */
		@RequestMapping(value="/updateOrderStateQishou")
		@ResponseBody
		public Object updateOrderStateQishou()throws Exception{
			logBefore(logger, "---新任务抢单---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String respMsg = "";
			String result = "";
			String orderStatus = "";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					respMsg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("order_takeou_id"))) {
						result = "00";
						respMsg="请求参数order_takeou_id（订单id）不能为空";
					}else{
							PageData stateSetData = qishouService.findByOrderTakeouId(pd);
							if (stateSetData == null) {
								result = "00";
								respMsg="请求参数order_takeou_id（订单id）不正确";
							}else{
								pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
								PageData haveData = qishouService.queryOrderTakeouHaveInHand(pd);
								int key = Integer.parseInt(stateSetData.getString("orderStateQishou"));
								int keys = Integer.parseInt(haveData.get("haveInHands").toString());
								System.out.println(keys);
								if (keys >= 3) {
									result = "01";
									orderStatus = "04";
									respMsg="已有三条正在进行中的订单,请您去完成。";
								}else if (key == 0) {
									result = "01";
									orderStatus = "02";
									respMsg="订单已取消";
								} else if(key == 1){
									result = "01";
									orderStatus = "03";
									respMsg="订单已被抢单";
								}else {
									pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
									qishouService.updateOrderStateQishou(pd);//修改待取餐状态（3、3、1 ）
									result = "01";
									orderStatus = "01";
									respMsg="抢单成功";
									PageData sData = qishouService.querytbOrderTakeou(pd);//获取设备id(推送指定商家)RegistrationID
									Map<String,Object> maps = new HashMap<String,Object>();
									if (sData.getString("RegistrationType").equals("ios")) {
										String registrationId = sData.getString("RegistrationID");
										maps.put("jump", "2");//2跳转到商家待取餐
										maps.put("mp3", "xxx.mp3");
										maps.put("notification_title", "订单大厅骑手已接单！");
										maps.put("msg_title", "骑手已接单，正在拼命的赶来！");
										String extrasparam = new Gson().toJson(maps);
										JpushClientUtil.ios_sj_sendToRegistrationId(registrationId, "订单大厅骑手已接单！", "骑手已接单，正在拼命的赶来！", "抢单", extrasparam);
										System.out.println(".........骑手抢单之后+执行推送给指定的商家（ios）.........");
									}else {
										String registrationId = sData.getString("RegistrationID");
										maps.put("jump", "2");//2跳转到商家待取餐
										maps.put("mp3", "xxx.mp3");
										maps.put("notification_title", "订单大厅骑手已接单！");
										maps.put("msg_title", "骑手已接单，正在拼命的赶来！");
										String extrasparam = new Gson().toJson(maps);
										JpushClientUtil.sj_sendToRegistrationId(registrationId, "订单大厅骑手已接单！", "骑手已接单，正在拼命的赶来！", "抢单", extrasparam);
										System.out.println(".........骑手抢单之后+执行推送给指定的商家（安卓）.........");
									}
								}
							}
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			map.put("respCode", result);
			map.put("orderStatus", orderStatus);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 获取待取货
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryOrderStateQishou")
		@ResponseBody
		public Object queryOrderStateQishou()throws Exception{
			logBefore(logger, "---获取待取餐--");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
					List<PageData> qishouOrderList = qishouService.queryOrderStateQishou(pd);//获取待取餐
					List<PageData>  resultList = new ArrayList<PageData>();
					for(int i=0;i<qishouOrderList.size();i++){
						PageData qishouOrder = new PageData();
						qishouOrder.put("order_takeou_id",qishouOrderList.get(i).get("order_takeou_id"));//订单主键id
						qishouOrder.put("shopName",qishouOrderList.get(i).get("shopName"));//取单门店地址名
						qishouOrder.put("latitude_sj",qishouOrderList.get(i).get("latitude_sj"));//经度
					    qishouOrder.put("longitude_sj",qishouOrderList.get(i).get("longitude_sj"));//纬度
						qishouOrder.put("qucan_number",qishouOrderList.get(i).get("qucan_number"));//取餐编号,标识+数字(S12)
						qishouOrder.put("peisongfei",qishouOrderList.get(i).get("peisongfei"));//当前订单的配送费
						qishouOrder.put("order_remark",qishouOrderList.get(i).get("order_remark"));//订单备注(要求，口味等)
						qishouOrder.put("orderTime",  qishouOrderList.get(i).get("orderTime"));//下单时间
						qishouOrder.put("detailAddress",qishouOrderList.get(i).get("detailAddress"));//配送地址
						qishouOrder.put("latitude",qishouOrderList.get(i).get("latitude"));//配送地址经度
					    qishouOrder.put("longitude",qishouOrderList.get(i).get("longitude"));//配送地址纬度
						qishouOrder.put("totalSum",qishouOrderList.get(i).get("totalSum"));//合计总价
						qishouOrder.put("paySum",qishouOrderList.get(i).get("paySum"));//实际支付金额
						qishouOrder.put("payMethod",qishouOrderList.get(i).get("payMethod"));//支付方式
						//把最新的结果放入到List中
						resultList.add(qishouOrder);
					}
					result = "01";
					respMsg="成功";
					map.put("respCode", result);
					map.put("resultList", resultList);
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 确认取货
		 * @throws Exception 
		 */
		@RequestMapping(value="/updateOrderStateQishouConfirm")
		@ResponseBody
		public Object updateOrderStateQishouConfirm()throws Exception{
			logBefore(logger, "---确认取货---");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("order_takeou_id"))) {
						result = "00";
						map.put("respCode", result);
						respMsg="请求参数order_takeou_id（订单id）不能为空";
					}else{
							PageData stateSetData = qishouService.findByOrderTakeouId(pd);
							if (stateSetData == null) {
								result = "00";
								map.put("respCode", result);
								respMsg="请求参数order_takeou_id（订单id）不正确";
							}else{
								pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
								qishouService.updateOrderStateQishouConfirm(pd);//确认取货
								result = "01";
								respMsg="确认取货成功";
								map.put("respCode", result);
								PageData sData = qishouService.querytbOrderTakeou(pd);//获取设备id(推送指定商家)RegistrationID
								Map<String,Object> maps = new HashMap<String,Object>();
								if (sData.getString("RegistrationType").equals("ios")) {
									String registrationId = sData.getString("RegistrationID");
									maps.put("jump", "3");//3跳转到商家进行中
									maps.put("mp3", "xxx.mp3");
									maps.put("notification_title", "订单大厅骑手已确认取货！");
									maps.put("msg_title", "骑手已确认取货，正在拼命的送货！");
									String extrasparam = new Gson().toJson(maps);
									JpushClientUtil.ios_sj_sendToRegistrationId(registrationId, "订单大厅骑手已确认取货！", "骑手已确认取货，正在拼命的送货！", "抢单", extrasparam);
									System.out.println(".........骑手确认取货之后+执行推送给指定的商家（ios）.........");
								}else {
									String registrationId = sData.getString("RegistrationID");
									maps.put("jump", "3");//3跳转到商家进行中
									maps.put("mp3", "xxx.mp3");
									maps.put("notification_title", "订单大厅骑手已确认取货！");
									maps.put("msg_title", "骑手已确认取货，正在拼命的送货！");
									String extrasparam = new Gson().toJson(maps);
									JpushClientUtil.sj_sendToRegistrationId(registrationId, "订单大厅骑手已确认取货！", "骑手已确认取货，正在拼命的送货！", "抢单", extrasparam);
									System.out.println(".........骑手确认取货之后+执行推送给指定的商家（安卓）.........");
								}
							}
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		
		/**
		 * 获取待送达
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryOrderStateQishoutow")
		@ResponseBody
		public Object queryOrderStateQishoutow()throws Exception{
			logBefore(logger, "---获取待送达--");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
					List<PageData> qishouOrderList = qishouService.queryOrderStateQishoutow(pd);//获取待送达
					List<PageData>  resultList = new ArrayList<PageData>();
					for(int i=0;i<qishouOrderList.size();i++){
						PageData qishouOrder = new PageData();
						qishouOrder.put("order_takeou_id",qishouOrderList.get(i).get("order_takeou_id"));//订单主键id
						qishouOrder.put("shopName",qishouOrderList.get(i).get("shopName"));//取单门店地址名
						qishouOrder.put("latitude_sj",qishouOrderList.get(i).get("latitude_sj"));//经度
					    qishouOrder.put("longitude_sj",qishouOrderList.get(i).get("longitude_sj"));//纬度
						qishouOrder.put("qucan_number",qishouOrderList.get(i).get("qucan_number"));//取餐编号,标识+数字(S12)
						qishouOrder.put("peisongfei",qishouOrderList.get(i).get("peisongfei"));//当前订单的配送费
						qishouOrder.put("order_remark",qishouOrderList.get(i).get("order_remark"));//订单备注(要求，口味等)
						qishouOrder.put("orderTime",  qishouOrderList.get(i).get("orderTime"));//下单时间
						qishouOrder.put("detailAddress",qishouOrderList.get(i).get("detailAddress"));//配送地址
						qishouOrder.put("latitude",qishouOrderList.get(i).get("latitude"));//配送地址经度
					    qishouOrder.put("longitude",qishouOrderList.get(i).get("longitude"));//配送地址纬度
					    qishouOrder.put("phone",qishouOrderList.get(i).get("phone"));//联系电话
						qishouOrder.put("totalSum",qishouOrderList.get(i).get("totalSum"));//合计总价
						qishouOrder.put("paySum",qishouOrderList.get(i).get("paySum"));//实际支付金额
						qishouOrder.put("payMethod",qishouOrderList.get(i).get("payMethod"));//支付方式
						//把最新的结果放入到List中
						resultList.add(qishouOrder);
					}
					result = "01";
					respMsg="成功";
					map.put("respCode", result);
					map.put("resultList", resultList);
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		
		/**
		 * 确认已送达
		 * @throws Exception 
		 */
		@RequestMapping(value="/updateOrderStateQishouSendupto")
		@ResponseBody
		public Object updateOrderStateQishouSendupto()throws Exception{
			logBefore(logger, "---确认已送达---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String respMsg = "失败";
			String result = "00";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					respMsg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("order_takeou_id"))||Tools.isEmpty(pd.getString("distance"))) {
						result = "00";
						map.put("respCode", result);
						respMsg="请求参数order_takeou_id（订单id）不能为空";
					}else{
							PageData stateSetData = qishouService.findByOrderTakeouId(pd);
							if (stateSetData == null) {
								result = "00";
								map.put("respCode", result);
								respMsg="请求参数order_takeou_id（订单id）不正确";
							}else{
								pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
								pd.put("qurysdTime", Tools.date2Str(new Date()));
								
								//根据骑手配送公里来计算骑手端的收益。
								PageData Accounts = qishouService.queryAccountbalance(pd);//骑手我的账户余额 、今日收入、总资产
								double peiSongGongLi = Double.parseDouble(pd.getString("distance"));//骑手已送达的配送全程的公里
								PageData peiSongGuiZe = h5sysService.peiSongGuiZeInfo(pd);//后台自定义的配置规则
								if (peiSongGuiZe != null) {
									if ( 1 < peiSongGongLi && peiSongGongLi <= 5) {//判断骑手送货距离是否满足配送费规则（1-5公里的范围内）骑手得5元
										pd.put("Incometoday", String.format("%.2f",Double.parseDouble(Accounts.getString("Incometoday")) + Double.parseDouble(peiSongGuiZe.getString("gongli1"))));//今日收入
										pd.put("Amount", String.format("%.2f",Double.parseDouble(Accounts.getString("Amount")) + Double.parseDouble(peiSongGuiZe.getString("gongli1"))));//我的账户余额
										pd.put("totalassets", String.format("%.2f",Double.parseDouble(Accounts.getString("totalassets")) + Double.parseDouble(peiSongGuiZe.getString("gongli1"))));//总资产
										pd.put("qishou_obtain", String.format("%.2f",Double.parseDouble(peiSongGuiZe.getString("gongli1"))));//骑手送达每一单应得的钱。
									}else if (5 < peiSongGongLi && peiSongGongLi <= 10 ) {//判断骑手送货距离是否满足配送费规则（5-10公里的范围内）骑手得10元
										pd.put("Incometoday", String.format("%.2f",Double.parseDouble(Accounts.getString("Incometoday")) + Double.parseDouble(peiSongGuiZe.getString("gongli2"))));//今日收入
										pd.put("Amount", String.format("%.2f",Double.parseDouble(Accounts.getString("Amount")) + Double.parseDouble(peiSongGuiZe.getString("gongli2"))));//我的账户余额
										pd.put("totalassets", String.format("%.2f",Double.parseDouble(Accounts.getString("totalassets")) + Double.parseDouble(peiSongGuiZe.getString("gongli2"))));//总资产
										pd.put("qishou_obtain", String.format("%.2f",Double.parseDouble(peiSongGuiZe.getString("gongli2"))));//骑手送达每一单应得的钱。
									}else if (10 < peiSongGongLi && peiSongGongLi <= 15) {//判断骑手送货距离是否满足配送费规则（10-15公里的范围内）骑手得15元
										pd.put("Incometoday", String.format("%.2f",Double.parseDouble(Accounts.getString("Incometoday")) + Double.parseDouble(peiSongGuiZe.getString("gongli3"))));//今日收入
										pd.put("Amount", String.format("%.2f",Double.parseDouble(Accounts.getString("Amount")) + Double.parseDouble(peiSongGuiZe.getString("gongli3"))));//我的账户余额
										pd.put("totalassets", String.format("%.2f",Double.parseDouble(Accounts.getString("totalassets")) + Double.parseDouble(peiSongGuiZe.getString("gongli3"))));//总资产
										pd.put("qishou_obtain", String.format("%.2f",Double.parseDouble(peiSongGuiZe.getString("gongli3"))));//骑手送达每一单应得的钱。
									}else if (15 <= peiSongGongLi) {//判断骑手送货距离是否满足配送费规则（15以上公里的范围内）骑手得20元
										pd.put("Incometoday", String.format("%.2f",Double.parseDouble(Accounts.getString("Incometoday")) + Double.parseDouble(peiSongGuiZe.getString("gongli4"))));//今日收入
										pd.put("Amount", String.format("%.2f",Double.parseDouble(Accounts.getString("Amount")) + Double.parseDouble(peiSongGuiZe.getString("gongli4"))));//我的账户余额
										pd.put("totalassets", String.format("%.2f",Double.parseDouble(Accounts.getString("totalassets")) + Double.parseDouble(peiSongGuiZe.getString("gongli4"))));//总资产
										pd.put("qishou_obtain", String.format("%.2f",Double.parseDouble(peiSongGuiZe.getString("gongli4"))));//骑手送达每一单应得的钱。
									}
									qishouService.setMywallet(pd);//存入骑手得账户 、今日收入、总资产
								}
								qishouService.updateOrderStateQishouSendupto(pd);//修改确认已送达
								result = "01";
								respMsg="确认已送达成功";
								map.put("respCode", result);
								
								//更新商家收益,把该单收益+到商家收益中和余额中
								//1.根据外卖订单的主键ID获取该订单的信息
								PageData pd1=new PageData();
								pd1.put("order_takeou_id", pd.getString("order_takeou_id"));
								pd1=qishouService.findByOrderTakeouId(pd1);
								
								//2.计算出商家应该获得的钱（客户支付钱-配送费）*（1-服务比例）t1.totalSum-(t1.paySum * t1.fuwubili+ t1.peisongfei)
								double money=Double.parseDouble(pd1.get("totalSum").toString())-(Double.parseDouble(pd1.get("paySum").toString())*Double.parseDouble(pd1.get("fuwubili").toString())+Double.parseDouble(pd1.get("peisongfei").toString()));
								System.out.println(money);
								//3.获取商家信息
								PageData pd2=new PageData();
								pd2.put("user_shangjia_id", pd1.getString("user_shangjia_fid"));
								pd2=qishouService.getShangJiaInforById(pd2);
								
								//4.计算商家最终的总收益和余额
								double totalassets1=Double.parseDouble(pd2.get("totalassets").toString()) + money;
								double Amount1=Double.parseDouble(pd2.get("Amount").toString()) + money;
								
								String totalassets=FormateUtil.formateDoubleAsIntPointString(totalassets1);
								String Amount=FormateUtil.formateDoubleAsIntPointString(Amount1);
								
								//5.更新商家总收益和余额
								pd2.put("totalassets", totalassets);
								pd2.put("Amount", Amount);
								qishouService.updateShangJiaInforById(pd2);
								
								PageData sData = qishouService.querytbOrderTakeou(pd);//获取设备id(推送指定商家)RegistrationID
								String registrationId = sData.getString("RegistrationID");
								Map<String,Object> maps = new HashMap<String,Object>();
								
								if (sData.getString("RegistrationType").equals("ios")) {
									maps.put("jump", "4");//4跳转到商家已完成
									maps.put("mp3", "xxx.mp3");
									maps.put("notification_title", "订单大厅骑手已送达！");
									maps.put("msg_title", "骑手已送达！");
									String extrasparam = new Gson().toJson(maps);
									JpushClientUtil.ios_sj_sendToRegistrationId(registrationId, "订单大厅骑手已送达！", "骑手已送达！", "抢单", extrasparam);
									System.out.println(".........骑手确认已送达之后+执行推送给指定的商家（ios）.........");
								}else {
									maps.put("jump", "4");//4跳转到商家已完成
									maps.put("mp3", "xxx.mp3");
									maps.put("notification_title", "订单大厅骑手已送达！");
									maps.put("msg_title", "骑手已送达！");
									String extrasparam = new Gson().toJson(maps);
									JpushClientUtil.sj_sendToRegistrationId(registrationId, "订单大厅骑手已送达！", "骑手已送达！", "抢单", extrasparam);
									System.out.println(".........骑手确认已送达之后+执行推送给指定的商家（安卓）.........");
								}
							}
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		
		/**
		 * 订单详情
		 * @throws Exception 
		 */
		@RequestMapping(value="/querytbOrderTakeou")
		@ResponseBody
		public Object querytbOrderTakeou()throws Exception{
			logBefore(logger, "---订单详情---");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					if (Tools.isEmpty(pd.getString("order_takeou_id"))) {
						result = "00";
						map.put("respCode", result);
						respMsg="请求参数order_takeou_id（订单id）不能为空";
					}else{
							PageData stateSetData = qishouService.findByOrderTakeouId(pd);
							if (stateSetData == null) {
								result = "00";
								map.put("respCode", result);
								respMsg="请求参数order_takeou_id（订单id）不正确";
							}else{
								PageData sData = qishouService.querytbOrderTakeou(pd);//订单详情
								if (sData == null) {
									result = "01";
									respMsg="暂时无数据";
									map.put("respCode", result);
									map.put("resultList", sData);
								}else{
									result = "01";
									respMsg="成功";
									map.put("respCode", result);
									map.put("resultList", sData);
								}
							}
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		
		/**
		 * 是否开工
		 * @throws Exception 
		 */
		@RequestMapping(value="/isupdateStart")
		@ResponseBody
		public Object isupdateStart()throws Exception{
			logBefore(logger, "---是否开工---");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}else{
						pd.put("phone", cacheData.getString("phone"));
						pd.put("user_qishou_id", cacheData.getString("user_qishou_fid"));
						PageData GeRZL = qishouService.queryGeRZL(pd);
						//判断是否开工状态
						if(GeRZL.getString("myState").equals("0")){
							pd.put("myState", 1);
							qishouService.isupdateStart(pd);//是否开工
							result = "01";
							respMsg="开工啦";
							map.put("respCode", result);
						}else if(GeRZL.getString("myState").equals("1")){
							pd.put("myState", 0);
							qishouService.isupdateStart(pd);//执行休息
							result = "02";
							respMsg="休息啦";
							map.put("respCode", result);
						}
					}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		
		/**
		 * 获取历史订单（已送达）
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryLiShiOrderStateQishou")
		@ResponseBody
		public Object queryLiShiOrderStateQishou()throws Exception{
			logBefore(logger, "---获取历史订单（已送达）--");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
					List<PageData> qishouLiShiOrderList = qishouService.queryLiShiOrderStateQishou(pd);//获取历史订单
					List<PageData>  resultList = new ArrayList<PageData>();
					for(int i=0;i<qishouLiShiOrderList.size();i++){
						PageData qishouOrder = new PageData();
						qishouOrder.put("order_takeou_id",qishouLiShiOrderList.get(i).get("order_takeou_id"));//订单主键id
						qishouOrder.put("shopName",qishouLiShiOrderList.get(i).get("shopName"));//取单门店地址名
						qishouOrder.put("qucan_number",qishouLiShiOrderList.get(i).get("qucan_number"));//取餐编号,标识+数字(S12)
						qishouOrder.put("peisongfei",qishouLiShiOrderList.get(i).get("peisongfei"));//当前订单的配送费
						qishouOrder.put("order_remark",qishouLiShiOrderList.get(i).get("order_remark"));//订单备注(要求，口味等)
						qishouOrder.put("orderTime",  qishouLiShiOrderList.get(i).get("orderTime"));//下单时间
						qishouOrder.put("qurysdTime",  qishouLiShiOrderList.get(i).get("qurysdTime"));//已送达时间
						qishouOrder.put("qishou_obtain",  qishouLiShiOrderList.get(i).get("qishou_obtain"));//骑手本单收益
						qishouOrder.put("address",qishouLiShiOrderList.get(i).get("address"));//配送地址
						qishouOrder.put("totalSum",qishouLiShiOrderList.get(i).get("totalSum"));//合计总价
						qishouOrder.put("paySum",qishouLiShiOrderList.get(i).get("paySum"));//实际支付金额
						qishouOrder.put("payMethod",qishouLiShiOrderList.get(i).get("payMethod"));//支付方式
						//把最新的结果放入到List中
						resultList.add(qishouOrder);
					}
					result = "01";
					respMsg="成功！";
					map.put("respCode", result);
					map.put("resultList", resultList);
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 获取历史订单（异常订单）
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryLiShiOrderStateQishouCancel")
		@ResponseBody
		public Object queryLiShiOrderStateQishouCancel()throws Exception{
			logBefore(logger, "---获取历史订单（异常订单）--");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}
					pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
					List<PageData> qishouLiShiOrderList = qishouService.queryLiShiOrderStateQishouCancel(pd);//获取历史订单（异常订单）
					List<PageData>  resultList = new ArrayList<PageData>();
					if (qishouLiShiOrderList.size()==0) {
						result = "01";
						respMsg="暂时（异常订单）数据";
						map.put("respCode", result);
						map.put("resultList", resultList);
					}else{
						for(int i=0;i<qishouLiShiOrderList.size();i++){
							PageData qishouOrder = new PageData();
							qishouOrder.put("order_takeou_id",qishouLiShiOrderList.get(i).get("order_takeou_id"));//订单主键id
							qishouOrder.put("shopName",qishouLiShiOrderList.get(i).get("shopName"));//取单门店地址名
							qishouOrder.put("qucan_number",qishouLiShiOrderList.get(i).get("qucan_number"));//取餐编号,标识+数字(S12)
							qishouOrder.put("peisongfei",qishouLiShiOrderList.get(i).get("peisongfei"));//当前订单的配送费
							qishouOrder.put("order_remark",qishouLiShiOrderList.get(i).get("order_remark"));//订单备注(要求，口味等)
							qishouOrder.put("orderTime",  qishouLiShiOrderList.get(i).get("orderTime"));//下单时间
							qishouOrder.put("address",qishouLiShiOrderList.get(i).get("address"));//配送地址
							qishouOrder.put("totalSum",qishouLiShiOrderList.get(i).get("totalSum"));//合计总价
							qishouOrder.put("paySum",qishouLiShiOrderList.get(i).get("paySum"));//实际支付金额
							qishouOrder.put("payMethod",qishouLiShiOrderList.get(i).get("payMethod"));//支付方式
							//把最新的结果放入到List中
							resultList.add(qishouOrder);
						}
						result = "01";
						respMsg="成功";
						map.put("respCode", result);
						map.put("resultList", resultList);
					}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
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
			String resprespMsg = "";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))||
						Tools.isEmpty(pd.getString("phone"))||
						Tools.isEmpty(pd.getString("securityCode"))||
						Tools.isEmpty(pd.getString("yanzhengma"))){
					respCode = "00";
					resprespMsg="请求参数[backCode;phone;securityCode;yanzhengma]不能为空！";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if (cacheData == null) {
						respCode = "00";
						resprespMsg="缓存信息失效,请重新登录！";
					}else
					if(!pd.getString("securityCode").equals(pd.getString("yanzhengma"))){ 
						respCode = "00";
						resprespMsg = "验证码不正确";
					}else {
						//查询电话号码是否重复，即该用户是否注册过
						PageData pds = this.qishouService.findByPhone(pd);
						if(pds!=null){
							respCode="00";
							resprespMsg="换绑失败,该手机号已存在!";
						}else{
							//换绑新的手机号
							pd.put("user_qishou_id", cacheData.getString("user_qishou_fid"));
							qishouService.updateUserphone(pd);
							qishouService.deleteCacheData(pd);//清除缓存信息
							respCode = "01";
							resprespMsg="成功";
						}
					}
				}
				
			}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString(), e);
			}
			map.put("respCode", respCode);
			map.put("respMsg", resprespMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
	
		/**
		 * 获取新任务，待取货，待送达，已送达，异常订单，系统消息未读的总数
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryCount")
		@ResponseBody
		public Object queryCount(HttpServletRequest request)throws Exception{
			logBefore(logger, "---获取新任务，待取货，待送达，已送达，异常订单，系统消息未读的总数---");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						respMsg="缓存信息失效,请重新登录";
					}else{
						pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
						PageData Count = qishouService.queryCount(pd);//获取新任务，待取货，待送达，已送达，异常订单，系统消息未读的总数
						result = "01";
						map.put("respCode", result);
						map.put("resultList", Count);
						respMsg="成功";
					}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		
		/**
		 * 我的收入
		 * @throws Exception 
		 */
		@RequestMapping(value="/queryMywallet")	
		@ResponseBody
		public Object queryMywallet(HttpServletRequest request)throws Exception{
			logBefore(logger, "---我的收入---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			PageData pdresult= new PageData();
			String respMsg = "失败！";
			String result = "00";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					map.put("respCode", result);
					respMsg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						respMsg="缓存信息失效,请重新登录";
						}else {
							pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
							//今日总收入
							PageData qishouincome = qishouService.queryQiShouIncomeToday(pd);
							//我的账户余额 、今日收入、总资产
							PageData Accounts = qishouService.queryAccountbalance(pd);
							//我的账户余额
							String a = qishouincome.get("sum").toString();//从订单表合计的今日收入
							String s = Accounts.get("Amount").toString();//我的账户余额
							String c = Accounts.get("Incometoday").toString();//我的今日总额收入
							String d = Accounts.get("totalassets").toString();//我的总资产
							
							double Incometodays =Double.parseDouble(c);//我的今日收入类型转换
							double IncometodaySum = Double.parseDouble(a);//从订单表合计的今日收入类型转换
							if (IncometodaySum == 0) {
								double jiri = IncometodaySum - Incometodays*0;
								System.out.println(jiri);
								double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
								pd.put("Incometoday", jiri);//今日收入
								pd.put("Amount", Accountsum);//我的账户余额
								pd.put("totalassets", Accountsum);//总资产
								qishouService.setMywallet(pd);
								result = "01";
								map.put("respCode", result);
								respMsg="计算成功";
							}else{
								if (Incometodays > IncometodaySum) {
// 								double jiri = Incometodays - IncometodaySum;
									double Accountsum = IncometodaySum +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
									double jiris = Incometodays + IncometodaySum;
									pd.put("Incometoday", jiris);//今日收入
									pd.put("Amount", Accountsum);//我的账户余额
									pd.put("totalassets", Accountsum);//总资产
									qishouService.setMywallet(pd);//存入账户余额和今日收入
									result = "01";
									map.put("respCode", result);
									respMsg="计算成功";
								}else{
									if (!Accounts.get("Incometoday").equals(IncometodaySum)) {
//								double jiri = yuanshi - today;
										double jiri = IncometodaySum - Incometodays;
										double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
										double jiris = Incometodays + jiri;
										pd.put("Incometoday", jiris);//今日收入
										pd.put("Amount", Accountsum);//我的账户余额
										pd.put("totalassets", Accountsum);//总资产
										qishouService.setMywallet(pd);//存入账户余额和今日收入
										result = "01";
										map.put("respCode", result);
										respMsg="计算成功";
									}else{
										if (IncometodaySum > Incometodays) {
											double jiri = IncometodaySum - Incometodays;
											double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
											double jiris = Incometodays + jiri;
											pd.put("Incometoday", jiris);//今日收入
											pd.put("Amount", Accountsum);//我的账户余额
											pd.put("totalassets", Accountsum);//总资产
											qishouService.setMywallet(pd);//存入账户余额和今日收入
											result = "01";
											map.put("respCode", result);
											respMsg="计算成功";
										}
										double jiri = IncometodaySum - Incometodays;
										if (!Accounts.get("Incometoday").equals(jiri)) {//判断今日收入金额是否相等
											double Accountsum = jiri +  Double.parseDouble(s);//我的账户余额 = 今日收入 + 账户余额
											pd.put("Incometoday", Incometodays);//今日收入
											pd.put("Amount", Accountsum);//我的账户余额
											pd.put("totalassets", Accountsum);//总资产
											qishouService.setMywallet(pd);//存入账户余额和今日收入
											result = "01";
											map.put("respCode", result);
											respMsg="计算成功";
										}else{
											result = "00";
											map.put("respCode", result);
											respMsg="失败";
										}
										
									}
								}
							}
							pd.put("phone", cacheData.getString("phone"));
							PageData GeRZL = qishouService.queryGeRZL(pd);
							pdresult.put("Amount", GeRZL.get("Amount"));//我的账户余额
							pdresult.put("Incometoday", GeRZL.get("Incometoday"));//今日收入
							pdresult.put("totalassets", GeRZL.get("totalassets"));//总资产
							pdresult.put("goExplain", BaseController.getPath(request)+"api/shangjia/goExplain.do");//提现说明
							result = "01";
							map.put("respCode", result);
							map.put("resultList", pdresult);
							respMsg="计算成功";
							
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
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
			String respMsg = "";
			String result = "00";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					map.put("respCode", result);
					respMsg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						respMsg="缓存信息失效,请重新登录";
					}else{
						
						pd.put("phone", cacheData.getString("phone"));
						pd.put("user_fid", cacheData.getString("user_qishou_fid"));
						PageData queryGeRZL = qishouService.queryGeRZL(pd);
						List<PageData> List = qishouService.queryBankCardList(pd);//我的银行卡管理列表
						if (List.size() == 0) {
							result = "01";
							map.put("respCode", result);
							respMsg="您还未添加银行卡，暂时无数据";
							map.put("resultList", List);
							map.put("payPwdStatus",queryGeRZL.getString("payPwdStatus"));//返回支付密码状态（0-未设置，1-已设置）
						}else{
							result = "01";
							respMsg="成功!";
							map.put("respCode", result);
							map.put("resultList", List);
							map.put("payPwdStatus",queryGeRZL.getString("payPwdStatus"));//返回支付密码状态（0-未设置，1-已设置）
						}
					}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", respMsg);
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
					map.put("respCode", result);
					msg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						msg="缓存信息失效,请重新登录";
						}
						if (Tools.isEmpty(pd.getString("bank_card_id"))) {
							result = "00";
							map.put("respCode", result);
							msg="bank_card_id（银行卡id）请求参数不能为空";
						}else{
							qishouService.unbundlingCardNumber(pd);//解绑
							result = "01";
							map.put("respCode", result);
							msg="解绑成功";
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", msg);
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
			String respMsg = "";
			String result = "00";
			String bankName = pd.getString("bankName");//银行卡的类型(所属银行)
			String cardNumber = pd.getString("cardNumber");//银行卡号
			String userName = pd.getString("userName");//姓名
			String phone = pd.getString("phone");//手机号码
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					map.put("respCode", result);
					respMsg="backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						respMsg="缓存信息失效,请重新登录";
					}else{
						if(Tools.isEmpty(bankName)||Tools.isEmpty(cardNumber)|| Tools.isEmpty(userName)|| Tools.isEmpty(phone)){
								result = "00";
								map.put("respCode", result);
								respMsg="请求协议参数不能为空";
						}else{
//								if (!cacheData.getString("phone").equals(pd.getString("phone"))) {//判断手机号码是否匹配
//									result = "00";
//									map.put("respCode", result);
//									respMsg="请您输入本人手机号码";
//								}else{
									cacheData.put("phone", cacheData.getString("phone"));
									PageData queryGeRZL = qishouService.queryGeRZL(pd);//查询姓名是否匹配
									if (!queryGeRZL.getString("realName").equals(pd.getString("userName"))) {
										result = "00";
										map.put("respCode", result);
										respMsg="请您输入本人真实的姓名";
									}else{
										PageData BankCard = qishouService.queryBankCardCardNumber(pd);//查询银行卡信息是否从复
										if (BankCard != null) {
											result = "00";
											map.put("respCode", result);
											respMsg="该银行卡号已经存在";
										}else{
											pd.put("userName", queryGeRZL.getString("realName"));//姓名
											pd.put("phone", phone);//手机号
											pd.put("bank_card_id", this.get32UUID());
											pd.put("bankName", bankName);//银行卡的类型(所属银行)
											pd.put("cardNumber", cardNumber);//银行卡号
											pd.put("user_fid", cacheData.getString("user_qishou_fid"));
											pd.put("create_time", DateUtil.getTime());//创建时间
											pd.put("status", 0);//(0-正常使用中，1-默认使用中）
											pd.put("bz", "骑手端app用户");
											qishouService.insertBankCard(pd);
											result = "01";
											map.put("respCode", result);
											map.put("payPwdStatus",queryGeRZL.getString("payPwdStatus"));//返回支付密码状态（0-未设置，1-已设置）
											respMsg="添加成功";
										}
									}
//								}
							}
					}
				}
			}catch (Exception e){
					e.printStackTrace();
					logger.error(e.toString(), e);
					map.put("respCode", result);
					respMsg="失败";
			}
			map.put("respCode", result);
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 设置支付密码
		 * @throws Exception 
		 */
		@RequestMapping(value="/setzhifupwd")
		@ResponseBody
		public Object setzhifupwd()throws Exception{
			logBefore(logger, "---设置支付密码--");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String msg = "";
			String result = "00";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					map.put("respCode", result);
					msg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						msg="缓存信息失效,请重新登录";
						}else {
							String phone = pd.getString("phone");
							pd.put("phone", phone);	//手机号
							String pwd = pd.getString("payPassword");
							PageData pds = qishouService.findByPhone(pd);//查询判断用户是否存在
							if (Tools.isEmpty(pwd)) {
								result = "00";
								map.put("respCode", result);
								msg="（payPassword）支付密码不能为空";
							}else if(pds!=null){
								pd.put("payPassword", MD5.md5(pwd));//密码
								qishouService.updateZhiFuPwd(pd);//修改密码
								result = "01";
								map.put("respCode", result);
								msg="支付密码已设置成功";
							}else{
								result = "00";
								map.put("respCode", result);
								msg = "该手机号不是支付手机号!";
							}
						}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respMsg", msg);
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
			String payPassword = pd.getString("payPassword");//提现密码
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					map.put("respCode", result);
					msg="backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						msg="缓存信息失效,请重新登录";
					}else{
						if(Tools.isEmpty(zhichu_amount)||Tools.isEmpty(cardNumber)){
								result = "00";
								map.put("respCode", result);
								msg="请求参数不能为空";
						}else{
								pd.put("phone", cacheData.getString("phone"));
								PageData BankCard = qishouService.queryBankCardCardNumber(pd);//查询银行卡信息是否匹配
								if (BankCard == null) {
									result = "00";
									map.put("respCode", result);
									msg="选择正确的银行卡";
								}else{
									pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
									PageData Account = qishouService.queryAccountbalance(pd);//我的账户余额
									String s = Account.get("Amount").toString();//我的账户余额
									String e = pd.get("zhichu_amount").toString();//提现余额
									double Amount = Double.parseDouble(s);
									double zhichuamount = Double.parseDouble(e);//提现金额
									System.out.println(zhichuamount);
									
									if (zhichuamount >= Amount) {//判断提现金额是否超过账户余额
										result = "00";
										map.put("respCode", result);
										msg="金额已超过可提现余额";
									}else{
										String payPwd="";
										payPwd = MD5.md5(payPassword); //密码加密
										PageData payPasswords = qishouService.queryGeRZL(pd);//查询密码
										if (!payPasswords.getString("payPassword").equals(payPwd)) {//判断提现密码是否匹配
											result = "00";
											map.put("respCode", result);
											msg="支付密码错误";
										}else{
											double zhichuamounts = Amount - zhichuamount;//账户金额 = 账户余额 - 支出金额
											pd.put("serial_number", this.getNumberForPK());//流水号
											pd.put("bank_card_tixian_id", this.get32UUID());
											pd.put("tixian_type", "提现");//类型
											pd.put("zhichu_amount", zhichuamount);//提现金额
											pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));//骑手外键id
											pd.put("bank_card_fid", BankCard.getString("bank_card_id"));//银行卡外键id
											pd.put("bankName", BankCard.getString("bankName"));//银行卡的类型(所属银行)
											pd.put("cardNumber", BankCard.getString("cardNumber"));//银行卡号
											pd.put("phone", BankCard.getString("phone"));//提现手机号码
											pd.put("realName", BankCard.getString("realName"));//提现姓名
											pd.put("tixian_time", DateUtil.getTime());//提现时间
											pd.put("bank_card_status", "受理中");//提现受理状态(0-正在受理中，1-已受理完成)
											pd.put("Amount", zhichuamounts);//我的账户余额
											qishouService.insertWithdrawCash(pd);//提取现金
											
											pd.put("totalassets", zhichuamounts);//总资产
											qishouService.setAccountAndAssets(pd);//存入计算出，提现后的剩下的余额
											result = "01";
											map.put("respCode", result);
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
					map.put("respCode", result);
					msg="失败";
			}
			map.put("respCode", result);
			map.put("respMsg", msg);
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
					map.put("respCode", result);
					msg="操作失败，请先登录，backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						msg="缓存信息失效,请重新登录";
					}else{
						pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));
						List<PageData> List = qishouService.queryBillingDetailsList(pd);//我的账单明细列表
						if (List.size() == 0) {
							result = "01";
							msg = "暂时无数据";
							map.put("respCode", result);
							map.put("resultList", List);
						}else {
							result = "01";
							msg="成功!";
							map.put("respCode", result);
							map.put("resultList", List);
						}
					}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respCode", result);
			map.put("respMsg", msg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 上报异常
		 * @throws Exception 
		 */
		@RequestMapping(value="/insertReportingAnomalies")
		@ResponseBody
		public Object insertReportingAnomalies(){
			logBefore(logger, "---上报异常---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String respMsg = "";
			String result = "00";
			try {
				if(Tools.isEmpty(pd.getString("backCode"))){
					result = "00";
					map.put("respCode", result);
					respMsg="backCode不能为空";
				}else{
					//根据backCode获取缓存信息
					PageData cacheData = qishouService.getDataByBackCode(pd);
					if(cacheData==null){
						result = "00";
						map.put("respCode", result);
						respMsg="缓存信息失效,请重新登录";
					}else{
						if(Tools.isEmpty(pd.getString("reporting_anomalies_reason"))||Tools.isEmpty(pd.getString("reporting_anomalies_content"))||Tools.isEmpty(pd.getString("order_takeou_id"))){
								result = "00";
								map.put("respCode", result);
								respMsg="请求协议参数不能为空";
						}else{
								pd.put("reporting_anomalies_id", this.get32UUID());
								pd.put("user_qishou_fid", cacheData.getString("user_qishou_fid"));//骑手外键id
								pd.put("reporting_anomalies_reason", pd.getString("reporting_anomalies_reason"));//异常原因
								pd.put("reporting_anomalies_content", pd.getString("reporting_anomalies_content"));//异常详细内容
								pd.put("order_takeou_fid", pd.getString("order_takeou_id"));//异常订单id
								pd.put("create_time", DateUtil.getTime());//创建时间
								qishouService.insertReportingAnomalies(pd);
								qishouService.setorderStateQishou(pd);//设置申报异常状态
								result = "01";
								map.put("respCode", result);
								respMsg="申报成功!";
							}
					}
				}
			}catch (Exception e){
					e.printStackTrace();
					logger.error(e.toString(), e);
					map.put("respCode", result);
					respMsg="失败";
			}
			map.put("respCode", result);
			map.put("respMsg", respMsg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 系统消息
		 * @throws Exception 
		 */
		@RequestMapping(value="/systemMessage")
		@ResponseBody
		public Object systemMessage()throws Exception{
			logBefore(logger, "---系统消息---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String msg = "";
			String result = "00";
			try {
				List<PageData> List = qishouService.systemMessage(pd);
				result = "01";
				msg="成功!";
				map.put("resultList", List);
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respCode", result);
			map.put("respMsg", msg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 设置是否已读状态
		 * @throws Exception 
		 */
		@RequestMapping(value="/setStatus")
		@ResponseBody
		public Object setStatus()throws Exception{
			logBefore(logger, "---设置是否已读状态---");
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String msg = "失败";
			String result = "00";
			try {
				PageData sData = qishouService.querySysMessageQishou(pd);
				if (sData == null) {
					result = "00";
					map.put("respCode", result);
					msg="消息(sys_message_id)出错啦!";
				}else{
					if (Tools.isEmpty(pd.getString("sys_message_id"))) {
						result = "00";
						map.put("respCode", result);
						msg="请求协议参数不能为空";
					}else {
						qishouService.setStatus(pd);
						result = "01";
						msg="设置已读成功!";
					}
				}
			}catch (Exception e){
				logger.error(e.toString(), e);
				map.put("respCode", result);
				map.put("respMsg", "失败");
				map.put("respMsg", e.getMessage());
			}
			map.put("respCode", result);
			map.put("respMsg", msg);
			logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
			return AppUtil.returnObject(pd, map);
		}
		
		/**
		 * 系统消息添加页面
		 * @return
		 */
		@RequestMapping(value="/goSysMessage")
		public ModelAndView goSysMessage(){
			logBefore(logger, "------系统消息添加页面-----");
			ModelAndView mv=new ModelAndView();
			mv.addObject("msg", "saveinsert");
			mv.setViewName("information/qiShou/xiangq");
			return mv;
		}
		
		
		
		/**
		 * 骑手端更新设备标识ID
		 * 功能：
		 * 作者：lj
		 * 日期：2017-8-10
		 */
		@RequestMapping(value="/updateQishouRegistrationID")
		@ResponseBody
		public Object updateQishouRegistrationID(HttpServletRequest request)throws Exception{
			logBefore(logger, "---骑手端更新设备标识ID---");
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
					PageData cacheData = qishouService.getDataByBackCode(pd);
					pd.put("RegistrationID", pd.get("RegistrationID"));
					pd.put("RegistrationType", pd.get("RegistrationType"));//设备类型
					pd.put("user_qishou_id", cacheData.getString("user_qishou_fid"));
					qishouService.updateQishouRegistrationID(pd);
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
		
		/**
		 * 骑手提现说明
		 * @return
		 */
		@RequestMapping(value="/goExplain")
		public ModelAndView goExplain(){
			logBefore(logger, "------骑手提现说明-----");
			ModelAndView mv=new ModelAndView();
			mv.addObject("msg", "saveinsert");
			mv.setViewName("information/qishou/tixian");
			return mv;
		}
}
