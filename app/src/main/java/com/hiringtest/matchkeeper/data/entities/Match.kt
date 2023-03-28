package com.hiringtest.matchkeeper.data.entities

import java.util.Date

data class Match(
	val id: String,
	val date: Date,
	val first: PlayersScore,
	val second: PlayersScore
) {

	/**
	 * @return winner of the game if scores are not equals, otherwise - null
	 * */
	val winner: Person? get() = if (first.score != second.score) maxOf(first, second).player else null

}
