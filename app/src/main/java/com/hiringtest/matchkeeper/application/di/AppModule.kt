package com.hiringtest.matchkeeper.application.di

import android.app.Application
import android.content.Context
import com.hiringtest.matchkeeper.application.MatchKeeperApp
import com.hiringtest.matchkeeper.application.Schedulers
import com.hiringtest.matchkeeper.data.di.MatchesDataModule
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers as RxSchedulers
import javax.inject.Singleton

@Module(includes = [MatchesDataModule::class])
class AppModule(val app: MatchKeeperApp) {

	@Provides
	@Singleton
	fun provideApplication(): Application = app

	@Singleton
	@Provides
	fun provideSchedulers(): Schedulers = object : Schedulers {
		override val main: Scheduler get() = AndroidSchedulers.mainThread()
		override val io: Scheduler get() = RxSchedulers.io()
		override val computation: Scheduler get() = RxSchedulers.computation()
		override val single: Scheduler get() = RxSchedulers.single()
	}

}