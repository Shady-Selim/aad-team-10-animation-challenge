package com.aad.alc4.team10.animatedweatherapp.model


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class City(
    @field:Json(name = "Name")
    val name: String? = "",
    @field:Json(name = "Zip")
    val zip: Int? = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(zip)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}