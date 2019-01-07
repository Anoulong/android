package com.anou.prototype.yoga.db

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

open class BaseEntity(
        @PrimaryKey
        @NonNull
        @SerializedName("_id")
        var id: String,
        var slug: String?,
        var eid: String?
) : Parcelable, Comparable<BaseEntity> {

    override fun compareTo(other: BaseEntity): Int {
        return if (id == other.id) {
            0
        } else {
            eid?.compareTo(other.eid ?: "") ?: slug?.compareTo(other.slug ?: "") ?: -1
        }
    }

    constructor(parcel: Parcel) : this(
            id = parcel.readString() ?: "",
            slug = parcel.readString(),
            eid = parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(slug)
        parcel.writeString(eid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseEntity> {
        override fun createFromParcel(parcel: Parcel): BaseEntity {
            return BaseEntity(parcel)
        }

        override fun newArray(size: Int): Array<BaseEntity?> {
            return arrayOfNulls(size)
        }
    }
}