package com.yizhan.controller.app.jpushModel;

import java.util.HashMap;
import java.util.Map;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

import com.google.gson.Gson;
import com.yizhan.controller.app.jpushModel.audience.Audience;
import com.yizhan.controller.app.jpushModel.notification.AndroidNotification;
import com.yizhan.controller.app.jpushModel.notification.IosNotification;
import com.yizhan.controller.app.jpushModel.notification.Notification;
import com.yizhan.util.Const;


/**
 * 推送综合类
 * 功能：实现多类型多方式推送
 * 作者： lj
 * date：2017-4-25
 *
 */
public class JpushClientUtil {
		//原始 公共配置
		private final static String appKey = Const.jp_appKey;
		private final static String masterSecret = Const.jp_masterSecret;
		private static JPushClient jPushClient = new JPushClient(masterSecret,appKey);
		
		//安卓商家端
		private final static String sj_appKey = Const.jp_sj_appKey;
	    private final static String sj_masterSecret = Const.jp_sj_masterSecret;
	    private static JPushClient jPushClient_sj = new JPushClient(sj_masterSecret,sj_appKey);
	    
	    
		//自定义安卓司机
		private final static String siji_appKey = Const.jp_siji_appKey;
	    private final static String siji_masterSecret = Const.jp_siji_masterSecret;
	    private static JPushClient jPushClient_siji = new JPushClient(siji_masterSecret,siji_appKey);
	    
	    
		//自定义安卓骑手
		private final static String qs_appKey = Const.jp_qs_appKey;
	    private final static String qs_masterSecret = Const.jp_qs_masterSecret;
	    private static JPushClient jPushClient_qs = new JPushClient(qs_masterSecret,qs_appKey);
	    
	    
	    //ios商家端
	    private final static String ios_sj_appKey = Const.jp_ios_sj_appKey;
	    private final static String ios_sj_masterSecret = Const.jp_ios_sj_masterSecret;
	    private static JPushClient jPushClient_ios_sj = new JPushClient(ios_sj_masterSecret,ios_sj_appKey);
	    
		//自定义ios司机
		private final static String ios_siji_appKey = Const.jp_ios_siji_appKey;
	    private final static String ios_siji_masterSecret = Const.jp_ios_siji_masterSecret;
	    private static JPushClient jPushClient_ios_siji = new JPushClient(ios_siji_masterSecret,ios_siji_appKey);
	    
		//自定义ios骑手
		private final static String ios_qs_appKey = Const.jp_ios_qs_appKey;
	    private final static String ios_qs_masterSecret = Const.jp_ios_qs_masterSecret;
	    private static JPushClient jPushClient_ios_qs = new JPushClient(ios_qs_masterSecret,ios_qs_appKey);
	    
	    
	    /**
	     * 安卓端
	     * 客户下单
	     * 自定义推送给指定的商家
	     * @param registrationId 设备标识
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     * 作者：lj
	     * 日期：2017-8-10
	     */
	    public static int sj_sendToRegistrationId(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
	            PushResult pushResult=jPushClient_sj.sendPush(pushPayload);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (APIConnectionException e){
	            e.printStackTrace();
	 
	        } catch (APIRequestException e){
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	    
	    
	    
	    /**
	     * ios端
	     * 客户下单
	     * 自定义推送给指定的商家
	     * @param registrationId 设备标识
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     * 作者：lj
	     * 日期：2017-8-10
	     */
	    public static int ios_sj_sendToRegistrationId(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
	            PushResult pushResult=jPushClient_ios_sj.sendPush(pushPayload);
	            System.out.println("---------"+pushResult.getResponseCode());
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (APIConnectionException e){
	            e.printStackTrace();
	 
	        } catch (APIRequestException e){
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	    
	    
	    
	    /**
	     * 安卓端
	     * 客户取消订单 推送通知给指定司机
	     * 自定义推送给指定的司机
	     * @param registrationId 设备标识
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     * 作者：lj
	     * 日期：2017-8-10
	     */
	    public static int siji_sendToRegistrationId(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
	            PushResult pushResult=jPushClient_siji.sendPush(pushPayload);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (APIConnectionException e){
	            e.printStackTrace();
	 
	        } catch (APIRequestException e){
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	    
	    
	    
	    
	    /**
	     * ios端
	     * 客户取消订单 推送通知给指定司机
	     * 自定义推送给指定的司机
	     * @param registrationId 设备标识
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     * 作者：lj
	     * 日期：2017-8-10
	     */
	    public static int ios_siji_sendToRegistrationId(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
	            PushResult pushResult=jPushClient_ios_siji.sendPush(pushPayload);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (APIConnectionException e){
	            e.printStackTrace();
	 
	        } catch (APIRequestException e){
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	    
	    
	    
	    /**
	     * 安卓端
	     * 自定义推送给指定的骑手
	     * @param registrationId 设备标识
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     * 作者：lj
	     * 日期：2017-8-10
	     */
	    public static int qs_sendToRegistrationId( String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
	            PushResult pushResult=jPushClient_qs.sendPush(pushPayload);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (APIConnectionException e){
	            e.printStackTrace();
	 
	        } catch (APIRequestException e){
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	    
	    
	    
	    
	    /**
	     * ios端
	     * 自定义推送给指定的骑手
	     * @param registrationId 设备标识
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     * 作者：lj
	     * 日期：2017-8-10
	     */
	    public static int ios_qs_sendToRegistrationId( String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
	            PushResult pushResult=jPushClient_ios_qs.sendPush(pushPayload);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (APIConnectionException e){
	            e.printStackTrace();
	 
	        } catch (APIRequestException e){
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	    
	    
	    /**
	     * 安卓端
	     * 商家受理后，推送给所有的骑手端
	     * 发送给所有骑手设备（安卓和IOS的手机）
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段 可为空
	     * @return 0推送失败，1推送成功
	     */
	    public static int sendToAllQishou( String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try{
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
	            System.out.println(pushPayload);
	            PushResult pushResult=jPushClient_qs.sendPush(pushPayload);
	            System.out.println(pushResult);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	                System.out.println("=========给全部用户（安卓）发送成功！！========");
	            }
	        } catch (Exception e){
	 
	            e.printStackTrace();
	        }
	 
	        return result;
	    }
	    
	    /**、ios端
	     * 商家受理后，推送给所有的骑手端
	     * 发送给所有骑手设备（安卓和IOS的手机）
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段 可为空
	     * @return 0推送失败，1推送成功
	     */
	    public static int ios_sendToAllQishou(String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try{
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
	            System.out.println(pushPayload);
	            PushResult pushResult=jPushClient_ios_qs.sendPush(pushPayload);
	            System.out.println(pushResult);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	                System.out.println("=========给全部用户（ios）发送成功！！========");
	            }
	        } catch (Exception e){
	 
	            e.printStackTrace();
	        }
	 
	        return result;
	    }
	    
	    
	    /**
	     * 安卓端
	     * 客户下单后，推送给所有的司机端
	     * 发送给所有司机设备（安卓和IOS的手机）
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段 可为空
	     * @return 0推送失败，1推送成功
	     */
	    public static int sendToAllSiji( String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
	            System.out.println(pushPayload);
	            PushResult pushResult=jPushClient_siji.sendPush(pushPayload);
	            System.out.println(pushResult);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	                System.out.println("=========给全部用户（安卓）发送成功！！========");
	            }
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	        }
	 
	        return result;
	    }
	    
	    
	    /**
	     * ios端
	     * 客户下单后，推送给所有的司机端
	     * 发送给所有司机设备（安卓和IOS的手机）
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段 可为空
	     * @return 0推送失败，1推送成功
	     */
	    public static int ios_sendToAllSiji( String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
	            System.out.println(pushPayload);
	            PushResult pushResult=jPushClient_ios_siji.sendPush(pushPayload);
	            System.out.println(pushResult);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	                System.out.println("=========给全部用户（ios）发送成功！！========");
	            }
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	        }
	 
	        return result;
	    }
	    
	    
	    
	    
	    //-----------------------------------------------------上面的是自定义的----------------------------------------------------------------//
	    //-----------------------------------------------------以下的是公共的----------------------------------------------------------------//
	    
	    /**
	     * 推送给设备标识参数的用户
	     * @param registrationId 设备标识
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     */
	    public static int sendToRegistrationId( String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
	            PushResult pushResult=jPushClient.sendPush(pushPayload);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (APIConnectionException e){
	            e.printStackTrace();
	 
	        } catch (APIRequestException e){
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	    
	 
	    /**
	     * 发送给所有安卓用户
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     */
	    public static int sendToAllAndroid( String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
	            System.out.println(pushPayload);
	            PushResult pushResult=jPushClient.sendPush(pushPayload);
	            System.out.println(pushResult);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	            }
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	 
	    /**
	     * 发送给所有IOS用户
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     */
	    public static int sendToAllIos(String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try{
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_ios_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
	            System.out.println("-------pushPayload：-------"+pushPayload);
	            PushResult pushResult=jPushClient.sendPush(pushPayload);
	            System.out.println(pushResult);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	                System.out.println("=========给全部ios用户发送成功！！========");
	            }
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	        }
	 
	         return result;
	    }
	 
	    /**
	     * 发送给所有用户
	     * @param notification_title 通知内容标题
	     * @param msg_title 消息内容标题
	     * @param msg_content 消息内容
	     * @param extrasparam 扩展字段
	     * @return 0推送失败，1推送成功
	     */
	    public static int sendToAll( String notification_title, String msg_title, String msg_content, String extrasparam) {
	        int result = 0;
	        try {
	            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
	            System.out.println(pushPayload);
	            PushResult pushResult=jPushClient.sendPush(pushPayload);
	            System.out.println(pushResult);
	            if(pushResult.getResponseCode()==200){
	                result=1;
	                System.out.println("=========给全部用户（安卓和ios）发送成功！！========");
	            }
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	        }
	 
	        return result;
	    }
	 
	 
	 /**
	  * 发送给所有用户 （包括安卓和ios）
	  * 功能：
	  * 作者：lj
	  * 日期：2017-4-25
	  */
	    public static PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.all())//接收者 all表示所有人
	                .setNotification(Notification.newBuilder()
	                        .setAlert(notification_title)
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                                .setAlert("")
	                                .setTitle(notification_title)
	                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                                .addExtra("allKey",extrasparam)
	                                .build()
	                        )
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                //传一个IosAlert对象，指定apns title、title、subtitle等
	                                .setAlert(notification_title)
	                                //直接传alert
	                                //此项是指定此推送的badge自动加1
	                                .incrBadge(1)
	                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                                .setSound("sound.caf")
	                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                                .addExtra("allKey",extrasparam)
	                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
	                                // .setContentAvailable(true)
	 
	                                .build()
	                        )
	                        .build()
	                )
	                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
	                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
	                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(msg_content)
	                        .setTitle(msg_title)
	                        .addExtra("allKey",extrasparam)
	                        .build())
	 
	                .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(true)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                        .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                        .setTimeToLive(86400)
	                        .build()
	                )
	                .build();
	    }
	 
	    
	    /**
	     * 根据设备标识推送给指定用户
	     * 功能：
	     * 作者：lj
	     * 日期：2017-8-10
	     */
	    private static PushPayload buildPushObject_all_registrationId_alertWithTitle(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
	 
	        System.out.println("---------根据设备标识推送给指定用户-----------");
	        //创建一个IosAlert对象，可指定APNs的alert、title等字段
	        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
	        return PushPayload.newBuilder()
	                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	                .setPlatform(Platform.all())
	                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registrationId
	                .setAudience(Audience.registrationId(registrationId))
	                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	                .setNotification(Notification.newBuilder()
	                        //指定当前推送的android通知
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                                .setAlert("")//显示消息标题（在通知标题下面）  msg_title
	                                .setTitle(notification_title)
	                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）androidNotification extras key
	                                .addExtra("allKey",extrasparam)
	                                .build())
			                        //指定当前推送的iOS通知
			                        .addPlatformNotification(IosNotification.newBuilder()
	                                //传一个IosAlert对象，指定apns title、title、subtitle等
	                                .setAlert(notification_title)
	                                //直接传alert
	                                //此项是指定此推送的badge自动加1
	                                .incrBadge(1)
	                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                                .setSound("sound.caf")
	                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）ios
	                                .addExtra("allKey",extrasparam)
	                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
	                                //取消此注释，消息推送时ios将无法在锁屏情况接收
	                                // .setContentAvailable(true)
	                                .build())
	                                .build())

					                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
					                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
					                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
					                .setMessage(Message.newBuilder()
					 
					                        .setMsgContent(msg_content)
					 
					                        .setTitle(msg_title)
					 
					                        .addExtra("allKey",extrasparam)
					 
					                        .build())
					 
					                .setOptions(Options.newBuilder()
					                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
					                        .setApnsProduction(false)
					                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
					                        .setSendno(1)
					                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
					                        .setTimeToLive(86400)
					 
					                        .build())
					 
					                .build();
	 
	    }
	    
	    
		 /**
		  * 给全部安卓用户推送
		  * 功能：
		  * 作者：lj
		  * 日期：2017-4-25
		  */
	    private static PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, String extrasparam) {
	        System.out.println("----------buildPushObject_android_registrationId_alertWithTitle");
	        return PushPayload.newBuilder()
	                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	                .setPlatform(Platform.android())
	                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
	                .setAudience(Audience.all())
	                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	                .setNotification(Notification.newBuilder()
	                        //指定当前推送的android通知
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                                .setAlert(notification_title)
	                                .setTitle(notification_title)
	                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                                .addExtra("androidNotification extras key",extrasparam)
	                                .build())
	                        .build()
	                )
	                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
	                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
	                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(msg_content)
	                        .setTitle(msg_title)
	                        .addExtra("message extras key",extrasparam)
	                        .build())
	 
	                .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(false)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                        .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                        .setTimeToLive(86400)
	                        .build())
	                .build();
	    }
	
	    /**
	     * 给全部ios用户推送
	     * 功能：
	     * 作者：lj
	     * 日期：2017-4-25
	     */
	    private static PushPayload buildPushObject_ios_all_alertWithTitle( String notification_title, String msg_title, String msg_content, String extrasparam) {
	        return PushPayload.newBuilder()
	                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	                .setPlatform(Platform.ios())
	                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
	                .setAudience(Audience.all())
	                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	                .setNotification(
	                		Notification.newBuilder()
	                        //指定当前推送的android通知
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                //传一个IosAlert对象，指定apns title、title、subtitle等
	                                .setAlert(notification_title)
	                                //直接传alert
	                                //此项是指定此推送的badge自动加1
	                                .incrBadge(1)
	                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                                .setSound("sound.caf")
	                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                                .addExtra("iosNotification extras key",extrasparam)
	                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
	                               // .setContentAvailable(true)
	 
	                                .build())
	                        .build()
	                )
	                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
	                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
	                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(msg_content)
	                        .setTitle(msg_title)
	                        .addExtra("message extras key",extrasparam)
	                        .build())
	 
	                .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(true)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                        .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                        .setTimeToLive(86400)
	                        .build())
	                .build();
	    }
	 
	    public static void main(String[] args){
	    	Map<String,Object> map = new HashMap<String,Object>();
			map.put("jump", "1");//1跳转到商家新订单
			map.put("mp3", "xxx.mp3");
			map.put("notification_title", "订单大厅有新订单！");
			map.put("msg_title", "客户已下单，请查收后并受理！");
			String extrasparam = new Gson().toJson(map);
	    	
	        if(JpushClientUtil.sj_sendToRegistrationId("140fe1da9e9eab75cc9", "2342143", "aaa", "bbb",extrasparam)==1){
	            System.out.println("=======推送成功success=========");
	        }
	    }
}
