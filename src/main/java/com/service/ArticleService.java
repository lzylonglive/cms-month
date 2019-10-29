package com.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.ArticleMapper;
import com.pojo.Article;
import com.pojo.Term;
import com.service.impl.ArticleServiceImpl;
import com.utils.StringUtil;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
@Service
public class ArticleService implements ArticleServiceImpl {

	@Autowired
	ArticleMapper mapper;

	@Override
	public PageInfo<Article> list(Integer chnId, Integer catId, Integer page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page,10);
		
		return new PageInfo(mapper.list(chnId,catId));
	}

	@Override
	public PageInfo<Article> hotList(Integer page) {
		// TODO Auto-generated method stub
		//设置页码
		PageHelper.startPage(page,10);
		//查询指定页码数据 并返回页面信息
		return new PageInfo(mapper.listHot());
	}

	/**
	 * 获取最新文章
	 * @param sum  获取的数目
	 * @return
	 */
	@Override
	public List<Article> last(int sum) {
		// TODO Auto-generated method stub
		return mapper.listLast(sum);
	}

	/**
	 * 根据文章的主键获取文章的内容
	 * @param articleId
	 * @return
	 */
	@Override
	public Article findById(Integer articleId) {
		// TODO Auto-generated method stub
		return mapper.findById(articleId);
	}

	@Override
	public int add(Article article) throws Exception {
		// TODO Auto-generated method stub
		int result = mapper.add(article);
		processTerm(article);
		return result;
	}
	
	/**
	 *  处理文章的标签
	 * @param article
	 * @throws Exception 
	 */
	private void processTerm(Article article) throws Exception {
		
		if(article.getTerms() == null) {
			return;
		}
		
		String[] terms = article.getTerms().split(",");
		for (String term : terms) {
			// 判断这个tag在数据库当中是否存在
			Term termBean = mapper.findTermByName(term);
			if(termBean == null) {
				String uniqueTerm = StringUtil.toUniqueTerm(term);
				termBean = new Term(term,uniqueTerm);
				mapper.addTerm(termBean);
			}
			
			try {
				mapper.addArticleTerm(article.getId(),termBean.getId());
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("插入失败");
			}
		}
	}

	/**
	 *  根据用户id查找文章列表
	 * @param id 用户id
	 * @param page
	 * @return 
	 */
	@Override
	public PageInfo<Article> listArticleByUserId(Integer id, Integer pageNum) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum,5);
		return new PageInfo<Article>(mapper.listByUserId(id));
	}
	
	/**
	 * 删除文章
	 * @param id  文章id
	 * @return
	 */
	@Override
	public int remove(Integer id) {
		// TODO Auto-generated method stub
		int result = mapper.deleteById(id);
		//删除中间表的内容
		mapper.delTermsByArticleId(id);
		return result;
	}

	/**
	 *  修改文章
	 * @param article
	 * @return
	 * @throws Exception 
	 */
	@Override
	public int update(Article article) throws Exception {
		// TODO Auto-generated method stub
		int result = mapper.update(article);
		//删除中间表中的内容
		mapper.delTermsByArticleId(article.getId());
		processTerm(article);
		return result;
	}

	/**
	 * 
	 * @param page 页码
	 * @param status 审核的状态
	 * @return
	 */
	@Override
	public PageInfo<Article> getAdminArticles(Integer page, Integer status) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page,10);
		
		return new PageInfo<Article>(mapper.listAdmin(status));
	}

	/**
	 *  审核文章
	 * @param articleId
	 * @param status 要审核的状态
	 * @return
	 */
	@Override
	public int updateStatus(Integer articleId, int status) {
		// TODO Auto-generated method stub
		return mapper.updateStatus(articleId,status);
	}

	/**
	 *  修改热门
	 * @param articleId
	 * @param status
	 * @return
	 */
	@Override
	public int updateHot(Integer articleId, int status) {
		// TODO Auto-generated method stub
		return mapper.updateHot(articleId,status);
	}

	/**
	 * 增加文章点击次数
	 * @param request
	 * @return
	 */
	@Override
	public int addHits(Integer id) {
		// TODO Auto-generated method stub
		return mapper.increaseHits(id);
	}
	
}
