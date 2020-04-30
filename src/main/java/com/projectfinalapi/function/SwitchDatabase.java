package com.projectfinalapi.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projectfinalapi.model.Query;

@Component
public class SwitchDatabase {
	@Autowired
	private Query q;
	
	public String getDatabaseRun() {
		String dbRun = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");	
		return dbRun;
	}

}
