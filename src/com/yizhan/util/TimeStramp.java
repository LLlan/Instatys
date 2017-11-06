package com.yizhan.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳工具类
 * 功能：
 * 作者： lj
 * date：2017-8-3
 *
 */
public class TimeStramp {
	
	
	   /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	    public static final String dtLong                  = "yyyyMMddHHmmss";
	    
	    /** 完整时间 yyyy-MM-dd HH:mm:ss */
	    public static final String simple                  = "yyyy-MM-dd HH:mm:ss";
	    
	    /** 年月日(无下划线) yyyyMMdd */
	    public static final String dtShort                 = "yyyyMMdd";
	
	 /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号,以yyyyMMddHHmmss为格式的当前系统时间
     * @return
     *      
     */
	public  static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtLong);
		return df.format(date);
	}
}
