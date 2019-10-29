package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.pojo.Links;

/**
 * @作者：lzy
 * @时间：2019年10月29日
 */
public interface LinkMapper {

	/**
	 * 友情链接查询
	 */
	@Select("select * from cms_links order by created desc")
	List<Links> list();

	/**
     * 添加链接
     * @return
     */
	@Insert("insert into cms_links (title,url,created) values (#{title},#{url},now())")
	int add(Links links);

	/**
     * 删除链接
     * @return
     */
	@Delete("delete from cms_links where id = #{value}")
	void delete(Integer id);

}
