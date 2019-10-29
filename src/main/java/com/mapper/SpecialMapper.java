package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.Special;

/**
 * @作者：lzy
 * @时间：2019年10月26日
 */
public interface SpecialMapper {

	/**
	 * 专题列表
	 * @return
	 */
	@Select("select id,title,abstract as digest,created from cms_special order by id desc")
	List<Special> list();

	/**
	 * 添加专题
	 * @param special
	 * @return
	 */
	@Insert("insert into cms_special (title,abstract,created) values (#{title},#{digest},now())")
	int add(Special special);

	/**
	 * 查看专题详情
	 * @param id
	 * @return
	 */
	@Select("select id,title,abstract as digest,created from cms_special where id = #{value}")
	Special findById(Integer id);

	/**
	 * 修改专题
	 * @param special
	 * @return
	 */
	@Update("update cms_special set title = #{title},abstract = #{digest} where id = #{id}")
	int update(Special special);

	/**
	 * 向专题中添加文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	@Insert("insert into cms_special_article (sid,aid) values (#{sid},#{aid})")
	int addArticle(@Param("sid")Integer specialId, @Param("aid")Integer articleId);

	/**
	 * 移除文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	@Delete("delete from cms_special_article where sid = #{sid} and aid = #{aid}")
	int removeArticle(@Param("sid")Integer specialId, @Param("aid")Integer articleId);

}
