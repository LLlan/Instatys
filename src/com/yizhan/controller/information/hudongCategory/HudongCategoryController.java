package com.yizhan.controller.information.hudongCategory;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.yizhan.service.information.hudongCategory.HudongCategoryService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.DateUtil;
import com.yizhan.util.PageData;

/**
 * 互动社区中的标签分类Controller层
 * @author zhangjh
 * 2017年5月17日
 */
@Controller
@RequestMapping(value="/hudongCategory")
public class HudongCategoryController extends BaseController{

	@Resource(name="hudongCategoryService")
	private HudongCategoryService hudongCategoryService;
	/**
	 * 获取页面列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getlistPage")
	public ModelAndView getlistPage(Page page) throws Exception{
		logBefore(logger, "获取互动社区标签页面列表");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=hudongCategoryService.getlistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/hudongCategory/hudongCategory_list");
		return mv;
	}
	/**
	 * 进入添加页面
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		logBefore(logger, "进入互动社区标签添加页面");
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg", "insert");
		mv.setViewName("information/hudongCategory/hudongCategory_edit");
		return mv;
	}
	/**
	 * 验证是否存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectByName")
	@ResponseBody
	public Object selectByName() throws Exception{
		logBefore(logger, "判断互动社区标签名称是否存在");
		Map<String, Object> map=new HashMap<String, Object>();
		String result="";
		PageData pd=new PageData();
		pd=this.getPageData();
		PageData pd1=hudongCategoryService.getDateByIdodName(pd);
		if(pd1 != null){//说明已经存在，无需添加
			result="已存在";
		}else{
			result="不存在";
		}
		map.put("result", result);
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 添加信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public ModelAndView insert() throws Exception{
		logBefore(logger, "添加互动社区标签名称到数据库");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("hudong_category_id", this.get32UUID());
		pd.put("addTime", DateUtil.getTime());
		hudongCategoryService.insert(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 进入修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit() throws Exception{
		logBefore(logger, "进入互动社区标签修改页面");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd=hudongCategoryService.getDateByIdodName(pd);
		mv.addObject("pd", pd);
		mv.addObject("msg", "update");
		mv.setViewName("information/hudongCategory/hudongCategory_edit");
		return mv;
	}
	/**
	 * 对指定对象进行修改
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/update")
	public ModelAndView update() throws Exception{
		logBefore(logger, "对指定互动社区标签进行修改");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("addTime", DateUtil.getTime());
		hudongCategoryService.update(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 删除指定的记录
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter writer) throws Exception{
		logBefore(logger, "删除指定互动社区标签名称");
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("level_id", pd.get("tagID"));
		hudongCategoryService.delete(pd);
		writer.close();
	}
	/**
	 * 批量删除记录
	 * @return
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll(){
		logBefore(logger, "批量删除指定互动社区标签名称");
		PageData pd=new PageData();
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			pd=this.getPageData();
			List<PageData> listpd=new ArrayList<PageData>();
			String USER_ids=pd.getString("USER_ids");
			if(USER_ids!=null && !USER_ids.equals("")){
				String ArrayUSER_ids[]=USER_ids.split(",");//分割成数组
				System.out.println("ArrayUSER_ids[]"+ArrayUSER_ids);
				hudongCategoryService.deleteAll(ArrayUSER_ids);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			listpd.add(pd);
			map.put("list", listpd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
}
