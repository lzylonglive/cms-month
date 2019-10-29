package com.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 专题
 * 
 * @作者：lzy @时间：2019年10月26日
 */
public class Special implements Serializable {

	private static final long serialVersionUID = -949024256321523891L;
	
	private Integer id;
	private String title;
	private String digest;// abstract
	private Date created;

	private Integer articleNum;

	private List<Article> articleList;

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(Integer articleNum) {
		this.articleNum = articleNum;
	}

	@Override
	public String toString() {
		return "Special [id=" + id + ", title=" + title + ", digest=" + digest + ", created=" + created
				+ ", articleNum=" + articleNum + ", articleList=" + articleList + "]";
	}

}
