package com.projectfinalapi.model;

public class Teams {
    private String team;
    private String teamImg;

    public Teams() {
    }

    public Teams(String team, String teamImg) {
        this.team = team;
        this.teamImg = teamImg;
    }

    @Override
    public String toString() {
        return "Teams{" + "team=" + team + ", teamImg=" + teamImg + '}';
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamImg() {
        return teamImg;
    }

    public void setTeamImg(String teamImg) {
        this.teamImg = teamImg;
    }
    
}
