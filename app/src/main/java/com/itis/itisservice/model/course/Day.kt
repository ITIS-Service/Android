package com.itis.itisservice.model.course

import android.os.Parcel
import android.os.Parcelable

enum class Day(val day: Int?) : Parcelable {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(day)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Day> {
        private val map = Day.values().associateBy(Day::day)
        fun fromInt(type: Int) = map[type]

        override fun createFromParcel(parcel: Parcel): Day {
            return Day.values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<Day?> {
            return arrayOfNulls(size)
        }
    }

}