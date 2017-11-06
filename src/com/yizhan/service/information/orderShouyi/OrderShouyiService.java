package com.yizhan.service.information.orderShouyi;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;

/**
 * 
 * @类名称： OrderShouyiService
 * @作者：lj 
 * @时间： 2017-8-17 下午2:40:50
 *
 */
@Service("orderShouyiService")
public class OrderShouyiService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 外卖收益列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderShouyilistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("orderShouyiMapper.getOrderShouyilistPage", page);
	}
	
	/**
	 * 外卖收益总合计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getOrderShouyiSum(PageData pd) throws Exception{
		return (PageData) dao.findForObject("orderShouyiMapper.getOrderShouyiSum", pd);
	}
	
	/**
	 * 后台获取同城打车收益列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderTongchengShouyilistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("orderShouyiMapper.getOrderTongchengShouyilistPage", page);
	}
	
	/**
	 * 同城打车收益总合计
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getOrderTongchengShouyiSum(PageData pd) throws Exception{
		return (PageData) dao.findForObject("orderShouyiMapper.getOrderTongchengShouyiSum", pd);
	}
	
}
