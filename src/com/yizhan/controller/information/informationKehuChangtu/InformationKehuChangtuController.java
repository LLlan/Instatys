package com.yizhan.controller.information.informationKehuChangtu;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.service.information.keHu.KeHuService;
import com.yizhan.util.PageData;

/**
 * 客户端打车信息列表后台功能控制器
 * @类名称： InformationKehuChangtuController
 * @作者：lj 
 * @时间： 2017-6-30 下午3:42:32
 *
 */
@Controller
@RequestMapping(value="/informationKehuChangtu")
public class InformationKehuChangtuController extends BaseController {

	@Resource(name = "keHuService")
	private KeHuService keHuService;
	
	/**
	 * 分页列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/informationKehulistPage")
	public ModelAndView informationKehulistPage(Page page){
		logBefore(logger, "-------客户端用户打车信息管理分页列表---------");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> informationKehulist = keHuService.InformationKehulistPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("informationKehulist",informationKehulist);
 			mv.setViewName("information/informationKehu/informationKehu_list");
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
			keHuService.del(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
}
