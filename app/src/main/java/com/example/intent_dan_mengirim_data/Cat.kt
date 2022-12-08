package com.example.intent_dan_mengirim_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    val owner: String?,
    val tag: String?,
    val img: String?
) : Parcelable