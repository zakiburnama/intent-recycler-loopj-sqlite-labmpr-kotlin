package com.example.intent_dan_mengirim_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val name: String?,
    val brand: String?,
    val type: String?,
    val year: Int?,
    val price: Double?
) : Parcelable