package com.voltek.materialnewsfeed.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.api.NewsApi
import com.voltek.materialnewsfeed.api.NewsApiArticlesResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl(MaterialNewsFeedApp.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val api: NewsApi = retrofit.create(NewsApi::class.java)
        val observable: Observable<NewsApiArticlesResponse> = api.getArticles(BuildConfig.ApiKey, "the-next-web")
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);
    }

    fun handleResponse(articles: NewsApiArticlesResponse) {
        Toast.makeText(this, "Articles: " + articles.articles.size, Toast.LENGTH_SHORT).show()
    }

    fun handleError(error: Throwable) {
        error.printStackTrace()
    }
}
