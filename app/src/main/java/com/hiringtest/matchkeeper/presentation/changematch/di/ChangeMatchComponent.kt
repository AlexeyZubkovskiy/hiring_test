package com.hiringtest.matchkeeper.presentation.changematch.di

import com.hiringtest.matchkeeper.domain.di.MatchesDomainModule
import com.hiringtest.matchkeeper.presentation.changematch.ChangeMatchFragment
import com.hiringtest.matchkeeper.presentation.matches.di.MatchesScope
import dagger.Subcomponent

@ChangeMatchScope
@Subcomponent(
	modules = [MatchesDomainModule::class, ChangeMatchModule::class]
)
interface ChangeMatchComponent {

	@Subcomponent.Factory
	interface Factory {
		fun create(): ChangeMatchComponent
	}

	fun inject(fragment: ChangeMatchFragment)

}