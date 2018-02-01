package com.oppo.cdo.card.domain.dto;

import io.protostuff.Tag;

public class BannerDto {
    @Tag(4)
    private String actionParam;
    @Tag(3)
    private String actionType;
    @Tag(6)
    private String desc;
    @Tag(1)
    private int id;
    @Tag(2)
    private String image;
    @Tag(7)
    private long time;
    @Tag(5)
    private String title;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String str) {
        this.actionType = str;
    }

    public String getActionParam() {
        return this.actionParam;
    }

    public void setActionParam(String str) {
        this.actionParam = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long j) {
        this.time = j;
    }
}