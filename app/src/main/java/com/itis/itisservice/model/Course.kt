package com.itis.itisservice.model

import android.os.Parcel
import android.os.Parcelable

open class Course(var id: Int = 0,

                  var name: String? = null,

                  var description: String? = null,

                  var teacher_id: Int = 0,

                  var teacher_full_name: String? = null,

                  var lesson_time: String? = null,

                  var lesson_place: String? = null,

                  var status: String? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(teacher_id)
        parcel.writeString(teacher_full_name)
        parcel.writeString(lesson_time)
        parcel.writeString(lesson_place)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}
