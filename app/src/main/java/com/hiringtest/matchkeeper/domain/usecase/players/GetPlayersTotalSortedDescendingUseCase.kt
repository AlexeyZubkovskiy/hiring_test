package com.hiringtest.matchkeeper.domain.usecase.players

import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import io.reactivex.Single

interface GetPlayersTotalSortedDescendingUseCase {

	fun getPlayersTotal(sortedBy: SortedBy): Single<List<PlayerTotal>>

	enum class SortedBy {
		GAMES_PLAYED,
		GAMES_WON
	}

}