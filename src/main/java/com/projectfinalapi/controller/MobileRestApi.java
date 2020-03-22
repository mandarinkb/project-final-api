package com.projectfinalapi.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectfinalapi.function.DateTime;
import com.projectfinalapi.model.Fixtures;
import com.projectfinalapi.model.LeagueTable;
import com.projectfinalapi.model.PlayerDetail;
import com.projectfinalapi.model.ResulesHomeAway;
import com.projectfinalapi.model.Results;
import com.projectfinalapi.model.Teams;
import com.projectfinalapi.model.TeamsAndLogo;
import com.projectfinalapi.model.TeamsDetail;
import com.projectfinalapi.service.ServiceMobileApi;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mobile")
public class MobileRestApi {
    @Autowired
    private ServiceMobileApi  serviceMobileApi;
    
    @Autowired
    private DateTime dateTime;
    
    @PostMapping(path = {"/results"}, headers = "Accept=application/json;charset=UTF-8")
    public List<Results> results(@RequestBody String str){
        JSONObject obj = new JSONObject(str);
        String fullDate = obj.getString("date");
        String league = obj.getString("league");
        String date = dateTime.splitDateFromMobile(fullDate);
        return serviceMobileApi.listResults(league,date); //premierleague  , thaipremierleague
    }

    @PostMapping(path = {"/fixtures"}, headers ="Accept=application/json;charset=UTF-8")
    public List<Fixtures> fixtures(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String fullDate = obj.getString("date");
        String league = obj.getString("league");
        String date = dateTime.splitDateFromMobile(fullDate);
        return serviceMobileApi.listFixtures(league, date);   
    }
    
   @PostMapping(path = {"/previous-results"}, headers="Accept=application/json;charset=UTF-8")
   public List<ResulesHomeAway>  previousResults(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        String team = obj.getString("home_away");
        team = team.replace("|", " - ");
        return serviceMobileApi.listResultsHomeAway(league, team);
   }
   
   @PostMapping(path ={"/league-table"}, headers="Accept=application/json;charset=UTF-8")
   public List<LeagueTable> listLeagueTable(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        String typeTable = obj.getString("type_table");
        return serviceMobileApi.listLeagueTable(league, typeTable);
   }

   @PostMapping(path ={"/teams"}, headers="Accept=application/json;charset=UTF-8")
   public List<Teams> listTeams(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        return serviceMobileApi.listTeams(league);
   }
   
   @PostMapping(path = {"/teams-detail"}, headers="Accept=application/json;charset=UTF-8")
   public List<TeamsDetail> listTeamsDetail(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        String team = obj.getString("team");
        return serviceMobileApi.listTeamsDetail(league, team);
   }
   
   @PostMapping(path = {"/team-logo"}, headers="Accept=application/json;charset=UTF-8")
   public List<TeamsAndLogo> listTeamsAndLogo(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        String team = obj.getString("team");
        return serviceMobileApi.listTeamsAndLogo(league, team);
   }
   
   @PostMapping(path ={"/players-detail"}, headers="Accept=application/json;charset=UTF-8")
   public List<PlayerDetail> listPlayerDetail(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        String player = obj.getString("player");
        return serviceMobileApi.listPlayerDetail(league, player);
   }
   @PostMapping(path = {"/performance-detail"}, headers="Accept=application/json;charset=UTF-8")
   public <T> List<T>listPerformanceDetail(@RequestBody String strBody){
       JSONObject obj = new JSONObject(strBody);
       String league = obj.getString("league");
       String player = obj.getString("player");
       String position = obj.getString("position");
       if ("ผู้รักษาประตู".equals(position)) {
           return (List<T>) serviceMobileApi.listPerformanceDetailPk(league, player);
       } else {
           return (List<T>) serviceMobileApi.listPerformanceDetailOther(league, player);
       }
   }
   @PostMapping(path = {"/avg-team"}, headers="Accept=application/json;charset=UTF-8")
   public String listAvgTeam(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        String team = obj.getString("home_away");
        team = team.replace("|", " - ");
        return serviceMobileApi.listAvgFormPresentTeam(league, team);    
   }

   @PostMapping(path = {"/score-analyze"}, headers="Accept=application/json;charset=UTF-8")
   public String listScoreAnalyze(@RequestBody String strBody){
        JSONObject obj = new JSONObject(strBody);
        String league = obj.getString("league");
        String date = obj.getString("date");
        String team = obj.getString("home_away");
        team = team.replace("|", " - ");
        return serviceMobileApi.listScoreAnalyze(league, date, team);
   }
}

