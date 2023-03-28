package com.hiringtest.matchkeeper.presentation.players.di

import com.hiringtest.matchkeeper.domain.di.PlayersDomainModule
import com.hiringtest.matchkeeper.presentation.players.PlayersFragment
import dagger.Subcomponent
import javax.inject.Inject

@PlayersScope
@Subcomponent(modules = [PlayersDomainModule::class, PlayersModule::class])
interface PlayersComponent {

	@Subcomponent.Factory
	interface Factory {

		fun create(): PlayersComponent

	}

	fun inject(fragment: PlayersFragment)

}