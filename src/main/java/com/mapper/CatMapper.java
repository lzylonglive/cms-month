package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.pojo.Cat;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
public interface CatMapper {

	/**
	 * 
	 * @param chnlId 频道主键id
	 * @return
	 */
	@Select("select id,name,channel_id channelId from cms_category where channel_id = #{value}")
	List<Cat> selectByChnlId(Integer chnId);

	/**
	 *  根据id获取对应的分类
	 * @param id
	 * @return
	 */
	Cat findById(Integer id);
}
