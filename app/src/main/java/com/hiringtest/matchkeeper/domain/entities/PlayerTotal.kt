package com.hiringtest.matchkeeper.domain.entities

import com.hiringtest.matchkeeper.data.entities.Person

data class PlayerTotal(
	val player: Person,
	val gamesPlayed: Int,
	val gamesWon: Int
) {
	val rating: Float
		get() = if (gamesWon != 0) {
			gamesWon.toFloat() / gamesPlayed.toFloat()
		} else 0f

}