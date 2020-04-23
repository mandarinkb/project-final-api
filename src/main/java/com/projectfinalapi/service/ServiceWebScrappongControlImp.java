package com.projectfinalapi.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectfinalapi.function.ApiResponse;
import com.projectfinalapi.function.DateTime;
import com.projectfinalapi.function.Json;
import com.projectfinalapi.model.Database;
import com.projectfinalapi.model.Query;
import com.projectfinalapi.model.ScheduleDto;
import com.projectfinalapi.model.SwitchDatabaseDto;
import com.projectfinalapi.model.WebDto;

@Service
public class ServiceWebScrappongControlImp implements ServiceWebScrappingControl {
	@Autowired
	private Json json;
	
	@Autowired
	private DateTime  dateTime; 

	@Autowired
	private Query q;
	  
    @Autowired
    private Database db;

    @Autowired
    private ApiResponse apiResponse;
    
    @Override
    public String findUsers() {
        String sql = "select * from USERS order by USER_ID desc";
        List<String> listVarchar = new ArrayList<String>();
        List<String> listChar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<>();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {  // แยก data type พร้อมเก็บลง list
                    i++;
                    // เก็บชื่อ column
                    if (columns.getColumnTypeName(i) == ("INTEGER")) {  
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i).toLowerCase()); //columns.getColumnName(i).toLowerCase()
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), true);
                        } else {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
    }

    @Override
    public String findUsersById(int id) {
        String sql = "select * from USERS where USER_ID =" + id;
        List<String> listVarchar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        JSONObject obj = new JSONObject();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INTEGER") {//INTEGER
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return obj.toString();
    }  
    
    @Override
    public String updateUsers(int id, String role) {
    	String username = q.findOneStrExcuteQuery("select USERNAME from USERS where USER_ID= '"+id+"' ");
        String sql = "update USERS set ROLE = '" + role + "' where USER_ID =" + id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.users(dateTime.timestamp(), 200, username, role);
    }
    
    @Override
    public String deleteUsers(int id) {
        String sql = "delete from USERS where USER_ID= '" + id+ "'";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);       
        return apiResponse.delete(dateTime.timestamp(), 204, "deleted successfully");
    }
    
    @Override
    public String saveWeb(WebDto web) {
        String sql = "insert into WEB (WEB_NAME,WEB_URL,WEB_STATUS,ICON_URL) "
                   + "values('" + web.getWebName() + "','" + web.getWebUrl() + "', '" + web.getWebStatus() + "','" + web.getIconUrl() + "')";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        
        return apiResponse.web(dateTime.timestamp(), 201, web.getWebUrl());
    }
    
    @Override
    public String findWeb() {
        String sql = "select * from WEB order by WEB_ID desc";
        List<String> listVarchar = new ArrayList<String>();
        List<String> listChar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<>();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INTEGER") {
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), true);
                        } else {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
    }



    @Override
    public String findWebById(int web_id) {
        String sql = "select * from WEB where WEB_ID =" + web_id;
        List<String> listVarchar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        JSONObject obj = new JSONObject();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INTEGER") {
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return obj.toString();
    }
    
    @Override
    public String updateWebStatus(int web_id , String webStatus) {
        String sql = "update WEB set WEB_STATUS = '" + webStatus+ "' where WEB_ID =" + web_id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);       
        return apiResponse.webStatus(dateTime.timestamp(), 200, webStatus);
    }

    @Override
    public String updateWeb(int web_id, WebDto web) {
        String sql = "update WEB set WEB_NAME = '" + web.getWebName() + "' , WEB_URL = '" + web.getWebUrl() + "' ,"
                   + " WEB_STATUS = '" + web.getWebStatus() + "' , ICON_URL = '" + web.getIconUrl() + "'"
                   + " WHERE WEB_ID = " + web_id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.web(dateTime.timestamp(), 200, web.getWebUrl());
    }    
   
    @Override
    public String deleteWeb(int web_id) {
        String sql = "delete from WEB where WEB_ID= '" + web_id + "'";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.delete(dateTime.timestamp(), 204, "deleted successfully");
    }    
    
    @Override
    public String saveSchedule(ScheduleDto schedule) {
        String sql = "insert into SCHEDULE(SCHEDULE_NAME,CRON_EXPRESSION,METHOD_NAME,PROJECT_NAME) "
                   + "values('" + schedule.getScheduleName() + "','" + schedule.getCronExpression() + "',"
                   		+ " '" + schedule.getMethodName() + "','" + schedule.getProjectName() + "')";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.schedule(dateTime.timestamp(), 200, schedule.getScheduleName());
    } 
    
    @Override
    public String findSchedule() {
        String sql = "select * from SCHEDULE order by SCHEDULE_ID desc";
        List<String> listVarchar = new ArrayList<String>();
        List<String> listChar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<>();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INTEGER") {
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), true);
                        } else {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
    }

    @Override
    public String findScheduleById(int id) {
        String sql = "select * from SCHEDULE where SCHEDULE_ID =" + id;
        List<String> listVarchar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        JSONObject obj = new JSONObject();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INTEGER") {
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return obj.toString();
    }

    @Override
    public String updateSchedule(int id, ScheduleDto schedule) {
        String sql = "update SCHEDULE set SCHEDULE_NAME = '" + schedule.getScheduleName() + "' , CRON_EXPRESSION = '" + schedule.getCronExpression() + "' ,"
                  + " METHOD_NAME = '" + schedule.getMethodName() + "' , PROJECT_NAME = '" + schedule.getProjectName() + "'  WHERE SCHEDULE_ID =" + id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.schedule(dateTime.timestamp(), 200, schedule.getScheduleName());
    }

    @Override
    public String deleteSchedule(int schedule_id) {
        String sql = "delete from SCHEDULE where SCHEDULE_ID= '" + schedule_id + "'";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.delete(dateTime.timestamp(), 204, "deleted successfully");
    }

	@Override
	public String saveSwitchDatabase(SwitchDatabaseDto switchDatabase) {
        String sql = "insert into SWITCH_DATABASE(DATABASE_NAME,DATABASE_STATUS) "
                + "values('" + switchDatabase.getDatabaseName() + "','" + switchDatabase.getDatabaseStatus() + "')";
     Connection conn = db.connectDatase();
     db.executeQuery(conn, sql);
     db.closeConnect(conn);
     return apiResponse.schedule(dateTime.timestamp(), 200, switchDatabase.getDatabaseName());
	}

	@Override
	public String findSwitchDatabase() {
        String sql = "select * from SWITCH_DATABASE order by DATABASE_ID desc";
        List<String> listVarchar = new ArrayList<String>();
        List<String> listChar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<>();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INTEGER") {
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), true);
                        } else {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
	}

	@Override
	public String findSwitchDatabaseleById(int id) {
        String sql = "select * from SWITCH_DATABASE where DATABASE_ID =" + id;
        List<String> listVarchar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        JSONObject obj = new JSONObject();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INTEGER") {
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return obj.toString();
	}

	@Override
	public String updateSwitchDatabaseStatus(int databaseId, String databaseStatus) {
        String sql = "update SWITCH_DATABASE set DATABASE_STATUS = '" + databaseStatus+ "' where DATABASE_ID =" + databaseId;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        // update สถานะอื่นๆที่เหลือให้เป็น 0
        String newSql = "update SWITCH_DATABASE set DATABASE_STATUS = '0' where DATABASE_ID <>"+ databaseId;
        db.executeQuery(conn, newSql);
        db.closeConnect(conn);       
        return apiResponse.switchDatabaseStatus(dateTime.timestamp(), 200, databaseStatus);
	}
	
	@Override
	public String updateSwitchDatabase(int id, SwitchDatabaseDto switchDatabase) {
        String sql = "update SWITCH_DATABASE set DATABASE_NAME = '" + switchDatabase.getDatabaseName() + "' , "
                + " DATABASE_STATUS = '" + switchDatabase.getDatabaseStatus() + "'  WHERE DATABASE_ID =" + id;
      Connection conn = db.connectDatase();
      db.executeQuery(conn, sql);
      db.closeConnect(conn);
      return apiResponse.schedule(dateTime.timestamp(), 200, switchDatabase.getDatabaseName());
	}

	@Override
	public String deleteSwitchDatabase(int databaseid) {
        String sql = "delete from SWITCH_DATABASE where DATABASE_ID= '" + databaseid + "'";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.delete(dateTime.timestamp(), 204, "deleted successfully");
	}



}
