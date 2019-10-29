package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comon.CMSRuntimeException;
import com.mapper.LinkMapper;
import com.pojo.Links;
import com.service.LinkService;
import com.utils.StringUtil;

/**
 * @作者：lzy
 * @时间：2019年10月28日
 */
public interface LinkServiceImpl {

	/**
	 * 友情链接查询
	 */
	List<Links> list();

	/**
     * 添加链接
     * @return
     */
	int add(Links links);

	/**
     * 删除链接
     * @return
     */
	void delete(Integer id);

}
