package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.pojo.Channel;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
public interface ChannelMapper {

	/**
	 * 获取所有的频道
	 */
	@Select("select * from cms_channel order by id")
	List<Channel> listAll();

	/**
	 *  根据id获取对应的频道
	 * @param id
	 * @return
	 */
	Channel findById(Integer id);
}
