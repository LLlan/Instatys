package com.yizhan.service.information.qishou;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.entity.system.AppUser;
import com.yizhan.entity.system.CacheProprietor;
import com.yizhan.util.PageData;

/**
 * 骑手端Service层
 * @类名称： QiShouService
 * @作者：lj 
 * @时间： 2017-7-5 上午9:44:52
 *
 */
@Service("qishouService")
public class QiShouService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 骑手申请管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getQishoulistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.getQishoulistPage", page);
	}
	
	/**
	 * 后台骑手审核操作
	 * @param map
	 * @throws Exception
	 */
	public void syschecked(Map<String, Object> map) throws Exception{
		dao.update("qiShouMapper.syschecked", map);
	}
	
	/**
	 * 外卖订单管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderTakeoulistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.getOrderTakeoulistPage", page);
	}
	
	/**
	 * 系统消息管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getSysMessagelistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.getSysMessagelistPage", page);
	}
	
	/**
	 * 执行系统消息添加
	 * @param pd
	 * @throws Exception
	 */
	public void saveinsert(PageData pd)throws Exception{
		dao.save("qiShouMapper.saveinsert", pd);
	}
	
	/**
	 * 根据ID获取一条对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDateBysysMessageId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("qiShouMapper.getDateBysysMessageId", pd);
	}
	
	/**
	 * 执行系统消息编辑
	 * @param pd
	 * @throws Exception 
	 */
	public void SysMessageupdate(PageData pd) throws Exception{
		dao.update("qiShouMapper.SysMessageupdate", pd);
	}
	
	/**
	 * 执行系统消息删除
	 * @param pd
	 * @throws Exception
	 */
	public void sysMessageDelete(PageData pd) throws Exception{
		dao.delete("qiShouMapper.sysMessageDeleteAll", pd);
	}
	
	/**
	 * 提现明细管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> BankCardTixianlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.BankCardTixianlistPage", page);
	}
	
	/**
	 * 后台骑手端银行转账审核受理操作
	 * @param map
	 * @throws Exception
	 */
	public void tixianshuoli(Map<String, Object> map) throws Exception{
		dao.update("qiShouMapper.tixianshuoli", map);
	}
	
	/**
	 * 执行保存骑手用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("qiShouMapper.saveU", pd);
	}
	
	/**
	 * 判断用户是否存在
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.findByPhone", pd);
	}
	
	/**
	 * 每次登录时,更新登录时间和IP
	 * @param pd
	 * @throws Exception
	 */
	public void updateLoginTimeAndIp(PageData pd) throws Exception{
		dao.update("qiShouMapper.updateLoginTimeAndIp", pd);
	}
	
	/**
	 * 查该骑手用户是否完善资料
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCompleteData(PageData pd)throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.getCompleteData", pd);              
	}
	
	/**
	 * 查该骑手用户是否完善资料
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCompleteDataByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.getCompleteDataByPhone", pd);              
	}
	
	/**
	 * 骑手app用户完善资料
	 * @param pd
	 * @throws Exception
	 */
	public void updateCompleteQishou(PageData pd)throws Exception{
		dao.save("qiShouMapper.updateCompleteQishou", pd);
	}
	
	/**
	 * 修改用户头像图片
	 * @param pd
	 * @throws Exception
	 */
	public void updateAppHeadImage(PageData pd)throws Exception{
		dao.save("qiShouMapper.updateAppHeadImage", pd);
	}
	
	/**
	 * 查询判断用户名是否已被占用 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryByUserName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.queryByUserName", pd);
	}
	
	/**
	 * 修改用户昵称
	 * @param pd
	 * @throws Exception
	 */
	public void updateAppNickName(PageData pd)throws Exception{
		dao.save("qiShouMapper.updateAppNickName", pd);
	}
	
	
	/**
	 * 获取个人资料信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryGeRZL(PageData pd) throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.queryGeRZL", pd);
	}
	
	/**
	 * 获取新任务
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderqishouLists(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.queryOrderqishouLists", pd);
	}
	
	/**
	 * 获取待取餐
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderStateQishou(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.queryOrderStateQishou", pd);
	}
	
	/**
	 * 获取历史订单（已送达）
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryLiShiOrderStateQishou(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.queryLiShiOrderStateQishou", pd);
	}
	
	/**
	 * 获取历史订单（异常订单）
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryLiShiOrderStateQishouCancel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.queryLiShiOrderStateQishouCancel", pd);
	}
	
	/**
	 * 获取待送达
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderStateQishoutow(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.queryOrderStateQishoutow", pd);
	}
	
	/**
	 * 根据ID获取一条信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByOrderTakeouId(PageData pd) throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.findByOrderTakeouId", pd);
	}
	
	/**
	 * 判断骑手端你已有正在进行中的订单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryOrderTakeouHaveInHand(PageData pd) throws Exception{
		return (PageData) dao.findForObject("qiShouMapper.queryOrderTakeouHaveInHand", pd);
	}
	
	
	/**
	 * 待取餐状态
	 * @param pd
	 * @throws Exception
	 */
	public void updateOrderStateQishou(PageData pd)throws Exception{
		dao.update("qiShouMapper.updateOrderStateQishou", pd);
	}
	
	/**
	 * 确认取货
	 * @param pd
	 * @throws Exception
	 */
	public void updateOrderStateQishouConfirm(PageData pd)throws Exception{
		dao.update("qiShouMapper.updateOrderStateQishouConfirm", pd);
	}
	
	/**
	 * 确认已送达
	 * @param pd
	 * @throws Exception
	 */
	public void updateOrderStateQishouSendupto(PageData pd)throws Exception{
		dao.update("qiShouMapper.updateOrderStateQishouSendupto", pd);
	}
	/**
	 * 获取商家信息（总收益、余额）
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getShangJiaInforById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("qiShouMapper.getShangJiaInforById", pd);
	}
	/**
	 * 更新商户总收益和余额
	 * @param pd
	 * @throws Exception
	 */
	public void updateShangJiaInforById(PageData pd)throws Exception{
		dao.update("qiShouMapper.updateShangJiaInforById", pd);
	}
	
	/**
	 * 订单详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querytbOrderTakeou(PageData pd) throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.querytbOrderTakeou", pd);
	}
	
	/**
	 * 是否开工
	 * @param pd
	 * @throws Exception
	 */
	public void isupdateStart(PageData pd)throws Exception{
		dao.update("qiShouMapper.isupdateStart", pd);
	}
	
	/**
	 * 换绑新的手机号
	 * @param pd
	 * @throws Exception
	 */
	public void updateUserphone(PageData pd) throws Exception{
		dao.update("qiShouMapper.updateUserphone", pd);
	}
	
	/**
	 * 获取新任务，待取货，待送达，已送达，异常订单，系统消息未读的总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("qiShouMapper.queryCount", pd);
	}
	
	/**
	 * 我的银行卡管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryBankCardList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.queryBankCardList", pd);
	}
	
	/**
	 * 银行卡解绑
	 * @param pd
	 * @throws Exception
	 */
	public void unbundlingCardNumber(PageData pd) throws Exception{
		dao.delete("qiShouMapper.unbundlingCardNumber", pd);
	}
	
	/**
	 * 查询银行卡信息是否从复
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryBankCardCardNumber(PageData pd) throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.queryBankCardCardNumber", pd);
	}
	
	/**
	 * 添加银行卡信息确认
	 * @param pd
	 * @throws Exception
	 */
	public void insertBankCard(PageData pd)throws Exception{
		dao.save("qiShouMapper.insertBankCard", pd);
	}
	
	/**
	 * 今日总收入
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryQiShouIncomeToday(PageData pd) throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.queryQiShouIncomeToday", pd);
	}
	
	/**
	 * 我的账户余额 、今日收入、总资产
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryAccountbalance(PageData pd) throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.queryAccountbalance", pd);
	}
	
	/**
	 * 我的钱包
	 * @param pd
	 * @throws Exception
	 */
	public void setMywallet(PageData pd)throws Exception{
		dao.save("qiShouMapper.setMywallet", pd);
	}
	
	/**
	 * 设置支付密码
	 * @param pd
	 * @throws Exception
	 */
	public void updateZhiFuPwd(PageData pd) throws Exception{
		dao.update("qiShouMapper.updateZhiFuPwd", pd);
	}
	
	/**
	 * 提取现金
	 * @param pd
	 * @throws Exception
	 */
	public void insertWithdrawCash(PageData pd)throws Exception{
		dao.save("qiShouMapper.insertWithdrawCash", pd);
	}
	
	/**
	 * 存入计算已支出，提现后的剩下的余额
	 * @param pd
	 * @throws Exception
	 */
	public void setAccountAndAssets(PageData pd)throws Exception{
		dao.save("qiShouMapper.setAccountAndAssets", pd);
	}
	
	/**
	 * 我的账单明细列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryBillingDetailsList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.queryBillingDetailsList", pd);
	}
	
	/**
	 * 上报异常
	 * @param pd
	 * @throws Exception
	 */
	public void insertReportingAnomalies(PageData pd)throws Exception{
		dao.save("qiShouMapper.insertReportingAnomalies", pd);
	}
	
	/**
	 * 设置申报异常状态
	 * @param pd
	 * @throws Exception
	 */
	public void setorderStateQishou(PageData pd)throws Exception{
		dao.update("qiShouMapper.setorderStateQishou", pd);
	}
	
	/**
	 * 系统消息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> systemMessage(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("qiShouMapper.systemMessage", pd);
	}
	
	/**
	 * 判断系统消息的id是否存在 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querySysMessageQishou(PageData pd) throws Exception{
		return (PageData)dao.findForObject("qiShouMapper.querySysMessageQishou", pd);
	}
	
	/**
	 * 设置是否已读状态
	 * @param pd
	 * @throws Exception
	 */
	public void setStatus(PageData pd)throws Exception{
		dao.update("qiShouMapper.setStatus", pd);
	}
	/////////////////////////////////////////////////////////////////缓存部分
	/**
	 * 保存缓存信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveCacheData(PageData pd) throws Exception {
		dao.save("qiShouMapper.saveCacheData", pd);
	}

	/**
	 * 根据登录成功后的返回码 BackCode去查询缓存信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByBackCode(PageData pd) throws Exception {
		return (PageData) dao.findForObject("qiShouMapper.getDataByBackCode",pd);
	}

	/**
	 * 更新缓存信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateCacheData(PageData pd) throws Exception {
		dao.update("qiShouMapper.updateCacheData", pd);
	}

	/**
	 * 清除缓存信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void deleteCacheData(PageData pd) throws Exception {
		dao.delete("qiShouMapper.deleteCacheData", pd);
	}
	
	/***
	 * 
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-10
	 */
	public void updateQishouRegistrationID(PageData pd) throws Exception{
		dao.delete("qiShouMapper.updateQishouRegistrationID", pd);
	}
	
}

