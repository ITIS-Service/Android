package com.itis.itisservice.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Course(
        @PrimaryKey
        var id: Int = 0,

        var name: String? = null,

        var description: String? = null,

        var courseNumber: Int = 0,

        var place: String? = null,

        var signUpOpen: Boolean = true,

        var teacher: Teacher? = null,

        var userCourseStatus: String? = null) : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readParcelable<Teacher>(Teacher::class.java.classLoader),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(courseNumber)
        parcel.writeString(place)
        parcel.writeByte(if (signUpOpen) 1 else 0)
        parcel.writeParcelable(teacher, flags)
        parcel.writeString(userCourseStatus)
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

