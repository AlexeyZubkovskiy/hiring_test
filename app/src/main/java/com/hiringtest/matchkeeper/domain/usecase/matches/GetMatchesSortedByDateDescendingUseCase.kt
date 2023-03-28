package com.hiringtest.matchkeeper.domain.usecase.matches

import com.hiringtest.matchkeeper.data.entities.Match
import io.reactivex.Single

interface GetMatchesSortedByDateDescendingUseCase {
	fun getMatchesSortedByDate(): Single<List<Match>>
}