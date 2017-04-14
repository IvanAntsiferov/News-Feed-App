package com.voltek.materialnewsfeed.ui

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.voltek.materialnewsfeed.R

abstract class BaseActivity : AppCompatActivity() {

    protected fun setupToolbar(
            displayHomeAsUpEnabled: Boolean = false,
            displayShowHomeEnabled: Boolean = false) {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        supportActionBar?.setDisplayShowHomeEnabled(displayShowHomeEnabled)
    }

    protected fun replaceFragment(fragment: Fragment,
                                  @IdRes id: Int,
                                  tag: String,
                                  shouldAddToBackStack: Boolean = false,
                                  shouldAnimate: Boolean = true) {

        val transaction = supportFragmentManager.beginTransaction()

        if (shouldAnimate)
            //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)

        if (shouldAddToBackStack)
            transaction.addToBackStack(tag)

        transaction.replace(id, fragment, tag)
        transaction.commit()
    }
}
