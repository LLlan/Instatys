package com.yizhan.controller.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.entity.information.ZywKeHu;
import com.yizhan.service.information.orderKehu.OrderService;
import com.yizhan.service.information.zhaoyiwang.KeHuService;
import com.yizhan.service.information.zhaoyiwang.ShangHuService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.Const;
import com.yizhan.util.PageData;

@Controller
@RequestMapping(value="/api/order")
public class OrderController extends BaseController{

	@Resource(name="orderService")
	private OrderService orderService;
	@Resource(name="shangHuService")
	private ShangHuService shangHuService;
	@Resource(name="kehuService")
	private KeHuService keHuService;
	/**
	 * 点击立即下单，进入确认订单页面，选择支付方式进行付款
	 * type:1-找医生，2-找机构，3-找药商
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/toPayPage")
	public ModelAndView toRegin_pay() throws Exception{
		logBefore(logger, "--点击立即下单，进入确认订单页面，选择支付方式进行付款--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		mv.addObject("pd", pd);	
		mv.setViewName("information/zhaoyiwang/zywkehuduan/user/region/regin_pay");
		return mv;
	}
	
	/**
	 * 后台获取医生订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getOrderOfYiShenglistPage")
	public ModelAndView getOrderOfYiShenglistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取医生订单列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderService.getOrderOfYiShenglistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shanghuorder/yishengorder_list");
		return mv;
	}
	/**
	 * 后台获取机构订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getOrderOfJiGoulistPage")
	public ModelAndView getOrderOfJiGoulistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取机构订单列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderService.getOrderOfJiGoulistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shanghuorder/jigouorder_list");
		return mv;
	}
	/**
	 * 后台获取药商订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getOrderOfYaoShanglistPage")
	public ModelAndView getOrderOfYaoShanglistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取药商订单列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderService.getOrderOfYaoShanglistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shanghuorder/yaoshangorder_list");
		return mv;
	}
	/**
	 * 后台获取客户订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getOrderOfKeHulistPage")
	public ModelAndView getOrderOfKeHulistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取客户订单列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderService.getOrderOfKeHulistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/kehuorder/kehuorder_list");
		return mv;
	}
	/**
	 * 后台获取医生类收益列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getShouYiOfYiShenglistPage")
	public ModelAndView getShouYiOfYiShenglistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取医生类收益列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderService.getShouYiOfYiShenglistPage(page);
		PageData sum=orderService.getShouYiOfYiShengSum(pd);
		mv.addObject("pd", pd);
		mv.addObject("sum", sum.get("sum"));
		mv.addObject("list", list);
		mv.setViewName("information/pingtaishouyi/yishengshouyi_list");
		return mv;
	}
	/**
	 * 后台获取机构类收益列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getShouYiOfJiGoulistPage")
	public ModelAndView getShouYiOfJiGoulistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取机构类收益列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderService.getShouYiOfJiGoulistPage(page);
		PageData sum=orderService.getShouYiOfJiGouSum(pd);
		mv.addObject("pd", pd);
		mv.addObject("sum", sum.get("sum"));
		mv.addObject("list", list);
		mv.setViewName("information/pingtaishouyi/jigoushouyi_list");
		return mv;
	}
	/**
	 * 后台获取药商类收益列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getShouYiOfYaoShanglistPage")
	public ModelAndView getShouYiOfYaoShanglistPage(Page page) throws Exception{
		logBefore(logger, "-- 后台获取药商类收益列表--");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=orderService.getShouYiOfYaoShanglistPage(page);
		PageData sum=orderService.getShouYiOfYaoShangSum(pd);
		mv.addObject("pd", pd);
		mv.addObject("sum", sum.get("sum"));
		mv.addObject("list", list);
		mv.setViewName("information/pingtaishouyi/yaoshangshouyi_list");
		return mv;
	}
	
	
	
	
}
