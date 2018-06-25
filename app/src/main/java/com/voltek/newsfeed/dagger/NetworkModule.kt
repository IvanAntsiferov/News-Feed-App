package com.voltek.newsfeed.dagger

import android.content.Context
import com.voltek.newsfeed.BuildConfig
import com.voltek.newsfeed.data.network.BaseInterceptor
import com.voltek.newsfeed.data.network.ConnectivityInterceptor
import com.voltek.newsfeed.data.network.NewsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String) {

    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(BaseInterceptor())
                .addInterceptor(ConnectivityInterceptor(context))
                .addLoggingInterceptor(BuildConfig.DEBUG)
                .build()
    }

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    private fun OkHttpClient.Builder.addLoggingInterceptor(isDebugBuild: Boolean) = when (isDebugBuild) {
        true -> this.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        else -> this
    }
}
