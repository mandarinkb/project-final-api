package com.projectfinalapi.service;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectfinalapi.function.Md5;
import com.projectfinalapi.function.OtherFunc;
import com.projectfinalapi.model.Elasticsearch;
import com.projectfinalapi.model.Fixtures;
import com.projectfinalapi.model.LeagueTable;
import com.projectfinalapi.model.PerformanceDetailOther;
import com.projectfinalapi.model.PerformanceDetailPk;
import com.projectfinalapi.model.PlayerDetail;
import com.projectfinalapi.model.ResulesHomeAway;
import com.projectfinalapi.model.Results;
import com.projectfinalapi.model.Teams;
import com.projectfinalapi.model.TeamsAndLogo;
import com.projectfinalapi.model.TeamsDetail;

@Service
public class ServiceMobileApiImpl implements ServiceMobileApi{
    public String logoTeam = null;
    public String season = null;
    public String team = null;

    @Autowired
    private Elasticsearch  elasticsearch;
    
    @Autowired
    private OtherFunc otherFunc;
    
    @Autowired
    private Md5 md5;
     
    @Override
    public List<Results> listResults(String league,String date) {
        // ดึงข้อมูลจาก elasticsearch
        String index = null;
        if("thaipremierleague".equals(league)){
            index = "results_thaipremierleague";
        }else if("premierleague".equals(league)){
            index = "results_premierleague";
        }
        String resultsValue = elasticsearch.getResules(index,date);
        
        //ดึงเอาาค่าที่ต้องการเพื่อส่งไปยัง api
        List<Results> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(resultsValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            String home = obj_source.getString("home");
            String home_img = obj_source.getString("home_img");
            int score_home = Integer.parseInt(obj_source.getString("score_home"));
            String away = obj_source.getString("away");
            String away_img = obj_source.getString("away_img");
            int score_away = Integer.parseInt(obj_source.getString("score_away"));
            //ใช้ constructer toString ที่overrideไว้
            Results results = new Results(home, home_img, score_home, away, away_img, score_away);
            list.add(results);
            
            //แปลงเป็น Json
            // Creating Object of ObjectMapper define in Jakson Api
            //ObjectMapper jacksonObj = new ObjectMapper();
            //String jsonStr = jacksonObj.writeValueAsString(results);
            //list.add(jsonStr);
        }
        return list;
    }

    @Override
    public List<Fixtures> listFixtures(String league, String date) {
        String index = null;
        if("thaipremierleague".equals(league)){
            index = "fixtures_thaipremierleague";
        }else if("premierleague".equals(league)){
            index = "fixtures_premierleague";
        }
        // ดึงข้อมูลจาก elasticsearch
        String fixturesValue = elasticsearch.getFixtures(index, date);
        
        List<Fixtures> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(fixturesValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            String home = obj_source.getString("home");
            String home_img = obj_source.getString("home_img");
            String away = obj_source.getString("away");
            String away_img = obj_source.getString("away_img");
            String time = obj_source.getString("time");
            Fixtures fixtures = new Fixtures(home, home_img, away, away_img, time);
            list.add(fixtures);
        }
        return list;        
    }

    @Override
    public List<ResulesHomeAway> listResultsHomeAway(String league, String homeAway) {
        String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "results_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "results_premierleague";
        }
        String awayHome = otherFunc.homeAwayToAwayHome(homeAway);
        String homeAwayId = null;
        String awayHomeId = null;
        
        try{
            homeAwayId = md5.encrypt(homeAway.replace(" - ", ""));
            awayHomeId = md5.encrypt(awayHome.replace(" - ", ""));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        String resultsValue = elasticsearch.getResulesHomeAway(index, homeAwayId, awayHomeId);

        List<ResulesHomeAway> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(resultsValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            String date_thai = obj_source.getString("date_thai");
            String home_img = obj_source.getString("home_img");
            String score_home = obj_source.getString("score_home");
            String away_img = obj_source.getString("away_img");
            String score_away = obj_source.getString("score_away");
            String home = obj_source.getString("home");
            String away = obj_source.getString("away");
            ResulesHomeAway r = new ResulesHomeAway(date_thai, home_img, score_home, away_img, score_away, home, away);
            list.add(r);
        }
        return list;
    }

    @Override
    public List<LeagueTable> listLeagueTable(String league, String typeTable) {
        String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "league_table_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "league_table_premierleague";
        }
        String leagueTableValue = elasticsearch.getLeagueTable(index, typeTable);
        List<LeagueTable> list = new ArrayList<>();
        JSONObject objLeagueTableValue = new JSONObject(leagueTableValue);
        JSONObject objHits = objLeagueTableValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            String valueRanking = obj_source.getString("ranking");
            String valueTeam = obj_source.getString("team");
            int valuePlayed = obj_source.getInt("played");
            int valueWon = obj_source.getInt("won");
            int valueDrawn = obj_source.getInt("drawn");
            int valueLost = obj_source.getInt("lost");
            int valueGoalFor = obj_source.getInt("goal_for");
            int valueGoalAgainst = obj_source.getInt("goal_against");
            String valueGoalDifference = obj_source.getString("goal_difference");
            int valuePoints = obj_source.getInt("points");
            LeagueTable l = new LeagueTable(valueRanking, valueTeam, valuePlayed, valueWon, valueDrawn, valueLost, valueGoalFor, valueGoalAgainst, valueGoalDifference, valuePoints);
            list.add(l);
        }
        return list;        
    }

    @Override
    public List<Teams> listTeams(String league) {
        String index = null;
        if ("ไทยลีก".equals(league)) {
            index = "present_teams_thaipremierleague";
        } else if ("พรีเมียร์ลีก".equals(league)) {
            index = "present_teams_premierleague";
        }
        String teamValue = elasticsearch.searchAllTeam(index);
        List<Teams> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(teamValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            String team = obj_source.getString("team");
            String teamImg = obj_source.getString("logo_team");
            Teams t = new Teams(team, teamImg);
            list.add(t);
        }
        return list;
    }

    @Override
    public List<TeamsDetail> listTeamsDetail(String league, String team) {
        String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "present_teams_detail_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "present_teams_detail_premierleague";
        }
        String teamId = null;
        try{
            teamId = md5.encrypt(team);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        String teamDetailValue = elasticsearch.searchAllPlayersOfTeam(index, teamId);
        List<TeamsDetail> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(teamDetailValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            JSONArray arrPlayers = obj_source.getJSONArray("players");
            for (int j = 0; j < arrPlayers.length(); j++) {
               String squad_nember = arrPlayers.getJSONObject(j).getString("squad_nember"); 
               String img_player = arrPlayers.getJSONObject(j).getString("img_player"); 
               String player_name = arrPlayers.getJSONObject(j).getString("player_name"); 
               String local_position = arrPlayers.getJSONObject(j).getString("position"); 
               TeamsDetail t = new TeamsDetail(squad_nember, img_player, player_name, local_position);
               list.add(t);
            }
        }
        return list;
    }

    @Override
    public List<TeamsAndLogo> listTeamsAndLogo(String league, String team) {
        String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "present_teams_detail_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "present_teams_detail_premierleague";
        }
        String teamId = null;
        try{
            teamId = md5.encrypt(team);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        String teamDetailValue = elasticsearch.searchAllPlayersOfTeam(index, teamId);
        List<TeamsAndLogo> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(teamDetailValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            JSONArray arrPlayers = obj_source.getJSONArray("players");
            for (int j = 0; j < 1; j++) {  // เอาข้อมูลเพียงแค่ชุดเดียว
               String player_team = arrPlayers.getJSONObject(j).getString("player_team"); 
               String player_logo_team = arrPlayers.getJSONObject(j).getString("player_logo_team"); 
               TeamsAndLogo t = new TeamsAndLogo(player_team, player_logo_team);
               list.add(t);
            }
        }
        return list;
    }

    @Override
    public List<PlayerDetail> listPlayerDetail(String league, String player) {
        String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "present_players_detail_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "present_players_detail_premierleague";
        }
        String playerId = null;
        List<PlayerDetail> list = new ArrayList<>();
        
        try{
        playerId = md5.encrypt(player);
        String playerDetailValue = elasticsearch.searchPlayerDetail(index, playerId);
        JSONObject objResultsValue = new JSONObject(playerDetailValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            String l_img_player = obj_source.getString("img_player");
            String l_player_name = obj_source.getString("player_name");
            String l_birthday = obj_source.getString("birthday");
            String l_nationality = obj_source.getString("nationality");
            String l_height = obj_source.getString("height");
            String l_position = obj_source.getString("position");
            String l_link_performance_detail = obj_source.getString("link_performance_detail");
            PlayerDetail p = new PlayerDetail(l_img_player, l_player_name, l_birthday, l_nationality,
                                              l_height, l_position, l_link_performance_detail);
            list.add(p);
        }             
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<PerformanceDetailPk> listPerformanceDetailPk(String league, String player) {
       String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "present_players_detail_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "present_players_detail_premierleague";
        }
        String playerId = null;
        try{
            playerId = md5.encrypt(player);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        String performanceDetailValue = elasticsearch.searchPerformanceDetail(index, playerId);
        List<PerformanceDetailPk> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(performanceDetailValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            JSONArray arrPerDetail = obj_source.getJSONArray("performance_detail");
            for (int j = 0; j < arrPerDetail.length(); j++) { 
               String l_own_goals = arrPerDetail.getJSONObject(j).getString("own_goals"); 
               String l_yellow_red_cards = arrPerDetail.getJSONObject(j).getString("yellow_red_cards"); 
               String l_yellow_cards = arrPerDetail.getJSONObject(j).getString("yellow_cards"); 
               String l_red_cards = arrPerDetail.getJSONObject(j).getString("red_cards"); 
               String l_competition = arrPerDetail.getJSONObject(j).getString("competition"); 
               String l_matches = arrPerDetail.getJSONObject(j).getString("matches"); 
               String l_substituted_off = arrPerDetail.getJSONObject(j).getString("substituted_off"); 
               String l_minutes_played = arrPerDetail.getJSONObject(j).getString("minutes_played"); 
               String l_club = arrPerDetail.getJSONObject(j).getString("club"); 
               String l_season = arrPerDetail.getJSONObject(j).getString("season"); 
               String l_goals = arrPerDetail.getJSONObject(j).getString("goals"); 
               String l_substituted_on = arrPerDetail.getJSONObject(j).getString("substituted_on");   
               String l_goals_conceded = arrPerDetail.getJSONObject(j).getString("goals_conceded");
               String l_clean_sheets = arrPerDetail.getJSONObject(j).getString("clean_sheets");
               double l_average_statistics = arrPerDetail.getJSONObject(j).getDouble("average_statistics");
               PerformanceDetailPk p = new PerformanceDetailPk(l_own_goals, l_yellow_red_cards, l_yellow_cards, l_red_cards, l_competition, l_matches, l_substituted_off, l_minutes_played,
                                                               l_club, l_season, l_goals, l_substituted_on, l_goals_conceded, l_clean_sheets, l_average_statistics);
               list.add(p);
            }
        }
        return list;
    }

    @Override
    public List<PerformanceDetailOther> listPerformanceDetailOther(String league, String player) {
        String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "present_players_detail_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "present_players_detail_premierleague";
        }
        String playerId = null;
        try{
            playerId = md5.encrypt(player);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        String performanceDetailValue = elasticsearch.searchPerformanceDetail(index, playerId);
        List<PerformanceDetailOther> list = new ArrayList<>();
        JSONObject objResultsValue = new JSONObject(performanceDetailValue);
        JSONObject objHits = objResultsValue.getJSONObject("hits");
        JSONArray arrHits = objHits.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
            JSONArray arrPerDetail = obj_source.getJSONArray("performance_detail");
            for (int j = 0; j < arrPerDetail.length(); j++) { 
               String l_own_goals = arrPerDetail.getJSONObject(j).getString("own_goals"); 
               String l_yellow_red_cards = arrPerDetail.getJSONObject(j).getString("yellow_red_cards"); 
               String l_yellow_cards = arrPerDetail.getJSONObject(j).getString("yellow_cards"); 
               String l_red_cards = arrPerDetail.getJSONObject(j).getString("red_cards"); 
               String l_competition = arrPerDetail.getJSONObject(j).getString("competition"); 
               String l_matches = arrPerDetail.getJSONObject(j).getString("matches"); 
               String l_substituted_off = arrPerDetail.getJSONObject(j).getString("substituted_off"); 
               String l_minutes_played = arrPerDetail.getJSONObject(j).getString("minutes_played"); 
               String l_club = arrPerDetail.getJSONObject(j).getString("club"); 
               String l_season = arrPerDetail.getJSONObject(j).getString("season"); 
               String l_goals = arrPerDetail.getJSONObject(j).getString("goals"); 
               String l_substituted_on = arrPerDetail.getJSONObject(j).getString("substituted_on");
               String l_assists = arrPerDetail.getJSONObject(j).getString("assists");
               String l_penalty_goals = arrPerDetail.getJSONObject(j).getString("penalty_goals");
               String l_minutes_played_goals = arrPerDetail.getJSONObject(j).getString("minutes_played_goals");
               double l_average_statistics = arrPerDetail.getJSONObject(j).getDouble("average_statistics");
                
               PerformanceDetailOther p = new PerformanceDetailOther(l_own_goals, l_yellow_red_cards, l_yellow_cards, l_red_cards, l_competition, l_matches, l_substituted_off, l_minutes_played,
                                                                     l_club, l_season, l_goals, l_substituted_on, l_assists, l_penalty_goals, l_minutes_played_goals, l_average_statistics);
               list.add(p);
            }
        }
        return list;
    }

    @Override
    public String listAvgFormPresentTeam(String league, String homeAway) {
        List<String> listObj = new ArrayList<>();
        JSONObject json = new JSONObject();
        String[] parts = homeAway.split(" - ");
        String home = parts[0];
        String away = parts[1];

        String teamIndex = null;
        if ("thaipremierleague".equals(league)) {
            teamIndex = "present_teams_detail_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            teamIndex = "present_teams_detail_premierleague";
        }
        String playersIndex = null;
        if ("thaipremierleague".equals(league)) {
            playersIndex = "present_players_detail_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            playersIndex = "present_players_detail_premierleague";
        }
        // หา home avg ก่อน
        String valueHome = null; 
        try {
            valueHome = elasticsearch.searchAllPlayersOfTeam(teamIndex, md5.encrypt(home));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServiceMobileApiImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> listHomePlayers = listPlayerOfTeam(valueHome);
        List<Double> listHomeSize = new ArrayList<>();
        double sumHomeAvg = 0;
        // ดึงค่า avg ของนักเตะนักเตะทั้งหมดที่เก็บไว้ใน list
        for (String value : listHomePlayers) {
            String valuePlayer = elasticsearch.searchPlayerDetail(playersIndex, value);
            double avg = listPerformanceDetail(valuePlayer);
            if(avg != 0.0){   //กรณีนักเตะใดๆไม่มีค่าเฉลี่ยก็ไม่เอามาคำนวน
               listHomeSize.add(avg);  //เก็บ ใส่ list เพื่อดูจำนวนนักเตะทั้งหมด
            } 
            sumHomeAvg = sumHomeAvg + avg;  // รวมค่า avg ฟอร์มของนักเตะทุกๆคน
        }
        DecimalFormat df1 = new DecimalFormat("#.##");
        String homeLogo = logoTeam;        
        String homeAvg = df1.format(sumHomeAvg / listHomeSize.size());
       // หา away avg
        String valueAway = null; 
        try {
            valueAway = elasticsearch.searchAllPlayersOfTeam(teamIndex, md5.encrypt(away));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServiceMobileApiImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> listAwayPlayers = listPlayerOfTeam(valueAway);
        List<Double> listAwaySize = new ArrayList<>();
        double sumAwayAvg = 0;
        // ดึงค่า avg ของนักเตะนักเตะทั้งหมดที่เก็บไว้ใน list
        for (String value : listAwayPlayers) {
            String valuePlayer = elasticsearch.searchPlayerDetail(playersIndex, value);
            double avg = listPerformanceDetail(valuePlayer);
            if(avg != 0.0){   //กรณีนักเตะใดๆไม่มีค่าเฉลี่ยก็ไม่เอามาคำนวน
                listAwaySize.add(avg);  //เก็บ ใส่ list เพื่อดูจำนวนนักเตะทั้งหมด
            }
            sumAwayAvg = sumAwayAvg + avg;  // รวมค่า avg ฟอร์มของนักเตะทุกๆคน
        }
        DecimalFormat df2 = new DecimalFormat("#.##");
        String awayLogo = logoTeam;        
        String awayAvg = df2.format(sumAwayAvg / listAwaySize.size());
        json.put("season", season);
        json.put("home", home);
        json.put("away", away);
        json.put("home_logo", homeLogo);
        json.put("home_avg", homeAvg);
        json.put("away_logo", awayLogo);
        json.put("away_avg", awayAvg); 
        listObj.add(json.toString());
        return listObj.toString();
    }
    @Override
    public List<String> listPlayerOfTeam(String value) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject objResultsValue = new JSONObject(value);
            JSONObject objHits = objResultsValue.getJSONObject("hits");
            JSONArray arrHits = objHits.getJSONArray("hits");
            for (int i = 0; i < arrHits.length(); i++) {
                JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
                logoTeam = obj_source.getString("logo_team");
                season = obj_source.getString("season");
                JSONArray arrPlayers = obj_source.getJSONArray("players");
                for (int j = 0; j < arrPlayers.length(); j++) {
                    String player_name_id = arrPlayers.getJSONObject(j).getString("player_name_id");
                    list.add(player_name_id);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Double listPerformanceDetail(String valuePlayers) {
        double value = 0;
        try {
            JSONObject objResultsValue = new JSONObject(valuePlayers);
            JSONObject objHits = objResultsValue.getJSONObject("hits");
            JSONArray arrHits = objHits.getJSONArray("hits");
            for (int i = 0; i < arrHits.length(); i++) {
                JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
                JSONArray arrPerDetail = obj_source.getJSONArray("performance_detail");
                for (int j = 0; j < 1; j++) {
                    double l_average_statistics = arrPerDetail.getJSONObject(j).getDouble("average_statistics");
                    value = l_average_statistics;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return value;
    }  

    @Override
    public String listScoreAnalyze(String league, String date, String homeAway) {
        String index = null;
        if ("thaipremierleague".equals(league)) {
            index = "score_analyze_thaipremierleague";
        } else if ("premierleague".equals(league)) {
            index = "score_analyze_premierleague";
        }  
        String valueScoreAnalyze = null; 
        try {
            valueScoreAnalyze = elasticsearch.searchScoreAnalyze(index, date, md5.encrypt(homeAway));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServiceMobileApiImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> list = new ArrayList<>();
        try {
            JSONObject objResultsValue = new JSONObject(valueScoreAnalyze);
            JSONObject objHits = objResultsValue.getJSONObject("hits");
            JSONArray arrHits = objHits.getJSONArray("hits");
            for (int i = 0; i < arrHits.length(); i++) {
                JSONObject obj_source = arrHits.getJSONObject(i).getJSONObject("_source");
                String score_analyze = obj_source.getString("score_analyze");
                String content = obj_source.getString("content");
                JSONObject json = new JSONObject();
                json.put("score_analyze", score_analyze);
                json.put("content", content);
                list.add(json.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list.toString();       
    }
}
