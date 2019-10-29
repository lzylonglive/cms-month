package com.liangzhanyong.cms;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pojo.Comment;
import com.service.impl.ArticleServiceImpl;
import com.service.impl.CommentServiceImpl;
import com.utils.StringUtil;

/**
 * @作者：lzy
 * @时间：2019年10月28日
 */
public class TestComment extends TestBase {

	@Autowired
	CommentServiceImpl commentimpl;
	
	
	@Test
	public void testComment() {
		
		int articleIds[]= {57,58,28,23,59,69,33,45,28,48};
		
		Random random = new Random();
		
		Calendar calendar = Calendar.getInstance();
		
		Date time = calendar.getTime();
		
		for (int i = 0; i < 1000; i++) {
			
			calendar.set(Calendar.YEAR,2019);
			calendar.set(Calendar.MONTH,random.nextInt(12));
			calendar.set(Calendar.DAY_OF_MONTH,random.nextInt(32));
			calendar.set(Calendar.HOUR,random.nextInt(24));
			calendar.set(Calendar.MINUTE,random.nextInt(60));
			calendar.set(Calendar.SECOND,random.nextInt(60));
			Date time2 = calendar.getTime();
			
//			Comment comment = new Comment();
			commentimpl.addComment(StringUtil.randomChar(120), 46, articleIds[random.nextInt(10)]);
		}
	}
	
}
