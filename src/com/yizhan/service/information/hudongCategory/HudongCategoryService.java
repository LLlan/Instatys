package com.yizhan.service.information.hudongCategory;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;
/**
 * 互动社区中的标签分类Service层
 * @author zhangjh
 * 2017年5月17日
 */
@Service("hudongCategoryService")
public class HudongCategoryService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 添加
	 * @param pd
	 * @throws Exception
	 */
	public void insert(PageData pd) throws Exception{
		dao.save("hudongCategoryMapper.insert", pd);
	}
	/**
	 * 查询所有的信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getlistPage(Page page) throws Exception{
		
		return (List<PageData>)dao.findForList("hudongCategoryMapper.getlistPage", page);
	}
	
	/**
	 * 根据主键ID或categoryName获取对象信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData getDateByIdodName(PageData pd) throws Exception{
		return (PageData) dao.findForObject("hudongCategoryMapper.getDateByIdorName", pd);
	}
	/**
	 * 更新指定记录
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd) throws Exception{
		dao.update("hudongCategoryMapper.update", pd);
	}
	
	/**
	 * 删除指定记录
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception{
		dao.delete("hudongCategoryMapper.delete", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] USER_IDS)throws Exception{
		dao.delete("hudongCategoryMapper.deleteAll", USER_IDS);
	}
}
