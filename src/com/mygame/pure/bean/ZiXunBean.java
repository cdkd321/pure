package com.mygame.pure.bean;

import java.io.Serializable;

public class ZiXunBean implements Serializable{
	private String id; // 设备名字
	private String title; // 设备UUID
	private String image;
	private String lioulanshu;
	private String dianzanshu;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLioulanshu() {
		return lioulanshu;
	}
	public void setLioulanshu(String lioulanshu) {
		this.lioulanshu = lioulanshu;
	}
	public String getDianzanshu() {
		return dianzanshu;
	}
	public void setDianzanshu(String dianzanshu) {
		this.dianzanshu = dianzanshu;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
