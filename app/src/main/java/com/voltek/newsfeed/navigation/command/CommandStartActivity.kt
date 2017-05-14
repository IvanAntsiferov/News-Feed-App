package com.voltek.newsfeed.navigation.command

import com.voltek.newsfeed.navigation.proxy.Command
import com.voltek.newsfeed.presentation.BaseActivity

/**
 * Basic command for navigation between activities.
 *
 * @param activityInstance activity, that need to be started
 * @param finish is current activity need to be finished after starting new one
 */
class CommandStartActivity (
        activityInstance: BaseActivity,
        val finish: Boolean = false
) : Command() {

    val activity: Class<Any> = activityInstance.javaClass
}
