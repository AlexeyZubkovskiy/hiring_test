package com.hiringtest.matchkeeper.presentation.players.di

import androidx.lifecycle.ViewModel
import com.hiringtest.matchkeeper.application.di.viewmodel.ViewModelKey
import com.hiringtest.matchkeeper.presentation.players.PlayersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlayersModule {

	@Binds
	@IntoMap
	@ViewModelKey(PlayersViewModel::class)
	abstract fun bindViewModel(viewModel: PlayersViewModel): ViewModel

}