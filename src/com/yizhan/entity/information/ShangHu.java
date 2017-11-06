package com.yizhan.entity.information;
/**
 * 商户用户信息
 * @author zhangjh
 *
 */
public class ShangHu {
	private String user_shanghu_id;//主键ID
	private String phone;//手机号
	private String userName;//用户名
	private String headImg;//头像
	private String youName;//姓名
	private String position;//职位
	private String mark;//标注(个人或者医院的名称)
	private String linkMethod;//联系方式(手机号或者座机)
	private String address;//就诊地址
	private double xianjinAmount;//现金金额
	private int integral;//积分
	
	
	public String getUser_shanghu_id() {
		return user_shanghu_id;
	}
	public void setUser_shanghu_id(String user_shanghu_id) {
		this.user_shanghu_id = user_shanghu_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getYouName() {
		return youName;
	}
	public void setYouName(String youName) {
		this.youName = youName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getLinkMethod() {
		return linkMethod;
	}
	public void setLinkMethod(String linkMethod) {
		this.linkMethod = linkMethod;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getXianjinAmount() {
		return xianjinAmount;
	}
	public void setXianjinAmount(double xianjinAmount) {
		this.xianjinAmount = xianjinAmount;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	
}
