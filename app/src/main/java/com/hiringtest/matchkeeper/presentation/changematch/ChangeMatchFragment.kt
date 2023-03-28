package com.hiringtest.matchkeeper.presentation.changematch


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.hiringtest.matchkeeper.presentation.base.BaseFragment
import com.hiringtest.matchkeeper.presentation.theme.MatchKeeperTheme
import com.hiringtest.matchkeeper.utils.EMPTY
import com.hiringtest.matchkeeper.utils.appComponent
import javax.inject.Inject

private const val ARG_IS_EDIT = "arg_is_edit"
private const val ARG_MATCH_ID = "arg_match_id"

class ChangeMatchFragment : BaseFragment() {

	companion object {
		fun createBundle(isEdit: Boolean = false, matchId: String = String.EMPTY): Bundle =
			bundleOf(
				ARG_IS_EDIT to isEdit,
				ARG_MATCH_ID to matchId
			)
	}

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory

	private val viewModel: ChangeMatchViewModel by viewModels<ChangeMatchViewModel> { viewModelFactory }

	private val args: ChangeMatchFragmentArgs by navArgs()

	override fun onAttach(context: Context) {
		requireContext().appComponent.changeMatchComponent().create().inject(this)
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
						Scaffold { paddingValues ->
							ChangeMatchScreenContent(
								modifier = Modifier.padding(paddingValues),
								state = state,
								onPlayer1NameChanged = viewModel::firstPlayerNameChanged,
								onPlayer1ScoreChanged = viewModel::firstPlayerScoreChanged,
								onPlayer2NameChanged = viewModel::secondPlayerNameChanged,
								onPlayer2ScoreChanged = viewModel::secondPlayerScoreChanged,
								onDateChanged = viewModel::dateChanged,
								closeClick = ::close,
								saveClick = viewModel::saveMatch
							)
						}
					}
				}
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.initState(isEdit = args.argIsEdit, matchId = args.argMatchId)
		viewModel.closeData.observe(viewLifecycleOwner) { needClose ->
			if (needClose) {
				close()
			}
		}
	}

	private fun close() {
		router.back()
	}

}