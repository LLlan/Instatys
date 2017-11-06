package com.yizhan.service.information.sijiOrderTongCheng;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;

/**
 * 
 * @类名称： SijiOrderTongChengService
 * @作者：lj 
 * @时间： 2017-6-1 上午9:45:01
 *
 */
@Service("sijiOrderTongChengService")
public class SijiOrderTongChengService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询列表分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> SijiInformationChangtuListPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SijiOrderTongChengMapper.SijiInformationChangtuListPage", page);
	}
	
	/**
	 * 同城打车抢单
	 * @param pd
	 * @throws Exception
	 */
	public void insertOrderTongCheng(PageData pd)throws Exception{
		dao.save("SijiOrderTongChengMapper.insertOrderTongCheng", pd);
	}
	
	/**
	 * 获取司机端同城订单列表信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderTongChengLists(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SijiOrderTongChengMapper.queryOrderTongChengList", pd);
	}
	
	/**
	 * 客户端同城订单列表信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryKeHuOrderTongChengList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SijiOrderTongChengMapper.queryKeHuOrderTongChengList", pd);
	}
	
	/**
	 * 获取司机端同城订单详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryOrderTongCheng(PageData pd) throws Exception{
		return (PageData)dao.findForObject("SijiOrderTongChengMapper.queryOrderTongCheng", pd);
	}

	/**
	 * 司机端已接到乘客
	 * @param pd
	 * @throws Exception
	 */
	public void SetOrderTongChengStatus(PageData pd) throws Exception{
		dao.update("SijiOrderTongChengMapper.SetOrderTongChengStatus", pd);
	}
	/**
	 * 更新订单编号
	 * @param pd
	 * @throws Exception
	 */
	public void updateOrderTongChengOrderNumberById(PageData pd) throws Exception{
		dao.update("SijiOrderTongChengMapper.updateOrderTongChengOrderNumberById", pd);
	}
	/**
	 * 司机端同城订单确认已送达
	 * @param pd
	 * @throws Exception
	 */
	public void updateOrderTongChengStatus(PageData pd) throws Exception{
		dao.update("SijiOrderTongChengMapper.updateOrderTongChengStatus", pd);
	}
	/**
	 * 乘客支付完成
	 * @param pd
	 * @throws Exception
	 */
	public void updateTongChengOrder(PageData pd) throws Exception{
		dao.update("SijiOrderTongChengMapper.updateTongChengOrder", pd);
	}
	/**
	 * 获取司机信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getSiJiInforById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("SijiOrderTongChengMapper.getSiJiInforById", pd);
	}
	/**
	 * 更新司机信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateSiJiInforById(PageData pd) throws Exception{
		dao.update("SijiOrderTongChengMapper.updateSiJiInforById", pd);
	}
	/**
	 * 根据订单号查询订单信息
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData getTongChengOrderInfoByOrderNumber(PageData pd) throws Exception{
		return (PageData) dao.findForObject("SijiOrderTongChengMapper.getTongChengOrderInfoByOrderNumber", pd);
	}
	
	/**
	 * 司机端同城订单和长途订单，接单总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryOrderTongChengAndChangTuOrderCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("SijiOrderTongChengMapper.queryOrderTongChengAndChangTuOrderCount", pd);
	}
	
	/**
	 * 判断司机端你已一条正在进行中的同城订单与长途订单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryOrderTongChengHaveInHand(PageData pd) throws Exception{
		return (PageData) dao.findForObject("SijiOrderTongChengMapper.queryOrderTongChengHaveInHand", pd);
	}
	
	
}
