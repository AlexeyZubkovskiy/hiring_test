package com.hiringtest.matchkeeper.presentation.uicommon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun DefaultErrorState(modifier: Modifier = Modifier, text: String) {
	Text(
		modifier = modifier
			.fillMaxWidth()
			.background(Color.Red),
		text = text,
		color = Color.Black,
		fontSize = 20.sp,
		fontWeight = FontWeight.ExtraBold
	)
}