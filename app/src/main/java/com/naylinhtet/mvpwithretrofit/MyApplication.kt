package com.naylinhtet.mvpwithretrofit

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.naylinhtet.mvpwithretrofit.utils.Constants
import com.naylinhtet.mvpwithretrofit.utils.SharePreference

class MyApplication: Application() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private var mInstance: MyApplication? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        getDeviceId()
        getDeviceInfo()
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId() {
        val deviceID = Settings.Secure.getString(contentResolver,
            Settings.Secure.ANDROID_ID)
        SharePreference(this).save(Constants.DEVICE_ID, deviceID)
    }

    private fun getDeviceInfo(){

        val infoBuilder = StringBuilder()

        infoBuilder.append(Build.BRAND)
        infoBuilder.append(",")
        infoBuilder.append(Build.MODEL)
        infoBuilder.append(",")
        infoBuilder.append(Build.VERSION.SDK_INT)
        infoBuilder.append(",")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            infoBuilder.append(Build.VERSION.BASE_OS)
            infoBuilder.append(",")
        }
        infoBuilder.append(Build.MANUFACTURER)
        infoBuilder.append(",")
        infoBuilder.append(Build.HARDWARE)
        infoBuilder.append(",")
        /*infoBuilder.append(Build.ID)
        infoBuilder.append(",")*/
        infoBuilder.append(Build.PRODUCT)
        /*infoBuilder.append(",")
        infoBuilder.append(Build.BOARD)
        infoBuilder.append(",")
        infoBuilder.append(Build.FINGERPRINT)
        infoBuilder.append(",")
        infoBuilder.append(Build.DISPLAY)*/

        val info: String = infoBuilder.replace("[^\\x0A\\x0D\\x20-\\x7E]".toRegex(), "")
        SharePreference(this).save(Constants.DEVICE_INFO, info)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}