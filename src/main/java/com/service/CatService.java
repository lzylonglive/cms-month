package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.CatMapper;
import com.pojo.Cat;
import com.service.impl.CatServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
@Service
public class CatService implements CatServiceImpl {

	@Autowired
	CatMapper mapper;

	/**
	 * 根据频道去获取下边的分类
	 * @param id
	 * @return
	 */
	@Override
	public List<Cat> getListByChnlId(Integer chnId) {
		// TODO Auto-generated method stub
		return mapper.selectByChnlId(chnId);
	}
	
	
}
