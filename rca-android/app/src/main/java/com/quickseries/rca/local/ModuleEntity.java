package com.quickseries.rca.local;

import java.util.Comparator;

/*******************************************************************************
 * QuickSeries® Publishing inc.
 * <p>
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 * <p>
 * Created by Anou Chanthavong on 2017-12-04.
 ******************************************************************************/
public class ModuleEntity {
    String id;
    String appEid;
    String eid;
    String title;
    String description;
    String type;
    boolean active;
    String createdAt;
    String updatedAt;

    public ModuleEntity(String type, String title ) {
        this.title = title;
        this.type = type;
    }

    public ModuleEntity(String type, String title, String eid ) {
        this.title = title;
        this.type = type;
        this.eid = eid;
    }

    public ModuleEntity() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppEid() {
        return appEid;
    }

    public void setAppEid(String appEid) {
        this.appEid = appEid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Comparator<ModuleEntity> moduleComparatorAToZ = new Comparator<ModuleEntity>() {
        @Override
        public int compare(ModuleEntity moduleEntity1, ModuleEntity moduleEntity2) {
            return moduleEntity1.getTitle().compareTo(moduleEntity2.getTitle());
        }
    };

    @Override
    public String toString() {
        return "ModuleEntity{" +
                "id='" + id + '\'' +
                ", appEid='" + appEid + '\'' +
                ", eid='" + eid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", active=" + active +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}