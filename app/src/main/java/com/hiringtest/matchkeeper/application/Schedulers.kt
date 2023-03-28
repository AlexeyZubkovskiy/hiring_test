package com.hiringtest.matchkeeper.application

import io.reactivex.Scheduler

interface Schedulers {

	val main: Scheduler

	val io: Scheduler

	val computation: Scheduler

	val single: Scheduler

}