package com.oppo.cdo.update.domain.dto;

import com.example.oppo.UpgradeReq;

import java.util.List;

import io.protostuff.Tag;

public class UpgradeWrapReq {
    @Tag(1)
    private List<UpgradeReq> upgrades;

    public List<UpgradeReq> getUpgrades() {
        return this.upgrades;
    }

    public void setUpgrades(List<UpgradeReq> list) {
        this.upgrades = list;
    }
}