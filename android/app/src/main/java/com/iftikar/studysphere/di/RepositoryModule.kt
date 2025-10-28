package com.iftikar.studysphere.di

import com.iftikar.studysphere.data.repository.AdminRepositoryImpl
import com.iftikar.studysphere.domain.repository.AdminRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAdminRepository(impl: AdminRepositoryImpl): AdminRepository
}