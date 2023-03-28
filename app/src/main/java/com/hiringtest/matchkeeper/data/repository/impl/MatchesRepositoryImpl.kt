package com.hiringtest.matchkeeper.data.repository.impl

import com.hiringtest.matchkeeper.application.Schedulers
import com.hiringtest.matchkeeper.data.datasource.MatchesDataSource
import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.repository.MatchesRepository
import com.hiringtest.matchkeeper.utils.lazyUnsafe
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
	private val matchesDataSource: MatchesDataSource,
	private val schedulers: Schedulers
) : MatchesRepository {

	//For this case didn't think about optimised collection for this case, because this is example
	//Also saving and updating of all matches is done here, but in real project it should be done inside data source
	//Repository also can contain some cache.
	private val matches: MutableList<Match> = matchesDataSource.getMatches().toMutableList()

	//Used for notifying observers for cases when we change matches list
	private val matchesSubject: PublishSubject<MutableList<Match>> by lazy { PublishSubject.create() }

	override fun getAllMatches(): Single<List<Match>> = Single.just(matches)
		.subscribeOn(schedulers.io)
		.map { it } //duct tape for mapping mutable list to list

	override fun observeAllMatches(): Observable<List<Match>> = matchesSubject
		.subscribeOn(schedulers.io)
		.map { it } //duct tape for mapping mutable list to list


	override fun getMatch(id: String): Single<Match> = Single.fromCallable {
		val match = matches.firstOrNull { match ->
			match.id == id
		}
		match ?: throw MatchNotFoundException("Match with id = $id doesn't exist")
	}.subscribeOn(schedulers.io)


	override fun updateMatch(match: Match): Completable = Completable.fromAction {
		//for this method lets assume that we call it only for update existing match,
		//we don't care about cases when it can't exist in a list
		val old = matches.firstOrNull { oldMatch -> oldMatch.id == match.id }
		old?.let { notNullOld ->
			matches.remove(notNullOld)
			matches.add(match)
			//notify observers about matches list changes
			matchesSubject.onNext(matches)
		}
	}.subscribeOn(schedulers.io)

	override fun addMatch(match: Match): Completable = Completable.fromAction {
		matches.add(match)
		//notify observers about matches list changes
		matchesSubject.onNext(matches)
	}.subscribeOn(schedulers.io)

	override fun removeMatch(match: Match): Completable = Completable.fromAction {
		matches.remove(match)
		//notify observers about matches list changes
		matchesSubject.onNext(matches)
	}.subscribeOn(schedulers.io)

}

private class MatchNotFoundException(message: String) : Throwable(message)