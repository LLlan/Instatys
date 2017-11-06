package com.yizhan.entity.information;

import java.io.Serializable;

public class QiShou implements Serializable { 
    private String user_qishou_id;//ID
    private String phone;
    private String loginPassword;
    private String payPassword;
    private String headImg;
    private String userName;
    private String myState;//0休息 1接单
    private String last_login_time;
    private String ip;
    private String status;//1使用中  默认   0禁用
	public String getUser_qishou_id() {
		return user_qishou_id;
	}
	public void setUser_qishou_id(String user_qishou_id) {
		this.user_qishou_id = user_qishou_id;
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
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
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
	public String getMyState() {
		return myState;
	}
	public void setMyState(String myState) {
		this.myState = myState;
	}
	
	
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "QiShou [user_qishou_id=" + user_qishou_id + ", phone=" + phone
				+ ", loginPassword=" + loginPassword + ", payPassword="
				+ payPassword + ", headImg=" + headImg + ", userName="
				+ userName + ", myState=" + myState + "]";
	}
    
    
    
}