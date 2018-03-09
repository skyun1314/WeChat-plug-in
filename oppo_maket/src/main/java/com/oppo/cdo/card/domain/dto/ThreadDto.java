package com.oppo.cdo.card.domain.dto;

import io.protostuff.Tag;

public class ThreadDto {
    @Tag(6)
    private String content;
    @Tag(10)
    private String detailUrl;
    @Tag(2)
    private long id;
    @Tag(9)
    private int label;
    @Tag(5)
    private long publishedTime;
    @Tag(8)
    private long pv;
    @Tag(3)
    private String tag;
    @Tag(4)
    private String title;
    @Tag(7)
    private int type;
    @Tag(1)
    private UserDto user;

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(UserDto userDto) {
        this.user = userDto;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public long getPublishedTime() {
        return this.publishedTime;
    }

    public void setPublishedTime(long j) {
        this.publishedTime = j;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public long getPv() {
        return this.pv;
    }

    public void setPv(long j) {
        this.pv = j;
    }

    public int getLabel() {
        return this.label;
    }

    public void setLabel(int i) {
        this.label = i;
    }

    public String getDetailUrl() {
        return this.detailUrl;
    }

    public void setDetailUrl(String str) {
        this.detailUrl = str;
    }
}