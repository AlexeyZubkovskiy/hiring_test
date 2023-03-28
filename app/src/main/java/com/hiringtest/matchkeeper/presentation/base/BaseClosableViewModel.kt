package com.hiringtest.matchkeeper.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject


abstract class BaseClosableViewModel<T : BaseViewModel.State> : BaseViewModel<T>() {

	private val mutableCloseData: MutableLiveData<Boolean> = MutableLiveData(false)

	val closeData: LiveData<Boolean> get() = mutableCloseData

	protected fun close() {
		mutableCloseData.value = true
	}

}