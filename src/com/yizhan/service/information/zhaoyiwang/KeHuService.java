package com.yizhan.service.information.zhaoyiwang;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.util.PageData;

@Service("kehuService")
public class KeHuService {
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
	 * 根据手机号或者用户名去查询用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByNameOrPhone(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangHuMapper.getDataByNameOrPhone", pd);
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
}
