package com.hiringtest.matchkeeper.domain.usecase.matches.impl

import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.domain.usecase.matches.GetMatchUseCase
import io.reactivex.Single
import javax.inject.Inject


class GetMatchUseCaseImpl @Inject constructor(private val matchesRepository: MatchesRepository) :
	GetMatchUseCase {
	override fun getMatch(id: String): Single<Match> = matchesRepository.getMatch(id)
}