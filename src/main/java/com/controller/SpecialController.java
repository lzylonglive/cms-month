package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comon.ResultMsg;
import com.pojo.Special;
import com.service.impl.SpecialServiceImpl;
import com.utils.StringUtil;

/**
 * @作者：lzy
 * @时间：2019年10月26日
 */
@Controller
@RequestMapping("special")
public class SpecialController {

	@Autowired
	SpecialServiceImpl impl;
	
	/**
	 * 获取专题列表
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request) {
		List<Special> specialList = impl.list();
		request.setAttribute("specialList", specialList);
		return "admin/special/list";
	}
	
	/**
	 * 跳转到添加专题页面
	 */
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(HttpServletRequest request) {
		return "admin/special/add";
	}
	
	/**
	 * 添加专题
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg add(HttpServletRequest request,Special special) {
		if(StringUtil.isEmpty(special.getDigest())) {
			return new ResultMsg(0, "摘要不能为空", null);
		}
		int result = impl.add(special);
		if(result > 0) {
			return new ResultMsg(1, "添加成功", null);
		}else {
			return new ResultMsg(2, "添加失败,请与管理员联系", null);
		}
	}
	
	/**
	 * 跳转到修改页面  以及回显
	 */
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(HttpServletRequest request,Integer id) {
		Special special = impl.findById(id);
		request.setAttribute("special", special);
		return "admin/special/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg update(HttpServletRequest request,Special special) {
		int result = impl.update(special);
		if(result > 0) {
			return new ResultMsg(1, "修改成功", null);
		}else {
			return new ResultMsg(2, "修改失败,请与管理员联系", null);
		}
	}
	
	/**
	 * 向专题中添加文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="addArticle",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg addArticle(HttpServletRequest request,Integer specialId,Integer articleId) {
		int result = impl.addArticle(specialId,articleId);
		if(result > 0) {
			return new ResultMsg(1, "添加成功", null);
		}else {
			return new ResultMsg(2, "添加失败,请与管理员联系", null);
		}
	}
	
	/**
	 * 移除文章
	 * @param specId
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="removeArticle",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg removeArticle(HttpServletRequest request,Integer specialId,Integer articleId) {
		int result = impl.removeArticle(specialId,articleId);
		if(result > 0) {
			return new ResultMsg(1, "移除成功", null);
		}else {
			return new ResultMsg(2, "移除失败,请与管理员联系", null);
		}
	}
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,Integer id) {
		Special special = impl.findById(id);
		request.setAttribute("special", special);
		return "admin/special/detail";
	}
	
}
