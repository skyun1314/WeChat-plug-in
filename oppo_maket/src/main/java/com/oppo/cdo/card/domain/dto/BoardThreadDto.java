package com.oppo.cdo.card.domain.dto;

/**
 * Created by zk on 2018/2/5.
 */

import io.protostuff.Tag;

public class BoardThreadDto {
    @Tag(3)
    private String actionParam;
    @Tag(1)
    private long id;
    @Tag(2)
    private String name;
    @Tag(4)
    private ThreadDto thread;

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getActionParam() {
        return this.actionParam;
    }

    public void setActionParam(String str) {
        this.actionParam = str;
    }

    public ThreadDto getThread() {
        return this.thread;
    }

    public void setThread(ThreadDto threadDto) {
        this.thread = threadDto;
    }
}