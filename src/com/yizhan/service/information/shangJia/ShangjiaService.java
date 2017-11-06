package com.yizhan.service.information.shangJia;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;

/**
 * 商家用户Service层
 * @author zhangjh
 * 2017年5月18日
 */
@Service("shangjiaService")
public class ShangjiaService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 保存用户信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveShangJiaUser(PageData pd) throws Exception{
		dao.save("shangjiaMapper.saveShangJiaUser", pd);
	}
	/**
	 * 根据手机号获取用户信息 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByPhone(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("shangjiaMapper.getDataByPhone", pd);
	}
	/**
	 * 判断商家店铺名称是否存在
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByShopName(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("shangjiaMapper.getDataByShopName", pd);
	}
	/**
	 * 判断商家真实姓名是否重复
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByRealName(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("shangjiaMapper.getDataByRealName", pd);
	}
	/**
	 * 判断商家身份号码是否重复
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByIdentityCard(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("shangjiaMapper.getDataByIdentityCard", pd);
	}
	/**
	 * 每次登录时,更新登录时间和IP
	 * @param pd
	 * @throws Exception
	 */
	public void updateLoginTimeAndIp(PageData pd) throws Exception{
		dao.update("shangjiaMapper.updateLoginTimeAndIp", pd);
	}
	/**
	 * 更新用户信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateUserData(PageData pd) throws Exception{
		dao.update("shangjiaMapper.updateUserData", pd);
	}
	/**
	 * 添加认证时上传的店内环境图或者商品图
	 * @param pd
	 * @throws Exception
	 */
	public void saveRenZhengImg(PageData pd) throws Exception{
		dao.save("shangjiaMapper.saveRenZhengImg", pd);
	}
	/**
	 * 获取认证时上传的店内环境图或者商品图
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getRenZhengImgList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("shangjiaMapper.getRenZhengImgList", pd);
	}
	/**
	 * 删除认证时上传的店内环境图或者商品图
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void deleteRenZhengImg(PageData pd) throws Exception{
		dao.delete("shangjiaMapper.deleteRenZhengImg", pd);
	}
	/////////////////////////////////////////////////////////////////缓存部分
	/**
	 * 保存缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveCacheData(PageData pd) throws Exception{
		dao.save("shangjiaMapper.saveCacheData", pd);
	}
	/**
	 *根据登录成功后的返回码 BackCode去查询缓存信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByBackCode(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangjiaMapper.getDataByBackCode", pd);
	}
	/**
	 * 更新缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateCacheData(PageData pd) throws Exception{
		dao.update("shangjiaMapper.updateCacheData", pd);
	}
	/**
	 * 清除缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteCacheData(PageData pd) throws Exception{
		dao.delete("shangjiaMapper.deleteCacheData", pd);
	}
	
	/**
	 * 查询所有的信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getlistPage(Page page) throws Exception{
		
		return (List<PageData>)dao.findForList("shangjiaMapper.getlistPage", page);
	}
	/**
	 * 获取指定商家的认证信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getRenZhengInforById(PageData pd) throws Exception{
		
		return (PageData)dao.findForObject("shangjiaMapper.getRenZhengInforById", pd);
	}
	/**
	 *后台商家审核
	 */
	public void syschecked(Map<String, Object> map) throws Exception{
		dao.update("shangjiaMapper.syschecked", map);
	}
	
	/**
	 * 商家系统消息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> systemMessage(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("shangjiaMapper.systemMessage", pd);
	}
	
	/**
	 * 判断系统消息的id是否存在 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querySysMessage(PageData pd) throws Exception{
		return (PageData)dao.findForObject("shangjiaMapper.querySysMessage", pd);
	}
	
	/**
	 * 设置是否已读状态
	 * @param pd
	 * @throws Exception
	 */
	public void setStatus(PageData pd)throws Exception{
		dao.update("shangjiaMapper.setStatus", pd);
	}
	
	/**
	 * 今日总收入
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryIncomeToday(PageData pd) throws Exception{
		return (PageData)dao.findForObject("shangjiaMapper.queryIncomeToday", pd);
	}
	
	/**
	 * 我的账户余额 、今日收入、总资产
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryAccountbalance(PageData pd) throws Exception{
		return (PageData)dao.findForObject("shangjiaMapper.queryAccountbalance", pd);
	}
	
	/**
	 * 我的钱包
	 * @param pd
	 * @throws Exception
	 */
	public void setMywallet(PageData pd)throws Exception{
		dao.save("shangjiaMapper.setMywallet", pd);
	}
	
	/**
	 * 我的银行卡管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryBankCardList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("shangjiaMapper.queryBankCardList", pd);
	}
	
	/**
	 * 银行卡解绑
	 * @param pd
	 * @throws Exception
	 */
	public void unbundlingCardNumber(PageData pd) throws Exception{
		dao.delete("shangjiaMapper.unbundlingCardNumber", pd);
	}
	
	/**
	 * 查询银行卡信息是否从复
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryBankCardCardNumber(PageData pd) throws Exception{
		return (PageData)dao.findForObject("shangjiaMapper.queryBankCardCardNumber", pd);
	}
	
	/**
	 * 添加银行卡信息确认
	 * @param pd
	 * @throws Exception
	 */
	public void insertBankCard(PageData pd)throws Exception{
		dao.save("shangjiaMapper.insertBankCard", pd);
	}
	
	/**
	 * 设置支付密码
	 * @param pd
	 * @throws Exception
	 */
	public void updateZhiFuPwd(PageData pd) throws Exception{
		dao.update("shangjiaMapper.updateZhiFuPwd", pd);
	}
	
	/**
	 * 提取现金
	 * @param pd
	 * @throws Exception
	 */
	public void insertWithdrawCash(PageData pd)throws Exception{
		dao.save("shangjiaMapper.insertWithdrawCash", pd);
	}
	
	/**
	 * 存入计算已支出，提现后的剩下的余额
	 * @param pd
	 * @throws Exception
	 */
	public void setAccountAndAssets(PageData pd)throws Exception{
		dao.save("shangjiaMapper.setAccountAndAssets", pd);
	}
	
	/**
	 * 我的账单明细列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryBillingDetailsList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("shangjiaMapper.queryBillingDetailsList", pd);
	}
	
	/**
	 * 系统消息管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getSysMessagelistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("shangjiaMapper.getSysMessagelistPage", page);
	}
	
	/**
	 * 执行系统消息添加
	 * @param pd
	 * @throws Exception
	 */
	public void saveinsert(PageData pd)throws Exception{
		dao.save("shangjiaMapper.saveinsert", pd);
	}
	
	/**
	 * 根据ID获取一条对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDateBysysMessageId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangjiaMapper.getDateBysysMessageId", pd);
	}
	
	/**
	 * 执行系统消息编辑
	 * @param pd
	 * @throws Exception 
	 */
	public void SysMessageupdate(PageData pd) throws Exception{
		dao.update("shangjiaMapper.SysMessageupdate", pd);
	}
	
	/**
	 * 执行系统消息删除
	 * @param pd
	 * @throws Exception
	 */
	public void sysMessageDelete(PageData pd) throws Exception{
		dao.delete("shangjiaMapper.sysMessageDeleteAll", pd);
	}
	
	/**
	 * 提现明细管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> BankCardTixianlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("shangjiaMapper.BankCardTixianlistPage", page);
	}
	
	/**
	 * 后台骑手端银行转账审核受理操作
	 * @param map
	 * @throws Exception
	 */
	public void tixianshuoli(Map<String, Object> map) throws Exception{
		dao.update("shangjiaMapper.tixianshuoli", map);
	}
	
	/**
	 * 是否营业
	 * @param pd
	 * @throws Exception
	 */
	public void isupdateStart(PageData pd)throws Exception{
		dao.update("shangjiaMapper.isupdateStart", pd);
	}
	
	/**
	 * 今日数据接单与合计总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangjiaMapper.queryCount", pd);
	}
	
	/**
	 * 查询进行中与未完成的订单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querytbOrderTakeou(PageData pd) throws Exception{
		return (PageData)dao.findForObject("shangJiaOrderMapper.querytbOrderTakeou", pd);
	}
	
	/*========================================商品管理===========================================================*/
	/**
	 * 查询所有的信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> goodsgetlistPage(Page page) throws Exception{
		
		return (List<PageData>)dao.findForList("goodsMapper.getlistPage", page);
	}
	/**
	 * 获取指定商家指定分类的所有商品
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> goodsgetlistAll(PageData pd) throws Exception{
		
		return (List<PageData>)dao.findForList("goodsMapper.getlistAll", pd);
	}
	/**
	 * 根据ID或者name获取对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData goodsgetDateByIdorName(PageData pd) throws Exception{
		return (PageData) dao.findForObject("goodsMapper.getDateByIdorName", pd);
	}
	/**
	 * 添加信息
	 * @throws Exception 
	 */
	public void goodsinsert(PageData pd) throws Exception{
		dao.save("goodsMapper.insert", pd);
	}
	/**
	 * 更新
	 * @param pd
	 * @throws Exception
	 */
	public void goodsupdate(PageData pd) throws Exception{
		dao.update("goodsMapper.update", pd);
	}
	/**
	 * 修改商品状态
	 * @param pd
	 * @throws Exception
	 */
	public void goodsChangeState(PageData pd) throws Exception{
		dao.update("goodsMapper.updatestateOne", pd);
	}
	/**
	 * 修改商品状态
	 * @param pd
	 * @throws Exception
	 */
	public void goodsstate(PageData pd) throws Exception{
		dao.update("goodsMapper.updatestate", pd);
	}
	/**
	 * 获取图片地址路径(删除商品的时候使用)
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData getImgPaths(PageData pd) throws Exception{
		return (PageData) dao.findForObject("goodsMapper.getImgPaths", pd);
	}
	/**
	 * 删除指定信息
	 * @param pd
	 * @throws Exception 
	 */
	public void goodsdelete(PageData pd) throws Exception{
		dao.delete("goodsMapper.goodsdelete", pd);
	}
	
	/**
	 * 删除商品
	 * @param pd
	 * @throws Exception 
	 */
	public void deleteGoods(PageData pd) throws Exception{
		dao.delete("goodsMapper.deleteGoods", pd);
	}
	/*========================================分类管理=======================================================*/
	/**
	 * 添加信息
	 * @param pd
	 * @throws Exception
	 */
	public void spflinsert(PageData pd) throws Exception{
		dao.save("goodsCategoryMapper.insert", pd);
	}
	
	/**
	 * 查询所有的信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getlistPagespfl(Page page) throws Exception{
		
		return (List<PageData>)dao.findForList("goodsCategoryMapper.getlistPage", page);
	}
	/**
	 * 查询所有的信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getlistspfl(PageData pd) throws Exception{
		
		return (List<PageData>)dao.findForList("goodsCategoryMapper.getlist", pd);
	}
	/**
	 * 根据ID或者名称获取对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData spflgetDateByIdorName(PageData pd) throws Exception{
		return (PageData) dao.findForObject("goodsCategoryMapper.spflgetDateByIdorName", pd);
	}
	/**
	 * 修改商品分类名称
	 * @param pd
	 * @throws Exception 
	 */
	public void spflupdate(PageData pd) throws Exception{
		dao.update("goodsCategoryMapper.update", pd);
	}
	/**
	 * 删除指定信息
	 * @param pd
	 * @throws Exception
	 */
	public void spfldeleteOne(PageData pd) throws Exception{
		dao.delete("goodsCategoryMapper.deleteOne", pd);
	}
	/**
	 * 删除指定信息
	 * @param pd
	 * @throws Exception
	 */
	public void spfldelete(PageData pd) throws Exception{
		dao.delete("goodsCategoryMapper.delete", pd);
	}
	
	/**
	 * 更新设备标识码ID
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-10
	 */
	public void updateShangjiaRegistrationID(PageData pd) throws Exception{
		dao.delete("shangjiaMapper.updateShangjiaRegistrationID", pd);
	}
	
}
