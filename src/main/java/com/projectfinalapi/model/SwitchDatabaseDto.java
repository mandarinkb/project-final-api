package com.projectfinalapi.model;

import org.springframework.stereotype.Component;

@Component
public class SwitchDatabaseDto {

	private int databaseId;
	private String databaseName;
	private String databaseStatus;
	
	public int getDatabaseId() {
		return databaseId;
	}
	public void setDatabaseId(int databaseId) {
		this.databaseId = databaseId;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getDatabaseStatus() {
		return databaseStatus;
	}
	public void setDatabaseStatus(String databaseStatus) {
		this.databaseStatus = databaseStatus;
	}
	
	
	
}
