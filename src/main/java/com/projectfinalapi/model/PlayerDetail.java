package com.projectfinalapi.model;

public class PlayerDetail {
    private String img_player;
    private String player_name;
    private String birthday;
    private String nationality;
    private String height;
    private String position;
    private String link_performance_detail;

    public PlayerDetail() {
    }

    public PlayerDetail(String img_player, String player_name, String birthday, String nationality, String height, String position, String link_performance_detail) {
        this.img_player = img_player;
        this.player_name = player_name;
        this.birthday = birthday;
        this.nationality = nationality;
        this.height = height;
        this.position = position;
        this.link_performance_detail = link_performance_detail;
    }

    @Override
    public String toString() {
        return "PlayerDetail{" + "img_player=" + img_player + ", player_name=" + player_name + ", birthday=" + birthday + ", nationality=" + nationality + ", height=" + height + ", position=" + position + ", link_performance_detail=" + link_performance_detail + '}';
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLink_performance_detail() {
        return link_performance_detail;
    }

    public void setLink_performance_detail(String link_performance_detail) {
        this.link_performance_detail = link_performance_detail;
    }
    
}
