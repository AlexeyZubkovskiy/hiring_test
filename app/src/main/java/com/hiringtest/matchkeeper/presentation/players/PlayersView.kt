package com.hiringtest.matchkeeper.presentation.players

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiringtest.matchkeeper.R
import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import com.hiringtest.matchkeeper.presentation.theme.MatchKeeperTheme
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultColumnItem
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultErrorState
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultLoadingState
import com.hiringtest.matchkeeper.utils.EMPTY

private const val PLAYER_COLUMN_WEIGHT = 0.3f
private const val GAMES_PLAYED_COLUMN_WEIGHT = 0.35f
private const val GAMES_WON_COlUMN_WEIGHT = 0.35f

@Composable
fun PlayersScreenContent(
	modifier: Modifier,
	state: PlayersViewModel.State,
	sortChangeListener: (PlayersViewModel.SortOrder) -> Unit
) {
	when (state) {
		is PlayersViewModel.State.Content -> PlayersContent(
			modifier = modifier,
			state = state,
			sortChangeListener = sortChangeListener
		)
		is PlayersViewModel.State.Error   -> DefaultErrorState(text = state.cause)
		PlayersViewModel.State.Loading    -> DefaultLoadingState()
	}
}

@Composable
private fun PlayersContent(
	modifier: Modifier = Modifier,
	state: PlayersViewModel.State.Content,
	sortChangeListener: (PlayersViewModel.SortOrder) -> Unit
) {

	Column(modifier = modifier) {
		PlayersHeader(
			sortOrder = state.sortOrder,
			sortChangeListener = sortChangeListener
		)

		LazyColumn {
			items(state.playersTotal) { playerTotal ->
				PlayerRow(playerTotal = playerTotal)
			}
		}
	}

}

@Composable
private fun PlayersHeader(
	modifier: Modifier = Modifier,
	sortOrder: PlayersViewModel.SortOrder,
	sortChangeListener: (PlayersViewModel.SortOrder) -> Unit
) {
	Row(modifier = modifier.fillMaxWidth()) {
		Box(
			modifier = Modifier
				.height(42.dp)
				.border(
					BorderStroke(
						0.3.dp,
						if (isSystemInDarkTheme()) Color.White else Color.Black
					)
				)
				.padding(4.dp)
				.weight(PLAYER_COLUMN_WEIGHT),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = stringResource(id = R.string.player),
				fontSize = 20.sp,
				fontWeight = FontWeight.SemiBold,
				maxLines = 1,
				textAlign = TextAlign.Center
			)
		}

		PlayersHeaderSelectableColumnHead(
			modifier = Modifier.weight(GAMES_PLAYED_COLUMN_WEIGHT),
			text = stringResource(id = R.string.games_played),
			isSelected = sortOrder == PlayersViewModel.SortOrder.GAMES_PLAYED_DESCENDING,
			onSelected = { sortChangeListener(PlayersViewModel.SortOrder.GAMES_PLAYED_DESCENDING) }
		)

		PlayersHeaderSelectableColumnHead(
			modifier = Modifier.weight(GAMES_WON_COlUMN_WEIGHT),
			text = stringResource(id = R.string.games_won),
			isSelected = sortOrder == PlayersViewModel.SortOrder.GAMES_WON_DESCENDING,
			onSelected = { sortChangeListener(PlayersViewModel.SortOrder.GAMES_WON_DESCENDING) }
		)
	}
}

@Composable
private fun PlayerRow(modifier: Modifier = Modifier, playerTotal: PlayerTotal) {
	Row(modifier = modifier.fillMaxWidth()) {
		DefaultColumnItem(
			modifier= Modifier.weight(PLAYER_COLUMN_WEIGHT),
			text = playerTotal.player.name,
			scrollable = true
		)
		DefaultColumnItem(
			modifier = Modifier.weight(GAMES_PLAYED_COLUMN_WEIGHT),
			text = playerTotal.gamesPlayed.toString()
		)
		DefaultColumnItem(
			modifier = Modifier.weight(GAMES_WON_COlUMN_WEIGHT),
			text = playerTotal.gamesWon.toString()
		)
	}
}

@Composable
private fun PlayersHeaderSelectableColumnHead(
	modifier: Modifier = Modifier,
	text: String,
	isSelected: Boolean,
	onSelected: () -> Unit
) {
	Box(
		modifier = modifier
			.background(if (isSelected) MaterialTheme.colors.secondary else Color.Transparent)
			.height(42.dp)
			.border(BorderStroke(0.3.dp, if (isSystemInDarkTheme()) Color.White else Color.Black))
			.padding(4.dp)
			.clickable {
				if (!isSelected) {
					onSelected()
				}
			},
		contentAlignment = Alignment.Center
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				modifier = Modifier.paddingFromBaseline(top = 0.dp, bottom = 0.dp),
				text = text,
				fontSize = 20.sp,
				fontWeight = FontWeight.SemiBold,
				maxLines = 1,
				textAlign = TextAlign.Center,

				)
			if (isSelected) {
				Image(
					painter = painterResource(id = R.drawable.ic_arrow_down_24),
					contentDescription = String.EMPTY,
					modifier = Modifier
						.size(36.dp)
						.padding(top = 4.dp),
					colorFilter = ColorFilter.tint(
						if (isSystemInDarkTheme()) Color.White else Color.Black
					)
				)
			}
		}
	}
}

@Preview
@Composable
private fun PlayersHeaderSelectableColumnHeadPreview() {
	MatchKeeperTheme {
		PlayersHeaderSelectableColumnHead(
			modifier = Modifier.defaultMinSize(minWidth = 224.dp),
			text = "Games Won",
			isSelected = true
		) {}
	}
}

@Preview
@Composable
private fun PlayersHeaderPreview() {
	MatchKeeperTheme {

		val (sortOrder, onChange) = remember { mutableStateOf(PlayersViewModel.SortOrder.GAMES_PLAYED_DESCENDING) }

		PlayersHeader(
			modifier = Modifier.fillMaxWidth(),
			sortOrder = sortOrder,
			sortChangeListener = {
				onChange(
					when (sortOrder) {
						PlayersViewModel.SortOrder.GAMES_PLAYED_DESCENDING -> PlayersViewModel.SortOrder.GAMES_WON_DESCENDING
						PlayersViewModel.SortOrder.GAMES_WON_DESCENDING    -> PlayersViewModel.SortOrder.GAMES_PLAYED_DESCENDING
					}
				)
			}
		)
	}
}