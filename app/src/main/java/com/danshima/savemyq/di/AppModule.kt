/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.danshima.savemyq.di

import android.app.Application
import androidx.room.Room
import com.danshima.savemyq.BuildConfig
import com.danshima.savemyq.api.Api
import com.danshima.savemyq.db.FeedDao
import com.danshima.savemyq.db.AppDatabase
import com.danshima.savemyq.db.SavingsGoalDao
import com.danshima.savemyq.db.SavingsRuleDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideApiService(): Api {
        val interceptor = HttpLoggingInterceptor()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl("https://qapital-ios-testtask.herokuapp.com")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesFeedDao(db: AppDatabase): FeedDao {
        return db.feedDao()
    }

    @Singleton
    @Provides
    fun provideSavingsRuleDao(db: AppDatabase): SavingsRuleDao {
        return db.savingsRuleDao()
    }

    @Singleton
    @Provides
    fun provideSavingsGoalDao(db: AppDatabase): SavingsGoalDao {
        return db.savingsGoalDao()
    }

}
