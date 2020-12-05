package com.naylinhtet.mvpwithretrofit.data.response

import com.google.gson.annotations.SerializedName
import com.naylinhtet.mvpwithretrofit.data.model.DataModel

data class TestingResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("totalResults") val totalResults: Int?,
    @SerializedName("articles")val articles: List<DataModel?>?
)