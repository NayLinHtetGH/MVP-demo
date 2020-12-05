package com.naylinhtet.mvpwithretrofit.contract

import com.naylinhtet.mvpwithretrofit.base.BasePresenter
import com.naylinhtet.mvpwithretrofit.base.BaseView
import com.naylinhtet.mvpwithretrofit.data.response.TestingResponse

interface TestingContract {
    interface TestingView: BaseView<Presenter> {
        fun initView()
        fun setError(message: String?)
        fun testingResponse(response: TestingResponse?)
    }

    interface Presenter: BasePresenter {
        fun dataRequest()
    }
}