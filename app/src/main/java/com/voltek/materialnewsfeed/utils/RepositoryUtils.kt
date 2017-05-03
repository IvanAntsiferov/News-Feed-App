package com.voltek.materialnewsfeed.utils

import android.content.Context
import android.net.ConnectivityManager
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.data.exception.NoConnectionException

// TODO reorganize utils package
object RepositoryUtils {

    /**
     * @throws NoConnectionException
     */
    fun checkConnection(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            throw NoConnectionException(context.getString(R.string.error_no_connection))
        }
    }
}
