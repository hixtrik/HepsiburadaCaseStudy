package com.hixtrik.hepsiburadacasestudy.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class ContentDetailResponse(
    @SerializedName("artistName")
    val artistName: String?,
    @SerializedName("artworkUrl100")
    val artworkUrl100: String?,
    @SerializedName("artworkUrl30")
    val artworkUrl30: String?,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String?,
    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String?,
    @SerializedName("collectionHdPrice")
    val collectionHdPrice: Double?,
    @SerializedName("collectionPrice")
    val collectionPrice: Double?,
    @SerializedName("contentAdvisoryRating")
    val contentAdvisoryRating: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("longDescription")
    val longDescription: String?,
    @SerializedName("previewUrl")
    val previewUrl: String?,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String?,
    @SerializedName("releaseDate")
    val releaseDate: Date?,
    @SerializedName("trackCensoredName")
    val trackCensoredName: String?,
    @SerializedName("trackExplicitness")
    val trackExplicitness: String?,
    @SerializedName("trackHdPrice")
    val trackHdPrice: Double?,
    @SerializedName("trackHdRentalPrice")
    val trackHdRentalPrice: Double?,
    @SerializedName("trackId")
    val trackId: Int?,
    @SerializedName("trackName")
    val trackName: String?,
    @SerializedName("trackPrice")
    val trackPrice: Double?,
    @SerializedName("trackRentalPrice")
    val trackRentalPrice: Double?,
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Int?,
    @SerializedName("trackViewUrl")
    val trackViewUrl: String?,
    @SerializedName("wrapperType")
    val wrapperType: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("collectionName")
    val collectionName: String?,
    @SerializedName("formattedPrice")
    val formattedPrice: String?,
)