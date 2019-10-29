package com.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comon.ConstClass;
import com.github.pagehelper.PageInfo;
import com.interceptor.PageUtils;
import com.pojo.Article;
import com.pojo.User;
import com.service.impl.ArticleServiceImpl;
import com.service.impl.UserServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月16日
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserServiceImpl useimpl;
	
	@Autowired
	ArticleServiceImpl artimpl;
	
	@GetMapping("register")
	public String register() {
		return "user/register";
	}
	
	@RequestMapping("index")
	public String index() {
		return "user/index";
	}
	
	/**
	 * 判断用户名是否已经被占用
	 */
	@RequestMapping("checkExist")
	@ResponseBody
	public boolean checkExist(String username) {
		return ! useimpl.checkUserExist(username);
	}
	
	@PostMapping("register")
	public String register(HttpServletRequest request,@Validated User user,BindingResult result) {
		if(result.hasErrors()) {
			return "user/register";
		}
		
		int results = useimpl.register(user);
		
		if(results > 0) {
			return "redirect:login";
		}else {
			request.setAttribute("errorMsg", "系统错误,请稍后重试");
			return "user/register";
		}
	}
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping("loginout")
	public String loginout(HttpServletRequest request) {
		request.getSession().removeAttribute(ConstClass.SESSION_USER_KEY);
		return "user/login";
	}
	
	@PostMapping("login")
	public String login(HttpServletRequest request,@Validated User user,BindingResult result) {
		if(result.hasErrors()) {
			return "login";
		}
		
		User loginUser = useimpl.login(user);
		
		if(loginUser == null) {
			request.setAttribute("errorMsg", "用户名或密码错误");
			return "user/login";
		}else {
			//判断用户是否被禁用
			if(loginUser.getLocked() == 0) {
				request.getSession().setAttribute(ConstClass.SESSION_USER_KEY, loginUser);
				
				if(loginUser.getRole() == ConstClass.USER_ROLE_GENERAL) {
					return "redirect:home";
				}else if(loginUser.getRole() == ConstClass.USER_ROLE_ADMIN) {
					return "redirect:../admin/index";
				}else {
					//其他情况
					return "user/login";
				}
			}else {
				request.setAttribute("errorMsg", "该账户已被冻结");
				return "user/login";
			}
		}
		
	}
	
	/**
	 * 进入个人中心(普通注册用户)
	 * @param request
	 * @return
	 */
	@RequestMapping("home")
	public String home(HttpServletRequest request) {
		return "/my/home";
	}
	
	/**
	 * 删除用户自己的文章
	 * @param id 文章id
	 * @return
	 */
	@RequestMapping("delArticle")
	@ResponseBody
	public boolean delArticle(Integer id) {
		return artimpl.remove(id) > 0;
	}
	
	/**
	 * 进入个人中心 获取我的文章
	 * @param request
	 * @return
	 */
	@RequestMapping("myarticlelist")
	public String myarticles(HttpServletRequest request,@RequestParam(defaultValue="1") Integer page) {
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		
		PageInfo<Article> pageArticle = artimpl.listArticleByUserId(loginUser.getId(),page);
		
		String pageStr = PageUtils.pageLoad(pageArticle.getPageNum(), pageArticle.getPages(), "/user/myarticlelist", 5);
		
		request.setAttribute("pageStr", pageStr);
		
		request.setAttribute("pageArticle", pageArticle);
		
		return "/my/list";
	}
	
	/**
	 * 跳转到上传头像页面
	 */
	@RequestMapping("photo")
	public String photo() {
		return "/my/photo";
	}
	
	/**
	 * 上传头像
	 * @throws Exception 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="photo",method=RequestMethod.POST)
	public String getphoto(HttpServletRequest request,MultipartFile file) throws Exception {
		User user = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		processFile(file, user);
		int result = useimpl.updatePhoto(user);
		return "redirect:home";
	}
	
	/**
	 * 处理文章的附件上传
	 * @param file
	 * @param article
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	private void processFile(MultipartFile file,User user) throws Exception {
		// 原来的文件名称
		if(file.isEmpty() || "".equals(file.getOriginalFilename()) || file.getOriginalFilename().lastIndexOf(".") < 0) {
			user.setPhoto("");
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
				
		user.setPhoto(destFileName.substring(7));
	}
	
}
