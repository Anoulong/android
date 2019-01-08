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
package com.anou.prototype.core.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

import com.google.gson.annotations.SerializedName


@Entity(tableName = "Module")
class ModuleEntity {

    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    @get:NonNull
    var id: String? = null
    @SerializedName("app_eid")
    var appEid: String? = null
    var eid: String? = null
    var title: String? = null
    var slug: String? = null
    var type: String? = null
    var description: String? = null
    var isActive: Boolean = false
    var isGeocode = false
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
    var content: String? = null
    var position: Int = 0

    companion object {

        val TEXT_TYPE = "text"
        val ABOUT = "about-us"
        val HOME = "home"
        val QUIZ = "scored-assessment"
        val NEWS = "news"
        val RESOURCES = "resources"
        val LIBRARY = "library"
        val CHECKLIST = "checklists"
        val VIDEOS = "videos"
        val PDF = "pdfs"
        val FAQ = "faq"
        val REPORTING = "reporting"
        val CATEGORY = "category"
        val PUBLIC_USER = "public-user-registration"
        val PRIVATE_USER = "private-user-registration"
        val EVENTS = "events"
        val ACCESS_CODE = "access-code"
    }
}