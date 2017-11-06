package com.yizhan.entity.information;
/**
 * 商家用户信息
 * @author zhangjh
 *
 */
public class ShangJia {

	private String user_shangjia_id;
	private String phone;
	private String shopName;
	private String logoImg;
	private String tel_phone;
	private String shopNotice;
	private String deliveryTime;
	private String deliveryAmount;
	private String deliveryCost;
	private String authenticationTime;
	private String authenticationState;
	private String submitTime;
	private String last_login_time;
	private String ip;
	private String status;
	private String bz;
	
	public ShangJia() {
		
	}
	public String getUser_shangjia_id() {
		return user_shangjia_id;
	}
	public void setUser_shangjia_id(String user_shangjia_id) {
		this.user_shangjia_id = user_shangjia_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	public String getTel_phone() {
		return tel_phone;
	}
	public void setTel_phone(String tel_phone) {
		this.tel_phone = tel_phone;
	}
	public String getShopNotice() {
		return shopNotice;
	}
	public void setShopNotice(String shopNotice) {
		this.shopNotice = shopNotice;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public String getDeliveryCost() {
		return deliveryCost;
	}
	public void setDeliveryCost(String deliveryCost) {
		this.deliveryCost = deliveryCost;
	}
	public String getAuthenticationTime() {
		return authenticationTime;
	}
	public void setAuthenticationTime(String authenticationTime) {
		this.authenticationTime = authenticationTime;
	}
	public String getAuthenticationState() {
		return authenticationState;
	}
	public void setAuthenticationState(String authenticationState) {
		this.authenticationState = authenticationState;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
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
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	@Override
	public String toString() {
		return "ShangJia [user_shangjia_id=" + user_shangjia_id + ", phone="
				+ phone + ", shopName=" + shopName + ", logoImg=" + logoImg
				+ ", tel_phone=" + tel_phone + ", shopNotice=" + shopNotice
				+ ", deliveryTime=" + deliveryTime + ", deliveryAmount="
				+ deliveryAmount + ", deliveryCost=" + deliveryCost
				+ ", authenticationTime=" + authenticationTime
				+ ", authenticationState=" + authenticationState
				+ ", submitTime=" + submitTime + ", last_login_time="
				+ last_login_time + ", ip=" + ip + ", status=" + status
				+ ", bz=" + bz + "]";
	}
}
