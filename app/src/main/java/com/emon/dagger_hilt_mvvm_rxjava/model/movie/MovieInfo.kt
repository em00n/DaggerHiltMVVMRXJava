package com.emon.dagger_hilt_mvvm_rxjava.model.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieInfo(
    @SerializedName("poster_path")
    @Expose
    val posterPath: String,

    @SerializedName("overview")
    @Expose
    val overview: String,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("id")
    @Expose
    val id: Int
)