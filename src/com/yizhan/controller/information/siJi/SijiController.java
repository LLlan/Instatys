package com.yizhan.controller.information.siJi;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.service.information.siJi.SijiService;
import com.yizhan.service.information.sijiInformationChangTu.SijiInformationChangTuService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.PageData;
/**
 * 
 * 司机端后台管理控制器
 * @类名称： SijiUserController
 * @作者：lj 
 * @时间： 2017-5-17 下午3:00:35
 *
 */
@Controller
@RequestMapping(value="/sijiUser")
public class SijiController extends BaseController{

	@Resource(name="sijiUserService")
	private SijiService sijiUserService;
	
	@Resource(name="sijiInformationChangTuService")
	private SijiInformationChangTuService sijiInformationChangTuService;
	/**
	 * 司机用户管理列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/sijiUserlistPage")
	public ModelAndView list(Page page){
		logBefore(logger, "司机用户管理列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> sijiUserList = sijiUserService.SijiUserlistPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("sijiUserList",sijiUserList);
 			mv.setViewName("information/siJi/siJi_list");
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return mv;
	}
	
	/**
	 * 司机身份认证审核处理
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/sijiAuthenticationState")
	@ResponseBody
	public Object sijiAuthenticationState() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd=new PageData();
		pd=this.getPageData();
		int key=Integer.parseInt(pd.getString("num"));
		switch (key) {
		case 1:
			//单个审核不通过
			pd.put("authenticationState", "0");
			sijiUserService.syscheckedNo(pd);
			break;
		case 2:
			//单个审核通过
			pd.put("authenticationState", "1");
			sijiUserService.syscheckedYes(pd);
			break;
		case 3:
			//批量审核不通过
			String ids=pd.getString("ids");
			String Arrayids[]=ids.split(",");//分割成数组
			sijiUserService.syscheckedNoAll(Arrayids);
			break;
		case 4:
			//批量审核通过
			String ids1=pd.getString("ids");
			String Arrayids1[]=ids1.split(",");//分割成数组
			sijiUserService.syscheckedYesAll(Arrayids1);
			break;
		default:
			break;
		}
		map.put("msg", "success");
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 后台司机长途发布信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/informationChangtulistPage")
	public ModelAndView informationChangtuList(Page page){
		logBefore(logger, "------司机长途发布信息列表--------");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> sijiInformationChangTuList = sijiInformationChangTuService.InformationChangtulistPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("sijiInformationChangTuList",sijiInformationChangTuList);
 			mv.setViewName("information/siJi/sijiInformationChangTu_list");
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return mv;
	}
}
