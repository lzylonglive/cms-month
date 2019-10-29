package com.service.impl;

import java.util.List;

import com.pojo.Special;

/**
 * @作者：lzy
 * @时间：2019年10月26日
 */
public interface SpecialServiceImpl {

	/**
	 * 专题列表
	 * @return
	 */
	List<Special> list();

	/**
	 * 添加专题
	 * @param special
	 * @return
	 */
	int add(Special special);

	/**
	 * 查看专题详情
	 * @param id
	 * @return
	 */
	Special findById(Integer id);

	/**
	 * 修改专题
	 * @param special
	 * @return
	 */
	int update(Special special);

	/**
	 * 向专题中添加文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	int addArticle(Integer specialId, Integer articleId);

	/**
	 * 移除文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	int removeArticle(Integer specialId, Integer articleId);

}
