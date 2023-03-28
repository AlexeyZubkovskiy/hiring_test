package com.hiringtest.matchkeeper.domain.usecase.players.impl

import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.domain.entities.PlayerTotal


/**
 * This method is not optimized by speed or resources,
 * because in real app such kind of information usually stores in database or backend, so
 * the place for it is data source.
 * */
fun List<Match>.toPlayersTotal(): List<PlayerTotal> {
	//All those actions can be done in one chain,
	//but I think that using local variables usually makes code easy to read and self documented.
	val players = this.flatMap { listOf(it.first.player, it.second.player) }.toSet()

	val playersMatches = players.associateWithTo(mutableMapOf()) { player ->
		this.filter { match -> (match.first.player == player) || (match.second.player == player) }
	}

	return playersMatches.map { (player, matches) ->
		val gamesWon = matches.count { match -> match.winner == player }
		PlayerTotal(player = player, gamesPlayed = matches.size, gamesWon = gamesWon)
	}
}