package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.pojo.Comment;

/**
 * @作者：lzy
 * @时间：2019年10月24日
 */
public interface CommentMapper {

	// 获取文章列表
	List<Comment> getCommentList(Integer articleId);

	// 发表评论(评论数量使用触发器增加)
	@Insert("insert into cms_comment (articleId,userId,content,created) values (#{articleId},#{userId},#{content},now())")
	int addComment(@Param("content")String content, @Param("articleId")Integer articleId, @Param("userId")Integer userId);

	// 删除评论(评论数量使用触发器删除)
	@Delete("delete from cms_comment where id = #{value}")
	int delComment(Integer comId);

}
