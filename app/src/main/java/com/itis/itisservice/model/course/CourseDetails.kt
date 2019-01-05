package com.itis.itisservice.model.course

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CourseDetails(
        @PrimaryKey
        var id: Int = 0,

        var course: Course? = null,

        var courseNumber: Int = 0,

        var place: String? = null,

        var signUpOpen: Boolean = true,

        var teacher: Teacher? = null,

        var userCourseStatus: CourseStatus? = null,

        var dayTimes: List<DayTime>? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable(Course::class.java.classLoader),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readParcelable(Teacher::class.java.classLoader),
            parcel.readParcelable(CourseStatus::class.java.classLoader),
            arrayListOf<DayTime>().apply {
                parcel.readList(this, DayTime::class.java.classLoader)
            })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(course, flags)
        parcel.writeInt(courseNumber)
        parcel.writeString(place)
        parcel.writeByte(if (signUpOpen) 1 else 0)
        parcel.writeParcelable(teacher, flags)
        parcel.writeParcelable(userCourseStatus, flags)
        parcel.writeList(dayTimes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseDetails> {
        override fun createFromParcel(parcel: Parcel): CourseDetails {
            return CourseDetails(parcel)
        }

        override fun newArray(size: Int): Array<CourseDetails?> {
            return arrayOfNulls(size)
        }
    }

}

