package com.hiringtest.matchkeeper.domain.di

import com.hiringtest.matchkeeper.domain.usecase.matches.CreateMatchUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.GetMatchUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.GetMatchesSortedByDateDescendingUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.ObserveMatchesSortedByDateDescendingUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.UpdateMatchUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.impl.CreateMatchUseCaseImpl
import com.hiringtest.matchkeeper.domain.usecase.matches.impl.GetMatchUseCaseImpl
import com.hiringtest.matchkeeper.domain.usecase.matches.impl.GetMatchesSortedByDateDescendingUseCaseImpl
import com.hiringtest.matchkeeper.domain.usecase.matches.impl.ObserveMatchesSortedByDateDescendingUseCaseImpl
import com.hiringtest.matchkeeper.domain.usecase.matches.impl.UpdateMatchUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface MatchesDomainModule {

	@Binds
	fun bindGetMatchesSortedByDateDescendingUseCase(
		useCaseImpl: GetMatchesSortedByDateDescendingUseCaseImpl
	): GetMatchesSortedByDateDescendingUseCase

	@Binds
	fun bindObserveMatchesSortedByDateDescendingUseCase(
		useCaseImpl: ObserveMatchesSortedByDateDescendingUseCaseImpl
	): ObserveMatchesSortedByDateDescendingUseCase

	@Binds
	fun bindCreateMatchUseCase(useCaseImpl: CreateMatchUseCaseImpl): CreateMatchUseCase

	@Binds
	fun bindUpdateMatchUseCase(useCaseImpl: UpdateMatchUseCaseImpl): UpdateMatchUseCase

	@Binds
	fun bindGetMatchUseCase(useCaseImpl: GetMatchUseCaseImpl): GetMatchUseCase

}