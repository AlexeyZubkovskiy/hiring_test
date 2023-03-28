package com.hiringtest.matchkeeper.data.datasource

import com.hiringtest.matchkeeper.data.entities.Match

//Used here just like architecture sample, instead of data source usually uses different things like database or retrofit service,
//also all stuff related to the saving/deleting/updating matches should be located in data source in real project,
//and only cached (if needed) in repository. But here I just simplified it to reduce amount of code and left this just for example.
interface MatchesDataSource {
	fun getMatches(): List<Match>
}