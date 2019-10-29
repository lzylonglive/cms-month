package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.LinkMapper;
import com.pojo.Links;
import com.service.impl.LinkServiceImpl;
import com.utils.StringUtil;

/**
 * @作者：lzy
 * @时间：2019年10月28日
 */
@Service
public class LinkService implements LinkServiceImpl {

	@Autowired
	LinkMapper mapper;

	/**
	 * 友情链接查询
	 */
	@Override
	public List<Links> list() {
		// TODO Auto-generated method stub
		return mapper.list();
	}

	/**
     * 添加链接
     * @return
     */
	@Override
	public int add(Links links) {
		// TODO Auto-generated method stub 
		if(StringUtil.isUrl(links.getUrl())) {
			 List<Links> list = mapper.list();
			 for (Links link : list) {
				if(links.getUrl()==links.getUrl()) {
					return mapper.add(links);
				}
			}
		}
		return 0;
	}

	/**
     * 删除链接
     * @return
     */
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		mapper.delete(id);
	}
	
}
