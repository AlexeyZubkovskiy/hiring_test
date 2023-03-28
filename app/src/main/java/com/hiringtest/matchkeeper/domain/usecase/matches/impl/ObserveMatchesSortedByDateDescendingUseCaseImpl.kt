package com.hiringtest.matchkeeper.domain.usecase.matches.impl

import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.domain.usecase.matches.ObserveMatchesSortedByDateDescendingUseCase
import io.reactivex.Observable
import javax.inject.Inject


class ObserveMatchesSortedByDateDescendingUseCaseImpl @Inject constructor(
	private val repository: MatchesRepository
) : ObserveMatchesSortedByDateDescendingUseCase {
	override fun observeMatchesSortedByDateUseCase(): Observable<List<Match>> =
		repository.observeAllMatches()
			.map { matches -> matches.sortedByDescending { match -> match.date } }
}