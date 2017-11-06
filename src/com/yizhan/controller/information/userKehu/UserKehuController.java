package com.yizhan.controller.information.userKehu;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.service.information.keHu.KeHuService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.PageData;
import com.yizhan.util.Tools;

/**
 * 客户端用户列表后台功能控制器
 * @类名称： UserKehuController
 * @作者：lj 
 * @时间： 2017-6-27 下午2:48:03
 *
 */
@Controller
@RequestMapping(value="/userKehu")
public class UserKehuController extends BaseController {

	@Resource(name = "keHuService")
	private KeHuService keHuService;
	
	/**
	 * 分页列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/userKehulistPage")
	public ModelAndView UserKehulistPage(Page page){
		logBefore(logger, "-------客户端用户管理分页列表---------");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> userKehulist = keHuService.UserKehulistPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("userKehulist",userKehulist);
 			mv.setViewName("information/userKehu/userKehu_list");
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
	
	//-------------------------同城计费设置-----------------------------
	
	/**
	 * 同城打车计费设置列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/tongchengjifeilistPage")
	public ModelAndView tongchengjifeilistPage(Page page){
		logBefore(logger, "-------同城打车计费设置列表---------");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> jifeilist = keHuService.tongchengjifeilistPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("jifeilist",jifeilist);
 			mv.setViewName("information/tcJifeilistSet/jifei_list");
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return mv;
	}
	
	/**
	 * 进入添加页面
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		logBefore(logger, "进入计费设置添加页面");
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg", "insertjifei");
		mv.setViewName("information/tcJifeilistSet/jifei_edit");
		return mv;
	}
	
	/**
	 * 执行新增
	 */
	@RequestMapping(value="/insertjifei")
	public ModelAndView save() throws Exception{
		logBefore(logger, "执行新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("jifei_id", this.get32UUID());	//主键
		pd.put("qibugongli", pd.getString("qibugongli"));	
		pd.put("qibujia", pd.getString("qibujia"));	
		pd.put("jifei_Amount", pd.getString("jifei_Amount"));	
		pd.put("create_time", Tools.date2Str(new Date()));	//创建时间
		keHuService.Insertjifei(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = keHuService.getDataJifeiId(pd);	//根据ID读取一条数据
			mv.setViewName("information/tcJifeilistSet/jifei_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 执行修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "执行修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("qibugongli", pd.getString("qibugongli"));	
		pd.put("qibujia", pd.getString("qibujia"));	
		pd.put("jifei_Amount", pd.getString("jifei_Amount"));	
		pd.put("update_time", Tools.date2Str(new Date()));	//修改时间
		keHuService.updateJifei(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 计费设置执行删除
	 */
	@RequestMapping(value="/deljifei")
	public void deletejifei(PrintWriter out){
		logBefore(logger, "删除");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			keHuService.deljifei(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				keHuService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
}
