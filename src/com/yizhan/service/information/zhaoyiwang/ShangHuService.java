package com.yizhan.service.information.zhaoyiwang;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.util.PageData;

@Service("shangHuService")
public class ShangHuService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 保存商户的信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveShangHu(PageData pd) throws Exception{
		dao.save("shangHuMapper.saveShangHu", pd);
	}
	/**
	 * 每次登录成功后更新最新登录时间以及登录的IP地址
	 * @param pd
	 * @throws Exception
	 */
	public void updateIpAndTime(PageData pd) throws Exception{
		dao.update("shangHuMapper.updateIpAndTime", pd);
	}
	/**
	 * 更新（保存）用户个人信息
	 * @param pd
	 * @throws Exception
	 */
	public void updatePersonInformation(PageData pd) throws Exception{
		dao.update("shangHuMapper.updatePersonInformation", pd);
	}
	/**
	 * 更新（保存）用户个人简介或者擅长领域
	 * @param pd
	 * @throws Exception
	 */
	public void updatePersonIntroductOrGoodsField(PageData pd) throws Exception{
		dao.update("shangHuMapper.updatePersonIntroductOrGoodsField", pd);
	}
	/**
	 * 根据手机号修改登录密码
	 * @param pd
	 * @throws Exception
	 */
	public void updatePasswordByPhone(PageData pd) throws Exception{
		dao.update("shangHuMapper.updatePasswordByPhone", pd);
	}
	/**
	 * 根据手机号或者用户名去查询用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByNameOrPhone(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangHuMapper.getDataByNameOrPhone", pd);
	}
	/**
	 * 根据ID查询用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangHuMapper.getDataById", pd);
	}
	/**
	 * 根据登录名和密码获取对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByNameAndPaw(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangHuMapper.getDataByNameAndPaw", pd);
	}
	/*/////////////////////////服务////////////////////////////////////*/
	/**
	 * 保存服务的信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveService(PageData pd) throws Exception{
		dao.save("shangHuMapper.saveService", pd);
	}
	/**
	 * 获取服务列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getServiceList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("shangHuMapper.getServiceList", pd);
	}
	/**
	 * 根据ID获取对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByIdOfService(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangHuMapper.getDataByIdOfService", pd);
	}
	/**
	 * 更新服务
	 * @param pd
	 * @throws Exception
	 */
	public void updateService(PageData pd) throws Exception{
		dao.update("shangHuMapper.updateService", pd);
	}
	/**
	 * 删除服务
	 * @param pd
	 * @throws Exception
	 */
	public void deleteService(PageData pd) throws Exception{
		dao.delete("shangHuMapper.deleteService", pd);
	}
	/********************上传申请图片************************/
	/**
	 * 保存申请图片
	 * @param pd
	 * @throws Exception
	 */
	public void saveApplyImgPath(PageData pd) throws Exception{
		dao.save("shangHuMapper.saveApplyImgPath", pd);
	}
	/**
	 * 改变开店申请状态和申请时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateStateAndTime(PageData pd) throws Exception{
		dao.update("shangHuMapper.updateStateAndTime", pd);
	}
}
