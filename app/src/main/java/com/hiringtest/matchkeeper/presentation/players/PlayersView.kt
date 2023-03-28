package com.hiringtest.matchkeeper.presentation.players

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiringtest.matchkeeper.R
import com.hiringtest.matchkeeper.data.entities.Person
import com.hiringtest.matchkeeper.domain.entities.PlayerTotal
import com.hiringtest.matchkeeper.presentation.theme.MatchKeeperTheme
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultColumnItem
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultErrorState
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultLoadingState
import com.hiringtest.matchkeeper.utils.EMPTY

private const val PLAYER_COLUMN_WEIGHT = 0.3f
private const val GAMES_PLAYED_COLUMN_WEIGHT = 0.35f
private const val GAMES_WON_COlUMN_WEIGHT = 0.35f

private const val WIN_RATE_FORMAT = "%.3f"

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

		if (state.kingOfGame != null) {
			KingOfTheGameRow(total = state.kingOfGame)
		}

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
			modifier = Modifier.weight(PLAYER_COLUMN_WEIGHT),
			text = playerTotal.player.name,
			scrollable = true
		)
		DefaultColumnItem(
			modifier = Modifier.weight(GAMES_PLAYED_COLUMN_WEIGHT),
			text = playerTotal.gamesPlayed.toString(),
			contentAlignment = Alignment.Center
		)
		DefaultColumnItem(
			modifier = Modifier.weight(GAMES_WON_COlUMN_WEIGHT),
			text = playerTotal.gamesWon.toString(),
			contentAlignment = Alignment.Center
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

@Composable
private fun KingOfTheGameRow(modifier: Modifier = Modifier, total: PlayerTotal) {

	val scrollState = rememberScrollState()

	Row(
		modifier = modifier
			.fillMaxWidth()
			.horizontalScroll(scrollState, enabled = true)
			.height(64.dp)
			.padding(8.dp),
		horizontalArrangement = Arrangement.SpaceEvenly,
		verticalAlignment = Alignment.CenterVertically
	) {

		Icon(
			modifier = Modifier
				.size(42.dp)
				.padding(end = 16.dp),
			painter = painterResource(id = R.drawable.ic_crown_24),
			contentDescription = String.EMPTY,
			tint = if (isSystemInDarkTheme()) Color.Transparent else Color.Black
		)

		Text(
			text = total.player.name,
			style = TextStyle(
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				letterSpacing = 2.sp,
				shadow = Shadow(
					color = if (isSystemInDarkTheme()) Color.White else Color.Black,
					offset = Offset(4f, 4f),
					blurRadius = 8f
				)
			),
			maxLines = 1,
			overflow = TextOverflow.Visible
		)

		val winRate = stringResource(id = R.string.win_rate)

		Text(
			modifier = Modifier.padding(start = 16.dp),
			text = "$winRate: ${WIN_RATE_FORMAT.format(total.rating)}",
			fontWeight = FontWeight.SemiBold,
			fontSize = 18.sp,
			maxLines = 1,
			overflow = TextOverflow.Visible
		)

	}
}

@Composable
@Preview
private fun KingOfTheGameRowPreview() {
	MatchKeeperTheme {
		KingOfTheGameRow(
			total = PlayerTotal(
				Person("Random Name Random Name Random Name Random Name Random Name"),
				gamesPlayed = 965,
				gamesWon = 789
			)
		)
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