package com.hiringtest.matchkeeper.presentation.players

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.hiringtest.matchkeeper.presentation.base.BaseFragment
import com.hiringtest.matchkeeper.presentation.theme.MatchKeeperTheme
import com.hiringtest.matchkeeper.utils.appComponent
import javax.inject.Inject

class PlayersFragment : BaseFragment() {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory

	private val viewModel: PlayersViewModel by viewModels<PlayersViewModel> { viewModelFactory }

	override fun onAttach(context: Context) {
		super.onAttach(context)
		requireContext().appComponent.playersComponent().create().inject(this)
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
					Scaffold { paddingValues ->
						MatchKeeperTheme {
							PlayersScreenContent(
								modifier = Modifier.padding(paddingValues),
								state = state,
								sortChangeListener = viewModel::sortOrderChanged
							)
						}

					}
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.loadInitial()
	}

}