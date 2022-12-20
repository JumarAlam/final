package com.cosc516;


public class GameState {
    private Integer stateid;
    private String statetime;
    private Integer region;
    private String name;
    private String email;
    private Integer gold;
    private Integer power;
    private Integer level;


    public GameState() {
    }

    public GameState(Integer stateid, String statetime, Integer region, String name, String email, Integer gold, Integer power, Integer level) {
        this.stateid = stateid;
        this.statetime = statetime;
        this.region = region;
        this.name = name;
        this.email = email;
        this.gold = gold;
        this.power = power;
        this.level = level;
    }

    public Integer getStateid() {
        return stateid;
    }

    public void setStateid(Integer stateid) {
        this.stateid = stateid;
    }

    public String getStatetime() {
        return statetime;
    }

    public void setStatetime(String statetime) {
        this.statetime = statetime;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


}