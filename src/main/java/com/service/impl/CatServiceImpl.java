package com.service.impl;

import java.util.List;

import com.pojo.Cat;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
public interface CatServiceImpl {

	List<Cat> getListByChnlId(Integer chnId);

}
