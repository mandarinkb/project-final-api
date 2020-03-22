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
}
