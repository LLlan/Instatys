  package com.yizhan.util;
  
  import org.springframework.context.ApplicationContext;
  
  public class Const
  {
	public static final String SESSION_APPUSER = "sessionAppUser";
    public static final String SESSION_USERBEAN = "UserEntity";
    public static final String SESSION_LOCATION = "Location";
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";
    public static final String SESSION_USER_SECURITY_CODE = "sessionUserSecurityCode";
    public static final String SESSION_USER = "sessionUser";
    public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String SESSION_menuList = "menuList";
    public static final String SESSION_allmenuList = "allmenuList";
    public static final String SESSION_QX = "QX";
    public static final String SESSION_userpds = "userpds";
    public static final String SESSION_USERROL = "USERROL";
    public static final String SESSION_USERNAME = "USERNAME";
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String LOGIN = "/login_toLogin.do";
    public static final String SYSNAME = "admin/config/SYSNAME.txt";
    public static final String PAGE = "admin/config/PAGE.txt";
    public static final String EMAIL = "admin/config/EMAIL.txt";
    public static final String SMS1 = "admin/config/SMS1.txt";
    public static final String SMS2 = "admin/config/SMS2.txt";
    public static final String FWATERM = "admin/config/FWATERM.txt";
    public static final String IWATERM = "admin/config/IWATERM.txt";
    public static final String WEIXIN = "admin/config/WEIXIN.txt";
    public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";
    public static final String FILEPATHFILE = "uploadFiles/file/";
    public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/";
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(main)|(client)|(servlet)|(uploadFiles)|(resource)|(weixin)|(group)|(api)|(static)|(websocket)|(XiaoQufw)|(familyInfo)).*";
    public static ApplicationContext WEB_APP_CONTEXT = null;
  
    public static final String[] APP_REGISTERED_PARAM_ARRAY = { "countries", "uname", "passwd", "title", "full_name", "company_name", "countries_code", "area_code", "telephone", "mobile" };
    public static final String[] APP_REGISTERED_VALUE_ARRAY = { "国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称", "国家编号", "区号", "电话", "手机号" };
  
    public static final String[] APP_GETAPPUSER_PARAM_ARRAY = { "USERNAME"};
    public static final String[] APP_GETAPPUSER_VALUE_ARRAY = { "用户名" };
    //原始
    public static  String jp_appKey = "3bdf9aa82700b35c32188d4c";
    public static  String jp_masterSecret = "f4300614be65533b5f802c38";
    //===============================================================//
    //安卓商家端
    public static  String jp_sj_appKey = "81ebccf4c6a577ec48619313";
    public static  String jp_sj_masterSecret = "578615d6ad3b9a4d8809580c";
    
    //安卓骑手端
    public static  String jp_qs_appKey = "f1a0ef89af46cc6f57a00c26";
    public static  String jp_qs_masterSecret = "0d766930427247c7d32fc13d";
    
    //安卓司机端
    public static  String jp_siji_appKey = "fe4e4145c58cf8125dbf7510";
    public static  String jp_siji_masterSecret = "806598ab1f359d0b4655b3e2";
    
    
    //ios商家端
    public static  String jp_ios_sj_appKey = "049cbe889d3172a13e2a4e84";
    public static  String jp_ios_sj_masterSecret = "7773db1db1e6b5b07cd50990";
    
    
    //ios司机端
    public static  String jp_ios_siji_appKey = "9c97dc64b0de81b570376213";
    public static  String jp_ios_siji_masterSecret = "b7c11f11ab18d86ac9c6f291";
    
    //ios骑手端
    public static  String jp_ios_qs_appKey = "f3cde92b2ff639544c65f68b";
    public static  String jp_ios_qs_masterSecret = "e482a8e0bfabb1014475fa1d";
    
    
    
  }

