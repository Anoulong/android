package com.anou.prototype.yoga.db.faq

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Faq")
data class FaqEntity(
        @PrimaryKey
        @NonNull
        @SerializedName("_id")
        var id: String,
        @ColumnInfo(index = true)
        @SerializedName("custom_module_eid")
        var moduleEid: String?,
        var slug: String?,
        var eid: String?,
        var title: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("created_at")
        var createdAt: String?
) : Parcelable, Comparable<FaqEntity> {

    override fun compareTo(other: FaqEntity): Int {
        return if (eid == other.eid) {
            0
        } else {
            title?.compareTo(other.title ?: "") ?: slug?.compareTo(other.slug ?: "") ?: -1
        }
    }

    constructor(parcel: Parcel) : this(
            id = parcel.readString() ?: "",
            moduleEid = parcel.readString(),
            slug = parcel.readString(),
            eid = parcel.readString(),
            title = parcel.readString(),
            updatedAt = parcel.readString(),
            createdAt = parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(moduleEid)
        parcel.writeString(slug)
        parcel.writeString(eid)
        parcel.writeString(title)
        parcel.writeString(updatedAt)
        parcel.writeString(createdAt)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FaqEntity> {
        override fun createFromParcel(parcel: Parcel): FaqEntity {
            return FaqEntity(parcel)
        }

        override fun newArray(size: Int): Array<FaqEntity?> {
            return arrayOfNulls(size)
        }
    }
}