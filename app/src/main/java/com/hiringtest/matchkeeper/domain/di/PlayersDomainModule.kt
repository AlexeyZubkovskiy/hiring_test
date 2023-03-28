package com.hiringtest.matchkeeper.domain.di

import com.hiringtest.matchkeeper.domain.usecase.players.GetPlayersTotalSortedDescendingUseCase
import com.hiringtest.matchkeeper.domain.usecase.players.impl.GetPlayersTotalSortedDescendingUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface PlayersDomainModule {

	@Binds
	fun bindGetPlayersTotalSortedDescendingUseCase(
		useCaseImpl: GetPlayersTotalSortedDescendingUseCaseImpl
	): GetPlayersTotalSortedDescendingUseCase

}