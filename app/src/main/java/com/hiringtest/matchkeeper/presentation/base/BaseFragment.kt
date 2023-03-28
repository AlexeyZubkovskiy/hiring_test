package com.hiringtest.matchkeeper.presentation.base

import androidx.fragment.app.Fragment
import com.hiringtest.matchkeeper.presentation.main.MainActivity
import com.hiringtest.matchkeeper.presentation.main.navigation.Router

abstract class BaseFragment : Fragment() {

	val router: Router
		get() = (activity as? MainActivity)?.router
			?: throw IllegalStateException("This fragment doesn't attach to MainActivity")

}