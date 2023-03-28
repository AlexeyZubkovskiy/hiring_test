package com.hiringtest.matchkeeper.presentation.changematch

import com.hiringtest.matchkeeper.application.Schedulers
import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.entities.Person
import com.hiringtest.matchkeeper.data.entities.PlayersScore
import com.hiringtest.matchkeeper.domain.usecase.matches.CreateMatchUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.GetMatchUseCase
import com.hiringtest.matchkeeper.domain.usecase.matches.UpdateMatchUseCase
import com.hiringtest.matchkeeper.presentation.base.BaseClosableViewModel
import com.hiringtest.matchkeeper.presentation.base.BaseViewModel
import java.util.Date
import javax.inject.Inject

/**
 * For this functionality missed validation logic,
 * so lets assume that name of player can be every string, case
 * when name of first and second player are the same also don't handled here.
 * State with error when validation failed or wrong input can be easily handled with this architecture.
 * */
class ChangeMatchViewModel @Inject constructor(
	private val getMatchUseCase: GetMatchUseCase,
	private val updateMatchUseCase: UpdateMatchUseCase,
	private val createMatchUseCase: CreateMatchUseCase,
	private val schedulers: Schedulers
) : BaseClosableViewModel<ChangeMatchViewModel.State>() {

	override fun getInitialState(): State = State.Loading

	fun initState(isEdit: Boolean, matchId: String) {
		if (isEdit) {
			setupEditMatchState(matchId)
		} else {
			setupNewMatchState()
		}
	}

	private fun setupNewMatchState() {
		mutableState.value = State.Content.Create(defaultMatchData)
	}

	private fun setupEditMatchState(matchId: String) {
		disposables + getMatchUseCase.getMatch(matchId)
			.observeOn(schedulers.main)
			.subscribe(
				{ match ->
					mutableState.value = State.Content.Edit(
						input = match.toMatchData(),
						original = match
					)

				},
				::handleError
			)
	}

	//This bunch of methods for first and second player can be refactored
	//to one method that receives some instance of Action subclasses, but I decided
	//to leave it here, because there is not so much such actions
	fun firstPlayerNameChanged(name: String) {
		doIfStateContent(currentState) { state ->
			val newState: State.Content = when (state) {
				is State.Content.Create -> state.copy(input = state.input.copyFirst(name = name))
				is State.Content.Edit   -> state.copy(input = state.input.copyFirst(name = name))
				else                    -> wrongState(state)
			}
			mutableState.value = newState
		}
	}

	fun firstPlayerScoreChanged(value: Int) {
		doIfStateContent(currentState) { state ->
			val newState: State.Content = when (state) {
				is State.Content.Create -> state.copy(input = state.input.copyFirst(score = value))
				is State.Content.Edit   -> state.copy(input = state.input.copyFirst(score = value))
				else                    -> wrongState(state)
			}
			mutableState.value = newState
		}
	}

	fun secondPlayerNameChanged(name: String) {
		doIfStateContent(currentState) { state ->
			val newState: State.Content = when (state) {
				is State.Content.Create -> state.copy(input = state.input.copySecond(name = name))
				is State.Content.Edit   -> state.copy(input = state.input.copySecond(name = name))
				else                    -> wrongState(state)
			}
			mutableState.value = newState
		}
	}

	fun secondPlayerScoreChanged(value: Int) {
		doIfStateContent(currentState) { state ->
			val newState: State.Content = when (state) {
				is State.Content.Create -> state.copy(input = state.input.copySecond(score = value))
				is State.Content.Edit   -> state.copy(input = state.input.copySecond(score = value))
				else                    -> wrongState(state)
			}
			mutableState.value = newState
		}
	}

	fun dateChanged(date: Date) {
		doIfStateContent(currentState) { state ->
			val newState: State.Content = when (state) {
				is State.Content.Create -> state.copy(input = state.input.copy(date = date))
				is State.Content.Edit   -> state.copy(input = state.input.copy(date = date))
				else                    -> wrongState(state)
			}
			mutableState.value = newState
		}
	}

	fun saveMatch() {
		when (val state = currentState) {
			is State.Content.Edit   -> updateMatch(state)
			is State.Content.Create -> createMatch(state)
			else                    -> Unit //sending error also possible
		}
	}

	private fun updateMatch(state: State.Content.Edit) {
		val (input, original) = state
		disposables + updateMatchUseCase.updateMatch(
			original.copy(
				date = input.date,
				first = input.first,
				second = input.second
			)
		).observeOn(schedulers.main)
			.subscribe(
				{ close() },
				::handleError
			)
	}

	private fun createMatch(state: State.Content.Create) {
		disposables + createMatchUseCase.createMatch(
			CreateMatchUseCase.NewMatchData(
				date = state.input.date,
				first = state.input.first,
				second = state.input.second
			)
		).observeOn(schedulers.main)
			.subscribe(
				{ close() },
				::handleError
			)
	}

	private fun handleError(error: Throwable) {
		mutableState.value = State.Error(error.localizedMessage.orEmpty())
	}

	sealed class State : BaseViewModel.State {
		abstract class Content : State() {
			data class Edit(val input: MatchData, val original: Match) : Content()

			data class Create(val input: MatchData) : Content()
		}

		object Loading : State()
		data class Error(val cause: String) : State()
	}

	data class MatchData(
		val date: Date,
		val first: PlayersScore,
		val second: PlayersScore
	)
}

private val defaultMatchData
	get() = ChangeMatchViewModel.MatchData(
		date = Date(),
		first = PlayersScore.EMPTY,
		second = PlayersScore.EMPTY
	)

private fun Match.toMatchData(): ChangeMatchViewModel.MatchData = ChangeMatchViewModel.MatchData(
	date = this.date,
	first = this.first,
	second = this.second
)

private inline fun doIfStateContent(
	state: ChangeMatchViewModel.State,
	action: (ChangeMatchViewModel.State.Content) -> Unit
) {
	if (state is ChangeMatchViewModel.State.Content) {
		action(state)
	}
}

@Throws(IllegalStateException::class)
private fun wrongState(state: ChangeMatchViewModel.State): Nothing {
	throw IllegalStateException("State ${state.javaClass.name} is not supported")
}

private fun ChangeMatchViewModel.MatchData.copyFirst(
	score: Int = this.first.score,
	name: String = this.first.player.name
): ChangeMatchViewModel.MatchData {
	val newFirst = this.first.copy(
		player = Person(name),
		score = score
	)
	return this.copy(first = newFirst)
}

private fun ChangeMatchViewModel.MatchData.copySecond(
	score: Int = this.second.score,
	name: String = this.second.player.name
): ChangeMatchViewModel.MatchData {
	val newSecond = this.second.copy(
		player = Person(name),
		score = score
	)
	return this.copy(second = newSecond)
}