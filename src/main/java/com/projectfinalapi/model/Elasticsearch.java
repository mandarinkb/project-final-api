package com.projectfinalapi.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Repository
public class Elasticsearch {

    public String getResules(String index, String date) {
        String values = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"query\": {\"match_phrase\": {\"date\": \"" + date + "\"}}}")
                    .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }

    public String getResulesHomeAway(String index, String homeAway, String AwayHome) {
        String values = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"sort\" : [{ \"date\" : \"desc\" }],\"query\": {\"bool\": {\"should\": [{\"match_phrase\": {\"home_away_id\": \"" + homeAway + "\"}},{\"match_phrase\": {\"home_away_id\": \"" + AwayHome + "\"}}]}}}")
                    .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }

    public String getFixtures(String index, String date) {
        String values = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"query\": {\"match_phrase\": {\"date\": \"" + date + "\"}}}")
                    .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }

    public String getLeagueTable(String index, String typeTable) {
        String values = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"query\": {\"bool\": {\"must\": {\"match_phrase\": {\"type_table\": \"" + typeTable + "\"}}}}}")
                    .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }

    public String searchAllTeam(String index) {
        String str = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"query\": {\"match_all\": {}}}")
                    .asString();
            str = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str.toString();
    }

    public String searchAllPlayersOfTeam(String index, String team) {
        String str = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"query\": {\"bool\": {\"must\": {\"match_phrase\": {\"team_id\": \"" + team + "\"}}}}}")
                    .asString();
            str = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str.toString();
    }

    public String searchPlayerDetail(String index, String nameId) {
        String values = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"query\": {\"bool\": {\"must\": {\"match_phrase\": {\"player_name_id\": \"" + nameId + "\"}}}}}")
                    .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }

    public String searchPerformanceDetail(String index, String nameId) {
        String values = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/" + index + "/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"size\": 100,\"query\": {\"bool\": {\"must\": {\"match_phrase\": {\"player_name_id\": \"" + nameId + "\"}}}}}")
                    .asString();
            values = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toString();
    }

    public String searchScoreAnalyze(String index, String date, String homeAway) {
        String str = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/"+index+"/_search")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"query\":{\"bool\":{\"must\":[{\"match_phrase\":{\"date\": \"" + date + "\"}},{\"match_phrase\":{\"home_away_id\": \"" + homeAway + "\"}}]}}}")
                    .asString();
            str = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }    
}
