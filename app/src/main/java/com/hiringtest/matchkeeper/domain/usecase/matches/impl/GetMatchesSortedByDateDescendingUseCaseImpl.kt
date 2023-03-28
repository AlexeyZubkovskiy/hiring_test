package com.hiringtest.matchkeeper.domain.usecase.matches.impl

import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.domain.usecase.matches.GetMatchesSortedByDateDescendingUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMatchesSortedByDateDescendingUseCaseImpl @Inject constructor(
	private val matchesRepository: MatchesRepository
) : GetMatchesSortedByDateDescendingUseCase {
	override fun getMatchesSortedByDate(): Single<List<Match>> = matchesRepository.getAllMatches()
		.map { matches -> matches.sortedByDescending { match -> match.date } }
}