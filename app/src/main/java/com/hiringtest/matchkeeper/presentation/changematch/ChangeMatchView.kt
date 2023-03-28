package com.hiringtest.matchkeeper.presentation.changematch

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiringtest.matchkeeper.R
import com.hiringtest.matchkeeper.data.entities.Person
import com.hiringtest.matchkeeper.data.entities.PlayersScore
import com.hiringtest.matchkeeper.presentation.theme.MatchKeeperTheme
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultErrorState
import com.hiringtest.matchkeeper.presentation.uicommon.DefaultLoadingState
import com.hiringtest.matchkeeper.utils.formatDefault
import java.util.Calendar
import java.util.Date

//Long parameters list can be optimised to object
@Composable
fun ChangeMatchScreenContent(
	modifier: Modifier = Modifier,
	state: ChangeMatchViewModel.State,
	onPlayer1NameChanged: (String) -> Unit,
	onPlayer1ScoreChanged: (Int) -> Unit,
	onPlayer2NameChanged: (String) -> Unit,
	onPlayer2ScoreChanged: (Int) -> Unit,
	onDateChanged: (Date) -> Unit,
	closeClick: () -> Unit,
	saveClick: () -> Unit
) {

	when (state) {
		is ChangeMatchViewModel.State.Content -> ChangeMatchContent(
			modifier = modifier,
			state = state,
			onPlayer1NameChanged = onPlayer1NameChanged,
			onPlayer1ScoreChanged = onPlayer1ScoreChanged,
			onPlayer2NameChanged = onPlayer2NameChanged,
			onPlayer2ScoreChanged = onPlayer2ScoreChanged,
			onDateChanged = onDateChanged,
			closeClick = closeClick,
			saveClick = saveClick
		)
		is ChangeMatchViewModel.State.Error   -> DefaultErrorState(text = state.cause)
		ChangeMatchViewModel.State.Loading    -> DefaultLoadingState()
	}

}

@Composable
private fun ChangeMatchContent(
	modifier: Modifier = Modifier,
	state: ChangeMatchViewModel.State.Content,
	onPlayer1NameChanged: (String) -> Unit,
	onPlayer1ScoreChanged: (Int) -> Unit,
	onPlayer2NameChanged: (String) -> Unit,
	onPlayer2ScoreChanged: (Int) -> Unit,
	onDateChanged: (Date) -> Unit,
	closeClick: () -> Unit,
	saveClick: () -> Unit
) {

	val context = LocalContext.current

	val date = when (state) {
		is ChangeMatchViewModel.State.Content.Create -> state.input.date
		is ChangeMatchViewModel.State.Content.Edit   -> state.input.date
		else                                         -> throw IllegalStateException("Unsupported state ${state.javaClass.name}")
	}

	val title = when (state) {
		is ChangeMatchViewModel.State.Content.Create -> context.getString(R.string.create_match)
		is ChangeMatchViewModel.State.Content.Edit   -> context.getString(R.string.edit_match)
		else                                         -> throw IllegalStateException("Unsupported state ${state.javaClass.name}")
	}

	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(4.dp)
	) {

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalArrangement = Arrangement.SpaceBetween
		) {

			Text(
				text = title,
				textAlign = TextAlign.Start,
				fontSize = 24.sp,
				fontWeight = FontWeight.SemiBold
			)

			Text(
				modifier = Modifier
					.clickable { closeClick() }
					.padding(8.dp),
				text = stringResource(id = R.string.cancel),
				fontSize = 16.sp,
			)
		}

		Button(
			modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
			onClick = {
				prepareDatePickerDialog(
					context = context,
					date = date,
					datePickedListener = onDateChanged
				).show()
			},
			shape = RoundedCornerShape(4.dp)
		) {
			Text(
				text = date.formatDefault(),
				fontSize = 14.sp
			)
		}

		val (playerScore1, playerScore2) = when (state) {
			is ChangeMatchViewModel.State.Content.Create -> state.input.first to state.input.second
			is ChangeMatchViewModel.State.Content.Edit   -> state.input.first to state.input.second
			else                                         -> throw IllegalStateException("Unsupported state ${state.javaClass.name}")
		}

		PlayerScore(
			nameTitle = stringResource(id = R.string.player_1),
			initialName = playerScore1.player.name,
			scoreTitle = stringResource(id = R.string.score),
			scoreValue = playerScore1.score,
			nameChanged = onPlayer1NameChanged,
			scoreChanged = onPlayer1ScoreChanged
		)

		Spacer(modifier = Modifier.height(32.dp))

		PlayerScore(
			nameTitle = stringResource(id = R.string.player_2),
			initialName = playerScore2.player.name,
			scoreTitle = stringResource(id = R.string.score),
			scoreValue = playerScore2.score,
			nameChanged = onPlayer2NameChanged,
			scoreChanged = onPlayer2ScoreChanged
		)

		Spacer(modifier = Modifier.height(32.dp))

		Box(
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth(),
			contentAlignment = Alignment.CenterEnd
		) {
			Button(
				onClick = { saveClick() },
				colors = ButtonDefaults.buttonColors(
					backgroundColor = MaterialTheme.colors.primaryVariant
				)
			) {
				Text(
					text = stringResource(id = R.string.save),
					fontWeight = FontWeight.SemiBold,
					fontSize = 16.sp
				)
			}
		}
	}
}

fun prepareDatePickerDialog(
	context: Context,
	date: Date,
	datePickedListener: (Date) -> Unit
): DatePickerDialog {
	val calendar = Calendar.getInstance()
	calendar.time = date

	return DatePickerDialog(
		context,
		{ _, year, month, dayOfMonth ->
			calendar.set(Calendar.YEAR, year)
			calendar.set(Calendar.MONTH, month)
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
			datePickedListener(calendar.time)
		},
		calendar.get(Calendar.YEAR),
		calendar.get(Calendar.MONTH),
		calendar.get(Calendar.DAY_OF_MONTH)
	)
}

@Composable
private fun PlayerScore(
	modifier: Modifier = Modifier,
	nameTitle: String,
	initialName: String,
	scoreTitle: String,
	scoreValue: Int,
	nameChanged: (String) -> Unit,
	scoreChanged: (Int) -> Unit
) {

	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp)
	) {

		val titleWidth = 124.dp
		Row(modifier = Modifier) {
			Text(
				modifier = Modifier
					.width(titleWidth)
					.align(Alignment.CenterVertically),
				text = nameTitle,
				textAlign = TextAlign.Start,
				fontSize = 20.sp,
				fontWeight = FontWeight.SemiBold,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.background(Color.LightGray, RoundedCornerShape(6.dp))
					.padding(8.dp)
			) {
				BasicTextField(
					modifier = Modifier.fillMaxWidth(),
					value = initialName,
					textStyle = TextStyle.Default.copy(fontSize = 18.sp),
					maxLines = 1,
					onValueChange = nameChanged,
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
				)
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		Row {
			Text(
				modifier = Modifier
					.width(titleWidth)
					.align(Alignment.CenterVertically),
				text = scoreTitle,
				textAlign = TextAlign.Start,
				fontSize = 20.sp,
				fontWeight = FontWeight.SemiBold,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)

			Box(
				modifier = Modifier
					.fillMaxWidth()
					.background(Color.LightGray, RoundedCornerShape(6.dp))
					.padding(8.dp)
			) {
				BasicTextField(
					modifier = Modifier.fillMaxWidth(),
					value = scoreValue.toString(),
					textStyle = TextStyle.Default.copy(fontSize = 18.sp),
					maxLines = 1,
					onValueChange = { newValue ->
						//very simplified validation of Int
						try {
							val value = newValue.toInt()
							scoreChanged(value)
						} catch (e: java.lang.NumberFormatException) {
							scoreChanged(0)
						}
					},
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
				)
			}
		}
	}
}

@Preview
@Composable
fun CreateMatchContentPreview() {
	MatchKeeperTheme {
		ChangeMatchContent(
			state = ChangeMatchViewModel.State.Content.Create(
				ChangeMatchViewModel.MatchData(
					Date(),
					PlayersScore.EMPTY.copy(player = Person(name = "Amos")),
					PlayersScore.EMPTY.copy(player = Person(name = "Diego"))
				)
			),
			onPlayer1NameChanged = {},
			onPlayer1ScoreChanged = {},
			onPlayer2NameChanged = {},
			onPlayer2ScoreChanged = {},
			onDateChanged = { },
			closeClick = {},
			saveClick = {}
		)
	}
}


@Preview
@Composable
private fun PlayerScorePreview() {
	MatchKeeperTheme() {
		PlayerScore(
			nameTitle = "Player 1",
			initialName = "Amos",
			scoreTitle = "Score",
			scoreValue = 5,
			nameChanged = {},
			scoreChanged = {}
		)
	}
}