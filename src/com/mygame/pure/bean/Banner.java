package com.mygame.pure.bean;

import java.io.Serializable;


public class Banner implements Serializable{
	private String image; // 设备名字
	private String url; // 设备UUID
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
