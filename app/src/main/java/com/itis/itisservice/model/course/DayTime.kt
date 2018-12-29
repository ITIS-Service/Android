package com.itis.itisservice.model.course

import android.os.Parcel
import android.os.Parcelable
import io.realm.annotations.PrimaryKey

data class DayTime(
        @PrimaryKey
        var id: Int = 0,
        var day: Day?,
        var times: List<String>?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable(Day::class.java.classLoader),
            parcel.createStringArrayList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(day, flags)
        parcel.writeStringList(times)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DayTime> {
        override fun createFromParcel(parcel: Parcel): DayTime {
            return DayTime(parcel)
        }

        override fun newArray(size: Int): Array<DayTime?> {
            return arrayOfNulls(size)
        }
    }
}