package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.interceptor.PageUtils;
import com.pojo.Article;
import com.pojo.Cat;
import com.pojo.Channel;
import com.pojo.Links;
import com.pojo.Special;
import com.service.impl.ArticleServiceImpl;
import com.service.impl.CatServiceImpl;
import com.service.impl.ChannelServiceImpl;
import com.service.impl.LinkServiceImpl;
import com.service.impl.SpecialServiceImpl;
/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
@Controller
public class IndexController {

	@Autowired
	ArticleServiceImpl artimpl;
	
	@Autowired
	CatServiceImpl catimpl;
	
	@Autowired
	ChannelServiceImpl chnimpl;
	
	@Autowired
	SpecialServiceImpl speimpl;
	
	@Autowired
	LinkServiceImpl linkimpl;
	
	@RequestMapping({"index","/"})
	public String index(HttpServletRequest request,@RequestParam(defaultValue="0")Integer chnId,@RequestParam(defaultValue="0")Integer catId,@RequestParam(defaultValue="1")Integer page) throws Exception {
//		List<Channel> channels = chnimpl.getAllChnls();
//		if(chnId != 0) {
//			//获取该栏目下的所有分类
//			List<Cat> catygories = catimpl.getListByChnlId(chnId);
//			request.setAttribute("catygories", catygories);
//			//获取该栏目下的文章
//			PageInfo<Article> articlelist = artimpl.list(chnId,catId,page);
//			request.setAttribute("articles", articlelist);
//			PageUtils.page(request, "/index?chnId="+chnId+"&catId="+catId, 10, articlelist.getList(), (long) articlelist.getTotal(), articlelist.getPageNum());
//		}else {
//			//首页热门
//			//获取热门文章
//			PageInfo<Article> articleList = artimpl.hotList(page);
//			request.setAttribute("articles", articleList);
//			PageUtils.page(request, "/index", 10, articleList.getList(), (long) articleList.getTotal(), articleList.getPageNum());
//		}
//		
//		List<Article> lastList = artimpl.last(5);
//		request.setAttribute("lastList", lastList);
//		
//		request.setAttribute("chnls", channels);
//		request.setAttribute("chnId", chnId);
//		request.setAttribute("catId", catId);
		
		Thread t1 = new Thread() {
			public void run() {
				List<Channel> allChnls = chnimpl.getAllChnls();
				request.setAttribute("chnls", allChnls);
			}
		};
		
		// 获取所有的频道
		Thread t2;
		if(chnId != 0) {
			//获取该栏目下的所有分类
			t2 = new Thread() {
				public void run() {
					List<Cat> catygories = catimpl.getListByChnlId(chnId);
					request.setAttribute("catygories", catygories);
					
					//获取该栏目下的文章
					PageInfo<Article> articleList = artimpl.list(chnId, catId, page);
					request.setAttribute("articles", articleList);
					PageUtils.page(request, "/index?chnId="+chnId+"&catId="+catId, 10, articleList.getList(), (long) articleList.getTotal(), articleList.getPageNum());
				}
			};
		}else {
			// 首页热门
			// 获取热门文章
			t2 = new Thread() {
				public void run() {
					PageInfo<Article> articleList = artimpl.hotList(page);
					request.setAttribute("articles", articleList);
					PageUtils.page(request, "/index", 10, articleList.getList(), (long) articleList.getTotal(), articleList.getPageNum());
				}
			};
		}
		
		Thread t3 = new Thread() {
			public void run() {
				//获取最新文章
				System.out.println("=====线程已开始=====");
				List<Article> lastList = artimpl.last(5);
				request.setAttribute("lastList", lastList);
				System.out.println("=====线程即将结束=====");
			}
		};
		
		ArrayList<Special> specials = new ArrayList<Special>();
		
		List<Special> list = speimpl.list();
		// 获取专题文章
		for (Special special : list) {
			Special newSpecial = speimpl.findById(special.getId());
			specials.add(newSpecial);
		}
		
		//获取友情链接
		List<Links> links = linkimpl.list();
		
		t1.start();
		t2.start();
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		
		System.out.println("=====主线程即将结束=====");
		
		request.setAttribute("chnId", chnId);
		request.setAttribute("catId", catId);
		
		request.setAttribute("specials", specials);
		request.setAttribute("links", links);
		
		return "index";
	}
	
}
