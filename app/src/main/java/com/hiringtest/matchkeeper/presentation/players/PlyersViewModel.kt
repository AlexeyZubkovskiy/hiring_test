package com.hiringtest.matchkeeper.presentation.players

import com.hiringtest.matchkeeper.application.Schedulers
import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import com.hiringtest.matchkeeper.domain.usecase.players.GetKingOfTheGameUseCase
import com.hiringtest.matchkeeper.domain.usecase.players.GetPlayersTotalSortedDescendingUseCase
import com.hiringtest.matchkeeper.presentation.base.BaseClosableViewModel
import com.hiringtest.matchkeeper.presentation.base.BaseViewModel
import com.hiringtest.matchkeeper.utils.exhaustive
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class PlayersViewModel @Inject constructor(
	private val getPlayersTotalUseCase: GetPlayersTotalSortedDescendingUseCase,
	private val getKingOfTheGameUseCase: GetKingOfTheGameUseCase,
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
		disposables + Single.zip(
			getPlayersTotalUseCase.getPlayersTotal(sortOrder.toDomain()),
			getKingOfTheGameUseCase.getKingOfTheGame()
		) { playersTotal, optionalKingOfGame ->
			val kingOfGame = optionalKingOfGame.orElse(null)
			playersTotal to kingOfGame
		}
			.observeOn(schedulers.main)
			.subscribe(
				{ (totals, kingOfGame) -> setupPlayersTotal(sortOrder, totals, kingOfGame) },
				::handleError
			)
	}

	private fun setupPlayersTotal(
		sortOrder: SortOrder,
		playersTotal: List<PlayerTotal>,
		kingOfGame: PlayerTotal?
	) {
		mutableState.value = State.Content(
			sortOrder = sortOrder,
			playersTotal = playersTotal,
			kingOfGame = kingOfGame
		)
	}

	private fun handleError(error: Throwable) {
		mutableState.value = State.Error(error.localizedMessage.orEmpty())
	}

	sealed class State : BaseViewModel.State {
		object Loading : State()
		data class Error(val cause: String) : State()
		data class Content(
			val sortOrder: SortOrder,
			val playersTotal: List<PlayerTotal>,
			val kingOfGame: PlayerTotal?
		) : State()
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