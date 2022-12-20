package com.cosc516;

import java.util.Date;

public class GameEvent {
    private Integer eventid ;
    private Integer userid ;
    private Date eventtime ;
    private Integer type ;
    private Integer diffgold ;
    private Integer diffpower ;
    private Integer difflevel ;
    private Integer count;

    public GameEvent() {
    }

    public GameEvent(Integer eventid, Integer userid, Date eventtime, Integer type, Integer diffgold, Integer diffpower, Integer difflevel, Integer count) {
        this.eventid = eventid;
        this.userid = userid;
        this.eventtime = eventtime;
        this.type = type;
        this.diffgold = diffgold;
        this.diffpower = diffpower;
        this.difflevel = difflevel;
        this.count = count;
    }

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getEventtime() {
        return eventtime;
    }

    public void setEventtime(Date eventtime) {
        this.eventtime = eventtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDiffgold() {
        return diffgold;
    }

    public void setDiffgold(Integer diffgold) {
        this.diffgold = diffgold;
    }

    public Integer getDiffpower() {
        return diffpower;
    }

    public void setDiffpower(Integer diffpower) {
        this.diffpower = diffpower;
    }

    public Integer getDifflevel() {
        return difflevel;
    }

    public void setDifflevel(Integer difflevel) {
        this.difflevel = difflevel;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}