package com.khanhpham.smartkidz.helpers

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor (private val application: Application) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkConnected(application)){
            throw NoNetworkException
        }
        return chain.proceed(chain.request())
    }

    private fun isNetworkConnected(context: Context?): Boolean {
        if (context != null){
            val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null){
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

}