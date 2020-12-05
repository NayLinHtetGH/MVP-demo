package com.naylinhtet.mvpwithretrofit.service

import com.naylinhtet.mvpwithretrofit.data.response.TestingResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    // get for
    @GET("top-headlines?country=us&category=business&apiKey=API_KEY")
    fun dataCall(): Observable<TestingResponse>
}