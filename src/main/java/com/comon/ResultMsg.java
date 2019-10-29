package com.comon;

/**
 * 用户前后端交互数据的结构体
 * 
 * @作者：lzy @时间：2019年10月22日
 */
public class ResultMsg {

	private int result;//处理的结果
	private String errorMsg;//错误消息
	private Object data;//返回的具体数据

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultMsg(int result, String errorMsg, Object data) {
		super();
		this.result = result;
		this.errorMsg = errorMsg;
		this.data = data;
	}

}
