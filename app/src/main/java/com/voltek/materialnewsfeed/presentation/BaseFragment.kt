package com.voltek.materialnewsfeed.presentation

import android.support.annotation.StringRes
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.voltek.materialnewsfeed.utils.CompositeDisposableComponent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : com.arellomobile.mvp.MvpAppCompatFragment(),
        com.voltek.materialnewsfeed.utils.CompositeDisposableComponent {

    // Holds all disposables with input events subscriptions
    override val mDisposable = io.reactivex.disposables.CompositeDisposable()

    // Helper methods
    protected fun toast(
            @android.support.annotation.StringRes resId: Int,
            length: Int = android.widget.Toast.LENGTH_SHORT
    ) {
        android.widget.Toast.makeText(context, getString(resId), length).show()
    }

    protected fun toast(
            message: String,
            length: Int = android.widget.Toast.LENGTH_SHORT
    ) {
        android.widget.Toast.makeText(context, message, length).show()
    }
}
