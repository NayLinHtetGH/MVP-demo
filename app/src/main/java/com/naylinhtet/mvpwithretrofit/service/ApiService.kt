package com.naylinhtet.mvpwithretrofit.service

import com.naylinhtet.mvpwithretrofit.data.response.TestingResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    // get for
    @GET("top-headlines?country=us&category=business&apiKey=3703d5022609479296a3ec74c1dc9820")
    fun dataCall(): Observable<TestingResponse>
}