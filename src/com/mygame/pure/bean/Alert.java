package com.mygame.pure.bean;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;

public class Alert extends EntityBase implements Serializable {
	@Column(column = "alertname")
	private String alertname;
	@Column(column = "alerttime")
	private String alerttime;
	@Column(column = "alertmusic")
	private String alertmusic;
	@Column(column = "currenttime")
	private long currenttime;
	@Column(column = "clockid")
	private int clockid;

	public int getClockid() {
		return clockid;
	}

	public void setClockid(int clockid) {
		this.clockid = clockid;
	}

	public long getCurrenttime() {
		return currenttime;
	}

	public void setCurrenttime(long currenttime) {
		this.currenttime = currenttime;
	}

	public String getAlertmusic() {
		return alertmusic;
	}

	public void setAlertmusic(String alertmusic) {
		this.alertmusic = alertmusic;
	}

	@Column(column = "alertrepeat")
	private String alertrepeat;

	public String getAlertName() {
		return alertname;
	}

	public void setAlertName(String alertname) {
		this.alertname = alertname;
	}

	public String getAlertTime() {
		return alerttime;
	}

	public void setAlertTime(String alerttime) {
		this.alerttime = alerttime;
	}

	public String getAlertRepeat() {
		return alertrepeat;
	}

	public void setAlertRepeat(String alertRepeat) {
		this.alertrepeat = alertRepeat;
	}
}
