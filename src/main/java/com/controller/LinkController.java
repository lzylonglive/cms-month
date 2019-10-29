package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comon.ResultMsg;
import com.pojo.Links;
import com.service.LinkService;
import com.service.impl.LinkServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月29日
 */
@Controller
@RequestMapping("links")
public class LinkController {

	@Autowired
	LinkServiceImpl impl;
	
	/**
	 * 	管理友情链接
	 * @return
	 */
	@RequestMapping("friendLink")
	public String friendLink(HttpServletRequest request) {
		List<Links> list = impl.list();
		request.setAttribute("list", list);
		return "admin/links/list";
	}
	
	/**
     * 进入添加页面
     * @return
     */
    @GetMapping("add")
    public String insert(){
       return "admin/links/add";
    }
	
    /**
     * 添加链接
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultMsg insert(Links links){
        int result = impl.add(links);
        if (result > 0){
            return  new ResultMsg(1,"添加成功",null);
        }else{
            return  new ResultMsg(2,"添加失败",null);
        }

    }
    
    /**
     * 删除链接
     * @return
     */
    @RequestMapping("remove")
    public String remove(Integer id){
        impl.delete(id);
        return "redirect:friendLink";
    }
    
}
