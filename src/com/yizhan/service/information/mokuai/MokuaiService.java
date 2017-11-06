package com.yizhan.service.information.mokuai;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;
@Service("mokuaiService")
public class MokuaiService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
	 * 获取列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> mokuailistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("mokuaiMapper.mokuailistPage", page);
	}
	/**
	 * 保存
	 * @param pd
	 * @throws Exception
	 */
	public void insert(PageData pd) throws Exception{
		dao.save("mokuaiMapper.insert", pd);
	}
	/**
	 * 根据ID获取对象信息
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData getDateById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("mokuaiMapper.getDateById", pd);
	}
	/**
	 * 更新指定对象的信息
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd) throws Exception{
		dao.update("mokuaiMapper.update", pd);
	}
	/**
	 * 根据ID删除指定对象的信息
	 * @param map
	 * @throws Exception
	 */
	public void delete(Map<String, Object> map) throws Exception{
		dao.delete("mokuaiMapper.delete", map);
	}
	/**
	 * 获取集合列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getListOfMokuai(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("mokuaiMapper.getListOfMokuai", pd);
	}
}
