package com.hiringtest.matchkeeper.domain.usecase.matches

import com.hiringtest.matchkeeper.data.entities.Match
import io.reactivex.Single


interface GetMatchUseCase {

	fun getMatch(id: String): Single<Match>

}