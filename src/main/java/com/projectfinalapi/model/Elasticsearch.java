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
    @Value("${elasIp}")
    private String elasIp;

    public String getByCategory(String index, String category) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": 0,\"size\": 1000,\"sort\": {\"discount\": \"desc\"},"
        	  		+ "\"query\": {\"bool\": {\"must\": {\"match\": {\"category\": \""+category+"\"}}}}}")
        	  .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }
    public String getByName(String index, String name) {
        String values = null;
        try {
        	Unirest.setTimeouts(0, 0);
        	HttpResponse<String> response = Unirest.post(elasIp+index+"/_search")
        	  .header("Content-Type", "application/json")
        	  .body("{\"from\": 0,\"size\": 1000,\"sort\": {\"discount\": \"desc\"},"
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
}
