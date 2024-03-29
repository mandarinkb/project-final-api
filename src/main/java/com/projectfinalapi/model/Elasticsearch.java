package com.projectfinalapi.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.projectfinalapi.function.Json;


@Repository
public class Elasticsearch {
	public static String elaSizeValue = "50";
	@Autowired
	private Json json;
	
    @Value("${elasIp}")
    private String elasIp;

    // หน้าแรก
    public String getByNameHistory(String index, String[] historyName,String from) {
    	String regxNameJson = json.historyRegxName(historyName);
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+from+",\"size\": "+elaSizeValue+",\n\"sort\": {\"discount\": \"desc\"},"
        	  		+ "\"query\": {\"bool\": {\"should\": ["
        	  		+ regxNameJson
        	  		+ "]}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
    // หน้าแรกกรณีไม่มีค่าใน history
    public String getItems(String index,String from) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+from+",\"size\": "+elaSizeValue+",\"sort\": {\"discount\": \"desc\"},\"query\": {\"match_all\": {}}}")
        	  .asString();

            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    /*
    //search category
    public String getByCategory(String index, String category,String from) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+from+",\"size\": "+elaSizeValue+",\"sort\": {\"discount\": \"desc\"},"
        	  		+ "\"query\": {\"bool\": {\"must\": {\"match\": {\"category\": \""+category+"\"}}}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    */
    // new
    public String getByCategory(String index, String category, String[] webName,String from) {
    	String webNameJson = json.webNameJson(webName);
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+from+",\"size\": "+elaSizeValue+",\"sort\": {\"discount\": \"desc\"},"
        	  		+ "\"query\": {\"bool\": {\"must\": [\n" + 
        	  		"{\"match\": {\"category\": \""+category+"\"}},"
        	  		+ "{\"dis_max\": {\"queries\": [\n" 
        	  		+ webNameJson +
        	  		"]}}]}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }

    public String getByName(String index, String name,String from) {
    	String regxNameJson = json.regxName(name);
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+from+",\"size\": "+elaSizeValue+",\n\"sort\": {\"discount\": \"desc\"},"
        	  		+ "\"query\": {\"bool\": {\"must\": ["
        	  		+ regxNameJson
        	  		+ "]}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
    public String getByNameAndFilter(String index, String name , String min,
    		                         String max, String[] webName,String from) {
    	String webNameJson = json.webNameJson(webName);
    	String regxNameJson = json.regxName(name);
        String values = null;
        String priceFilter = "";
        if(min =="10001" && max == "10001") { // กรณี มากกว่าที่กำหนด
        	priceFilter = "{\"price\": {\"gte\": "+max+"}";
        }else {
        	priceFilter = "{\"price\": {\"gte\": "+min+",\"lte\": "+max+"}";
        }
        
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+from+",\"size\": "+elaSizeValue+",\n\"sort\": {\"discount\": \"desc\"},"
        	  		+ "\n\"query\": {\n\"bool\": {\n\"must\": ["
        	  		+ regxNameJson
        	  		+ ",{\n\"dis_max\": {\n\"queries\": ["
        	  		+ webNameJson
        	  		+ "\n]\n}\n},\n{\"range\": "+priceFilter+""
        	  		+ "}}\n]\n}\n}\n}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }    
    
    public String getLog(String datetime) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+"web_scrapping_log/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": 0,"
        	  		+ "\"size\": 1000,"
        	  		+ "\"sort\": {\"timestamp\": \"desc\"},"
        	  		+ "\"query\": {\"bool\": {\"must\": {\"match_phrase\": {\"datetime\": \""+datetime+"\"}}}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }  
}
