package com.projectfinalapi.model;

//กรณีผู้รักษาประตู
public class PerformanceDetailPk {

private String own_goals;
private String yellow_red_cards;
private String yellow_cards;
private String red_cards;
private String competition;
private String matches;
private String substituted_off;
private String minutes_played;
private String club;
private String season;
private String goals;
private String substituted_on;
private String goals_conceded;
private String clean_sheets;
private double average_statistics;

public PerformanceDetailPk() {
}

public PerformanceDetailPk(String own_goals, String yellow_red_cards, String yellow_cards, String red_cards, String competition, String matches, String substituted_off, String minutes_played, String club, String season, String goals, String substituted_on, String goals_conceded, String clean_sheets, double average_statistics) {
   this.own_goals = own_goals;
   this.yellow_red_cards = yellow_red_cards;
   this.yellow_cards = yellow_cards;
   this.red_cards = red_cards;
   this.competition = competition;
   this.matches = matches;
   this.substituted_off = substituted_off;
   this.minutes_played = minutes_played;
   this.club = club;
   this.season = season;
   this.goals = goals;
   this.substituted_on = substituted_on;
   this.goals_conceded = goals_conceded;
   this.clean_sheets = clean_sheets;
   this.average_statistics = average_statistics;
}

@Override
public String toString() {
   return "PerformanceDetailPk{" + "own_goals=" + own_goals + ", yellow_red_cards=" + yellow_red_cards + ", yellow_cards=" + yellow_cards + ", red_cards=" + red_cards + ", competition=" + competition + ", matches=" + matches + ", substituted_off=" + substituted_off + ", minutes_played=" + minutes_played + ", club=" + club + ", season=" + season + ", goals=" + goals + ", substituted_on=" + substituted_on + ", goals_conceded=" + goals_conceded + ", clean_sheets=" + clean_sheets + ", average_statistics=" + average_statistics + '}';
}

public String getOwn_goals() {
   return own_goals;
}

public void setOwn_goals(String own_goals) {
   this.own_goals = own_goals;
}

public String getYellow_red_cards() {
   return yellow_red_cards;
}

public void setYellow_red_cards(String yellow_red_cards) {
   this.yellow_red_cards = yellow_red_cards;
}

public String getYellow_cards() {
   return yellow_cards;
}

public void setYellow_cards(String yellow_cards) {
   this.yellow_cards = yellow_cards;
}

public String getRed_cards() {
   return red_cards;
}

public void setRed_cards(String red_cards) {
   this.red_cards = red_cards;
}

public String getCompetition() {
   return competition;
}

public void setCompetition(String competition) {
   this.competition = competition;
}

public String getMatches() {
   return matches;
}

public void setMatches(String matches) {
   this.matches = matches;
}

public String getSubstituted_off() {
   return substituted_off;
}

public void setSubstituted_off(String substituted_off) {
   this.substituted_off = substituted_off;
}

public String getMinutes_played() {
   return minutes_played;
}

public void setMinutes_played(String minutes_played) {
   this.minutes_played = minutes_played;
}

public String getClub() {
   return club;
}

public void setClub(String club) {
   this.club = club;
}

public String getSeason() {
   return season;
}

public void setSeason(String season) {
   this.season = season;
}

public String getGoals() {
   return goals;
}

public void setGoals(String goals) {
   this.goals = goals;
}

public String getSubstituted_on() {
   return substituted_on;
}

public void setSubstituted_on(String substituted_on) {
   this.substituted_on = substituted_on;
}

public String getGoals_conceded() {
   return goals_conceded;
}

public void setGoals_conceded(String goals_conceded) {
   this.goals_conceded = goals_conceded;
}

public String getClean_sheets() {
   return clean_sheets;
}

public void setClean_sheets(String clean_sheets) {
   this.clean_sheets = clean_sheets;
}

public double getAverage_statistics() {
   return average_statistics;
}

public void setAverage_statistics(double average_statistics) {
   this.average_statistics = average_statistics;
}

}
