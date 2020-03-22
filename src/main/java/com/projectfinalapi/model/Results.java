package com.projectfinalapi.model;

public class Results {
    private String homeTeam;
    private String homeImg;
    private int homeScore;
    private String awayTeam;
    private String awayImg;
    private int awayScore;    

    public Results() {
    }

    public Results(String homeTeam, String homeImg, int homeScore, String awayTeam, String awayImg, int awayScore) {
        this.homeTeam = homeTeam;
        this.homeImg = homeImg;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.awayImg = awayImg;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getHomeImg() {
        return homeImg;
    }

    public void setHomeImg(String homeImg) {
        this.homeImg = homeImg;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getAwayImg() {
        return awayImg;
    }

    public void setAwayImg(String awayImg) {
        this.awayImg = awayImg;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public String toString() {
        return "Results{" + "homeTeam=" + homeTeam + ", homeImg=" + homeImg + ", homeScore=" + homeScore + ", awayTeam=" + awayTeam + ", awayImg=" + awayImg + ", awayScore=" + awayScore + '}';
    }
}
