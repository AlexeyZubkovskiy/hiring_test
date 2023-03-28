package com.hiringtest.matchkeeper.domain.usecase.matches.impl

import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.domain.usecase.matches.UpdateMatchUseCase
import io.reactivex.Completable
import javax.inject.Inject


class UpdateMatchUseCaseImpl @Inject constructor(
	private val matchesRepository: MatchesRepository
) : UpdateMatchUseCase {

	override fun updateMatch(match: Match): Completable =
		matchesRepository.updateMatch(match)

}