package com.oppo.cdo.card.domain.dto;

import com.oppo.cdo.common.domain.dto.ResourceDto;
import io.protostuff.Tag;
import java.util.List;

public class BannerCardDto extends CardDto {
    @Tag(104)
    private List<ResourceDto> apps;
    @Tag(103)
    private List<BannerDto> banners;
    @Tag(106)
    private int clickCount;
    @Tag(102)
    private String desc;
    @Tag(107)
    private int exposeCount;
    @Tag(105)
    private String fileUrl;
    @Tag(108)
    private String identifier;
    @Tag(101)
    private String title;

    public int getClickCount() {
        return this.clickCount;
    }

    public void setClickCount(int i) {
        this.clickCount = i;
    }

    public int getExposeCount() {
        return this.exposeCount;
    }

    public void setExposeCount(int i) {
        this.exposeCount = i;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String str) {
        this.identifier = str;
    }

    public List<BannerDto> getBanners() {
        return this.banners;
    }

    public void setBanners(List<BannerDto> list) {
        this.banners = list;
    }

    public List<ResourceDto> getApps() {
        return this.apps;
    }

    public void setApps(List<ResourceDto> list) {
        this.apps = list;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}