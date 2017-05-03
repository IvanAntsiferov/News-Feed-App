package com.voltek.materialnewsfeed.Utils

import android.content.Context
import android.net.ConnectivityManager
import com.voltek.materialnewsfeed.data.exception.NoConnectionException

object RepositoryUtils {

    /**
     * @throws NoConnectionException
     */
    fun checkConnection(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            throw NoConnectionException()
        }
    }
}
