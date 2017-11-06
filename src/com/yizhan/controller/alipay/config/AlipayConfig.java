package com.yizhan.controller.alipay.config;

import com.yizhan.controller.base.BaseController;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088022714785785";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	//=======物业的=======//
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANk1teNudWMJqokPnbbfdT5HBTAM4+2M3AcEweN9j2srkIboRQi+e85S0EK2usxTikTzmeV3nPCVJyjJmF0P4yrA3Mkaz5MtCJRB9kRVt3YizTToKF6TMglkDJgcO26Wvb7EMAgEi4JP0SG458KmxC416lGh6zS+uyl5b8pTefL9AgMBAAECgYBN0Q87zpvhgloFRoHzPTUsvZbfMw8JUNiW7x5jWN1jwLTDkD0DN/L05PhVnk9n2loD/93XtrWT/2uLEkYyuPlh88TTW8I5SSY/5GTeGzH/rOvcgh/YWVzBXDEoavfm0dSOv8PXgGH+fO3LHzewosRLwBbOE4RG9r1uhUXGP3ifYQJBAO2e7W6CidNYWEXBUgXJL5qxeQjWZnGLXYX4zIc1/SRRiYUz+0M7+r/QRNM2wZcgQU3KEE8AZW2GQN+oqHH17JUCQQDqAqExJCBq9BisT0MEqemwwA3BuYCeEXN0PmCK3eNYrP1MIKkQMOoQdrSySLyl/5hhM6aDtLaIn8fOfg1m1+rJAkAPM+ja9i0dQXy8UccOqbWK/ynQP+9j+lesd6OWTwT4KuNSi2XbAl5joU6V+N3f7mdfM7thYpQassk5xg+o4uQVAkEAx3/2bC6gdI4lesEYIaG52TXihIXhYOD/zNpg9AK3D6JrT7uIZoVNBzwsUNIyAlMlVbjy/NXTyCZv8VsFkegCAQJAA6VcK8qj6y+cKHuwJXs36vJrwgTJ2KhgzTBsHZfjcxAICB232ssUs7c0JoDhIFk19u/xFFOyaiIBveqUozawvQ==";
										
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	//=======物业的=======//
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
				
	//外卖
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://47.93.199.239/Instaty/api/alipay/notify_url";
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
	public static String return_url = "http://47.93.199.239/Instaty/api/alipay/return_url";

	//同城打车
	public static String notify_url_tcdc = "http://47.93.199.239/Instaty/api/alipay_tcdc/notify_url";
	public static String return_url_tcdc = "http://47.93.199.239/Instaty/api/alipay_tcdc/return_url";
	
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "alipay.wap.create.direct.pay.by.user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}

