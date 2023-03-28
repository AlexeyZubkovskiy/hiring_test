package com.hiringtest.matchkeeper.presentation.matches

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiringtest.matchkeeper.R
import com.hiringtest.matchkeeper.data.entities.Match
import com.hiringtest.matchkeeper.presentation.theme.MatchKeeperTheme
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultColumnItem
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultErrorState
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultLoadingState
import com.hiringtest.matchkeeper.utils.generateMatches
import com.hiringtest.matchkeeper.utils.formatDefault


private const val DATE_COLUMN_WEIGHT = 0.24f
private const val PLAYER_COLUMN_WEIGHT = 0.28f
private const val SCORE_COLUMN_WEIGHT = 0.1f

@Composable
fun MatchesScreenContent(
	modifier: Modifier = Modifier,
	state: MatchesViewModel.State,
	editClick: (Match) -> Unit
) {

	when (state) {
		is MatchesViewModel.State.Content -> MatchesScreenContentFilled(
			modifier = modifier,
			state = state,
			onEditClick = editClick
		)
		is MatchesViewModel.State.Error   -> DefaultErrorState(text = state.cause)
		MatchesViewModel.State.Loading    -> DefaultLoadingState()

	}

}

@Composable
private fun MatchesScreenContentFilled(
	modifier: Modifier = Modifier,
	state: MatchesViewModel.State.Content,
	onEditClick: (Match) -> Unit
) {
	Column(modifier = modifier.fillMaxSize()) {
		MatchesHeader()
		LazyColumn {
			items(state.matches) { match: Match ->
				MatchItem(match = match, longClickListener = onEditClick)
			}
		}
	}
}

@Composable
private fun MatchesHeader(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		HeaderText(
			modifier = Modifier.weight(DATE_COLUMN_WEIGHT),
			text = stringResource(id = R.string.date),
		)
		HeaderText(
			modifier = Modifier.weight(PLAYER_COLUMN_WEIGHT + SCORE_COLUMN_WEIGHT),
			text = stringResource(id = R.string.first_player)
		)
		HeaderText(
			modifier = Modifier.weight(PLAYER_COLUMN_WEIGHT + SCORE_COLUMN_WEIGHT),
			text = stringResource(id = R.string.second_player)
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MatchItem(match: Match, longClickListener: (Match) -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.combinedClickable(
				onLongClick = { longClickListener(match) },
				onClick = {}
			),
		verticalAlignment = Alignment.CenterVertically,
	) {
		DefaultColumnItem(
			text = match.date.formatDefault(),
			modifier = Modifier.weight(DATE_COLUMN_WEIGHT),
			scrollable = true
		)

		DefaultColumnItem(
			text = match.first.player.name,
			modifier = Modifier.weight(PLAYER_COLUMN_WEIGHT),
			scrollable = true
		)

		DefaultColumnItem(
			text = match.first.score.toString(),
			modifier = Modifier.weight(SCORE_COLUMN_WEIGHT),
			scrollable = true,
			contentAlignment = Alignment.Center
		)

		DefaultColumnItem(
			text = match.second.player.name,
			modifier = Modifier.weight(PLAYER_COLUMN_WEIGHT),
			scrollable = true
		)

		DefaultColumnItem(
			text = match.second.score.toString(),
			modifier = Modifier.weight(SCORE_COLUMN_WEIGHT),
			scrollable = true,
			contentAlignment = Alignment.Center
		)
	}
}

@Composable
private fun HeaderText(
	text: String,
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.height(64.dp)
			.border(BorderStroke(0.3.dp, if (isSystemInDarkTheme()) Color.White else Color.Black)),
		contentAlignment = Alignment.Center
	) {
		Text(
			textAlign = TextAlign.Center,
			fontSize = 20.sp,
			fontWeight = FontWeight.SemiBold,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			text = text
		)
	}
}

@Preview
@Composable
private fun MatchesScreenPreview() {
	MatchKeeperTheme {
		MatchesScreenContent(
			state = MatchesViewModel.State.Content(
				matches = generateMatches()
			),
			editClick = {}
		)
	}
}

@Preview
@Composable
private fun MatchesHeaderPreview() {
	MatchKeeperTheme {
		MatchesHeader()
	}
}