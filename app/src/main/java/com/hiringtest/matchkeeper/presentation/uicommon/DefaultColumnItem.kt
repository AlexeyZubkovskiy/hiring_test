package com.hiringtest.matchkeeper.presentation.uicommon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultColumnItem(
	text: String,
	modifier: Modifier = Modifier,
	scrollable: Boolean = false,
	contentAlignment: Alignment = Alignment.CenterStart
) {
	val scrollState = rememberScrollState()
	Box(
		modifier = modifier
			.height(42.dp)
			.border(BorderStroke(0.3.dp, if (isSystemInDarkTheme()) Color.White else Color.Black))
			.padding(all = 4.dp),
		contentAlignment = contentAlignment
	) {
		Text(
			modifier = if (scrollable) Modifier.horizontalScroll(scrollState) else Modifier,
			textAlign = TextAlign.Center,
			fontSize = 16.sp,
			fontWeight = FontWeight.Normal,
			maxLines = 1,
			overflow = TextOverflow.Visible,
			text = text
		)
	}
}