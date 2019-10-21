package com.aad.alc4.team10.animatedweatherapp.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Region(
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Countries") val countries: List<Country>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Country)
    )

    override fun writeToParcel(p0: Parcel, p1: Int) = with(p0) {
        writeString(name)
        writeTypedList(countries)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Region> {
        override fun createFromParcel(parcel: Parcel)=Region(parcel)
        override fun newArray(size: Int): Array<Region?> = arrayOfNulls(size)
    }
}