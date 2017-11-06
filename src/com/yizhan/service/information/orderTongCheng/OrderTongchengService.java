package com.yizhan.service.information.orderTongCheng;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;

/**
 * 
 * @类名称： OrderTongchengService
 * @作者：lj 
 * @时间： 2017-5-18 下午4:10:23
 *
 */
@Service("orderTongchengService")
public class OrderTongchengService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询列表分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderTongchengListPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderTongchengMapper.queryOrderTongchenglistPage", page);
	}
	
	/**
	 * 执行新增
	 * @param pd
	 * @throws Exception
	 */
	public void InsertOrderTongcheng(PageData pd)throws Exception{
		dao.save("OrderTongchengMapper.InsertOrderTongcheng", pd);
	}
	
	/**
	 * 执行修改
	 * @param pd
	 * @throws Exception
	 */
	public void UpdateOrderTongcheng(PageData pd)throws Exception{
		dao.update("OrderTongchengMapper.UpdateOrderTongcheng", pd);
	}
	
	/**
	 * 根据ID读取一条数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrderTongchengMapper.findById", pd);
	}
	
	/**
	 * 执行删除
	 * @param pd
	 * @throws Exception
	 */
	public void del(PageData pd)throws Exception{
		dao.delete("OrderTongchengMapper.del", pd);
	}

	/**
	 * 执行批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OrderTongchengMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 根据条件查询一条记录，判断是否存在
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getBySuigongName(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("OrderTongchengMapper.getBySuigongName", pd);
	}
	
}
