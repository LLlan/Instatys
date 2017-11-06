package com.yizhan.controller.app.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.net.httpserver.Authenticator.Success;
import com.weixin.model.Member;
import com.yizhan.controller.app.payUtil.AlipayConfig;
import com.yizhan.controller.app.payUtil.AlipayCore;
import com.yizhan.controller.app.payUtil.AlipayNotify;
import com.yizhan.controller.app.payUtil.UtilDate;
import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.entity.system.Proprietor;
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
@RequestMapping(value="/api/appPay")
public class appPayController extends BaseController{
	
	@ResponseBody
	@RequestMapping(value = "/alipay.do",  produces = "text/html;charset=UTF-8",method={RequestMethod.GET})
	public static Object alipay(HttpServletRequest request) throws Exception {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		Map<String,String> map = new HashMap<String,String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
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
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号	
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号	
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		
		//异步通知ID
		String notify_id=request.getParameter("notify_id");
		
		//sign
		String sign=request.getParameter("sign");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		if(notify_id!=""&&notify_id!=null){////判断接受的post通知中有无notify_id，如果有则是异步通知。
			if(AlipayNotify.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
			{
				if(AlipayNotify.getSignVeryfy(params, sign))//使用支付宝公钥验签
				{
					//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
					if(trade_status.equals("TRADE_FINISHED")){
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//注意：
						//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
						//请务必判断请求时的out_trade_no、total_fee、seller_id与通知时获取的out_trade_no、total_fee、seller_id为一致的
						
					} else if (trade_status.equals("TRADE_SUCCESS")){
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//注意：
						//付款完成后，支付宝系统发送该交易状态通知
						//请务必判断请求时的out_trade_no、total_fee、seller_id与通知时获取的out_trade_no、total_fee、seller_id为一致的
					}
					//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					map.put("respMsg", "成功");//请不要修改或删除
					
					//调试打印log
					AlipayCore.logResult("notify_url success!","notify_url");
				}
				else//验证签名失败
				{
					map.put("respMsg", "sign fail");
				}
			}
			else//验证是否来自支付宝的通知失败
			{
				map.put("respMsg", "response fail");
			}
		}
		else{
			map.put("respMsg", "no notify message");
		}
		
			return AppUtil.returnObject(new PageData(), map);     

	     }
	
	@ResponseBody
	@RequestMapping(value = "/alipay2.do",  produces = "text/html;charset=UTF-8",method={RequestMethod.GET})
	public static Object alipay(String body, String subject, String out_trade_no, String total_amount) throws Exception {
	         //公共参数
	         Map<String, String> map = new HashMap<String, String>();
	         map.put("app_id", AlipayConfig.app_id);
	         map.put("method", "alipay.trade.app.pay");
	         map.put("format", "json");
	         map.put("charset", "utf-8");
	         map.put("sign_type", "RSA");
	         map.put("timestamp", UtilDate.getDateFormatter());
	         map.put("version", "1.0");
	         map.put("notify_url", AlipayConfig.service);

	         Map<String, String> m = new HashMap<String, String>();

	         m.put("body", body);
	         m.put("subject", subject);
	         m.put("out_trade_no", out_trade_no);
	         m.put("timeout_express", "30m");
	         m.put("total_amount", total_amount);
	         m.put("seller_id", AlipayConfig.partner);
	         m.put("product_code", "QUICK_MSECURITY_PAY");

	         JSONObject bizcontentJson= JSONObject.fromObject(m);

	         map.put("biz_content", bizcontentJson.toString());
	         //对未签名原始字符串进行签名       
	         //String rsaSign = AlipaySignature.rsaSign(map, AlipayConfig.private_key, "utf-8");

	         Map<String, String> map4 = new HashMap<String, String>();

	         map4.put("app_id", AlipayConfig.app_id);
	         map4.put("method", "alipay.trade.app.pay");
	         map4.put("format", "json");
	         map4.put("charset", "utf-8");
	         map4.put("sign_type", "RSA");
	         map4.put("timestamp", URLEncoder.encode(UtilDate.getDateFormatter(),"UTF-8"));
	         map4.put("version", "1.0");
	         map4.put("notify_url",  URLEncoder.encode(AlipayConfig.service,"UTF-8"));
	         //最后对请求字符串的所有一级value（biz_content作为一个value）进行encode，编码格式按请求串中的charset为准，没传charset按UTF-8处理
	         map4.put("biz_content", URLEncoder.encode(bizcontentJson.toString(), "UTF-8"));

	         Map par = AlipayCore.paraFilter(map4); //除去数组中的空值和签名参数
	        //String json4 = AlipayCore.createLinkString(map4);   //拼接后的字符串
	        //json4=json4 + "&sign=" + URLEncoder.encode(rsaSign, "UTF-8");
	        //System.out.println(json4.toString());
	        //AliPayMsg apm = new AliPayMsg();
	        //apm.setCode("1");
	        //apm.setMsg("支付成功");
	        //apm.setData(json4.toString());  
	        //JSONObject json = JSONObject.fromObject(apm);
	        //System.out.println(json.toString());
	        return AppUtil.returnObject(new PageData(), map4);     

	     }
	
	
	@ResponseBody
	@RequestMapping(value = "/callback.do",  produces = "text/html;charset=UTF-8",method={RequestMethod.POST})
	public String callbacks( HttpServletRequest request ) throws Exception {
	        //接收支付宝返回的请求参数
	        Map requestParams = request.getParameterMap();
	        JSONObject json = JSONObject.fromObject(requestParams);
	        String trade_status = json.get("trade_status").toString().substring(2,json.get("trade_status").toString().length()-2);
	        String out_trade_no = json.get("out_trade_no").toString().substring(2,json.get("out_trade_no").toString().length()-2);
	        String notify_id = json.get("notify_id").toString().substring(2,json.get("notify_id").toString().length()-2);

	        System.out.println("====================================================");
	        System.out.println(json.toString());
	        System.out.println("支付宝回调地址！");
	        System.out.println("商户的订单编号：" + out_trade_no);
	        System.out.println("支付的状态：" + trade_status);    

	        if(trade_status.equals("TRADE_SUCCESS")) {

	                /**
	                 *支付成功之后的业务处理
	                 */
	                return "success";
	            
	        }else {

		            /**
		             *支付失败后的业务处理
		             */
		            return "fail";

	        }
	    }
	
}
