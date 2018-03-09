package com.oppo.cdo.card.domain.dto;

import io.protostuff.Tag;
import java.util.List;

public class NavCardDto extends CardDto {
    @Tag(101)
    private List<BannerDto> banners;
    @Tag(102)
    private List<BannerDto> items;
    @Tag(103)
    private BannerDto notice;

    public List<BannerDto> getBanners() {
        return this.banners;
    }

    public void setBanners(List<BannerDto> list) {
        this.banners = list;
    }

    public List<BannerDto> getItems() {
        return this.items;
    }

    public void setItems(List<BannerDto> list) {
        this.items = list;
    }

    public BannerDto getNotice() {
        return this.notice;
    }

    public void setNotice(BannerDto bannerDto) {
        this.notice = bannerDto;
    }
}