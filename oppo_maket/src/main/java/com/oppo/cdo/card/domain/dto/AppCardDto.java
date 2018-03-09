package com.oppo.cdo.card.domain.dto;

/**
 * Created by zk on 2018/2/5.
 */
import com.oppo.cdo.common.domain.dto.ResourceDto;
import io.protostuff.Tag;

public class AppCardDto extends CardDto {
    @Tag(103)
    private ResourceDto app;
    @Tag(102)
    private String desc;
    @Tag(101)
    private String title;

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

    public ResourceDto getApp() {
        return this.app;
    }

    public void setApp(ResourceDto resourceDto) {
        this.app = resourceDto;
    }
}