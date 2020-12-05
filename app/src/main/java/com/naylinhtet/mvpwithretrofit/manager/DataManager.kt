package com.naylinhtet.mvpwithretrofit.manager

import android.content.Context
import com.naylinhtet.mvpwithretrofit.data.response.TestingResponse
import com.naylinhtet.mvpwithretrofit.service.ProvideApi
import io.reactivex.Observable

class DataManager(val context: Context)  {
    private var provideApi: ProvideApi = ProvideApi()

    fun dataCall(): Observable<TestingResponse> {
        return provideApi.createLoginApiService(context).dataCall()
    }
}