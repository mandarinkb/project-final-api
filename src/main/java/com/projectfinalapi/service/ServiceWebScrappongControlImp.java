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
import com.projectfinalapi.model.Database;
import com.projectfinalapi.model.Query;

@Service
public class ServiceWebScrappongControlImp implements ServiceWebScrappingControl {
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
                        listChar.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(listChar.get(i), true);
                        } else {
                            obj.put(listChar.get(i), false);
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
                    if (columns.getColumnTypeName(i) == "INTEGER") {
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
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
    
/*    @Override
    public String saveUsers(String username, String password, String role) {
        String sql = "INSERT INTO users (username,password,role) "
                   + "VALUES('" + username + "','" + password + "', '" + role + "')";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "บันทึกข้อมูลเรียบร้อย");
    }
*/
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
    public String getWeb() {
        String sql = "select * from web ORDER BY web_id DESC";
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
                    if (columns.getColumnTypeName(i) == "INT") {
                        listInt.add(columns.getColumnName(i));
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i));
                    } else {
                        listVarchar.add(columns.getColumnName(i));
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(listChar.get(i), true);
                        } else {
                            obj.put(listChar.get(i), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
    }

    @Override
    public String saveWeb(String webName, String url, String type, String typeDetail, String webStatus, String season, String baseUrl, String detail) {
        String sql = "INSERT INTO web (web_name,url,type,type_detail,web_status,season,base_url,detail) "
                   + "VALUES('" + webName + "','" + url + "', '" + type + "','" + typeDetail + "','" + webStatus + "','" + season + "','" + baseUrl + "','" + detail + "')";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "บันทึกข้อมูลเรียบร้อย");
    }

    @Override
    public String findWebById(int web_id) {
        String sql = "select * FROM web WHERE web_id =" + web_id;
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
                    if (columns.getColumnTypeName(i) == "INT") {
                        listInt.add(columns.getColumnName(i));
                    } else {
                        listVarchar.add(columns.getColumnName(i));
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return obj.toString();
    }
    
    @Override
    public String updateWebStatus(int id, String webStatus) {
        String sql = "UPDATE web SET web_status = '" + webStatus + "' WHERE web_id =" + id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "อัพเดทสถานะเรียบร้อย");
    }

    @Override
    public String updateWeb(int web_id, String webName, String url, String type, String typeDetail, String webStatus, String season, String baseUrl, String detail) {
        String sql = "UPDATE web SET web_name = '" + webName + "' , url = '" + url + "' ,"
                   + " type = '" + type + "' , type_detail = '" + typeDetail + "' , web_status = '" + webStatus + "',"
                   + " season = '" + season + "', base_url = '" + baseUrl + "', detail = '" + detail + "' WHERE web_id =" + web_id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "อัพเดทข้อมูลเรียบร้อย");
    }    
   
    @Override
    public String deleteWeb(int web_id) {
        String sql = "DELETE FROM web WHERE web_id= '" + web_id + "'";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "ลบข้อมูลเรียบร้อย");
    }    
    
    @Override
    public String getSchedule() {
        String sql = "select * from schedule ORDER BY schedule_id DESC";
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
                    if (columns.getColumnTypeName(i) == "INT") {
                        listInt.add(columns.getColumnName(i));
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i));
                    } else {
                        listVarchar.add(columns.getColumnName(i));
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(listChar.get(i), true);
                        } else {
                            obj.put(listChar.get(i), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
    }

    @Override
    public String findScheduleById(int id) {
        String sql = "select * FROM schedule WHERE schedule_id =" + id;
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
                    if (columns.getColumnTypeName(i) == "INT") {
                        listInt.add(columns.getColumnName(i));
                    } else {
                        listVarchar.add(columns.getColumnName(i));
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return obj.toString();
    }

    @Override
    public String saveSchedule(String scheduleName, String cronExpression, String functionName, String projectName, String detail) {
        String sql = "INSERT INTO schedule (schedule_name,cron_expression,function_name,project_name,detail) "
                   + "VALUES('" + scheduleName + "','" + cronExpression + "', '" + functionName + "','" + projectName + "','" + detail + "')";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "บันทึกข้อมูลเรียบร้อย");
    }

    @Override
    public String updateSchedule(int id, String scheduleName, String cronExpression, String functionName, String projectName, String detail) {
        String sql = "UPDATE schedule SET schedule_name = '" + scheduleName + "' , cron_expression = '" + cronExpression + "' ,"
                + " function_name = '" + functionName + "' , project_name = '" + projectName + "' , detail = '" + detail + "' WHERE schedule_id =" + id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "อัพเดทข้อมูลเรียบร้อย");
    }

    @Override
    public String deleteSchedule(int schedule_id) {
        String sql = "DELETE FROM schedule WHERE schedule_id= '" + schedule_id + "'";
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);
        return apiResponse.status(200, "ลบข้อมูลเรียบร้อย");
    }

}
