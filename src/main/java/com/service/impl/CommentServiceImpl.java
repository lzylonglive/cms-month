package com.service.impl;

import com.github.pagehelper.PageInfo;
import com.pojo.Comment;

/**
 * @作者：lzy
 * @时间：2019年10月24日
 */
public interface CommentServiceImpl {

	// 获取评论列表(文章ID, 页码)
	PageInfo<Comment> getCommentList(Integer articleId, Integer pageNum);

	/**
	 * 发表评论
	 * @param content        评论内容
	 * @param articleId      文章ID
	 * @param userId         用户ID
	 * @return
	 */
	int addComment(String content, Integer articleId, Integer id);

	/**
	 * 	删除评论
	 * @param comId          评论ID
	 * @return
	 */
	int delComment(Integer comId);

}
