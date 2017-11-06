package com.yizhan.service.information.shangjiaOrder;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;

/**
 * 商家订单Service层
 * @author zhangjh
 * 2017年5月18日
 */
@Service("shangjiaOrderService")
public class ShangjiaOrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取订单列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("shangJiaOrderMapper.getOrderList", pd);
	}
	/**
	 * 获取指定条件下的订单数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getOrderNumberByTiaoJian(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shangJiaOrderMapper.getOrderNumberByTiaoJian", pd);
	}
	/**
	 * 更新订单状态
	 * @param pd
	 * @throws Exception
	 */
	public void update_order_state(PageData pd) throws Exception{
		dao.update("shangJiaOrderMapper.update_order_state", pd);
	}
	
	/**
	 * 商家订单管理列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderTakeoulistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("shangJiaOrderMapper.getOrderTakeoulistPage", page);
	}
}
