package com.hiringtest.matchkeeper.presentation.matches.di

import com.hiringtest.matchkeeper.domain.di.MatchesDomainModule
import com.hiringtest.matchkeeper.presentation.matches.MatchesFragment
import dagger.Subcomponent

@MatchesScope
@Subcomponent(modules = [MatchesDomainModule::class, MatchesModule::class])
interface MatchesComponent {

	@Subcomponent.Factory
	interface Factory {
		fun create(): MatchesComponent
	}

	fun inject(fragment: MatchesFragment)

}