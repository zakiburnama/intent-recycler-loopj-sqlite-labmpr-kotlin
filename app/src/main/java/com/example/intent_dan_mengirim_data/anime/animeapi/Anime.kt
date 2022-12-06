package com.example.intent_dan_mengirim_data.anime.animeapi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime(
    val id: Int?,
    val name: String?,
    val img: String?
) : Parcelable
