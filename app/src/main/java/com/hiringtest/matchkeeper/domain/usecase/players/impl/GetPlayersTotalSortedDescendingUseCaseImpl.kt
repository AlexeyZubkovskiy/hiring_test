package com.hiringtest.matchkeeper.domain.usecase.players.impl

import com.hiringtest.matchkeeper.data.entities.Match
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

/**
 * This method is not optimized by speed or resources,
 * because in real app such kind of information usually stores in database or backend, so
 * the place for it is data source.
 * */
private fun List<Match>.toPlayersTotal(): List<PlayerTotal> {
	//All those actions can be done in one chain,
	//but I think that using local variables usually makes code easy to read and self documented.
	val players = this.flatMap { listOf(it.first.player, it.second.player) }.toSet()

	val playersMatches = players.associateWithTo(mutableMapOf()) { player ->
		this.filter { match -> (match.first.player == player) || (match.second.player == player) }
	}

	return playersMatches.map { (player, matches) ->
		val gamesWon = matches.count { match -> match.winner == player }
		PlayerTotal(player = player, gamesPlayed = matches.size, gamesWon = gamesWon)
	}
}