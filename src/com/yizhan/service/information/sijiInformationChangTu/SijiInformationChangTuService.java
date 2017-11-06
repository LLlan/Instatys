package com.yizhan.service.information.sijiInformationChangTu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;


/**
 * 
 * @类名称： SijiInformationChangTuService
 * @作者：lj 
 * @时间： 2017-5-23 下午5:13:22
 *
 */
@Service("sijiInformationChangTuService")
public class SijiInformationChangTuService {

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 后台司机长途发布信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> InformationChangtulistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SijiInformationChangTuMapper.InformationChangtulistPage", page);
	}
	
	/**
	 * 查看司机发布的长途路线列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> querySijiInformationChangTuPage(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SijiInformationChangTuMapper.querySijiInformationChangTuPage", pd);
	}
	
	/**
	 * 根据条件查看司机发布的长途路线列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> querySijiInformationChangTuList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SijiInformationChangTuMapper.querySijiInformationChangTuList", pd);
	}
	
	/**
	 * 查看客户端发布的长途路线列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryKehuInformationChangTuPage(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SijiInformationChangTuMapper.queryKehuInformationChangTuPage", pd);
	}
	
	
	/**
	 * 司机发布长途路线信息
	 * @param pd
	 * @throws Exception
	 */
	public void insertSijiInformationChangTu(PageData pd)throws Exception{
		dao.save("SijiInformationChangTuMapper.insertSijiInformationChangTu", pd);
	}
	
	/**
	 * 根据ID查出该长途打车列表一条信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryInformationChangTuID(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiInformationChangTuMapper.queryInformationChangTuID", pd);
	}
	
	/**
	 * 根据ID查出该长途打车列表一条信息客户端
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryInformationChangTuIDkuhu(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiInformationChangTuMapper.queryInformationChangTuIDkuhu", pd);
	}
	
	/**
	 * 已被抢单
	 * @param pd
	 * @throws Exception
	 */
	public void updateStatus(PageData pd)throws Exception{
		dao.update("SijiInformationChangTuMapper.updateStatus", pd);
	}
	
}
