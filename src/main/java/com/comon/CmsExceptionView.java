package com.comon;

/**
 * @作者：lzy
 * @时间：2019年10月25日
 */
public class CmsExceptionView extends RuntimeException {

	private static final long serialVersionUID = 4416410287235387858L;

	public CmsExceptionView(String msg) {
		super(msg);
	}
	
}
