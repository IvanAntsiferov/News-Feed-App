package com.voltek.materialnewsfeed.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.voltek.materialnewsfeed.BuildConfig
import com.voltek.materialnewsfeed.MaterialNewsFeedApp
import com.voltek.materialnewsfeed.R
import com.voltek.materialnewsfeed.api.NewsApi
import com.voltek.materialnewsfeed.api.NewsApiArticlesResponse
import com.voltek.materialnewsfeed.api.NewsApiSourcesResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl(MaterialNewsFeedApp.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val api: NewsApi = retrofit.create(NewsApi::class.java)
        val observableA: Observable<NewsApiArticlesResponse> = api.getArticles(BuildConfig.ApiKey, "the-next-web")
        observableA.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseA, this::handleError);

        val observableS: Observable<NewsApiSourcesResponse> = api.getSources(BuildConfig.ApiKey, "")
        observableS.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseS, this::handleError);
    }

    fun handleResponseA(articles: NewsApiArticlesResponse) {
        Toast.makeText(this, "Articles: " + articles.articles.size, Toast.LENGTH_SHORT).show()
    }

    fun handleError(error: Throwable) {
        error.printStackTrace()
    }

    fun handleResponseS(sources: NewsApiSourcesResponse) {
        Toast.makeText(this, "Sources: " + sources.sources.size, Toast.LENGTH_SHORT).show()
    }
}
