package com.projectfinalapi.model;

public class LeagueTable {
    private String ranking;
    private String team;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goal_for;
    private int goal_against;
    private String goal_difference;
    private int points;

    public LeagueTable() {
    }

    public LeagueTable(String ranking, String team, int played, int won, int drawn, int lost, int goal_for, int goal_against, String goal_difference, int points) {
        this.ranking = ranking;
        this.team = team;
        this.played = played;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goal_for = goal_for;
        this.goal_against = goal_against;
        this.goal_difference = goal_difference;
        this.points = points;
    }

    @Override
    public String toString() {
        return "LeagueTable{" + "ranking=" + ranking + ", team=" + team + ", played=" + played + ", won=" + won + ", drawn=" + drawn + ", lost=" + lost + ", goal_for=" + goal_for + ", goal_against=" + goal_against + ", goal_difference=" + goal_difference + ", points=" + points + '}';
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getGoal_for() {
        return goal_for;
    }

    public void setGoal_for(int goal_for) {
        this.goal_for = goal_for;
    }

    public int getGoal_against() {
        return goal_against;
    }

    public void setGoal_against(int goal_against) {
        this.goal_against = goal_against;
    }

    public String getGoal_difference() {
        return goal_difference;
    }

    public void setGoal_difference(String goal_difference) {
        this.goal_difference = goal_difference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
      
}
