package com.voltek.materialnewsfeed.navigation.command

import android.app.Activity
import com.voltek.materialnewsfeed.navigation.proxy.Command
import android.os.Bundle
import android.support.annotation.Nullable

/**
 * Basic command for between activity navigation.
 *
 * @param activity activity, that need to be started
 * @param args bundle arguments for this activity
 * @param finish is current activity need to be finished after starting new one
 */
class CommandStartActivity (
        val activity: Activity,
        @Nullable val args: Bundle? = null,
        val finish: Boolean = false)
    : Command()
