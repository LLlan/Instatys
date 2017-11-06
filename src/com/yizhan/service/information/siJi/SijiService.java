package com.yizhan.service.information.siJi;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.entity.system.CacheUserSiji;
import com.yizhan.util.PageData;

/**
 * 
 * @类名称： SijiUserService
 * @作者：lj 
 * @时间： 2017-5-17 下午2:54:00
 *
 */
@Service("sijiUserService")
public class SijiService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 司机用户管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> SijiUserlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SijiMapper.SijiUserlistPage", page);
	}
	
	/**
	 * 审核不通过
	 */
	public void syscheckedNo(PageData pd) throws Exception{
		dao.update("SijiMapper.syscheckedNo", pd);
	}
	
	/**
	 * 审核通过
	 */
	public void syscheckedYes(PageData pd) throws Exception{
		dao.update("SijiMapper.syscheckedYes", pd);
	}
	/**
	 * 批量审核不通过
	 */
	public void syscheckedNoAll(String[] ids) throws Exception{
		dao.update("SijiMapper.syscheckedNoAll", ids);
	}
	/**
	 * 审核批量通过
	 */
	public void syscheckedYesAll(String[] ids) throws Exception{
		dao.update("SijiMapper.syscheckedYesAll", ids);
	}

	
//===========================（开始接口）==========================================
	/**
	 * 根据backCode获取缓存信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public CacheUserSiji getCacheInfo(PageData pd) throws Exception{
		return (CacheUserSiji)dao.findForObject("SijiMapper.getCacheInfo", pd);
	}
	
	/**
	 *根据登录成功后的返回码 BackCode去查询缓存信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByBackCode(PageData pd) throws Exception{
		return (PageData) dao.findForObject("SijiMapper.getDataByBackCode", pd);
	}
	
	
	/**
	 * 查询电话号码是否重复，即该用户是否注册过
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SijiMapper.findByPhone", pd);
	}
	
	/**
	 * 查询判断用户名是否已被占用 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryByUserName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SijiMapper.queryByUserName", pd);
	}
	
	
	/**
	 * 执行司机用户注册信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("SijiMapper.SijiUserInsert", pd);
	}
	
	
	/**
	 * 根据手机号码和密码判断登录，查到有该用户则登录成功
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SijiMapper.getUserByNameAndPwd", pd);              
	}
	
	/**
	 * 每一次登录，都要修改这次登录时间为最后一次登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("SijiMapper.updateLastLogin", pd);
	}
	
	/**
	 * 查该司机用户是否完善资料2
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCompleteDataByPhones(PageData pd)throws Exception{	
		return (List<PageData>)dao.findForList("SijiMapper.getCompleteDataByPhones", pd);              
	}
	
	/**
	 * 查该司机用户是否完善资料
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCompleteDataByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SijiMapper.getCompleteDataByPhone", pd);              
	}
	
	/**
	 * 根据电话号码查出该用户的所有信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserSijiByPhone(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.getUserSijiByPhone", pd);
	}

	/**
	 * 根据用户名和密码获取用户缓存信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getCacheInfoByPhoneAndPwd(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.getCacheInfoByPhoneAndPwd", pd);
	}
	
	/**
	 * 保存用户缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void putCacheInfo(PageData pd)throws Exception{
		dao.save("SijiMapper.putCacheInfo", pd);
	}
	
	/**
	 * 删除缓存信息  司机端app注销的用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteCacheInfo(PageData pd)throws Exception{
		dao.delete("SijiMapper.deleteCacheInfo", pd);
	}
	
	/**
	 * 修改密码
	 * @param pd
	 * @throws Exception
	 */
	public void updatePwd(PageData pd) throws Exception{
		dao.update("SijiMapper.updatePwd", pd);
	}
	
	/**
	 * 司机app用户完善资料
	 * @param pd
	 * @throws Exception
	 */
	public void updateCompleteSiji(PageData pd)throws Exception{
		dao.save("SijiMapper.updateCompleteSiji", pd);
	}
	
	/**
	 * 修改车辆信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateVehicleInfo(PageData pd)throws Exception{
		dao.save("SijiMapper.updateVehicleInfo", pd);
	}
	
	
	/**
	 * 修改用户头像图片
	 * @param pd
	 * @throws Exception
	 */
	public void updateAppHeadImage(PageData pd)throws Exception{
		dao.save("SijiMapper.updateAppHeadImage", pd);
	}
	
	
	/**
	 * 修改用户昵称
	 * @param pd
	 * @throws Exception
	 */
	public void updateAppNickName(PageData pd)throws Exception{
		dao.save("SijiMapper.updateAppNickName", pd);
	}
	
	
	/**
	 * 换绑新的手机号
	 * @param pd
	 * @throws Exception
	 */
	public void updateUserphone(PageData pd) throws Exception{
		dao.update("SijiMapper.updateUserphone", pd);
	}
	
	/**
	 * 换绑新的手机号时更新缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateCacheUserSijiData(PageData pd) throws Exception{
		dao.update("SijiMapper.updateCacheUserSijiData", pd);
	}
	
	
	/**
	 * 获取司机端所有的个人资料信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querySijidGeRZL(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.querySijidGeRZL", pd);
	}
	
	
	/**
	 * 查询银行卡信息是否从复
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryBankCardCardNumber(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.queryBankCardCardNumber", pd);
	}
	
	/**
	 * 查询银行卡id是否存在
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querybankCardId(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.querybankCardId", pd);
	}
	
	/**
	 * 添加银行卡信息确认
	 * @param pd
	 * @throws Exception
	 */
	public void insertBankCard(PageData pd)throws Exception{
		dao.save("SijiMapper.insertBankCard", pd);
	}
	
	
	/**
	 * 我的银行卡管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryBankCardList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SijiMapper.queryBankCardList", pd);
	}
	
	
	/**
	 * 我的银行卡管理详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryBankCard(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.queryBankCard", pd);
	}
	
	/**
	 * 银行卡解绑
	 * @param pd
	 * @throws Exception
	 */
	public void unbundlingCardNumber(PageData pd) throws Exception{
		dao.delete("SijiMapper.unbundlingCardNumber", pd);
	}
	
	/**
	 * 设置提现密码
	 * @param pd
	 * @throws Exception
	 */
	public void updateTXPwd(PageData pd) throws Exception{
		dao.update("SijiMapper.updateTXPwd", pd);
	}
	
	/**
	 * 设置成默认银行卡 
	 * @param pd
	 * @throws Exception
	 */
	public void setdefault(PageData pd) throws Exception{
		dao.update("SijiMapper.setdefault", pd);
	}
	
	
	/**
	 * 正常的使用的 
	 */
	public void setdefaultON(String[] ids) throws Exception{
		dao.update("SijiMapper.setdefaultON", ids);
	}
	
	/**
	 * 获取默认银行卡
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querydefaultBankCard(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.querydefaultBankCard", pd);
	}
	
	
	/**
	 * 提取现金
	 * @param pd
	 * @throws Exception
	 */
	public void insertWithdrawCash(PageData pd)throws Exception{
		dao.save("SijiMapper.insertWithdrawCash", pd);
	}
	
	/**
	 * 存入计算出，提现后的剩下的余额
	 * @param pd
	 * @throws Exception
	 */
	public void setAccountAndAssets(PageData pd)throws Exception{
		dao.save("SijiMapper.setAccountAndAssets", pd);
	}
	
	/**
	 * 我的账单明细列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryBillingDetailsList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SijiMapper.queryBillingDetailsList", pd);
	}
	
	
	/**
	 * 我的明细账单详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryBillDetails(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.queryBillDetails", pd);
	}
	
	/**
	 * 提现说明
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryCashDeclaration(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.queryCashDeclaration", pd);
	}
	
	
	/**
	 * 同城订单的今日总收入
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querytongchengIncomeToday(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.querytongchengIncomeToday", pd);
	}
	
	/**
	 * 长途订单的今日总收入
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querychangtuIncomeToday(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.querychangtuIncomeToday", pd);
	}
	
	/**
	 * 我的账户余额
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryAccountbalance(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.queryAccountbalance", pd);
	}
	
	/**
	 * 我的钱包
	 * @param pd
	 * @throws Exception
	 */
	public void setMywallet(PageData pd)throws Exception{
		dao.save("SijiMapper.setMywallet", pd);
	}
	
	
	/***
	 * 司机端更新设备标识ID
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-10
	 */
	public void updateSijiRegistrationID(PageData pd) throws Exception{
		dao.delete("SijiMapper.updateSijiRegistrationID", pd);
	}
	
	/**
	 * 获取司机端设备标识ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querySijiRegistrationID(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiMapper.querySijiRegistrationID", pd);
	}
	
}
