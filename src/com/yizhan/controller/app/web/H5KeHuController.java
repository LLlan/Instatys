package com.yizhan.controller.app.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.net.aso.s;

import org.apache.james.mime4j.field.datetime.DateTime;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.yizhan.controller.app.jpushModel.JpushClientUtil;
import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.entity.information.KeHu;
import com.yizhan.service.information.h5kehu.H5KeHuService;
import com.yizhan.service.information.h5sys.h5sysService;
import com.yizhan.service.information.keHu.KeHuService;
import com.yizhan.service.information.orderChangTu.OrderChangtuService;
import com.yizhan.service.information.sijiInformationChangTu.SijiInformationChangTuService;
import com.yizhan.service.information.sijiOrderTongCheng.SijiOrderTongChengService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.Const;
import com.yizhan.util.DateTimeUtil;
import com.yizhan.util.DateUtil;
import com.yizhan.util.FileUpload;
import com.yizhan.util.LocationUtils;
import com.yizhan.util.MD5;
import com.yizhan.util.PageData;
import com.yizhan.util.PathUtil;
import com.yizhan.util.SmsUtil;
import com.yizhan.util.SortUtil;
import com.yizhan.util.Tools;

@Controller
@RequestMapping("/api/h5KeHu")
public class H5KeHuController extends BaseController {
	
	@Resource(name="h5KeHuService")
	private H5KeHuService h5KeHuService;
	
	@Resource(name="sijiInformationChangTuService")
	private SijiInformationChangTuService sijiInformationChangTuService;
	
	@Resource(name="sijiOrderTongChengService")
	private SijiOrderTongChengService sijiOrderTongChengService;
	
	@Resource(name="orderChangtuService")
	private OrderChangtuService orderChangtuService;
	
	@Resource(name="keHuService")
	private KeHuService keHuService;
	
	@Resource(name="h5sysService")
	private h5sysService h5sysService;
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value="/toLogin")
	public ModelAndView tologin(){
		logBefore(logger, "---toLogin---登录页面-----");
		ModelAndView mv = new  ModelAndView();
		mv.setViewName("kehuduan/login");
		return mv;
	}
	
	/**
	 * 执行登录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Object login(HttpSession session,HttpServletRequest request) throws Exception{
		logBefore(logger, "--执行登录--");
		Map<String, Object> map=new HashMap<String, Object>();
		PageData pd=new PageData();
		pd=this.getPageData();
		PageData tempPd=new PageData();
		String respCode = "";
		String respMsg="";
		if(isNumeric(pd.getString("login_phone"))){
			tempPd.put("phone", pd.getString("login_phone"));
		}
		tempPd.put("loginPassword", MD5.md5(pd.getString("loginPassword")));
		PageData tempData=h5KeHuService.getDataByNameAndPaw(tempPd);
		if(tempData!=null){
			respCode="01";
			respMsg="恭喜您登录成功!";
			//创建session
			//createSession(tempData.getString("user_shanghu_id"));
			KeHu kehu=new KeHu();
			kehu.setUser_kehu_id(tempData.getString("user_kehu_id"));
			kehu.setPhone(tempData.getString("phone"));
			session.setAttribute("h5User",kehu);
			//更新登录时间和登录IP
			PageData temp=new PageData();
			temp.put("last_login_time", DateUtil.getTime());//最近登陆时间	
			temp.put("ip", request.getRemoteHost());//ip地址	
			temp.put("user_kehu_id", tempData.getString("user_kehu_id"));
			h5KeHuService.updateLoginTimeAndIp(temp);
		}else {
			respCode="00";
			respMsg="您的账号或密码出错！";
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 进入注册页面
	 * @return
	 */
	@RequestMapping(value="/toRegister")
	public ModelAndView toRegister(){
		logBefore(logger, "--进入注册页面--");
		ModelAndView mv=new ModelAndView();
		mv.setViewName("kehuduan/zhuce");
		return mv;
	}
	
	/**
	 * 获取短信验证码
	 * @return
	 */
	@RequestMapping(value="/getSms")
	@ResponseBody
	public Object getSms(){
		logBefore(logger, "--获取短信验证码--");
		PageData pd = new PageData();
		pd= this.getPageData();
		Map<String,String> map = new HashMap<String,String>();
		String phone = pd.getString("phone");
		map = SmsUtil.sendMsM(phone);
		map.put("phone", phone);
		if(map.size()>0){
			map.put("reqCode", "01");
		}else{
			map.put("reqCode", "00");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 注册
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object register(HttpServletRequest request) throws Exception{
		logBefore(logger, "--注册--");
		Map<String, Object> map=new HashMap<String, Object>();
		PageData pd=new PageData();
		pd=this.getPageData();
		PageData pdPhone=new PageData();
		pdPhone.put("phone", pd.getString("phone"));
		//根据用户名或者手机号查询对象信息
		PageData tempPhone=h5KeHuService.getDataByNameOrPhone(pdPhone);
		String respCode = "";
		String respMsg="";
			if(tempPhone!=null){//该手机号已经注册
				respCode="00";
				respMsg="该账号已经存在！";
			}else{
				respCode="01";
				respMsg="恭喜您注册成功！";
				pd.put("user_kehu_id", this.get32UUID());	//主键
				pd.put("phone", pd.getString("phone"));	
				pd.put("loginPassword", MD5.md5(pd.getString("loginPassword")));	
//				pd.put("headImg","");	
				pd.put("userName","用户"+DateUtil.getTimeStamp());
//				pd.put("payPassword","");
				pd.put("registerTime", DateUtil.getTime());//注册时间	
				pd.put("last_login_time",DateUtil.getTime());//最近登陆时间	
				pd.put("ip", request.getRemoteHost());//ip地址
				pd.put("status", 1); //1 使用中 0 已停用
				pd.put("bz", "K5版客户端用户");	
				h5KeHuService.saveU(pd);
			}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 *  忘记密码
	 * @return
	 */
	@RequestMapping(value="/wangjimima")
	public ModelAndView wangjimima(){
		logBefore(logger, "--忘记密码--");
		ModelAndView mv=new ModelAndView();
		mv.setViewName("kehuduan/user/psd_chage");
		return mv;
	}
	
	/**
	 * 执行修改密码
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/changepwd")
	@ResponseBody
	public Object changepwd(HttpSession session,HttpServletRequest request) throws Exception{
		logBefore(logger, "--执行修改密码--");
		Map<String, Object> map=new HashMap<String, Object>();
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		String respCode = "";
		String respMsg = "";
		PageData tempPd=new PageData();
		PageData pdPhone=new PageData();
		pdPhone.put("phone", pd.getString("phone"));
		//根据手机号查询对象信息
		PageData tempPhone=h5KeHuService.getDataByNameOrPhone(pdPhone);
		if (tempPhone!=null) {
			tempPd.put("phone", pd.getString("phone"));
			tempPd.put("loginPassword", MD5.md5(pd.getString("newPassword")));
			h5KeHuService.updatePasswordByPhone(tempPd);
			respCode="01";
			respMsg="密码修改成功！";
		}else{
			respCode="00";
			respMsg="该账号还未注册，请您去注册！";
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 *  长途发布路线
	 * @return
	 */
	@RequestMapping(value="/fabuNews")
	public ModelAndView fabuNews(){
		logBefore(logger, "--fabuNews---长途发布路线表单页面--");
		ModelAndView mv=new ModelAndView();
		if(isSession()){
			mv.addObject("msg", "savefabuNews");
			mv.setViewName("kehuduan/taxi/fabuNews");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 保存长途发布路线
	 * @throws Exception
	 */
	@RequestMapping(value="/savefabuNews")
	public ModelAndView savefabuNews(HttpSession session) throws Exception{
		logBefore(logger, "---savefabuNews--保存长途发布路线--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String userName = pd.getString("userName");	
		String phone = pd.getString("phone");	
		String departureTime = pd.getString("departureTime");	
		String departurePlace = pd.getString("departurePlace");	
		String destination = pd.getString("destination");	
		String carpoolFee = pd.getString("carpoolFee");	
		String userNum = pd.getString("userNum");	
		String departureCity = pd.getString("departureCity");	
		String arrivalCity = pd.getString("arrivalCity");	
		KeHu kehu = (KeHu) session.getAttribute("h5User");
		if (userName!=null && 
				phone!=null && 
					departureTime!=null &&
						departurePlace !=null && 
							destination !=null &&
								carpoolFee !=null &&
									userNum !=null &&
										departureCity !=null &&
											arrivalCity !=null) {
			pd.put("information_kehu_changtu_id", this.get32UUID());
			pd.put("releaseTime",DateUtil.getTime());//发布时间
			pd.put("userName",userName);//乘客姓名
			pd.put("phone",phone);//手机号码
			pd.put("departureTime", departureTime.substring(0, 16));//出发时间	
//			pd.put("departureTime", DateUtil.getTime());//出发时间	
			pd.put("userNum",userNum);//可乘人数
			pd.put("departureCity",departureCity);//出发城市
			pd.put("departurePlace",departurePlace);//出发点
			pd.put("arrivalCity",arrivalCity);//目的城市
			pd.put("destination",destination);//目的地
			pd.put("carpoolFee",carpoolFee);//拼车费
			pd.put("status",0);//拼车费
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			h5KeHuService.savefabuctlx(pd);
			mv.setViewName("redirect:/api/h5KeHu/wdfbchangtuCarList");
		}
		return mv;
	}
	
	
	/**
	 *  会员资料页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/ziliao")
	public ModelAndView ziliao(HttpSession session,HttpServletRequest request) throws Exception{
		logBefore(logger, "--ziliao---会员资料页面--");
		ModelAndView mv=new ModelAndView();
		if(isSession()){
			PageData pd= new PageData();
			pd = this.getPageData();
			KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			PageData pds = h5KeHuService.getDataById(pd);
//			pdData.put("headImg", BaseController.getPath(request)+pds.getString("headImg"));
//			pdData.put("userName", pds.getString("userName"));
//			pdData.put("phone", pds.getString("phone"));
			mv.addObject("msg", "saveziliao");
			mv.addObject("pds", pds);
			mv.setViewName("kehuduan/ziliao");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 去修改头像页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/gotouxiang")
	public ModelAndView gotouxiang(HttpSession session,HttpServletRequest request) throws Exception{
		logBefore(logger, "--gotouxiang---去修改头像页面--");
		ModelAndView mv=new ModelAndView();
		if(isSession()){
			PageData pd= new PageData();
			pd = this.getPageData();
			KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			PageData pds = h5KeHuService.getDataById(pd);
//			pdData.put("headImg", BaseController.getPath(request)+pds.getString("headImg"));
			mv.addObject("pds", pds);
			mv.addObject("msg", "savetouxiang");
			mv.setViewName("kehuduan/touxiang");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	
	/**
	 * 上传与修改头像
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savetouxiang")
	public ModelAndView savetouxiang(HttpSession session,
			@RequestParam(required = false) MultipartFile imgFile
			) throws Exception{
		logBefore(logger, "--savetouxiang---上传与修改头像--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		String TOUXIANG = "touxiang/";	
		String  ffile = DateUtil.getDays(), headImgURLName = "";
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			if (null != imgFile && !imgFile.isEmpty()){
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + TOUXIANG + ffile;//文件上传路径
				headImgURLName = FileUpload.fileUp(imgFile,filePath, this.get32UUID());	//执行上传
				pd.put("imgFile", Const.FILEPATHIMG + TOUXIANG + ffile + "/" + headImgURLName);
				System.out.println(pd.getString("imgFile"));
				pd.put("user_kehu_id", kehu.getUser_kehu_id());
				h5KeHuService.touxiang(pd);
				mv.addObject("respCode", "01");
			}else{
				mv.addObject("respCode", "00");
			}
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		mv.setViewName("redirect:/api/h5KeHu/ziliao");
		return mv;
	}
	
	/**
	 * 去修改用户名页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goyonghuming")
	public ModelAndView goyonghuming(HttpSession session) throws Exception{
		logBefore(logger, "---goyonghuming-----去修改用户名页面------");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
			PageData pd= new PageData();
			pd = this.getPageData();
			KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			PageData pds = h5KeHuService.getDataById(pd);
			mv.addObject("pds", pds);
			mv.addObject("msg", "saveyonghuming");
			mv.setViewName("kehuduan/yonghuming");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 执行修改用户名
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveyonghuming")
	@ResponseBody
	public ModelAndView saveyonghuming(HttpSession session) throws Exception{
		logBefore(logger, "--saveyonghuming--执行修改用户名-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		String userName=pd.getString("userName");
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			pd.put("userName", userName);
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			h5KeHuService.saveyonghuming(pd);//修改用户名
		}else {
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		mv.setViewName("redirect:/api/h5KeHu/ziliao");
		return mv;
	}
	
	
	/**
	 * 换绑旧手机号页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/changeTel")
	public ModelAndView changeTelss() throws Exception{
		logBefore(logger, "---changeTel----换绑旧手机号页面----");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
			mv.setViewName("kehuduan/user/changeTel");
		}
		return mv;
	}
	
	
	/**
	 * 执行查询是否存在账号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/yanzhengPhone")
	@ResponseBody
	public Object yanzhengPhone() throws Exception{
		logBefore(logger, "------yanzhengPhone------");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("phone", pd.getString("phone"));
		PageData pds = this.h5KeHuService.findByPhone(pd);
		if(pds!=null){//判断“旧手机号与新手机号”是否存在可以执行换绑新的手机号了。
			map.put("respCode", "01");
			System.out.println("Object:账号存在可以换绑新手机号");
		}else {//“旧手机号与新手机号”否则号码出错啦！
			map.put("respCode", "00");
			System.out.println("Object:账号不存在，还没注册");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 换绑新手机号页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/newTel")
	public ModelAndView newTel() throws Exception{
		logBefore(logger, "---newTel----换绑新手机号页面----");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
				mv.setViewName("kehuduan/user/newTel");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 执行换绑新手机号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savexgphone")
	@ResponseBody
	public ModelAndView savexgphone(HttpSession session) throws Exception{
		logBefore(logger, "--savexgphone--执行换绑新手机号-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		String phone=pd.getString("phone");
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
				pd.put("phone", phone);
				pd.put("user_kehu_id", kehu.getUser_kehu_id());
				h5KeHuService.updatephone(pd);//执行修改手机号
		}else {
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		mv.setViewName("redirect:/api/h5KeHu/toLogin");
		return mv;
	}
	
	
	/**
	 * 支付密码设置验证页面
	 * @return
	 */
	@RequestMapping(value="/changePsds")
	public ModelAndView changePsds(){
		logBefore(logger, "---changePsds----支付密码设置验证页面----");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
			mv.addObject("msg", "changePsd");
			mv.setViewName("kehuduan/user/changePsds");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 输入支付密码设置页面
	 * @return
	 */
	@RequestMapping(value="/changePsd")
	public ModelAndView changePsd(){
		logBefore(logger, "---changePsd----支付密码设置页面----");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
			mv.addObject("msg", "changePsws");
			mv.setViewName("kehuduan/user/changePsd");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 再次输入支付密码设置页面
	 * @return
	 */
	@RequestMapping(value="/changePsws")
	public ModelAndView changePsws(HttpSession session){
		logBefore(logger, "---changePsws----再次输入支付密码设置页面----");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		String pwd = pd.getString("a")+pd.getString("b")+pd.getString("c")+pd.getString("d")+pd.getString("e")+pd.getString("f");
		System.out.println("----------password----------"+pwd);
		System.out.println(pd.put("pwd", MD5.md5(pwd)));
		if(isSession()){
			mv.addObject("msg", "savechangePsws");
			mv.setViewName("kehuduan/user/changePsws");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 执行支付密码设置
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/savechangePsws")
	public ModelAndView savechangePsws(HttpSession session) throws Exception{
		logBefore(logger, "---savechangePsws----执行支付密码设置----");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		String pwd = pd.getString("a")+pd.getString("b")+pd.getString("c")+pd.getString("d")+pd.getString("e")+pd.getString("f");
		System.out.println("----------password----------"+pwd);
		System.out.println(pd.put("pwd", MD5.md5(pwd)));
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			pd.put("payPassword", MD5.md5(pwd));//加密
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			h5KeHuService.updatepayPassword(pd);//设置支付密码
			mv.setViewName("redirect:/api/h5KeHu/ziliao");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	
	/**
	 * 美食外卖商家页面列表(销售量)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/shops")
	public ModelAndView shops() throws Exception{
		logBefore(logger, "---shops--美食外卖商家页面列表(销售量)---");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			//删除临时订单
			pd.put("user_kehu_id", getSessionUser().getUser_kehu_id());
			h5KeHuService.delTempOrder(pd);
			System.out.println("------返回就删除临时表成功------");
			
			pd.put("currentMonth", DateTimeUtil.formateYearAndMonth(new Date()));
			List<PageData> ShangjiaList = h5KeHuService.queryUserShangjiaList(pd);
			mv.addObject("pd",ShangjiaList);
			mv.setViewName("kehuduan/shopcart/shops");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
	
		return mv;
	}
	
	/**
	 * 美食外卖商家页面列表(距离最近)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/shopsTwo")
	public ModelAndView shopsTwo() throws Exception{
		logBefore(logger, "---shopsTwo--美食外卖商家页面列表(距离最近)---");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> ShangjiaList = h5KeHuService.queryUserShangjiaList(pd);
		mv.addObject("pd",ShangjiaList);
		mv.setViewName("kehuduan/shopcart/shopsTwo");
		return mv;
	}
	
	
	/**
	 * 去商家详情页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/shop")
	public ModelAndView shop() throws Exception{
		logBefore(logger, "--shop---去商家详情页面---");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		PageData pds = new PageData();
		pd = this.getPageData();
		List reseltlist = new ArrayList();
		
		mv.addObject("user_shangjia_id", pd.getString("user_shangjia_id"));
		pd.put("user_shangjia_id", pd.getString("user_shangjia_id"));
		pd.put("categoryName", pd.getString("categoryName"));
		pd.put("yearsAndmonth", DateTimeUtil.formateYearAndMonth(new Date()));
		System.out.println(DateTimeUtil.formateYearAndMonth(new Date())+"****************");
		
		//获取商家信息
		PageData sData = h5KeHuService.queryUserShangjia(pd);
		
		//查出所有商品种类
		List<PageData> goodsCategoryList = h5KeHuService.goodsCategoryList(pd);
		
		for(PageData pdselect:goodsCategoryList){
			//查本月销售量
			pdselect.put("currentMonth", DateTimeUtil.formateYearAndMonth(new Date()));
			//查出某个商家下的所有商品 by yym 
			List<PageData> goodsList =  h5KeHuService.getDataByCategoryNameAndId(pdselect);
			reseltlist.add(goodsList);
		}
		
		mv.addObject("tid", pd.get("tid"));
		mv.addObject("pd", sData);
		mv.addObject("goodsCategoryList", goodsCategoryList);
		mv.addObject("goodsList", reseltlist);
		pds = h5KeHuService.getqisonjiagebyID(pd);
		mv.addObject("pds", pds);
		mv.setViewName("kehuduan/shopcart/shop");
		return mv;
	}
	
	/**
	 * 去商品详情页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/shopi")
	public ModelAndView shopi() throws Exception{
		logBefore(logger, "--shopi---去商品详情页面---");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		PageData pds = new PageData();
		pd = this.getPageData();
		pd.put("goods_id", pd.getString("goods_id"));
		//去商品详情页面
		PageData sData = h5KeHuService.queryshangpingxing(pd);
		mv.addObject("pds", sData);
		mv.addObject("goodsNum", pd.getString("goodsNum"));
		mv.setViewName("kehuduan/shopcart/shopi");
		return mv;
	}
	
	/**
	 * ajax查出商品种类
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goodsCategoryList")
	@ResponseBody
	public Object goodsCategoryList() throws Exception{
		logBefore(logger, "--ajax查出商品种类--");
		PageData pd = new PageData();
		Map map = new HashMap();
		pd = this.getPageData();
		//查出所有商品种类
		List<PageData> goodsCategoryList = h5KeHuService.goodsCategoryList(pd);
		map.put("goodsCategoryList", goodsCategoryList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * ajax 请求 页面初始化 根据商品分类名字和商家id查出列表数据
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getDataByCategoryNameAndId")
	@ResponseBody
	public Object getDataByCategoryNameAndId() throws Exception{
		logBefore(logger, "--ajax根据商品分类名字和商家id查出列表数据-------");
		PageData pd = new PageData();
		Map map = new HashMap();
		pd = this.getPageData();
		List<PageData> shangpinList =  h5KeHuService.getDataByCategoryNameAndId(pd);
		map.put("shangpinList", shangpinList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	
	
	/**
	 * 商品列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goodsList")
	public ModelAndView goodsList() throws Exception{
		logBefore(logger, "---goodsList--");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		PageData pds = new PageData();
		pd = this.getPageData();
 		if(isSession()){
 			List<PageData> goodsList = h5KeHuService.goodsList(pd);
			mv.addObject("goodsList", goodsList);
			pd.put("user_shangjia_id", pd.getString("user_shangjia_fid"));
			mv.setViewName("redirect:/api/h5KeHu/shop?user_shangjia_fid="+pd.getString("user_shangjia_fid"));
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 去商家结算页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/orderInfo")
	public ModelAndView orderInfo() throws Exception{
		logBefore(logger, "--orderInfo---去商家结算页面---");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>  list = new ArrayList();
		if(isSession()){
			/*if(Tools.notEmpty(pd.getString("strs"))){
				String strs = new String(pd.getString("strs"));
				String strs1[] = strs.split(",yizhan,");
				for(int i=0;i<strs1.length;i++){
					System.out.println("------i------"+i);
					String strs2[] = strs1[i].split(",");
					for(int j=0;j<1;j++){
						System.out.println("------j------"+j);
						System.out.println("------0------"+strs2[j]);
						System.out.println("------1------"+strs2[j+1]);
						System.out.println("------2------"+strs2[j+2]);
						System.out.println("------3------"+strs2[j+3]);
						System.out.println("------4------"+strs2[j+4]);
						PageData pdinset = new PageData();
								if(list.size()>0){
										for(int y=0;y<list.size();y++){
											if(list.get(y).getString("id").equals(strs2[j].toString())){
												//移除原来的
												list.remove(y);
												//添加数量大的
												pdinset.put("id", strs2[j]);
												pdinset.put("name", strs2[j+1]);
												pdinset.put("num", strs2[j+2]);
												pdinset.put("price", strs2[j+3]);
												pdinset.put("canhefei", strs2[j+4]);
											}else{
												pdinset.put("id", strs2[j]);
												pdinset.put("name", strs2[j+1]);
												pdinset.put("num", strs2[j+2]);
												pdinset.put("price", strs2[j+3]);
												pdinset.put("canhefei", strs2[j+4]);
											}
										}	
										list.add(pdinset);
									}else{
										PageData pdinset2 = new PageData();
										pdinset2.put("id", strs2[j]);
										pdinset2.put("name", strs2[j+1]);
										pdinset2.put("num", strs2[j+2]);
										pdinset2.put("price", strs2[j+3]);
										pdinset2.put("canhefei", strs2[j+4]);
										list.add(pdinset2);
									}
							
						
							}
					
					}
			}*/
			
			/**
			 * by yym
			 * 把订单数据先暂时添加到临时订单表中 
			 */
			for(int i=0;i<list.size();i++){
				PageData pdinsert = this.getPageData();
				pdinsert.put("temp_id", this.get32UUID());
				pdinsert.put("kehu_id", getSessionUser().getUser_kehu_id());
				pdinsert.put("shangjia_id",  pd.get("user_shangjia_id"));
				pdinsert.put("sp_id", list.get(i).get("id"));
				pdinsert.put("sp_name", list.get(i).get("name"));
				pdinsert.put("price", list.get(i).get("price"));
				pdinsert.put("num", list.get(i).get("num"));
				pdinsert.put("canhefei", list.get(i).get("canhefei"));
				h5KeHuService.saveTempOrder(pdinsert);
			}
			
			//size==0是从选择地址后回来，没带回来，从库中去取
			if(list.size()==0){
				pd.put("user_kehu_id",  getSessionUser().getUser_kehu_id());
				List<PageData> resultList = h5KeHuService.selectTempOrderList(pd);
				PageData totolData = h5KeHuService.getTotolByUserKeHuID(pd);
				mv.addObject("totolData", totolData);
				mv.addObject("list", resultList);
			}else{
				mv.addObject("list", list);
			}
			
			
			pd.put("user_kehu_fid",  getSessionUser().getUser_kehu_id());
			//查默认地址
			PageData pdsData =  h5KeHuService.getByisDefault(pd);
			mv.addObject("user_shangjia_id", pd.get("user_shangjia_id"));
			mv.addObject("total", pd.get("total"));
			//查餐盒费
			
			PageData pdcanhe = this.getPageData();
			pdcanhe.put("kehu_id", getSessionUser().getUser_kehu_id());
			pdcanhe.put("shangjia_id",pd.get("user_shangjia_id"));
			PageData canhefeiData =  h5KeHuService.getCanhefeiSUM(pdcanhe);
		
			mv.addObject("canhefeiData", canhefeiData);
			mv.addObject("pds", pdsData);
			mv.addObject("user_kehu_id",  getSessionUser().getUser_kehu_id());
			mv.setViewName("kehuduan/shopcart/order_info");
//			mv.setViewName("redirect:/api/h5KeHu/myorder");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	
	
	@RequestMapping(value="/saveOrderTemp")
	@ResponseBody
	public Object saveOrderTemp() throws Exception{
		logBefore(logger, "--保存到外卖临时表中--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
			
		pd.put("user_kehu_id",  getSessionUser().getUser_kehu_id());
		//查出临时表所有数据
		List<PageData> resultList = h5KeHuService.selectTempOrderList(pd);
		//查出合计数
		PageData totolData = h5KeHuService.getTotolByUserKeHuID(pd);
		int flag=0;
		if(resultList.size()>0){
			//增加
			if(pd.get("tag").equals("1")){
				//新增一行数据
			
				for(int i=0;i<resultList.size();i++){
					//如果是数量增加，则在原数据上数量+1
					if(resultList.get(i).get("sp_id").equals(pd.get("id"))){
						PageData upd = new PageData();
						upd.put("num", Integer.parseInt(resultList.get(i).get("num").toString())+1);
						upd.put("user_kehu_id", pd.get("user_kehu_id"));
						upd.put("sp_id", pd.get("id"));
						//更新某件商品的数量
						h5KeHuService.updateNumberbyId(upd);
						flag=1;
					}
				} 
					
				/*	先删除后添加
					PageData del = this.getPageData();
					del.put("sp_id", pd.get("id"));
					del.put("user_kehu_id", pd.get("user_kehu_id"));
					h5KeHuService.delTempOrderBySpID(del);*/
				if(flag==0){
					//添加
					PageData pdinsert = this.getPageData();
					pdinsert.put("temp_id", this.get32UUID());
					pdinsert.put("kehu_id", getSessionUser().getUser_kehu_id());
					pdinsert.put("shangjia_id",pd.get("user_shangjia_id"));
					pdinsert.put("sp_id", pd.get("id"));
					pdinsert.put("sp_name",pd.get("name"));
					pdinsert.put("price", pd.get("price"));
					pdinsert.put("num", pd.get("num"));
					pdinsert.put("canhefei", pd.get("canhefei"));
					h5KeHuService.saveTempOrder(pdinsert);
					System.out.println("-----跳出来了-------");
				}
				
			
			//减	
			}else{
				for(int i=0;i<resultList.size();i++){
					//如果是数量增加，则在原数据上数量-1
					if(resultList.get(i).get("sp_id").equals(pd.get("id"))){
						if(Integer.parseInt(resultList.get(i).get("num").toString())>1){
							PageData upd = new PageData();
							upd.put("num", Integer.parseInt(resultList.get(i).get("num").toString())-1);
							upd.put("user_kehu_id", pd.get("user_kehu_id"));
							upd.put("sp_id", pd.get("id"));
							//更新某件商品的数量
							h5KeHuService.updateNumberbyId(upd);
						}else{
							//删除
							PageData del = new PageData();
							del.put("sp_id", pd.get("id"));
							del.put("user_kehu_id", pd.get("user_kehu_id"));
							h5KeHuService.delTempOrderBySpID(del);
							System.out.println("----减到0则删除成功----");
							break;
						}
					
					}else{
						//不进这里
					}
				}
			}
			
		}else{
			PageData pdinsert = this.getPageData();
			pdinsert.put("temp_id", this.get32UUID());
			pdinsert.put("kehu_id", getSessionUser().getUser_kehu_id());
			pdinsert.put("shangjia_id",pd.get("user_shangjia_id"));
			pdinsert.put("sp_id", pd.get("id"));
			pdinsert.put("sp_name",pd.get("name"));
			pdinsert.put("price", pd.get("price"));
			pdinsert.put("num", pd.get("num"));
			pdinsert.put("canhefei", pd.get("canhefei"));
			h5KeHuService.saveTempOrder(pdinsert);
		}
		/*//查出临时表所有数据
		pd.put("user_kehu_id", getSessionUser().getUser_kehu_id());
		List<PageData> finalList = h5KeHuService.selectTempOrderList(pd);
		map.put("finalList", finalList);*/
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 购物车集合
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-17
	 */
	@RequestMapping(value="/gwcList")
	@ResponseBody
	public Object gwcList() throws Exception{
		logBefore(logger, "--查看购物车--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//最后查出所有
		//查出临时表所有数据
		pd.put("user_kehu_id", getSessionUser().getUser_kehu_id());
		List<PageData> finalList = h5KeHuService.selectTempOrderList(pd);
		map.put("finalList", finalList);
		logBefore(logger, AppUtil.returnObject(new PageData(), map).toString());
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 清空购物车
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-18
	 */
	@RequestMapping(value="/clearCar")
	@ResponseBody
	public Object clearCar() throws Exception{
		logBefore(logger, "--清空购物车--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("user_kehu_id", getSessionUser().getUser_kehu_id());
		h5KeHuService.delTempOrder(pd);
		map.put("respMsg", "01");
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	
	
	/**
	 * 提交订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payOrder")
	public ModelAndView payOrder() throws Exception{
		logBefore(logger, "--payOrder---客户端外卖提交订单---");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		if (isSession()) {
			pd.put("user_kehu_id", getSessionUser().getUser_kehu_id());
			List<PageData> resultlist = h5KeHuService.selectTempOrderList(pd);
			PageData maxnumData = h5KeHuService.selectMaxQucanNumber(pd);
			
			//平台服务费比例
			PageData fuwuData  = h5sysService.getDateByfuwufeiId(pd);
			//配送费比例
			//PageData peisonData  = h5sysService.getDateBypeisongfeiId(pdinsert);
			//订单表主键id
			String order_takeou_id = this.get32UUID();
			//插入订单表
			PageData pdinsert = this.getPageData();
			pdinsert.put("order_takeou_id", order_takeou_id);
			pdinsert.put("orderTime", DateUtil.getTime());
			pdinsert.put("orderNumber","");//先设置为空字符串，在支付时更新
			if(maxnumData!=null){
				pdinsert.put("qucan_number",(Integer.parseInt(maxnumData.get("maxnum").toString())+1));
			}else{
				pdinsert.put("qucan_number",1);
			}
			
			//保存到订单表中
			pdinsert.put("order_remark", pd.get("liuyan"));//留言 备注
			pdinsert.put("payState", "未支付");//支付后修改是微信支付还是支付宝支付
			pdinsert.put("orderStateKehu", 1);	
			pdinsert.put("orderStateShangjia", 1);
			pdinsert.put("orderStateQishou", 6);
			pdinsert.put("payMethod", "");//支付方式
			pdinsert.put("totalSum", pd.get("totol"));
			pdinsert.put("paySum", pd.get("totol"));
			pdinsert.put("user_kehu_fid",  getSessionUser().getUser_kehu_id());
			pdinsert.put("user_shangjia_fid",  pd.get("user_shangjia_id"));
			pdinsert.put("shouhuo_address_fid",  pd.get("shouhuo_address_id"));
			pdinsert.put("user_qishou_fid",  "");
			pdinsert.put("qurysdTime",  "");
			pdinsert.put("peisongfei", 3);//暂时先定死3块钱，客户提出再修改
			pdinsert.put("fuwubili", fuwuData.get("fuwubili"));
			h5KeHuService.insertOrderTakeou(pdinsert);
			
			//批量添加到商品订单表
			for(int i=0;i<resultlist.size();i++){
				PageData goods = this.getPageData();
				goods.put("order_goods_id", this.get32UUID());
				goods.put("goodsName", resultlist.get(i).get("name"));
				goods.put("goodsNum", resultlist.get(i).get("num"));
				goods.put("canhefei", resultlist.get(i).get("canhefei"));
				goods.put("originalPrice", resultlist.get(i).get("price"));//商品原价
				goods.put("presentPrice", resultlist.get(i).get("price"));//商品则后价
				//外建 外卖订单id
				goods.put("takeout_order_fid", order_takeou_id);
				goods.put("goods_fid", resultlist.get(i).get("sp_id"));
				h5KeHuService.insertOrderGoods(goods);
				
			}
			
			//删除临时订单表
			h5KeHuService.delTempOrder(pd);
			mv.addObject("order_takeou_id", order_takeou_id);
			mv.addObject("totol", pd.get("totol"));
			mv.setViewName("kehuduan/shopcart/pay_order");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	
	@RequestMapping(value="/delTempOrder")
	public ModelAndView delTempOrder(HttpSession session) throws Exception{
		logBefore(logger, "--删除临时订单--");
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		if(isSession()){
			pd.put("user_kehu_id", pd.get("user_kehu_id"));
			h5KeHuService.delTempOrder(pd);
			mv.setViewName("redirect:/api/h5KeHu/shop.do?user_shangjia_id="+pd.get("user_shangjia_id"));
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	
	/**
	 * 管理收货地址页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/address")
	public ModelAndView address(HttpSession session) throws Exception{
		logBefore(logger, "--address----管理收货地址页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			pd.put("user_kehu_fid", kehu.getUser_kehu_id());
			List<PageData> addressList  = h5KeHuService.addressList(pd);//收货地址列表
			mv.addObject("pd", addressList);
			mv.addObject("user_shangjia_id", pd.get("user_shangjia_id"));
			
			if(pd.getString("tag").equals("2")){
				mv.addObject("tag", pd.get("tag"));
				mv.setViewName("kehuduan/user/address2");
			}else{
				mv.setViewName("kehuduan/user/address");
			}
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 跳转新增收货地址页面
	 * @return
	 */
	@RequestMapping(value="/addressAdd")
	public ModelAndView addressAdd(){
		logBefore(logger, "--addressAdd----跳转新增收货地址页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd =new PageData();
		pd= this.getPageData();
		if(isSession()){
			mv.addObject("user_shangjia_id", pd.get("user_shangjia_id"));
			mv.addObject("msg", "saveaddress");
			mv.addObject("tag", pd.get("tag"));
			mv.setViewName("kehuduan/user/address_add");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 跳转修改收货地址页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addressEdit")
	public ModelAndView addressEdit() throws Exception{
		logBefore(logger, "--addressEdit----跳转修改收货地址页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		if(isSession()){
			mv.addObject("tag", pd.get("tag"));
			mv.addObject("user_shangjia_id", pd.get("user_shangjia_id"));
			pd.put("shouhuo_address_id", pd.getString("shouhuo_address_id"));
			pd = h5KeHuService.addressEdit(pd); //根据id查询一条信息
			mv.addObject("pd", pd);
			mv.addObject("msg", "saveEdit");
			mv.setViewName("kehuduan/user/address_edit");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 执行删除收货地址
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addressDelete")
	public ModelAndView addressDelete() throws Exception{
		logBefore(logger, "--addressDelete----执行删除收货地址--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		if(isSession()){
			pd.put("shouhuo_address_id", pd.getString("shouhuo_address_id"));
			h5KeHuService.addressDelete(pd);
			mv.setViewName("redirect:/api/h5KeHu/address?tag="+pd.getString("tag")+"&user_shangjia_id="+pd.getString("user_shangjia_id"));
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 执行编辑收货地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveEdit")
	@ResponseBody
	public ModelAndView saveEdit(HttpSession session) throws Exception{
		logBefore(logger, "--saveEdit--执行编辑收货地址-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			
			//批量设置成不是默认
			String ids= kehu.getUser_kehu_id();
			System.out.println(ids);
			if(pd.getString("isDefault").equals("1")){
				String Arrayids[]=ids.split(",");//分割成数组
				h5KeHuService.setisDefaultON(Arrayids);
			}
		
			pd.put("shouhuo_address_id", pd.getString("shouhuo_address_id"));
			pd.put("linkmanName", pd.getString("linkmanName"));
			pd.put("phone", pd.getString("phone"));
			pd.put("address", pd.getString("address"));
			pd.put("latitude", pd.getString("lat"));
			pd.put("longitude", pd.getString("lng"));
			pd.put("detailAddress", pd.getString("detailAddress"));
			pd.put("identity", pd.getString("identity"));
			pd.put("lable", pd.getString("lable"));
			pd.put("isDefault", pd.getString("isDefault"));
			pd.put("user_kehu_fid", kehu.getUser_kehu_id());
			pd.put("update_time", Tools.date2Str(new Date()));//创建时间
			h5KeHuService.saveEdit(pd);//执行编辑收货地址
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		
		mv.setViewName("redirect:/api/h5KeHu/address.do?tag="+pd.getString("tag")+"&user_shangjia_id="+pd.getString("user_shangjia_id"));
		return mv;
	}
	
	
	/**
	 * 执行新增收货地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveaddress")
	@ResponseBody
	public ModelAndView saveaddress(HttpSession session) throws Exception{
		logBefore(logger, "--saveaddress--执行新增收货地址-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			String stri = pd.getString("isDefault");
			if (stri!=null) {
				int key = Integer.parseInt(stri);
				System.out.println(key);
				if (key==0) {
					pd.put("shouhuo_address_id", this.get32UUID());
					pd.put("linkmanName", pd.getString("linkmanName"));
					pd.put("phone", pd.getString("phone"));
					pd.put("address", pd.getString("address"));
					pd.put("latitude", pd.getString("lat"));
					pd.put("longitude", pd.getString("lng"));
					pd.put("detailAddress", pd.getString("detailAddress"));
					pd.put("identity", pd.getString("identity"));
					pd.put("lable", pd.getString("lable"));
					pd.put("isDefault", pd.getString("isDefault"));
					pd.put("user_kehu_id", kehu.getUser_kehu_id());
					pd.put("create_time", Tools.date2Str(new Date()));//创建时间
					h5KeHuService.saveaddress(pd);//执行新增收货地址
				}else {
					//批量设置成不是默认
					String ids= kehu.getUser_kehu_id();
					String Arrayids[]=ids.split(",");//分割成数组
					h5KeHuService.setisDefaultON(Arrayids);
					pd.put("shouhuo_address_id", this.get32UUID());
					pd.put("linkmanName", pd.getString("linkmanName"));
					pd.put("phone", pd.getString("phone"));
					pd.put("address", pd.getString("address"));
					pd.put("latitude", pd.getString("lat"));
					pd.put("longitude", pd.getString("lng"));
					pd.put("detailAddress", pd.getString("detailAddress"));
					pd.put("identity", pd.getString("identity"));
					pd.put("lable", pd.getString("lable"));
					pd.put("isDefault", pd.getString("isDefault"));
					pd.put("user_kehu_id", kehu.getUser_kehu_id());
					pd.put("create_time", Tools.date2Str(new Date()));//创建时间
					h5KeHuService.saveaddress(pd);//执行新增收货地址
				}
			}
			mv.setViewName("redirect:/api/h5KeHu/address?tag="+pd.get("tag")+"&user_shangjia_id="+pd.get("user_shangjia_id"));
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	
	/**
	 * 顺风车客户端进入首页
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/index")
	public ModelAndView index() throws Exception{
		logBefore(logger, "--顺风车客户端进入首页--");
		ModelAndView mv = new  ModelAndView();
		PageData pd=new PageData();
		pd = this.getPageData();
		//if(isSession()){
			pd.put("cnxh", "cnxh");
			List<PageData> ShangjiaList = h5KeHuService.queryUserShangjiaList(pd);
			List<PageData>	varList = h5KeHuService.picturesList(pd);	//轮播图列表
			//用于登录成功提示
			mv.addObject("tag", "1");
			mv.addObject("respCode", "01");
			mv.addObject("msg", "session有效");
			mv.addObject("pd",ShangjiaList);
			mv.addObject("varList", varList);
		//}else{
			//mv.addObject("respCode", "00");
			//mv.addObject("msg", "session失效");
		//}
		mv.setViewName("kehuduan/index");
		return mv;
	}
	
	
	/**
	 * 去顺风车客户端活动社区页面
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/hdsq")
	public ModelAndView hdsq(HttpSession session,HttpServletRequest request) throws Exception{
		logBefore(logger, "----hudongZone---去顺风车客户端活动社区页面---");
		ModelAndView mv = new  ModelAndView();
		//if(isSession()){
			PageData pd=new PageData();
			pd = this.getPageData();
			List<PageData> mabiaoList  = h5KeHuService.selectall(pd);//互动社区码表类型
			PageData category = new PageData();
			category.put("hudong_category_id", pd.getString("hudong_category_id"));
			category.put("content", pd.getString("content"));
			List<PageData> hdsqList  = h5KeHuService.selecHuDongSheQuList(category);//互动社区列表
		    List<PageData>  resultLists = new ArrayList<PageData>();
		    String imgs[] = new String[0];
		    for (int k=0;k<hdsqList.size();k++) {
		    	PageData hdsqLists = new PageData();
		    	hdsqLists.put("hudong_dongtai_id",hdsqList.get(k).get("hudong_dongtai_id"));
		    	hdsqLists.put("userName",hdsqList.get(k).get("userName"));
		    	hdsqLists.put("headImg",hdsqList.get(k).get("headImg"));
		    	hdsqLists.put("content",hdsqList.get(k).get("content"));
		    	hdsqLists.put("fabuTime",hdsqList.get(k).get("fabuTime"));
		    	hdsqLists.put("isTop",hdsqList.get(k).get("isTop"));
		    	hdsqLists.put("categoryName",hdsqList.get(k).get("categoryName"));
		    	hdsqLists.put("fabuTime", DateUtil.getDistanceTime(hdsqList.get(k).get("fabuTime").toString()));
		    	hdsqLists.put("userName", hdsqList.get(k).get("userName"));
		    	hdsqLists.put("zanNumber", hdsqList.get(k).get("zanNumber"));
		    	hdsqLists.put("plcounts", hdsqList.get(k).get("plcounts"));
		    	if(Tools.notEmpty(hdsqList.get(k).get("imgPath").toString())){
	                 imgs = hdsqList.get(k).get("imgPath").toString().split(",");
	            }else{
	                imgs=new String[0];
	            }
		    	List<PageData> imgList = new ArrayList<PageData>();
		    	 for(int j=0;j<imgs.length;j++){
	                 PageData pd21  = new PageData();
	                 if(imgs[j].equals("")){
	                     pd21.put("imgPath", "");
	                 }else{
	                     pd21.put("imgPath", imgs[j]);
	                 }
	                 imgList.add(pd21);
	            }
		    	hdsqLists.put("imgList", imgList);
		    	resultLists.add(hdsqLists);
			}
		    mv.addObject("pd",pd);
			mv.addObject("tid", pd.get("tid"));
			mv.addObject("hdsqList", resultLists);
			mv.addObject("mabiaoList", mabiaoList);
			mv.setViewName("kehuduan/hudongZone");
		//}else{
			//mv.setViewName("redirect:/api/h5KeHu/index");
		//}
		return mv;
	}
	
	/**
	 * 去1级评论点赞
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getOneDianzanNumber")
	@ResponseBody
	public Object zanNumber() throws Exception{
		logBefore(logger, "---zanNumber--去1级评论点赞--");
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
 		if(isSession()){
			pd.put("hudong_dongtai_id", pd.getString("hudong_dongtai_fid"));
			pd.put("user_kehu_fid",  getSessionUser().getUser_kehu_id());
			PageData sdData = h5KeHuService.selectOneDianzanNum(pd);
			if(sdData==null){
				PageData pdinsert = new PageData();
				pdinsert.put("dianzan_id", this.get32UUID());
				pdinsert.put("hudong_dongtai_fid",  pd.getString("hudong_dongtai_fid"));
				pdinsert.put("user_kehu_fid", getSessionUser().getUser_kehu_id());
				pdinsert.put("zanNumber", "1");
				h5KeHuService.insertOneDianzanNum(pdinsert);
				PageData sdData2 = h5KeHuService.getDianzanNumberByHudongId(pd);
				map.put("zanNumber", sdData2.get("zannum"));
				map.put("respCode", "01");
			}else{
				/*PageData sdDatas = h5KeHuService.selectOneDianzanNum(pd);
				int num = Integer.parseInt(sdDatas.get("zanNumber").toString())+1;
				pd.put("changeNum",num);
				h5KeHuService.updateOneDianzanNum(pd);*/
				PageData sdData2 = h5KeHuService.getDianzanNumberByHudongId(pd);
				map.put("zanNumber", sdData2.get("zannum"));
				map.put("respCode", "03");
			}
			
		}else{
			map.put("respCode", "00");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 去评论列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/nichengMou")
	public ModelAndView nichengMou() throws Exception{
		logBefore(logger, "---nichengMou--去评论列表页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("hudong_dongtai_fid", pd.getString("hudong_dongtai_fid"));
			PageData sdData = h5KeHuService.queryHudongDongtai(pd);
			PageData count = h5KeHuService.querycount(pd);//一级回复总数
			PageData sdDatas = h5KeHuService.getDianzanNumberByHudongId(pd);//查询一级点赞总数量
			List<PageData> pdDataList  = h5KeHuService.hudongPinglunlistPage(pd);//一级评论列表
			mv.addObject("pd", pdDataList);
			mv.addObject("sdData", sdData);
			mv.addObject("count", count);
			mv.addObject("sdDatas", sdDatas);
			mv.addObject("id", pd.getString("hudong_dongtai_fid"));
			mv.setViewName("kehuduan/Interaction/nichengMou");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 去2级评论点赞
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getTwoDianzanNumber")
	@ResponseBody
	public Object zanNumberTwo() throws Exception{
		logBefore(logger, "---zanNumberTwo--去2级评论点赞--");
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
 		if(isSession()){
			pd.put("hudong_pinglun_fid", pd.getString("hudong_pinglun_fid"));
			pd.put("user_kehu_fid",  getSessionUser().getUser_kehu_id());
			PageData sdData = h5KeHuService.selectTwoDianzanNum(pd);
			if(sdData==null){
				PageData pdinsert = new PageData();
				pdinsert.put("dianzan_two_id", this.get32UUID());
				pdinsert.put("hudong_pinglun_fid",  pd.getString("hudong_pinglun_fid"));
				pdinsert.put("user_kehu_fid", getSessionUser().getUser_kehu_id());
				pdinsert.put("zanNumber", "1");
				h5KeHuService.insertTwoDianzanNum(pdinsert);
				PageData sdData2 = h5KeHuService.getDianzanNumberByPinglunId(pd);
				map.put("zanNumbers", sdData2.get("zannum"));
				map.put("respCode", "01");
			}else{
				PageData sdData2 = h5KeHuService.getDianzanNumberByPinglunId(pd);
				map.put("zanNumber", sdData2.get("zannum"));
				map.put("respCode", "03");
			}
			
		}else{
			map.put("respCode", "00");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 去3级评论点赞
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getThreeDianzanNumber")
	@ResponseBody
	public Object zanNumberThree() throws Exception{
		logBefore(logger, "---zanNumberThree--去3级评论点赞--");
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
 		if(isSession()){
			pd.put("hudong_pinglun_two_fid", pd.getString("hudong_pinglun_two_fid"));
			pd.put("user_kehu_fid",  getSessionUser().getUser_kehu_id());
			PageData sdData = h5KeHuService.selectThreeDianzanNum(pd);
			if(sdData==null){
				PageData pdinsert = new PageData();
				pdinsert.put("dianzan_three_id", this.get32UUID());
				pdinsert.put("hudong_pinglun_two_fid",  pd.getString("hudong_pinglun_two_fid"));
				pdinsert.put("user_kehu_fid", getSessionUser().getUser_kehu_id());
				pdinsert.put("zanNumber", "1");
				h5KeHuService.insertThreeDianzanNum(pdinsert);
				PageData sdData2 = h5KeHuService.getDianzanNumberByPinglunTwoId(pd);
				map.put("zanNumber", sdData2.get("zannum"));
				map.put("respCode", "01");
			}else{
				PageData sdData2 = h5KeHuService.getDianzanNumberByPinglunTwoId(pd);
				map.put("zanNumber", sdData2.get("zannum"));
				map.put("respCode", "03");
			}
			
		}else{
			map.put("respCode", "00");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 去二级评论页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/pinglunBieren")
	public ModelAndView pinglunBieren(HttpSession session) throws Exception{
		logBefore(logger, "---pinglunBieren--去二级评论页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("hudong_pinglun_fid", pd.getString("hudong_pinglun_fid"));
			PageData sdData = h5KeHuService.queryHudongPinglun(pd);
			PageData count = h5KeHuService.querycountTwo(pd);
			PageData sdDatas = h5KeHuService.getDianzanNumberByPinglunId(pd);
			List<PageData> pdDataList  = h5KeHuService.hudongPinglunTwolistPage(pd);//二级评论列表
			mv.addObject("pd", sdData);
			mv.addObject("pds", pdDataList);
			mv.addObject("count", count);
			mv.addObject("sdDatas", sdDatas);
			mv.setViewName("kehuduan/Interaction/pinglunBieren");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 去发布二级评论页面
	 * @return
	 */
	@RequestMapping(value="/fabuPinglunTwo")
	public ModelAndView fabuPinglunTwo(){
		logBefore(logger, "---fabuPinglunTwo--去发二级布评论页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			mv.addObject("msg", "savepinglunBieren");
			mv.addObject("hudong_pinglun_id", pd.getString("hudong_pinglun_fid"));
			mv.setViewName("kehuduan/Interaction/fabuPinglunTwo");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 执行保存发二级评论
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/savepinglunBieren")
	public ModelAndView savepinglunBieren(HttpSession session) throws Exception{
		logBefore(logger, "---savepinglunBieren--执行保存发二级评论--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			pd.put("hudong_pinglun_two_id", this.get32UUID());//评论主键id
			pd.put("pinglunContent", pd.getString("content"));//评论内容
			pd.put("pinglunTime", Tools.date2Str(new Date()));//评论时间
//			pd.put("hudong_dongtai_fid", pd.getString("hudong_dongtai_id"));//互动动态外键id
			pd.put("hudong_pinglun_fid", pd.getString("hudong_pinglun_fid"));//一级评论外键id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());//评论用户
			h5KeHuService.savepinglunBieren(pd);
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		mv.setViewName("redirect:/api/h5KeHu/pinglunBieren?hudong_pinglun_fid="+pd.getString("hudong_pinglun_fid"));
		return mv;
	}
	
	/**
	 * 去发布评论页面
	 * @return
	 */
	@RequestMapping(value="/fabuPinglun")
	public ModelAndView fabuPinglun(){
		logBefore(logger, "---fabuPinglun--去发布评论页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			mv.addObject("msg", "savefabuPinglun");
			mv.addObject("hudong_dongtai_id", pd.getString("hudong_dongtai_fid"));
			mv.setViewName("kehuduan/Interaction/fabuPinglun");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	
	/**
	 * 执行保存发布评论
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/savefabuPinglun")
	public ModelAndView savefabuPinglun(HttpSession session) throws Exception{
		logBefore(logger, "---savefabuPinglun--保存发布评论--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		if(isSession()){
			pd.put("hudong_pinglun_id", this.get32UUID());//评论主键id
			pd.put("pinglunContent", pd.getString("content"));//评论内容
			pd.put("pinglunTime", Tools.date2Str(new Date()));//评论时间
//			pd.put("zanNumber", pd.getString("zanNumber"));//点赞数量
			pd.put("hudong_dongtai_fid", pd.getString("hudong_dongtai_fid"));//互动动态外键id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());//评论用户
			h5KeHuService.savepinglun(pd);
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		mv.setViewName("redirect:/api/h5KeHu/nichengMou?hudong_dongtai_fid="+pd.getString("hudong_dongtai_fid"));
		return mv;
	}
	
	/**
	 * 去打车拼车页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dcpc")
	public ModelAndView dcpc() throws Exception{
		logBefore(logger, "---dcpc---去打车拼车页面------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("user_kehu_fid", getSessionUser().getUser_kehu_id());
			PageData weifukuan = h5KeHuService.getorderTongcheng(pd);//查询未完成同城订单
			PageData weifukuanjinxing = h5KeHuService.getorderTongchengjinxing(pd);//查询进行中同城订单
			PageData weifukuanyijiejia= h5KeHuService.getorderTongyijiejia(pd);//查询司机已接驾到乘客的订单
			if (weifukuan == null && weifukuanjinxing == null && weifukuanyijiejia == null) {//判断乘客同城订单是否完成
				mv.setViewName("kehuduan/dachepinche");
			}else if(weifukuanjinxing != null) {
				if (weifukuanjinxing.getString("order_tongcheng_status").equals("1")) {//判断乘客是否存在进行中的订单
						System.out.println("01"+"判断乘客是否存在进行中的订单");
						mv.addObject("respCode", "01");
						mv.addObject("order_tongcheng_id", weifukuanjinxing.getString("order_tongcheng_id"));
						mv.addObject("information_tongcheng_id", weifukuanjinxing.getString("information_tongcheng_id"));
						mv.setViewName("kehuduan/dachepinche");
					}
			}else if (weifukuan != null) {
				if(weifukuan.getString("order_tongcheng_status").equals("2")){//判断乘客是否存在未付款订单
					System.out.println("02"+"判断乘客是否存在未付款订单");
					mv.addObject("respCode", "02");
					mv.addObject("order_tongcheng_id", weifukuan.getString("order_tongcheng_id"));
					mv.addObject("information_tongcheng_id", weifukuan.getString("information_tongcheng_id"));
					mv.setViewName("kehuduan/dachepinche");
				}
			}else if (weifukuanyijiejia != null) {
				if (weifukuanyijiejia.getString("order_tongcheng_status").equals("4")) {//判断乘客是否已被司机接驾到
					System.out.println("04"+"判断乘客是否已被司机接驾到");
					mv.addObject("respCode", "04");
					mv.addObject("order_tongcheng_id", weifukuanyijiejia.getString("order_tongcheng_id"));
					mv.addObject("information_tongcheng_id", weifukuanyijiejia.getString("information_tongcheng_id"));
					mv.setViewName("kehuduan/dachepinche");
				}
			}
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 输入商品名称或关键词
	 * @return
	 */
	@RequestMapping(value="/search")
	public ModelAndView search(){
		logBefore(logger, "---search---输入商品名称或关键词------");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
			mv.setViewName("kehuduan/search");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 我的同城打车订单列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dachedingdan")
	public ModelAndView dachedingdan(HttpSession session) throws Exception{
		logBefore(logger, "------dachedingdan---我的同城打车订单列表-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (isSession()) {
			KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			List<PageData> TongChengOrderList = sijiOrderTongChengService.queryKeHuOrderTongChengList(pd);//同城订单列表
			for (int i = 0; i < TongChengOrderList.size(); i++) {
				TongChengOrderList.get(i).put("timeshow", DateUtil.getDistanceTime(TongChengOrderList.get(i).get("orderTime").toString()));
			}
			mv.addObject("pd",TongChengOrderList);
			mv.setViewName("kehuduan/taxi/dachedingdan");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 我的长途打车订单列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dachedingdans")
	public ModelAndView dachedingdans(HttpSession session) throws Exception{
		logBefore(logger, "------dachedingdans---我的长途打车订单列表-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (isSession()) {
			KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			List<PageData> ChangTuOrderList = orderChangtuService.queryOrderKeHuChangtuList(pd);//长途订单列表
			mv.addObject("pd",ChangTuOrderList);
			mv.setViewName("kehuduan/taxi/dachedingdans");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 我的长途订单详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/changtuXiangdans")
	public ModelAndView changtuXiangdans(HttpSession session) throws Exception{
		logBefore(logger, "--changtuXiangdans---我的长途订单详情-----");
		ModelAndView mv = new  ModelAndView();
		PageData pd  = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("order_changtu_id", pd.getString("order_changtu_id"));
			PageData OrderChangTu =	orderChangtuService.queryOrderChangTuKeHu(pd);//我的长途发布详情
			mv.addObject("pd",OrderChangTu);
			mv.setViewName("kehuduan/taxi/changtuXiangdans");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 我的打车同城订单详情
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/tongchengcome")
	public ModelAndView tongchengcome() throws Exception{
		logBefore(logger, "------tongchengcome---我的打车同城订单详情-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd  = new PageData();
		pd = this.getPageData();
		if (isSession()) {
			pd.put("order_tongcheng_id", pd.getString("order_tongcheng_id"));
			PageData OrderTongCheng = sijiOrderTongChengService.queryOrderTongCheng(pd);//同城订单详情
			mv.addObject("pd", OrderTongCheng);
			mv.setViewName("kehuduan/taxi/tongchengcome");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 确认送达
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/changtuSure")
	public ModelAndView changtuSure() throws Exception{
		logBefore(logger, "------changtuSure---确认送达-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd  = new PageData();
		pd = this.getPageData();
		if (isSession()) {
			pd.put("order_changtu_id", pd.getString("order_changtu_id"));
			PageData OrderChangTu =	orderChangtuService.queryOrderChangTuKeHu(pd);//我的长途发布详情
			mv.addObject("pd",OrderChangTu);
			mv.setViewName("kehuduan/taxi/changtuSure");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 我的外卖订单
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/myorder")
	public ModelAndView myorder() throws Exception{
		logBefore(logger, "----myorder--我的外卖订单------");
		ModelAndView mv = new  ModelAndView();
		PageData pd= new PageData();
		pd = this.getPageData();
		if (isSession()) {
			pd.put("user_kehu_fid",  getSessionUser().getUser_kehu_id());
			List<PageData> OrderList = h5KeHuService.queryOrderTakeouLists(pd);//我的外卖订单全部订单
			mv.addObject("OrderList", OrderList);
			mv.setViewName("kehuduan/user/my_order");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 我的外卖订单全部订单
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/orderDetail")
	public ModelAndView orderDetail() throws Exception{
		logBefore(logger, "----orderDetail--我的外卖订单全部订单------");
		ModelAndView mv = new  ModelAndView();
		PageData pd= new PageData();
		pd = this.getPageData();
		if (isSession()){
			pd.put("order_takeou_id", pd.getString("order_takeou_id"));
			PageData sData = h5KeHuService.querytbOrderTakeou(pd);//订单详情
			List<PageData> kehugoodsList = h5KeHuService.kehuOrderGoodsLists(pd);
			mv.addObject("goodsList", kehugoodsList);
			mv.addObject("pd", sData);
			mv.setViewName("kehuduan/user/order_detail");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 取消外卖订单
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/quxiaoOrderDelete")
	public ModelAndView quxiaoOrderDelete() throws Exception{
		logBefore(logger, "----quxiaoOrder--取消外卖订单------");
		ModelAndView mv = new  ModelAndView();
		PageData pd= new PageData();
		pd = this.getPageData();
		pd.put("order_takeou_id", pd.getString("order_takeou_id"));
		h5KeHuService.quxiaoOrderDelete(pd);//执行删除
		h5KeHuService.quxiaoOrderGoodsDelete(pd);//执行删除商品
		mv.setViewName("redirect:/api/h5KeHu/myorder");
		return mv;
	}
	
	/**
	 * 去我的页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/wd")
	public ModelAndView wd(HttpSession session) throws Exception{
		logBefore(logger, "--wd--去我的页面-----");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
			PageData pd= new PageData();
			pd = this.getPageData();
			KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
			pd.put("user_kehu_id", kehu.getUser_kehu_id());
			PageData pds = h5KeHuService.getDataById(pd);
			PageData haveData = h5KeHuService.queryOrderTakeouHaveInHand(pd);
			int keys = Integer.parseInt(haveData.get("haveInHandTc").toString());
			int key = Integer.parseInt(haveData.get("haveInHandCt").toString());
			if (keys == 0 && key==0) {
				System.out.println("显示0");
				mv.addObject("ss", 0);
			}else {
				System.out.println("显示1");
				mv.addObject("ss", 1);
			}
			mv.addObject("pd", haveData);
			mv.addObject("pds", pds);
			mv.setViewName("kehuduan/wode");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 去同城打车页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/tongchengCar")
	public ModelAndView tongchengCar() throws Exception{
		logBefore(logger, "----tongchengCar-----去同城打车页面------");
		ModelAndView mv = new  ModelAndView();
		if(isSession()){
			mv.addObject("msg", "savetongchengCar");
			mv.setViewName("kehuduan/taxi/tongchengCar");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 执行同城呼叫
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savetongchengCar")
	@ResponseBody
	public ModelAndView savetongchengCar(HttpSession session) throws Exception{
		logBefore(logger, "--savetongchengCar--执行同城呼叫-------");
		ModelAndView mv = new  ModelAndView();
		PageData pd = this.getPageData();
		System.out.println("--起始点经纬度：--"+pd.getString("inps1")+"--------"+pd.getString("inps2")+"----目的地经纬度：---"+pd.getString("inps3")+"-------"+pd.getString("inps4"));
		double a = Double.parseDouble(pd.getString("inps1"));
		double b = Double.parseDouble(pd.getString("inps2"));
		double c = Double.parseDouble(pd.getString("inps3"));
		double d = Double.parseDouble(pd.getString("inps4"));
		double ae = LocationUtils.getDistance(a, b, c, d);
		double be = LocationUtils.GetDistancetwo(a, b, c, d);
		double ce = LocationUtils.GetDistance(a, b, c, d);
		System.out.println("----第一种换算结果(ae)：---"+ae+"(m)"+"----第二种换算结果(be)：---"+be+"(km)"+"----第三种换算结果(ce)：---"+ce+"(m)");
		System.out.println("-----start起始点的经纬度:----"+pd.getString("latitude_longitude_start")+"---end--目的地的经纬度：--"+pd.getString("latitude_longitude_end"));
		PageData jine = keHuService.tongchengjifeijine(pd);
		double jifeis = 0.0;
		String about_Amount ="";
		double jifei = Double.parseDouble(jine.getString("jifei_Amount")) * ae;
		double jifeisr = Double.parseDouble(jine.getString("jifei_Amount")) * ce;
		System.out.println(jifei);
		System.out.println(jifeisr);
		if (be <= Double.parseDouble(jine.getString("qibugongli"))) {//如果实际公里<=起步公里
			jifeis = Double.parseDouble(jine.getString("qibujia"));//起步费（元）
			about_Amount = String .format("%.2f",jifeis);
			System.out.println(jifeis);//大约乘车金额
		} else {
			//超出公里算法    超出公里费*（超出公里-起步公里）+起步费
			jifeis = Double.parseDouble(jine.getString("jifei_Amount")) * (be-Double.parseDouble(jine.getString("qibugongli")))+Double.parseDouble(jine.getString("qibujia"));
			about_Amount = String .format("%.2f",jifeis);
			System.out.println(about_Amount);//大约乘车金额
		}
		
		KeHu kehu = (KeHu) session.getAttribute("h5User");//获取用户id
		String information_tongcheng_id = this.get32UUID();
		if(isSession()){
			pd.put("information_tongcheng_id", information_tongcheng_id);
			pd.put("create_time",DateUtil.getTime());//发布时间
			pd.put("departurePlace",pd.getString("departurePlace"));//出发地点
			pd.put("destination",pd.getString("destinations"));//目的地
			pd.put("latitude_longitude_start",pd.getString("latitude_longitude_start"));//纬度
			pd.put("latitude_longitude_end",pd.getString("latitude_longitude_end"));//精度
			pd.put("about_Amount",about_Amount);//大约乘车金额
			pd.put("hujiao_status",0);//呼叫状态（0-呼叫中 1-取消呼叫）
			pd.put("user_kehu_fid", kehu.getUser_kehu_id());
			h5KeHuService.savetongchengCar(pd);
			mv.addObject("information_tongcheng_id", information_tongcheng_id);
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		mv.setViewName("redirect:/api/h5KeHu/tongchenghujiao");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jump", "1");//1跳转到司机端（打车拼车）同城新订单列表
		map.put("mp3", "xxx.mp3");
		map.put("notification_title", "同城打车拼车有新订单了！");
		map.put("msg_title", "同城打车有新订单了！");
		String extrasparam = new Gson().toJson(map);
		JpushClientUtil.sendToAllSiji("同城打车拼车有新订单了！", "同城打车有新订单了！", "有新订单了", extrasparam);
		JpushClientUtil.ios_sendToAllSiji("同城打车拼车有新订单了！", "同城打车有新订单了！", "有新订单了", extrasparam);
		System.out.println(".........客户点击同城呼叫后+执行推送给全部司机用户消息（安卓和ios）.........");
		return mv;
	}
	
	/**
	 *  同城呼叫
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/tongchenghujiao")
	public ModelAndView tongchenghujiao() throws Exception{
		logBefore(logger, "----tongchenghujiao--同城呼叫-----");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			PageData pds = h5KeHuService.getTongchengCar(pd);//根据id查询一条同城信息大约金额
			if (pds.getString("hujiao_status").equals("2")) {
				mv.setViewName("kehuduan/taxi/tongchenghujiaos");
			}
			mv.addObject("respCode", "01");
			mv.addObject("information_tongcheng_id", pd.getString("information_tongcheng_id"));
			mv.addObject("pds", pds);
			mv.setViewName("kehuduan/taxi/tongchenghujiao");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 *  30秒请求一次查询司机是否接单了
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/tongchenghuStatus")
	@ResponseBody
	public Object tongchenghuStatus() throws Exception{
		logBefore(logger, "----tongchenghuStatus--30秒请求一次查询司机是否接单了-----");
		ModelAndView mv=new ModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			PageData pds = h5KeHuService.getTongchengCar(pd);//根据id查询一条同城信息大约金额
			if (pds.getString("hujiao_status").equals("2")) {
				map.put("respCode", "02");
				map.put("information_tongcheng_id", pd.getString("information_tongcheng_id"));
				map.put("pds", pds);
				System.out.println("02 司机正在赶来");
			}else{
				System.out.println("03等待司机接驾");
				map.put("respCode", "03");
			}
			
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 *  30秒请求一次查询司机是否接驾到乘客
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/orderStatusjiedao")
	@ResponseBody
	public Object orderStatusjiedao() throws Exception{
		logBefore(logger, "----orderStatusjiedao--30秒请求一次查询司机是否接驾到乘客-----");
		ModelAndView mv=new ModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			PageData pds = h5KeHuService.getTongchengCar(pd);//根据id查询一条同城信息
				if(pds.getString("order_tongcheng_status").equals("4")){
					map.put("respCode", "04");
					map.put("information_tongcheng_id", pd.getString("information_tongcheng_id"));
					map.put("pds", pds);
					System.out.println("04司机已接到乘客");
				}else{
					map.put("respCode", "05");
					System.out.println("05等待司机接驾");
				}
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 *  司机正在赶来界面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/tongchenghujiaos")
	public Object tongchenghujiaos() throws Exception{
		logBefore(logger, "----tongchenghujiaos--司机正在赶来界面-----");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			PageData pds = h5KeHuService.getTongchengCar(pd);//根据id查询一条同城信息
			if (pds.getString("order_tongcheng_status").equals("1")) {
				mv.setViewName("kehuduan/taxi/tongchenghujiaos");
				mv.addObject("information_tongcheng_id", pd.getString("information_tongcheng_id"));
				mv.addObject("pds", pds);
				mv.addObject("respCode", "01");
				System.out.println("司机正在赶来....");
			}else if (pds.getString("order_tongcheng_status").equals("4")) {
				mv.setViewName("kehuduan/taxi/tongchenghujiaosi");
				mv.addObject("information_tongcheng_id", pd.getString("information_tongcheng_id"));
				mv.addObject("pds", pds);
				System.out.println("司机已接到乘客....");
			}
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	/**
	 * 选择支付方式同城打车
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/topaytcdc")
	public Object topaytcdc() throws Exception{
		logBefore(logger, "----topaytcdc--选择支付方式同城打车-----");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("order_tongcheng_id", pd.getString("order_tongcheng_id"));
		PageData OrderTongCheng = sijiOrderTongChengService.queryOrderTongCheng(pd);//同城订单详情
		mv.addObject("order_tongcheng_id", pd.getString("order_tongcheng_id"));
		mv.addObject("radeAmount", pd.getString("radeAmount"));
		mv.addObject("pds", OrderTongCheng);
		mv.setViewName("kehuduan/taxi/pay_order");
		return mv;
	}
	
	/**
	 * 执行取消呼叫同城订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="SetorderStatus")
	public ModelAndView SetorderStatus() throws Exception{
		logBefore(logger, "------SetorderStatus---执行取消呼叫同城订单---");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("information_tongcheng_id", pd.getString("information_tongcheng_id"));
			//获取司机id
			PageData pds = h5KeHuService.getUserSijiFid(pd);
			pds.put("user_siji_id", pds.getString("user_siji_fid"));
			
			PageData sData = h5KeHuService.queryRegistrationID(pds);
			String registrationId =  sData.getString("RegistrationID");//设备标识
			Map<String,Object> map = new HashMap<String,Object>();
			if (sData.getString("RegistrationType").equals("ios")) {
				map.put("jump", "3");//3跳转到商家新订单
				map.put("mp3", "xxx.mp3");
				map.put("notification_title", "很抱歉，同城订单已取消！");
				map.put("msg_title", "同城订单已取消给您带来不便，敬请您谅解！");
				String extrasparam = new Gson().toJson(map);
				JpushClientUtil.ios_siji_sendToRegistrationId(registrationId, "很抱歉，同城订单已取消！", "同城订单已取消给您带来不便，敬请您谅解！", "谢谢", extrasparam);
				System.out.println(".........客户已取消订同城单+执行推送给指定司机(ios).........");
			}else {
				map.put("jump", "3");//3跳转到商家新订单
				map.put("mp3", "xxx.mp3");
				map.put("notification_title", "很抱歉，同城订单已取消！");
				map.put("msg_title", "同城订单已取消给您带来不便，敬请您谅解！");
				String extrasparam = new Gson().toJson(map);
				JpushClientUtil.siji_sendToRegistrationId(registrationId, "很抱歉，同城订单已取消！", "同城订单已取消给您带来不便，敬请您谅解！", "谢谢", extrasparam);
				System.out.println(".........客户已取消订同城单+执行推送给指定司机(安卓).........");
			}
			PageData pdshujiaoStatus = h5KeHuService.getTongchengCar(pd);//根据id查询一条同城信息
			if (pdshujiaoStatus.getString("hujiao_status").equals("2")) {
				pd.put("hujiao_status", 2);
				h5KeHuService.cancelTongchenghujiaoDelete(pd);//当客户点击取消删除同城过度信息状态为2的信息
				h5KeHuService.SetorderStatus(pd);//当客户点击取消删除订单表。
				h5KeHuService.deleteSetorderStatus(pd);//当客户点击取消删除已接驾订单表。
			}
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		mv.setViewName("redirect:/api/h5KeHu/tongchengCar");
		return mv;
	}
	
	/**
	 * 执行呼叫超时，请重新呼叫！
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/chongxinTongchenghujiao")
	public ModelAndView chongxinTongchenghujiao() throws Exception{
		logBefore(logger, "------chongxinTongchenghujiao---呼叫超时，请重新呼叫！---");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("information_tongcheng_id", pd.getString("information_tongcheng_id"));
			
			//获取司机id
			PageData pds = h5KeHuService.getUserSijiFid(pd);
			if (pds != null) {
				pds.put("user_siji_id", pds.getString("user_siji_fid"));
				PageData sData = h5KeHuService.queryRegistrationID(pds);
				String registrationId =  sData.getString("RegistrationID");//设备标识
				Map<String,Object> map = new HashMap<String,Object>();
				if (sData.getString("RegistrationType").equals("ios")) {
					map.put("jump", "3");//3跳转到商家新订单
					map.put("mp3", "xxx.mp3");
					map.put("notification_title", "很抱歉，同城订单已取消！");
					map.put("msg_title", "同城订单已取消给您带来不便，敬请您谅解！");
					String extrasparam = new Gson().toJson(map);
					JpushClientUtil.ios_siji_sendToRegistrationId(registrationId, "很抱歉，同城订单已取消！", "同城订单已取消给您带来不便，敬请您谅解！", "谢谢", extrasparam);
					System.out.println(".........客户已取消订同城单+执行推送给指定司机(ios).........");
				}else{
					map.put("jump", "3");//3跳转到商家新订单
					map.put("mp3", "xxx.mp3");
					map.put("notification_title", "很抱歉，同城订单已取消！");
					map.put("msg_title", "同城订单已取消给您带来不便，敬请您谅解！");
					String extrasparam = new Gson().toJson(map);
					JpushClientUtil.siji_sendToRegistrationId(registrationId, "很抱歉，同城订单已取消！", "同城订单已取消给您带来不便，敬请您谅解！", "谢谢", extrasparam);
					System.out.println(".........客户已取消订同城单+执行推送给指定司机(an).........");
				}
			}
			pd.put("hujiao_status", 0);
			h5KeHuService.cancelTongchenghujiaoDelete(pd);
			h5KeHuService.DeletehujiaoStatusTow(pd);
			h5KeHuService.SetorderStatus(pd);//当客户点击取消删除订单表。1
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		mv.setViewName("redirect:/api/h5KeHu/tongchengCar");
		return mv;
	}
	
	/**
	 * 执行删除呼叫同城信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cancelTongchenghujiaoDelete")
	public ModelAndView cancelTongchenghujiao() throws Exception{
		logBefore(logger, "------cancelTongchenghujiaoDelete---执行删除呼叫同城信息---");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("information_tongcheng_id", pd.getString("information_tongcheng_id"));
			
			//获取司机id
			PageData pds = h5KeHuService.getUserSijiFid(pd);
			pds.put("user_siji_id", pds.getString("user_siji_fid"));
			PageData sData = h5KeHuService.queryRegistrationID(pds);
			String registrationId =  sData.getString("RegistrationID");//设备标识
			Map<String,Object> map = new HashMap<String,Object>();
			if (sData.getString("RegistrationType").equals("ios")) {
				map.put("jump", "3");//3跳转到商家新订单
				map.put("mp3", "xxx.mp3");
				map.put("notification_title", "很抱歉，同城订单已取消！");
				map.put("msg_title", "同城订单已取消给您带来不便，敬请您谅解！");
				String extrasparam = new Gson().toJson(map);
				JpushClientUtil.ios_siji_sendToRegistrationId(registrationId, "很抱歉，同城订单已取消！", "同城订单已取消给您带来不便，敬请您谅解！", "谢谢", extrasparam);
				System.out.println(".........客户已取消订同城单+执行推送给指定司机(ios).........");
				
			}else {
				map.put("jump", "3");//3跳转到商家新订单
				map.put("mp3", "xxx.mp3");
				map.put("notification_title", "很抱歉，同城订单已取消！");
				map.put("msg_title", "同城订单已取消给您带来不便，敬请您谅解！");
				String extrasparam = new Gson().toJson(map);
				JpushClientUtil.siji_sendToRegistrationId(registrationId, "很抱歉，同城订单已取消！", "同城订单已取消给您带来不便，敬请您谅解！", "谢谢", extrasparam);
				System.out.println(".........客户已取消订同城单+执行推送给指定司机(an).........");
			}
			
			PageData pdshujiaoStatus = h5KeHuService.getTongchengCar(pd);//根据id查询一条同城信息
			if (pdshujiaoStatus.getString("hujiao_status").equals("0")) {
				pd.put("hujiao_status", 0);
				h5KeHuService.cancelTongchenghujiaoDelete(pd);
			}else if(pdshujiaoStatus.getString("hujiao_status").equals("2")) {
				pd.put("hujiao_status", 2);
				h5KeHuService.cancelTongchenghujiaoDelete(pd);//当客户点击取消删除同城过度信息状态为2的信息
				h5KeHuService.SetorderStatus(pd);//当客户点击取消删除订单表。1
				h5KeHuService.deleteSetorderStatus(pd);//当客户点击取消删除已接驾订单表。
			}
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		mv.setViewName("redirect:/api/h5KeHu/tongchengCar");
		return mv;
	}
	
	/**
	 * 去长途拼车列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/changtuCar")
	public ModelAndView changtuCar(Page page) throws Exception{
		logBefore(logger, "--changtuCar---去长途拼车列表页面-----");
		ModelAndView mv = new  ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("user_kehu_fid", getSessionUser().getUser_kehu_id());
			PageData weifukuanchangtu = h5KeHuService.getorderChangtu(pd);//长途订单
			if (weifukuanchangtu == null) {//判断长途订单是否完成
				List<PageData> changtuCarList = sijiInformationChangTuService.querySijiInformationChangTuPage(pd);//查看司机发布的长途路线列表
				List<PageData>  resultLists = new ArrayList<PageData>();
			    for (int i=0;i<changtuCarList.size();i++) {
			    	PageData Lists = new PageData();
			    	Lists.put("departureCity",changtuCarList.get(i).get("departureCity"));
			    	Lists.put("arrivalCity",changtuCarList.get(i).get("arrivalCity"));
			    	Lists.put("information_changtu_id",changtuCarList.get(i).get("information_changtu_id"));
			    	Lists.put("userName",changtuCarList.get(i).get("userName"));
			    	Lists.put("phone",changtuCarList.get(i).get("phone"));
			    	Lists.put("departureTime",changtuCarList.get(i).get("departureTime"));
			    	Lists.put("userNum",changtuCarList.get(i).get("userNum"));
			    	Lists.put("departurePlace",changtuCarList.get(i).get("departurePlace"));
			    	Lists.put("destination",changtuCarList.get(i).get("destination"));
			    	Lists.put("releaseTime", Tools.getTimes(changtuCarList.get(i).get("releaseTime").toString()));
			    	Lists.put("carpoolFee", changtuCarList.get(i).get("carpoolFee"));
			    	resultLists.add(Lists);
				}
				mv.addObject("changtuCarList",resultLists);
				mv.setViewName("kehuduan/taxi/changtuCar");
			}else{
				System.out.println("03"+"长途");
				mv.addObject("respCode", "03");
				List<PageData> changtuCarList = sijiInformationChangTuService.querySijiInformationChangTuPage(pd);//查看司机发布的长途路线列表
				List<PageData>  resultLists = new ArrayList<PageData>();
			    for (int i=0;i<changtuCarList.size();i++) {
			    	PageData Lists = new PageData();
			    	Lists.put("departureCity",changtuCarList.get(i).get("departureCity"));
			    	Lists.put("arrivalCity",changtuCarList.get(i).get("arrivalCity"));
			    	Lists.put("information_changtu_id",changtuCarList.get(i).get("information_changtu_id"));
			    	Lists.put("userName",changtuCarList.get(i).get("userName"));
			    	Lists.put("phone",changtuCarList.get(i).get("phone"));
			    	Lists.put("departureTime",changtuCarList.get(i).get("departureTime"));
			    	Lists.put("userNum",changtuCarList.get(i).get("userNum"));
			    	Lists.put("departurePlace",changtuCarList.get(i).get("departurePlace"));
			    	Lists.put("destination",changtuCarList.get(i).get("destination"));
			    	Lists.put("releaseTime", Tools.getTimes(changtuCarList.get(i).get("releaseTime").toString()));
			    	Lists.put("carpoolFee", changtuCarList.get(i).get("carpoolFee"));
			    	resultLists.add(Lists);
				}
				mv.addObject("changtuCarList",resultLists);
				mv.setViewName("kehuduan/taxi/changtuCar");
			}
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 我的长途发布列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/wdfbchangtuCarList")
	public ModelAndView wdfbchangtuCarList(Page page) throws Exception{
		logBefore(logger, "--wdfbchangtuCarList---我的拼车长途发布列表-----");
		ModelAndView mv = new  ModelAndView();
//		PageData pd  = new PageData();
//		pd = this.getPageData();
//		List<PageData> changtuCarList = h5KeHuService.changtuCarList(pd);
		if(isSession()){
			List<PageData> wdfbchangtuList = h5KeHuService.querySijiInformationKeHuChangTuPage(page);//查询我的拼车列表
			mv.addObject("wdfbchangtuList",wdfbchangtuList);
			mv.setViewName("kehuduan/taxi/changtuCars");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 我的长途发布详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/changtuXiangdan")
	public ModelAndView changtuXiangdan(HttpSession session) throws Exception{
		logBefore(logger, "--changtuXiangdan---我的长途发布详情-----");
		ModelAndView mv = new  ModelAndView();
		PageData pd  = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("information_kehu_changtu_id", pd.getString("information_kehu_changtu_id"));
			PageData changtuXiangdan = h5KeHuService.changtuXiangdan(pd);//我的长途发布详情
			mv.addObject("changtuXiangdan",changtuXiangdan);
		}else {
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		mv.setViewName("kehuduan/taxi/changtuXiangdan");
		return mv;
	}
	
	/**
	 * 我要下单页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/chengke")
	public ModelAndView chengke(HttpSession session,Page page) throws Exception{
		logBefore(logger, "------chengke---我要下单页面---");
		ModelAndView mv = new  ModelAndView();
		PageData pd  = new PageData();
		pd = this.getPageData();
		if(isSession()){
			pd.put("information_changtu_fid", pd.getString("information_changtu_id"));
			PageData sData  = h5KeHuService.querysetpincheuserNum(pd);
			if(sData!=null){
				System.out.println(sData.get("pincheUserNum")+"-------客户拼车人数---------");
				System.out.println(pd.getString("userNum")+"----司机发布的拼车人数----");
				mv.addObject("pincheUserNum", sData.get("pincheUserNum"));
			}
			mv.addObject("msg", "savechengkes");
			mv.addObject("id", pd.getString("information_changtu_id"));
			mv.addObject("userNum", pd.getString("userNum"));
			mv.setViewName("kehuduan/taxi/chengke");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	/**
	 * 我要下单拼车人数验证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/checkUserNum")
	@ResponseBody
	public Object checkUserNum() throws Exception{
		logBefore(logger, "------checkUserNum---我要下单拼车人数验证---");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("information_changtu_fid", pd.getString("information_changtu_id"));
		PageData sData  = h5KeHuService.querysetpincheuserNum(pd);
		if(sData!=null){
			map.put("respCode", "01");
			map.put("pincheUserNum", sData.get("pincheUserNum"));
		}else {
			map.put("respCode", "00");
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 保存我的拼车人数
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/savechengkes")
	public ModelAndView chengkes(HttpSession session) throws Exception{
		logBefore(logger, "------savechengkes---保存我的拼车人数---");
		ModelAndView mv = new  ModelAndView();
		PageData pd  = new PageData();
		pd = this.getPageData();
		KeHu kehu = (KeHu) session.getAttribute("h5User");
		if(isSession()){
			pd.put("tongcheng_pinche_id", this.get32UUID());
			pd.put("information_changtu_fid", pd.getString("information_changtu_id"));
			pd.put("pinche_userNum", pd.getString("renshu"));//拼车人数
			pd.put("user_kehu_fid", kehu.getUser_kehu_id());
			pd.put("create_time", Tools.date2Str(new Date()));
			h5KeHuService.savechengkes(pd);//保存我的拼车人数 
			pd.put("information_changtu_id", pd.getString("information_changtu_id"));
			PageData PostingDetails = sijiInformationChangTuService.queryInformationChangTuID(pd);
			pd.put("order_changtu_id", this.get32UUID());
			pd.put("order_changtu_Time", Tools.date2Str(new Date()));//长途拼车客户下单时间
			pd.put("departureTime", PostingDetails.get("departureTime"));//长途拼车出发时间
			pd.put("departureCity", PostingDetails.getString("departureCity"));//长途拼车出发城市
			pd.put("changtu_departurePlace", PostingDetails.getString("departurePlace"));//长途拼车出发地
			pd.put("arrivalCity", PostingDetails.getString("arrivalCity"));//目的地城市
			pd.put("changtu_destination", PostingDetails.getString("destination"));//目的地
			pd.put("changtu_mileage", PostingDetails.getString("mileage"));//里程数
			pd.put("changtu_radeAmount", PostingDetails.getString("carpoolFee"));//乘车金额
			pd.put("user_siji_fid", PostingDetails.getString("user_fid"));//外键ID(司机端用户主键ID)
			pd.put("user_kehu_fid", kehu.getUser_kehu_id());//外键ID(客户端用户主键ID)
			pd.put("order_changtu_status", 1);//长途订单状态（1-进行中 2-已完成  3-已取消）
			pd.put("userNum",pd.getString("renshu"));//长途订单拼车人数
//			pd.put("information_changtu_fid", pd.getString("information_kehu_changtu_id"));//外键ID(长途拼车信息表的主键ID号)
			orderChangtuService.insertOrderChangTu(pd);//我要下单
			PageData OrderChangTu =	orderChangtuService.queryOrderChangTu(pd);//长途订单详情
			Map<String,Object> map = new HashMap<String,Object>();
			String registrationId =  OrderChangTu.getString("RegistrationID");//设备标识
			map.put("jump", "4");//4跳转到司机长途订单
			map.put("mp3", "xxx.mp3");
			map.put("notification_title", "长途拼车有新消息！");
			map.put("msg_title", "长途拼车有新消息，请您及时联系乘客！");
			String extrasparam = new Gson().toJson(map);
			if (OrderChangTu.getString("RegistrationType").equals("ios")) {
				JpushClientUtil.ios_siji_sendToRegistrationId(registrationId, "长途拼车有新消息！", "长途拼车有新消息，请您及时联系乘客！", "谢谢", extrasparam);
				System.out.println(".........客户已取消订同城单+执行推送给指定司机(ios).........");
			} else {
				JpushClientUtil.siji_sendToRegistrationId(registrationId, "长途拼车有新消息！", "长途拼车有新消息，请您及时联系乘客！", "谢谢", extrasparam);
				System.out.println(".........客户已取消订同城单+执行推送给指定司机(an).........");
			}
			mv.setViewName("redirect:/api/h5KeHu/changtuCar");
		}else {
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	
	/**
	 * 去发布动态页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gofabudongtai")
	public ModelAndView gofabudongtai() throws Exception{
		logBefore(logger, "---gofabudongtai--去发布动态页面---------");
		ModelAndView mv = new  ModelAndView();
		PageData pd  = new PageData();
		pd = this.getPageData();
		if (isSession()) {
			List<PageData> mabiaoList  = h5KeHuService.selectall(pd);//互动社区码表类型
			mv.addObject("mabiaoList", mabiaoList);
			mv.addObject("msg", "savedongtai");
			mv.setViewName("kehuduan/fabudongtai");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	/*
	public Object isDengLuOrNo(){
		
		Map<String, Object> map=new HashMap<String, Object>();
		PageData pd=new PageData();
		
		String respCode="00";
		
		if(isSession()){
			respCode="01";
		}
		
		map.put("respCode", respCode);
		
		return AppUtil.returnObject(pd, map);
	}
 	*/
	
	
	/**
	 * 保存发布动态
	 * @throws Exception
	 */
	@RequestMapping(value="/savedongtai")
	@ResponseBody
	public ModelAndView savefabu(HttpSession session,
			@RequestParam(required=false) MultipartFile[] imgPath,
			@RequestParam(required=false) String isTop,
			@RequestParam(required=false) String content,
			@RequestParam(required=false) String categoryName,
			@RequestParam(required=false) String hudong_category_id
			) throws Exception{
		logBefore(logger, "---savedongtai--保存发布动态--");
		ModelAndView mv=new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String hudong_dongtai_id = this.get32UUID();
		String fabuFilePath = "dongtai/";
		String ffile = DateUtil.getDays(), fileName = "";
		System.out.println("--------长度-------"+imgPath.length);
		if (isSession()) {
			if(imgPath[0].getSize()>0){
				for(int i=0;i<imgPath.length;i++){
					if (imgPath[i]!=null && !imgPath[i].isEmpty()) {
						String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + fabuFilePath + ffile;		//文件上传路径
						fileName = FileUpload.fileUp(imgPath[i], filePath, this.get32UUID());	
						System.out.println("================进来了========");
						//添加到数据库
						pd.put("hudong_img_id", this.get32UUID());
						pd.put("imgPath", Const.FILEPATHIMG + fabuFilePath  + ffile+"/" + fileName);
						pd.put("hudong_dongtai_fid", hudong_dongtai_id);
						pd.put("create_time", DateUtil.getTime());//创建时间
						h5KeHuService.savefile(pd);
					}
				}
			}else{
				System.out.println("=======图片文件为空======");
			}
			KeHu kehu = (KeHu) session.getAttribute("h5User");
			pd.put("user_kehu_fid", kehu.getUser_kehu_id());
			pd.put("hudong_dongtai_id", hudong_dongtai_id);
			pd.put("categoryName", categoryName);
			pd.put("isTop", isTop);//是否置顶(0-不置顶，1-置顶)
			pd.put("content", content);
			pd.put("hudong_category_fid", hudong_category_id);
			pd.put("fabuTime", DateUtil.getTime());//发布时间
			h5KeHuService.savefabu(pd);
			mv.setViewName("redirect:/api/h5KeHu/hdsq");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	
	/**
	 * 在线支付
	 * @return
	 */
	@RequestMapping(value="/zaixianZhifu")
	public ModelAndView zaixianZhifu(){
		logBefore(logger, "---zaixianZhifu--在线支付--");
		ModelAndView mv=new ModelAndView();
		if(isSession()){
			mv.setViewName("kehuduan/Interaction/zaixianZhifu");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/index");
		}
		return mv;
	}
	
	
	/**
	 * 判断一串字符串中是否含有非数字字符
	 * 无：返回TRUE
	 * 有：返回FALSE
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断session是否存在
	 * 存在：返回TRUE
	 * 反之：返回FALSE
	 * @return
	 */
	public boolean isSession(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		KeHu keHu=(KeHu) session.getAttribute("h5User");
		if(keHu==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取session对象
	 * @return
	 */
	public KeHu getSessionUser(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		KeHu KeHu=(KeHu) session.getAttribute("h5User");
		return KeHu;
	}
	
	/**
	 * 创建用户session
	 * @throws Exception 
	 */
	public void createSession(String user_shanghu_id) throws Exception{
		PageData pd=new PageData();
		pd.put("user_shanghu_id", user_shanghu_id);
		PageData tempData=h5KeHuService.getDataByNameOrPhone(pd);
		KeHu kehu=new KeHu();
		kehu.setUser_kehu_id(tempData.getString("user_kehu_id"));
		kehu.setPhone(tempData.getString("phone"));
		kehu.setUserName(tempData.getString("userName"));
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		session.setAttribute("h5User",kehu);
	}
	
	
	/**
	 * 进入个人中心中的设置页面（个人资料页面）
	 * @return
	 */
	@RequestMapping(value="/toPersonCenterSetting")
	public ModelAndView toPersonCenterSetting(){
		logBefore(logger, "--进入个人中心中的设置页面（个人资料页面）--");
		ModelAndView mv=new ModelAndView();
		if(isSession()){
			mv.setViewName("kehuduan/address_add");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
		return mv;
	}
	
	/**
	 * 
	 * 退出 销毁session
	 * 
	 */
	public void removeSession(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		session.removeAttribute("h5User");
	}
	
	
	/**
	 * 退出注销 
	 * @return
	 */
	@RequestMapping(value="/gotuichu")
	public ModelAndView gotuichu(){
		logBefore(logger, "--退出注销--");
		ModelAndView mv = new  ModelAndView();
		//销毁session
		removeSession();
		mv.setViewName("kehuduan/index");
		return mv;
	}
	
	
	
	
	/**
	 * 退出，注销 清除用户登录信息缓存
	 * @throws Exception 
	 */
	@RequestMapping(value="/tuichu")
	@ResponseBody
	public Object clearLoginInfo(HttpServletRequest request){
		logBefore(logger, "---清除用户登录信息--");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		
		try {
			if(Tools.isEmpty(pd.getString("backCode"))){
				map.put("msg", "backCode不能为空");
			}else{
				h5KeHuService.deleteCacheInfo(pd);
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
