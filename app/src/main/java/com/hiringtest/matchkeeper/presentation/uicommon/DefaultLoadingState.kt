package com.hiringtest.matchkeeper.presentation.uicommon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DefaultLoadingState(modifier: Modifier = Modifier) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.fillMaxHeight()
	) {
		CircularProgressIndicator(
			modifier = Modifier
				.width(64.dp)
				.height(64.dp)
				.align(Alignment.Center)
		)
	}
}