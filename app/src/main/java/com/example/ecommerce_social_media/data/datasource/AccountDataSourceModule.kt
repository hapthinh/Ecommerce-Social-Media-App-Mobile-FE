package com.example.ecommerce_social_media.data.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountDataSourceModule {
    @Binds
    abstract fun binAccountDataSource(
        accountDataSourceImpl: AccountDataSourceImpl
    ): AccountDataSource
}