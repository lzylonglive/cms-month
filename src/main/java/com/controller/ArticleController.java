package com.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comon.ArticleType;
import com.comon.CmsAssertJson;
import com.comon.ConstClass;
import com.comon.ResultMsg;
import com.google.gson.Gson;
import com.pojo.Article;
import com.pojo.Cat;
import com.pojo.Channel;
import com.pojo.ImageBean;
import com.pojo.User;
import com.service.impl.ArticleServiceImpl;
import com.service.impl.CatServiceImpl;
import com.service.impl.ChannelServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月18日
 */
@Controller
@RequestMapping("article")
public class ArticleController {
	
	@Autowired
	ArticleServiceImpl artimpl;
	
	@Autowired
	CatServiceImpl catimpl;
	
	@Autowired
	ChannelServiceImpl chnimpl;
	
	/**
	 *  显示一篇具体的文章
	 * @param id  文章的id
	 * @return
	 */
	@RequestMapping("show")
	public String show(HttpServletRequest request,Integer id) {
		CmsAssertJson.Assert(id!=0,"文章id不能等于0");
		/**
		 * 增加文章点击次数
		 * @param request
		 * @return
		 */
		artimpl.addHits(id);
		Article article = artimpl.findById(id);
		
		if(article.getArticleType() == ArticleType.HTML) {
			request.setAttribute("article", article);
			return "article/detail";
		}else {
			Gson gson = new Gson();
			article.setImgList(gson.fromJson(article.getContent(), List.class));
			request.setAttribute("article", article);
			return "article/slieimgarticle";
		}
		
	}
	
	/**
	 * 跳转到添加的页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String show(HttpServletRequest request) {
		List<Channel> allChnls = chnimpl.getAllChnls();
		request.setAttribute("channels", allChnls);
		return "article/publish";
	}
	
	//发布文章
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(HttpServletRequest request,Article article,MultipartFile file) throws Exception {
		
		processFile(file, article);
		
		//获取作者
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		
		article.setUserId(loginUser.getId());
		
		artimpl.add(article);
		
		return "article/publish";
	}
	//跳转到发布图片文章页面
	@RequestMapping(value = "addimg",method=RequestMethod.GET)
	public String addimg(HttpServletRequest request) {
		List<Channel> allChnls = chnimpl.getAllChnls();
		request.setAttribute("channels", allChnls);
		return "article/publishimg";
	}

	//发布图片文章
	@RequestMapping(value="addimg",method=RequestMethod.POST)
	public String addimg(HttpServletRequest request,Article article,@RequestParam("file")MultipartFile file,@RequestParam("imgs")MultipartFile[] imgs,@RequestParam("imgsdesc")String[] imgsdesc) throws Exception {
		article.setArticleType(ArticleType.IMAGE);
		
		processFile(file, article);
		
		ArrayList<ImageBean> imgBeans = new ArrayList<ImageBean>();
		
		for (int i = 0; i < imgs.length; i++) {
			String picUrl = processFile(imgs[i]);
			if(!"".equals(picUrl)) {
				ImageBean imageBean = new ImageBean(imgsdesc[i], picUrl);
				imgBeans.add(imageBean);
			}
		}
		
		Gson gson = new Gson();
		
		String json = gson.toJson(imgBeans);//文章的内容
		
		article.setContent(json);
		
		//获取作者
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		
		article.setUserId(loginUser.getId());
		
		artimpl.add(article);
		
		return "article/publish";
	}
	
	/**
	 * 跳转到修改的页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(HttpServletRequest request,Integer id) {
		List<Channel> allChnls = chnimpl.getAllChnls();
		
		Article article = artimpl.findById(id);
		
		request.setAttribute("article", article);
		request.setAttribute("content1", article.getContent());
		request.setAttribute("channels", allChnls);
		
		return "my/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public boolean update(HttpServletRequest request,Article article,MultipartFile file) throws Exception {
		processFile(file, article);
		
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		article.setUserId(loginUser.getId());
		
		int result = artimpl.update(article);
		
		return result > 0;
	}
	
	/**
	 * 根据频道获取相应的分类  用户发布文章或者修改文章的下拉框
	 * @param chnlId 频道id
	 * @retur
	 */
	@RequestMapping(value="listCatByChnl",method=RequestMethod.GET)
	@ResponseBody
	public ResultMsg getCatByChnl(int chnlId) {
		CmsAssertJson.Assert(chnlId > 0,"频道id必须大于0");
		List<Cat> chnlList = catimpl.getListByChnlId(chnlId);
		return new ResultMsg(1, "获取数据成功", chnlList);
	}
	
	/**
	 * 处理文章的附件上传
	 * @param file
	 * @param article
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	private void processFile(MultipartFile file,Article article) throws Exception {
		// 原来的文件名称
		
		if(file.isEmpty() || "".equals(file.getOriginalFilename()) || file.getOriginalFilename().lastIndexOf(".") < 0) {
			article.setPicture("");
			return;
		}
		
		String originName = file.getOriginalFilename();
		String suffixName = originName.substring(originName.lastIndexOf('.'));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String path = "D:/Pic/" + sdf.format(new Date());
		File pathFile = new File(path);
		if(!pathFile.exists()) {
			pathFile.mkdir();
		}
		
		String destFileName = path + "/" + UUID.randomUUID().toString() + suffixName;

		File destFile = new File(destFileName);
				
		file.transferTo(destFile);//文件另存到这个目录下边
				
		article.setPicture(destFileName.substring(7));
	}
	
	/**
	 * 处理每一个图片集合中的文件
	 * @param file
	 * @param article
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String processFile(MultipartFile file) throws Exception {
		// 原来的文件名称
		if(file.isEmpty() || "".equals(file.getOriginalFilename()) || file.getOriginalFilename().lastIndexOf('.') < 0) {
			return "";
		}
		
		String originalFilename = file.getOriginalFilename();
		String suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String path = "D:/Pic/" + sdf.format(new Date());
		
		File pathFile = new File(path);
		
		if(!pathFile.exists()) {
			pathFile.mkdir();
		}
		
		String destFileName = path + "/" + UUID.randomUUID().toString() + suffixName;
		
		File distFile = new File(destFileName);
		
		file.transferTo(distFile);//文件另存到这个目录下边
		
		return destFileName.substring(7);
	}
}
