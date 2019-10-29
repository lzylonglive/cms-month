package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comon.ConstClass;
import com.comon.ResultMsg;
import com.github.pagehelper.PageInfo;
import com.interceptor.PageUtils;
import com.pojo.Comment;
import com.pojo.User;
import com.service.impl.CommentServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月24日
 */
@Controller
@RequestMapping("comment")
public class CommentController {

	@Autowired
	CommentServiceImpl impl;
	
	/**
	 * 获取评论
	 * @param articleId
	 * @return
	 */
	@RequestMapping("getCommentList")
	public String getCommentList(Model model,Integer articleId,@RequestParam(defaultValue="1")Integer page) {
		PageInfo<Comment> commentPage = impl.getCommentList(articleId,page);
		String pageStr = PageUtils.pageLoad(commentPage.getPageNum(), commentPage.getPages(), "/comment/getCommentList?articleId="+articleId, 10);
		model.addAttribute("commentPage", commentPage);
		model.addAttribute("pageUtil", pageStr);
		return "my/comment/list";
	}
	
	/**
	 * 用户评论
	 * @param request
	 * @param content       评论内容
	 * @param articleId     文章ID
	 * @return
	 */
	@RequestMapping("addComment")
	@ResponseBody
	public ResultMsg addComment(HttpServletRequest request,String content,Integer articleId) {
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		if(loginUser == null) {
			return new ResultMsg(-1, "尚未登陆,不能评论", null);
		}
		
		int res = impl.addComment(content,articleId,loginUser.getId());
		
		if(res > 0) {
			return new ResultMsg(1, "发表成功！", null);
		}else {
			return new ResultMsg(0, "发表失败！", null);
		}
		
	}
	
	/**
	 * 	删除评论
	 * @param request
	 * @param comId          评论ID
	 * @return
	 */
	@RequestMapping("delComment")
	@ResponseBody
	public ResultMsg delComment(HttpServletRequest request,Integer comId) {
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		
		if(loginUser == null) {
			return new ResultMsg(0, "尚未登陆,不能删除评论", null);
		}
		
		int result = impl.delComment(comId);
		
		if(result > 0) {
			return new ResultMsg(1, "删除成功!", null);
		}else {
			return new ResultMsg(2, "删除失败!", null);
		}
		
	}
	
}
