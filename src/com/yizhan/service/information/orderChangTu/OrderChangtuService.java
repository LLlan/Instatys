package com.yizhan.service.information.orderChangTu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;
/**
 * 
 * @类名称： OrderChangtuService
 * @作者：lj 
 * @时间： 2017-5-19 上午9:28:00
 *
 */
@Service("orderChangtuService")
public class OrderChangtuService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询列表分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderChangtuListPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderChangtuMapper.queryOrderChangtulistPage", page);
	}
	
	/**
	 * 执行删除
	 * @param pd
	 * @throws Exception
	 */
	public void del(PageData pd)throws Exception{
		dao.delete("OrderChangtuMapper.del", pd);
	}
	
	/**
	 * 获取司机端长途订单列表信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderChangtuList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrderChangtuMapper.queryOrderChangtuList", pd);
	}
	
	/**
	 * 获取司机端长途订单列表信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderKeHuChangtuList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrderChangtuMapper.queryOrderKeHuChangtuList", pd);
	}
	
	/**
	 * 司机端长途打车我要下单
	 * @param pd
	 * @throws Exception
	 */
	public void insertOrderChangTu(PageData pd)throws Exception{
		dao.save("OrderChangtuMapper.insertOrderChangTu", pd);
	}
	
	/**
	 * 获取司机端长途订单详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryOrderChangTu(PageData pd) throws Exception{
		return (PageData)dao.findForObject("OrderChangtuMapper.queryOrderChangTu", pd);
	}
	
	/**
	 * 获取客户端长途订单详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryOrderChangTuKeHu(PageData pd) throws Exception{
		return (PageData)dao.findForObject("OrderChangtuMapper.queryOrderChangTuKeHu", pd);
	}
	
	/**
	 * 司机端长途订单确认已送达
	 * @param pd
	 * @throws Exception
	 */
	public void updateOrderChangTuStatus(PageData pd) throws Exception{
		dao.update("OrderChangtuMapper.updateOrderChangTuStatus", pd);
	}
}
