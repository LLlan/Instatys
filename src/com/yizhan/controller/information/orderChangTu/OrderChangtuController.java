package com.yizhan.controller.information.orderChangTu;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.service.information.orderChangTu.OrderChangtuService;
import com.yizhan.util.PageData;

/**
 * 司机长途拼车订单后台控制器
 * @类名称： orderChangtu
 * @作者：lj 
 * @时间： 2017-5-19 上午9:24:03
 *
 */
@Controller
@RequestMapping(value="/orderChangtu")
public class OrderChangtuController extends BaseController {

	@Resource(name = "orderChangtuService")
	private OrderChangtuService orderChangtuService;
	
	/**
	 * 长途拼车订单分页列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/orderChangtuListPage")
	public ModelAndView orderChangtuList(Page page){
		logBefore(logger, "长途拼车订单分页列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> orderChangtuList = orderChangtuService.queryOrderChangtuListPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("orderChangtuList",orderChangtuList);
 			mv.setViewName("information/orderChangTu/orderChangTu_list");
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return mv;
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/del")
	public void delete(PrintWriter out){
		logBefore(logger, "删除");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			orderChangtuService.del(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
}
