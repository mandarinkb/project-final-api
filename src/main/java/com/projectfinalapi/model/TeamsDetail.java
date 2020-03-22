package com.projectfinalapi.model;

public class TeamsDetail {
    private String squad_nember;
    private String img_player;
    private String player_name;
    private String position;

    public TeamsDetail() {
    }

    public TeamsDetail(String squad_nember, String img_player, String player_name, String position) {
        this.squad_nember = squad_nember;
        this.img_player = img_player;
        this.player_name = player_name;
        this.position = position;
    }

    @Override
    public String toString() {
        return "TeamsDetail{" + "squad_nember=" + squad_nember + ", img_player=" + img_player + ", player_name=" + player_name + ", position=" + position + '}';
    }

    public String getSquad_nember() {
        return squad_nember;
    }

    public void setSquad_nember(String squad_nember) {
        this.squad_nember = squad_nember;
    }

    public String getImg_player() {
        return img_player;
    }

    public void setImg_player(String img_player) {
        this.img_player = img_player;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
