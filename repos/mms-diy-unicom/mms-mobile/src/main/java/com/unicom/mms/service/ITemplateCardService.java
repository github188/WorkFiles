package com.unicom.mms.service;

import java.util.List;

import com.unicom.mms.entity.TbTemplateCard;
import com.unicom.mms.util.PageInfo;

/**
 * 
* 功能描述: 模板明信片管理
* @author chenliang 新增日期：2013-1-14
* @since mms-cms-unicom
 */
public interface ITemplateCardService extends ICommonService<TbTemplateCard> {

	/**
	 * 
	 * 更多模版明信片
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @author zhangjh 新增日期：2013-7-10
	 * @since mms-mobile
	 */
	public PageInfo<TbTemplateCard> page(int currentPage,int pageSize);
	
	/**
	 * 
	 * 根据选择的模板ID查询模板图片
	 * 
	 * @param tempPicId
	 * @return TbTemplateCard
	 * @author lizl 新增日期：2013-7-11
	 * @since mms-mobile
	 */
	public TbTemplateCard queryTempPicById(int tempPicId);
}