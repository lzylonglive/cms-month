package com.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pojo.Article;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
public interface ArticleServiceImpl {

	PageInfo<Article> list(Integer chnId, Integer catId, Integer page);

	PageInfo<Article> hotList(Integer page);

	/**
	 * 获取最新文章
	 * @param sum  获取的数目
	 * @return
	 */
	List<Article> last(int sum);

	/**
	 * 根据文章的主键获取文章的内容
	 * @param articleId
	 * @return
	 */
	Article findById(Integer articleId);

	/**
	 *  添加文章
	 * @param article
	 * @return
	 * @throws Exception 
	 */
	int add(Article article) throws Exception;

	/**
	 * 删除文章
	 * @param id  文章id
	 * @return
	 */
	int remove(Integer id);

	/**
	 *  根据用户id查找文章列表
	 * @param id 用户id
	 * @param page
	 * @return 
	 */
	PageInfo<Article> listArticleByUserId(Integer id, Integer pageNum);
	
	/**
	 *  修改文章
	 * @param article
	 * @return
	 * @throws Exception 
	 */
	int update(Article article) throws Exception;

	/**
	 * 
	 * @param page 页码
	 * @param status 审核的状态
	 * @return
	 */
	PageInfo<Article> getAdminArticles(Integer page, Integer status);

	/**
	 *  审核文章
	 * @param articleId
	 * @param status 要审核的状态
	 * @return
	 */
	int updateStatus(Integer articleId, int status);

	/**
	 *  文章热门状态
	 * @param articleId
	 * @param status 
	 * @return
	 */
	int updateHot(Integer articleId, int status);

	/**
	 * 增加文章点击次数
	 * @param request
	 * @return
	 */
	int addHits(Integer id);

}
