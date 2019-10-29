package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.CommentMapper;
import com.pojo.Comment;
import com.service.impl.CommentServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月24日
 */
@Service
public class CommentService implements CommentServiceImpl {

	@Autowired
	CommentMapper mapper;

	// 获取评论列表(文章ID)
	@Override
	public PageInfo<Comment> getCommentList(Integer articleId, Integer pageNum) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, 10);
		List<Comment> list = mapper.getCommentList(articleId);
		return new PageInfo<Comment>(list);
	}

	// 发表评论
	@Override
	public int addComment(String content, Integer articleId, Integer userId) {
		// TODO Auto-generated method stub
		int res = mapper.addComment(content,articleId,userId);
		return res;
	}

	// 删除评论
	@Override
	public int delComment(Integer comId) {
		// TODO Auto-generated method stub
		int result = mapper.delComment(comId);
		return result;
	}
	
}
