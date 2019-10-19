package com.aad.alc4.team10.animatedweatherapp.model


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Country(
    @field:Json(name = "Name")
    val name: String? = "",
    @field:Json(name = "Cities")
    val cities: List<City?>? = listOf(),
    @field:Json(name = "Short")
    val short: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(City),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeTypedList(cities)
        parcel.writeString(short)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }
}