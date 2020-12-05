@file:Suppress("DEPRECATION")

package com.naylinhtet.mvpwithretrofit.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    fun isConnected (context: Context) :Boolean{

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}