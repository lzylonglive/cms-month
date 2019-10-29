package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.ChannelMapper;
import com.pojo.Channel;
import com.service.impl.ChannelServiceImpl;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
@Service
public class ChannelService implements ChannelServiceImpl {

	@Autowired
	ChannelMapper mapper;

	/**
	 *  获取所有的频道（栏目）
	 * @return
	 */
	@Override
	public List<Channel> getAllChnls() {
		// TODO Auto-generated method stub
		return mapper.listAll();
	}
	
}
