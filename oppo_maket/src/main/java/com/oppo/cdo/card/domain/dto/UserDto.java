package com.oppo.cdo.card.domain.dto;

import io.protostuff.Tag;

public class UserDto {
    @Tag(3)
    private String avatar;
    @Tag(1)
    private String id;
    @Tag(2)
    private String nickName;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String str) {
        this.nickName = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }
}