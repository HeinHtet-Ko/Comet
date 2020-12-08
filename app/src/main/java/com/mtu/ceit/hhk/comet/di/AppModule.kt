package com.mtu.ceit.hhk.comet.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mtu.ceit.hhk.comet.network.TMDB_API
import com.mtu.ceit.hhk.comet.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGlideRequestManager(@ActivityContext context: Context): RequestManager = Glide.with(context)


    @Provides
    @Singleton
    fun provideGson():Gson =GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):TMDB_API =
        retrofit.create(TMDB_API::class.java)


}