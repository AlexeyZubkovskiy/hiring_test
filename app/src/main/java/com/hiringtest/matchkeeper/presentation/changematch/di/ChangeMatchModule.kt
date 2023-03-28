package com.hiringtest.matchkeeper.presentation.changematch.di

import androidx.lifecycle.ViewModel
import com.hiringtest.matchkeeper.application.di.viewmodel.ViewModelKey
import com.hiringtest.matchkeeper.presentation.changematch.ChangeMatchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChangeMatchModule {

	@Binds
	@IntoMap
	@ViewModelKey(ChangeMatchViewModel::class)
	abstract fun bindViewModel(viewModel: ChangeMatchViewModel): ViewModel

}