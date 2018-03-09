package com.oppo.cdo.card.domain.dto.search;

import com.oppo.cdo.card.domain.dto.CardDto;
import com.oppo.cdo.common.domain.dto.ResourceDto;
import io.protostuff.Tag;

public class SearchCardDto extends CardDto {
    @Tag(100)
    private ResourceDto app;
    @Tag(101)
    private String downRate;
    @Tag(102)
    private String pic1;
    @Tag(103)
    private String pic2;
    @Tag(104)
    private String pic3;
    @Tag(105)
    private String pic4;
    @Tag(106)
    private String pic5;

    public ResourceDto getApp() {
        return this.app;
    }

    public void setApp(ResourceDto resourceDto) {
        this.app = resourceDto;
    }

    public String getDownRate() {
        return this.downRate;
    }

    public void setDownRate(String str) {
        this.downRate = str;
    }

    public String getPic1() {
        return this.pic1;
    }

    public void setPic1(String str) {
        this.pic1 = str;
    }

    public String getPic2() {
        return this.pic2;
    }

    public void setPic2(String str) {
        this.pic2 = str;
    }

    public String getPic3() {
        return this.pic3;
    }

    public void setPic3(String str) {
        this.pic3 = str;
    }

    public String getPic4() {
        return this.pic4;
    }

    public void setPic4(String str) {
        this.pic4 = str;
    }

    public String getPic5() {
        return this.pic5;
    }

    public void setPic5(String str) {
        this.pic5 = str;
    }
}