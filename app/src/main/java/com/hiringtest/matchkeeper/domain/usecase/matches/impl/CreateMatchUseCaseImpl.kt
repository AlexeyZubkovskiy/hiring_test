package com.hiringtest.matchkeeper.domain.usecase.matches.impl

import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.domain.usecase.matches.CreateMatchUseCase
import com.hiringtest.matchkeeper.utils.generateId
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CreateMatchUseCaseImpl @Inject constructor(
	private val matchesRepository: MatchesRepository
) : CreateMatchUseCase {
	override fun createMatch(data: CreateMatchUseCase.NewMatchData): Completable =
		Single.just(data.toNewMatch(generateId()))
			.flatMapCompletable(matchesRepository::addMatch)
}

private fun CreateMatchUseCase.NewMatchData.toNewMatch(id: String): Match = Match(
	id = id,
	date = this.date,
	first = this.first,
	second = this.second
)