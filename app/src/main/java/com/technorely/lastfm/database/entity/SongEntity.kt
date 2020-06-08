package com.technorely.lastfm.database.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    var artistName: String?,
    var name: String?,
    var playCount: String?,
    var listeners: String?,
    var image: String?,
    var rank: String?
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    constructor(parcel: Parcel) : this(
        parcel.readString(),
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
        parcel.writeString(artistName)
        parcel.writeString(name)
        parcel.writeString(playCount)
        parcel.writeString(listeners)
        parcel.writeString(image)
        parcel.writeString(rank)
        parcel.writeLong(id)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongEntity> {
        override fun createFromParcel(parcel: Parcel): SongEntity {
            return SongEntity(parcel)
        }

        override fun newArray(size: Int): Array<SongEntity?> {
            return arrayOfNulls(size)
        }
    }


}