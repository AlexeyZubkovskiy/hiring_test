package com.hiringtest.matchkeeper.data.repository

import com.hiringtest.matchkeeper.data.entities.Match
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MatchesRepository {

	fun getAllMatches(): Single<List<Match>>

	fun observeAllMatches(): Observable<List<Match>>

	fun getMatch(id: String): Single<Match>

	fun updateMatch(match: Match): Completable

	fun addMatch(match: Match): Completable

	fun removeMatch(match: Match): Completable

}