package com.oppo.cdo.card.domain.dto;

import io.protostuff.Tag;
import java.util.List;

public class ViewLayerWrapDto {
    @Tag(3)
    private List<Object> cards;
    @Tag(1)
    private int isEnd;
    @Tag(2)
    private String title;

    public int getIsEnd() {
        return this.isEnd;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setIsEnd(int i) {
        this.isEnd = i;
    }

    public List<Object> getCards() {
        return this.cards;
    }

    public void setCards(List<Object> list) {
        this.cards = list;
    }

    public String toString() {
        return "ViewLayerWrapDto{isEnd=" + this.isEnd + ", title='" + this.title + '\'' + ", cards=" + this.cards + '}';
    }
}