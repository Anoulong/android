/*******************************************************************************
 * QuickSeries® Publishing inc.
 *
 *
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 *
 *
 * Created by Anou Chanthavong on 2018-01-29.
 */
package com.anou.prototype.yoga.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

import com.google.gson.annotations.SerializedName


@Entity(tableName = "Module")
data class ModuleEntity(
    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    @get:NonNull
    var id: String,
    @SerializedName("app_eid")
    var appEid: String? = null,
    var eid: String? = null,
    var title: String? = null,
    var slug: String? = null,
    var type: String? = null,
    var description: String? = null,
    var isActive: Boolean = false,
    var isGeocode: Boolean = false,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    var content: String? = null,
    var position: Int = 0
) {


    enum class ModuleType(val type: String) {
        TEXT_TYPE("text"),
        ABOUT("about-us"),
        HOME("home"),
        QUIZ("scored-assessment"),
        NEWS("news"),
        RESOURCES("resources"),
        LIBRARY("library"),
        CHECKLIST("checklists"),
        VIDEOS("videos"),
        PDF("pdfs"),
        FAQ("faq"),
        REPORTING("reporting"),
        CATEGORY("category"),
        PUBLIC_USER("public-user-registration"),
        PRIVATE_USER("private-user-registration"),
        EVENTS("events"),
        ACCESS_CODE("access-code")
    }
}