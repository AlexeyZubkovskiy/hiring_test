package com.hiringtest.matchkeeper.domain.usecase.matches

import com.hiringtest.matchkeeper.data.entities.Match
import io.reactivex.Completable

interface UpdateMatchUseCase {

	fun updateMatch(match: Match): Completable

}