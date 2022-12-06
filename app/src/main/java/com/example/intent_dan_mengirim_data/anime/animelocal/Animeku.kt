package com.example.intent_dan_mengirim_data.anime.animelocal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animeku(
    var name: String,
    var description: String,
    var photo: Int
) : Parcelable
