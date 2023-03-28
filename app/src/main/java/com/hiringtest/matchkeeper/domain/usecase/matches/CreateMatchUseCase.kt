package com.hiringtest.matchkeeper.domain.usecase.matches

import com.hiringtest.matchkeeper.data.entities.PlayersScore
import io.reactivex.Completable
import java.util.Date

interface CreateMatchUseCase {

	fun createMatch(data: NewMatchData) : Completable

	data class NewMatchData(
		val date: Date,
		val first: PlayersScore,
		val second: PlayersScore
	)
}