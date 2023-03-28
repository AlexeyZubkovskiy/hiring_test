package com.hiringtest.matchkeeper.presentation.matches.di

import androidx.lifecycle.ViewModel
import com.hiringtest.matchkeeper.application.di.viewmodel.ViewModelKey
import com.hiringtest.matchkeeper.presentation.matches.MatchesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MatchesModule {

	@Binds
	@IntoMap
	@ViewModelKey(MatchesViewModel::class)
	abstract fun bindViewModel(viewModel: MatchesViewModel): ViewModel

}