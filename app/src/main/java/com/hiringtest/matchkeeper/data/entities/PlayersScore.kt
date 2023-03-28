package com.hiringtest.matchkeeper.data.entities

import com.hiringtest.matchkeeper.utils.EMPTY

data class PlayersScore(val player: Person, val score: Int) : Comparable<PlayersScore> {

	companion object {
		val EMPTY: PlayersScore = PlayersScore(player = Person(String.EMPTY), score = 0)
	}

	override fun compareTo(other: PlayersScore): Int = when {
		this.score == other.score -> 0
		this.score > other.score  -> 1
		else                      -> -1
	}

}
