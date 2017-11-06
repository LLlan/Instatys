package com.yizhan.controller.information.shangJia;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yizhan.controller.base.BaseController;
import com.yizhan.entity.Page;
import com.yizhan.service.information.shangJia.ShangjiaService;
import com.yizhan.service.information.shangjiaOrder.ShangjiaOrderService;
import com.yizhan.util.AppUtil;
import com.yizhan.util.Const;
import com.yizhan.util.DateUtil;
import com.yizhan.util.FileUpload;
import com.yizhan.util.PageData;
import com.yizhan.util.PathUtil;
/**
 * 商家用户Controller层
 * @author zhangjh
 * 2017年5月18日
 */
@Controller
@RequestMapping(value="/shangjia")
public class ShangjiaController extends BaseController{
	public static final String shangpinIMG = "shangjia/shangpin/";//图片上传路径(商家商品图片)
	@Resource(name="shangjiaService")
	private ShangjiaService shangjiaService;
	
	@Resource(name="shangjiaOrderService")
	private ShangjiaOrderService shangjiaOrderService;
	/*================================================商家用户管理======================================================*/
	/**
	 * 获取页面列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getlistPage")
	public ModelAndView getlistPage(Page page) throws Exception{
		logBefore(logger, "获取商家页面列表");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=shangjiaService.getlistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shangJia/shangJia_list");
		return mv;
	}
	/**
	 * 获取指定商家的认证信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getRenZhengInforById")
	@ResponseBody
	public Object getRenZhengInforById() throws Exception{
		logBefore(logger, "获取商家页面列表");
		Map<String, Object> map=new HashMap<String, Object>();
		PageData pd=new PageData();
		pd=this.getPageData();
		
		PageData resultData=new PageData();
		
		//获取信息
		pd=shangjiaService.getRenZhengInforById(pd);
		
		if(pd!=null){
			List<PageData> list1=new ArrayList<PageData>();
			List<PageData> list2=new ArrayList<PageData>();
			
			String[] imgPaths1=pd.getString("imgPaths1").split(",");
			String[] imgPaths2=pd.getString("imgPaths2").split(",");
			
			//店内图
			for (int i = 0; i < imgPaths1.length; i++) {
				PageData temp=new PageData();
				temp.put("imgPath", imgPaths1[i]);
				list1.add(temp);
			}
			
			//商品图
			for (int i = 0; i < imgPaths2.length; i++) {
				PageData temp=new PageData();
				temp.put("imgPath", imgPaths2[i]);
				list2.add(temp);
			}
			
			resultData.put("menlianImg", pd.getString("menlianImg"));
			resultData.put("handIdentityImg", pd.getString("handIdentityImg"));
			resultData.put("businessLicenceImg", pd.getString("businessLicenceImg"));
			resultData.put("licenceImg", pd.getString("licenceImg"));
			resultData.put("logoImg", pd.getString("logoImg"));
			resultData.put("imgPaths1", list1);
			resultData.put("imgPaths2", list2);
		}
		
		map.put("resultData", resultData);
		
		return AppUtil.returnObject(resultData, map);
	}
	/**
	 * 后台商家审核操作
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/syschecked")
	public void syschecked(PrintWriter writer) throws Exception{
		logBefore(logger, "后台商家审核操作");
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
		shangjiaService.syschecked(map);
		writer.close();
	}
	/*================================================商品管理======================================================*/
	/**
	 * 获取页面列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goodsgetlistPage")
	public ModelAndView goodsgetlistPage(Page page) throws Exception{
		logBefore(logger, "获取商品页面列表");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=shangjiaService.goodsgetlistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shangJia/goods_list");
		return mv;
	}
	/**
	 * 进入添加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goodstoAdd")
	public ModelAndView goodstoAdd() throws Exception{
		logBefore(logger, "进入商品添加页面");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		mv.addObject("list", shangjiaService.getlistspfl(pd));
		mv.addObject("msg", "goodsinsert");
		mv.setViewName("information/shangJia/goods_edit");
		return mv;
	}
	/**
	 * 验证是否存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goodsselectByName")
	@ResponseBody
	public Object goodsselectByName() throws Exception{
		logBefore(logger, "判断商品名称是否存在");
		Map<String, Object> map=new HashMap<String, Object>();
		String result="";
		PageData pd=new PageData();
		pd=this.getPageData();
		PageData pd1=shangjiaService.goodsgetDateByIdorName(pd);
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
	@RequestMapping(value="/goodsinsert")
	public ModelAndView goodsinsert(
			MultipartFile goodsImg,
			String goodsName,
			String goodsDetail,
			String originalPrice,
			String presentPrice,
			String canhefei,
			String categoryName
			) throws Exception{
		logBefore(logger, "添加商品分类名称到数据库");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		//上传图片
		String  timePath = DateUtil.getDays(), goodsImgName = "";
		if (null != goodsImg && !goodsImg.isEmpty()){
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + shangpinIMG + timePath;//文件上传路径
			goodsImgName = FileUpload.fileUp(goodsImg, filePath, this.get32UUID());	//执行上传
		}
		pd.put("goodsImg", Const.FILEPATHIMG + shangpinIMG + timePath + "/" + goodsImgName);
		pd.put("goods_id", this.get32UUID());
		pd.put("fabuTime", DateUtil.getTime());
		pd.put("goodsName", goodsName);
		pd.put("originalPrice", originalPrice);
		pd.put("presentPrice", presentPrice);
		//pd.put("canhefei", canhefei);
		pd.put("goodsState", "上架");
		pd.put("categoryName",categoryName);
		//1.获取添加人的主键ID
		//2.pd.put("user_shangjia_fid",id);
		shangjiaService.goodsinsert(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 进入修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goodstoEdit")
	public ModelAndView goods() throws Exception{
		logBefore(logger, "进入商品分类修改页面");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd=shangjiaService.goodsgetDateByIdorName(pd);
		mv.addObject("list", shangjiaService.getlistspfl(pd));
		mv.addObject("pd", pd);
		mv.addObject("msg", "goodsupdate");
		mv.setViewName("information/shangJia/goods_edit");
		return mv;
	}
	/**
	 * 对指定对象进行修改
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goodsupdate")
	public ModelAndView goodsupdate(
			@RequestParam(required=false) MultipartFile goodsImg,
			String goodsName,
			String goodsDetail,
			String originalPrice,
			String presentPrice,
			String canhefei,
			String categoryName,
			String goods_id
			) throws Exception{
		logBefore(logger, "对指定商品分类进行修改");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd.put("goods_id", goods_id);
		PageData pd1=shangjiaService.goodsgetDateByIdorName(pd);
		//上传图片
		String  ffile = DateUtil.getDays(), goodsImgName = "";
		if (null != goodsImg && !goodsImg.isEmpty()){
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + shangpinIMG + ffile;//文件上传路径
			goodsImgName = FileUpload.fileUp(goodsImg, filePath, this.get32UUID());	//执行上传
			pd.put("goodsImg", Const.FILEPATHIMG + shangpinIMG + ffile + "/" + goodsImgName);
			//删除已存在的图片
			File filed1=new File(PathUtil.getClasspath()+pd1.getString("goodsImg"));
			if(filed1.exists()){
				filed1.delete();
			}
		}else{
			pd.put("goodsImg", pd1.getString("goodsImg"));
		}
		pd.put("fabuTime", DateUtil.getTime());
		pd.put("goodsName", goodsName);
		pd.put("goodsDetail", goodsDetail);
		pd.put("originalPrice", originalPrice);
		pd.put("presentPrice", presentPrice);
		pd.put("canhefei", canhefei);
		pd.put("categoryName",categoryName);
		shangjiaService.goodsupdate(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 删除指定的记录
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/goodsdelete")
	public void goodsdelete(PrintWriter writer) throws Exception{
		logBefore(logger, "删除指定商品分类名称");
		PageData pd=new PageData();
		pd=this.getPageData();
		String str[]=pd.getString("ids").split(",");
		List<String> ids=new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			ids.add(str[i]);
		}
		pd.put("ids", ids);
		//删除对象时，删除图片
		PageData pdPaths=shangjiaService.getImgPaths(pd);
		String imgPaths[]=pdPaths.getString("imgPaths").split(",");
		for (int i = 0; i < imgPaths.length; i++) {
			File file=new File(PathUtil.getClasspath()+imgPaths[i]);
			if(file.exists()){
				file.delete();
			}
		}
		shangjiaService.goodsdelete(pd);
		writer.close();
	}
	/**
	 * 修改商品状态
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/goodsstate")
	public void goodsstate(PrintWriter writer) throws Exception{
		logBefore(logger, "修改商品状态");
		PageData pd=new PageData();
		pd=this.getPageData();
		String str[]=pd.getString("ids").split(",");
		List<String> ids=new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			ids.add(str[i]);
		}
		if(pd.getString("state").equals("1")){
			pd.put("goodsState", "上架");
		}else{
			pd.put("goodsState", "下架");
		}
		pd.put("ids", ids);
		shangjiaService.goodsstate(pd);
		writer.close();
	}
	/*================================================商品分类管理======================================================*/
	/**
	 * 获取页面列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getlistPagespfl")
	public ModelAndView getlistPagespfl(Page page) throws Exception{
		logBefore(logger, "获取商品分类页面列表");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=shangjiaService.getlistPagespfl(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shangJia/goodsCategory_list");
		return mv;
	} 
	/**
	 * 进入添加页面
	 * @return
	 */
	@RequestMapping(value="/spfltoAdd")
	public ModelAndView spfltoAdd(){
		logBefore(logger, "进入商品分类添加页面");
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg", "spflinsert");
		mv.setViewName("information/shangJia/goodsCategory_edit");
		return mv;
	}
	/**
	 * 验证是否存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/spflselectByName")
	@ResponseBody
	public Object spflselectByName() throws Exception{
		logBefore(logger, "判断商品分类名称是否存在");
		Map<String, Object> map=new HashMap<String, Object>();
		String result="";
		PageData pd=new PageData();
		pd=this.getPageData();
		PageData pd1=shangjiaService.spflgetDateByIdorName(pd);
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
	@RequestMapping(value="/spflinsert")
	public ModelAndView spflinsert() throws Exception{
		logBefore(logger, "添加商品分类名称到数据库");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("goods_category_id", this.get32UUID());
		pd.put("addTime", DateUtil.getTime());
		/*
			1.获取添加人的主键ID
			2.pd.put("user_shangjia_fid",id);
		 */
		shangjiaService.spflinsert(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 进入修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/spfltoEdit")
	public ModelAndView spfltoEdit() throws Exception{
		logBefore(logger, "进入商品分类修改页面");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd=shangjiaService.spflgetDateByIdorName(pd);
		mv.addObject("pd", pd);
		mv.addObject("msg", "spflupdate");
		mv.setViewName("information/shangJia/goodsCategory_edit");
		return mv;
	}
	/**
	 * 对指定对象进行修改
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/spflupdate")
	public ModelAndView spflupdate() throws Exception{
		logBefore(logger, "对指定商品分类进行修改");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("addTime", DateUtil.getTime());
		shangjiaService.spflupdate(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 删除指定的记录
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value="/spfldelete")
	public void spfldelete(PrintWriter writer) throws Exception{
		logBefore(logger, "删除指定商品分类名称");
		PageData pd=new PageData();
		pd=this.getPageData();
		String str[]=pd.getString("ids").split(",");
		List<String> ids=new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			ids.add(str[i]);
		}
		pd.put("ids", ids);
		shangjiaService.spfldelete(pd);
		writer.close();
	}
	
	/*================================================商家订单管理======================================================*/
	
	/**
	 * 商家订单管理列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/ordergetlistPage")
	public ModelAndView ordergetlistPage(Page page) throws Exception{
		logBefore(logger, "----商家订单管理列表---");
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=shangjiaOrderService.getOrderTakeoulistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shangJia/orderTakeou_list");
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
		List<PageData> list=shangjiaService.getSysMessagelistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("information/shangJia/sysMessage_list");
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
		mv.setViewName("information/shangJia/sysMessage_edit");
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
		shangjiaService.saveinsert(pd);
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
		pd  = shangjiaService.getDateBysysMessageId(pd);
		mv.addObject("pd", pd);
		mv.addObject("msg", "SysMessageupdate");
		mv.setViewName("information/shangJia/sysMessage_edit");
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
		shangjiaService.SysMessageupdate(pd);
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
		shangjiaService.sysMessageDelete(pd);
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
	 			List<PageData> bankCardTixianList = shangjiaService.BankCardTixianlistPage(page);
	 			mv.addObject("pd",pd);
	 			mv.addObject("bankCardTixianList",bankCardTixianList);
	 			mv.setViewName("information/shangJia/bankCardTixian_list");
			} catch (Exception e) {
				logger.error(e.toString(),e);
			}
			return mv;
		}
		
		/**
		 * 后台商家端银行人工转账审核受理操作
		 * @param writer
		 * @throws Exception
		 */
		@RequestMapping(value="/tixianshuoli")
		public void tixianshuoli(PrintWriter writer) throws Exception{
			logBefore(logger, "---后台商家端银行人工转账审核受理操作----");
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
			shangjiaService.tixianshuoli(map);
			writer.close();
		}
		
		//*****************(提现明细管理结束)*********************
}
