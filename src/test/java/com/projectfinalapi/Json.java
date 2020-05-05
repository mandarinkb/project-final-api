package com.projectfinalapi;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.projectfinalapi.model.Elasticsearch;

public class Json {

	public static String changeKeyUpperCase (String inputStr) {
        String[] splitStr = inputStr.toLowerCase().split("_");
        int index = 0;
        List<String> listStr = new ArrayList<>();
        for (String split : splitStr) {
        	if(index > 0) {
        		split = split.substring(0, 1).toUpperCase() + split.substring(1);
        	}
        	index++;
        	listStr.add(split);
        }  
        String newStr = ""; 
        for(String str:listStr) {
        	newStr = newStr.concat(str);
        }
        return newStr;
	}
	
	void demo() {
		for(int i = 0;i<353; i= i + 99) {
			System.out.println(i);
		}
	}
	
    public String countItemDesc() {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post("http://localhost:9200/tescolotus-db-2/_count")
        	  .header("Content-Type", "application/json")
        	  .body("{\"query\": {\"match_all\": {}}}")
        	  .asString();

            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
	public static void main(String args[]) throws JSONException {
		String str = "USER_NAME";
		Json obj = new Json();
		//obj.changeKeyUpperCase(str);
		//String s = "{\"match_phrase\": {\"category\": \"เครื่องใช้ไฟฟ้า อุปกรณ์ภายในบ้าน\"}},\n{\"match_phrase\": {\"category\": \"ผลิตภัณฑ์เพื่อสุขภาพ ความงาม\"}},";
/*		
		String s = "{\"match_phrase\": {\"category\": \"เครื่องใช้ไฟฟ้า อุปกรณ์ภายในบ้าน\"}}";
		System.out.println(s);
		System.out.println("last character: " +s.substring(s.length() - 1)); 		
	    if (s != null && s.length() > 0 && s.charAt(s.length() - 1) == ',') {
	        s = s.substring(0, s.length() - 1);
	    }
	    System.out.println("after => "+ s);
*/
		// obj.demo();

		JSONObject js = new JSONObject(obj.countItemDesc());
		String count = js.getString("count");
		System.out.println(count);
	}
}
