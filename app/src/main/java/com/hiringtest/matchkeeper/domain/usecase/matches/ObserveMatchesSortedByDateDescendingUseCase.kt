package com.hiringtest.matchkeeper.domain.usecase.matches

import com.hiringtest.matchkeeper.data.entities.Match
import io.reactivex.Observable

interface ObserveMatchesSortedByDateDescendingUseCase {
	fun observeMatchesSortedByDateUseCase(): Observable<List<Match>>
}