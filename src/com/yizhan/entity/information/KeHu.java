package com.yizhan.entity.information;

public class KeHu { 
    private String user_kehu_id;//ID 
    private String phone;//手机号
    private String loginPassword;//登录密码
    private String headImg;//头像
    private String userName;//用户名
    private String registerTime;//注册时间
	public String getUser_kehu_id() {
		return user_kehu_id;
	}
	public void setUser_kehu_id(String user_kehu_id) {
		this.user_kehu_id = user_kehu_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "KeHu [user_kehu_id=" + user_kehu_id + ", phone=" + phone
				+ ", loginPassword=" + loginPassword + ", headImg=" + headImg
				+ ", userName=" + userName + ", registerTime=" + registerTime
				+ "]";
	}
    
    
    
}