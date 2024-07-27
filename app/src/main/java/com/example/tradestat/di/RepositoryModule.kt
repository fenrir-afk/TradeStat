package com.example.tradestat.di


import com.example.data.login.dataSource.LoginDataSource
import com.example.data.login.repository.LoginRepositoryImp
import com.example.domain.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
       loginDataSource: LoginDataSource
    ): LoginRepository {
        return LoginRepositoryImp(loginDataSource)
    }
}