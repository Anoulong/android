package com.anou.prototype.yoga.db.news


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

import java.io.Serializable

@Entity(tableName = "News")
class NewsEntity(
    @PrimaryKey
    @SerializedName("_id")
    var id: String,
    @ColumnInfo(index = true)
    @SerializedName("custom_module_eid")
    var customModuleEid: String? = null,
    var slug: String? = null,
    var eid: String? = null,
    var path: String? = null,
    @Embedded
    var title: Title? = null,
    @Embedded
    var photo: Photo? = null,
    @Embedded
    @SerializedName("photo_credit")
    var photoCredit: PhotoCredit? = null,
    @Embedded
    var caption: Caption? = null,
    @Embedded
    var body: Body? = null,
    @Embedded
    var subtitle: Subtitle? = null,
    @SerializedName("author_eid")
    var authorEid: String? = null,
    @Embedded
    var fields: Fields? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @ColumnInfo(index = true)
    var status: String? = null ) {


    //region Embedded Classes
    @Entity
    class Title {
        @ColumnInfo(name = "title_en")
        var en: String? = null
        @ColumnInfo(name = "title_es")
        var es: String? = null

    }

    @Entity
    class Photo {
        @ColumnInfo(name = "photo_es")
        var es: String? = null
        @ColumnInfo(name = "photo_en")
        var en: String? = null

    }

    @Entity
    class PhotoCredit {
        @ColumnInfo(name = "photoCredit")
        var en: String? = null
    }

    @Entity
    class Caption {
        @ColumnInfo(name = "caption_en")
        var en: String? = null
    }

    @Entity
    class Body {
        @ColumnInfo(name = "body_en")
        var en: String? = null
    }

    @Entity
    class Fields {
        @ColumnInfo(name = "fields_tag")
        var tag: String? = null
    }

    @Entity
    class Subtitle {
        @ColumnInfo(name = "subtitle_en")
        var en: String? = null
    }

    //endregion

}
