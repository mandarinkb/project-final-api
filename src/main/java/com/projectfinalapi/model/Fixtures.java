package com.projectfinalapi.model;

public class Fixtures {
    private String homeTeam;
    private String homeImg;
    private String awayTeam;
    private String awayImg;
    private String time;

    public Fixtures() {
    }

    public Fixtures(String homeTeam, String homeImg, String awayTeam, String awayImg, String time) {
        this.homeTeam = homeTeam;
        this.homeImg = homeImg;
        this.awayTeam = awayTeam;
        this.awayImg = awayImg;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Fixtures{" + "homeTeam=" + homeTeam + ", homeImg=" + homeImg + ", awayTeam=" + awayTeam + ", awayImg=" + awayImg + ", time=" + time + '}';
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
      
}
