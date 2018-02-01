package com.tianyl.android.wechat.sync;

public class AppMsg {

	private String title;

	private String url;

	private String digest;

	private String publishTime;

	private String appName;

	private String publisherUsername;

	private String type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPublisherUsername() {
		return publisherUsername;
	}

	public void setPublisherUsername(String publisherUsername) {
		this.publisherUsername = publisherUsername;
	}

	public void print() {
		System.out.println(getStr(appName, 20) + getStr(type, 2) + getStr(publisherUsername, 20) + getStr(publishTime, 20) + "\t" + title + "\t" + url);
	}

	private String getStr(String str, int length) {
		if (str == null) {
			str = "";
		}
		if (str.length() < length) {
			for (int i = str.length(); i < length; i++) {
				str += " ";
			}
		}
		return str;
	}
}
