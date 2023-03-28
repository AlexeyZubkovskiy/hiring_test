package com.hiringtest.matchkeeper.data.datasource.impl

import com.hiringtest.matchkeeper.data.datasource.MatchesDataSource
import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.utils.generateMatches
import javax.inject.Inject

class MatchesDataSourceImpl @Inject constructor() : MatchesDataSource {

	private val matches:List<Match> = generateMatches()

	override fun getMatches(): List<Match> = matches

}