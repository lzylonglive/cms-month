package com.liangzhanyong.cms;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mapper.ArticleMapper;
import com.pojo.Article;
import com.pojo.Term;
import com.service.impl.ArticleServiceImpl;
import com.utils.FileUtil;
import com.utils.StringUtil;

/**
 * @作者：lzy
 * @时间：2019年10月29日
 */
public class TestIo {

	@Autowired
	ArticleServiceImpl impl;
	
	@Autowired
	ArticleMapper mapper;
	
	//读取文章存入数据库
	@Test
	public void testImport() throws Exception {
		File file = new File("");//文件
		//获取子目录
		String[] list = file.list();
		for (int i = 0; i < list.length; i++) {
			if(list[i].endsWith(".txt")) {
//				File txtFile = new File("" + list[i]);
				String content = FileUtil.readFileByLine("" + list[i]);
				Article article = new Article();
				article.setContent(content);
				article.setTitle(list[i].substring(0,list[i].lastIndexOf('.')));
				article.setChannelId(4);
				article.setCategoryId(20);
				impl.add(article);
			}
		}
	}
	
	//读取标签存入数据库
	@Test
	public void testImportTerm() throws Exception {
		List<String> list = FileUtil.readFile("");//文件
		for (String string : list) {
			String uniqueTerm = StringUtil.toUniqueTerm(string);
			Term term = new Term(string, uniqueTerm);
			mapper.addTerm(term);
		}
	}
	
}
