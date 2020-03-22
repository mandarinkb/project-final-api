package com.projectfinalapi.service;

import java.util.List;

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


public interface ServiceMobileApi {
    List<Results> listResults(String league,String date);
    List<Fixtures> listFixtures(String league,String date);
    List<ResulesHomeAway> listResultsHomeAway(String league,String homeAway);
    List<LeagueTable> listLeagueTable(String league,String typeTable);
    List<Teams> listTeams(String league);
    List<TeamsDetail> listTeamsDetail(String league ,String team);
    List<TeamsAndLogo> listTeamsAndLogo(String league ,String team);
    List<PlayerDetail> listPlayerDetail(String league ,String player);
    List<PerformanceDetailPk> listPerformanceDetailPk(String league ,String player);        //กรณีผู้รักษาประตู
    List<PerformanceDetailOther> listPerformanceDetailOther(String league ,String player); //กรณีผู้เล่นอื่นๆ
    
    String listAvgFormPresentTeam(String league, String homeAway);  // ค่าเฉลี่ย ฟอร์มทีมปัจจุบัน
    List<String> listPlayerOfTeam(String value);
    Double listPerformanceDetail(String value);
    
    String listScoreAnalyze(String league, String date, String homeAway);
}
