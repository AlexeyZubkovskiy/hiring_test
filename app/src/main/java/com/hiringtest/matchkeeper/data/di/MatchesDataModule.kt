package com.hiringtest.matchkeeper.data.di

import com.hiringtest.matchkeeper.application.Schedulers
import com.hiringtest.matchkeeper.data.datasource.MatchesDataSource
import com.hiringtest.matchkeeper.data.datasource.impl.MatchesDataSourceImpl
import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.data.repository.impl.MatchesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MatchesDataModule {

	@Singleton
	@Provides
	fun provideMatchesDataSource(): MatchesDataSource = MatchesDataSourceImpl()

	@Singleton
	@Provides
	fun provideMatchesRepository(
		schedulers: Schedulers,
		matchesDataSource: MatchesDataSource
	): MatchesRepository = MatchesRepositoryImpl(matchesDataSource, schedulers)

}