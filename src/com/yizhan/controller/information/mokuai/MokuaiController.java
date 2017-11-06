package com.yizhan.controller.information.mokuai;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.format.datetime.joda.MillisecondInstantPrinter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.entity.information.KeHu;
import com.yizhan.service.information.mokuai.MokuaiService;
import com.yizhan.util.Const;
import com.yizhan.util.DateUtil;
import com.yizhan.util.FileUpload;
import com.yizhan.util.PageData;
import com.yizhan.util.PathUtil;

@Controller
@RequestMapping(value="/api/mokuai")
public class MokuaiController extends BaseController{
	@Resource(name="mokuaiService")
	private MokuaiService mokuaiService;
	
	/**
	 * 获取列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/mokuailistPage")
	public ModelAndView mokuailistPage(Page page) throws Exception{
		logBefore(logger, "获取列表");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=mokuaiService.mokuailistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/mokuai/mokuai_list");
		return mv;
	}
	/**
	 * 进入添加页面
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		logBefore(logger, "进入添加页面");
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg", "insert");
		mv.setViewName("information/mokuai/mokuai_edit");
		return mv;
	}
	/**
	 * 添加信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public ModelAndView insert(
			@RequestParam(required=false) MultipartFile file,
			String title,
			String introduct,
			String type
			) throws Exception{
		logBefore(logger, "添加信息");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		
		//保存图片
		String filePath=Const.FILEPATHFILE + "mokuai/headimg/" + DateUtil.getDays() + "/";
		String fileName=FileUpload.fileUp(file,PathUtil.getClasspath() + filePath, this.get32UUID());
		
		//保存信息
		pd.put("mokuai_id", this.get32UUID());
		pd.put("addTime", DateUtil.getTime());
		pd.put("title", title);
		pd.put("introduct", introduct);
		pd.put("type", type);
		pd.put("headImg", filePath+fileName);
		
		mokuaiService.insert(pd);
		
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
		logBefore(logger, "进入修改页面");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		//获取指定对象信息
		pd=mokuaiService.getDateById(pd);
		mv.addObject("pd", pd);
		mv.addObject("msg", "update");
		
		mv.setViewName("information/mokuai/mokuai_edit");
		return mv;
	}
	/**
	 * 对指定对象进行修改
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(
			@RequestParam(required=false) MultipartFile file,
			String title,
			String introduct,
			String type,
			String headImg,
			String mokuai_id
			) throws Exception{
		logBefore(logger, "对指定对象进行修改");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		
		if(file!=null && !file.isEmpty()){
			//保存图片
			String filePath=Const.FILEPATHFILE + "mokuai/headimg/" + DateUtil.getDays() + "/";
			String fileName=FileUpload.fileUp(file,PathUtil.getClasspath() + filePath, this.get32UUID());
			pd.put("headImg", filePath+fileName);
		}else{
			pd.put("headImg", headImg);
		}
		
		//更新信息
		pd.put("mokuai_id", mokuai_id);
		pd.put("addTime", DateUtil.getTime());
		pd.put("title", title);
		pd.put("introduct", introduct);
		pd.put("type", type);
		
		mokuaiService.update(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 根据ID删除指定对象的信息
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter writer) throws Exception{
		logBefore(logger, "---根据ID删除指定对象的信息--");
		PageData pd=new PageData();
		Map<String, Object> map=new HashMap<String, Object>();
		pd=this.getPageData();
		String id[]=pd.getString("ids").split(",");
		List<String> ids=new ArrayList<String>();
		for (int i = 0; i < id.length; i++) {
			ids.add(id[i]);
		}
		map.put("ids", ids);
		mokuaiService.delete(map);
		writer.close();
	}
	/**
	 * 获取列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListOfMokuai")
	public ModelAndView getListOfMokuai() throws Exception{
		
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		if(isSession()){
			List<PageData> list=mokuaiService.getListOfMokuai(pd);
			mv.addObject("list", list);
			
			String msg="";
			if(pd.getString("type").equals("1")){
				msg="旅游景点";
			}else if(pd.getString("type").equals("2")){
				msg="汽车服务";
			}else if(pd.getString("type").equals("3")){
				msg="休闲娱乐";
			}else if(pd.getString("type").equals("4")){
				msg="酒店宾馆";
			}else if(pd.getString("type").equals("5")){
				msg="婚礼庆典";
			}else{
				msg="生鲜超市";
			}
			mv.addObject("msg", msg);
			mv.setViewName("information/mokuai/shops");
		}else{
			mv.setViewName("redirect:/api/h5KeHu/toLogin");
		}
	
		return mv;
	}
	
	
	
	/**
	 * 判断session是否存在
	 * 存在：返回TRUE
	 * 反之：返回FALSE
	 * @return
	 */
	public boolean isSession(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		KeHu keHu=(KeHu) session.getAttribute("h5User");
		if(keHu==null){
			return false;
		}
		return true;
	}
	
}
