package com.comon;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @作者：lzy
 * @时间：2019年10月25日
 */
@ControllerAdvice
public class CmsControllerAdvice {

	@ResponseBody
	@ExceptionHandler(value = CmsExceptionJson.class)
	//使用@ExceptionHandler修饰后会作用在所有的@RequestMapping上。
	public ResultMsg myErrorHandler(CmsExceptionJson ce) {
		return new ResultMsg(ce.hashCode(), ce.getMessage(), "");
	}
	
	@ExceptionHandler(value = CmsExceptionView.class)
	public ModelAndView myErrorHandler(CmsExceptionView ce) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("code",ce.hashCode());
		modelAndView.addObject("msg", ce.getMessage());
		return modelAndView;
	}
	
}
