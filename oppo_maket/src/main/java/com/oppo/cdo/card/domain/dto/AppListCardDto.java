package com.oppo.cdo.card.domain.dto;

import com.oppo.cdo.common.domain.dto.ResourceDto;
import io.protostuff.Tag;
import java.util.List;

public class AppListCardDto extends CardDto {
    @Tag(104)
    private String appContextPath;
    @Tag(103)
    private List<ResourceDto> apps;
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

    public List<ResourceDto> getApps() {
        return this.apps;
    }

    public void setApps(List<ResourceDto> list) {
        this.apps = list;
    }

    public String getAppContextPath() {
        return this.appContextPath;
    }

    public void setAppContextPath(String str) {
        this.appContextPath = str;
    }


    @Override
    public String toString() {
        return "AppListCardDto{" +
                "appContextPath='" + appContextPath + '\'' +
                ", apps=" + apps +
                ", desc='" + desc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}