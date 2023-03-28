package com.hiringtest.matchkeeper.utils

import android.content.Context
import com.hiringtest.matchkeeper.application.MatchKeeperApp
import com.hiringtest.matchkeeper.application.di.AppComponent

val Context.appComponent: AppComponent
	get() = when (this) {
		is MatchKeeperApp -> this.appComponent
		else              -> this.applicationContext.appComponent
	}