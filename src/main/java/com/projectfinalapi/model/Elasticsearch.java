package com.projectfinalapi.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Repository
public class Elasticsearch {
	public static String elaSizeValue = "50";
	
    @Value("${elasIp}")
    private String elasIp;

    //search
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
    
    public String getByName(String index, String name,String from) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+from+",\"size\": "+elaSizeValue+",\"sort\": {\"discount\": \"desc\"},"
        	  		+ "\"query\": {\"regexp\": {\"name\": {\"value\": \"(.*)"+name+"(.*)\","
        	  		+ "\"flags\" : \"ALL\",\"max_determinized_states\": 10000,"
        	  		+ "\"rewrite\": \"constant_score\" }}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
    public String getByNameAndFilter(String index, String name , String min,
    		                         String max, String webNameJson,String from) {
        String values = null;
        String priceFilter = "";
        if(min.equals(max)) { // กรณี มากกว่าที่กำหนด
        	priceFilter = "{\"price\": {\"gte\": "+max+"}";
        }else {
        	priceFilter = "{\"price\": {\"gte\": "+min+",\"lte\": "+max+"}";
        }
        
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\n\"from\": "+from+",\n\"size\": "+elaSizeValue+",\n\"sort\": {\n\"discount\": \"desc\"\n},"
        	  	  + "\n\"query\": {\n\"bool\": {\n\"must\": [\n"
        	  	  + "{\n\"regexp\": {\n\"name\": {\n\"value\": \"(.*)"+name+"(.*)\","
        	  	  + "\n\"flags\": \"ALL\",\n\"max_determinized_states\": 10000,"
        	  	  + "\n\"rewrite\": \"constant_score\"\n}}},"
        	  	  + "\n{\n\"dis_max\": {\n\"queries\": [\n"
        	  	  + webNameJson
        	  	  + "\n]\n}},\n{\"range\": "+priceFilter+"}}\n]\n}}}")
        	  .asString();// "\n]\n}},\n{\"range\": {\"price\": {\"gte\": "+min+",\"lte\": "+max+"}}}\n]\n}}}"

            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
    public String getItemDesc(String index,String fromValue ) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+fromValue+",\"size\": "+elaSizeValue+",\"sort\": {\"discount\": \"desc\"},\"query\": {\"match_all\": {}}}")
        	  .asString();

            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
/*    
    public String getLog(String fromValue ) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+"web_scrapping_log/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": "+fromValue+",\"size\": "+elaSizeValue+",\"sort\": {\"timestamp\": \"desc\"},\"query\": {\"match_all\": {}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
*/    
    
    public String getLog(String timestamp) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post("http://localhost:9200/web_scrapping_log/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"query\": {\"bool\": {\"must\": {\"match_phrase\": {\"timestamp\": \""+timestamp+"\"}}}}}")
        	  .asString();

            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
/*    
    //count
    public String countByCategory(String index, String category) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_count")
        	  .header("Content-Type", "application/json")
        	  .body("{\"query\": {\"bool\": {\"must\": {\"match\": {\"category\": \""+category+"\"}}}}}")
        	  .asString();
 
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
    public String countByName(String index, String name) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_count")
        	  .header("Content-Type", "application/json")
        	  .body("{\"query\": {\"regexp\": {\"name\": {\"value\": \"(.*)"+name+"(.*)\","
        	  		+ "\"flags\" : \"ALL\",\"max_determinized_states\": 10000,"
        	  		+ "\"rewrite\": \"constant_score\" }}}}")
        	  .asString();

            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
    public String countByNameAndFilter(String index, String name , String min, String max, String webNameJson) {
        String values = null;
        String priceFilter = "";
        if(min.equals(max)) { // กรณี มากกว่าที่กำหนด
        	priceFilter = "{\"price\": {\"gte\": "+max+"}";
        }else {
        	priceFilter = "{\"price\": {\"gte\": "+min+",\"lte\": "+max+"}";
        }
        
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_count")
        	  .header("Content-Type", "application/json")
        	  .body("{\"query\": {\n\"bool\": {\n\"must\": [\n"
        	  	  + "{\n\"regexp\": {\n\"name\": {\n\"value\": \"(.*)"+name+"(.*)\","
        	  	  + "\n\"flags\": \"ALL\",\n\"max_determinized_states\": 10000,"
        	  	  + "\n\"rewrite\": \"constant_score\"\n}}},"
        	  	  + "\n{\n\"dis_max\": {\n\"queries\": [\n"
        	  	  + webNameJson
        	  	  + "\n]\n}},\n{\"range\": "+priceFilter+"}}\n]\n}}}")
        	  .asString();


            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    
    public String countItemDesc(String index) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_count")
        	  .header("Content-Type", "application/json")
        	  .body("{\"query\": {\"match_all\": {}}}")
        	  .asString();

            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
*/    
}
