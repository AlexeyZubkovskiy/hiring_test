package com.hiringtest.matchkeeper.application.di

import com.hiringtest.matchkeeper.application.di.viewmodel.ViewModelModule
import com.hiringtest.matchkeeper.presentation.changematch.di.ChangeMatchComponent
import com.hiringtest.matchkeeper.presentation.matches.di.MatchesComponent
import com.hiringtest.matchkeeper.presentation.players.PlayersFragment
import com.hiringtest.matchkeeper.presentation.players.di.PlayersComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		ViewModelModule::class,
		AppModule::class
	]
)
interface AppComponent {

	fun matchesComponent(): MatchesComponent.Factory

	fun changeMatchComponent(): ChangeMatchComponent.Factory

	fun playersComponent(): PlayersComponent.Factory

}
