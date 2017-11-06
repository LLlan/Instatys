package com.yizhan.service.information.h5kehu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yizhan.dao.DaoSupport;
import com.yizhan.entity.Page;
import com.yizhan.util.PageData;
/**
 * h5Service
 * 功能：
 * 作者： lj
 * date：2017-8-9
 *
 */

@Service("h5KeHuService")
public class H5KeHuService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 根据手机号或者用户名去查询用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByNameOrPhone(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataByNameOrPhone", pd);
	}
	
	/**
	 * 根据ID查询用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataById", pd);
	}
	
	/**
	 * 保存客户端用户信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd) throws Exception{
		dao.save("keHuMapper.saveKeHuUser", pd);
	}
	
	/**
	 * 根据手机号判断该用户是否已经存在 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByPhone(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("keHuMapper.getDataByPhone", pd);
	}
	
	/**
	 * 根据登录名和密码获取对象信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByNameAndPaw(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataByNameAndPaw", pd);
	}
	
	/**
	 * 每次登录时,更新登录时间和IP
	 * @param pd
	 * @throws Exception
	 */
	public void updateLoginTimeAndIp(PageData pd) throws Exception{
		dao.update("keHuMapper.updateLoginTimeAndIp", pd);
	}
	/**
	 *根据登录成功后的返回码 BackCode去查询缓存信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDataByBackCode(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getDataByBackCode", pd);
	}
	/**
	 * 保存缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveCacheData(PageData pd) throws Exception{
		dao.save("keHuMapper.saveCacheData", pd);
	}
	/**
	 * 清除缓存信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteCacheInfo(PageData pd) throws Exception{
		dao.delete("keHuMapper.deleteCacheInfo", pd);
	}
	
	/**
	 * 根据手机号修改登录密码
	 * @param pd
	 * @throws Exception
	 */
	public void updatePasswordByPhone(PageData pd) throws Exception{
		dao.update("keHuMapper.updatePasswordByPhone", pd);
	}
	public PageData getUserByNameAndPwd(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getUserByNameAndPwd", pd);
	}
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("keHuMapper.updateLastLogin", pd);
	}
	public void insertcach(PageData pd) throws Exception {
			dao.save("keHuMapper.insertcach", pd);
	}
	
	/**
	 * 修改头像
	 * @param pd
	 * @throws Exception
	 */
	public void touxiang(PageData pd) throws Exception {
		 dao.update("keHuMapper.touxiang", pd);
	}
	
	public PageData selectbyphone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.selectbyphone", pd);
	}
	public PageData selectbyphone1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.selectbyphone1", pd);
	}
	
	/**
	 * 修改用户名
	 * @param pd
	 * @throws Exception
	 */
	public void saveyonghuming(PageData pd) throws Exception {
		dao.save("keHuMapper.saveyonghuming", pd);
	}
	
	/**
	 * 执行同城呼叫
	 * @param pd
	 * @throws Exception
	 */
	public void savetongchengCar(PageData pd) throws Exception {
		dao.save("keHuMapper.savetongchengCar", pd);
	}
	
	/**
	 * 根据id查询一条同城信息大约金额
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getTongchengCar(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getTongchengCar", pd);
	}
	
	/**
	 * 执行取消呼叫同城订单
	 * @param pd
	 * @throws Exception
	 */
	public void SetorderStatus(PageData pd) throws Exception {
		dao.delete("keHuMapper.SetorderStatus", pd);
	}
	
	/**
	 * 执行取消呼叫已接驾的同城订单
	 * @param pd
	 * @throws Exception
	 */
	public void deleteSetorderStatus(PageData pd) throws Exception {
		dao.delete("keHuMapper.deleteSetorderStatus", pd);
	}
	
	/**
	 * 执行删除呼叫同城信息被抢了 2
	 * @param pd
	 * @throws Exception
	 */
	public void DeletehujiaoStatusTow(PageData pd) throws Exception {
		dao.delete("keHuMapper.DeletehujiaoStatusTow", pd);
	}
	
	/**
	 * 执行删除呼叫同城信息
	 * @param pd
	 * @throws Exception
	 */
	public void cancelTongchenghujiaoDelete(PageData pd) throws Exception {
		 dao.delete("keHuMapper.cancelTongchenghujiaoDelete", pd);
	}
	
	/**
	 * 执行新增收货地址
	 * @param pd
	 * @throws Exception
	 */
	public void saveaddress(PageData pd) throws Exception {
		dao.save("keHuMapper.saveaddress", pd);
	}
	
	/**
	 * 执行编辑收货地址
	 * @param pd
	 * @throws Exception
	 */
	public void saveEdit(PageData pd) throws Exception {
		dao.update("keHuMapper.saveEdit", pd);
	}
	
	/**
	 * 执行删除收货地址
	 * @param pd
	 * @throws Exception
	 */
	public void addressDelete(PageData pd) throws Exception {
		dao.delete("keHuMapper.addressDelete", pd);
	}
	
	/**
	 * 批量设置成不是默认
	 */
	public void setisDefaultON(String[] ids) throws Exception{
		dao.update("keHuMapper.setisDefaultON", ids);
	}
	
	/**
	 * 根据id查询一条信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData addressEdit(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.addressEdit", pd);
	}
	
	/**
	 * 确认订单的默认地址
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getByisDefault(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getByisDefault", pd);
	}
	
	/**
	 * 查询电话号码是否重复，即该用户是否注册过
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("keHuMapper.findByPhone", pd);
	}
	
	/**
	 * 换绑手机新手机号
	 * @param pd
	 * @throws Exception
	 */
	public void updatephone(PageData pd) throws Exception {
		dao.update("keHuMapper.updatephone", pd);
		
	}
	
	/**
	 * 设置支付密码
	 * @param pd
	 * @throws Exception
	 */
	public void updatepayPassword(PageData pd) throws Exception {
		dao.update("keHuMapper.updatepayPassword", pd);
		
	}
	
	/**
	 * 动态码表类型
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selectall(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.selectall",pd);
	}
	
	/**
	 * 动态社区列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selecHuDongSheQuList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.selecHuDongSheQuList",pd);
	}
	
	/**
	 * 根据id查询一条信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryHudongDongtai(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.queryHudongDongtai", pd);
	}
	
	/**
	 * 去顶级评论点赞
	 * @param pd
	 * @throws Exception
	 */
	public void zanNumber(PageData pd) throws Exception {
		dao.update("keHuMapper.zanNumber", pd);
		
	}
	
	/**
	 * 根据id查询一条一级评论信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryHudongPinglun(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.queryHudongPinglun", pd);
	}
	
	/**
	 * 一级评论回复总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querycount(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.querycount", pd);
	}
	
	/**
	 * 二级评论回复总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querycountTwo(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.querycountTwo", pd);
	}
	
	/**
	 * 收货地址列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> addressList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.addressList",pd);
	}
	
	/**
	 * 长途路线列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> changtuCarList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.changtuCarList",pd);
	}
	
	/**
	 * 查询我的拼车列表分页
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> querySijiInformationKeHuChangTuPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("keHuMapper.querySijiInformationKeHuChangTuPage", page);
	}
	
	/**
	 * 我的发布详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData changtuXiangdan(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.changtuXiangdan", pd);
	}
	
	/**
	 * 保存发互动社区动态内容
	 * @param pd
	 * @throws Exception
	 */
	public void savefabu(PageData pd) throws Exception {
		dao.save("keHuMapper.savefabu", pd);
	}
	
	/**
	 * 保存发布互动图片
	 * @param pd
	 * @throws Exception
	 */
	public void savefile(PageData pd) throws Exception {
		dao.save("keHuMapper.savefile", pd);
	}
	
	/**
	 * 保存我的拼车人数 
	 * @param pd
	 * @throws Exception
	 */
	public void savechengkes(PageData pd) throws Exception {
		dao.save("keHuMapper.savechengkes", pd);
	}
	
	
	/**
	 * 拼车人数
	 * @param pd
	 * @throws Exception
	 */
	public void setpincheuserNum(PageData pd) throws Exception {
		dao.update("keHuMapper.setpincheuserNum", pd);
		
	}
	
	/**
	 * 查询拼车人数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querysetpincheuserNum(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.querysetpincheuserNum", pd);
	}
	
	/**
	 * 获取司机端长途拼车信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryinformationChangTu(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.queryinformationChangTu", pd);
	}
	
	/**
	 * 保存长途发布路线
	 * @param pd
	 * @throws Exception
	 */
	public void savefabuctlx(PageData pd) throws Exception {
		dao.save("keHuMapper.savefabuctlx", pd);
	}
	
	
	/**
	 * 执行保存发布评论
	 * @param pd
	 * @throws Exception
	 */
	public void savepinglun(PageData pd) throws Exception {
		dao.save("keHuMapper.savepinglun",pd);
	}
	
	/**
	 * 一级评论列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> hudongPinglunlistPage(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.hudongPinglunListPage",pd);
	}
	
	
	/**
	 * 二级评论列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> hudongPinglunTwolistPage(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.hudongPinglunTwoListPage",pd);
	}
	
	/**
	 * 执行保存发二级评论
	 * @param pd
	 * @throws Exception
	 */
	public void savepinglunBieren(PageData pd) throws Exception {
		dao.save("keHuMapper.savepinglunBieren",pd);
	}
	
	
	public PageData sel(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.sel", pd);
	}
	
	
	
	public PageData selephone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.selephone",pd);
	}
	
	
	
	//----------------------------点赞功能----------------------------//
	/**
	 * 查询一级点赞数量是否为空
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData selectOneDianzanNum(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.selectOneDianzanNum",pd);
	}
	
	/**
	 * 查询一级点赞总数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDianzanNumberByHudongId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.getDianzanNumberByHudongId",pd);
	}
	
	/**
	 * 执行保存一级点赞
	 * @param pd
	 * @throws Exception
	 */
	public void insertOneDianzanNum(PageData pd) throws Exception {
		dao.save("keHuMapper.insertOneDianzanNum",pd);
	}
	
	/**
	 * 查询二级点赞数量是否为空
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData selectTwoDianzanNum(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.selectTwoDianzanNum",pd);
	}
	
	/**
	 * 查询二级点赞总数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDianzanNumberByPinglunId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.getDianzanNumberByPinglunId",pd);
	}
	
	/**
	 * 执行保存二级点赞
	 * @param pd
	 * @throws Exception
	 */
	public void insertTwoDianzanNum(PageData pd) throws Exception {
		dao.save("keHuMapper.insertTwoDianzanNum",pd);
	}
	
	/**
	 * 查询三级点赞数量是否为空
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData selectThreeDianzanNum(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.selectThreeDianzanNum",pd);
	}
	
	/**
	 * 查询三级点赞总数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDianzanNumberByPinglunTwoId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("keHuMapper.getDianzanNumberByPinglunTwoId",pd);
	}
	
	/**
	 * 执行保存三级点赞
	 * @param pd
	 * @throws Exception
	 */
	public void insertThreeDianzanNum(PageData pd) throws Exception {
		dao.save("keHuMapper.insertThreeDianzanNum",pd);
	}
	
	/**
	 * 美食外卖商家页面列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryUserShangjiaList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("keHuMapper.queryUserShangjiaList", pd);
	}
	
	/**
	 * 轮播图列表
	 * @作者:lj
	 * 2017-8-24下午5:41:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> picturesList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("keHuMapper.picturesList", pd);
	}
	
	
	/**
	 * 商家码表类型
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> goodsCategoryList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.goodsCategoryList",pd);
	}
	
	/**
	 * 商家商品列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> goodsList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.goodsList",pd);
	}
	
	/**
	 * 商家详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryUserShangjia(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.queryUserShangjia", pd);
	}
	
	/**
	 * 去商品详情页面
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryshangpingxing(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.queryshangpingxing", pd);
	}
	
	/**
	 * ajax 请求 页面初始化 根据商品分类名字和商家id查出列表数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getDataByCategoryNameAndId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.getDataByCategoryNameAndId",pd);
	}
	
	
	/**
	 * 保存外卖订单
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-3
	 */
	public void insertOrderTakeou(PageData pd) throws Exception {
		dao.save("keHuMapper.insertOrderTakeou",pd);
	}
	
	/**
	 * 保存外卖订单商品表 一个外卖单对应多个商品 这是关系表
	 * @param pd
	 * @throws Exception
	 */
	public void insertOrderGoods(PageData pd) throws Exception {
		dao.save("keHuMapper.insertOrderGoods",pd);
	}
	
	/**
	 * 我的外卖订单全部订单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryOrderTakeouLists(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("keHuMapper.queryOrderTakeouLists", pd);
	}
	
	/**
	 * 订单详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData querytbOrderTakeou(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.querytbOrderTakeou", pd);
	}
	
	/**
	 * 执行删除外卖订单
	 * @param pd
	 * @throws Exception
	 */
	public void quxiaoOrderDelete(PageData pd) throws Exception {
		dao.delete("keHuMapper.quxiaoOrderDelete", pd);
	}
	
	/**
	 * 执行删除外卖订单
	 * @param pd
	 * @throws Exception
	 */
	public void quxiaoOrderGoodsDelete(PageData pd) throws Exception {
		dao.delete("keHuMapper.quxiaoOrderGoodsDelete", pd);
	}
	
	/**
	 * 客户端详细商家商品列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> kehuOrderGoodsLists(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.kehuOrderGoodsLists",pd);
	}
	
	/**
	 * 客户端正在进行中的订单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryOrderTakeouHaveInHand(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.queryOrderTakeouHaveInHand", pd);
	}

	
	/**
	 * 保存临时订单表
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-2
	 */
	public void saveTempOrder(PageData pd) throws Exception {
		dao.update("keHuMapper.saveTempOrder", pd);
		
	}
	
	/**
	 * 删除临时订单 根据客户id
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-2
	 */
	public void delTempOrder(PageData pd) throws Exception {
		dao.delete("keHuMapper.delTempOrder", pd);
		
	}
	/**
	 * 减到0则删除
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-17
	 */
	public void delTempOrderBySpID(PageData pd) throws Exception {
		dao.delete("keHuMapper.delTempOrderBySpID", pd);
		
	}
	
	/**
	 *  修改临时订单表的某件商品的数量 
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-17
	 */
	public void updateNumberbyId(PageData pd) throws Exception {
		dao.delete("keHuMapper.updateNumberbyId", pd);
		
	}
	
	/**
	 * 查询临时订单
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-2
	 */
	public List<PageData> selectTempOrderList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("keHuMapper.selectTempOrderList",pd);
	}
	
	/**
	 * 查询总价格
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-2
	 */
	public PageData getTotolByUserKeHuID(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getTotolByUserKeHuID", pd);
	}
	
	/**
	 * 查询最大的取餐号
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-3
	 */
	public PageData selectMaxQucanNumber(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.selectMaxQucanNumber", pd);
	}
	
	/**
	 * 查询商家的起送价格
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-3
	 */
	public PageData getqisonjiagebyID(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getqisonjiagebyID", pd);
	}
	
	/**
	 * 查餐盒费
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-8
	 */
	public PageData getCanhefeiSUM(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getCanhefeiSUM", pd);
	}
	
	
	/**
	 * 更新订单编号到订单表中
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-9
	 */
	public void updateOrderNumber(PageData pd) throws Exception {
		dao.update("keHuMapper.updateOrderNumber", pd);
		
	}
	
	/**
	 * 更新订单信息
	 * 功能：
	 * 作者：lj
	 * 日期：2017-8-9
	 */
	public void updateOrderInfoByOrderNumber(PageData pd) throws Exception {
		dao.update("keHuMapper.updateOrderInfoByOrderNumber", pd);
		
	}
	
	/**
	 * 根据用户id查询一条同城订单是否存在进行中信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getorderTongchengjinxing(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getorderTongchengjinxing", pd);
	}
	
	/**
	 * 根据用户id查询一条同城订单是否存在未付款信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getorderTongcheng(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getorderTongcheng", pd);
	}
	
	/**
	 * 根据用户id查询一条同城订单是否存在已接驾订单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getorderTongyijiejia(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getorderTongyijiejia", pd);
	}
	
	/**
	 * 根据用户id查询一条长途订单是否存在未付款信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getorderChangtu(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getorderChangtu", pd);
	}
	
	/**
	 * 根据外卖订单编号获取查询商家的id
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData geuserShangjiaFid(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.geuserShangjiaFid", pd);
	}
	
	/**
	 * 根据同城信息ID获取查询司机id
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserSijiFid(PageData pd) throws Exception{
		return (PageData) dao.findForObject("keHuMapper.getUserSijiFid", pd);
	}
	
	/**
	 * 获取指定司机设备ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryRegistrationID(PageData pd) throws Exception{
		return (PageData)dao.findForObject("keHuMapper.queryRegistrationID", pd);
	}
}

