package com.yizhan.controller.information.orderShouyi;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.service.information.orderShouyi.OrderShouyiService;
import com.yizhan.util.PageData;

/**
 * 后台平台收益
 * @类名称： OrderShouyiController
 * @作者：lj 
 * @时间： 2017-8-17 下午2:45:41
 *
 */
@Controller
@RequestMapping(value="/api/ordershouyi")
public class OrderShouyiController extends BaseController{

	@Resource(name="orderShouyiService")
	private OrderShouyiService orderShouyiService;
	
	/**
	 * 后台获取外卖收益列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getOrderShouyilistPage")
	public ModelAndView getOrderShouyilistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取外卖收益列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderShouyiService.getOrderShouyilistPage(page);
		PageData sum=orderShouyiService.getOrderShouyiSum(pd);
		if(sum!=null){
			mv.addObject("Total", sum.get("Total"));
		}
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/orderShouyi/waimaishouyi_list");
		return mv;
	}
	
	/**
	 * 后台获取同城打车收益列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getOrderTongchengShouyilistPage")
	public ModelAndView getOrderTongchengShouyilistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取同城打车收益列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderShouyiService.getOrderTongchengShouyilistPage(page);
		PageData sum=orderShouyiService.getOrderTongchengShouyiSum(pd);
		if(sum!=null){
			mv.addObject("Total", sum.get("Total"));
		}
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/orderShouyi/tongchengshouyi_list");
		return mv;
	}
	
}
