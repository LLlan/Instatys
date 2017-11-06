package com.yizhan.controller.information.orderTongCheng;

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
import com.yizhan.service.information.orderTongCheng.OrderTongchengService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.PageData;
import com.yizhan.util.Tools;

/**
 * 司机同城打车订单后台控制器
 * @类名称： orderTongchengController
 * @作者：lj 
 * @时间： 2017-5-18 下午4:07:54
 *
 */
@Controller
@RequestMapping(value="/orderTongcheng")
public class OrderTongchengController extends BaseController{
	
	@Resource(name="orderTongchengService")
	private OrderTongchengService orderTongchengService;
	/**
	 * 同城打车订单列表分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/orderTongchengListPage")
	public ModelAndView orderTongchengList(Page page){
		logBefore(logger, "同城打车订单分页列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData> orderTongchengList = orderTongchengService.queryOrderTongchengListPage(page);
 			mv.addObject("pd",pd);
 			mv.addObject("orderTongchengList",orderTongchengList);
 			mv.setViewName("information/orderTongCheng/orderTongCheng_list");
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.addObject("pd", pd);
			mv.addObject("msg", "save");
			mv.setViewName("information/orderTongCheng/orderTongCheng_edit");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 执行新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(
			@RequestParam(value="destination",required=false) String destination,
			@RequestParam(value="departurePlace",required=false) String departurePlace,
			@RequestParam(value="mileage",required=false) String mileage,
			@RequestParam(value="mileage",required=false) String radeAmount,
			@RequestParam(required=false) String kehu_user_fid,
			@RequestParam(required=false) String user_siji_fid
			) throws Exception{
		logBefore(logger, "执行新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("order_tongcheng_id", this.get32UUID());	//主键
		pd.put("destination", destination);	
		pd.put("departurePlace", departurePlace);	
		pd.put("radeAmount", radeAmount);	
		pd.put("orderTime", Tools.date2Str(new Date()));	//下单时间
		pd.put("kehu_user_fid", pd.getString("kehu_user_fid"));
		pd.put("user_siji_fid", pd.getString("user_siji_fid"));
		orderTongchengService.InsertOrderTongcheng(pd);
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
			pd = orderTongchengService.findById(pd);	//根据ID读取一条数据
			mv.setViewName("information/orderTongCheng/orderTongCheng_edit");
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
	public ModelAndView edit(
			@RequestParam(required=false) String suigong_name,
			@RequestParam(required=false) String suigong_plan,
			@RequestParam(required=false) String kehu_user_fid,
			@RequestParam(required=false) String user_siji_fid
			) throws Exception{
		logBefore(logger, "执行修改");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("suigong_name", suigong_name);
		pd.put("suigong_user", suigong_plan);
		pd.put("suigong_item", Tools.date2Str(new Date()));	//修改时间
		pd.put("kehu_user_fid", pd.getString("kehu_user_fid"));
		pd.put("user_siji_fid", pd.getString("user_siji_fid"));
		orderTongchengService.UpdateOrderTongcheng(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
			orderTongchengService.del(pd);
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
				orderTongchengService.deleteAll(ArrayDATA_IDS);
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

	/**
	 * 根据条件查询一条记录，判断是否存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectBySuigongName")
	@ResponseBody
	public Object selectBySuigongName() throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		String result="";
		PageData pd=new PageData();
		pd=this.getPageData();
		PageData pds=new PageData();
		pds=orderTongchengService.getBySuigongName(pd);
		if(pds != null){//说明该数据已经存在，无需执行
			result="已存在";
		}else{
			result="不存在";
		}
		map.put("result", result);
		return AppUtil.returnObject(pd, map);
	}

}
