package com.projectfinalapi.model;

public class ResulesHomeAway {
    private String dateThai;
    private String homeImg;
    private String homeScore;
    private String awayImg;
    private String awayScore; 
    private String home; 
    private String away; 

    public ResulesHomeAway() {
    }

    public ResulesHomeAway(String dateThai, String homeImg, String homeScore, String awayImg, String awayScore, String home, String away) {
        this.dateThai = dateThai;
        this.homeImg = homeImg;
        this.homeScore = homeScore;
        this.awayImg = awayImg;
        this.awayScore = awayScore;
        this.home = home;
        this.away = away;
    }

    @Override
    public String toString() {
        return "ResulesHomeAway{" + "dateThai=" + dateThai + ", homeImg=" + homeImg + ", homeScore=" + homeScore + ", awayImg=" + awayImg + ", awayScore=" + awayScore + ", home=" + home + ", away=" + away + '}';
    }

    public String getDateThai() {
        return dateThai;
    }

    public void setDateThai(String dateThai) {
        this.dateThai = dateThai;
    }

    public String getHomeImg() {
        return homeImg;
    }

    public void setHomeImg(String homeImg) {
        this.homeImg = homeImg;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayImg() {
        return awayImg;
    }

    public void setAwayImg(String awayImg) {
        this.awayImg = awayImg;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

}

