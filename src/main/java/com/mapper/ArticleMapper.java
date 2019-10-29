package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.Article;
import com.pojo.Term;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
public interface ArticleMapper {

	/**
	 * 频道id必须大于0，分类id可以为0，当分类id为0的时候，查询该频道下的所有分类的文章
	 * 否在查询该分类下的文章
	 * @param chnId  频道id
	 * @param catId  分类id
	 * @return
	 */
	List list(@Param("chnId")Integer chnId, @Param("catId")Integer catId);

	/**
	 * 获取热门文章
	 * @return
	 */
	List<Article> listHot();

	/**
	 * 获取最新文章
	 * @param sum
	 * @return
	 */
	List<Article> listLast(int sum);

	Article findById(Integer articleId);

	/**
	 * 添加文章
	 * @param article
	 * @return
	 */
	int add(Article article);

	// 用户查询文章列表
	List<Article> listByUserId(Integer id);

	/**
	 * 根据文章id删除文章
	 * @param id
	 * @return
	 */
	@Update("update cms_article set deleted = 1 where id = #{value}")
	int deleteById(Integer id);
	
	@Update("update cms_article set title=#{title},content=#{content},picture=#{picture},channel_id=#{channelId},category_id=#{categoryId},updated=now() WHERE id=#{id}")
	int update(Article article);

	/**
	 * 获取需要管理的文章
	 * @return
	 */
	List<Article> listAdmin(@Param("status")Integer status);

	/**
	 * 修改文章状态
	 * @param articleId
	 * @param status
	 * @return
	 */
	@Update("update cms_article set status = #{status},updated = now() where id = #{articleId}")
	int updateStatus(@Param("articleId")Integer articleId, @Param("status")int status);

	/**
	 * 修改文章热门状态
	 * @param articleId
	 * @param status
	 * @return
	 */
	@Update("update cms_article set hot = #{status},updated = now() where id = #{articleId}")
	int updateHot(@Param("articleId")Integer articleId, @Param("status")int status);

	/**
	 * 根据标签名称获取标签对象
	 * @param tag
	 * @return
	 */
	@Select("select * from cms_term where unique_name = #{value} limit 1")
	Term findTermByName(String term);

	/**
	 * 增加Term实体备案
	 * @param termBean
	 * @return
	 */
	int addTerm(Term termBean);

	/**
	 * 增加数据到文章标签中间表
	 * @param articleId 
	 * @param termId  
	 */
	@Insert("insert into cms_article_term_middle (aid,tid) values (#{articleId},#{termId})")
	void addArticleTerm(@Param("articleId")Integer articleId, @Param("termId")Integer termId);

	/**
	 *  删除中间表中的内容
	 * @param articleId
	 */
	@Delete("delete from cms_article_term_middle where aid = #{value}")
	int delTermsByArticleId(Integer articleId);
	
	/**
	 * 根据主题id获取文章列表
	 * @param id
	 * @return
	 */
	@Select("select a.id,a.title,a.created from cms_special_article sa join cms_article a on sa.aid = a.id where sa.sid = #{value}")
	List<Article> findByIdSpecialId(Integer id);

	/**
	 * 专题中文章数量
	 */
	@Select("select count(1) from cms_special_article sa join cms_article a on sa.aid = a.id where sa.sid = #{value}")
	Integer getArticleNum(Integer id);

	/**
	 * 增加文章点击次数
	 * @param request
	 * @return
	 */
	@Update("update cms_article set Hits = hits + 1 where id = #{id}")
	int increaseHits(Integer id);

}
