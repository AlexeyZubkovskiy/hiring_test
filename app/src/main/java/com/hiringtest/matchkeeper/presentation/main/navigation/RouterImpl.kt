package com.hiringtest.matchkeeper.presentation.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.hiringtest.matchkeeper.R
import com.hiringtest.matchkeeper.presentation.changematch.ChangeMatchFragment


class RouterImpl(private val navController: NavController) : Router {

	override fun showEditMatch(matchId: String) {
		navController.navigate(
			R.id.destination_change_match,
			ChangeMatchFragment.createBundle(isEdit = true, matchId = matchId)
		)
	}

	override fun showCreateNewMatch() {
		navController.navigate(
			R.id.destination_change_match,
			ChangeMatchFragment.createBundle()
		)
	}

	override fun showPlayers() {
		navController.navigate(R.id.destination_players)
	}

	override fun back() {
		navController.popBackStack()
	}

}