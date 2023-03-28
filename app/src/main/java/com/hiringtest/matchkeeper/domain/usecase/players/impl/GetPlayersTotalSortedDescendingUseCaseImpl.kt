package com.hiringtest.matchkeeper.domain.usecase.players.impl

import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import com.hiringtest.matchkeeper.domain.usecase.players.GetPlayersTotalSortedDescendingUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetPlayersTotalSortedDescendingUseCaseImpl @Inject constructor(
	private val matchesRepository: MatchesRepository
) : GetPlayersTotalSortedDescendingUseCase {
	override fun getPlayersTotal(sortedBy: GetPlayersTotalSortedDescendingUseCase.SortedBy): Single<List<PlayerTotal>> =
		matchesRepository.getAllMatches().map { matches ->
			matches.toPlayersTotal()
				.sortedByDescending { playerTotal ->
					when (sortedBy) {
						GetPlayersTotalSortedDescendingUseCase.SortedBy.GAMES_PLAYED -> playerTotal.gamesPlayed
						GetPlayersTotalSortedDescendingUseCase.SortedBy.GAMES_WON    -> playerTotal.gamesWon
					}
				}
		}
}

