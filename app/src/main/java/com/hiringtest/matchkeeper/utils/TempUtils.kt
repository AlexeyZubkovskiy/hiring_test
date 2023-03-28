package com.hiringtest.matchkeeper.utils

import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.data.entities.Person
import com.hiringtest.matchkeeper.data.entities.PlayersScore
import java.util.Date


fun generateMatches(): List<Match> =  listOf(
	Match(
		id = generateId(),
		date = 1678523465000.toDate(),
		first = PlayersScore(Person("Amos"), 4),
		second = PlayersScore(Person("Diego"), 5)
	),
	Match(
		id = generateId(),
		date = 1678437065000.toDate(),
		first = PlayersScore(Person("Amos"), 1),
		second = PlayersScore(Person("Diego"), 5)
	),
	Match(
		id = generateId(),
		date = 1678350665000.toDate(),
		first = PlayersScore(Person("Amos"), 2),
		second = PlayersScore(Person("Diego"), 5)
	),
	Match(
		id = generateId(),
		date = 1678264265000.toDate(),
		first = PlayersScore(Person("Amos"), 0),
		second = PlayersScore(Person("Diego"), 5)
	),
	Match(
		id = generateId(),
		date = 1678264265000.toDate(),
		first = PlayersScore(Person("Amos"), 6),
		second = PlayersScore(Person("Diego"), 5)
	),
	Match(
		id = generateId(),
		date = 1678164265000.toDate(),
		first = PlayersScore(Person("Amos"), 5),
		second = PlayersScore(Person("Diego"), 2)
	),
	Match(
		id = generateId(),
		date = 1678064265000.toDate(),
		first = PlayersScore(Person("Joel"), 0),
		second = PlayersScore(Person("Diego"), 5)
	),
	Match(
		id = generateId(),
		date = 1677864265000.toDate(),
		first = PlayersScore(Person("Tim"), 4),
		second = PlayersScore(Person("Amos"), 5)
	),
	Match(
		id = generateId(),
		date = 1677464265000.toDate(),
		first = PlayersScore(Person("Tim"), 5),
		second = PlayersScore(Person("Amos"), 2)
	),
	Match(
		id = generateId(),
		date = 1677264265000.toDate(),
		first = PlayersScore(Person("Amos"), 5),
		second = PlayersScore(Person("Joel"), 4)
	),
	Match(
		id = generateId(),
		date = 1677064265000.toDate(),
		first = PlayersScore(Person("Joel"), 5),
		second = PlayersScore(Person("Tim"), 2)
	)
)

private fun Long.toDate(): Date = Date(this)