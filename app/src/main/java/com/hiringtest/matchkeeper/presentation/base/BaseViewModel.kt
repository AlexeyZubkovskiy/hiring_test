package com.hiringtest.matchkeeper.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.NoSuchElementException


abstract class BaseViewModel<T : BaseViewModel.State> : ViewModel() {

	protected val disposables: MutableList<Disposable> = mutableListOf()

	//I decided to use live data instead of rx subject,
	//because live data works much better for cases when I need receive state immediately.
	//Also live data has "out of the box" support of the lifecycle unlike rxjava.
	@Suppress("LeakingThis")
	protected val mutableState: MutableLiveData<T> = MutableLiveData(getInitialState())

	val state: LiveData<T> get() = mutableState

	protected val currentState: T
		get() = mutableState.value ?: throw IllegalStateException("State is null")

	abstract fun getInitialState(): T

	override fun onCleared() {
		super.onCleared()
		disposables.forEach { disposable ->
			if (!disposable.isDisposed) {
				disposable.dispose()
			}
		}
	}

	interface State

	protected operator fun MutableList<Disposable>.plus(disposable: Disposable) =
		this.add(disposable)
}

