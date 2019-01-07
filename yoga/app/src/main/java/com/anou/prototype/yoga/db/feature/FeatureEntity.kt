package com.anou.prototype.yoga.db.feature

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Feature")
data class FeatureEntity(
        @PrimaryKey
        @NonNull
        @SerializedName("_id")
        var id: String,
        @ColumnInfo(index = true)
        @SerializedName("eid")
        var moduleEid: String?,
        var slug: String?,
        var eid: String?,
        var title: String?,
        var nbItems: Int = 0,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("created_at")
        var createdAt: String?
) : Parcelable, Comparable<FeatureEntity> {

    override fun compareTo(other: FeatureEntity): Int {
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
            nbItems = parcel.readInt(),
            updatedAt = parcel.readString(),
            createdAt = parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(moduleEid)
        parcel.writeString(slug)
        parcel.writeString(eid)
        parcel.writeString(title)
        parcel.writeInt(nbItems)
        parcel.writeString(updatedAt)
        parcel.writeString(createdAt)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeatureEntity> {
        override fun createFromParcel(parcel: Parcel): FeatureEntity {
            return FeatureEntity(parcel)
        }

        override fun newArray(size: Int): Array<FeatureEntity?> {
            return arrayOfNulls(size)
        }
    }
}