package com.yizhan.controller.app.jpushUtil;

import cn.jpush.api.JPushClient;

public class BaseURL {
	//添加代理时需修改 HOST_NAME 和 ALL_PATH 路径根据自己的需要配置
	public static String HOST_NAME_SSL = "https://api.jpush.cn:443";
	public static String HOST_NAME = "http://api.jpush.cn:8800";
	protected static final String ALL_PATH = "/sendmsg/v2/sendmsg";       //全功能，发送地址
	protected static final String SIMPLE_CUSTOM_PATH = "/sendmsg/v2/custom_message"; //简易接口，自定义消息
	protected static final String SIMPLE_NOTIFICATION_PATH = "/sendmsg/v2/notification"; //简易接口，自定义通知                          
	
	 //在极光注册上传应用的 appKey 和 masterSecret
	public static final String appKey ="fbb5174b19083d071ac96ecb";////必填
	public static final String masterSecret = "d6dfc7daca23140cc535e1ec";//必填，每个应用都对应一个masterSecret
	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
	 */
	public static long timeToLive =  60 * 60 * 24;  
	
	
	
	public static final int MAX = Integer.MAX_VALUE;
		public static final int MIN = (int) MAX/2;
		
	/**
	 * 保持 sendNo 的唯一性是有必要的
	 * It is very important to keep sendNo unique.
	 * @return sendNo
	 */
	public static int getRandomSendNo() {
	    return (int) (MIN + Math.random() * (MAX - MIN));
	}
	
	
	private static String getHostname(boolean enableSSL) {
		return enableSSL? HOST_NAME_SSL :HOST_NAME;
	}

	public  static String getUrlForPath(final String path,boolean enableSSL) {
		return getHostname(enableSSL) + path;
	}
	
}
