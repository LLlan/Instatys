package com.yizhan.service.information.h5sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;


@Service("h5sysService")
public class h5sysService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 配送规则分页列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> peisongfeilistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("H5sysMapper.peisongfeilistPage", page);
	}
	
	/**
	 * 获取配送规则信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData peiSongGuiZeInfo(PageData pd)throws Exception{
		return (PageData) dao.findForObject("H5sysMapper.peiSongGuiZeInfo", pd);
	}
	
	/**
	 * 执行配送费规则添加
	 * @param pd
	 * @throws Exception
	 */
	public void saveinsert(PageData pd)throws Exception{
		dao.save("H5sysMapper.saveinsert", pd);
	}
	
	/**
	 * 根据ID获取一条对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDateBypeisongfeiId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("H5sysMapper.getDateBypeisongfeiId", pd);
	}
	
	/**
	 * 执行配送费规则编辑
	 * @param pd
	 * @throws Exception 
	 */
	public void sysPeisongfeiupdate(PageData pd) throws Exception{
		dao.update("H5sysMapper.sysPeisongfeiupdate", pd);
	}
	
	/**
	 * 执行配送费规则删除与批量删除
	 * @param pd
	 * @throws Exception
	 */
	public void sysPeisongfeiDelete(PageData pd) throws Exception{
		dao.delete("H5sysMapper.sysPeisongfeiDeleteAll", pd);
	}
	
	/**
	 * 服务费规则分页列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> fuwufeiListPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("H5sysMapper.fuwufeilistPage", page);
	}
	
	/**
	 * 执行服务费规则添加
	 * @param pd
	 * @throws Exception
	 */
	public void saveFuwufei(PageData pd)throws Exception{
		dao.save("H5sysMapper.saveFuwufei", pd);
	}
	
	
	/**
	 * 根据ID获取一条对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDateByfuwufeiId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("H5sysMapper.getDateByfuwufeiId", pd);
	}
	
	/**
	 * 执行服务费规则规则编辑
	 * @param pd
	 * @throws Exception 
	 */
	public void sysFuwufeiUpdate(PageData pd) throws Exception{
		dao.update("H5sysMapper.sysFuwufeiUpdate", pd);
	}
	
	
}

