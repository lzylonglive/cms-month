package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.ArticleMapper;
import com.mapper.SpecialMapper;
import com.pojo.Special;
import com.service.impl.SpecialServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月26日
 */
@Service
public class SpecialService implements SpecialServiceImpl {

	@Autowired
	SpecialMapper smapper;
	
	@Autowired
	ArticleMapper amapper;

	/**
	 * 专题列表
	 * @return
	 */
	@Override
	public List<Special> list() {
		// TODO Auto-generated method stub
		List<Special> list = smapper.list();
		for (Special special : list) {
			special.setArticleNum(amapper.getArticleNum(special.getId()));
		}
		return list;
	}

	/**
	 * 添加专题
	 * @param special
	 * @return
	 */
	@Override
	public int add(Special special) {
		// TODO Auto-generated method stub
		return smapper.add(special);
	}

	/**
	 * 查看专题详情
	 * @param id
	 * @return
	 */
	@Override
	public Special findById(Integer id) {
		// TODO Auto-generated method stub
		Special special = smapper.findById(id);
		special.setArticleList(amapper.findByIdSpecialId(id));
		return special;
	}

	/**
	 * 修改专题
	 * @param special
	 * @return
	 */
	@Override
	public int update(Special special) {
		// TODO Auto-generated method stub
		return smapper.update(special);
	}

	/**
	 * 向专题中添加文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	@Override
	public int addArticle(Integer specialId, Integer articleId) {
		// TODO Auto-generated method stub
		return smapper.addArticle(specialId,articleId);
	}

	/**
	 * 移除文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	@Override
	public int removeArticle(Integer specialId, Integer articleId) {
		// TODO Auto-generated method stub
		return smapper.removeArticle(specialId,articleId);
	}
	
}
