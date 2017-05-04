package com.voltek.materialnewsfeed.navigation.command

import com.voltek.materialnewsfeed.navigation.proxy.Command
import android.os.Bundle
import android.support.annotation.Nullable
import com.voltek.materialnewsfeed.ui.BaseActivity

/**
 * Basic command for between activity navigation.
 *
 * @param activityInstance activity, that need to be started
 * @param args bundle arguments for this activity
 * @param finish is current activity need to be finished after starting new one
 */
class CommandStartActivity (
        activityInstance: BaseActivity,
        @Nullable val args: Bundle? = null,
        val finish: Boolean = false
) : Command() {

    val activity: Class<Any> = activityInstance.javaClass
}
