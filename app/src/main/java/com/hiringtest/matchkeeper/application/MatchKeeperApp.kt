package com.hiringtest.matchkeeper.application

import android.app.Application
import com.hiringtest.matchkeeper.application.di.AppComponent
import com.hiringtest.matchkeeper.application.di.AppModule
import com.hiringtest.matchkeeper.application.di.DaggerAppComponent

class MatchKeeperApp: Application() {

	val appComponent: AppComponent by lazy { initAppComponent() }

	override fun onCreate() {
		super.onCreate()
		appComponent
	}

	private fun initAppComponent(): AppComponent = DaggerAppComponent
		.builder()
		.appModule(AppModule(this))
		.build()

}