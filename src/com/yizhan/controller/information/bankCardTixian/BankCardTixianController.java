package com.yizhan.controller.information.bankCardTixian;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.service.information.bankCardTixian.BankCardTixianService;
import com.yizhan.util.DateUtil;
import com.yizhan.util.PageData;

/**
 * 司机端提现列表后台功能控制器
 * @类名称： BankCardTixianController
 * @作者：lj 
 * @时间： 2017-6-24 下午4:42:37
 *
 */
@Controller
@RequestMapping(value="/bankCardTixian")
public class BankCardTixianController extends BaseController {

	@Resource(name = "bankCardTixianService")
	private BankCardTixianService bankCardTixianService;
	
	/**
	 * 分页列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/bankCardTixianListPage")
	public ModelAndView bankCardTixianListPage(Page page){
		logBefore(logger, "-------司机提现明细分页列表---------");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> bankCardTixianList = bankCardTixianService.BankCardTixianlistPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("bankCardTixianList",bankCardTixianList);
 			mv.setViewName("information/bankCardTixian/bankCardTixian_list");
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
			bankCardTixianService.del(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 后台司机端银行转账审核受理操作
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/syschecked")
	public void syschecked(PrintWriter writer) throws Exception{
		logBefore(logger, "---后台司机端银行转账审核受理操作----");
		Map<String, Object> map =new HashMap<String, Object>();
		PageData pd=new PageData();
		pd=this.getPageData();
		String num=pd.getString("num");
		String id[]=pd.getString("ids").split(",");
		List<String> ids=new ArrayList<String>();
		for (int i = 0; i < id.length; i++) {
			ids.add(id[i]);
		}
		map.put("ids", ids);
		map.put("update_time", DateUtil.getTime());
		if(num.equals("0")){
			map.put("bank_card_status","受理失败");
		}else{
			map.put("bank_card_status", "受理成功");
		}
		bankCardTixianService.syschecked(map);
		writer.close();
	}
	
}
