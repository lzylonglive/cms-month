package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comon.ConstClass;
import com.comon.ResultMsg;
import com.github.pagehelper.PageInfo;
import com.interceptor.PageUtils;
import com.pojo.Article;
import com.pojo.User;
import com.service.impl.AdminServiceImpl;
import com.service.impl.ArticleServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月22日
 */
@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	ArticleServiceImpl artimpl;
	
	@Autowired
	AdminServiceImpl admimpl;
	
	@RequestMapping("index")
	public String index() {
		return "admin/index";
	}
	
	@RequestMapping("manArticle")
	public String adminArticle(HttpServletRequest request,@RequestParam(defaultValue="1")Integer page,@RequestParam(defaultValue="0")Integer status) {
		PageInfo<Article> pageInfo = artimpl.getAdminArticles(page,status);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("status", status);
		
		String pageStr = PageUtils.pageLoad(pageInfo.getPageNum(), pageInfo.getPages(), "/admin/manArticle?status="+status, 10);
		request.setAttribute("page", pageStr);
		return "admin/article/list";
	}
	/**
	 * 
	 */
	@RequestMapping("getArticle")
	public String getArticle(HttpServletRequest request,Integer id) {
		Article article = artimpl.findById(id);
		request.setAttribute("article", article);
		return "admin/article/detail";
	}
	
	/**
	 * 审核文章
	 * @param request
	 * @param articleId  文章的id
	 * @param status  审核后的状态  1 审核通过  2 不通过
	 * @return
	 */
	@RequestMapping("checkArticle")
	@ResponseBody
	public ResultMsg checkArticle(HttpServletRequest request,Integer articleId,int status) {
		User login = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		
		if(login == null) {
			return new ResultMsg(2, "请先登录！！！", null);
		}
		
		if(login.getRole() != ConstClass.USER_ROLE_ADMIN) {
			return new ResultMsg(3, "对不起,您没有权限审核文章", null);
		}
		
		Article article = artimpl.findById(articleId);
		
		if(article == null) {
			return new ResultMsg(4, "抱歉,不存在这边文章", null);
		}
		
		if(article.getStatus() == status) {
			return new ResultMsg(5, "操作重复,无需此操作！", null);
		}
		
		int result = artimpl.updateStatus(articleId,status);
		
		if(result > 0) {
			return new ResultMsg(1, "审核通过！", null);
		}else {
			return new ResultMsg(6, "操作失败,请与管理员联系或稍后再试", null);
		}
	}
	
	/**
	 * 设置热门
	 * @param request
	 * @param articleId  文章的id
	 * @param status  热门状态  1 审核通过  2 不通过
	 * @return
	 */
	@RequestMapping("sethot")
	@ResponseBody
	public ResultMsg setHot(HttpServletRequest request,Integer articleId,int status) {
		User login = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		
		if(login == null) {
			return new ResultMsg(2, "请先登录！！！", null);
		}
		
		if(login.getRole() != ConstClass.USER_ROLE_ADMIN) {
			return new ResultMsg(3, "对不起，您没有权限修改文章热门状态", null);
		}
		
		Article article = artimpl.findById(articleId);
		
		if(article == null) {
			return new ResultMsg(4, "抱歉,不存在这边文章", null);
		}
		
		if(article.getHot() == status) {
			return new ResultMsg(5, "操作重复,无需此操作！", null);
		}
		
		int result = artimpl.updateHot(articleId,status);
		
		if(result > 0) {
			return new ResultMsg(1, "操作成功！", null);
		}else {
			return new ResultMsg(6, "操作失败,请与管理员联系或稍后再试", null);
		}
		
	}
	
	/**
	 * 	管理员管理用户
	 * @param model
	 * @param pageNum     分页页码
	 * @param name        模糊查询名称
	 * @return
	 */
	@RequestMapping("userlist")
	public String userList(Model model,@RequestParam(defaultValue="1")Integer page,@RequestParam(defaultValue="")String name) {
		PageInfo<User> userList = admimpl.userList(page,name);
		String pageStr = PageUtils.pageLoad(userList.getPageNum(), userList.getPages(), "/admin/userlist?name="+name, 10);
		model.addAttribute("name", name);
		model.addAttribute("userList", userList);
		model.addAttribute("pageUtil", pageStr);
		return "admin/user/list";
	}
	
	/**
	 * 	修改用户状态
	 * @param id
	 * @param locked
	 * @return
	 */
	@RequestMapping("modifyUserStatus")
	@ResponseBody
	public boolean modifyUserStatus(Integer id,Integer locked) {
		int result = admimpl.modifyUserStatus(id,locked);
		return result > 0;
	}

}
