package com.voltek.materialnewsfeed.ui

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.voltek.materialnewsfeed.utils.CompositeDisposableComponent
import com.voltek.materialnewsfeed.NewsApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.navigation.command.CommandStartActivity
import com.voltek.materialnewsfeed.navigation.proxy.Navigator
import com.voltek.materialnewsfeed.ui.details.CommandShareArticle
import io.reactivex.disposables.CompositeDisposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity : MvpAppCompatActivity(),
        Navigator,
        CompositeDisposableComponent {

    // Holds all disposables with input events subscriptions
    override val mDisposable = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        NewsApp.getNavigatorBinder().setNavigator(this)
    }

    override fun onPause() {
        super.onPause()
        NewsApp.getNavigatorBinder().removeNavigator()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    // Base navigator commands
    protected fun startActivity(command: CommandStartActivity): Boolean {
        val intent = Intent(this, command.activity)

        if (command.args != null)
            intent.putExtras(command.args)

        startActivity(intent)

        if (command.finish)
            finish()

        return true
    }

    protected fun shareArticle(command: CommandShareArticle): Boolean {
        if (!command.url.isEmpty() && !command.title.isEmpty()) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
            shareIntent.type = "text/plain"
            //shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, command.url)
            startActivity(Intent.createChooser(shareIntent, command.title))
        }
        return true
    }

    // Activity helper methods
    protected fun setupToolbar(
            displayHomeAsUpEnabled: Boolean = false,
            displayShowHomeEnabled: Boolean = false
    ) {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        supportActionBar?.setDisplayShowHomeEnabled(displayShowHomeEnabled)
    }

    protected fun replaceFragment(
            fragment: BaseFragment,
            @IdRes id: Int,
            tag: String,
            shouldAddToBackStack: Boolean = false,
            shouldAnimate: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()

        if (shouldAnimate)
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)

        if (shouldAddToBackStack)
            transaction.addToBackStack(tag)

        transaction.replace(id, fragment, tag)
        transaction.commit()
    }
}
