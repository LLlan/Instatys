package com.yizhan.service.information.shouhuoAddress;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.util.PageData;

/**
 * 收货地址Service层
 * @author zhangjh
 * 2017年5月20日
 */
@Service("shouhuoAddressService")
public class ShouhuoAddressService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取收货地址列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getListShouHuoAddress(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("shouhuoAddressMapper.getlistAll", pd);
	}
	/**
	 * 新增收货地址
	 * @param pd
	 * @throws Exception
	 */
	public void saveShouHuoAddress(PageData pd) throws Exception{
		dao.save("shouhuoAddressMapper.insert", pd);
	}
	/**
	 * 根据ID获取指定收货地址对象
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData toEditShouHuoAddress(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shouhuoAddressMapper.getDateById", pd);
	}
	/**
	 * 更新收货地址
	 * @param pd
	 * @throws Exception
	 */
	public void updateShouHuoAddress(PageData pd) throws Exception{
		dao.update("shouhuoAddressMapper.update", pd);
	}
	/**
	 * 删除收货地址
	 * @param pd
	 * @throws Exception
	 */
	public void deleteShouHuoAddress(PageData pd) throws Exception{
		dao.delete("shouhuoAddressMapper.delete", pd);
	}
}
