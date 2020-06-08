package com.technorely.lastfm.network

import com.technorely.lastfm.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { provideRetrofit(get()) }
    factory { provideBaseHttpConfig() }
    factory { provideResponseHandler() }
    factory(named("parser")) { (factories: GsonConverterFactory) -> provideRetrofitWithParser(get(), factories) }
}

fun provideResponseHandler(): ResponseHandler {
    return ResponseHandler()
}

fun provideRetrofit(builder: OkHttpClient.Builder): LastFmApi {
    val retrofit: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(builder.build())
        .addConverterFactory(GsonConverterFactory.create())

    return retrofit.build().create(LastFmApi::class.java)
}

fun provideRetrofitWithParser(builder: OkHttpClient.Builder, factory: GsonConverterFactory): LastFmApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(builder.build())
    retrofit.addConverterFactory(factory)
    return retrofit.build().create(LastFmApi::class.java)
}


fun provideBaseHttpConfig(): OkHttpClient.Builder {
    val log = HttpLoggingInterceptor()
    log.setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .addQueryParameter("format", "json")
                .build()
            val newRequest = request.newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }
        .addInterceptor(log)
    return okHttpClient
}