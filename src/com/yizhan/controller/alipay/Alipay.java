package com.yizhan.controller.alipay;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yizhan.controller.alipay.config.AlipayConfig;
import com.yizhan.controller.alipay.util.AlipayNotify;
import com.yizhan.controller.alipay.util.AlipaySubmit;
import com.yizhan.controller.app.jpushModel.JpushClientUtil;
import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.information.KeHu;
import com.yizhan.entity.information.ZywKeHu;
import com.yizhan.service.information.h5kehu.H5KeHuService;
import com.yizhan.service.information.orderKehu.OrderService;
import com.yizhan.service.information.zhaoyiwang.KeHuService;
import com.yizhan.service.information.zhaoyiwang.ShangHuService;
import com.yizhan.util.Const;
import com.yizhan.util.DateUtil;
import com.yizhan.util.PageData;
import com.yizhan.util.TimeStramp;
import com.yizhan.util.Tools;
/**
 * 调用支付宝支付
 * 功能：
 * 作者： lj
 * date：2017-8-2
 *
 */
@Controller
@RequestMapping("/api/alipay")
public class Alipay extends BaseController{
	//double保留两位小数
	private DecimalFormat df = new DecimalFormat("#0.0000");
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="h5KeHuService")
	private H5KeHuService h5KeHuService;
	
	/**
	 * 唤起手机支付宝APP
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/alipay")  
    public ModelAndView payConfirm(HttpServletRequest request,HttpSession session) throws Exception{ 
		logBefore(logger, "--唤起手机支付宝APP--");
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		//生成session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session2 = currentUser.getSession();
		KeHu KeHu=(KeHu) session2.getAttribute("h5User");
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =TimeStramp.getOrderNum();
        //订单名称，必填
        String subject ="外卖顺风车：购买商品";
        //付款金额，必填pd.getString("totol");
        String total_fee ="0.01";
        //支付方式
        String zf_type="";
        if(pd.get("zf_type").equals("1")){
        	zf_type="微信";
        }else{
        	zf_type="支付宝";
        }
        
        //收银台页面上，商品展示的超链接，必填
        String show_url = request.getParameter("WIDshow_url");
        //商品描述，可空request.getParameter("WIDbody");
        String body = "这是外卖顺丰车的购买商品支付宝支付过程";
        System.out.println("====参数out_trade_no="+out_trade_no+",subject="+subject+",total_fee="+total_fee+",show_url="+show_url+",body="+body);
        
        //更新订单号到订单表中
        pd.put("payMethod", zf_type);//支付方式
        pd.put("orderNumber", out_trade_no);
        pd.put("user_kehu_id", KeHu.getUser_kehu_id());
        h5KeHuService.updateOrderNumber(pd);
        
        //把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("app_pay","y");//启用此参数可唤起钱包APP支付。
		sParaTemp.put("body", body);
		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
        //如sParaTemp.put("参数名","参数值");
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
		System.out.println("================sHtmlText:===="+sHtmlText);
		mv.addObject("sHtmlText", sHtmlText);
		mv.setViewName("alipay/alipay");
		return mv;
    }  
	
	/**
	 * 支付成功回调（异步通知）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notify_url")
	public void notify_url(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logBefore(logger, "--支付成功回调（异步通知）--");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();){
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		System.out.println("=========================================支付成功回调（异步通知）参数params="+params);
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		//if(verify_result){//验证成功
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				
				System.out.println("---------------异步通知支付成功--------------------");
				System.out.println("---------------异步通知支付成功--------------------");
			}else{
				System.out.println("-----------------异步通知支付失败----------------");
			}
		
			System.out.println("---------验证成功---------");
			response.getWriter().write("success");
		//}else{
			//System.out.println("验证失败");
		//}
	}
	/**
	 * 支付成功回调（同步通知）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/return_url")
	public ModelAndView return_url(HttpServletRequest request,HttpSession session) throws Exception{
		logBefore(logger, "--支付成功回调（同步通知）--");
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		System.out.println("++++++++++++++++++++++++++++++++支付成功回调（同步通知）参数:"+params);
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		//if(verify_result){//验证成功
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//成功要做的事  插入订单 或修改订单信息
				System.out.println("---------------同步通知支付成功--------------------");
				System.out.println("---------------同步通知支付成功--------------------");
				System.out.println("---------------同步通知支付成功--------------------");
				
				//现在现在在同步中进行订单的操作，正式发布到外网上移到异步通知处做，同步通知内只做页面跳转即可
				pd.put("payState", "已支付");
				pd.put("orderStateKehu", "2");
				pd.put("orderNumber", out_trade_no);
				h5KeHuService.updateOrderInfoByOrderNumber(pd);
				
				//根据外卖订单编号获取查询商家的id
				PageData pds = h5KeHuService.geuserShangjiaFid(pd);
				pds.put("user_shangjia_id", pds.getString("user_shangjia_fid"));
				//获取商家信息
				PageData sData = h5KeHuService.queryUserShangjia(pds);
				String registrationId =  sData.getString("RegistrationID");//设备标识
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("jump", "1");//1跳转到商家新订单
				map.put("mp3", "xxx.mp3");
				map.put("notification_title", "订单大厅有新订单！");
				map.put("msg_title", "客户已下单，请查收后并受理！");
				String extrasparam = new Gson().toJson(map);
				if (sData.getString("RegistrationType").equals("ios")) {
					JpushClientUtil.ios_sj_sendToRegistrationId(registrationId, "订单大厅有新订单！", "客户已下单，请查收后并受理！", "您有新订单", extrasparam);
					System.out.println(".........客户付款后后+执行推送给指定商家消息！(ios).........");
					mv.addObject("order_takeou_id", pds.getString("order_takeou_id"));
				} else {
					JpushClientUtil.sj_sendToRegistrationId(registrationId, "订单大厅有新订单！", "客户已下单，请查收后并受理！", "您有新订单", extrasparam);
					System.out.println(".........客户付款后后+执行推送给指定商家消息！(an).........");
					mv.addObject("order_takeou_id", pds.getString("order_takeou_id"));
				}
				mv.setViewName("alipay/alipayreturn2");
			}else{
				System.out.println("-----------------同步通知支付失败----------------");
				System.out.println("-----------------同步通知支付失败----------------");
				System.out.println("-----------------同步通知支付失败----------------");
			}
			System.out.println("验证成功");
		//}else{
			//System.out.println("验证失败");
		//}
		return mv;
	}
///////////////////////////////////////////////////以下为在微信浏览器中选择支付宝支付时的方法/////////////////////////////////////////////////////////////	
	// 请在菜单中选择在浏览器中打开
	@RequestMapping(value="pay")
	public ModelAndView pay(HttpSession session) throws Exception{
		logBefore(logger, "--请在菜单中选择在浏览器中打开--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		//生成session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session2 = currentUser.getSession();
		KeHu KeHu=(KeHu) session2.getAttribute("h5User");
		
		String out_trade_no =TimeStramp.getOrderNum();
		
		//更新订单号到订单表中
        pd.put("payMethod", "支付宝");//支付方式
        pd.put("orderNumber", out_trade_no);
        pd.put("user_kehu_id", KeHu.getUser_kehu_id());
        h5KeHuService.updateOrderNumber(pd);
		
		//pd.put("order_number", pd.getString("num"));
		//PageData tempIsOrNo=orderService.get_tb_temp_information(pd);
		/*if(tempIsOrNo==null){
			//获取客户信息
			//保存临时信息
	      	PageData temp=new PageData();
	      	temp.put("temp_id", this.get32UUID());
	      	temp.put("order_number", pd.getString("WIDout_trade_no"));
	      	temp.put("fuwu_id", pd.getString("fuwu_id"));
	      	temp.put("xingming", pd.getString("xingming"));
	      	temp.put("dianhua", pd.getString("dianhua"));
	      	temp.put("dizhi", pd.getString("dizhi"));
	      	temp.put("beizhu", pd.getString("beizhu"));
	      	temp.put("number", pd.getString("number"));
	      	
	      	temp.put("WIDsubject", pd.getString("WIDsubject"));
	      	temp.put("WIDtotal_fee", pd.getString("WIDtotal_fee"));
	      	temp.put("WIDbody", pd.getString("WIDbody"));
	      	temp.put("WIDshow_url", pd.getString("WIDshow_url"));

	      	orderService.save_tb_temp_information1(temp);
	      	mv.setViewName("alipay/pay");
		}else{
			mv.setViewName("redirect:/api/alipay/demo_post?order_number="+pd.getString("num"));
		}*/
		mv.setViewName("alipay/pay");
		return mv;
	}
	
	//在浏览器中打开，确认支付页面
	@RequestMapping(value="/demo_post")
	public ModelAndView demo_post() throws Exception{
		logBefore(logger, "--在浏览器中打开，确认支付页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		//根据订单编号，获取临时保存的信息
		PageData temp=orderService.get_tb_temp_information(pd);
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("out_trade_no", temp.getString("order_number"));
		sParaTemp.put("subject", temp.getString("WIDsubject"));
		sParaTemp.put("total_fee", temp.getString("WIDtotal_fee"));
		sParaTemp.put("show_url", temp.getString("WIDshow_url"));
		sParaTemp.put("app_pay","y");//启用此参数可唤起钱包APP支付。
		sParaTemp.put("body", temp.getString("WIDbody"));
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认支付");
		System.out.println("================sHtmlText:===="+sHtmlText);

		mv.addObject("sHtmlText", sHtmlText);
		mv.addObject("temp", temp);
		mv.setViewName("alipay/alipay");
		return mv;
	}
}
