package com.yizhan.service.information.sijiInformationTongCheng;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.entity.system.CacheUserSiji;
import com.yizhan.util.PageData;

/**
 * 
 * @类名称： SijiInformationChangtuService
 * @作者：lj 
 * @时间： 2017-5-23 上午11:29:20
 *
 */
@Service("sijiInformationTongChengService")
public class SijiInformationTongChengService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 获取司机端同城打车列表信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryInformationTongChengPage(PageData pd)throws Exception{	
		return (List<PageData>)dao.findForList("SijiInformationTongChengMapper.queryInformationTongChengPage", pd);              
	}
	
	/**
	 * 查询列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryInformationTongCheng(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiInformationTongChengMapper.queryInformationTongCheng", pd);
	}
	
	/**
	 * 根据ID查出该同城打车列表一条信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryInformationTongChengByPhone(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiInformationTongChengMapper.queryInformationTongChengByPhone", pd);
	}

	
	/**
	 * 已被抢单
	 * @param pd
	 * @throws Exception
	 */
	public void updateHujiaoStatus(PageData pd)throws Exception{
		dao.update("SijiInformationTongChengMapper.updateHujiaoStatus", pd);
	}
}
