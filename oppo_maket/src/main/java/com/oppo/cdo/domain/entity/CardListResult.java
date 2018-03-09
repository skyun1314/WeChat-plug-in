package com.oppo.cdo.domain.entity;

import com.oppo.cdo.card.domain.dto.ViewLayerWrapDto;

public class CardListResult {
    private Status a;
    private ViewLayerWrapDto b;
    private int c;
    private boolean d;

    public enum Status {
        OK,
        NO_MORE,
        ERROR
    }

    public void a(ViewLayerWrapDto viewLayerWrapDto, int i, int i2) {
        this.b = viewLayerWrapDto;
        if (this.b != null) {
            this.c = i + i2;
        }
    }

    public ViewLayerWrapDto a() {
        return this.b;
    }

    public Status b() {
        return this.a;
    }

    public void a(Status status) {
        this.a = status;
    }

    public int c() {
        return this.c;
    }

    public void d() {
        this.d = true;
    }

    public boolean e() {
        return this.d;
    }
}