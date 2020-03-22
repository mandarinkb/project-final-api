package com.projectfinalapi.model;

public class TeamsAndLogo {
    private String player_team;
    private String player_logo_team;

    public TeamsAndLogo() {
    }

    public TeamsAndLogo(String player_team, String player_logo_team) {
        this.player_team = player_team;
        this.player_logo_team = player_logo_team;
    }

    @Override
    public String toString() {
        return "TeamsAndLogo{" + "player_team=" + player_team + ", player_logo_team=" + player_logo_team + '}';
    }

    public String getPlayer_team() {
        return player_team;
    }

    public void setPlayer_team(String player_team) {
        this.player_team = player_team;
    }

    public String getPlayer_logo_team() {
        return player_logo_team;
    }

    public void setPlayer_logo_team(String player_logo_team) {
        this.player_logo_team = player_logo_team;
    }
}
