package com.yizhan.service.information.bankCardTixian;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;

/**
 * 
 * @类名称： BankCardTixianService
 * @作者：lj 
 * @时间： 2017-6-24 下午4:45:50
 *
 */
@Service("bankCardTixianService")
public class BankCardTixianService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询列表分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> BankCardTixianlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BankCardTixianMapper.BankCardTixianlistPage", page);
	}
	
	/**
	 * 执行删除
	 * @param pd
	 * @throws Exception
	 */
	public void del(PageData pd)throws Exception{
		dao.delete("BankCardTixianMapper.del", pd);
	}
	
	/**
	 * 后台司机端银行转账审核受理操作
	 * @param map
	 * @throws Exception
	 */
	public void syschecked(Map<String, Object> map) throws Exception{
		dao.update("BankCardTixianMapper.syschecked", map);
	}
	
}
