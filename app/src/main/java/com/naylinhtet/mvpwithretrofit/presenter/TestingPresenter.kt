package com.naylinhtet.mvpwithretrofit.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.naylinhtet.mvpwithretrofit.contract.TestingContract
import com.naylinhtet.mvpwithretrofit.manager.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class TestingPresenter(val context: Context,
                       private val dataView: TestingContract.TestingView
) : TestingContract.Presenter  {

    private val dataManager = DataManager(context)
    @SuppressLint("CheckResult")
    override fun dataRequest() {
        dataManager.dataCall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                dataView.testingResponse(response)
            }, {
                if (it is HttpException) {
                    val exception = it
                    when {
                        exception.code() == 500 -> dataView.setError("Server Error")
                        exception.code() == 404 -> dataView.setError("Not Found")
                        //exception.code() == 403 -> dataView.logoutUser()
                        else -> dataView.setError(it.localizedMessage)
                    }
                } else {
                    dataView.setError(it.localizedMessage)
                }
            })
    }

    override fun start() {
    }

}