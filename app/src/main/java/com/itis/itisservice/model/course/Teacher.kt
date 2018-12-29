package com.itis.itisservice.model.course

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Teacher(
        @PrimaryKey
        var id: Int? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var email: String? = null,
        var link: String? = null) : RealmObject(), Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeString(firstName)
                parcel.writeString(lastName)
                parcel.writeString(email)
                parcel.writeString(link)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Teacher> {
                override fun createFromParcel(parcel: Parcel): Teacher {
                        return Teacher(parcel)
                }

                override fun newArray(size: Int): Array<Teacher?> {
                        return arrayOfNulls(size)
                }
        }
}