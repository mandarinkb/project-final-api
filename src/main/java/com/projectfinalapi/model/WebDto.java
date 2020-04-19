package com.projectfinalapi.model;

import org.springframework.stereotype.Component;

@Component
public class WebDto {

	private int webId;
	private String webName;
	private String webUrl;
	private String webStatus;
	private String iconUrl;
	public int getWebId() {
		return webId;
	}
	public void setWebId(int webId) {
		this.webId = webId;
	}
	public String getWebName() {
		return webName;
	}
	public void setWebName(String webName) {
		this.webName = webName;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getWebStatus() {
		return webStatus;
	}
	public void setWebStatus(String webStatus) {
		this.webStatus = webStatus;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	
}
