package com.naylinhtet.mvpwithretrofit.service

import android.content.Context
import android.os.Build
import com.naylinhtet.mvpwithretrofit.BuildConfig
import com.naylinhtet.mvpwithretrofit.utils.Constants
import com.naylinhtet.mvpwithretrofit.utils.Constants.USER_ID
import com.naylinhtet.mvpwithretrofit.utils.SharePreference
import com.naylinhtet.mvpwithretrofit.utils.Tls12SocketFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

/**
 * Created by Nay Lin Htet
 */
@Suppress("DEPRECATION")
class ProvideApi {
    private var baseUrl = BuildConfig.API_ENDPOINT

    private fun getOkHttpClient(context: Context): OkHttpClient.Builder {

        val sharedPreference = SharePreference(context)
        val builder = OkHttpClient.Builder()

        val deviceId = sharedPreference.getValueString(Constants.DEVICE_ID).toString()
        val atkn = sharedPreference.getValueString(Constants.ATKN).toString()
        val deviceInfo = sharedPreference.getValueString(Constants.DEVICE_INFO).toString()
        val useId = sharedPreference.getValueString(USER_ID).toString()

        //header Interceptor
        builder.addInterceptor(ChuckInterceptor(context))
        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", BuildConfig.API_KEY)
                .addHeader("x-device-id", deviceId)
                .addHeader("x-app-version", BuildConfig.VERSION_NAME)
                .addHeader("x-language", "mm")
                .addHeader("x-api-token", atkn)
                .addHeader("x-user-id", useId)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-device-info", deviceInfo)
                .build()
            chain.proceed(request)
        }

        return builder
            .connectTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000, TimeUnit.SECONDS)
    }

    private fun retrofitBuilder(context: Context): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(enableTls12OnPreLollipop(getOkHttpClient(context)).build())
            .build()
    }

    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder?): OkHttpClient.Builder {

        if (Build.VERSION.SDK_INT in 16..21) {

            try {
                val sc: SSLContext = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                client!!.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))

                val cs: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.RESTRICTED_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
                    )
                    .build()

                val spaces: ArrayList<ConnectionSpec> = ArrayList()
                spaces.add(cs)
                spaces.add(ConnectionSpec.COMPATIBLE_TLS)
                spaces.add(ConnectionSpec.CLEARTEXT)

                client.connectionSpecs(spaces)

            } catch (exc: Exception) {

            }
        }

        return client!!
    }
    fun createLoginApiService(context: Context): ApiService {
        return retrofitBuilder(context).create(ApiService::class.java)
    }
}