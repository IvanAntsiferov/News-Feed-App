package com.voltek.materialnewsfeed.utils

import android.content.Context
import android.net.ConnectivityManager
import com.voltek.materialnewsfeed.R

object NetworkUtils {

    /**
     * @throws Exception
     */
    fun checkConnection(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            throw Exception(context.getString(R.string.error_no_connection))
        }
    }
}
