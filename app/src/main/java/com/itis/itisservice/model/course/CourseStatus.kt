package com.itis.itisservice.model.course

import android.os.Parcel
import android.os.Parcelable

enum class CourseStatus(val status: String?) : Parcelable {

    WAITING("Ожидание"),
    ACCEPTED("Принят"),
    REJECTED("Отклонен"),
    MOVED("Перемещен");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseStatus> {
        override fun createFromParcel(parcel: Parcel): CourseStatus {
            return CourseStatus.values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<CourseStatus?> {
            return arrayOfNulls(size)
        }
    }
}