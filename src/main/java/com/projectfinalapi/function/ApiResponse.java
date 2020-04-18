package com.projectfinalapi.function;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ApiResponse {

    public String status(int status , String meassage){
        JSONObject obj = new JSONObject();
        obj.put("status", status);
        obj.put("message", meassage);
        return obj.toString();
    }
    public String error(String timestamp , int status , String errer , String message , String path) {
    	JSONObject obj = new JSONObject();
    	obj.put("timestamp", timestamp);
    	obj.put("status", status);
    	obj.put("error", errer);
    	obj.put("message", message);
    	obj.put("path", path);
    	return obj.toString();
    }
    public String authenticate(String timestamp , int status , String token) {
    	JSONObject obj = new JSONObject();
    	obj.put("timestamp", timestamp);
    	obj.put("status", status);
    	obj.put("token", token);
    	return obj.toString();
    }
    public String users(String timestamp , int status , String username , String role) {
    	JSONObject obj = new JSONObject();
    	obj.put("timestamp", timestamp);
    	obj.put("status", status);
    	obj.put("username", username);
    	obj.put("role", role);
    	return obj.toString();
    }
    public String web(String timestamp , int status , String url) {
    	JSONObject obj = new JSONObject();
    	obj.put("timestamp", timestamp);
    	obj.put("status", status);
    	obj.put("url", url);
    	return obj.toString();
    }
    public String schedule(String timestamp , int status , String scheduleName) {
    	JSONObject obj = new JSONObject();
    	obj.put("timestamp", timestamp);
    	obj.put("status", status);
    	obj.put("scheduleName", scheduleName);
    	return obj.toString();
    }
    public String delete(String timestamp , int status , String message ) {
    	JSONObject obj = new JSONObject();
    	obj.put("timestamp", timestamp);
    	obj.put("status", status);
    	obj.put("message", message);
    	return obj.toString();
    }
    
}
