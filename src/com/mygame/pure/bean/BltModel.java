package com.mygame.pure.bean;

import com.lidroid.xutils.db.annotation.Column;

public class BltModel extends EntityBase{
	// 蓝牙硬件所涉及的数据
	@Column(column = "bltname")
	private String bltname; // 设备名字
	@Column(column = "bltid")
	private String bltid; // 设备UUID
	@Column(column = "bltversion")
	private String bltversion; // 硬件版本
	@Column(column = "bltelec")
	private String bltelec; // 电量
	@Column(column = "date")
	private String date; // 日期
	@Column(column = "water")
	private String water; // 水分
	public String getBltname() {
		return bltname;
	}
	public void setBltname(String bltname) {
		this.bltname = bltname;
	}
	public String getBltid() {
		return bltid;
	}
	public void setBltid(String bltid) {
		this.bltid = bltid;
	}
	public String getBltversion() {
		return bltversion;
	}
	public void setBltversion(String bltversion) {
		this.bltversion = bltversion;
	}
	public String getBltelec() {
		return bltelec;
	}
	public void setBltelec(String bltelec) {
		this.bltelec = bltelec;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	
	
	

}
