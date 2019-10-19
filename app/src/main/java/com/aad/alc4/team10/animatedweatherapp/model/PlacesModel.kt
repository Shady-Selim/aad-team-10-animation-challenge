package com.aad.alc4.team10.animatedweatherapp.model


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class PlacesModel(
    @field:Json(name = "Name")
    val name: String? = "",
    @field:Json(name = "Countries")
    val countries: List<Country?>? = listOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Country)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeTypedList(countries)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlacesModel> {
        override fun createFromParcel(parcel: Parcel): PlacesModel {
            return PlacesModel(parcel)
        }

        override fun newArray(size: Int): Array<PlacesModel?> {
            return arrayOfNulls(size)
        }
    }
}