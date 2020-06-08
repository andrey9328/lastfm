package com.technorely.lastfm.database.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    var name: String?,
    var playCount: String?,
    var mbid: String?,
    var imageUrl: String?,
    var country: String?
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readLong()
    }

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(playCount)
        parcel.writeString(mbid)
        parcel.writeString(imageUrl)
        parcel.writeString(country)
        parcel.writeLong(id)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArtistEntity> {
        override fun createFromParcel(parcel: Parcel): ArtistEntity {
            return ArtistEntity(parcel)
        }

        override fun newArray(size: Int): Array<ArtistEntity?> {
            return arrayOfNulls(size)
        }
    }
}