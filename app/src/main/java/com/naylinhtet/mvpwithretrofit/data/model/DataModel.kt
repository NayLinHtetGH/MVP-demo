package com.naylinhtet.mvpwithretrofit.data.model

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("urlToImage") val urlToImage: String?
)