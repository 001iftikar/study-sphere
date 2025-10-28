package com.iftikar.studysphere.di

import android.content.Context
import com.iftikar.studysphere.shared.InternetConnectivityObserver
import com.iftikar.studysphere.shared.LocalUserSessionHandler
import com.iftikar.studysphere.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    //this one is for appwrite client
    @Provides
    @Singleton
    fun provideAppwriteClient(@ApplicationContext context: Context): Client {
        return Client(context)
            .setEndpoint(AppConstants.AppwriteConstants.APPWRITE_PUBLIC_ENDPOINT)
            .setProject(AppConstants.AppwriteConstants.APPWRITE_PROJECT_ID)
    }

    // This one is for data store
    @Provides
    @Singleton
    fun provideLocalUserSessionHandler(@ApplicationContext context: Context): LocalUserSessionHandler {
        return LocalUserSessionHandler(context)
    }

    // Internet observer
    @Provides
    @Singleton
    fun provideInternetConnectivityObserver(@ApplicationContext context: Context) =
        InternetConnectivityObserver(context)
}