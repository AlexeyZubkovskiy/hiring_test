package com.hiringtest.matchkeeper.presentation.matches

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.hiringtest.matchkeeper.R
import com.hiringtest.matchkeeper.presentation.base.BaseFragment
import com.hiringtest.matchkeeper.presentation.theme.MatchKeeperTheme
import com.hiringtest.matchkeeper.utils.EMPTY
import com.hiringtest.matchkeeper.utils.appComponent
import javax.inject.Inject

class MatchesFragment : BaseFragment() {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory

	private val viewModel: MatchesViewModel by viewModels<MatchesViewModel> { viewModelFactory }

	override fun onAttach(context: Context) {
		requireContext().appComponent.matchesComponent().create().inject(this)
		super.onAttach(context)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		return ComposeView(requireContext()).apply {
			setContent {

				val observableState = viewModel.state.observeAsState()

				observableState.value?.let { state ->
					MatchKeeperTheme {
						Scaffold(
							topBar = {
								TopAppBar {
									Icon(
										modifier = Modifier.padding(16.dp).clickable { router.showPlayers() },
										painter = painterResource(id = R.drawable.ic_group_24),
										contentDescription = String.EMPTY
									)
								}
							},
							floatingActionButton = {
								if (state is MatchesViewModel.State.Content) {
									FloatingActionButton(onClick = { router.showCreateNewMatch() }) {
										Image(
											painter = painterResource(id = R.drawable.ic_add_24dp),
											contentDescription = String.EMPTY
										)
									}
								}
							},
							content = { padding ->
								MatchesScreenContent(
									modifier = Modifier.padding(padding),
									state = state,
									editClick = { match -> router.showEditMatch(match.id) },
								)
							}
						)
					}
				}
			}
		}
	}
}