package com.oppo.cdo.searchx.domain.dto;

import com.oppo.cdo.card.domain.dto.ViewLayerWrapDto;
import io.protostuff.Tag;

public class SearchResultWrapDto extends ViewLayerWrapDto {
    @Tag(13)
    private int end;
    @Tag(14)
    private String recList;
    @Tag(15)
    private String searchTip;
    @Tag(12)
    private int start;
    @Tag(11)
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int i) {
        this.total = i;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int i) {
        this.start = i;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int i) {
        this.end = i;
    }

    public String getRecList() {
        return this.recList;
    }

    public void setRecList(String str) {
        this.recList = str;
    }

    public String getSearchTip() {
        return this.searchTip;
    }

    public void setSearchTip(String str) {
        this.searchTip = str;
    }

    public String toString() {
        return "SearchResultListDto{total=" + this.total + ", start=" + this.start + ", end=" + this.end + ", recList='" + this.recList + '\'' + '}';
    }
}