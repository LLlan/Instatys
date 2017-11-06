package com.yizhan.controller.alipay;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yizhan.controller.alipay.config.AlipayConfig;
import com.yizhan.controller.alipay.util.AlipayNotify;
import com.yizhan.controller.alipay.util.AlipaySubmit;
import com.yizhan.controller.app.jpushModel.JpushClientUtil;
import com.yizhan.controller.base.BaseController;
import com.yizhan.service.information.siJi.SijiService;
import com.yizhan.service.information.sijiOrderTongCheng.SijiOrderTongChengService;
import com.yizhan.util.FormateUtil;
import com.yizhan.util.PageData;
/**
 * 调用支付宝支付
 * 功能：
 * 作者： lj
 * date：2017-8-2
 *
 */
@Controller
@RequestMapping("/api/alipay_tcdc")
public class Alipay_tcdc extends BaseController{
	//double保留两位小数
	//private DecimalFormat df = new DecimalFormat("#0.00");
	
	@Resource(name="sijiOrderTongChengService")
	private SijiOrderTongChengService sijiOrderTongChengService;
	
	@Resource(name="sijiUserService")
	private SijiService sijiUserService;
	
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
		PageData pd = new PageData();
		
		//获取页面传递过来的该订单的主键ID以及订单编号
        String order_tongcheng_id = request.getParameter("order_tongcheng_id");
        String orderNumber = request.getParameter("orderNumber");
		
        //根据订单的主键ID号更新订单编号
        pd.put("order_tongcheng_id", order_tongcheng_id);
        pd.put("orderNumber", orderNumber);
        sijiOrderTongChengService.updateOrderTongChengOrderNumberById(pd);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =request.getParameter("orderNumber");
        //订单名称，必填
        String subject ="外卖顺风车：同城打车";
        //付款金额，必填request.getParameter("total_fee");
        String total_fee ="0.01";
        
        //收银台页面上，商品展示的超链接，
        String show_url = request.getParameter("WIDshow_url");
        //商品描述，可空request.getParameter("WIDbody");
        String body = "这是外卖顺丰车的购买商品支付宝支付过程";
        System.out.println("====参数out_trade_no="+out_trade_no+",subject="+subject+",total_fee="+total_fee+",show_url="+show_url+",body="+body);
        
        //把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url_tcdc);
		sParaTemp.put("return_url", AlipayConfig.return_url_tcdc);
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
		//boolean verify_result = AlipayNotify.verify(params);
		//if(verify_result){//验证成功
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//成功要做的事  插入订单 或修改订单信息
				System.out.println("---------------同步通知支付成功--------------------");
				System.out.println("---------------同步通知支付成功--------------------");
				System.out.println("---------------同步通知支付成功--------------------");
				
				//现在现在在同步中进行订单的操作，正式发布到外网上移到异步通知处做，同步通知内只做页面跳转即可
				//1507bfd3f7f2213c435  1507bfd3f7f2213c435
				pd.put("order_tongcheng_status", "5");
				pd.put("payMethod", "支付宝");
				pd.put("orderNumber", out_trade_no);
				sijiOrderTongChengService.updateTongChengOrder(pd);
				//1.获取订单信息
				PageData pd2 =new PageData();
				pd2.put("orderNumber", out_trade_no);
				pd2=sijiOrderTongChengService.getTongChengOrderInfoByOrderNumber(pd2);
				
				//2.获取司机信息
				PageData pd3=new PageData();
				pd3.put("user_siji_id", pd2.getString("user_siji_fid"));
				pd3=sijiOrderTongChengService.getSiJiInforById(pd3);
				
				//3.计算司机最终资产和余额
				//double Amount=Double.parseDouble(pd3.getString("Amount")) + Double.parseDouble(pd2.getString("radeAmount")) * (1-Double.parseDouble(pd2.getString("fuwubili")));
				//double totalassets=Double.parseDouble(pd3.getString("totalassets")) + Double.parseDouble(pd2.getString("radeAmount")) * (1-Double.parseDouble(pd2.getString("fuwubili")));
				
				//4.更新司机信息
				//pd3.put("Amount", FormateUtil.formateDoubleAsIntPointString(Amount));
				//pd3.put("totalassets", FormateUtil.formateDoubleAsIntPointString(totalassets));
				//sijiOrderTongChengService.updateSiJiInforById(pd3);
				//end,正式发布到外网上移到异步通知处做
				
				//根据订单号查询订单信息,获取该订单的主键ID号
				PageData pd1 =new PageData();
				pd1.put("orderNumber", out_trade_no);
				pd1=sijiOrderTongChengService.getTongChengOrderInfoByOrderNumber(pd1);
				mv.addObject("order_tongcheng_id", pd1.getString("order_tongcheng_id"));
				
				PageData sData = sijiUserService.querySijiRegistrationID(pd3);
				String registrationId = sData.getString("RegistrationID");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("jump", "2");//2跳转到司机端（全部订单）同城订单列表
				map.put("mp3", "xxx.mp3");
				map.put("notification_title", "同城订单支付成功！");
				map.put("msg_title", "请您查收，谢谢！");
				String extrasparam = new Gson().toJson(map);
				if (sData.getString("RegistrationType").equals("ios")) {
					//支付成功后，获取司机的设备ID，推送给指定的司机。
					JpushClientUtil.ios_siji_sendToRegistrationId(registrationId, "同城订单支付成功！", "请您查收，谢谢！", "支付成功！", extrasparam);
					System.out.println(".........客户付款后+执行推送给指定司机消息！(ios).........");
				} else {
					//支付成功后，获取司机的设备ID，推送给指定的司机。
					JpushClientUtil.siji_sendToRegistrationId(registrationId, "同城订单支付成功！", "请您查收，谢谢！", "支付成功！", extrasparam);
					System.out.println(".........客户付款后+执行推送给指定司机消息！(an).........");
				}
				
				//计算司机每单应该获得资产和余额，今日输入
				PageData OrderTongCheng = sijiOrderTongChengService.queryOrderTongCheng(pd1);//同城订单详情
				//pd.put("user_siji_fid", cacheData.getString("user_siji_fid"));
				PageData pd33 =new PageData();
				pd33.put("user_siji_fid", pd3.get("user_siji_id"));
				PageData Account = sijiUserService.queryAccountbalance(pd33);
				//计算司机每单应该获得的钱  = 乘客乘车金额 -（乘客乘车金额*服务比例）
				DecimalFormat df = new DecimalFormat("######0.00");  
				double money=Double.parseDouble(OrderTongCheng.getString("radeAmount"))-(Double.parseDouble(OrderTongCheng.getString("radeAmount"))*Double.parseDouble(OrderTongCheng.getString("fuwubili")));
				String strMoney = df.format(money);
				System.out.println(df.format(Double.parseDouble(OrderTongCheng.getString("radeAmount"))));
				System.out.println(strMoney);
				pd33.put("Incometoday",df.format(Double.parseDouble(Account.getString("Incometoday")) + Double.parseDouble(strMoney)));//今日收入 + 司机收益
				pd33.put("Amount", df.format(Double.parseDouble(Account.getString("Amount")) + Double.parseDouble(strMoney)));//我的账户余额 + 司机收益
				pd33.put("totalassets",df.format(Double.parseDouble(Account.getString("totalassets")) + Double.parseDouble(strMoney)));//总资产 +  司机收益
				sijiUserService.setMywallet(pd33);//存入账户余额和今日收入、总资产
				
				mv.setViewName("alipay/alipayreturntcdc");
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
	public ModelAndView pay() throws Exception{
		logBefore(logger, "--请在菜单中选择在浏览器中打开--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		//页面传值订单编号和订单的主键ID
		
		//根据订单编号获取 订单信息
		PageData temPageData=sijiOrderTongChengService.getTongChengOrderInfoByOrderNumber(pd);
		
		if(temPageData==null){
			//根据订单的主键ID号更新订单编号
	        sijiOrderTongChengService.updateOrderTongChengOrderNumberById(pd);
			mv.setViewName("alipay/pay");
		}else{
			mv.setViewName("redirect:/api/alipay_tcdc/demo_post?orderNumber="+pd.getString("orderNumber"));
		}
		
		return mv;
	}
	
	//在浏览器中打开，确认支付页面
	@RequestMapping(value="/demo_post")
	public ModelAndView demo_post() throws Exception{
		logBefore(logger, "--在浏览器中打开，确认支付页面--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		//根据订单的编号获取该订单的信息
		PageData temp=sijiOrderTongChengService.getTongChengOrderInfoByOrderNumber(pd);
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url_tcdc);
		sParaTemp.put("return_url", AlipayConfig.return_url_tcdc);
		sParaTemp.put("out_trade_no", temp.getString("orderNumber"));
		sParaTemp.put("subject", "外卖顺风车：同城打车");
		//sParaTemp.put("total_fee", temp.getString("radeAmount"));
		sParaTemp.put("total_fee", "0.01");
		sParaTemp.put("show_url", "");
		sParaTemp.put("app_pay","y");//启用此参数可唤起钱包APP支付。
		sParaTemp.put("body", "");
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认支付");
		System.out.println("================sHtmlText:===="+sHtmlText);

		mv.addObject("sHtmlText", sHtmlText);
		mv.setViewName("alipay/alipay");
		return mv;
	}
}
