package com.hiringtest.matchkeeper.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.hiringtest.matchkeeper.R
import com.hiringtest.matchkeeper.presentation.main.navigation.Router
import com.hiringtest.matchkeeper.presentation.main.navigation.RouterImpl
import com.hiringtest.matchkeeper.utils.lazyUnsafe

class MainActivity : AppCompatActivity() {

	val router: Router by lazyUnsafe {
		RouterImpl(this.findNavController(R.id.main_container))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}
}