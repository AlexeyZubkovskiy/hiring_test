package com.hiringtest.matchkeeper.presentation.main.navigation

/**
 * Router used for navigation,
 * also this approach can help in cases when we need to change our navigation approach, so
 * we can change our navigation library/approach almost painless, because we care only about
 * another interface implementation.
* */
interface Router {

	fun showEditMatch(matchId: String)

	fun showCreateNewMatch()

	fun showPlayers()

	fun back()

}