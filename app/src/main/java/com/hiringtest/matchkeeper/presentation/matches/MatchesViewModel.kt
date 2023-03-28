package com.hiringtest.matchkeeper.presentation.matches

import com.hiringtest.matchkeeper.application.Schedulers
import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.domain.usecase.matches.GetMatchesSortedByDateDescendingUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.ObserveMatchesSortedByDateDescendingUseCase
import com.hiringtest.matchkeeper.presentation.base.BaseViewModel

import javax.inject.Inject

class MatchesViewModel @Inject constructor(
	private val getMatchesSortedByDateUseCase: GetMatchesSortedByDateDescendingUseCase,
	private val observeMatchesSortedByDateDescendingUseCase: ObserveMatchesSortedByDateDescendingUseCase,
	private val schedulers: Schedulers
) : BaseViewModel<MatchesViewModel.State>() {

	init {
		initialAction()
	}

	override fun getInitialState(): State = State.Loading

	private fun initialAction() {
		loadMatches()
		observeMatches()
	}

	private fun loadMatches() {
		disposables + getMatchesSortedByDateUseCase
			.getMatchesSortedByDate()
			.observeOn(schedulers.main)
			.subscribe(
				this::updateMatches,
				this::handleError
			)
	}

	private fun observeMatches() {
		disposables + observeMatchesSortedByDateDescendingUseCase
			.observeMatchesSortedByDateUseCase()
			.observeOn(schedulers.main)
			.subscribe(
				this::updateMatches,
				this::handleError
			)
	}

	private fun updateMatches(matches: List<Match>) {
		mutableState.value = State.Content(matches)
	}

	private fun handleError(error: Throwable) {
		mutableState.value = State.Error(error.localizedMessage.orEmpty())
	}

	sealed class State : BaseViewModel.State {
		class Content(val matches: List<Match>) : State()
		object Loading : State()
		class Error(val cause: String) : State()
	}

}
