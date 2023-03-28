package com.hiringtest.matchkeeper.domain.usecase.players.impl

import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import com.hiringtest.matchkeeper.domain.usecase.players.GetKingOfTheGameUseCase
import io.reactivex.Single
import java.util.Optional
import javax.inject.Inject

/**
 * In terms of performance for current realisation this not optimised approach,
 * because we do almost the same calculation in [GetPlayersTotalSortedDescendingUseCaseImpl],
 * but in real app data like players total should be cached or stored somewhere for fast access.
* */
class GetKingOfTheGameUseCaseImpl @Inject constructor(
	private val matchesRepository: MatchesRepository
) : GetKingOfTheGameUseCase {
	override fun getKingOfTheGame(): Single<Optional<PlayerTotal>> = matchesRepository
		.getAllMatches()
		.map { matches ->
			matches.toPlayersTotal()
				.maxByOrNull { it.rating }
				?.let { king -> Optional.of(king) } ?: Optional.empty()
		}
}