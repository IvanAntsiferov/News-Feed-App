package com.voltek.materialnewsfeed.presentation

import android.net.Uri
import com.voltek.materialnewsfeed.presentation.details.CommandShareArticle

abstract class BaseActivity : com.arellomobile.mvp.MvpAppCompatActivity(),
        com.voltek.materialnewsfeed.navigation.proxy.Navigator,
        com.voltek.materialnewsfeed.utils.CompositeDisposableComponent {

    // Holds all disposables with input events subscriptions
    override val mDisposable = io.reactivex.disposables.CompositeDisposable()

    override fun onResume() {
        super.onResume()
        com.voltek.materialnewsfeed.NewsApp.Companion.getNavigatorBinder().setNavigator(this)
    }

    override fun onPause() {
        super.onPause()
        com.voltek.materialnewsfeed.NewsApp.Companion.getNavigatorBinder().removeNavigator()
    }

    override fun attachBaseContext(newBase: android.content.Context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(newBase))
    }

    // Base navigator commands
    protected fun startActivity(command: com.voltek.materialnewsfeed.navigation.command.CommandStartActivity): Boolean {
        val intent = android.content.Intent(this, command.activity)

        if (command.args != null)
            intent.putExtras(command.args)

        startActivity(intent)

        if (command.finish)
            finish()

        return true
    }

    protected fun shareArticle(command: CommandShareArticle): Boolean {
        if (!command.url.isEmpty() && !command.title.isEmpty()) {
            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND)
            shareIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, command.url)
            startActivity(android.content.Intent.createChooser(shareIntent, command.title))
        }
        return true
    }

    protected fun openWebsite(command: com.voltek.materialnewsfeed.navigation.command.CommandOpenWebsite): Boolean {
        if (!command.url.isEmpty()) {
            val browserIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(command.url))
            if (browserIntent.resolveActivity(packageManager) != null)
                startActivity(browserIntent)
            else
                android.widget.Toast.makeText(this, getString(com.voltek.materialnewsfeed.R.string.error_no_browser), android.widget.Toast.LENGTH_LONG).show()
        }
        return true
    }

    // Activity helper methods
    protected fun setupToolbar(
            displayHomeAsUpEnabled: Boolean = false,
            displayShowHomeEnabled: Boolean = false
    ) {
        val toolbar = findViewById(com.voltek.materialnewsfeed.R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
        supportActionBar?.setDisplayShowHomeEnabled(displayShowHomeEnabled)
    }

    protected fun replaceFragment(
            fragment: BaseFragment,
            @android.support.annotation.IdRes id: Int,
            tag: String,
            shouldAddToBackStack: Boolean = false,
            shouldAnimate: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()

        if (shouldAnimate)
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        if (shouldAddToBackStack)
            transaction.addToBackStack(tag)

        transaction.replace(id, fragment, tag)
        transaction.commit()
    }
}
