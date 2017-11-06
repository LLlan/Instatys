package com.yizhan.controller.information.qiShou;

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
import com.yizhan.service.information.qishou.QiShouService;
import com.yizhan.util.DateUtil;
import com.yizhan.util.PageData;
/**
 * 骑手端管理Controller层
 * @类名称： QishouController
 * @作者：lj 
 * @时间： 2017-7-13 上午9:16:25
 *
 */
@Controller
@RequestMapping(value="/qishou")
public class QishouController extends BaseController{
	
	@Resource(name="qishouService")
	private QiShouService qishouService;

	/**
	 * 骑手申请管理列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getlistPage")
	public ModelAndView getlistPage(Page page) throws Exception{
		logBefore(logger, "----骑手申请管理列表-----");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=qishouService.getQishoulistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/qiShou/qiShou_list");
		return mv;
	}
	
	/**
	 * 后台骑手审核操作
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/syschecked")
	public void syschecked(PrintWriter writer) throws Exception{
		logBefore(logger, "---后台骑手审核操作----");
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
		map.put("authenticationTime", DateUtil.getTime());
		if(num.equals("0")){
			map.put("authenticationState","0");
		}else{
			map.put("authenticationState","1");
		}
		qishouService.syschecked(map);
		writer.close();
	}
	
	/**
	 * 外卖订单管理列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getOrderTakeoulistPage")
	public ModelAndView getOrderTakeoulistPage(Page page) throws Exception{
		logBefore(logger, "----外卖订单管理列表---");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=qishouService.getOrderTakeoulistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/qiShou/orderTakeou_list");
		return mv;
	}
	
	//*****************(系统消息管理开始)*********************
	/**
	 * 系统消息管理列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getSysMessagelistPage")
	public ModelAndView getSysMessagelistPage(Page page) throws Exception{
		logBefore(logger, "----系统消息管理列表---");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=qishouService.getSysMessagelistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/qiShou/sysMessage_list");
		return mv;
	}
	
	/**
	 * 系统消息添加页面
	 * @return
	 */
	@RequestMapping(value="/goSysMessageAdd")
	public ModelAndView goSysMessageAdd(){
		logBefore(logger, "------系统消息添加页面-----");
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg", "saveinsert");
		mv.setViewName("information/qiShou/sysMessage_edit");
		return mv;
	}
	
	/**
	 * 执行系统消息添加
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveinsert")
	public ModelAndView saveinsert() throws Exception{
		logBefore(logger, "------执行系统消息添加-------");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("sys_message_id", this.get32UUID());
		pd.put("title", pd.getString("title"));
		pd.put("profiles", pd.getString("profiles"));
		pd.put("message_content", pd.getString("message_content"));
		pd.put("create_time", DateUtil.getTime());
		pd.put("status", 1);
		qishouService.saveinsert(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 系统消息编辑页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goSysMessageEdit")
	public ModelAndView goSysMessageEdit() throws Exception{
		logBefore(logger, "------系统消息编辑页面-----");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd  = qishouService.getDateBysysMessageId(pd);
		mv.addObject("pd", pd);
		mv.addObject("msg", "SysMessageupdate");
		mv.setViewName("information/qiShou/sysMessage_edit");
		return mv;
	}
	
	/**
	 * 执行系统消息编辑
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/SysMessageupdate")
	public ModelAndView SysMessageupdate() throws Exception{
		logBefore(logger, "------执行系统消息编辑-----");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("title", pd.getString("title"));
		pd.put("profiles", pd.getString("profiles"));
		pd.put("message_content", pd.getString("message_content"));
		qishouService.SysMessageupdate(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 执行系统消息删除
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/sysMessageDelete")
	public void sysMessageDelete(PrintWriter writer) throws Exception{
		logBefore(logger, "-----执行系统消息删除-----");
		PageData pd=new PageData();
		pd=this.getPageData();
		String str[]=pd.getString("ids").split(",");
		List<String> ids=new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			ids.add(str[i]);
		}
		pd.put("ids", ids);
		qishouService.sysMessageDelete(pd);
		writer.close();
	}
	
	//*****************(系统消息管理结束)*********************
	
	
	//*****************(提现明细管理开始)*********************
	/**
	 * 提现明细管理列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/bankCardTixianListPage")
	public ModelAndView bankCardTixianListPage(Page page){
		logBefore(logger, "-------提现明细管理列表---------");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> bankCardTixianList = qishouService.BankCardTixianlistPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("bankCardTixianList",bankCardTixianList);
 			mv.setViewName("information/qiShou/bankCardTixian_list");
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return mv;
	}
	
	/**
	 * 后台骑手端银行转账审核受理操作
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/tixianshuoli")
	public void tixianshuoli(PrintWriter writer) throws Exception{
		logBefore(logger, "---后台骑手端银行转账审核受理操作----");
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
		qishouService.tixianshuoli(map);
		writer.close();
	}
	
	//*****************(提现明细管理结束)*********************
}
