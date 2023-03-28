package com.hiringtest.matchkeeper.presentation.players

import com.hiringtest.matchkeeper.application.Schedulers
import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import com.hiringtest.matchkeeper.domain.usecase.players.GetPlayersTotalSortedDescendingUseCase
import com.hiringtest.matchkeeper.presentation.base.BaseClosableViewModel
import com.hiringtest.matchkeeper.presentation.base.BaseViewModel
import com.hiringtest.matchkeeper.utils.exhaustive
import javax.inject.Inject

class PlayersViewModel @Inject constructor(
	private val getPlayersTotalUseCase: GetPlayersTotalSortedDescendingUseCase,
	private val schedulers: Schedulers
) : BaseClosableViewModel<PlayersViewModel.State>() {

	private val defaultSortOrder get() = SortOrder.GAMES_PLAYED_DESCENDING

	override fun getInitialState(): State = State.Loading

	fun loadInitial() {
		loadPlayersTotal(defaultSortOrder)
	}

	fun sortOrderChanged(sortOrder: SortOrder) {
		loadPlayersTotal(sortOrder)
	}

	private fun loadPlayersTotal(sortOrder: SortOrder) {
		disposables + getPlayersTotalUseCase.getPlayersTotal(sortOrder.toDomain())
			.observeOn(schedulers.main)
			.subscribe(
				{ totals -> setupPlayersTotal(sortOrder, totals) },
				::handleError
			)
	}

	private fun setupPlayersTotal(sortOrder: SortOrder, playersTotal: List<PlayerTotal>) {
		mutableState.value = State.Content(
			sortOrder = sortOrder,
			playersTotal = playersTotal
		)
	}

	private fun handleError(error: Throwable) {
		mutableState.value = State.Error(error.localizedMessage.orEmpty())
	}

	sealed class State : BaseViewModel.State {
		object Loading : State()
		data class Error(val cause: String) : State()
		data class Content(val sortOrder: SortOrder, val playersTotal: List<PlayerTotal>) : State()
	}

	enum class SortOrder {
		GAMES_PLAYED_DESCENDING,
		GAMES_WON_DESCENDING
	}

}

private fun PlayersViewModel.SortOrder.toDomain(): GetPlayersTotalSortedDescendingUseCase.SortedBy =
	when (this) {
		PlayersViewModel.SortOrder.GAMES_PLAYED_DESCENDING -> GetPlayersTotalSortedDescendingUseCase.SortedBy.GAMES_PLAYED
		PlayersViewModel.SortOrder.GAMES_WON_DESCENDING    -> GetPlayersTotalSortedDescendingUseCase.SortedBy.GAMES_WON
	}.exhaustive