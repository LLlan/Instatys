package com.yizhan.service.information.keHu;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;

@Service("keHuService")
public class KeHuService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 查询用户管理列表分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> UserKehulistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("keHuMapper.UserKehulistPage", page);
	}
	
	/**
	 * 客户端用户打车信息管理分页列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> InformationKehulistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("keHuMapper.InformationKehulistPage", page);
	}
	
	/**
	 * 执行删除
	 * @param pd
	 * @throws Exception
	 */
	public void del(PageData pd)throws Exception{
		dao.delete("keHuMapper.del", pd);
	}
	
	
	
	/**
	 * 保存客户端用户信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveKeHuUser(PageData pd) throws Exception{
		dao.save("keHuMapper.saveKeHuUser", pd);
	}
	/**
	 * 根据手机号判断该用户是否已经存在 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByPhone(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataByPhone", pd);
	}
	/**
	 * 根据用户名判断该用户名是否已经存在 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByUserName(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataByUserName", pd);
	}
	/**
	 * 每次登录时,更新登录时间和IP
	 * @param pd
	 * @throws Exception
	 */
	public void updateLoginTimeAndIp(PageData pd) throws Exception{
		dao.update("keHuMapper.updateLoginTimeAndIp", pd);
	}
	/**
	 * 更新用户的手机号码或用户名
	 * @param pd
	 * @throws Exception
	 */
	public void updateUserData(PageData pd) throws Exception{
		dao.update("keHuMapper.updateUserData", pd);
	}
	/**
	 *根据登录成功后的返回码 BackCode去查询缓存信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByBackCode(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataByBackCode", pd);
	}
	/**
	 * 保存缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveCacheData(PageData pd) throws Exception{
		dao.save("keHuMapper.saveCacheData", pd);
	}
	/**
	 * 清除缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteCacheData(PageData pd) throws Exception{
		dao.delete("keHuMapper.deleteCacheData", pd);
	}
	
	/**
	 * 更新缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateCacheData(PageData pd) throws Exception{
		dao.update("keHuMapper.updateCacheData", pd);
	}
	
	/**
	 * 同城打车计费设置列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> tongchengjifeilistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("keHuMapper.tongchengjifeilistPage", page);
	}
	
	/**
	 * 计费金额
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData tongchengjifeijine(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.tongchengjifeijine", pd);
	}
	
	/**
	 * 执行保存同城计费设置信息
	 * @param pd
	 * @throws Exception
	 */
	public void Insertjifei(PageData pd) throws Exception{
		dao.save("keHuMapper.Insertjifei", pd);
	}
	
	/**
	 * 根据计费id查询一条信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataJifeiId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataJifeiId", pd);
	}
	
	/**
	 * 执行编辑同城计费设置信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateJifei(PageData pd) throws Exception{
		dao.update("keHuMapper.updateJifei", pd);
	}
	
	/**
	 * 执行同城计费设置删除 
	 * @param pd
	 * @throws Exception
	 */
	public void deljifei(PageData pd) throws Exception{
		dao.delete("keHuMapper.deljifei", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] DATA_IDS)throws Exception{
		dao.delete("keHuMapper.deleteAll", DATA_IDS);
	}
	
}

